using materiels_service.entity;
using materiels_service.service;

namespace materiels_service.GraphQL.queries;

public class MaterielQuery
{
    public IQueryable<Materiel?> GetAllMateriels([Service] IMaterielService service) => service.GetAllMateriels();

    public Materiel? GetMaterielById(int id, [Service] IMaterielService service) => service.GetMaterielById(id);

    public IQueryable<Materiel?> GetMaterielsByDate(DateTime date, [Service] IMaterielService service) => service.GetMaterielsByDate(date);

    public IQueryable<Materiel?> GetMaterielsBySerie(string serie, [Service] IMaterielService service) => service.GetMaterielsBySerie(serie);

    public IQueryable<Materiel?> GetAvailableMateriels([Service] IMaterielService service) => service.GetAvailableMateriels();
}