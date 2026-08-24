package model;

import interfaces.Registrable;

import java.util.Objects;

public class Paciente implements Registrable {

    private int id;
    private String cedula;
    private String nombre;
    private String apellido;
    private String telefono;

    // Constructor sin ID para pacientes nuevos
    public Paciente(
            String cedula,
            String nombre,
            String apellido,
            String telefono
    ) {
        setCedula(cedula);
        setNombre(nombre);
        setApellido(apellido);
        setTelefono(telefono);
    }

    // Constructor con ID para cargar pacientes desde CSV
    public Paciente(
            int id,
            String cedula,
            String nombre,
            String apellido,
            String telefono
    ) {
        this.id = id;
        setCedula(cedula);
        setNombre(nombre);
        setApellido(apellido);
        setTelefono(telefono);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        if (cedula == null || cedula.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "La cédula no puede ser nula ni vacía."
            );
        }

        this.cedula = cedula.trim();
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        if (
                telefono == null
                        || !telefono.matches("^[0-9]{7,10}$")
        ) {
            throw new IllegalArgumentException(
                    "El teléfono debe contener entre 7 y 10 dígitos."
            );
        }

        this.telefono = telefono;
    }

    @Override
    public String getDatosRegistro() {
        return toString();
    }

    @Override
    public boolean esValido() {
        return cedula != null
                && !cedula.isEmpty()
                && nombre != null
                && !nombre.isEmpty()
                && apellido != null
                && !apellido.isEmpty()
                && telefono != null
                && telefono.matches("^[0-9]{7,10}$");
    }

    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }

        if (objeto == null || getClass() != objeto.getClass()) {
            return false;
        }

        Paciente otroPaciente = (Paciente) objeto;

        return cedula.equals(otroPaciente.cedula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cedula);
    }

    @Override
    public String toString() {
        return nombre + " " + apellido
                + " - " + cedula
                + " - " + telefono;
    }
}