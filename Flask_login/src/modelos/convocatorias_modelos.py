from datetime import datetime
class Convocatorias:  
    
    def __init__(self,id=None, name=None, fecha = None,activo=None,url=None,descripcion=None,id_user=None):
        self.id =id
        self.name = name
        if (fecha is None):
            fecha=datetime.now()
        self.fecha = fecha
        self.url=url
        self.descripcion=descripcion
        self.activo = activo
       
        