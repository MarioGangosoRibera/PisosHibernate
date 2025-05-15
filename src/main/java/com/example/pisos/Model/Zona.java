package com.example.pisos.Model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "zonas")
public class Zona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idZona")
    private int idZona;

    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "zona", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Piso> listaPisos;

    public Zona() {
    }

    public Zona(String nombre) {
        this.nombre = nombre;
    }

    public Zona(int idZona, String nombre) {
        this.idZona = idZona;
        this.nombre = nombre;
    }

    // Getters y setters

    public int getIdZona() {
        return idZona;
    }

    public void setIdZona(int idZona) {
        this.idZona = idZona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Piso> getListaPisos() {
        return listaPisos;
    }

    public void setListaPisos(List<Piso> listaPisos) {
        this.listaPisos = listaPisos;
    }

    @Override
    public String toString() {
        return nombre; // útil para que se muestre bien en el ComboBox
    }
}
