require('dotenv').config();
const express = require('express');
const resultRoutes = require('./routes/resultRoutes');
const eurekaHelper = require('./eureka/eureka-helper');
const getConfigFromCloud = require('./config/getConfig');
const sequelize = require('./config/db');
const errorHandler = require('./middleware/errorHandler');
const healthCheck = require('./utils/healthCheck');
const app = express();
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

app.use(errorHandler);

// Start the application and fetch the configuration from Spring Cloud Config Server
async function startApp() {
    try {
        // Fetch configuration from Spring Cloud Config Server
        const config = await getConfigFromCloud();

        // Log the fetched configuration
        console.log('Fetched configuration from Spring Cloud Config:', config);

        // Set environment variables from fetched configuration
        if (config.spring && config.spring.datasource) {
            const datasource = config.spring.datasource;

            process.env.MYSQL_HOST = new URL(datasource.url).host || '178.16.129.132'; // MySQL host from URL
            process.env.MYSQL_USER = datasource.username || 'root';                     // MySQL username
            process.env.MYSQL_PASSWORD = datasource.password || '';                     // MySQL password
            process.env.MYSQL_DATABASE = new URL(datasource.url).pathname.split('/')[1] || 'my_db'; // MySQL database name
            process.env.MYSQL_PORT = new URL(datasource.url).port || '3307';            // MySQL port

            // Log database connection details
            console.log(`Connecting to database at ${process.env.MYSQL_HOST}:${process.env.MYSQL_PORT} with username: ${process.env.MYSQL_USER}`);
        }

        // Extract server port from config (defaults to 3000 if not set)
        const serverPort = config.server && config.server.port ? config.server.port : 3000;
        console.log(`Server will run on port ${serverPort}`);

        // Define application routes
        app.use('/api', resultRoutes); // Routes for handling result-related requests
        app.get('/health', healthCheck);// Health check route

        // Start the server with the configured port
        app.listen(serverPort, async () => {
            try {
                // Verify and establish a connection to MySQL database
                await sequelize.authenticate();
                await sequelize.sync(); // Sync models with the database
                console.log(`Database connected successfully and server is running on http://localhost:${serverPort}`);
            } catch (err) {
                console.error('Unable to connect to the database:', err);
                process.exit(1); // Exit the application if the database connection fails
            }
        });

        // Register the service with Eureka if needed
        eurekaHelper.registerWithEureka('results-service', serverPort);

    } catch (error) {
        console.error('Could not fetch configuration:', error);
        process.exit(1); // Exit the app if fetching configuration fails
    }
}

// Start the app
startApp();

// Export the app for testing
module.exports = app;
