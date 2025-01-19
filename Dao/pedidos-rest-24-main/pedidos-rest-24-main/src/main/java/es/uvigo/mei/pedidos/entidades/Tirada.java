package es.uvigo.mei.pedidos.entidades;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Tirada {

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
    @ManyToOne
    @JoinColumn(name = "brigada_id")
    private Brigada brigada;


    //@JsonIgnore
    @ManyToOne
    @JoinColumn(name = "rotativa_id")
    private Rotativa rotativa;

    public Tirada() {
    }

    public Tirada(String nombre, String descripcion,Brigada brigada,Rotativa rotativa) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.brigada=brigada;
        this.rotativa=rotativa;
        
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
    
    public Brigada getBrigada() {
        return brigada;
    }
    public void setBrigada(Brigada brigada) {
        this.brigada = brigada;
    }

    public Rotativa getRotativa() {
        return rotativa;
    }
    public void setRotativa(Rotativa rotativa) {
        this.rotativa = rotativa;
    }

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
        final Tirada other = (Tirada) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Tirada{ " + "nombre=" + nombre +'}';
    }




}
