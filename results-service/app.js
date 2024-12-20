require('dotenv').config();
const express = require('express');
const axios = require('axios');
const Eureka = require('eureka-js-client').Eureka; // Eureka JS Client
const getConfigFromCloud = require('./config/getConfig');
const resultRoutes = require('./routes/result.routes');
const healthCheck = require('./utils/healthCheck');
const errorHandler = require('./middleware/errorHandler');
const pool = require('./db/db'); // Import MySQL pool
const { startEurekaClient } = require('./eureka/eureka-client');
const eurekaHelper = require('./eureka/eureka-helper');

const app = express();

// Middleware to parse incoming JSON and URL-encoded data
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

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

            process.env.MYSQL_HOST = new URL(datasource.url).host || '178.16.129.132'; // Get MySQL host from URL
            process.env.MYSQL_USER = datasource.username || 'root';              // MySQL username
            process.env.MYSQL_PASSWORD = datasource.password || '';              // MySQL password
            process.env.MYSQL_DATABASE = new URL(datasource.url).pathname.split('/')[1] || 'my_db'; // Extract database name from URL
            process.env.MYSQL_PORT = new URL(datasource.url).port || '3307';     // MySQL port

            // Optional: Print the values to verify if they are being set correctly
            console.log(`Connecting to database at ${process.env.MYSQL_HOST}:${process.env.MYSQL_PORT} with username: ${process.env.MYSQL_USER}`);
        }

        // Extract server port from config (fallback to 3000 if not set in config)
        const serverPort = config.server && config.server.port ? config.server.port : 3000;
        // Log the server port
        console.log(`Server is running on port ${serverPort}`);


        // Define application routes
        app.use('/api/results', resultRoutes);  // Define routes for the results
        app.get('/health', healthCheck);        // Health check route

        // Error handling middleware
        app.use(errorHandler);  // Error handling middleware

        //startEurekaClient();
        // Start the server with the configured port
        app.listen(serverPort, () => {
            console.log(`Server running on http://localhost:${serverPort}`);
        });
    } catch (error) {
        console.error('Could not fetch configuration:', error);
        process.exit(1);  // Exit the app if fetching configuration fails
    }
}

// Start the app
startApp();
eurekaHelper.registerWithEureka('results-service', 3000);
module.exports = app;
