# models/materiel.py
from . import db
from sqlalchemy import Column, Integer, String, Date, Float

class Materiel(db.Model):
    __tablename__ = 'materiel'
    id = Column(Integer, primary_key=True, autoincrement=True)
    description = Column(String(255), nullable=False)
    date = Column(Date, nullable=False)
    available = Column(Integer, nullable=False)
    serie = Column(String(100), nullable=False)

    # Relationship to projects
    projects = db.relationship('Projet', back_populates='material')
