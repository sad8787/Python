package es.uvigo.mei.pedidos.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Brigada implements Serializable{
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
    //@OneToMany(mappedBy = "brigada", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)    
    //private List<Trabajador> trabajadores =new ArrayList<>();




    public Brigada() {
    }

    public Brigada(String nombre, String descripcion) {
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


   


   // public List<Trabajador> getTrabajadores() {
      //  return trabajadores;
    //}

    //public void setTrabajadores(List<Trabajador> trabajadores) {
    //    this.trabajadores = trabajadores;
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
        final Brigada other = (Brigada) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Brigada{ nombre=" + nombre  +'}';
    }

}
