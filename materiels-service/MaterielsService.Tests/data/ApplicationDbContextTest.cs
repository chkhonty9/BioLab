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
            _options = new DbContextOptionsBuilder<ApplicationDbContext>()
                .UseInMemoryDatabase(databaseName: Guid.NewGuid().ToString()) // Unique database name for each test
                .Options;
        }

        [Fact]
        public void AddMateriel_ShouldAddMaterielToDatabase()
        {
            // Arrange
            using (var context = new ApplicationDbContext(_options))
            {
                var materiel = new Materiel
                {
                    Id = 1, // Ensure a unique ID for the test
                    Description = "Test Materiel",
                    Available = true,
                    Date = DateTime.Now,
                    Serie = "A123"
                };

                // Act
                context.Materiels.Add(materiel);
                context.SaveChanges();
            }

            // Assert
            using (var context = new ApplicationDbContext(_options))
            {
                var materiel = context.Materiels.SingleOrDefault(m => m.Id == 1);
                Assert.NotNull(materiel);
                Assert.Equal("Test Materiel", materiel.Description);
            }
        }

        [Fact]
        public void GetMaterielById_ShouldReturnMateriel_WhenItExists()
        {
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
            // Arrange
            using (var context = new ApplicationDbContext(_options))
            {
                var materiel = new Materiel
                {
                    Id = 1,
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
                var materielToUpdate = context.Materiels.SingleOrDefault(m => m.Id == 1);
                materielToUpdate.Description = "Updated Materiel";
                context.SaveChanges();
            }

            // Assert
            using (var context = new ApplicationDbContext(_options))
            {
                var updatedMateriel = context.Materiels.SingleOrDefault(m => m.Id == 1);
                Assert.NotNull(updatedMateriel);
                Assert.Equal("Updated Materiel", updatedMateriel.Description);
            }
        }

        [Fact]
        public void DeleteMateriel_ShouldRemoveMaterielFromDatabase()
        {
            // Arrange
            using (var context = new ApplicationDbContext(_options))
            {
                var materiel = new Materiel
                {
                    Id = 1,
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
                var materielToDelete = context.Materiels.SingleOrDefault(m => m.Id == 1);
                context.Materiels.Remove(materielToDelete);
                context.SaveChanges();
            }

            // Assert
            using (var context = new ApplicationDbContext(_options))
            {
                var deletedMateriel = context.Materiels.SingleOrDefault(m => m.Id == 1);
                Assert.Null(deletedMateriel);
            }
        }
    }
}
