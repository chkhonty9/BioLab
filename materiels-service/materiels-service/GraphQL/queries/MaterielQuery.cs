using materiels_service.entity;
using materiels_service.service;

namespace materiels_service.GraphQL.queries;

public class MaterielQuery
{
    public IQueryable<Materiel?> GetAllMateriels([Service] MaterielService service) => service.GetAllMateriels();

    public Materiel? GetMaterielById(int id, [Service] MaterielService service) => service.GetMaterielById(id);

    public IQueryable<Materiel?> GetMaterielsByDate(DateTime date, [Service] MaterielService service) => service.GetMaterielsByDate(date);

    public IQueryable<Materiel?> GetMaterielsBySerie(string serie, [Service] MaterielService service) => service.GetMaterielsBySerie(serie);

    public IQueryable<Materiel?> GetAvailableMateriels([Service] MaterielService service) => service.GetAvailableMateriels();
}