package service;

import interfaces.Consultable;
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

    // Getters utilizados por DatosCSV.JAVA

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

}