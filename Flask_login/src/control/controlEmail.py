import smtplib
from email.message import EmailMessage
import ssl
import configparser

def enviar_correo(dir_path,asunto,nombre,email,body):
    # Configuración del correo
    config_Parser = configparser.ConfigParser()               
    config_Parser.read(dir_path)


    remitente = config_Parser['email']['email']
    destinatario = email
    contraseña = config_Parser['email']['password']
    body=f'''Nombre: {nombre}
    Email: {email} 
     {body}'''
    # Crear el mensaje
    mensaje = EmailMessage()
    mensaje['Subject'] = asunto
    mensaje['From'] = remitente
    mensaje['To'] = destinatario
    mensaje.set_content(body)
    result=False
    try:
        context= ssl.create_default_context()
        # Conectar al servidor SMTP de Microsoft
        with smtplib.SMTP_SSL('smtp.gmail.com', 465,context=context) as servidor:
            #servidor.starttls()  # Iniciar conexión segura
            servidor.login(remitente, contraseña)  # Autenticarse
            servidor.sendmail(remitente,destinatario,mensaje.as_string())
            servidor.send_message(mensaje)  # Enviar correo
            result= True
    except Exception as e:
        print(f"Error al enviar el correo: {e}")
    return result




