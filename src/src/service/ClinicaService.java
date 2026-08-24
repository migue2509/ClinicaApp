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








    //ENCARGADA: MARTHA CARO TAREA: METODOS DE PACIENTE








    // ENCARGADO: MICHAEL TAREA: METODOS DE MEDICO






    // ENCARGADO: JHOHAN TAREA: METODOS DE TURNO




}
