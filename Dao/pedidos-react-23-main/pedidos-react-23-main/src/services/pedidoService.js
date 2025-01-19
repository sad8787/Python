import api from "./pedidosAPI";

class PedidoService {
  buscarTodos() {
    return api.get("/pedidos");
  }

  buscarPorId(id) {
    return api.get(`/pedidos/${id}`);
  }

  crear(data) {
    return api.post("/pedidos", data);
  }

  modificar(id, data) {
    return api.put(`/pedidos/${id}`, data);
  }

  eliminar(id) {
    return api.delete(`/pedidos/${id}`);
  }

  buscarPorClienteDNI(dni) {
    return api.get(`/pedidos?clienteDni=${dni}`);
  }
}

const service = new PedidoService();
export default service
