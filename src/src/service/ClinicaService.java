package service;

import model.Paciente;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ClinicaService {
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

    // getter para DatosCSV
    public List<Paciente> getPacientes() {
        return pacientes;
    }

    public List<Medico> getMedicos() {
        return medicos;
    }

    public List<Turno> getTurnos() {
        return turnos;
    }

    @Override
    public List<Turno> listarTurnosDelDia(LocalDate fecha) {

        List<Turno> turnosDelDia = new ArrayList<>();

        for (Turno turno : turnos) {

            LocalDate fechaTurno = turno.getFechaHora().toLocalDate();

            if(fechaTurno.equals(fecha)) {
                turnosDelDia.add(turno);
            }
        }

        turnosDelDia.sort(Comparator.comparing(Turno::getFechaHora));

        return turnosDelDia;
    }

    @Override
    public List<Turno> buscarPorMedico(Medico medico) {
        List<Turno> turnosDelMedico = new ArrayList<>();

        for (Turno turno : turnos) {
            if (turno.getMedico().equals(medico)){
                turnosDelMedico.add(turno);
            }
        }

        return turnosDelMedico;
    }

    @Override
    public List<Turno> buscarPorPaciente(Paciente paciente) {
        List<Turno> turnosDelPaciente = new ArrayList<>();

        for(Turno turno : turnos) {
            if(turno.getPaciente().equals(paciente)){
                turnosDelPaciente.add(turno);
            }
        }

        return turnosDelPaciente;
    }

    // ENCARGADO: MIGUEL TAREA: implementar Consultable y declarar las listas privadas





    private List<Paciente> pacientes = new ArrayList<>();

    // Registrar paciente
    public void registrarPaciente(Paciente p) {

        if (!p.esValido()) {
            System.out.println("Error: los datos del paciente no son válidos.");
            return;
        }

        if (pacientes.contains(p)) {
            System.out.println("Error: ya existe un paciente con la misma cédula.");
            return;
        }

        int nuevoId = 1;

        for (Paciente paciente : pacientes) {
            if (paciente.getId() >= nuevoId) {
                nuevoId = paciente.getId() + 1;
            }
        }

        p.setId(nuevoId);
        pacientes.add(p);

        System.out.println("Paciente registrado correctamente: " + p);
    }

    // Buscar paciente por cédula
    public Paciente buscarPorCedula(String cedula) {

        for (Paciente paciente : pacientes) {
            if (paciente.getCedula().equals(cedula)) {
                return paciente;
            }
        }

        return null;
    }

    // Listar pacientes
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

    // Getter para DatosCSV
    public List<Paciente> getPacientes() {
        return pacientes;
    }


    public void registrarMedico(Medico m) {

        if (m.esValido() == false) {
            System.out.println("Los datos del medico no estan completos o son invalidos.");
            return;
        }

        if (medicos.contains(m)) {
            System.out.println("Ya existe un medico registrado con ese mismo nombre y apellido.");
            return;
        }

        int idMasAlto = 0;
        for (Medico medicoGuardado : medicos) {
            if (medicoGuardado.getId() > idMasAlto) {
                idMasAlto = medicoGuardado.getId();
            }
        }

        m.setId(idMasAlto + 1);

        medicos.add(m);

        System.out.println("Medico registrado exitosamente: " + m.toString());
    }

    public Medico buscarPorNombreApellido(String nombre, String apellido) {
        for (Medico m : medicos) {
            if (m.getNombre().equalsIgnoreCase(nombre) && m.getApellido().equalsIgnoreCase(apellido)) {
                return m;
            }
        }
        return null;
    }

    public void listarMedicos() {
        if (medicos.isEmpty()) {
            System.out.println("No hay medicos registrados.");
            return;
        }

        List<Medico> copiaMedicos = new ArrayList<>();
        for (Medico m : medicos) {
            copiaMedicos.add(m);
        }

        copiaMedicos.sort(
                Comparator.comparing(Medico::getEspecialidad)
                        .thenComparing(Medico::getApellido)
        );
    }
}