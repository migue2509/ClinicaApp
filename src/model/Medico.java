package model;

import interfaces.Registrable;

import java.util.Locale;
import java.util.Objects;

public class Medico implements Registrable {

    private int id;
    private String nombre;
    private String apellido;
    private Especialidad especialidad;

    // Constructor sin ID para médicos nuevos
    public Medico(
            String nombre,
            String apellido,
            Especialidad especialidad
    ) {
        setNombre(nombre);
        setApellido(apellido);
        setEspecialidad(especialidad);
    }

    // Constructor con ID para cargar médicos desde CSV
    public Medico(
            int id,
            String nombre,
            String apellido,
            Especialidad especialidad
    ) {
        this.id = id;
        setNombre(nombre);
        setApellido(apellido);
        setEspecialidad(especialidad);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El nombre no puede ser nulo ni vacío."
            );
        }

        this.nombre = nombre.trim();
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El apellido no puede ser nulo ni vacío."
            );
        }

        this.apellido = apellido.trim();
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        if (especialidad == null) {
            throw new IllegalArgumentException(
                    "La especialidad no puede ser nula."
            );
        }

        this.especialidad = especialidad;
    }

    @Override
    public String getDatosRegistro() {
        return toString();
    }

    @Override
    public boolean esValido() {
        return nombre != null
                && !nombre.isEmpty()
                && apellido != null
                && !apellido.isEmpty()
                && especialidad != null;
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }

        if (objeto == null || getClass() != objeto.getClass()) {
            return false;
        }

        Medico otroMedico = (Medico) objeto;

        return nombre.equalsIgnoreCase(otroMedico.nombre)
                && apellido.equalsIgnoreCase(otroMedico.apellido);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                nombre.toLowerCase(Locale.ROOT),
                apellido.toLowerCase(Locale.ROOT)
        );
    }

    @Override
    public String toString() {
        return "Dr. " + nombre + " " + apellido
                + " - " + especialidad;
    }
}