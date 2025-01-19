import api from "./pedidosAPI";

class Brigadaservice {
  buscarTodas() {
    return api.get("/brigadas");
  }
  buscarPorDescripcion(descripcion) {
    return api.get(`/brigadas?descripcion=${descripcion}`);
  }
  buscarPorId(id) {
    return api.get(`/brigadas/${id}`);
  }  
  buscarPorNombre(nombre) {
    return api.get(`/brigadas?nombre=${nombre}`);
  }
  crear(data) {
    return api.post("/brigadas", data);
  }
  modificar(id, data) {
    return api.put(`/brigadas/${id}`, data);
  }
  eliminar(id) {
    return api.delete(`/brigadas/${id}`);
  }
 
}

const service = new Brigadaservice();
export default service 
