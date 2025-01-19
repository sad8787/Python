package es.uvigo.mei.pedidos.servicios;

import java.util.List;
import java.util.Optional;

import es.uvigo.mei.pedidos.entidades.Contrato;
public interface  ContratoService {
    public Contrato crear(Contrato contrato);
	public Contrato modificar(Contrato contrato);
	public void eliminar(Contrato contrato);
	public Optional<Contrato> buscarPorid(Long id);

	public List<Contrato> buscarTodos();
    public List<Contrato> buscarPorNombre(String patron);
	public List<Contrato> buscarPorPatronDescripcion(String patron);
	
}
