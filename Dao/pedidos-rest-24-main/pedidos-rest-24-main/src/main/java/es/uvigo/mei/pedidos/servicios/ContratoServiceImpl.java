package es.uvigo.mei.pedidos.servicios;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.uvigo.mei.pedidos.daos.ContratoDAO;
import es.uvigo.mei.pedidos.entidades.Contrato;

@Service
public class ContratoServiceImpl implements ContratoService{
    @Autowired
    ContratoDAO dao;

    @Override
	@Transactional
    public Contrato crear(Contrato contrato){
        return dao.save(contrato);
    }
    @Override
	@Transactional
	public Contrato modificar(Contrato contrato) {
		return dao.save(contrato);
	}
    @Override
	@Transactional
	public void eliminar(Contrato contrato){
        dao.delete(contrato);
    }
    @Override
	@Transactional
	public Optional<Contrato> buscarPorid(Long id){
        return dao.findById(id);
    }
    @Override
	@Transactional
	public List<Contrato> buscarTodos(){
        return dao.findAll();
    }
    @Override
	@Transactional
    public List<Contrato> buscarPorNombre(String patron){
       return  dao.findByNombreContaining(patron);
    }

    @Override
	@Transactional
	public List<Contrato> buscarPorPatronDescripcion(String patron){
        return dao.findByPatronDescripcion(patron);

    }

    
}
