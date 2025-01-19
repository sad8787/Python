package es.uvigo.mei.pedidos.servicios;

import java.util.List;
import java.util.Optional;

import es.uvigo.mei.pedidos.entidades.Brigada;




public interface BrigadaService {
    public Brigada crear(Brigada brigada);
	public Brigada modificar(Brigada brigada);
	public void eliminar(Brigada brigada);
	
	public List<Brigada> buscarTodos();
	public List<Brigada> buscarPorNombre(String nombre);
	public Optional<Brigada> buscarPorId(long  id);
	public List<Brigada> buscarPorPatronDescripcion(String patron);

	

	
	
}
