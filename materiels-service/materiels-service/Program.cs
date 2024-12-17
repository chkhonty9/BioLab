using materiels_service.config;
using materiels_service.data;
using materiels_service.GraphQL.mutations;
using materiels_service.GraphQL.queries;
using materiels_service.GraphQL.types;
using materiels_service.repository;
using materiels_service.service;
using Steeltoe.Discovery.Client;
using Steeltoe.Extensions.Configuration.ConfigServer;
using Steeltoe.Extensions.Configuration.Placeholder;
using Microsoft.EntityFrameworkCore;

var builder = WebApplication.CreateBuilder(args);


// Add Steeltoe features for config server and discovery client
builder.AddPlaceholderResolver(); // For ${..:..} syntax in variables
builder.AddConfigServer(); // Fetch properties from Spring Cloud Config Server
builder.Services.Configure<AppConfiguration>(builder.Configuration.GetSection("AppConfiguration"));
builder.AddDiscoveryClient();    // Enable Eureka Discovery Client

/*
// Add services to the container.
builder.Services.AddDbContext<ApplicationDbContext>(options =>
    options.UseInMemoryDatabase("MaterielDB")); // For testing with an in-memory database
    */

// Fetch the connection string from environment variables
var connectionString = builder.Configuration["database:connection-string"] ?? 
                       "Server=localhost;Database=my_db;User=root;Password=nano@password";

// Use the connection string to configure DbContext
builder.Services.AddDbContext<ApplicationDbContext>(options =>
    options.UseMySql(connectionString, ServerVersion.AutoDetect(connectionString))
);

builder.Services.AddControllers();  

// Register repositories and services
builder.Services.AddScoped<IMaterielRepository, MaterielRepository>();
builder.Services.AddScoped<IMaterielService, MaterielService>();

// Add GraphQL Server configuration
builder.Services
    .AddGraphQLServer()
    .AddQueryType<MaterielQuery>()
    .AddMutationType<MaterielMutation>()
    .AddType<MaterielType>();

// Add Health Check services
builder.Services.AddHealthChecks();



// Add Swagger for API Documentation (optional)
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

var app = builder.Build();



app.UseRouting();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

// GraphQL endpoint mapping
app.MapGraphQL();

// Enable HTTPS redirection
app.UseHttpsRedirection();

app.MapControllers();

// Configure the HTTP request pipeline.
app.MapHealthChecks("/health");

// Ensure app runs correctly
app.Run();