using JetBrains.Annotations;
using materiels_service.controllers;
using materiels_service.data;
using materiels_service.entity;
using materiels_service.service;
using Microsoft.AspNetCore.Mvc;
using Moq;

namespace MaterielsService.Tests.controllers;

[TestSubject(typeof(MaterielController))]
public class MaterielControllerTest
{
    private readonly Mock<IMaterielService> _mockMaterielService;
    private readonly MaterielController _controller;

    public MaterielControllerTest()
    {
        _mockMaterielService = new Mock<IMaterielService>();
        _controller = new MaterielController(_mockMaterielService.Object);
    }

    [Fact]
    public void GetMateriels_ShouldReturnOk_WhenMaterielsExist()
    {
        // Arrange
        var materiels = new List<Materiel>
        {
            new Materiel { Id = 1, Description = "Materiel 1", Available = true, Date = DateTime.Now, Serie = "A123" },
            new Materiel { Id = 2, Description = "Materiel 2", Available = false, Date = DateTime.Now, Serie = "B456" }
        };
        _mockMaterielService.Setup(service => service.GetAllMateriels()).Returns(materiels.AsQueryable());

        // Act
        var result = _controller.GetMateriels();

        // Assert
        var actionResult = Assert.IsType<OkObjectResult>(result);
        var returnedMateriels = Assert.IsType<List<Materiel>>(actionResult.Value);
        Assert.Equal(2, returnedMateriels.Count);
    }

    [Fact]
    public void GetMateriels_ShouldReturnNotFound_WhenNoMaterielsExist()
    {
        // Arrange
        _mockMaterielService.Setup(service => service.GetAllMateriels())
            .Returns(Enumerable.Empty<Materiel>().AsQueryable());

        // Act
        var result = _controller.GetMateriels();

        // Assert
        var actionResult = Assert.IsType<NotFoundObjectResult>(result);
        Assert.Equal("No materiels found.", actionResult.Value);
    }

    [Fact]
    public void GetMaterielById_ShouldReturnOk_WhenMaterielExists()
    {
        // Arrange
        var materiel = new Materiel
            { Id = 1, Description = "Materiel 1", Available = true, Date = DateTime.Now, Serie = "A123" };
        _mockMaterielService.Setup(service => service.GetMaterielById(1)).Returns(materiel);

        // Act
        var result = _controller.GetMaterielById(1);

        // Assert
        var actionResult = Assert.IsType<OkObjectResult>(result);
        var returnedMateriel = Assert.IsType<Materiel>(actionResult.Value);
        Assert.Equal(1, returnedMateriel.Id);
    }

    [Fact]
    public void GetMaterielById_ShouldReturnNotFound_WhenMaterielDoesNotExist()
    {
        // Arrange
        _mockMaterielService.Setup(service => service.GetMaterielById(It.IsAny<int>())).Returns((Materiel)null);

        // Act
        var result = _controller.GetMaterielById(999);

        // Assert
        var actionResult = Assert.IsType<NotFoundObjectResult>(result);
        Assert.Equal("Materiel with ID 999 not found.", actionResult.Value);
    }

    [Fact]
    public void GetAvailableMateriels_ShouldReturnOk_WhenAvailableMaterielsExist()
    {
        // Arrange
        var availableMateriels = new List<Materiel>
        {
            new Materiel
                { Id = 1, Description = "Available Materiel", Available = true, Date = DateTime.Now, Serie = "C789" }
        };
        _mockMaterielService.Setup(service => service.GetAvailableMateriels())
            .Returns(availableMateriels.AsQueryable());

        // Act
        var result = _controller.GetAvailableMateriels();

        // Assert
        var actionResult = Assert.IsType<OkObjectResult>(result);
        var returnedMateriels = Assert.IsType<List<Materiel>>(actionResult.Value);
        Assert.Equal(1, returnedMateriels.Count);
    }

    [Fact]
    public void GetMaterielsByDate_ShouldReturnOk_WhenMaterielsFoundForDate()
    {
        // Arrange
        var date = DateTime.Now.Date;
        var materiels = new List<Materiel>
        {
            new Materiel { Id = 1, Description = "Materiel by Date", Available = true, Date = date, Serie = "D123" }
        };
        _mockMaterielService.Setup(service => service.GetMaterielsByDate(date)).Returns(materiels.AsQueryable());

        // Act
        var result = _controller.GetMaterielsByDate(date);

        // Assert
        var actionResult = Assert.IsType<OkObjectResult>(result);
        var returnedMateriels = Assert.IsType<List<Materiel>>(actionResult.Value);
        Assert.Equal(1, returnedMateriels.Count);
    }

    [Fact]
    public void AddMateriel_ShouldReturnCreatedAtAction_WhenMaterielIsAdded()
    {
        // Arrange
        var newMateriel = new Materiel
            { Description = "New Materiel", Available = true, Date = DateTime.Now, Serie = "E456" };
        _mockMaterielService.Setup(service => service.AddMateriel(newMateriel)).Returns(newMateriel);

        // Act
        var result = _controller.AddMateriel(newMateriel);

        // Assert
        var actionResult = Assert.IsType<CreatedAtActionResult>(result);
        var createdMateriel = Assert.IsType<Materiel>(actionResult.Value);
        Assert.Equal(newMateriel.Description, createdMateriel.Description);
    }

    [Fact]
    public void UpdateMateriel_ShouldReturnOk_WhenMaterielIsUpdated()
    {
        // Arrange
        var existingMateriel = new Materiel
            { Id = 1, Description = "Old Materiel", Available = true, Date = DateTime.Now, Serie = "F123" };
        var updatedMateriel = new Materiel
            { Id = 1, Description = "Updated Materiel", Available = true, Date = DateTime.Now, Serie = "F123" };

        _mockMaterielService.Setup(service => service.GetMaterielById(1)).Returns(existingMateriel);
        _mockMaterielService.Setup(service => service.UpdateMateriel(updatedMateriel)).Returns(updatedMateriel);

        // Act
        var result = _controller.UpdateMateriel(1, updatedMateriel);

        // Assert
        var actionResult = Assert.IsType<OkObjectResult>(result);
        var updated = Assert.IsType<Materiel>(actionResult.Value);
        Assert.Equal("Updated Materiel", updated.Description);
    }
}