using JetBrains.Annotations;
using materiels_service.entity;
using materiels_service.repository;
using materiels_service.service;
using Moq;

namespace MaterielsService.Tests.service;

[TestSubject(typeof(MaterielService))]
public class MaterielServiceTest
{
   private readonly Mock<IMaterielRepository> _mockRepository;
        private readonly MaterielService _materielService;

        public MaterielServiceTest()
        {
            _mockRepository = new Mock<IMaterielRepository>();
            _materielService = new MaterielService(_mockRepository.Object);
        }

        [Fact]
        public void GetAllMateriels_ShouldReturnAllMateriels()
        {
            // Arrange
            var materiels = new[] { new Materiel { Id = 1 }, new Materiel { Id = 2 } }.AsQueryable();
            _mockRepository.Setup(r => r.GetAllMateriels()).Returns(materiels);

            // Act
            var result = _materielService.GetAllMateriels();

            // Assert
            Assert.Equal(2, result.Count());
            _mockRepository.Verify(r => r.GetAllMateriels(), Times.Once);
        }

        [Fact]
        public void GetMaterielById_ShouldReturnCorrectMateriel()
        {
            // Arrange
            var materiel = new Materiel { Id = 1 };
            _mockRepository.Setup(r => r.GetMaterielById(1)).Returns(materiel);

            // Act
            var result = _materielService.GetMaterielById(1);

            // Assert
            Assert.Equal(1, result?.Id);
            _mockRepository.Verify(r => r.GetMaterielById(1), Times.Once);
        }

        [Fact]
        public void GetMaterielsByDate_ShouldReturnMaterielsByDate()
        {
            // Arrange
            var date = new DateTime(2023, 12, 1);
            var materiels = new[] { new Materiel { Id = 1, Date = date }, new Materiel { Id = 2, Date = date } }.AsQueryable();
            _mockRepository.Setup(r => r.GetMaterielsByDate(date)).Returns(materiels);

            // Act
            var result = _materielService.GetMaterielsByDate(date);

            // Assert
            Assert.Equal(2, result.Count());
            _mockRepository.Verify(r => r.GetMaterielsByDate(date), Times.Once);
        }

        [Fact]
        public void GetMaterielsBySerie_ShouldReturnMaterielsBySerie()
        {
            // Arrange
            var serie = "ABC123";
            var materiels = new[] { new Materiel { Id = 1, Serie = serie }, new Materiel { Id = 2, Serie = serie } }.AsQueryable();
            _mockRepository.Setup(r => r.GetMaterielsBySerie(serie)).Returns(materiels);

            // Act
            var result = _materielService.GetMaterielsBySerie(serie);

            // Assert
            Assert.Equal(2, result.Count());
            _mockRepository.Verify(r => r.GetMaterielsBySerie(serie), Times.Once);
        }

        [Fact]
        public void GetAvailableMateriels_ShouldReturnAvailableMateriels()
        {
            // Arrange
            var materiels = new[] { new Materiel { Id = 1, Available = true }, new Materiel { Id = 2, Available = true } }.AsQueryable();
            _mockRepository.Setup(r => r.GetAvailableMateriels()).Returns(materiels);

            // Act
            var result = _materielService.GetAvailableMateriels();

            // Assert
            Assert.Equal(2, result.Count());
            _mockRepository.Verify(r => r.GetAvailableMateriels(), Times.Once);
        }

        [Fact]
        public void AddMateriel_ShouldAddMateriel()
        {
            // Arrange
            var materiel = new Materiel { Id = 1, Description = "Test Materiel" };
            _mockRepository.Setup(r => r.AddMateriel(It.IsAny<Materiel>())).Verifiable();

            // Act
            var result = _materielService.AddMateriel(materiel);

            // Assert
            Assert.Equal(materiel, result);
            _mockRepository.Verify(r => r.AddMateriel(materiel), Times.Once);
        }

        [Fact]
        public void UpdateMateriel_ShouldUpdateMateriel()
        {
            // Arrange
            var materiel = new Materiel { Id = 1, Description = "Updated Materiel" };
            _mockRepository.Setup(r => r.UpdateMateriel(It.IsAny<Materiel>())).Verifiable();

            // Act
            var result = _materielService.UpdateMateriel(materiel);

            // Assert
            Assert.Equal(materiel, result);
            _mockRepository.Verify(r => r.UpdateMateriel(materiel), Times.Once);
        }
}