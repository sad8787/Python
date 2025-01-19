package es.uvigo.mei.pedidos.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.uvigo.mei.pedidos.daos.BrigadaDAO;
import es.uvigo.mei.pedidos.entidades.Brigada;
import es.uvigo.mei.pedidos.entidades.Contrato;
import es.uvigo.mei.pedidos.entidades.Trabajador;

@Service
public class BrigadaServiceImpl implements BrigadaService  {
    @Autowired
    BrigadaDAO dao;
    @Override
	@Transactional
    public Brigada crear(Brigada brigada){return dao.save(brigada);}
    @Override
	@Transactional
	public Brigada modificar(Brigada brigada){return dao.save(brigada);}
    @Override
	@Transactional
	public void eliminar(Brigada brigada){ dao.delete(brigada);}
    @Override
	@Transactional
	public List<Brigada> buscarTodos(){return dao.findAll();};
    @Override
	@Transactional
	public List<Brigada> buscarPorNombre(String nombre){return dao.findByNombreContaining(nombre);}
    @Override
	@Transactional
	public Optional<Brigada> buscarPorId(long  id){return dao.findById(id);}
	@Override
	@Transactional
	public List<Brigada> buscarPorPatronDescripcion(String patron){return dao.findByPatronDescripcion(patron);}
	
	
    
    
}
