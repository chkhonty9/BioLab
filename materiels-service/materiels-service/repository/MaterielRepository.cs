using System.Linq;
using materiels_service.data;
using materiels_service.entity;

namespace materiels_service.repository;

public class MaterielRepository : IMaterielRepository
{
    
    private readonly ApplicationDbContext _context;

    public MaterielRepository(ApplicationDbContext context)
    {
        _context = context;
    }
    
    public IQueryable<Materiel?> GetAllMateriels() => _context.Materiels;

    public Materiel? GetMaterielById(int id) => _context.Materiels.FirstOrDefault(m => m.Id == id);

    public IQueryable<Materiel?> GetMaterielsByDate(DateTime date) => 
        _context.Materiels.Where(m => m.Date == date);

    public IQueryable<Materiel?> GetMaterielsBySerie(string serie) => 
        _context.Materiels.Where(m => m.Serie.Contains(serie));

    public IQueryable<Materiel> GetAvailableMateriels()
    {
        return _context.Materiels.Where(m => m.Available == true);
    }
    
    public void AddMateriel(Materiel materiel)
    {
        _context.Materiels.Add(materiel);
        _context.SaveChanges();
    }

    public void UpdateMateriel(Materiel materiel)
    {
        _context.Materiels.Update(materiel);
        _context.SaveChanges();
    }
}