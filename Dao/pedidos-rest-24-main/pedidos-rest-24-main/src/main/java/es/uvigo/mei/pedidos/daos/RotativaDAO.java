package es.uvigo.mei.pedidos.daos;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.uvigo.mei.pedidos.entidades.Rotativa;
import es.uvigo.mei.pedidos.entidades.Tirada;

public interface RotativaDAO extends JpaRepository<Rotativa, Long>{
    List<Rotativa> findByNombreContaining(String nombre); 
     
    @Query("SELECT f FROM Rotativa f WHERE f.descripcion LIKE %:patron%")
	public List<Rotativa> findByPatronDescripcion(@Param("patron") String patron);
    //@Query("SELECT f FROM Tirada AS f  JOIN FETCH f.rotativa    WHERE f.rotativa.id  = :id")
    //public List<Tirada> findByRotativaId(Long id);
    
}


