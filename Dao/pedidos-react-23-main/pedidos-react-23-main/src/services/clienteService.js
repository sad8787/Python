import api from "./pedidosAPI";

class ClienteService {
  buscarTodos() {
    return api.get("/clientes");
  }

  buscarPorDNI(dni) {
    return api.get(`/clientes/${dni}`);
  }

  crear(data) {
    return api.post("/clientes", data);
  }

  modificar(dni, data) {
    return api.put(`/clientes/${dni}`, data);
  }

  eliminar(dni) {
    return api.delete(`/clientes/${dni}`);
  }

  buscarPorNombre(nombre) {
    return api.get(`/clientes?nombre=${nombre}`);
  }

  buscarPorLocalidad(localidad) {
    return api.get(`/clientes?localidad=${localidad}`);
  }
}

const service = new ClienteService();
export default service;
