public class ClinicaService {
    // ENCARGADO: MIGUEL TAREA: implementar Consultable y declarar las listas privadas








    //ENCARGADA: MARTHA CARO TAREA: METODOS DE PACIENTE








    // ENCARGADO: MICHAEL TAREA: METODOS DE MEDICO






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
