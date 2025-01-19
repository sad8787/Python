package es.uvigo.mei.pedidos.entidades;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Rotativa implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }   
    private String nombre;
    private String descripcion;

    //@JsonIgnore
    //@OneToMany(mappedBy = "rotativa", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    //private List<Tirada> tiradas;
   


    public Rotativa() {
    }

    public Rotativa(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        
    }

    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    //public List<Tirada> getTiradas() {
     //   return tiradas;
    ///}

    //public void setTiradas(List<Tirada> tiradas) {
     //   this.tiradas = tiradas;
   // }

    @Override
    public int hashCode() {
        int hash = Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Rotativa other = (Rotativa) obj;        
        if (this.id != null) {
            return this.id.equals(other.getId());
        }        
        return true;
    }

    @Override
    public String toString() {
        return "Rotativa{" + " nombre=" + nombre  + '}';
    }
    
    
}
