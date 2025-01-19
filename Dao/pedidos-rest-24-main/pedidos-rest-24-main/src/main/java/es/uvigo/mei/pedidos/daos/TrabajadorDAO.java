package es.uvigo.mei.pedidos.daos;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.uvigo.mei.pedidos.entidades.Trabajador;

public interface TrabajadorDAO extends JpaRepository<Trabajador, Long>{     
    List<Trabajador> findByNombreContaining(String nombre);	
    List<Trabajador> findByDNIContaining(String dni);	
    List<Trabajador> findByCargoContaining(String cargo);
    List<Trabajador> findByNivelEducativoContaining(String nivelEducativo);

    List<Trabajador> findByContratoId(Long id);   

    
    List<Trabajador> findByBrigadaId(Long id);   
    
    
}
