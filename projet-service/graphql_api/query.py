# graphql_api/query.py
import graphene
from graphene_sqlalchemy import SQLAlchemyObjectType
from models.biologist import Biologist
from models.materiel import Materiel
from models.projet import Projet  # Ensure you are importing from models.projet.py

class BiologistType(SQLAlchemyObjectType):
    class Meta:
        model = Biologist
        # Additional settings if needed

class MaterielType(SQLAlchemyObjectType):
    class Meta:
        model = Materiel
        # Additional settings if needed

class ProjetType(SQLAlchemyObjectType):
    class Meta:
        model = Projet
        # Additional settings can go here, like specifying `exclude_fields` if needed.
    # Add relations to Biologist and Materiel types
    biologist = graphene.Field(BiologistType)
    material = graphene.Field(MaterielType)

    # Resolver method for these relationships
    def resolve_biologist(self, info):
        return self.biologist  # This will resolve the related biologist data from the db

    def resolve_material(self, info):
        return self.material  # This will resolve the related material data from the db


class Query(graphene.ObjectType):
    all_projets = graphene.List(ProjetType)
    projet_by_id = graphene.Field(ProjetType, id=graphene.Int(required=True))
    projets_by_date = graphene.List(ProjetType, date=graphene.String(required=True))
    projets_by_titre = graphene.List(ProjetType, titre=graphene.String(required=True))
    projets_by_biologist = graphene.List(ProjetType, biologist_id=graphene.Int(required=True))
    projets_by_materiel = graphene.List(ProjetType, material_id=graphene.Int(required=True))

    def resolve_all_projets(self, info):
        return Projet.query.all()

    def resolve_projet_by_id(self, info, id):
        return Projet.query.get(id)

    def resolve_projets_by_date(self, info, date):
        return Projet.query.filter(Projet.date == date).all()

    def resolve_projets_by_titre(self, info, titre):
        return Projet.query.filter(Projet.titre.like(f'%{titre}%')).all()

    def resolve_projets_by_biologist(self, info, biologist_id):
        return Projet.query.filter(Projet.biologist_id == biologist_id).all()

    def resolve_projets_by_materiel(self, info, material_id):
        return Projet.query.filter(Projet.material_id == material_id).all()