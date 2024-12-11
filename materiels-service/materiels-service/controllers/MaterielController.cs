using System.Linq;
using Microsoft.AspNetCore.Mvc;
using materiels_service.data;
using materiels_service.entity;


namespace materiels_service.controllers;

[ApiController]
[Route("api/materiels")]
public class MaterielController : ControllerBase
{
    private readonly ApplicationDbContext _context;

    public MaterielController(ApplicationDbContext context)
    {
        _context = context;
    }

    [HttpGet]
    public IActionResult GetMateriels()
    {
        return Ok(_context.Materiels.ToList());
    }

    [HttpPost]
    public IActionResult AddMateriel(Materiel? materiel)
    {
        _context.Materiels.Add(materiel);
        _context.SaveChanges();
        return CreatedAtAction(nameof(GetMateriels), new { id = materiel.Id }, materiel);
    }
}