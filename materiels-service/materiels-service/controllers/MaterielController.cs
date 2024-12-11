using System.Linq;
using Microsoft.AspNetCore.Mvc;
using materiels_service.data;
using materiels_service.entity;

namespace materiels_service.controllers;

[ApiController]
[Route("api/[controller]")]
public class MaterielController(ApplicationDbContext context) : ControllerBase
{
    // GET: api/materiel
    [HttpGet]
    public IActionResult GetMateriels()
    {
        var materiels = context.Materiels.ToList();
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
        var materiel = context.Materiels.FirstOrDefault(m => m.Id == id);
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
        var availableMateriels = context.Materiels.Where(m => m.Available).ToList();
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
        var materielsByDate = context.Materiels.Where(m => m.Date.Date == date.Date).ToList();
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
        var materielsBySerie = context.Materiels.Where(m => m.Serie == serie).ToList();
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

        context.Materiels.Add(materiel);
        context.SaveChanges();

        return CreatedAtAction(nameof(GetMaterielById), new { id = materiel.Id }, materiel);
    }

    // PUT: api/materiel/{id}
    [HttpPut("{id}")]
    public IActionResult UpdateMateriel(int id, [FromBody] Materiel updatedMateriel)
    {
        var existingMateriel = context.Materiels.FirstOrDefault(m => m.Id == id);
        if (existingMateriel == null)
        {
            return NotFound($"Materiel with ID {id} not found.");
        }

        existingMateriel.Description = updatedMateriel.Description;
        existingMateriel.Date = updatedMateriel.Date;
        existingMateriel.Available = updatedMateriel.Available;
        existingMateriel.Serie = updatedMateriel.Serie;

        context.SaveChanges();
        return Ok(existingMateriel);
    }

    // DELETE: api/materiel/{id}
    [HttpDelete("{id}")]
    public IActionResult DeleteMateriel(int id)
    {
        var materiel = context.Materiels.FirstOrDefault(m => m.Id == id);
        if (materiel == null)
        {
            return NotFound($"Materiel with ID {id} not found.");
        }

        context.Materiels.Remove(materiel);
        context.SaveChanges();
        return NoContent();
    }
}
