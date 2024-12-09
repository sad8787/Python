from data.comfiguracion import Configuracion
import mysql.connector
import json
from modelos.proyecto_modelo import Proyecto

def iniciarDB(config):
        try:
                # Establecer conexión
                conexion = mysql.connector.connect(**config)
                if conexion.is_connected():
                        print("Conexión exitosa a la base de datos")
                                # Crear un cursor para ejecutar consultas
                        cursor = conexion.cursor()                              
                        cursor.execute(f'''CREATE TABLE IF NOT EXISTS users ( id INT AUTO_INCREMENT PRIMARY KEY,name VARCHAR(100) NOT NULL,
                                password VARCHAR(150) UNIQUE, email VARCHAR(150) UNIQUE, isadmin TINYINT(1) NOT NULL,activo TINYINT(1) NOT NULL );''')
                        
                        create_proyectos=f'''CREATE TABLE IF NOT EXISTS proyectos (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        fecha DATETIME NOT NULL,
                        url VARCHAR(255),
                        descripcion TEXT,
                        activo TINYINT(1) NOT NULL                        
                        );'''                        
                        cursor.execute(create_proyectos)

                        create_convocatorias=f'''CREATE TABLE IF NOT EXISTS convocatorias (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        fecha DATETIME NOT NULL,
                        url VARCHAR(255),
                        descripcion TEXT,
                        activo TINYINT(1) NOT NULL                        
                        );'''                        
                        cursor.execute(create_convocatorias)

                        create_formacion=f''' CREATE TABLE IF NOT EXISTS formacion (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) ,
                        queHacemos TEXT,
                        equipoFormativo TEXT,
                        objetivos TEXT, 
                        resultados TEXT                                              
                        );  '''
                        cursor.execute(create_formacion)



                        create_emprendimiento=f'''  CREATE TABLE IF NOT EXISTS emprendimiento (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) ,
                        queHacemos TEXT,
                        equipoFormativo TEXT,
                        objetivos TEXT, 
                        resultados TEXT                                              
                        );    '''
                        conexion.commit()
                        print("create table users")
                        if conexion.is_connected():
                                cursor.close()
                                conexion.close()
                                print("Conexión cerrada")
        except mysql.connector.Error as e:
                print(f"Error al conectar a MySQL: {e}")


############################################### users ####################################
def userByid(id):
        return True


def loginUser(config,user_name =None,email=None,passwod=None):
        try: 
                conexion = mysql.connector.connect(**config)
                if conexion.is_connected():                                              
                        cursor = conexion.cursor()                        
                        select_user = '''SELECT id, name, email,password,isadmin,activo FROM users WHERE (name = %s or email = %s) and password = %s and activo=%s;  '''
                        values=(user_name,email,passwod,1)
                        cursor.execute(select_user ,values)                        
                        rows = cursor.fetchall()                 
                        return rows
        except mysql.connector.Error as e:
                print(f"Error al conectar a MySQL: {e}")
        return []        


def addUser(config,name, email, password, isadmin=0):     
        result =False            
        try:
                # Establecer conexión
                conexion = mysql.connector.connect(**config)
                if conexion.is_connected():                          
                        cursor = conexion.cursor()
                        user_existe=f'''SELECT * FROM users WHERE name = '{name}' or email = '{email}';   '''
                        cursor.execute(user_existe)                        
                        rows = cursor.fetchall()
                        print(rows)
                        if(len(rows) < 1):
                                print ('rows <1')
                                create_user = ''' INSERT INTO users( name, email,password, isadmin,activo) VALUES ( %s, %s, %s,%s,%s);     '''
                                values =(name,email,password,isadmin,1)
                                cursor.execute(create_user,values)
                                conexion.commit()                                  
                                result=True
                        else:
                                result = False  
                                                     
                        if conexion.is_connected():
                                cursor.close()
                                conexion.close()
                        return result        
        except mysql.connector.Error as e:
                print(f"Error al conectar a MySQL: {e}")
                return False
  

def getUserBayId(config,id):    
        row=[]
        try:    
                # Establecer conexión
                conexion = mysql.connector.connect(**config)
                if conexion.is_connected(): 
                        cursor = conexion.cursor()              

                        select_user = f'''SELECT id, name, email,password, isadmin,activo FROM users where id={id};   '''
                        cursor.execute(select_user)        
                        rows = cursor.fetchall()
                        
                        if(len(rows) < 1):
                                return row
                        row = rows[0]
                        if conexion.is_connected():
                                cursor.close()
                                conexion.close()      
        except mysql.connector.Error as e:
                print(f"Error al conectar a MySQL: {e}")
        return row


