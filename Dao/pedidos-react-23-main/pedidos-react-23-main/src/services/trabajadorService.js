import api from "./pedidosAPI";

class Trabajadoreservice {
  buscarTodas() {
    return api.get("/trabajadores");
  }
  buscarPorDescripcion(descripcion) {
    return api.get(`/trabajadores?descripcion=${descripcion}`);
  }

  buscarPorId(id) {
    return api.get(`/trabajadores/${id}`);
  }

  buscarPorDNI(dni) {
    return api.get(`/trabajadores?dni=${dni}`);
  }
  buscarPorNombre(nombre) {
    return api.get(`/trabajadores?nombre=${nombre}`);
  }

  buscarPorbrigadaId(id) {
    return api.get(`/trabajadores/brigada/${id}`);
  }

  buscarPorcontratoId(id) {
    return api.get(`/trabajadores/contrato/${id}`);
  }

  getBrigada(id) {   
    return api.get(`/trabajadores/${id}/brigada`);
  }
  crear(data) {
    return api.post("/trabajadores", data);
  }

  modificar(id, data) {
    return api.put(`/trabajadores/${id}`, data);
  }

  eliminar(id) {
    return api.delete(`/trabajadores/${id}`);
  }

 
}

const service = new Trabajadoreservice();
export default service 
