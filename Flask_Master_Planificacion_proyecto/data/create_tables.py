import mysql.connector
from mysql.connector import Error


class Create_tables:    
    
    def crear_base_datos_si_no_existe(host1,port1,user1,password1,database1):
        
        print(f'''crear_base_datos_si_no_existe {host1} {port1} {user1} {password1} {database1}''')        
        # Conectar al servidor MySQL
        
      
        try:          
            connection = mysql.connector.connect(
                host = host1,
                port = port1,
                user = user1, # Cambiar al usuario de tu base de datos
                password = password1,  # Cambiar a la contraseña de tu base de datos
                database = database1          
            )
            print("connection")
            if connection.is_connected():
                cursor = connection.cursor()
                # Crear la base de datos si no existe
                cursor.execute(f"CREATE DATABASE IF NOT EXISTS {database1}")
                print(f"Base de datos '{database1}' creada o ya existe.")
    
        except Error as e:
            print("Error al conectar a MySQL o crear la base de datos:", e)
    
        finally:
            if connection.is_connected():
                cursor.close()
                connection.close()
            print("""Crea una base de datos si no existe.""")

# Llamar a la función para crear la base de datos

