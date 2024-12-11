from flask import Flask,render_template,request , jsonify, render_template, redirect, url_for, session
#from config import Config, db
from werkzeug.security import generate_password_hash, check_password_hash
#import jwt
#import datetime
import json
import getpass
import configparser
#from modelos.user_model import User
from data.comfiguracion import Configuracion

from data.mysqldb import ejecutaSQL 
from control import control
from control.controlEmail import enviar_correo
#import data.dbSql as dbSql


app = Flask(__name__)

configuracion =Configuracion()
config= configuracion.config
dir_path= configuracion.dir_path



control.iniciarDB(config)
port1=user1=password1=database1=""
host1=0
counter=0
while_True=True
config_Parser = configparser.ConfigParser()     
config_Parser.read(dir_path)
print(config_Parser['mysql']['host'])


while while_True:

    counter = counter + 1
    if counter == 5:
        host1 = config_Parser['mysql']['host']
        port1 = config_Parser['mysql']['port']
        user1 = config_Parser['mysql']['user'] # Cambiar al usuario de tu base de datos
        password1 = config_Parser['mysql']['password']  # Cambiar a la contraseña de tu base de datos
        database1 = config_Parser['mysql']['database']  # Cambiar al nombre de tu base de datos
        break
    print("----------- Desea entrar los datos para la coneccion a la base de datos?  Y or N")
    input1 = input()
    if((input1=="Y") or (input1=="y") or (input1=="S")or (input1=="s")):
        print("Entre el host")
        host1 = input()
        print("Entre el Port")
        port1 = input()
        print("Entre el user")
        user1 = input() # Cambiar al usuario de tu base de datos
        print("Entre el password")
        password1 = input() # Cambiar a la contraseña de tu base de datos
        print("Entre la base de datos")
        database1 = input() 

        mail = input('Enter your email:')        
        pswd = getpass.getpass('Password:')
        print(f'''host: {host1} port: {port1}  user: {user1}  database: { database1 }   {mail}''')
        print("----------- Esta de acurdo con los datos datos?  Y or N")
        input1 = input()
        if((input1=="Y") or (input1=="y") or (input1=="S")or (input1=="s")):
            config_Parser['mysql']['host'] = host1
            config_Parser['mysql']['port'] =port1
            config_Parser['mysql']['user'] =user1 # Cambiar al usuario de tu base de datos
            config_Parser['mysql']['password'] = password1  # Cambiar a la contraseña de tu base de datos
            config_Parser['mysql']['database'] = database1  # Cambiar al nombre de tu base de datos

            config_Parser['email']['email']  = mail
            config_Parser['email']['password'] = pswd

            # Guardar los cambios de vuelta al archivo
            with open(f'{dir_path}', 'w') as configfile:
                config_Parser.write(configfile)
            break
    else:
        host1=config_Parser['mysql']['host']       
        user1 = config_Parser['mysql']['user'] # Cambiar al usuario de tu base de datos
        password1 = config_Parser['mysql']['password']  # Cambiar a la contraseña de tu base de datos
        database1 = config_Parser['mysql']['database']  # Cambiar al nombre de tu base de datos        
        break




          
############################################################# users ##########################    
@app.route('/users/<int:user_id>')
def get_user(user_id):
    user = control.getUserBayId(config,user_id)
    print(user)
    if user:
        user_data = {
            "id": user[0],
            "name": user[1],
            "email": user[2],
            "isadmin":user[4],
            "activo":user[5]
            }
        return jsonify({"status": "success", "data": user_data}), 200
    else:
        return jsonify({"status": "error", "message": "User not found"}), 404
       

@app.route('/login', methods=['GET', 'POST'])
def login():    
    if request.method == 'POST':        
        if(request.json):
            data = request.json            
            user = data.get('user')
            password = data.get('password')   

            rows= control.loginUser(config,user_name =user,email=user,passwod=password)  
            if (len(rows)>0):                
                user=rows[0]
                user = { "id": user[0], "name": user[1], "email": user[2], "isadmin":user[4], "activo":user[5] }
                jsonuser =json.dumps(user, indent=4)
                print(jsonuser)        
                return jsonify({"status": "success", "data": jsonuser}), 200
            
        return jsonify({"message": "Credenciales inválidas"}), 401 


@app.route('/users')
def get_users():
    users = control.getUsers(config)    
    if users:
        users_data = [{"id": user[0], "name": user[1], "email": user[2],"isadmin":user[4],"activo":user[5]} for user in users]
        return jsonify({"status": "success", "data": users_data}), 200
    else:
        return jsonify({"status": "error", "message": "User not found"}), 404

