#import mysql.connector
#from mysql.connector import Error

import psycopg2
import os
import configparser
dir_path = os.path.dirname(os.path.realpath(__file__))
config = configparser.ConfigParser()
config.read(f'{dir_path}//..//data//config.ini')

class DbSql:
    def Get_Connection():              
        connection = psycopg2.connect(
                host=config['bd']['host'],
                port=config['bd']['port'],
                user = config['bd']['user'], # Cambiar al usuario de tu base de datos
                password = config['bd']['password'],  # Cambiar a la contraseña de tu base de datos
                database=config['bd']['database']  # Cambiar al nombre de tu base de datos
            )       
        return connection


    def execute_query(query, params=None):
        """Establece conexión, ejecuta una consulta SQL y cierra la conexión."""
        try:
            # Conectar a la base de datos  test:test@localhost:3306/proyectmastervigo
            connection = mysql.connector.connect(
            user = config['bd']['user'],
            password = config['bd']['password'],
            host=config['bd']['host'],
            port=config['bd']['port'],
            database=config['bd']['database']
            )
            
            if connection.is_connected():
                print("Conexión exitosa a la base de datos")
                
                # Crear un cursor para ejecutar la consulta
                cursor = connection.cursor(dictionary=True)
                
                # Ejecutar la consulta
                cursor.execute(query, params)
                
                # Si la consulta es SELECT, devolver los resultados
                if query.strip().lower().startswith("select"):
                    results = cursor.fetchall()
                    return results
                
                # Confirmar cambios si no es SELECT (INSERT, UPDATE, DELETE)
                connection.commit()
                print("Consulta ejecutada exitosamente")
        
        except Error as e:
            print(f"Error al conectar o ejecutar la consulta: {e}")
        
        finally:
            # Cerrar la conexión
            if connection.is_connected():
                cursor.close()
                connection.close()
                print("Conexión cerrada correctamente")