package es.uvigo.mei.pedidos.daos;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.uvigo.mei.pedidos.entidades.Contrato;

public interface ContratoDAO extends JpaRepository<Contrato, Long>{
    List<Contrato> findByNombreContaining(String nombre);	
    @Query("SELECT f FROM Contrato f WHERE f.descripcion LIKE %:patron%")
	public List<Contrato> findByPatronDescripcion(@Param("patron") String patron);
    
}
