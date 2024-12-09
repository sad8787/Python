import mysql.connector


# Configuración de la conexión
config = {
    'host': 'localhost',  # Dirección del servidor MySQL
    'user': 'sadiel',  # Tu usuario de MySQL
    'password': 'sadiel',  # Tu contraseña de MySQL
    'database': 'logindb'  # Base de datos a la que quieres conectar
}

try:
    # Establecer conexión
    conexion = mysql.connector.connect(**config)
    if conexion.is_connected():
        print("Conexión exitosa a la base de datos")

        # Crear un cursor para ejecutar consultas
        cursor = conexion.cursor()
        cursor.execute("SELECT DATABASE();")
        database_name = cursor.fetchone()
        print(f"Conectado a la base de datos: {database_name[0]}")
        
        #cursor.execute("""CREATE TABLE IF NOT EXISTS users ( id INT AUTO_INCREMENT PRIMARY KEY,nombre VARCHAR(100) NOT NULL,
        #        email VARCHAR(150) UNIQUE, isadmin TINYINT(1) NOT NULL ) """)
        print("Tabla creada exitosamente")

        if conexion.is_connected():
                cursor.close()
                conexion.close()
                print("Conexión cerrada")
except mysql.connector.Error as e:
    print(f"Error al conectar a MySQL: {e}")


       