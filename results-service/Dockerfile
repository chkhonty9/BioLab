# Use the official Node 20 image
FROM node:20 as build

# Set the working directory for your application inside the container
WORKDIR /usr/src/app

# Copy the package.json and package-lock.json (if present) to install dependencies first
COPY package*.json ./

# Install the dependencies specified in package.json
RUN npm install

# Copy the rest of the project files to the container
COPY . .

# Expose the port your app will run on (change if necessary)
EXPOSE 3000

# Set environment variable for production (optional but recommended)
#ENV NODE_ENV=production

# Command to run your Node.js application (typically 'node app.js' or 'npm start')
CMD ["npm", "start"]
