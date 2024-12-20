# models/projet.py
from . import db
from sqlalchemy import Column, Integer, String, Float, Date, ForeignKey

class Projet(db.Model):
    __tablename__ = 'projet'
    id = Column(Integer, primary_key=True, autoincrement=True)
    titre = Column(String(255), nullable=False)
    date = Column(Date, nullable=False)
    hum_max = Column(Float, nullable=False)
    temp_max = Column(Float, nullable=False)
    pompe_st = Column(Integer, nullable=False)
    
    # # Foreign Keys
    # biologist_id = Column(Integer, ForeignKey('biologist.id'), nullable=False)
    # material_id = Column(Integer, ForeignKey('materiel.id'), nullable=False)

    # Foreign Keys
    biologist_id = db.Column(db.Integer, db.ForeignKey('biologist.id'), nullable=False)
    material_id = db.Column(db.Integer, db.ForeignKey('materiel.id'), nullable=False)


    # Relationships
    biologist = db.relationship('Biologist', back_populates='projects')
    material = db.relationship('Materiel', back_populates='projects')
