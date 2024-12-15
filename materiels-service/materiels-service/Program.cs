using materiels_service.data;
using materiels_service.GraphQL.mutations;
using materiels_service.GraphQL.queries;
using materiels_service.GraphQL.types;
using materiels_service.repository;
using materiels_service.service;
using Microsoft.Extensions.Diagnostics.HealthChecks;  // For HealthCheckResult
using Microsoft.EntityFrameworkCore;

var builder = WebApplication.CreateBuilder(args);

/*
// Add services to the container.
builder.Services.AddDbContext<ApplicationDbContext>(options =>
    options.UseInMemoryDatabase("MaterielDB")); // For testing with an in-memory database
    */

// Fetch the connection string from environment variables
var connectionString = Environment.GetEnvironmentVariable("CONNECTION_STRING") ??
                       "Server=localhost;Database=my_db;User=root;Password=nano@password";  // Fallback for local development

// Use the connection string to configure DbContext
builder.Services.AddDbContext<ApplicationDbContext>(options =>
    options.UseMySql(connectionString, ServerVersion.AutoDetect(connectionString))
);

builder.Services.AddControllers();  

builder.Services.AddScoped<IMaterielRepository, MaterielRepository>();
builder.Services.AddScoped<IMaterielService, MaterielService>();

// Add GraphQL Server configuration
builder.Services
    .AddGraphQLServer()
    .AddQueryType<MaterielQuery>()
    .AddMutationType<MaterielMutation>()
    .AddType<MaterielType>();

// Add Health Check services
builder.Services.AddHealthChecks()
    .AddDbContextCheck<ApplicationDbContext>("Database Health")  // Check the health of the database
    .AddCheck("Custom Check", () => HealthCheckResult.Healthy("The custom check is OK."));

// Add Swagger for API Documentation (optional)
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

var app = builder.Build();

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
app.UseHealthChecks("/health");

// Ensure app runs correctly
app.Run();