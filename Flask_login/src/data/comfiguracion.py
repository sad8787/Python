import os
import configparser
import string

class Configuracion:
    config = configparser.ConfigParser()
    dir_path=''
    def __init__(self,):
        dir_path1 = os.path.dirname(os.path.realpath(__file__))
        b=str(dir_path1)
        string1 = string.digits+string.ascii_letters +",. _-+():"
        #string1="abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWQYZ, .1234567890-_:"
        dir_path1=f''''''
        for x in b:
            if(x in string1):
                dir_path1+=x
            else:
                dir_path1+=f'//'
        dir_path1=f'{dir_path1}//config.ini'
        self.dir_path =dir_path1
        config_Parser = configparser.ConfigParser() 
        print(f'config dir {dir_path1}')       
        config_Parser.read(dir_path1) 
        self.config=config = {
            'host': config_Parser['mysql']['host'],  # Dirección del servidor MySQL
            'user': config_Parser['mysql']['user']  ,# Tu usuario de MySQL
            'password': config_Parser['mysql']['password'],  # Tu contraseña de MySQL
            'database': config_Parser['mysql']['database']  # Base de datos a la que quieres conectar                
        }
        
    

   