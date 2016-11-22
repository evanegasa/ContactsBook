package data;

import java.io.Serializable;
import java.util.Arrays;

public class Contact implements Serializable{

    private static final long serialVersionUID = 1365869385288780493L;

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

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String[] getCorreos() {
        return correos;
    }

    public int getTelefono() {
        return telefono;
    }

    public long getCelular() {
        return celular;
    }

    public String getDireccion() {
        return direccion;
    }

    @Override
    public String toString() {
        return "<html>Nombre: " + nombre + "<br> Apellido: " + apellido + "<br> Correos: " + Arrays.toString(correos) + "<br> Telefono: " + telefono + "<br>Celular: " + celular + "<br> Direccion: " + direccion + "</html>";
    }
}