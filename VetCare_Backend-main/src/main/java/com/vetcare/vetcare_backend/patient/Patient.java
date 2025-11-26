package com.vetcare.vetcare_backend.patient;

import jakarta.persistence.*;

@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @Column(length = 36)
    private String id;      // mismo id que usas en Android (UUID en String)

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String especie; // "Perro", "Gato", etc.

    private String raza;

    @Column(nullable = false)
    private String tutor;

    public Patient() {
    }

    public Patient(String id, String nombre, String especie, String raza, String tutor) {
        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.tutor = tutor;
    }

    // Getters y setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getTutor() {
        return tutor;
    }

    public void setTutor(String tutor) {
        this.tutor = tutor;
    }
}
