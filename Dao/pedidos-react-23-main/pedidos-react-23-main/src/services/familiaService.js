import api from "./pedidosAPI";

class FamiliaService {
  buscarTodas() {
    return api.get("/familias");
  }

  buscarPorId(id) {
    return api.get(`/familias/${id}`);
  }

  crear(data) {
    return api.post("/familias", data);
  }

  modificar(id, data) {
    return api.put(`/familias/${id}`, data);
  }

  eliminar(id) {
    return api.delete(`/familias/${id}`);
  }

  buscarPorDescripcion(descripcion) {
    return api.get(`/familias?descripcion=${descripcion}`);
  }
}

const service = new FamiliaService();
export default service 
