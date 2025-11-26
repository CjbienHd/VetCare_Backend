package com.vetcare.vetcare_backend.appointment;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AppointmentService {

    private final AppointmentRepository repo;

    public AppointmentService(AppointmentRepository repo) {
        this.repo = repo;
    }

    public List<Appointment> findAll() {
        return repo.findAll();
    }

    public Appointment findById(String id) {
        return repo.findById(id).orElse(null);
    }

    public Appointment create(Appointment data) {
        // Si no viene id desde Android, lo generamos aquí
        if (data.getId() == null || data.getId().isBlank()) {
            data.setId(UUID.randomUUID().toString());
        }
        return repo.save(data);
    }

    public Appointment update(String id, Appointment data) {
        Appointment existing = repo.findById(id).orElse(null);
        if (existing == null) return null;

        existing.setPatientName(data.getPatientName());
        existing.setMotivo(data.getMotivo());
        existing.setFechaHora(data.getFechaHora());
        existing.setNotas(data.getNotas());
        existing.setEstado(data.getEstado());

        return repo.save(existing);
    }

    public boolean delete(String id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }
}
