using materiels_service.data;
using materiels_service.GraphQL.mutations;
using materiels_service.GraphQL.queries;
using materiels_service.GraphQL.types;
using materiels_service.repository;
using materiels_service.service;
using Microsoft.EntityFrameworkCore;
using MySql.Data.MySqlClient;
using Steeltoe.Discovery.Client;
using Steeltoe.Extensions.Configuration.ConfigServer;

var builder = WebApplication.CreateBuilder(args);

// Configure logging
builder.Logging.AddConsole();

builder.Configuration.AddConfigServer(new ConfigServerClientSettings
{
    Uri = builder.Configuration.GetValue<string>("CONFIG_SERVICE_URL") ?? "http://localhost:9999",
    Name = "materiels-service",
}, null);

builder.Services.AddDiscoveryClient(builder.Configuration);

// Fetch database connection string
var connectionString = builder.Configuration.GetConnectionString("DefaultConnection");
// Add MySQL DB context configuration
/*builder.Services.AddDbContext<ApplicationDbContext>(options =>
    options.UseMySql(connectionString, ServerVersion.AutoDetect(connectionString))
        .EnableSensitiveDataLogging()
        .LogTo(Console.WriteLine, LogLevel.Information)
);*/

// Add MySQL DbContext configuration
builder.Services.AddDbContext<ApplicationDbContext>(options =>
    options.UseMySql(connectionString, ServerVersion.AutoDetect(connectionString))
        .EnableSensitiveDataLogging()
        .LogTo(Console.WriteLine, LogLevel.Information)
        .EnableDetailedErrors()
);


// Add the necessary services
builder.Services.AddControllers();
builder.Services.AddScoped<IMaterielRepository, MaterielRepository>();
builder.Services.AddScoped<IMaterielService, MaterielService>();

// Add GraphQL server configuration
builder.Services
    .AddGraphQLServer()
    .AddQueryType<MaterielQuery>()
    .AddMutationType<MaterielMutation>()
    .AddType<MaterielType>()
    .ModifyRequestOptions(options => options.IncludeExceptionDetails = true);

// Add health check services
builder.Services.AddHealthChecks();

// Add Swagger (optional)
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen();

var app = builder.Build();

// Configure middleware (use Swagger in development)
if (app.Environment.IsDevelopment())
{
    app.UseSwagger();
    app.UseSwaggerUI();
}

// Map GraphQL endpoint
app.MapGraphQL();

// Enable HTTPS redirection
app.UseHttpsRedirection();

// Map controllers and health check endpoints
app.MapControllers();
app.MapHealthChecks("/health");

// Enable service discovery
app.UseDiscoveryClient();

app.Run();
