package es.uvigo.mei.pedidos.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.uvigo.mei.pedidos.daos.RotativaDAO;
import es.uvigo.mei.pedidos.entidades.Rotativa;
@Service
public class RotativaServiceImple implements RotativaService{
    @Autowired
    RotativaDAO dao;
    @Override
	@Transactional
    public Rotativa crear(Rotativa rotativa){return dao.save(rotativa);}
    @Override
	@Transactional
	public Rotativa modificar(Rotativa rotativa){return dao.save(rotativa);}
    @Override
	@Transactional
	public void eliminar(Rotativa rotativa){dao.delete(rotativa);}
	@Override
	@Transactional
	public List<Rotativa> buscarTodos(){return dao.findAll();}
    @Override
	@Transactional
	public List<Rotativa> buscarPorNombre(String nombre){return dao.findByNombreContaining(nombre);}
    @Override
	@Transactional
	public Optional<Rotativa> buscarPorId(long  id){return  dao.findById(id);}
    @Override
	@Transactional
    public List<Rotativa> buscarPorDescripcion(String patron){ return dao.findByPatronDescripcion(patron);}
}
