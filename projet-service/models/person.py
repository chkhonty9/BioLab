# models/person.py
from . import db
from sqlalchemy import Column, Integer, String

class Person(db.Model):
    __tablename__ = 'person'
    id = Column(Integer, primary_key=True, autoincrement=True)
    email = Column(String(255), unique=True, nullable=True)
    first_name = Column(String(255), nullable=True)
    last_name = Column(String(255), nullable=True)
    password = Column(String(255), nullable=True)
    role = Column(String(255), nullable=True)

    # Relationships
    admin = db.relationship('Admin', back_populates='person', uselist=False)
    biologist = db.relationship('Biologist', back_populates='person', uselist=False)
