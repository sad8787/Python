package es.uvigo.mei.pedidos.entidades;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class Contrato implements Serializable {
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

   

    
    @Temporal(TemporalType.DATE)
    private Date inDate;
    @Temporal(TemporalType.DATE)
    private Date outDate;


    
    public Contrato() {
    }

    public Contrato(String nombre, String descripcion,Date inDate,Date outDate) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.outDate=outDate;
        this.inDate= inDate;
        
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

    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }

    public Date getinDate() {
        return inDate;
    }

    public void setOutDate(Date outDate) {
        this.outDate = outDate;
    }

    public Date getOutDate() {
        return outDate;
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
        final Contrato other = (Contrato) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Contrato{ nombre=" + nombre  +"Fecha de inicio= "+inDate +"fecha final= "+outDate +'}';
    }










}
