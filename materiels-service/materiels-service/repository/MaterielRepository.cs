using System.Linq;
using materiels_service.data;
using materiels_service.entity;

namespace materiels_service.repository;

public class MaterielRepository(ApplicationDbContext context)
{
    public IQueryable<Materiel?> GetAllMateriels() => context.Materiels;

    public Materiel? GetMaterielById(int id) => context.Materiels.FirstOrDefault(m => m.Id == id);

    public IQueryable<Materiel?> GetMaterielsByDate(DateTime date) => 
        context.Materiels.Where(m => m.Date == date);

    public IQueryable<Materiel?> GetMaterielsBySerie(string serie) => 
        context.Materiels.Where(m => m.Serie.Contains(serie));

    public IQueryable<Materiel> GetAvailableMateriels()
    {
        return context.Materiels.Where(m => m.Available == true);
    }
    
    public void AddMateriel(Materiel materiel)
    {
        context.Materiels.Add(materiel);
        context.SaveChanges();
    }

    public void UpdateMateriel(Materiel materiel)
    {
        context.Materiels.Update(materiel);
        context.SaveChanges();
    }
}