def getUsers(config):  
        rows=[]
        try:    
                # Establecer conexión
                conexion = mysql.connector.connect(**config)
                if conexion.is_connected(): 
                        cursor = conexion.cursor()           

                        select_user = f'''SELECT id, name, email,password, isadmin,activo FROM users ;   '''
                        cursor.execute(select_user)        
                        rows = cursor.fetchall()                         
                        if conexion.is_connected():
                                cursor.close()
                                conexion.close()      
        except mysql.connector.Error as e:
                print(f"Error al conectar a MySQL: {e}")
        return rows


def deleteUser(config,id):
        result =False       
        try:                   
                # Establecer conexión
                conexion = mysql.connector.connect(**config)
                if conexion.is_connected(): 
                        cursor = conexion.cursor() 
                        consulta = "DELETE FROM users WHERE id = %s"
                        cursor.execute(consulta, (id,))
                        conexion.commit()
                        result =True
                        if conexion.is_connected():
                                cursor.close()
                                conexion.close()      
        except mysql.connector.Error as e:
                print(f"Error al conectar a MySQL: {e}")
                if conexion.is_connected():
                        cursor.close()
                        conexion.close()
        return result


def updateUser(config,id,name, email,  isadmin=0,activo=1):
        result =False            
        try:
                # Establecer conexión
                conexion = mysql.connector.connect(**config)
                if conexion.is_connected():                          
                        cursor = conexion.cursor()
                        sqlupdate=f'''UPDATE users( name, email, isadmin,activo) VALUES ( %s, %s,%s,%s) where id={id};'''
                        values =(name,email,isadmin,activo)
                        cursor.execute(sqlupdate,values)
                        conexion.commit()   
                        result= True                                                
                        if conexion.is_connected():
                                cursor.close()
                                conexion.close()
                               
        except mysql.connector.Error as e:
                print(f"Error al conectar a MySQL: {e}")
        return result 


####################################### proyectos #######################################
def getProyectos(config):
        rows=[]
        try:    
                # Establecer conexión
                conexion = mysql.connector.connect(**config)
                if conexion.is_connected(): 
                        cursor = conexion.cursor() 
                        select_Proyectos = f'''SELECT id, name, fecha,url,descripcion, activo FROM proyectos ;   '''
                        cursor.execute(select_Proyectos)        
                        rows = cursor.fetchall()                         
                        if conexion.is_connected():
                                cursor.close()
                                conexion.close()      
        except mysql.connector.Error as e:
                print(f"Error al conectar a MySQL: {e}")
                if conexion.is_connected():
                        cursor.close()
                        conexion.close()
        return rows


def getProyectosById(config,id):
        rows=[]
        try:   
                # Establecer conexión
                conexion = mysql.connector.connect(**config)
                if conexion.is_connected(): 
                        cursor = conexion.cursor() 
                        select_Proyectos = f'''SELECT id, name, fecha,url,descripcion, activo FROM proyectos where id={id} ;   '''
                        cursor.execute(select_Proyectos)        
                        rows = cursor.fetchall()                         
                        if conexion.is_connected():
                                cursor.close()
                                conexion.close()      
        except mysql.connector.Error as e:
                print(f"Error al conectar a MySQL: {e}")
                if conexion.is_connected():
                        cursor.close()
                        conexion.close()
        return rows



def addProyecto(config, name, fecha,url,descripcion,activo):
        result =False  
        print(f'{name} ,{fecha} ,{url},{descripcion},{activo} ')                  
        try:
                # Establecer conexión
                conexion = mysql.connector.connect(**config)
                if conexion.is_connected():                          
                        cursor = conexion.cursor()
                        user_existe=f'''SELECT * FROM proyectos WHERE name = '{name}' ;   '''
                        cursor.execute(user_existe)                        
                        rows = cursor.fetchall()
                        print(rows)
                        if(len(rows) < 1):                                
                                create_user = ''' INSERT INTO proyectos( name, fecha,url,descripcion, activo) VALUES ( %s, %s, %s,%s,%s);     '''
                                values =(name, fecha,url,descripcion,activo)
                                cursor.execute(create_user,values)
                                conexion.commit()                                  
                                result=True
                        else:
                                result = False  
                                                     
                        if conexion.is_connected():
                                cursor.close()
                                conexion.close()
                               
        except mysql.connector.Error as e:
                print(f"Error al conectar a MySQL: {e}")
                if conexion.is_connected():
                        cursor.close()
                        conexion.close()
        return result


def updateProyecto(config,id, name, fecha,url,descripcion,activo):
        result =False                   
        try:
                # Establecer conexión
                conexion = mysql.connector.connect(**config)
                if conexion.is_connected():    
                        cursor = conexion.cursor()              
                        update_user = f''' UPDATE proyectos( name, fecha,url,descripcion, activo)
                        VALUES ( %s, %s, %s,%s,%s)
                        where id= {id}; '''
                        values =(name, fecha,url,descripcion,activo)
                        cursor.execute(update_user,values)
                        conexion.commit()                                  
                        result=True                                             
                        if conexion.is_connected():
                                cursor.close()
                                conexion.close()
                            
        except mysql.connector.Error as e:
                print(f"Error al conectar a MySQL: {e}")
                if conexion.is_connected():
                        cursor.close()
                        conexion.close()                        
        return result   


