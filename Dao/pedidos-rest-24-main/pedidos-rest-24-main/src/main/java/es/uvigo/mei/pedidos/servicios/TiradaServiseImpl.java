package es.uvigo.mei.pedidos.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.uvigo.mei.pedidos.daos.TiradaDAO;

import es.uvigo.mei.pedidos.entidades.Tirada;

@Service
public class TiradaServiseImpl implements TiradaServise {
    @Autowired
    TiradaDAO dao;
    @Override
	@Transactional
    public Tirada crear(Tirada tirada){return dao.save(tirada);}
    @Override
	@Transactional
	public Tirada modificar(Tirada tirada){return dao.save(tirada);}
    @Override
	@Transactional
	public void eliminar(Tirada tirada){dao.delete(tirada);}
    @Override
	@Transactional	
	public List<Tirada> buscarTodos(){return dao.findAll();}
    @Override
	@Transactional
	public List<Tirada> buscarPorNombre(String nombre){return dao.findByNombreContaining(nombre);}
    @Override
	@Transactional
	public Optional<Tirada> buscarPorId(long  id){return dao.findById(id);}
    @Override
	@Transactional
    public List<Tirada> buscarPorDescripcion(String patron){return dao.findByPatronDescripcion(patron);}

    @Override
	@Transactional
    public List<Tirada> buscarPorBrigadaId(long id){return dao.findByBrigadaId(id);}
    @Override
	@Transactional
    public List<Tirada> buscarPorRotativaId(long id){return dao.findByRotativaId(id);}
}
