import json
import psycopg2
import os
import configparser
from data.toPosgres import dir_path
from model.user import User
from sqlalchemy import false

@staticmethod
def addUser(user):
    config = configparser.ConfigParser()
    e=dir_path()
    config.read(e) 
    #bd connection
    bd_user = config['db']['user']
    bd_password = config['db']['password']   
    bd_host=config['db']['host']
    bd_port=config['db']['port']
    bd_database=config['db']['database']
    result= "False"
    try:    
        connection = psycopg2.connect(
            user = bd_user,
            password = bd_password,
            host = bd_host,
            port = bd_port,
            database = bd_database
            )
        
        cursor = connection.cursor()          
        user_existe=f'''SELECT id, name, email,password,isAdmin FROM users WHERE name = %s or email = %s;   '''
        cursor.execute(user_existe,(user.name,user.email))
        rows=cursor.fetchall()
        if(len(rows) < 1):
            create_user = '''
            INSERT INTO public.users(
	        name, email,password, isadmin)
	        VALUES ( %s, %s, %s,%s);
            '''
            cursor.execute(create_user,(user.name,user.email,user.password,user.isAdmin))
            connection.commit()
            result= "User registered successfully!"
        else:
            result = "El usuario ya existe"
    except (Exception, psycopg2.Error) as error:
        return ("Error while connecting to PostgreSQL: ", error)
    finally:
        if connection:
            cursor.close()
            connection.close()            
    return result

def listUser():
    config = configparser.ConfigParser()
    e=dir_path()
    config.read(e) 
    #bd connection
    bd_user = config['db']['user']
    bd_password = config['db']['password']   
    bd_host=config['db']['host']
    bd_port=config['db']['port']
    bd_database=config['db']['database']
    rows=[]
    try:    
        connection = psycopg2.connect(
            user = bd_user,
            password = bd_password,
            host = bd_host,
            port = bd_port,
            database = bd_database
            )
        
        cursor = connection.cursor()              

        create_user = '''SELECT id, name, email, isadmin FROM public.users;   '''
        cursor.execute(create_user)        
        rows = cursor.fetchall()
    except (Exception, psycopg2.Error) as error:
        print("Error while connecting to PostgreSQL: ", error)
    finally:
        if connection:
            cursor.close()
            connection.close() 
        return rows

def getUserBayId(id):
    config = configparser.ConfigParser()
    e=dir_path()
    config.read(e) 
    #bd connection
    bd_user = config['db']['user']
    bd_password = config['db']['password']   
    bd_host=config['db']['host']
    bd_port=config['db']['port']
    bd_database=config['db']['database']
    rows=[]
    try:    
        connection = psycopg2.connect(
            user = bd_user,
            password = bd_password,
            host = bd_host,
            port = bd_port,
            database = bd_database
            )
        
        cursor = connection.cursor()              

        create_user = f'''SELECT id, name, email, isadmin FROM public.users where id={id};   '''
        cursor.execute(create_user)        
        rows = cursor.fetchall()[0]
    except (Exception, psycopg2.Error) as error:
        print("Error while connecting to PostgreSQL: ", error)
    finally:
        if connection:
            cursor.close()
            connection.close() 
        return rows

def getUserByNameAndEmail(name1,email1):
    config = configparser.ConfigParser()
    e=dir_path()
    config.read(e) 
    #bd connection
    bd_user = config['db']['user']
    bd_password = config['db']['password']   
    bd_host=config['db']['host']
    bd_port=config['db']['port']
    bd_database=config['db']['database']
    result= False
    try:    
        connection = psycopg2.connect(
            user = bd_user,
            password = bd_password,
            host = bd_host,
            port = bd_port,
            database = bd_database
            )
        
        cursor = connection.cursor()              
        
        select_user = '''SELECT id, name, email,password,isAdmin FROM users WHERE name = %s or email = %s;  '''
        cursor.execute(select_user,(name1,email1))              
        result = cursor.fetchall()[0]
    except (Exception, psycopg2.Error) as error:
        print("Error while connecting to PostgreSQL: ", error)
    finally:
        if connection:
            cursor.close()
            connection.close()            
    return result


def user_login(name,password):
    config = configparser.ConfigParser()
    e=dir_path()
    config.read(e) 
    #bd connection
    bd_user = config['db']['user']
    bd_password = config['db']['password']   
    bd_host=config['db']['host']
    bd_port=config['db']['port']
    bd_database=config['db']['database']
    result= False
    try:    
        connection = psycopg2.connect(
            user = bd_user,
            password = bd_password,
            host = bd_host,
            port = bd_port,
            database = bd_database
            )
        
        cursor = connection.cursor()              
        
        login_user = '''SELECT id, name, email,password,isAdmin FROM users WHERE name = %s AND password = %s;  '''
        cursor.execute(login_user,(name,password))              
        result = cursor.fetchall()[0]
    except (Exception, psycopg2.Error) as error:
        print("Error while connecting to PostgreSQL: ", error)
    finally:
        if connection:
            cursor.close()
            connection.close()            
    return result