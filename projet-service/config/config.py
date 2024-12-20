# config.py
import os

MYSQL_CONFIG = {
    'host': os.getenv('MYSQL_HOST', '178.16.129.132'),
    'user': os.getenv('MYSQL_USER', 'root'),
    'password': os.getenv('MYSQL_PASSWORD', ''),
    'database': os.getenv('MYSQL_DATABASE', 'my_db'),
    'port': int(os.getenv('MYSQL_PORT', 3307))
}
