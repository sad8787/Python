

from werkzeug.security import generate_password_hash, check_password_hash
import mysql.connector
from mysql.connector import Error

import os
import configparser
dir_path = os.path.dirname(os.path.realpath(__file__))
config = configparser.ConfigParser()
config.read(f'{dir_path}//..//data//config.ini')


class User():    
    id
    def __init__(self, name, email, password, isadmin=False):
        self.id 
        self.name = name
        self.email = email
        self.password = password
        self.isadmin = isadmin=False
        



    def check_password(self, password):       
        return self.password == password

    def __repr__(self):
        return f"<Usuario {self.name} - {self.email}>"
