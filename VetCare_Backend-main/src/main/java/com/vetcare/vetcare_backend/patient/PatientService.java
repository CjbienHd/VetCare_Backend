package com.vetcare.vetcare_backend.patient;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PatientService {

    private final PatientRepository repo;

    public PatientService(PatientRepository repo) {
        this.repo = repo;
    }

    public List<Patient> getAll() {
        return repo.findAll();
    }

    public Patient getById(String id) {
        return repo.findById(id).orElse(null);
    }

    public Patient create(Patient p) {
        // si viene sin id desde el front, lo genero aquí
        if (p.getId() == null || p.getId().isBlank()) {
            p.setId(UUID.randomUUID().toString());
        }
        return repo.save(p);
    }

    public Patient update(String id, Patient data) {
        Patient existing = repo.findById(id).orElse(null);
        if (existing == null) return null;

        existing.setNombre(data.getNombre());
        existing.setEspecie(data.getEspecie());
        existing.setRaza(data.getRaza());
        existing.setTutor(data.getTutor());

        return repo.save(existing);
    }

    public boolean delete(String id) {
        if (!repo.existsById(id)) return false;
        repo.deleteById(id);
        return true;
    }
}
