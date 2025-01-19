package es.uvigo.mei.pedidos.entidades;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Trabajador implements Serializable{    
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }    
    
    private String DNI;  
    public String getDNI() {
        return DNI;
    }
    public void setDNI(String DNI) {
        this.DNI = DNI;
    }
    
    private String nombre;
    private String cargo;
    private String nivelEducativo;


    //@JsonIgnore
    @ManyToOne   
    @JoinColumn(name = "contrato_id")
    private Contrato contrato;

    //@JsonIgnore     
    @ManyToOne
    @JoinColumn(name = "brigada_id")
    private Brigada brigada;
    
    public Trabajador() {
    }

    public Trabajador(String DNI,String nombre, String cargo, String nivelEducativo,Contrato contrato,Brigada brigada) {
        this.DNI=DNI;
        this.nombre = nombre;
        this.cargo = cargo;
        this.nivelEducativo = nivelEducativo;
        this.contrato=contrato;
        this.brigada=brigada;
    }




    
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getCargo() {
        return cargo;
    }
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
    public String getNivelEducativo() {
        return nivelEducativo;
    }
    public void setNivelEducativo(String nivelEducativo) {
        this.nivelEducativo = nivelEducativo;
    }

    public Contrato getContrato() {
        return contrato;
    }
    public void setContrato(Contrato contrato) {
        this.contrato = contrato;
    }
    
    public Brigada getBrigada() {
        return brigada;
    }
    public void setBrigada(Brigada brigada) {
        this.brigada = brigada;
    }
    
    @Override
    public int hashCode() {
        int hash = Objects.hashCode(this.DNI);
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
        final Trabajador other = (Trabajador) obj;
        if (!Objects.equals(this.DNI, other.DNI)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Trabajador{" + "DNI=" + DNI + ", nombre=" + nombre  +"Cargo= "+cargo +"Nivel Educativo= "+nivelEducativo +'}';
    }
    
}
