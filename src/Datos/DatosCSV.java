package Datos;

import model.Especialidad;
import model.EstadoTurno;
import model.Medico;
import model.Paciente;
import model.Turno;
import service.ClinicaService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DatosCSV {

    private static final String DIR = "datos/";

    private static final String F_PACIENTES =
            DIR + "pacientes.csv";

    private static final String F_MEDICOS =
            DIR + "medicos.csv";

    private static final String F_TURNOS =
            DIR + "turnos.csv";

    private static final DateTimeFormatter FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static void cargar(ClinicaService servicio) {
        new File(DIR).mkdirs();

        cargarPacientes(servicio);
        cargarMedicos(servicio);
        cargarTurnos(servicio);
    }

    private static void cargarPacientes(
            ClinicaService servicio
    ) {
        File archivo = new File(F_PACIENTES);

        if (!archivo.exists()) {
            return;
        }

        try (
                BufferedReader br =
                        new BufferedReader(
                                new FileReader(archivo)
                        )
        ) {
            String linea;

            while ((linea = br.readLine()) != null) {

                if (linea.isBlank()) {
                    continue;
                }

                // id,cedula,nombre,apellido,telefono
                String[] datos = linea.split(",", -1);

                Paciente paciente = new Paciente(
                        Integer.parseInt(datos[0].trim()),
                        datos[1].trim(),
                        datos[2].trim(),
                        datos[3].trim(),
                        datos[4].trim()
                );

                servicio.getPacientes().add(paciente);
            }

        } catch (IOException e) {
            System.out.println(
                    "Error al cargar pacientes: "
                            + e.getMessage()
            );
        }
    }

    private static void cargarMedicos(
            ClinicaService servicio
    ) {
        File archivo = new File(F_MEDICOS);

        if (!archivo.exists()) {
            return;
        }

        try (
                BufferedReader br =
                        new BufferedReader(
                                new FileReader(archivo)
                        )
        ) {
            String linea;

            while ((linea = br.readLine()) != null) {

                if (linea.isBlank()) {
                    continue;
                }

                // id,nombre,apellido,especialidad
                String[] datos = linea.split(",", -1);

                Medico medico = new Medico(
                        Integer.parseInt(datos[0].trim()),
                        datos[1].trim(),
                        datos[2].trim(),
                        Especialidad.valueOf(
                                datos[3].trim()
                        )
                );

                servicio.getMedicos().add(medico);
            }

        } catch (IOException e) {
            System.out.println(
                    "Error al cargar médicos: "
                            + e.getMessage()
            );
        }
    }

    private static void cargarTurnos(
            ClinicaService servicio
    ) {
        File archivo = new File(F_TURNOS);

        if (!archivo.exists()) {
            return;
        }

        try (
                BufferedReader br =
                        new BufferedReader(
                                new FileReader(archivo)
                        )
        ) {
            String linea;

            while ((linea = br.readLine()) != null) {

                if (linea.isBlank()) {
                    continue;
                }

                /*
                 * Formato:
                 * id,cedulaPaciente,nombreMedico,
                 * apellidoMedico,fechaHora,estado
                 */
                String[] datos = linea.split(",", -1);

                Paciente paciente =
                        servicio.buscarPorCedula(
                                datos[1].trim()
                        );

                Medico medico =
                        servicio.buscarPorNombreApellido(
                                datos[2].trim(),
                                datos[3].trim()
                        );

                if (paciente == null || medico == null) {
                    continue;
                }

                Turno turno = new Turno(
                        Integer.parseInt(datos[0].trim()),
                        paciente,
                        medico,
                        LocalDateTime.parse(
                                datos[4].trim(),
                                FMT
                        ),
                        EstadoTurno.valueOf(
                                datos[5].trim()
                        )
                );

                servicio.getTurnos().add(turno);
            }

        } catch (IOException e) {
            System.out.println(
                    "Error al cargar turnos: "
                            + e.getMessage()
            );
        }
    }

    public static void guardar(ClinicaService servicio) {
        new File(DIR).mkdirs();

        guardarPacientes(servicio.getPacientes());
        guardarMedicos(servicio.getMedicos());
        guardarTurnos(servicio.getTurnos());
    }

    private static void guardarPacientes(
            List<Paciente> pacientes
    ) {
        try (
                PrintWriter pw =
                        new PrintWriter(
                                new FileWriter(F_PACIENTES)
                        )
        ) {
            for (Paciente paciente : pacientes) {
                pw.println(
                        paciente.getId() + ","
                                + paciente.getCedula() + ","
                                + paciente.getNombre() + ","
                                + paciente.getApellido() + ","
                                + paciente.getTelefono()
                );
            }

        } catch (IOException e) {
            System.out.println(
                    "Error al guardar pacientes: "
                            + e.getMessage()
            );
        }
    }

    private static void guardarMedicos(
            List<Medico> medicos
    ) {
        try (
                PrintWriter pw =
                        new PrintWriter(
                                new FileWriter(F_MEDICOS)
                        )
        ) {
            for (Medico medico : medicos) {
                pw.println(
                        medico.getId() + ","
                                + medico.getNombre() + ","
                                + medico.getApellido() + ","
                                + medico.getEspecialidad()
                );
            }

        } catch (IOException e) {
            System.out.println(
                    "Error al guardar médicos: "
                            + e.getMessage()
            );
        }
    }

    private static void guardarTurnos(
            List<Turno> turnos
    ) {
        try (
                PrintWriter pw =
                        new PrintWriter(
                                new FileWriter(F_TURNOS)
                        )
        ) {
            for (Turno turno : turnos) {
                pw.println(
                        turno.getId() + ","
                                + turno.getPaciente().getCedula() + ","
                                + turno.getMedico().getNombre() + ","
                                + turno.getMedico().getApellido() + ","
                                + turno.getFechaHora().format(FMT) + ","
                                + turno.getEstado()
                );
            }

        } catch (IOException e) {
            System.out.println(
                    "Error al guardar turnos: "
                            + e.getMessage()
            );
        }
    }
}