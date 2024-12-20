# graphql/schema.py
import graphene
from .query import Query
from .mutation import CreateProjet, UpdateProjet, DeleteProjet

class Mutation(graphene.ObjectType):
    create_projet = CreateProjet.Field()
    update_projet = UpdateProjet.Field()
    delete_projet = DeleteProjet.Field()

schema = graphene.Schema(query=Query, mutation=Mutation)
