using JetBrains.Annotations;
using materiels_service.data;
using materiels_service.entity;
using materiels_service.repository;
using Microsoft.EntityFrameworkCore;
using Moq;

namespace MaterielsService.Tests.repository;

[TestSubject(typeof(MaterielRepository))]
public class MaterielRepositoryTest
{
    private readonly ApplicationDbContext _context;
    private readonly MaterielRepository _repository;

    public MaterielRepositoryTest()
    {
        // Setup the In-Memory database for testing
        var connectionString = "server=178.16.129.132;port=3307;database=test_db;user=root;password="; // Replace with your test MySQL connection string

        // Set up DbContext options to use MySQL for tests
        var options = new DbContextOptionsBuilder<ApplicationDbContext>()
            .UseMySql(connectionString, ServerVersion.AutoDetect(connectionString)) // Configure MySQL
            .Options;

        _context = new ApplicationDbContext(options);
        _repository = new MaterielRepository(_context);

        // Seed the database with test data
        SeedDatabase();
    }

    private void SeedDatabase()
    {
        // Ensure unique IDs and clean any existing data
        _context.Database.EnsureDeleted();  // Ensure the database is reset
        _context.Database.EnsureCreated();  // Recreate the schema

        // Add test data with unique Ids
        var materiels = new List<Materiel>
        {
            new Materiel { Id = 1, Description = "Materiel 1", Available = true, Date = DateTime.Now, Serie = "SerieA" },
            new Materiel { Id = 2, Description = "Materiel 2", Available = false, Date = DateTime.Now.AddDays(-1), Serie = "SerieB" },
            new Materiel { Id = 3, Description = "Materiel 3", Available = true, Date = DateTime.Now.AddDays(1), Serie = "SerieC" }
        };

        // Add the materiels to the in-memory database
        _context.Materiels.AddRange(materiels);
        _context.SaveChanges();
    }

    [Fact]
    public void GetAllMateriels_ShouldReturnAllMateriels()
    {
        // Act: Call the method you want to test
        var result = _repository.GetAllMateriels();

        // Assert: Verify the results
        Assert.Equal(3, result.Count());
    }

    [Fact]
    public void GetMaterielById_ShouldReturnCorrectMateriel()
    {
        // Act: Get a Materiel by ID
        var result = _repository.GetMaterielById(1);

        // Assert: Verify the Materiel is correct
        Assert.NotNull(result);
        Assert.Equal(1, result?.Id);
        Assert.Equal("Materiel 1", result?.Description);
    }

    [Fact]
    public void GetMaterielsByDate_ShouldReturnCorrectMateriels()
    {
        // Arrange: Ensure the database is clean and seed it with data
        var date = DateTime.Now.Date; // Use only the date part
        var materiel1 = new Materiel 
        { 
            Id = 66, 
            Description = "Materiel 1", 
            Available = true, 
            Date = date, 
            Serie = "SerieA" 
        };
    
        var materiel2 = new Materiel 
        { 
            Id = 77, 
            Description = "Materiel 2", 
            Available = false, 
            Date = date.AddDays(-1), 
            Serie = "SerieB" 
        };

        // Detach any existing entities tracked by the context before seeding
        foreach (var entity in _context.ChangeTracker.Entries<Materiel>())
        {
            entity.State = EntityState.Detached;
        }

        // Add materiels only if needed for the specific test
        _context.Materiels.Add(materiel1);
        _context.Materiels.Add(materiel2);
        _context.SaveChanges();

        // Act: Get Materiels by date
        var result = _repository.GetMaterielsByDate(date);

        // Assert: Verify the Materiels are filtered by the correct date
        Assert.Single(result);  // Only 1 Materiel with the specified date should match
        Assert.Equal("Materiel 1", result.FirstOrDefault()?.Description); // Verify the name
    }

    [Fact]
    public void GetMaterielsBySerie_ShouldReturnCorrectMateriels()
    {
        // Act: Get Materiels by Serie
        var serie = "SerieA";
        var result = _repository.GetMaterielsBySerie(serie);

        // Assert: Verify the Materiels are filtered by the correct Serie
        Assert.Single(result);
        Assert.Equal("Materiel 1", result.FirstOrDefault()?.Description);
    }

    [Fact]
    public void GetAvailableMateriels_ShouldReturnAvailableMateriels()
    {
        // Act: Get available Materiels
        var result = _repository.GetAvailableMateriels();

        // Assert: Verify the result contains only available Materiels
        Assert.Equal(2, result.Count());
        Assert.All(result, materiel => Assert.True(materiel.Available));
    }

    [Fact]
    public void AddMateriel_ShouldAddMaterielToDatabase()
    {
        // Arrange: Create a new Materiel
        var newMateriel = new Materiel { Description = "Materiel 4", Available = true, Date = DateTime.Now, Serie = "SerieD" };

        // Act: Add the new Materiel
        _repository.AddMateriel(newMateriel);
        _context.SaveChanges();

        // Assert: Verify the Materiel was added
        var result = _context.Materiels.SingleOrDefault(m => m.Description == "Materiel 4");
        Assert.NotNull(result);
        Assert.Equal("Materiel 4", result?.Description);
    }

    [Fact]
    public void UpdateMateriel_ShouldUpdateMaterielInDatabase()
    {
        // Arrange: Get the Materiel to update
        var materielToUpdate = _context.Materiels.FirstOrDefault(m => m.Id == 1);
        if (materielToUpdate != null)
        {
            materielToUpdate.Description = "Updated Materiel 1";
        }

        // Act: Update the Materiel
        _repository.UpdateMateriel(materielToUpdate);

        // Assert: Verify the Materiel was updated
        var result = _context.Materiels.SingleOrDefault(m => m.Id == 1);
        Assert.NotNull(result);
        Assert.Equal("Updated Materiel 1", result?.Description);
    }
}
