using JetBrains.Annotations;
using materiels_service.data;
using materiels_service.entity;
using Microsoft.EntityFrameworkCore;
using System;
using Xunit;

namespace MaterielsService.Tests.data
{
    [TestSubject(typeof(ApplicationDbContext))]
    public class ApplicationDbContextTest
    {
        private DbContextOptions<ApplicationDbContext> _options;

        // Constructor to create a unique in-memory database for each test
        public ApplicationDbContextTest()
        {
            var connectionString = "server=178.16.129.132;port=3307;database=test_db;user=root;password="; // Replace with your test MySQL connection string

            // Set up DbContext options to use MySQL for tests
            _options = new DbContextOptionsBuilder<ApplicationDbContext>()
                .UseMySql(connectionString, ServerVersion.AutoDetect(connectionString)) // Configure MySQL
                .Options;
            
        }

        [Fact]
        public void AddMateriel_ShouldAddMaterielToDatabase()
        {
            // Arrange: Create a clean database and seed data
            ResetDatabase();
            using (var context = new ApplicationDbContext(_options))
            {
                var materiel = new Materiel
                {
                    Description = "Test Materiel",
                    Available = true,
                    Date = DateTime.Now,
                    Serie = "A123"
                };

                context.Materiels.Add(materiel);
                context.SaveChanges();
            }

            // Act: Retrieve the materiel
            using (var context = new ApplicationDbContext(_options))
            {
                var materiel = context.Materiels.SingleOrDefault(m => m.Serie == "A123"); // Ensure unique query condition
                Assert.NotNull(materiel);
                Assert.Equal("Test Materiel", materiel.Description);
            }
        }

        // Utility method to reset database state
        public void ResetDatabase()
        {
            using (var context = new ApplicationDbContext(_options))
            {
                context.Materiels.RemoveRange(context.Materiels);
                context.SaveChanges();
            }
        }


        [Fact]
        public void GetMaterielById_ShouldReturnMateriel_WhenItExists()
        {
            ResetDatabase();
            // Arrange
            using (var context = new ApplicationDbContext(_options))
            {
                context.Materiels.Add(new Materiel
                {
                    Id = 1,
                    Description = "Test Materiel",
                    Available = true,
                    Date = DateTime.Now,
                    Serie = "A123"
                });
                context.SaveChanges();
            }

            // Act & Assert
            using (var context = new ApplicationDbContext(_options))
            {
                var materiel = context.Materiels.SingleOrDefault(m => m.Id == 1);
                Assert.NotNull(materiel);
                Assert.Equal("Test Materiel", materiel.Description);
            }
        }

        [Fact]
        public void GetMaterielById_ShouldReturnNull_WhenMaterielDoesNotExist()
        {
            // Act & Assert
            using (var context = new ApplicationDbContext(_options))
            {
                var materiel = context.Materiels.SingleOrDefault(m => m.Id == 999);
                Assert.Null(materiel);
            }
        }

        [Fact]
        public void UpdateMateriel_ShouldUpdateExistingMateriel()
        {
            ResetDatabase();
            // Arrange: Ensure the database is clean or remove existing data before inserting new entries
            using (var context = new ApplicationDbContext(_options))
            {
                context.Materiels.RemoveRange(context.Materiels); // Clean up the table
                context.SaveChanges();
            }

            using (var context = new ApplicationDbContext(_options))
            {
                var materiel = new Materiel
                {
                    Description = "Test Materiel",
                    Available = true,
                    Date = DateTime.Now,
                    Serie = "A123"
                };

                context.Materiels.Add(materiel);
                context.SaveChanges();
            }

            // Act & Assert
            using (var context = new ApplicationDbContext(_options))
            {
                var materielToUpdate = context.Materiels.SingleOrDefault(m => m.Serie == "A123");
                Assert.NotNull(materielToUpdate); // Ensure materiel was found

                materielToUpdate.Description = "Updated Description";
                context.SaveChanges();  // Save updated values
            }
        }


        [Fact]
        public void DeleteMateriel_ShouldRemoveMaterielFromDatabase()
        {
            ResetDatabase();
            // Arrange
            using (var context = new ApplicationDbContext(_options))
            {
                var materiel = new Materiel
                {
                    Id = 3,
                    Description = "Test Materiel",
                    Available = true,
                    Date = DateTime.Now,
                    Serie = "A123"
                };

                context.Materiels.Add(materiel);
                context.SaveChanges();
            }

            // Act
            using (var context = new ApplicationDbContext(_options))
            {
                var materielToDelete = context.Materiels.SingleOrDefault(m => m.Id == 3);
                context.Materiels.Remove(materielToDelete);
                context.SaveChanges();
            }

            // Assert
            using (var context = new ApplicationDbContext(_options))
            {
                var deletedMateriel = context.Materiels.SingleOrDefault(m => m.Id == 3);
                Assert.Null(deletedMateriel);
            }
        }
    }
}
