package es.uvigo.mei.pedidos.daos;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.uvigo.mei.pedidos.entidades.Brigada;

public interface BrigadaDAO extends JpaRepository<Brigada, Long>{
    List<Brigada> findByNombreContaining(String nombre);    
    @Query("SELECT f FROM Brigada f WHERE f.descripcion LIKE %:patron%")
	public List<Brigada> findByPatronDescripcion(@Param("patron") String patron);

   // @Query("SELECT f FROM Tirada AS f  JOIN FETCH f.brigada   WHERE f.rotativa.id  = :id")
    //public List<Tirada> findByTiradaByBrigadaId(Long id);
    
}


