# Use an official Python 3.10 slim image
FROM python:3.10-slim

# Install necessary build dependencies
RUN apt-get update && apt-get install -y \
    build-essential \
    pkg-config \
    libmariadb-dev \
    libmariadb-dev-compat \
    && rm -rf /var/lib/apt/lists/*

# Set the working directory inside the container
WORKDIR /usr/src/app

# Copy the requirements.txt into the working directory
COPY requirements.txt ./

# Install Python dependencies from the requirements file
RUN pip install --no-cache-dir -r requirements.txt

# Optionally: Update the application to import from collections.abc if 'MutableMapping' is used.
# This approach avoids reliance on modifying installed package files directly.

# If the MutableMapping error happens in your application code, use this solution:
RUN echo "from collections.abc import MutableMapping" > /usr/src/app/fix_import.py \
    && python /usr/src/app/fix_import.py

# Copy the rest of the application files
COPY . .

# Set the default command to run your application
CMD ["python", "app.py"]
