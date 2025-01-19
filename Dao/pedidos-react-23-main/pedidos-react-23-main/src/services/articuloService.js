import api from "./pedidosAPI";

class ArticuloService {
  buscarTodos() {
    return api.get("/articulos");
  }

  buscarPorId(id) {
    return api.get(`/articulos/${id}`);
  }

  crear(data) {
    return api.post("/articulos", data);
  }

  modificar(id, data) {
    return api.put(`/articulos/${id}`, data);
  }

  eliminar(id) {
    return api.delete(`/articulos/${id}`);
  }

  buscarPorDescripcion(descripcion) {
    return api.get(`/articulos?descripcion=${descripcion}`);
  }

  buscarPorFamiliaId(familiaId) {
    return api.get(`/articulos?familiaId=${familiaId}`);
  }
}

const service = new ArticuloService();
export default service;
