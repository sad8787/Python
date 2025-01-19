package es.uvigo.mei.pedidos.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.uvigo.mei.pedidos.entidades.Tirada;

public interface TiradaDAO extends JpaRepository<Tirada, Long>{  
    
    List<Tirada> findByNombreContaining(String nombre);	
    @Query("SELECT f FROM Tirada f WHERE f.descripcion LIKE %:patron%")
	public List<Tirada> findByPatronDescripcion(@Param("patron") String patron);

    
    @Query("SELECT f FROM Tirada AS f  JOIN FETCH f.brigada    WHERE f.brigada.id  = :id")
    public List<Tirada> findByBrigadaId(Long id);   
    //@Query("SELECT DISTINCT p FROM Pedido AS p JOIN FETCH p.lineas WHERE p.id = :id")
    @Query("SELECT f FROM Tirada AS f  JOIN FETCH f.rotativa    WHERE f.rotativa.id  = :id")
    public List<Tirada> findByRotativaId(Long id);   
    
    
}
