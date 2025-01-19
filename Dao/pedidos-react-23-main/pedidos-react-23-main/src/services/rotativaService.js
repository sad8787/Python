import api from "./pedidosAPI";

class RotativaService {
  buscarTodas() {
    return api.get("/rotativas");
  }

  buscarPorId(id) {
    return api.get(`/rotativas/${id}`);
  }

  crear(data) {
    return api.post("/rotativas", data);
  }

  modificar(id, data) {
    return api.put(`/rotativas/${id}`, data);
  }

  eliminar(id) {
    return api.delete(`/rotativas/${id}`);
  }

  buscarPorDescripcion(descripcion) {
    return api.get(`/rotativas?descripcion=${descripcion}`);
  }
}

const service = new RotativaService();
export default service 
