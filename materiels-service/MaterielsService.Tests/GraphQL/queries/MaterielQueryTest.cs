using HotChocolate.AspNetCore;
using JetBrains.Annotations;
using HotChocolate.AspNetCore;
using materiels_service.entity;
using materiels_service.GraphQL.queries;
using materiels_service.service;
using Moq;

namespace MaterielsService.Tests.GraphQL.queries;

[TestSubject(typeof(MaterielQuery))]
public class MaterielQueryTest
{
    private readonly Mock<IMaterielService> _materielServiceMock;
    private readonly MaterielQuery _materielQuery;

    public MaterielQueryTest()
    {
        _materielServiceMock = new Mock<IMaterielService>();
        _materielQuery = new MaterielQuery();
    }

    [Fact]
    public void GetAllMateriels_ShouldReturnAllMateriels()
    {
        // Arrange
        var materiels = new[]
        {
            new Materiel { Id = 1, Description = "Materiel 1" },
            new Materiel { Id = 2, Description = "Materiel 2" }
        }.AsQueryable();

        _materielServiceMock.Setup(service => service.GetAllMateriels())
            .Returns(materiels);

        // Act
        var result = _materielQuery.GetAllMateriels(_materielServiceMock.Object);

        // Assert
        Assert.NotNull(result);
        Assert.Equal(2, result.Count());
        Assert.Equal("Materiel 1", result.First().Description);
        _materielServiceMock.Verify(service => service.GetAllMateriels(), Times.Once);
    }

    [Fact]
    public void GetMaterielById_ShouldReturnMateriel_WhenItExists()
    {
        // Arrange
        var materiel = new Materiel { Id = 1, Description = "Test Materiel" };
        _materielServiceMock.Setup(service => service.GetMaterielById(1))
            .Returns(materiel);

        // Act
        var result = _materielQuery.GetMaterielById(1, _materielServiceMock.Object);

        // Assert
        Assert.NotNull(result);
        Assert.Equal("Test Materiel", result.Description);
        _materielServiceMock.Verify(service => service.GetMaterielById(1), Times.Once);
    }

    [Fact]
    public void GetMaterielById_ShouldReturnNull_WhenMaterielDoesNotExist()
    {
        // Arrange
        _materielServiceMock.Setup(service => service.GetMaterielById(999))
            .Returns((Materiel)null);

        // Act
        var result = _materielQuery.GetMaterielById(999, _materielServiceMock.Object);

        // Assert
        Assert.Null(result);
        _materielServiceMock.Verify(service => service.GetMaterielById(999), Times.Once);
    }

    [Fact]
    public void GetMaterielsByDate_ShouldReturnMaterielsForGivenDate()
    {
        // Arrange
        var date = DateTime.Now.Date;
        var materiels = new[]
        {
            new Materiel { Id = 1, Date = date, Description = "Materiel 1" },
            new Materiel { Id = 2, Date = date, Description = "Materiel 2" }
        }.AsQueryable();

        _materielServiceMock.Setup(service => service.GetMaterielsByDate(date))
            .Returns(materiels);

        // Act
        var result = _materielQuery.GetMaterielsByDate(date, _materielServiceMock.Object);

        // Assert
        Assert.NotNull(result);
        Assert.Equal(2, result.Count());
        Assert.All(result, m => Assert.Equal(date, m.Date));
        _materielServiceMock.Verify(service => service.GetMaterielsByDate(date), Times.Once);
    }

    [Fact]
    public void GetMaterielsBySerie_ShouldReturnMaterielsForGivenSerie()
    {
        // Arrange
        var serie = "A123";
        var materiels = new[]
        {
            new Materiel { Id = 1, Serie = serie, Description = "Materiel 1" },
            new Materiel { Id = 2, Serie = serie, Description = "Materiel 2" }
        }.AsQueryable();

        _materielServiceMock.Setup(service => service.GetMaterielsBySerie(serie))
            .Returns(materiels);

        // Act
        var result = _materielQuery.GetMaterielsBySerie(serie, _materielServiceMock.Object);

        // Assert
        Assert.NotNull(result);
        Assert.Equal(2, result.Count());
        Assert.All(result, m => Assert.Equal(serie, m.Serie));
        _materielServiceMock.Verify(service => service.GetMaterielsBySerie(serie), Times.Once);
    }

    [Fact]
    public void GetAvailableMateriels_ShouldReturnAvailableMateriels()
    {
        // Arrange
        var materiels = new[]
        {
            new Materiel { Id = 1, Available = true, Description = "Available Materiel" },
            new Materiel { Id = 2, Available = true, Description = "Available Materiel 2" }
        }.AsQueryable();

        _materielServiceMock.Setup(service => service.GetAvailableMateriels())
            .Returns(materiels);

        // Act
        var result = _materielQuery.GetAvailableMateriels(_materielServiceMock.Object);

        // Assert
        Assert.NotNull(result);
        Assert.Equal(2, result.Count());
        Assert.All(result, m => Assert.True(m.Available));
        _materielServiceMock.Verify(service => service.GetAvailableMateriels(), Times.Once);
    }
}