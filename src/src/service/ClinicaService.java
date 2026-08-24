 
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
  
 
    // ENCARGADO: JHOHAN TAREA: METODOS DE TURNO
    public void asignarTurno(Turno turno) {


        boolean pacienteExiste = false;
        for (Paciente p : pacientes) {
            if (p.getCedula().equals(turno.getPaciente().getCedula())) {
                pacienteExiste = true;
            }
        }


        boolean medicoExiste = false;
        for (Medico m : medicos) {
            if (m.getNombre().equalsIgnoreCase(turno.getMedico().getNombre())
                    && m.getApellido().equalsIgnoreCase(turno.getMedico().getApellido())) {
                medicoExiste = true;
            }
        }


        boolean turnoRepetido = false;
        for (Turno actual : turnos) {
            if (actual.equals(turno)) {
                turnoRepetido = true;
            }
        }

        if (!pacienteExiste) {
            System.out.println("el paciente no está registrado.");
        } else if (!medicoExiste) {
            System.out.println(" el médico no está registrado.");
        } else if (turnoRepetido) {
            System.out.println(" el médico ya tiene un turno assginado en esa misma fecha y hora.");
        } else {
            int id = 0;
            for (Turno actual : turnos) {
                if (actual.getId() > id) {
                    id = actual.getId();
                }
            }
            turno.setId(id + 1);
            turnos.add(turno);
            System.out.println("Turno asignado satisfactoriamente: " + turno);
        }
    }

    public void cancelarTurno(int idBuscado) {

        Turno encontrado = null;
        for (Turno actual : turnos) {
            if (actual.getId() == idBuscado) {
                encontrado = actual;
            }
        }

        if (encontrado == null) {
            System.out.println("Turno no encontrado.");
        } else if (encontrado.getEstado() == EstadoTurno.ATENDIDO
                || encontrado.getEstado() == EstadoTurno.CANCELADO) {
            System.out.println("No se puede cancelar este turno.");
        } else {
            encontrado.setEstado(EstadoTurno.CANCELADO);
            System.out.println("Turno cancelado: " + encontrado);
        }
    }

    public void cambiarEstadoTurno(int idBuscado, EstadoTurno nuevoEstado) {

        Turno encontrado = null;
        for (Turno actual : turnos) {
            if (actual.getId() == idBuscado) {
                encontrado = actual;
            }
        }

        if (encontrado == null) {
            System.out.println("Turno no encontrado.");
        } else {
            encontrado.setEstado(nuevoEstado);
            System.out.println("Estado actualizado: " + encontrado);
        }
    }
}
 