@app.route('/register',methods=['GET', 'POST'])
def register(): 
    print ('register')  
    if (request.method=='POST'):        
        try:
            data = request.json
            password = data.get('password')             
            user_name = data.get('user')
            email = data.get('email')   
            
            result = control.addUser(config=config,name=user_name, email=email, password=password)
            if (result == True) :                                 
                return jsonify({"message": "User registered successfully!"}), 201 
            else :
                return  jsonify({"message": "El usuario ya existe"}), 201
        except Exception as err:
            print(f"Unexpected {err=}, {type(err)=}")
            return jsonify({"message": err})
    

@app.route('/deleteuser/<int:user_id>')
def delete_user(user_id):
    result = control.deleteUser(config,user_id)
    if(result == True):
        return jsonify({"status": "success"}), 200
    return jsonify({"message": "Credenciales inválidas"}), 401

@app.route('/updateuser',methods=['GET', 'POST'])
def update_user(): 
    result=False
    try:
        if (request.method=='POST'):
            data = request.json
            id = data.get('id')             
            name = data.get('name')
            email = data.get('email')  
            isadmin = data.get('isadmin')
            activo = data.get('activo')
            result= control.updateUser(config,id,name, email, isadmin,activo)
            if (result == True) :                                 
                return jsonify({"message": "successfully!"}), 201 
            else :
                return  jsonify({"message": "Algo salio mal"}), 201
    except Exception as err:
            print(f"Unexpected {err=}, {type(err)=}")
            return jsonify({"message": err})

####################################### convocatorias #############################3

@app.route('/updateconvocatorias',methods=['GET', 'POST'])
def update_convocatorias():
    result=False
    try:
        if (request.method=='POST'):
            data = request.json
            id = data.get('id')             
            name = data.get('name')
            fecha = data.get('fecha')  
            url = data.get('url')
            descripcion = data.get('descripcion')
            activo = data.get('activo')

            result= control.updateConvocatorias(config,id,name,fecha,url,descripcion,activo)
            if (result == True) :                                 
                return jsonify({"message": "successfully!"}), 201 
            else :
                return  jsonify({"message": "algo salio mal"}), 201
    except Exception as err:
            print(f"Unexpected {err=}, {type(err)=}")
            return jsonify({"message": err})      
 
@app.route('/convocatorias')
def get_convocatorias():
    convocatorias = control.getConvocatorias(config)    
    if convocatorias:
        convocatorias_data = [{"id": c[0], "name": c[1], "fecha": c[2],"url":c[3],"descripcion":c[4] ,"activo":c[5]} for c in convocatorias]
        return jsonify({"status": "success", "data": convocatorias_data}), 200
    else:
        return jsonify({"status": "error", "message": "not found"}), 404   


@app.route('/convocatorias/<int:id>')
def get_convocatoria(id):
    convocatorias = control.getConvocatoriasById(config,id)    
    if convocatorias:
        convocatorias_data=[]
        try:
            convocatorias_data = [{"id": c[0], "name": c[1], "fecha": c[2],"url":c[3],"descripcion":c[4] ,"activo":c[5]} for c in convocatorias]
        
        except:
            convocatorias_data=[]
        return jsonify({"status": "success", "data": convocatorias_data}), 200
    else:
        return jsonify({"status": "error", "message": "not found"}), 404   


@app.route('/deleteconvocatoria/<int:id>')
def delete_convocatoria(id):
    result = control.deleteConvocatorias(id)
    if(result == True):
        return jsonify({"status": "success"}), 200
    return jsonify({"message": "Credenciales inválidas"}), 401

@app.route('/newconvocatoria',methods=['GET', 'POST'])
def add_New_convocatoria():
    print ('newconvocatoria')
    result=False
    try:
        if (request.method=='POST'):
            data = request.json                    
            name = data.get('name')
            fecha = data.get('fecha')  
            url = data.get('url')
            descripcion=data.get('descripcion')         
            print(f'''{name}, {fecha}, {url},{descripcion} ''')              
            result= control.addConvocatorias(config,name, fecha, url,descripcion)
            if (result == True) :                                 
                return jsonify({"message": "User registered successfully!"}), 201 
            else :
                return  jsonify({"message": "El usuario ya existe"}), 201
    except Exception as err:
            print(f"Unexpected {err=}, {type(err)=}")
            return jsonify({"message": err})



##################################################  proyecto  ######################################

