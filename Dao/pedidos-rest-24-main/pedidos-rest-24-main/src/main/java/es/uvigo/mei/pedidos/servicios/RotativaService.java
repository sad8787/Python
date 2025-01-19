package es.uvigo.mei.pedidos.servicios;

import java.util.List;
import java.util.Optional;

import es.uvigo.mei.pedidos.entidades.Rotativa;

public interface RotativaService {
    public Rotativa crear(Rotativa rotativa);
	public Rotativa modificar(Rotativa rotativa);
	public void eliminar(Rotativa rotativa);
	
	public List<Rotativa> buscarTodos();
	public List<Rotativa> buscarPorNombre(String nombre);
	public Optional<Rotativa> buscarPorId(long  id);
    public List<Rotativa> buscarPorDescripcion(String patron);
}
