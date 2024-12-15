# Use the official .NET 8 SDK image for building the application
FROM mcr.microsoft.com/dotnet/sdk:8.0 AS build

# Set the working directory to /src
WORKDIR /src

# Copy the project file(s) and restore any dependencies
COPY ./materiels-service/materiels-service.csproj ./materiels-service/
RUN dotnet restore "./materiels-service/materiels-service.csproj"

# Copy all the source files
COPY ./materiels-service/. ./materiels-service/

# Build the application
WORKDIR /src/materiels-service
RUN dotnet build "materiels-service.csproj" -c Release -o /app/build

# Publish the application to the /app directory
RUN dotnet publish "materiels-service.csproj" -c Release -o /app/publish

# Create a final image using the runtime image
FROM mcr.microsoft.com/dotnet/aspnet:8.0 AS final

# Set the working directory to /app
WORKDIR /app

# Copy the build output from the build stage
COPY --from=build /app/publish .

# Set the entry point for the application
ENTRYPOINT ["dotnet", "materiels-service.dll"]
