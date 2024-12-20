# models/__init__.py
from flask_sqlalchemy import SQLAlchemy

db = SQLAlchemy()

# Import models after initializing db to avoid circular import issues
from .person import Person
from .admin import Admin
from .biologist import Biologist
from .projet import Projet
from .materiel import Materiel
