package com.vetcare.vetcare_backend.appointment;

import jakarta.persistence.*;

@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @Column(length = 36)
    private String id; // mismo formato UUID String que en Android

    @Column(nullable = false)
    private String patientName; // nombre del paciente (texto plano)

    @Column(nullable = false)
    private String motivo;

    @Column(nullable = false)
    private String fechaHora; // por simplicidad, texto (ej: "2025-11-30 10:30")

    @Column
    private String notas;

    @Column(nullable = false)
    private String estado; // Programada, Completada, Cancelada, etc.

    public Appointment() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
