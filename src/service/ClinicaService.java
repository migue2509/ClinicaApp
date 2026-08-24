package service;

import interfaces.Consultable;
import model.EstadoTurno;
import model.Medico;
import model.Paciente;
import model.Turno;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ClinicaService implements Consultable {

    private List<Paciente> pacientes = new ArrayList<>();
    private List<Medico> medicos = new ArrayList<>();
    private List<Turno> turnos = new ArrayList<>();

    // Getters utilizados por DatosCSV

    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public List<Medico> getMedicos() {
        return medicos;
    }

    public List<Turno> getTurnos() {
        return turnos;
    }

    // Métodos de Consultable

    @Override
    public List<Turno> listarTurnosDelDia(LocalDate fecha) {

        List<Turno> turnosDelDia = new ArrayList<>();

        for (Turno turno : turnos) {
            LocalDate fechaTurno =
                    turno.getFechaHora().toLocalDate();

            if (fechaTurno.equals(fecha)) {
                turnosDelDia.add(turno);
            }
        }

        turnosDelDia.sort(
                Comparator.comparing(Turno::getFechaHora)
        );

        return turnosDelDia;
    }

    @Override
    public List<Turno> buscarPorMedico(Medico medico) {

        List<Turno> turnosDelMedico = new ArrayList<>();

        for (Turno turno : turnos) {
            if (turno.getMedico().equals(medico)) {
                turnosDelMedico.add(turno);
            }
        }

        return turnosDelMedico;
    }

    @Override
    public List<Turno> buscarPorPaciente(Paciente paciente) {

        List<Turno> turnosDelPaciente = new ArrayList<>();

        for (Turno turno : turnos) {
            if (turno.getPaciente().equals(paciente)) {
                turnosDelPaciente.add(turno);
            }
        }

        return turnosDelPaciente;
    }

    // Métodos de Paciente

    public void registrarPaciente(Paciente paciente) {

        if (!paciente.esValido()) {
            System.out.println(
                    "Error: los datos del paciente no son válidos."
            );
            return;
        }

        if (pacientes.contains(paciente)) {
            System.out.println(
                    "Error: ya existe un paciente con la misma cédula."
            );
            return;
        }

        int nuevoId = 1;

        for (Paciente pacienteRegistrado : pacientes) {
            if (pacienteRegistrado.getId() >= nuevoId) {
                nuevoId = pacienteRegistrado.getId() + 1;
            }
        }

        paciente.setId(nuevoId);
        pacientes.add(paciente);

        System.out.println(
                "Paciente registrado correctamente: " + paciente
        );
    }

    public Paciente buscarPorCedula(String cedula) {

        for (Paciente paciente : pacientes) {
            if (paciente.getCedula().equals(cedula)) {
                return paciente;
            }
        }

        return null;
    }

    public void listarPacientes() {

        if (pacientes.isEmpty()) {
            System.out.println("No hay pacientes registrados.");
            return;
        }

        List<Paciente> copia = new ArrayList<>(pacientes);

        copia.sort(
                Comparator.comparing(Paciente::getApellido)
                        .thenComparing(Paciente::getNombre)
        );

        for (Paciente paciente : copia) {
            System.out.println(paciente);
        }
    }

    // Métodos de Médico

    public void registrarMedico(Medico medico) {

        if (!medico.esValido()) {
            System.out.println(
                    "Error: los datos del médico no son válidos."
            );
            return;
        }

        if (medicos.contains(medico)) {
            System.out.println(
                    "Error: ya existe un médico con ese nombre y apellido."
            );
            return;
        }

        int nuevoId = 1;

        for (Medico medicoRegistrado : medicos) {
            if (medicoRegistrado.getId() >= nuevoId) {
                nuevoId = medicoRegistrado.getId() + 1;
            }
        }

        medico.setId(nuevoId);
        medicos.add(medico);

        System.out.println(
                "Médico registrado correctamente: " + medico
        );
    }

    public Medico buscarPorNombreApellido(
            String nombre,
            String apellido
    ) {
        for (Medico medico : medicos) {
            if (
                    medico.getNombre().equalsIgnoreCase(nombre)
                            && medico.getApellido().equalsIgnoreCase(apellido)
            ) {
                return medico;
            }
        }

        return null;
    }

    public void listarMedicos() {

        if (medicos.isEmpty()) {
            System.out.println("No hay médicos registrados.");
            return;
        }

        List<Medico> copia = new ArrayList<>(medicos);

        copia.sort(
                Comparator.comparing(Medico::getEspecialidad)
                        .thenComparing(Medico::getApellido)
        );

        for (Medico medico : copia) {
            System.out.println(medico);
        }
    }

    // Métodos de Turno

    public void asignarTurno(Turno turno) {

        Paciente pacienteEncontrado = buscarPorCedula(
                turno.getPaciente().getCedula()
        );

        if (pacienteEncontrado == null) {
            System.out.println(
                    "Error: el paciente no está registrado."
            );
            return;
        }

        Medico medicoEncontrado = buscarPorNombreApellido(
                turno.getMedico().getNombre(),
                turno.getMedico().getApellido()
        );

        if (medicoEncontrado == null) {
            System.out.println(
                    "Error: el médico no está registrado."
            );
            return;
        }

        if (turnos.contains(turno)) {
            System.out.println(
                    "Error: el médico ya tiene un turno en esa fecha y hora."
            );
            return;
        }

        int nuevoId = 1;

        for (Turno turnoRegistrado : turnos) {
            if (turnoRegistrado.getId() >= nuevoId) {
                nuevoId = turnoRegistrado.getId() + 1;
            }
        }

        turno.setId(nuevoId);
        turnos.add(turno);

        System.out.println(
                "Turno asignado correctamente: " + turno
        );
    }

    public void cancelarTurno(int idTurno) {

        Turno turnoEncontrado = buscarTurnoPorId(idTurno);

        if (turnoEncontrado == null) {
            System.out.println("Turno no encontrado.");
            return;
        }

        if (
                turnoEncontrado.getEstado() == EstadoTurno.ATENDIDO
                        || turnoEncontrado.getEstado()
                        == EstadoTurno.CANCELADO
        ) {
            System.out.println(
                    "El turno no se puede cancelar porque está "
                            + turnoEncontrado.getEstado() + "."
            );
            return;
        }

        turnoEncontrado.setEstado(EstadoTurno.CANCELADO);

        System.out.println(
                "Turno cancelado correctamente: " + turnoEncontrado
        );
    }

    public void cambiarEstadoTurno(
            int idTurno,
            EstadoTurno nuevoEstado
    ) {
        Turno turnoEncontrado = buscarTurnoPorId(idTurno);

        if (turnoEncontrado == null) {
            System.out.println("Turno no encontrado.");
            return;
        }

        turnoEncontrado.setEstado(nuevoEstado);

        System.out.println(
                "Estado actualizado correctamente: " + turnoEncontrado
        );
    }

    private Turno buscarTurnoPorId(int idTurno) {

        for (Turno turno : turnos) {
            if (turno.getId() == idTurno) {
                return turno;
            }
        }

        return null;
    }
}