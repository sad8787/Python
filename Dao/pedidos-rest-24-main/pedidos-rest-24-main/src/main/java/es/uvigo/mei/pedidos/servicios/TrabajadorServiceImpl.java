package es.uvigo.mei.pedidos.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.uvigo.mei.pedidos.daos.TrabajadorDAO;
import es.uvigo.mei.pedidos.entidades.Trabajador;

@Service
public class TrabajadorServiceImpl  implements TrabajadorService{
    @Autowired
    TrabajadorDAO dao;
    @Override
	@Transactional
    public Trabajador crear(Trabajador trabajador){return dao.save(trabajador);}
    @Override
	@Transactional
	public Trabajador modificar(Trabajador trabajador){return dao.save(trabajador);}
	@Override
	@Transactional
    public void eliminar(Trabajador trabajador){dao.delete(trabajador);}
    @Override
	@Transactional
    public List<Trabajador> buscarTodos(){return dao.findAll();}
	@Override
	@Transactional
    public List<Trabajador> buscarPorDNI(String dni){return dao.findByDNIContaining(dni);}	
	@Override
	@Transactional
    public List<Trabajador> buscarPorNombre(String nombre){return dao.findByNombreContaining(nombre);}
    @Override
	@Transactional
    public List<Trabajador> buscarPorBrigada(long  id){return  dao.findByBrigadaId(id);}
    @Override
	@Transactional
	public List<Trabajador> buscarPorContrto(long id){return  dao.findByContratoId(id);}
    @Override
	@Transactional
	public Optional<Trabajador> buscarPorId(long  id){return dao.findById(id);}
	@Override
	@Transactional
	public List<Trabajador> buscarPorBrigadaId(long id){return  dao.findByBrigadaId(id);}
}
