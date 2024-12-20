# models/biologist.py
from . import db
from sqlalchemy import Column, Integer, String, ForeignKey
from .projet import Projet  # Avoid circular import issue

class Biologist(db.Model):
    __tablename__ = 'biologist'
    id = Column(Integer, ForeignKey('person.id'), primary_key=True)
    label = Column(String(255), nullable=True)
    admin_id = Column(Integer, ForeignKey('admin.id'), nullable=True)

    # Relationship with Person (inherits)
    person = db.relationship('Person', back_populates='biologist')

    # Relationship to Admin
    admin = db.relationship('Admin', backref='biologists')

    # Relationship to projects
    projects = db.relationship('Projet', back_populates='biologist')
