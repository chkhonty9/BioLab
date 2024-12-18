using HotChocolate.Types;
using materiels_service.entity;
namespace materiels_service.GraphQL.types;

public class MaterielType : ObjectType<Materiel>
{
    protected override void Configure(IObjectTypeDescriptor<Materiel> descriptor)
    {
        descriptor.Field(m => m.Id).Type<IntType>();
        descriptor.Field(m => m.Description).Type<NonNullType<StringType>>();
        descriptor.Field(m => m.Date).Type<NonNullType<DateTimeType>>();
        descriptor.Field(m => m.Available).Type<NonNullType<BooleanType>>();
        descriptor.Field(m => m.Serie).Type<NonNullType<StringType>>();
    }
}