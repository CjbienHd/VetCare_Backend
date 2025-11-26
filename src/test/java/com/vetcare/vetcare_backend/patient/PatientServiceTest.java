package com.vetcare.vetcare_backend.patient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class PatientServiceTest {

    private PatientRepository repo;
    private PatientService service;

    @BeforeEach
    void setUp() {
        repo = mock(PatientRepository.class);
        service = new PatientService(repo);
    }

    @Test
    void getAll_delegaEnRepositorio() {
        Patient p1 = new Patient();
        p1.setId("1");
        Patient p2 = new Patient();
        p2.setId("2");

        when(repo.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<Patient> result = service.getAll();

        assertThat(result).hasSize(2);
        verify(repo).findAll();
    }

    @Test
    void create_generatesIdWhenMissing() {
        Patient input = new Patient(); // id null
        when(repo.save(any(Patient.class))).thenAnswer(inv -> inv.getArgument(0));

        Patient saved = service.create(input);

        assertThat(saved.getId()).isNotNull();
        verify(repo).save(any(Patient.class));
    }

    @Test
    void update_returnsNullWhenNotFound() {
        when(repo.findById("abc")).thenReturn(Optional.empty());

        Patient updated = service.update("abc", new Patient());

        assertThat(updated).isNull();
        verify(repo, never()).save(any());
    }

    @Test
    void update_updatesFieldsCorrectly() {
        Patient existing = new Patient();
        existing.setId("abc");
        existing.setNombre("Luna");
        existing.setEspecie("Perro");
        existing.setRaza("Labrador");
        existing.setTutor("Juan");

        Patient data = new Patient();
        data.setNombre("Luna Modificada");
        data.setEspecie("Perro");
        data.setRaza("Golden");
        data.setTutor("Carlos");

        when(repo.findById("abc")).thenReturn(Optional.of(existing));
        when(repo.save(any(Patient.class))).thenAnswer(inv -> inv.getArgument(0));

        Patient result = service.update("abc", data);

        assertThat(result.getNombre()).isEqualTo("Luna Modificada");
        assertThat(result.getRaza()).isEqualTo("Golden");
        assertThat(result.getTutor()).isEqualTo("Carlos");
        verify(repo).save(existing);
    }

    @Test
    void delete_returnsFalseWhenNotExists() {
        when(repo.existsById("missing")).thenReturn(false);

        boolean deleted = service.delete("missing");

        assertThat(deleted).isFalse();
        verify(repo, never()).deleteById(anyString());
    }

    @Test
    void delete_returnsTrueWhenExists() {
        when(repo.existsById("abc")).thenReturn(true);

        boolean deleted = service.delete("abc");

        assertThat(deleted).isTrue();
        verify(repo).deleteById("abc");
    }
}
