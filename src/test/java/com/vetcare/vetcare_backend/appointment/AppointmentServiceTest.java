package com.vetcare.vetcare_backend.appointment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class AppointmentServiceTest {

    private AppointmentRepository repo;
    private AppointmentService service;

    @BeforeEach
    void setUp() {
        repo = mock(AppointmentRepository.class);
        service = new AppointmentService(repo);
    }

    @Test
    void findAll_delegaEnRepositorio() {
        Appointment a1 = new Appointment();
        a1.setId("1");
        Appointment a2 = new Appointment();
        a2.setId("2");

        when(repo.findAll()).thenReturn(Arrays.asList(a1, a2));

        List<Appointment> result = service.findAll();

        assertThat(result).hasSize(2);
        verify(repo).findAll();
    }

    @Test
    void create_generatesIdWhenMissing() {
        Appointment input = new Appointment(); // id null
        when(repo.save(any(Appointment.class))).thenAnswer(inv -> inv.getArgument(0));

        Appointment saved = service.create(input);

        assertThat(saved.getId()).isNotNull();
        verify(repo).save(any(Appointment.class));
    }

    @Test
    void update_returnsNullWhenNotFound() {
        when(repo.findById("abc")).thenReturn(Optional.empty());

        Appointment updated = service.update("abc", new Appointment());

        assertThat(updated).isNull();
        verify(repo, never()).save(any());
    }

    @Test
    void update_updatesFieldsCorrectly() {
        Appointment existing = new Appointment();
        existing.setId("abc");
        existing.setPatientName("Luna");
        existing.setMotivo("Vacuna");
        existing.setFechaHora("2025-01-01");
        existing.setNotas("nada");
        existing.setEstado("Programada");

        Appointment data = new Appointment();
        data.setPatientName("Luna Modificada");
        data.setMotivo("Control");
        data.setFechaHora("2025-02-02");
        data.setNotas("observaciones");
        data.setEstado("Completada");

        when(repo.findById("abc")).thenReturn(Optional.of(existing));
        when(repo.save(any(Appointment.class))).thenAnswer(inv -> inv.getArgument(0));

        Appointment result = service.update("abc", data);

        assertThat(result.getPatientName()).isEqualTo("Luna Modificada");
        assertThat(result.getMotivo()).isEqualTo("Control");
        assertThat(result.getEstado()).isEqualTo("Completada");
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
