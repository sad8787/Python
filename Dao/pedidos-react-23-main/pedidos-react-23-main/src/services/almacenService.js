import api from "./pedidosAPI";

class AlmacenService {
  buscarTodos() {
    return api.get("/almacenes");
  }

  buscarPorId(id) {
    return api.get(`/almacenes/${id}`);
  }

  crear(data) {
    return api.post("/almacenes", data);
  }

  modificar(id, data) {
    return api.put(`/almacenes/${id}`, data);
  }

  eliminar(id) {
    return api.delete(`/almacenes/${id}`);
  }

  buscarPorLocalidad(localidad) {
    return api.get(`/almacenes?localidad=${localidad}`);
  }

  buscarPorArticuloId(idArticulo) {
    return api.get(`/almacenes?articuloId=${idArticulo}`);
  }

  buscarArticulosAlmacen(id) {
    return api.get(`/almacenes/${id}/articulos`);
  }

  anadirArticuloAlmacen(idAlmacen, nuevoArticuloAlmacen) {
    return api.post(`/almacenes/${idAlmacen}/articulos`, nuevoArticuloAlmacen);
  }

  modificarArticuloAlmacen(idAlmacen, idArticulo, nuevoArticuloAlmacen) {
    return api.put(`/almacenes/${idAlmacen}/articulos/${idArticulo}`, nuevoArticuloAlmacen);
  }

  eliminarArticuloAlmacen(idAlmacen, idArticulo) {
    return api.delete(`/almacenes/${idAlmacen}/articulos/${idArticulo}`);
  }
}

const service = new AlmacenService();
export default service;
