using materiels_service.data;
using materiels_service.GraphQL.mutations;
using materiels_service.GraphQL.queries;
using materiels_service.GraphQL.types;
using materiels_service.repository;
using materiels_service.service;
using Microsoft.EntityFrameworkCore;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
builder.Services.AddDbContext<ApplicationDbContext>(options =>
    options.UseInMemoryDatabase("MaterielDB")); // For testing with an in-memory database
builder.Services.AddControllers();  

builder.Services.AddScoped<IMaterielRepository, MaterielRepository>();
builder.Services.AddScoped<IMaterielService, MaterielService>();

// Add GraphQL Server configuration
builder.Services
    .AddGraphQLServer()
    .AddQueryType<MaterielQuery>()
    .AddMutationType<MaterielMutation>()
    .AddType<MaterielType>();

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

// Ensure app runs correctly
app.Run();