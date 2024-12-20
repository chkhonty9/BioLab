# Set the base image to Python 3.9 (or any appropriate version)
FROM python:3.9-slim

# Set the working directory in the container
WORKDIR /app

# Copy the current directory contents into the container at /app
COPY . /app

# Install the dependencies required for the project (including Flask, GraphQL, and MySQL)
RUN pip install --no-cache-dir -r requirements.txt

# Expose the port your application will run on (Flask defaults to port 5000)
EXPOSE 5000

# Set the environment variable for Flask
ENV FLASK_APP=app.py
ENV FLASK_RUN_HOST=0.0.0.0

# Command to run the application
CMD ["python", "app.py"]
