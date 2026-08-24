package model;

import interfaces.Registrable;

public class Medico implements Registrable {
    private int id;
    private String nombre;
    private String apellido;
    private Especialidad especialidad;

    public Medico(int id, String nombre, String apellido, Especialidad especialidad) {
        this.id = id;
        setNombre(nombre);
        setApellido(apellido);
        setEspecialidad(especialidad);
    }

    public Medico(String nombre, String apellido, Especialidad especialidad) {
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
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacio.");
        }
        this.nombre = nombre.trim();
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido es obligatorio.");
        }
        this.apellido = apellido.trim();
    }

    public Especialidad getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(Especialidad especialidad) {
        if (especialidad == null) {
            throw new IllegalArgumentException("Tiene que elegir una especialidad valida.");
        }
        this.especialidad = especialidad;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        Medico otroMedico = (Medico) obj;

        if (this.nombre.equalsIgnoreCase(otroMedico.nombre) && this.apellido.equalsIgnoreCase(otroMedico.apellido)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(
                nombre.toLowerCase(),
                apellido.toLowerCase()
        );
    }

    @Override
    public String toString() {
        return "Dr. " + nombre + " " + apellido + " - " + especialidad;
    }

    @Override
    public String getDatosRegistro() {
        return toString();
    }

    @Override
    public boolean esValido() {
        if (this.nombre != null && !this.nombre.isEmpty() &&
                this.apellido != null && !this.apellido.isEmpty() &&
                this.especialidad != null) {
            return true;
        }
        return false;
    }
}
