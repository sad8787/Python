from flask import Flask, request, jsonify,url_for
from flask_sqlalchemy import SQLAlchemy
from flask_bcrypt import Bcrypt
from flask_jwt_extended import JWTManager, create_access_token, jwt_required, get_jwt_identity
from configparser import ConfigParser
from itsdangerous import URLSafeTimedSerializer

from models.modelos import User
from control.controlEmail import enviar_correo

ts = URLSafeTimedSerializer('supersecretkey')

def control_User(data,db,bcrypt):
    print ("control users")
    try:                      
        action=data.get('action')          
    except Exception as err:        
        return jsonify({"success": str( False),"ok": str(False) ,"controlador": "users","accion":"add_user","code": "201"   ,"message": {str(err)}}), 201
    if(action=="add"):
        return add_user(data,db,bcrypt)
    if(action=="delete"):
        return delete(data,db)
    if(action=="update"):
        return update(data,db)
    if(action=="list"):
        return list_user(data,db)
    if(action=="searchById"):
        return searchById(data,db)   
    if(action=="search"):
        return search(data,db)                        
    if(action=="changePassword"):        
        return changePassword(data,db, bcrypt )
    if(action=="login"):        
        return login(data,db, bcrypt )

def add_user(data,db,bcrypt):
    try:                      
            name = data.get('name')
            email = data.get('email')  
            password = data.get('password')  

            if not email or not password or not name:
                return jsonify({"error": "Todos los datos son requeridos"}), 400

    except Exception as err:        
            return jsonify({"success": str( False),"ok": str(False) ,"controlador": "users","accion":"add_user","code": "201"   ,"message": {str(err)}}), 201
    
    existe =User.query.filter_by(email=email).first()
    if existe :
        if(existe.activo):
            return jsonify({"error": "El correo ya está registrado"}), 400
        else:
            token = ts.dumps(new_user.email, salt='email-confirm-key')
            confirm_url = url_for('confirm_email', token=token, _external=True)        
            asunto = "Confirma tu correo electrónico"
            body=f'''Por favor, haz clic en el siguiente enlace para activar tu cuenta: {confirm_url}'''
            enviar_correo(asunto,new_user.name,new_user.email,body)
            return jsonify({"success": True,"ok": True,"controlador": "users",
                            "accion":"add_user","code": "201",
                            "message": "Usuario registrado. Revisa tu correo para activarlo."}), 201
    
    try:
        hashed_password = bcrypt.generate_password_hash(data['password']).decode('utf-8')
        new_user = User(name=data['name'], email=data['email'],password=hashed_password,is_admin=False,activo=False)
        db.session.add(new_user)
        db.session.commit()


        token = ts.dumps(new_user.email, salt='email-confirm-key')
        confirm_url = url_for('confirm_email', token=token, _external=True)
        
        asunto = "Confirma tu correo electrónico"
        body=f'''Por favor, haz clic en el siguiente enlace para activar tu cuenta: {confirm_url}'''
        enviar_correo(asunto,new_user.name,new_user.email,body)
        return jsonify({"success": True,"ok": True,"controlador": "users","accion":"add_user","code": "201","message": "Usuario registrado. Revisa tu correo para activarlo."}), 201

    except Exception as err:       
        return jsonify({"success": False,"ok": False,"controlador": "users","accion":"add_user","code": "201"   ,"message": str(err)}),201

def delete(data,db):
    try:                      
        id=data.get('id') 
        action=data.get('action') 
        controlador=data.get('controlador')
        user = User.query.get_or_404(id)
        db.session.delete(user)
        db.session.commit()
        return jsonify({"message": "User deleted successfully"})
    except Exception as err:        
        return jsonify({"success": str( False),"ok": str(False) ,"controlador":controlador ,"action":action,"code": "201"   ,"message": {str(err)}}), 201

def update(data,db):
    id=data.get('id')  
    name =data.get('name') 
    email =data.get('email')     
    is_admin =data.get('is_admin')    
    activo= data.get('is_admin')
    user = User.query.get_or_404(id)
    
    user.name = name
    user.email = email
    user.is_admin = is_admin
    user.activo=activo
    db.session.commit()
    return jsonify({"message": "user updated successfully"}),201

def list_user(data,db):
    users = User.query.all()
    list_user=[]
    for user in users:
        list_user.append( {"id": user.id, "name": user.name, "email": user.email,"is_admin": user.is_admin, "activo": user.activo})
    
    return jsonify({"success": True,"ok": True,"controlador": "users",
                    "accion":"list","code": "201","message": "list user",
                    "users":list_user,"len":len(list_user)}), 201    
 
def searchById(data,db):

    ID = data.get('id')
    user = User.query.filter_by(id=ID).first()
    r= {"id": user.id, "name": user.name, "email": user.email,"is_admin": user.is_admin, "activo": user.activo}
    
    return jsonify({"success": True,"ok": True,"controlador": "users",
                    "accion":"list","code": "201","message": "list user",
                    "users":r,"len":1}), 201

def search(data,db):
    value= data.get("searchvalue")
    users = User.query.filter(
        (User.email == value) | (User.name == value)
    ).all()
    
    list_user=[]
    for user in users:
        list_user.append( {"id": user.id, "name": user.name, "email": user.email})
    print(list_user)
    return jsonify({"success": True,"ok": True,"controlador": "users",
                    "accion":"list","code": "201","message": "list user",
                    "users":list_user,"len":len(list_user)}), 201    

def changePassword(data,db,bcrypt):    
    id=data.get('id')     
    newpassword = data.get('newpassword')     
    oldpassword  =data.get('oldpassword') 

    newpassword = bcrypt.generate_password_hash(newpassword).decode('utf-8')
    oldpassword = bcrypt.generate_password_hash(oldpassword).decode('utf-8') 
    if not id or not newpassword or not oldpassword:
        return jsonify({"error": "Todos los datos son requeridos"}), 400 
    
    user = User.query.get_or_404(id)  
   
    print(f'{user.id} {user.password} {user.email} old {oldpassword}')
    if user and bcrypt.check_password_hash(user.password, oldpassword):              
            user.password =  newpassword   
            db.session.commit()
            return jsonify({"message": "Successfully"}),201
    else:        
        return jsonify({"success": True,"ok": True,"controlador": "users", "error": "Datos incorrectos"})
    
def login(data,db,bcrypt):
    print(f'''login {data}''')
    email = data.get('email')
    password = data.get('password')
    
    # Validate email and password
    if not email or not password:
        return jsonify({"message": "Email and password are required"}), 400
    
    # Find the user in the database
    
    user = User.query.filter_by(email=email).first()
   
    print(bcrypt.check_password_hash(user.password, password))
    if user and bcrypt.check_password_hash(user.password, password) and user.activo:
        # Generate JWT token
        access_token = create_access_token(identity={"id": user.id, "is_admin": user.is_admin})
        print(access_token)
        return jsonify({"success": str( True),"ok": str(True),"token":access_token,"message": "Ok"}),200
    else:
        return jsonify({"success": str( False),"ok": str(False),"message": "Invalid email or password"}), 401