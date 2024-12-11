using System.Linq;
using Microsoft.AspNetCore.Mvc;
using materiels_service.entity;
using materiels_service.service;

namespace materiels_service.controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class MaterielController : ControllerBase
    {
        private readonly IMaterielService _materielService;

        public MaterielController(IMaterielService materielService)
        {
            _materielService = materielService;
        }

        // GET: api/materiel
        [HttpGet]
        public IActionResult GetMateriels()
        {
            var materiels = _materielService.GetAllMateriels().ToList();
            if (!materiels.Any())
            {
                return NotFound("No materiels found.");
            }

            return Ok(materiels);
        }

        // GET: api/materiel/{id}
        [HttpGet("{id}")]
        public IActionResult GetMaterielById(int id)
        {
            var materiel = _materielService.GetMaterielById(id);
            if (materiel == null)
            {
                return NotFound($"Materiel with ID {id} not found.");
            }

            return Ok(materiel);
        }

        // GET: api/materiel/available
        [HttpGet("available")]
        public IActionResult GetAvailableMateriels()
        {
            var availableMateriels = _materielService.GetAvailableMateriels().ToList();
            if (!availableMateriels.Any())
            {
                return NotFound("No available materiels found.");
            }

            return Ok(availableMateriels);
        }

        // GET: api/materiel/by-date/{date}
        [HttpGet("by-date/{date}")]
        public IActionResult GetMaterielsByDate(DateTime date)
        {
            var materielsByDate = _materielService.GetMaterielsByDate(date).ToList();
            if (!materielsByDate.Any())
            {
                return NotFound($"No materiels found for date {date:yyyy-MM-dd}.");
            }

            return Ok(materielsByDate);
        }

        // GET: api/materiel/by-serie/{serie}
        [HttpGet("by-serie/{serie}")]
        public IActionResult GetMaterielsBySerie(string serie)
        {
            var materielsBySerie = _materielService.GetMaterielsBySerie(serie).ToList();
            if (!materielsBySerie.Any())
            {
                return NotFound($"No materiels found with serie '{serie}'.");
            }

            return Ok(materielsBySerie);
        }

        // POST: api/materiel
        [HttpPost]
        public IActionResult AddMateriel([FromBody] Materiel materiel)
        {
            if (materiel == null)
            {
                return BadRequest("Materiel cannot be null.");
            }

            var addedMateriel = _materielService.AddMateriel(materiel);

            return CreatedAtAction(nameof(GetMaterielById), new { id = addedMateriel.Id }, addedMateriel);
        }

        // PUT: api/materiel/{id}
        [HttpPut("{id}")]
        public IActionResult UpdateMateriel(int id, [FromBody] Materiel updatedMateriel)
        {
            var existingMateriel = _materielService.GetMaterielById(id);
            if (existingMateriel == null)
            {
                return NotFound($"Materiel with ID {id} not found.");
            }

            updatedMateriel.Id = existingMateriel.Id; // Ensure the ID stays the same
            var updated = _materielService.UpdateMateriel(updatedMateriel);

            return Ok(updated);
        }
    }
}