def deleteProyecto(config,id): 
        result =False       
        try:                   
                # Establecer conexión
                conexion = mysql.connector.connect(**config)
                if conexion.is_connected(): 
                        cursor = conexion.cursor() 
                        consulta = "DELETE FROM proyectos WHERE id = %s"
                        cursor.execute(consulta, (id,))
                        conexion.commit()
                        result =True
                        if conexion.is_connected():
                                cursor.close()
                                conexion.close()      
        except mysql.connector.Error as e:
                print(f"Error al conectar a MySQL: {e}")
                if conexion.is_connected():
                        cursor.close()
                        conexion.close()
        return result


########################################## convocatorias ############################3###
def getConvocatorias(config):
        rows=[]
        try:    
                # Establecer conexión
                conexion = mysql.connector.connect(**config)
                if conexion.is_connected(): 
                        cursor = conexion.cursor() 
                        select_Proyectos = f'''SELECT id, name, fecha,url,descripcion, activo FROM proyectos ;   '''
                        cursor.execute(select_Proyectos)        
                        rows = cursor.fetchall()                         
                        if conexion.is_connected():
                                cursor.close()
                                conexion.close()      
        except mysql.connector.Error as e:
                print(f"Error al conectar a MySQL: {e}")
                if conexion.is_connected():
                        cursor.close()
                        conexion.close()
        return rows


def getConvocatoriasById(config,id):
        rows=[]
        try:   
                # Establecer conexión
                conexion = mysql.connector.connect(**config)
                if conexion.is_connected(): 
                        cursor = conexion.cursor() 
                        select_Proyectos = f'''SELECT id, name, fecha,url,descripcion, activo, id_user FROM proyectos where id={id} ;   '''
                        cursor.execute(select_Proyectos)        
                        rows = cursor.fetchall()                         
                        if conexion.is_connected():
                                cursor.close()
                                conexion.close()      
        except mysql.connector.Error as e:
                print(f"Error al conectar a MySQL: {e}")
                if conexion.is_connected():
                        cursor.close()
                        conexion.close()
        return rows


def addConvocatorias(config, name, fecha,url,descripcion):
        result =False 
        activo=1           
        try:
                # Establecer conexión
                conexion = mysql.connector.connect(**config)
                if conexion.is_connected():                          
                        cursor = conexion.cursor()
                        user_existe=f'''SELECT * FROM convocatorias WHERE name = '{name}' ;   '''
                        cursor.execute(user_existe)                        
                        rows = cursor.fetchall()
                        print(rows)
                        if(len(rows) < 1):
                                print ('rows <1')
                                create_convocatorias = ''' INSERT INTO convocatorias( name, fecha,url,descripcion, activo) VALUES ( %s, %s, %s,%s,%s);     '''
                                values =(name, fecha,url,descripcion,activo)
                                cursor.execute(create_convocatorias,values)
                                conexion.commit()                                  
                                result=True
                        else:
                                result = False  
                                                     
                        if conexion.is_connected():
                                cursor.close()
                                conexion.close()
                        return result        
        except mysql.connector.Error as e:
                print(f"Error al conectar a MySQL: {e}")
                if conexion.is_connected():
                        cursor.close()
                        conexion.close()
                return False


def updateConvocatorias(config,id, name, fecha,url,descripcion, activo ):
        result =False                   
        try:
                # Establecer conexión
                conexion = mysql.connector.connect(**config)
                if conexion.is_connected():                          
                        cursor = conexion.cursor()
                        update_proyecto = f''' UPDATE proyectos( name, fecha,url,descripcion, activo)
                                  VALUES ( %s, %s, %s,%s,%s)
                                  where id= {id}; '''
                        values =(name, fecha,url,descripcion,activo)
                        cursor.execute(update_proyecto,values)
                        conexion.commit()                                  
                        result=True                                            
                        if conexion.is_connected():
                                cursor.close()
                                conexion.close()                               
        except mysql.connector.Error as e:
                print(f"Error al conectar a MySQL: {e}")
                if conexion.is_connected():
                        cursor.close()
                        conexion.close()                        
        return result

        
def deleteConvocatorias(config,id): 
        result =False       
        try:                   
                # Establecer conexión
                conexion = mysql.connector.connect(**config)
                if conexion.is_connected(): 
                        cursor = conexion.cursor() 
                        consulta = "DELETE FROM proyectos WHERE id = %s"
                        cursor.execute(consulta, (id,))
                        conexion.commit()
                        result =True
                        if conexion.is_connected():
                                cursor.close()
                                conexion.close()      
        except mysql.connector.Error as e:
                print(f"Error al conectar a MySQL: {e}")
                if conexion.is_connected():
                        cursor.close()
                        conexion.close()
        return result
