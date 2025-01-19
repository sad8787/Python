package es.uvigo.mei.pedidos.servicios;

import java.util.List;
import java.util.Optional;

import es.uvigo.mei.pedidos.entidades.Trabajador;

public interface TrabajadorService {
    public Trabajador crear(Trabajador trabajador);
	public Trabajador modificar(Trabajador trabajador);
	public void eliminar(Trabajador trabajador);
	public List<Trabajador> buscarPorDNI(String dni);
	public List<Trabajador> buscarTodos();
	public List<Trabajador> buscarPorNombre(String nombre);
	public List<Trabajador> buscarPorBrigada(long  id);
	public List<Trabajador> buscarPorBrigadaId(long id);
	public List<Trabajador> buscarPorContrto(long id);
	public Optional<Trabajador> buscarPorId(long  id);

    
}
