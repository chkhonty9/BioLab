using JetBrains.Annotations;
using materiels_service.entity;
using materiels_service.GraphQL.mutations;
using materiels_service.service;
using Moq;

namespace MaterielsService.Tests.GraphQL.mutations;

[TestSubject(typeof(MaterielMutation))]
public class MaterielMutationTest
{
    private readonly Mock<IMaterielService> _materielServiceMock;
    private readonly MaterielMutation _materielMutation;

    public MaterielMutationTest()
    {
        _materielServiceMock = new Mock<IMaterielService>();
        _materielMutation = new MaterielMutation();
    }

    [Fact]
    public void AddMateriel_ShouldReturnAddedMateriel()
    {
        // Arrange
        var inputMateriel = new Materiel
        {
            Id = 1,
            Description = "New Materiel",
            Available = true,
            Date = DateTime.Now,
            Serie = "A123"
        };

        // Set up the mock to return the input materiel when AddMateriel is called
        _materielServiceMock.Setup(service => service.AddMateriel(It.IsAny<Materiel>()))
            .Returns(inputMateriel);

        // Act
        var result = _materielMutation.AddMateriel(inputMateriel, _materielServiceMock.Object);

        // Assert
        Assert.NotNull(result);
        Assert.Equal(inputMateriel.Description, result.Description);
        Assert.Equal(inputMateriel.Serie, result.Serie);
        _materielServiceMock.Verify(service => service.AddMateriel(It.IsAny<Materiel>()), Times.Once);
    }

    [Fact]
    public void UpdateMateriel_ShouldReturnUpdatedMateriel()
    {
        // Arrange
        var inputMateriel = new Materiel
        {
            Id = 1,
            Description = "Updated Materiel",
            Available = true,
            Date = DateTime.Now,
            Serie = "B456"
        };

        // Set up the mock to return the updated materiel when UpdateMateriel is called
        _materielServiceMock.Setup(service => service.UpdateMateriel(It.IsAny<Materiel>()))
            .Returns(inputMateriel);

        // Act
        var result = _materielMutation.UpdateMateriel(1, inputMateriel, _materielServiceMock.Object);

        // Assert
        Assert.NotNull(result);
        Assert.Equal(inputMateriel.Description, result.Description);
        Assert.Equal(inputMateriel.Serie, result.Serie);
        Assert.Equal(1, result.Id); // Ensure the ID remains unchanged
        _materielServiceMock.Verify(service => service.UpdateMateriel(It.IsAny<Materiel>()), Times.Once);
    }
}