@app.route('/newproyecto',methods=['GET', 'POST'])
def add_New_proyecto():    
    result=False
    try:
        if (request.method=='POST'):            
            data = request.json                    
            name = data.get('name')
            fecha = data.get('fecha')  
            url = data.get('url')
            descripcion=data.get('descripcion')   
            print(f'{name} ,{fecha} ,{url},{descripcion}')     
            result= control.addProyecto(config,name, fecha, url,descripcion,activo=1)            
            if (result == True) :                                 
                return jsonify({"message": "proyecto registered successfully!"}), 201 
            else :
                return  jsonify({"message": "El proyecto ya existe"}), 201
    except Exception as err:
        print(f"Unexpected {err=}, {type(err)=}")
        return jsonify({"message": err})


@app.route('/proyectos')
def get_proyectos():
    p = control.getProyectos(config)    
    if p:
        convocatorias_data = [{"id": c[0], "name": c[1], "fecha": c[2],"url":c[3],"descripcion":c[4] ,"activo":c[5]} for c in p]
        return jsonify({"status": "success", "data": convocatorias_data}), 200
    else:
        return jsonify({"status": "error", "message": "not found"}), 404   


@app.route('/proyectos/<int:id>')
def get_proyectosById(id):
    convocatorias = control.getProyectosById(config,id)    
    if convocatorias:
        convocatorias_data=[]
        try:
            convocatorias_data = [{"id": c[0], "name": c[1], "fecha": c[2],"url":c[3],"descripcion":c[4] ,"activo":c[5]} for c in convocatorias]
        
        except:
            convocatorias_data=[]
        return jsonify({"status": "success", "data": convocatorias_data}), 200
    else:
        return jsonify({"status": "error", "message": "not found"}), 404   


@app.route('/deleteproyecto/<int:id>')
def delete_proyecto(id):
    result = control.deleteProyecto(id)
    if(result == True):
        return jsonify({"status": "success"}), 200
    return jsonify({"message": "Credenciales inválidas"}), 401

@app.route('/updateproyecto',methods=['GET', 'POST'])
def update_proyecto():
    result=False
    try:
        if (request.method=='POST'):
            data = request.json
            id = data.get('id')             
            name = data.get('name')
            fecha = data.get('fecha')  
            url = data.get('url')
            descripcion = data.get('descripcion')
            activo = data.get('activo')
            result= control.updateProyecto(config,id,name,fecha,url,descripcion,activo)
            if (result == True) :                                 
                return jsonify({"message": "successfully!"}), 201 
            else :
                return  jsonify({"message": "Algo salio mal"}), 201
    except Exception as err:
            print(f"Unexpected {err=}, {type(err)=}")
            return jsonify({"message": err})    


##########################################  email ###############################


@app.route('/sendemail',methods=['GET', 'POST'])
def sendemail():
    result=False
    try:
        if (request.method=='POST'):
            data = request.json
            asunto = data.get('asunto')             
            nombre = data.get('nombre')
            email = data.get('email')  
            body = data.get('body')            
            print(f'{dir_path},{asunto},{nombre},{email},{body}')
            result= enviar_correo(dir_path,asunto,nombre,email,body)
            if (result == True) :                                 
                return jsonify({"message": "successfully!"}), 201 
            else :
                return  jsonify({"message": "Algo salio mal"}), 201
    except Exception as err:
            print(f"Unexpected {err=}, {type(err)=}")
            return jsonify({"message": err})    




##############################################   TEST    ################################
@app.route('/test/sendemail')
def testsendemail():      
    if (request.method=='POST'):        
        return 'hola post'
    else:
        return render_template('/sendemail.html')


@app.route('/test/newproyectos')
def testNewproyectos():      
    if (request.method=='POST'):        
        return 'hola post'
    else:
        return render_template('/newProyecto.html')


@app.route('/test/updateproyectos')
def testUpdateProyectos():    
    if (request.method=='POST'):        
        return 'hola post'
    else:
        return render_template('/updateProyectos.html')@app.route('/test/newproyectos')
    


@app.route('/test/newconvocatoria')
def testNewConvocatorias():  
    print ('test new comvocatorias')  
    if (request.method=='POST'):        
        return 'hola post'
    else:
        return render_template('/newConvocatorias.html')

@app.route('/test/updateConvocatorias')   
def testupdateConvocatorias():    
    if (request.method=='POST'):        
        return 'hola post'
    else:
        return render_template('/updateConvocatorias.html')



@app.route('/test/login')
def testlogin():    
    if (request.method=='POST'):        
        return 'hola post'
    else:
        return render_template('/login.html')


@app.route('/test/register')
def testregister():    
    if (request.method=='POST'):        
        return 'hola post'
    else:
        return render_template('/register.html')

@app.route('/test/updateUser')
def testupdateUser():    
    if (request.method=='POST'):        
        return 'hola post'
    else:
        return render_template('/updateUser.html')


if __name__== '__main__':   
    app.run()