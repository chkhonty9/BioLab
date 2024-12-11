using materiels_service.entity;
using materiels_service.repository;

namespace materiels_service.service;

public class MaterielService(IMaterielRepository repository) : IMaterielService
{
    public IQueryable<Materiel?> GetAllMateriels() => repository.GetAllMateriels();

    public Materiel? GetMaterielById(int id) => repository.GetMaterielById(id);

    public IQueryable<Materiel?> GetMaterielsByDate(DateTime date) => repository.GetMaterielsByDate(date);

    public IQueryable<Materiel?> GetMaterielsBySerie(string serie) => repository.GetMaterielsBySerie(serie);

    public IQueryable<Materiel?> GetAvailableMateriels() => repository.GetAvailableMateriels();

    public Materiel AddMateriel(Materiel materiel)
    {
        repository.AddMateriel(materiel);
        return materiel;
    }

    public Materiel UpdateMateriel(Materiel materiel)
    {
        repository.UpdateMateriel(materiel);
        return materiel;
    }
}