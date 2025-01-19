package es.uvigo.mei.pedidos.servicios;

import java.util.List;
import java.util.Optional;

import es.uvigo.mei.pedidos.entidades.Tirada;

public interface TiradaServise {
    public Tirada crear(Tirada tirada);
	public Tirada modificar(Tirada tirada);
	public void eliminar(Tirada tirada);
	
	public List<Tirada> buscarTodos();
	public List<Tirada> buscarPorNombre(String nombre);
	public Optional<Tirada> buscarPorId(long  id);
    public List<Tirada> buscarPorDescripcion(String patron);
    public List<Tirada> buscarPorBrigadaId(long id);
    public List<Tirada> buscarPorRotativaId(long id);
}
