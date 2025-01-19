import api from "./pedidosAPI";

class Tiradaservice {
  buscarTodas() {
    return api.get("/tiradas");
  }
  buscarPorDescripcion(descripcion) {
    return api.get(`/tiradas?descripcion=${descripcion}`);
  }

  buscarPorId(id) {
    return api.get(`/tiradas/${id}`);
  }

  
  buscarPorNombre(dni) {
    return api.get(`/tiradas?nombre=${dni}`);
  }
 
  getBrigada(id) {   
    return api.get(`/tiradas/${id}/brigada`);
  }
  getRotativa(id) {   
    return api.get(`/tiradas/${id}/rotativa`);
  }
  crear(data) {
    return api.post("/tiradas", data);
  }

  modificar(id, data) {
    return api.put(`/tiradas/${id}`, data);
  }

  eliminar(id) {
    return api.delete(`/tiradas/${id}`);
  }
  buscarPorRotativaId(id) {
    return api.get(`/tiradas/rotativa/${id}`);
  }
  buscarPorbrigadaId(id) {
    return api.get(`/tiradas/brigada/${id}`);
  }

 
}

const service = new Tiradaservice();
export default service 
