# models/admin.py
from . import db
from sqlalchemy import Column, Integer, String, ForeignKey

class Admin(db.Model):
    __tablename__ = 'admin'
    id = Column(Integer, ForeignKey('person.id'), primary_key=True)
    label = Column(String(255), nullable=True)

    # Link back to Person
    person = db.relationship('Person', back_populates='admin')
