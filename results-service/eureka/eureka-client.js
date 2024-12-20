const Eureka = require('eureka-js-client').Eureka;

// Eureka client setup
const startEurekaClient = () => {
    const url = process.env.DISCOVERY_SERVICE_URL ?? 'http://localhost:8761/eureka';
    console.log('URL to be used: ' + url);

    // Parse the URL
    const parsedUrl = new URL(url);
    console.log('PArsed URL : ', parsedUrl);


    // Ensure that the URL format is valid
    if (!parsedUrl.hostname || !parsedUrl.port) {
        console.error('Invalid URL: Unable to parse Eureka service URL correctly.');
        return;
    }

    // Eureka client configuration
    const client = new Eureka({
        instance: {
            app: 'results-service',
            hostName: 'localhost',
            ipAddr: '127.0.0.1',
            port: 3000,
            vipAddress: 'results-service',
            dataCenterInfo: {
                name: 'MyOwn'
            },
        },
        eureka: {
            host: parsedUrl.hostname,
            port: parsedUrl.port,
            servicePath: '/eureka/apps/'
        }
    });

    // Start Eureka client
    client.start((error) => {
        if (error) {
            console.log('Error during Eureka client startup:', error);
            if (error.response) {
                console.log('Error details:', error.response.body);
                console.log('Status Code:', error.response.statusCode);
            }
        } else {
            console.log('Eureka client started successfully');
        }
    });
};

// Export the Eureka start function for external usage
module.exports = { startEurekaClient };
