package model;

import java.time.LocalDateTime;
import java.util.Objects;

public class Turno {

    private int id;
    private Paciente paciente;
    private Medico medico;
    private LocalDateTime fechaHora;
    private EstadoTurno estado;

    // Constructor sin ID para turnos nuevos
    public Turno(
            Paciente paciente,
            Medico medico,
            LocalDateTime fechaHora
    ) {
        setPaciente(paciente);
        setMedico(medico);
        setFechaHora(fechaHora);
        this.estado = EstadoTurno.PENDIENTE;
    }

    // Constructor con ID para cargar turnos desde CSV
    public Turno(
            int id,
            Paciente paciente,
            Medico medico,
            LocalDateTime fechaHora,
            EstadoTurno estado
    ) {
        this.id = id;
        setPaciente(paciente);
        setMedico(medico);
        setFechaHora(fechaHora);
        setEstado(estado);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        if (paciente == null) {
            throw new IllegalArgumentException(
                    "El paciente no puede ser nulo."
            );
        }

        this.paciente = paciente;
    }

    public Medico getMedico() {
        return medico;
    }

    public void setMedico(Medico medico) {
        if (medico == null) {
            throw new IllegalArgumentException(
                    "El médico no puede ser nulo."
            );
        }

        this.medico = medico;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        if (fechaHora == null) {
            throw new IllegalArgumentException(
                    "La fecha y hora no pueden ser nulas."
            );
        }

        if (fechaHora.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException(
                    "La fecha y hora no pueden estar en el pasado."
            );
        }

        this.fechaHora = fechaHora;
    }

    public EstadoTurno getEstado() {
        return estado;
    }

    public void setEstado(EstadoTurno estado) {
        if (estado == null) {
            throw new IllegalArgumentException(
                    "El estado no puede ser nulo."
            );
        }

        this.estado = estado;
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }

        if (objeto == null || getClass() != objeto.getClass()) {
            return false;
        }

        Turno otroTurno = (Turno) objeto;

        return medico.equals(otroTurno.medico)
                && fechaHora.equals(otroTurno.fechaHora);
    }

    @Override
    public int hashCode() {
        return Objects.hash(medico, fechaHora);
    }

    @Override
    public String toString() {
        return "[" + estado + "] "
                + paciente.getNombre() + " "
                + paciente.getApellido()
                + " — Dr. "
                + medico.getNombre() + " "
                + medico.getApellido()
                + " (" + medico.getEspecialidad() + ")"
                + " — " + fechaHora;
    }
}