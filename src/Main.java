import Datos.DatosCSV;
import model.Especialidad;
import model.EstadoTurno;
import model.Medico;
import model.Paciente;
import model.Turno;
import service.ClinicaService;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        ClinicaService servicio = new ClinicaService();
        Scanner scanner = new Scanner(System.in);

        DatosCSV.cargar(servicio);

        int opcion;

        do {
            mostrarMenu();
            opcion = leerEntero(scanner, "Seleccione una opción: ");

            try {
                switch (opcion) {
                    case 1:
                        registrarPaciente(scanner, servicio);
                        break;

                    case 2:
                        registrarMedico(scanner, servicio);
                        break;

                    case 3:
                        asignarTurno(scanner, servicio);
                        break;

                    case 4:
                        listarTurnosDelDia(scanner, servicio);
                        break;

                    case 5:
                        cancelarTurno(scanner, servicio);
                        break;

                    case 6:
                        buscarTurnosPorMedico(scanner, servicio);
                        break;

                    case 7:
                        buscarTurnosPorPaciente(scanner, servicio);
                        break;

                    case 8:
                        cambiarEstadoTurno(scanner, servicio);
                        break;

                    case 9:
                        servicio.listarPacientes();
                        break;

                    case 10:
                        servicio.listarMedicos();
                        break;

                    case 0:
                        System.out.println("Cerrando el sistema...");
                        break;

                    default:
                        System.out.println("Opción inválida.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }

        } while (opcion != 0);

        DatosCSV.guardar(servicio);
        scanner.close();

        System.out.println("Hasta pronto. Datos guardados.");
    }

    private static void mostrarMenu() {
        System.out.println("\n========== CLINICAAPP ==========");
        System.out.println("1. Registrar paciente");
        System.out.println("2. Registrar médico");
        System.out.println("3. Asignar turno");
        System.out.println("4. Listar turnos del día");
        System.out.println("5. Cancelar turno");
        System.out.println("6. Ver turnos por médico");
        System.out.println("7. Ver turnos por paciente");
        System.out.println("8. Cambiar estado de turno");
        System.out.println("9. Listar pacientes");
        System.out.println("10. Listar médicos");
        System.out.println("0. Salir");
        System.out.println("================================");
    }

    private static void registrarPaciente(
            Scanner scanner,
            ClinicaService servicio
    ) {
        System.out.println("\n--- Registrar paciente ---");

        String cedula = leerTexto(scanner, "Cédula: ");
        String nombre = leerTexto(scanner, "Nombre: ");
        String apellido = leerTexto(scanner, "Apellido: ");
        String telefono = leerTexto(scanner, "Teléfono: ");

        Paciente paciente = new Paciente(
                cedula,
                nombre,
                apellido,
                telefono
        );

        servicio.registrarPaciente(paciente);
    }

    private static void registrarMedico(
            Scanner scanner,
            ClinicaService servicio
    ) {
        System.out.println("\n--- Registrar médico ---");

        String nombre = leerTexto(scanner, "Nombre: ");
        String apellido = leerTexto(scanner, "Apellido: ");

        System.out.println(
                "Especialidades: GENERAL, PEDIATRIA, " +
                        "CARDIOLOGIA, URGENCIAS"
        );

        String textoEspecialidad = leerTexto(
                scanner,
                "Especialidad: "
        );

        Especialidad especialidad = Especialidad.valueOf(
                textoEspecialidad.toUpperCase()
        );

        Medico medico = new Medico(
                nombre,
                apellido,
                especialidad
        );

        servicio.registrarMedico(medico);
    }

    private static void asignarTurno(
            Scanner scanner,
            ClinicaService servicio
    ) {
        System.out.println("\n--- Asignar turno ---");

        String cedula = leerTexto(
                scanner,
                "Cédula del paciente: "
        );

        Paciente paciente = servicio.buscarPorCedula(cedula);

        if (paciente == null) {
            System.out.println("Paciente no encontrado.");
            return;
        }

        String nombreMedico = leerTexto(
                scanner,
                "Nombre del médico: "
        );

        String apellidoMedico = leerTexto(
                scanner,
                "Apellido del médico: "
        );

        Medico medico = servicio.buscarPorNombreApellido(
                nombreMedico,
                apellidoMedico
        );

        if (medico == null) {
            System.out.println("Médico no encontrado.");
            return;
        }

        LocalDateTime fechaHora = leerFechaHora(scanner);

        Turno turno = new Turno(
                paciente,
                medico,
                fechaHora
        );

        servicio.asignarTurno(turno);
    }

    private static void listarTurnosDelDia(
            Scanner scanner,
            ClinicaService servicio
    ) {
        System.out.println("\n--- Turnos del día ---");

        LocalDate fecha = leerFecha(scanner);

        List<Turno> turnos = servicio.listarTurnosDelDia(fecha);

        mostrarTurnos(turnos);
    }

    private static void cancelarTurno(
            Scanner scanner,
            ClinicaService servicio
    ) {
        System.out.println("\n--- Cancelar turno ---");

        int idTurno = leerEntero(
                scanner,
                "ID del turno: "
        );

        servicio.cancelarTurno(idTurno);
    }

    private static void buscarTurnosPorMedico(
            Scanner scanner,
            ClinicaService servicio
    ) {
        System.out.println("\n--- Turnos por médico ---");

        String nombre = leerTexto(
                scanner,
                "Nombre del médico: "
        );

        String apellido = leerTexto(
                scanner,
                "Apellido del médico: "
        );

        Medico medico = servicio.buscarPorNombreApellido(
                nombre,
                apellido
        );

        if (medico == null) {
            System.out.println("Médico no encontrado.");
            return;
        }

        List<Turno> turnos = servicio.buscarPorMedico(medico);

        mostrarTurnos(turnos);
    }

    private static void buscarTurnosPorPaciente(
            Scanner scanner,
            ClinicaService servicio
    ) {
        System.out.println("\n--- Turnos por paciente ---");

        String cedula = leerTexto(
                scanner,
                "Cédula del paciente: "
        );

        Paciente paciente = servicio.buscarPorCedula(cedula);

        if (paciente == null) {
            System.out.println("Paciente no encontrado.");
            return;
        }

        List<Turno> turnos = servicio.buscarPorPaciente(paciente);

        mostrarTurnos(turnos);
    }

    private static void cambiarEstadoTurno(
            Scanner scanner,
            ClinicaService servicio
    ) {
        System.out.println("\n--- Cambiar estado del turno ---");

        int idTurno = leerEntero(
                scanner,
                "ID del turno: "
        );

        System.out.println(
                "Estados: PENDIENTE, ATENDIDO, CANCELADO"
        );

        String textoEstado = leerTexto(
                scanner,
                "Nuevo estado: "
        );

        EstadoTurno nuevoEstado = EstadoTurno.valueOf(
                textoEstado.toUpperCase()
        );

        servicio.cambiarEstadoTurno(
                idTurno,
                nuevoEstado
        );
    }

    private static LocalDate leerFecha(Scanner scanner) {

        while (true) {
            int anio = leerEntero(scanner, "Año: ");
            int mes = leerEntero(scanner, "Mes: ");
            int dia = leerEntero(scanner, "Día: ");

            try {
                return LocalDate.of(anio, mes, dia);
            } catch (DateTimeException e) {
                System.out.println(
                        "Fecha inválida. Inténtalo nuevamente."
                );
            }
        }
    }

    private static LocalDateTime leerFechaHora(
            Scanner scanner
    ) {
        while (true) {
            int anio = leerEntero(scanner, "Año: ");
            int mes = leerEntero(scanner, "Mes: ");
            int dia = leerEntero(scanner, "Día: ");
            int hora = leerEntero(scanner, "Hora: ");
            int minuto = leerEntero(scanner, "Minuto: ");

            try {
                return LocalDateTime.of(
                        anio,
                        mes,
                        dia,
                        hora,
                        minuto
                );
            } catch (DateTimeException e) {
                System.out.println(
                        "Fecha u hora inválida. Inténtalo nuevamente."
                );
            }
        }
    }

    private static int leerEntero(
            Scanner scanner,
            String mensaje
    ) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine();

            try {
                return Integer.parseInt(entrada.trim());
            } catch (NumberFormatException e) {
                System.out.println(
                        "Debes ingresar un número válido."
                );
            }
        }
    }

    private static String leerTexto(
            Scanner scanner,
            String mensaje
    ) {
        System.out.print(mensaje);
        return scanner.nextLine().trim();
    }

    private static void mostrarTurnos(List<Turno> turnos) {

        if (turnos.isEmpty()) {
            System.out.println("No se encontraron turnos.");
            return;
        }

        for (Turno turno : turnos) {
            System.out.println(turno);
        }
    }
}