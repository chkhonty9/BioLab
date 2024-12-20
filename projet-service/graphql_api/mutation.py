# graphql_api/mutation.py
import graphene
from models import db, Projet
import graphene
from models import db, Projet
from graphql_api.query import ProjetType  # Import ProjetType from query.py

class ProjetInputType(graphene.InputObjectType):
    titre = graphene.String()
    date = graphene.String()
    hum_max = graphene.Float()
    temp_max = graphene.Float()
    pompe_st = graphene.Int()
    biologist_id = graphene.Int()
    material_id = graphene.Int()

class CreateProjet(graphene.Mutation):
    class Arguments:
        projet_data = ProjetInputType(required=True)

    success = graphene.Boolean()
    projet = graphene.Field(ProjetType)  # Use ProjetType as the output type

    def mutate(self, info, projet_data):
        projet = Projet(**projet_data)
        db.session.add(projet)
        db.session.commit()
        return CreateProjet(success=True, projet=projet)

class UpdateProjet(graphene.Mutation):
    class Arguments:
        id = graphene.Int(required=True)
        projet_data = ProjetInputType(required=True)

    success = graphene.Boolean()

    def mutate(self, info, id, projet_data):
        projet = Projet.query.get(id)
        if projet:
            for key, value in projet_data.items():
                setattr(projet, key, value)
            db.session.commit()
            return UpdateProjet(success=True)
        return UpdateProjet(success=False)

class DeleteProjet(graphene.Mutation):
    class Arguments:
        id = graphene.Int(required=True)

    success = graphene.Boolean()

    def mutate(self, info, id):
        projet = Projet.query.get(id)
        if projet:
            db.session.delete(projet)
            db.session.commit()
            return DeleteProjet(success=True)
        return DeleteProjet(success=False)
