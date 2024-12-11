using materiels_service.entity;

namespace materiels_service.repository;

public interface IMaterielRepository
{
    IQueryable<Materiel> GetAllMateriels();
    Materiel GetMaterielById(int id);
    IQueryable<Materiel> GetMaterielsByDate(DateTime date);
    IQueryable<Materiel> GetMaterielsBySerie(string serie);
    IQueryable<Materiel> GetAvailableMateriels();
    void  AddMateriel(Materiel materiel);
    void  UpdateMateriel(Materiel materiel);
}