using materiels_service.entity;

namespace materiels_service.service;

public interface IMaterielService
{
    IQueryable<Materiel?> GetAllMateriels();
    Materiel? GetMaterielById(int id);
    IQueryable<Materiel?> GetMaterielsByDate(DateTime date);
    IQueryable<Materiel?> GetMaterielsBySerie(string serie);
    IQueryable<Materiel?> GetAvailableMateriels();
    Materiel AddMateriel(Materiel materiel);
    Materiel UpdateMateriel(Materiel materiel);
    public void DeleteMateriel(int id);
}