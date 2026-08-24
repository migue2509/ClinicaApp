package interfaces;

public interface Registrable {

    // Retorna los datos del objeto listos para la consola.
    String getDatosRegistro();

    // True si todos los atributos obligatorios estan correctamene definidos.
    boolean esValido();
}
