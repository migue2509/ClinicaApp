package service;

import model.Paciente;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ClinicaService {

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
}