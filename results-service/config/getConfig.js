const axios = require('axios');

// Function to fetch configuration from the Spring Cloud Config Server
async function getConfigFromCloud() {
    const configServerUrl = process.env.CONFIG_SERVICE_URL ?? 'http://localhost:9999'; // URL of your Spring Cloud Config Server
    const appName = 'results-service';  // Replace with your app name (same as defined in the Spring Cloud Config)
    const profile = 'default';     // This is typically 'default' for dev, prod, etc.

    try {
        const response = await axios.get(`${configServerUrl}/${appName}/${profile}`);
        return response.data;  // This contains the configuration data
    } catch (error) {
        console.error('Error fetching configuration:', error);
        throw error;
    }
}

module.exports = getConfigFromCloud;
