import api from "./pedidosAPI";

class ContratoService {
  buscarTodas() {
    return api.get("/contratos");
  }
  buscarPorId(id) {
    return api.get(`/contratos/${id}`);
  }
  buscarPorDescripcion(descripcion) {
    return api.get(`/contratos?descripcion=${descripcion}`);
  }
  buscarPorNombre(nombre) {
    return api.get(`/contratos?nombre=${nombre}`);
  }
  crear(data) {
    return api.post("/contratos", data);
  }

  modificar(id, data) {
    return api.put(`/contratos/${id}`, data);
  }

  eliminar(id) {
    return api.delete(`/contratos/${id}`);
  }


}

const service = new ContratoService();
export default service 
