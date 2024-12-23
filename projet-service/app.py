import os
from flask import Flask, json
from config.config import MYSQL_CONFIG
from models import db  # Singleton instance
from flask_graphql import GraphQLView
from graphql_api.query import Query
from py_eureka_client import eureka_client
from graphql_api.schema import Mutation
import requests
import socket
import pymysql
import graphene

pymysql.install_as_MySQLdb()

app = Flask(__name__)


# ======================
# Fetching Configuration for the Service (Dynamic)
# ======================
def fetch_config(service_name):
    config_service_url = os.getenv('CONFIG_SERVICE_URL', 'http://localhost:9999')
    config_url = f'{config_service_url}/{service_name}/default'
    try:
        response = requests.get(config_url)
        response.raise_for_status()  # Raise error for non-2xx responses
        print(f"Fetching config:", response.json())
        return response.json()
    except requests.exceptions.RequestException as e:
        print(f"Error fetching config: {e}")
        return {}


# ======================
# Eureka Service Registration
# ======================

# Read Eureka server URL from the environment or fallback to localhost
eureka_server_url = os.getenv('DISCOVERY_SERVICE_URL', 'http://localhost:8761/eureka')

# Initialize Eureka client
eureka_client.init(
    app_name="project-service",  # Register with Eureka using service name
    eureka_server=eureka_server_url,  # Use the Eureka server URL from the configuration
    instance_port=5000,
    instance_host='localhost'
)

# ======================
# Connecting data base
# ======================

app.config['SQLALCHEMY_DATABASE_URI'] = (
    f"mysql://{MYSQL_CONFIG['user']}:{MYSQL_CONFIG['password']}@"
    f"{MYSQL_CONFIG['host']}:{MYSQL_CONFIG['port']}/{MYSQL_CONFIG['database']}"
)
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False

db.init_app(app)

# ======================
# GraphQL Schema Setup
# ======================

schema = graphene.Schema(query=Query, mutation=Mutation)
app.add_url_rule('/graphql', view_func=GraphQLView.as_view('graphql', schema=schema, graphiql=True))


# ======================
# Flask App Setup
# ======================
@app.route('/health', methods=['GET'])
def health_check():
    return json.dumps({"status": "OK"}), 200


if __name__ == "__main__":
    # Fetch config for dynamic settings
    config = fetch_config('project-service')  # Fetch the service-specific configuration

    # Use the app context to create tables
    with app.app_context():
        db.create_all()  # Ensures tables are created before querying
    app.run(host='0.0.0.0', debug=True)
