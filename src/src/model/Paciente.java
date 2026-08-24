package model;

import java.util.Objects;

public class Paciente {

    private int id;
    private String cedula;
    private String nombre;
    private String apellido;
    private String telefono;

    // Constructor sin ID
    public Paciente(String cedula, String nombre, String apellido, String telefono) {
        setCedula(cedula);
        setNombre(nombre);
        setApellido(apellido);
        setTelefono(telefono);
    }

    // Constructor con ID
    public Paciente(int id, String cedula, String nombre, String apellido, String telefono) {
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
            throw new IllegalArgumentException("La cédula no puede ser nula ni vacía.");
        }
        this.cedula = cedula.trim();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo ni vacío.");
        }
        this.nombre = nombre.trim();
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede ser nulo ni vacío.");
        }
        this.apellido = apellido.trim();
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        if (telefono == null || !telefono.matches("^[0-9]{7,10}$")) {
            throw new IllegalArgumentException(
                    "El teléfono debe contener entre 7 y 10 dígitos."
            );
        }
        this.telefono = telefono;
    }

    public boolean esValido() {
        return cedula != null && !cedula.trim().isEmpty()
                && nombre != null && !nombre.trim().isEmpty()
                && apellido != null && !apellido.trim().isEmpty()
                && telefono != null && telefono.matches("^[0-9]{7,10}$");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof Paciente)) {
            return false;
        }

        Paciente paciente = (Paciente) o;
        return cedula.equals(paciente.cedula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cedula);
    }

    @Override
    public String toString() {
        return nombre + " " + apellido + " - " + cedula + " - " + telefono;
    }


}