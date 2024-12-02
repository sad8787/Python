from flask import Flask,render_template,request , jsonify, render_template, redirect, url_for, session
#from config import Config, db
from werkzeug.security import generate_password_hash, check_password_hash
import jwt
import datetime
from model.user import User
from data.toPosgres import toPosgres,dir_path,tabla_existe
from controlers.utilControler import addUser,listUser,getUserBayId,user_login,getUserByNameAndEmail




import os
import configparser

config = configparser.ConfigParser()
dir_addres=dir_path()
config.read(dir_addres)



app = Flask(__name__)
#app.config.from_object(Config)
#db.init_app(app)

print(len(config.keys()))

print ("Hola este es un Proyecto Para la asignatura Planificacion de Proyecto")


port1=user1=password1=database1=""
host1=0
counter = 0
while_True=True
while while_True:
    counter = counter + 1
    if counter == 5:
        host1 = config['db']['host']
        port1 = config['db']['port']
        user1 = config['db']['user'] # Cambiar al usuario de tu base de datos
        password1 = config['db']['password']  # Cambiar a la contraseña de tu base de datos
        database1 = config['db']['database']  # Cambiar al nombre de tu base de datos
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
        print(f'''host: {host1} port: {port1}  user: {user1}  database: { database1 }''')
        print("----------- Esta de acurdo con los datos datos?  Y or N")
        input1 = input()
        if((input1=="Y") or (input1=="y") or (input1=="S")or (input1=="s")):
            config['db']['host'] = host1
            config['db']['port'] =port1
            config['db']['user'] =user1 # Cambiar al usuario de tu base de datos
            config['db']['password'] = password1  # Cambiar a la contraseña de tu base de datos
            config['db']['database'] = database1  # Cambiar al nombre de tu base de datos

            # Guardar los cambios de vuelta al archivo
            with open(f'{dir_addres}', 'w') as configfile:
                config.write(configfile)
            break
    else:
        host1 = config['db']['host']
        port1 = config['db']['port']
        user1 = config['db']['user'] # Cambiar al usuario de tu base de datos
        password1 = config['db']['password']  # Cambiar a la contraseña de tu base de datos
        database1 = config['db']['database']  # Cambiar al nombre de tu base de datos        
        break
    
try:  
    if(not tabla_existe('''users''')):
        toPosgres()  
# Consultar usuarios   
except Exception  as e:
            print("Error al conectar a MySQL o crear la base de datos:", e)
#Create_tables.crear_base_datos_si_no_existe(host1,port1,user1,password1,database1)








@app.route('/')
def index():    
    if (request.method=='POST'):        
        return 'hola post'
    else:
        return render_template('/login.html')


@app.route('/register',methods=['GET', 'POST'])
def registro():
    if (request.method=='POST'): 
        if(request.json):
            try:
                data = request.json
                user_name = data.get('user')
                email1 = data.get('email')
                password1 = data.get('password')
                user = User(name=user_name, email=email1, password=password1)                
                result = addUser(user)
                
                print(f'''{result}''')       
                return jsonify({"message": f'''{result}'''}), 201    
            except Exception as err:
                print(f"Unexpected {err=}, {type(err)=}")
                return jsonify({"message": err})        
        return jsonify({"message": "datosincorrectos"}),401
    else:
        return render_template('/register.html')

@app.route('/users')
def list_users():
    users = listUser()
    users_data = [{"id": user[0], "name": user[1], "email": user[2],"isAdmin":[4],"activo":[5]} for user in users]
    return jsonify({"status": "success", "data": users_data}), 200
   
    
@app.route('/users/<int:user_id>')
def get_user(user_id):
    user = getUserBayId(user_id)
    if user:
        user_data = {
            "id": user[0],
            "name": user[1],
            "email": user[2],
            "isAdmin":[4],
            "activo":[5]
            }
        return jsonify({"status": "success", "data": user_data}), 200
    else:
        return jsonify({"status": "error", "message": "User not found"}), 404



@app.route('/login', methods=['GET', 'POST'])
def login():    
    if request.method == 'POST':        
        if(request.json):
            data = request.json
            print(data)
            user_name = data.get('user')
            password = data.get('password')             
            user = user_login(name= user_name,password=password)      
             # Aquí puedes manejar la autenticación
            if user:
                user_data = {
                    "id": user[0],
                    "name": user[1],
                    "email": user[2],
                    "password": user[3],
                    "isAdmin":[4],
                    "activo":[5]
                }
                return jsonify({"message": "Login exitoso"},user_data), 200
            else:
                return jsonify({"message": "Credenciales inválidas"}), 401            
        else:
            print(request.form['user'])
            print(request.form['password'])
            return jsonify({"message": "Credenciales inválidas"}), 401        
    else:
        return 't'

if __name__== '__main__':    
    app.run()
