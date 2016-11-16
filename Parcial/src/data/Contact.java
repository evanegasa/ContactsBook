package data;

import java.util.Arrays;

public class Contact{

    private final String nombre;
    private final String apellido;
    private final String[] correos;
    private final int telefono;
    private final long celular;
    private final String direccion;

    public Contact(String nombre, String apellido, String[] correos, int telefono, long celular, String direccion){
        this.nombre = nombre.substring(0, 1).toUpperCase() + nombre.substring(1);
        this.apellido = apellido.substring(0, 1).toUpperCase() + apellido.substring(1);
        this.correos = correos;
        this.telefono = telefono;
        this.celular = celular;
        this.direccion = direccion;
    }
    
    public String getKey() {
        return nombre + " " + apellido;
    }

    @Override
    public String toString() {
        return "Nombre: " + nombre + ", Apellido: " + apellido + ", Correos: " + Arrays.toString(correos) + ", Telefono: " + telefono + ", Celular: " + celular + ", Direccion: " + direccion;
    }
}