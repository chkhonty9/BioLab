using materiels_service.entity;
using materiels_service.service;

namespace materiels_service.GraphQL.mutations;

public class MaterielMutation
{
    public Materiel AddMateriel(Materiel input, [Service] MaterielService service) => service.AddMateriel(input);

    public Materiel UpdateMateriel(int id, Materiel input, [Service] MaterielService service)
    {
        input.Id = id;
        return service.UpdateMateriel(input);
    }
}