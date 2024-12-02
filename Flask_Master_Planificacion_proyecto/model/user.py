class User:    
    # конструктор класса
    def __init__(self, name= None,email= None,password= None,isAdmin= False,activo=True):
        self.id=None
        self.name = name
        self.email = email
        self.password = password  
        self.isAdmin = isAdmin
        self.activo = activo
        
        
    def cheqPassword(self,password):
        return (self.password == password  )

