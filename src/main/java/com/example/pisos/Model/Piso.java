package com.example.pisos.Model;

import javax.persistence.*;

@Entity
@Table(name = "pisos")
public class Piso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPiso")
    private int idPiso;

    @Column(name = "direccion")
    private String direccion;

    @ManyToOne
    @JoinColumn(name = "idZona", referencedColumnName = "idZona")
    private Zona zona;

    public Piso() {
    }

    public Piso(String direccion, Zona zona) {
        this.direccion = direccion;
        this.zona = zona;
    }

    public Piso(int idPiso, String direccion, Zona zona) {
        this.idPiso = idPiso;
        this.direccion = direccion;
        this.zona = zona;
    }

    // Getters y setters

    public int getIdPiso() {
        return idPiso;
    }

    public void setIdPiso(int idPiso) {
        this.idPiso = idPiso;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Zona getZona() {
        return zona;
    }

    public void setZona(Zona zona) {
        this.zona = zona;
    }

    // Para mostrar en la tabla o debugging
    @Override
    public String toString() {
        return direccion + " (" + zona.getNombre() + ")";
    }
}
