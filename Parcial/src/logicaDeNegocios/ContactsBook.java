package logicaDeNegocios;

import ui.*;
import data.Contact;
import java.util.TreeMap;
import static ui.ContactsBookUI.*;

public class ContactsBook{

    private static final TreeMap<String, Contact> contactos = new TreeMap<>();

    static String nombre;
    static String apellido;
    static String[] correos;
    static int telefono;
    static long celular;
    static String direccion;

    public static void addContact(){

        nombre = getString("Nombre", 3, 10, false);
        apellido = getString("Apellido", 3, 10, false);
        correos = getCorreos("Correos electrónicos");
        telefono = (int) getNumber("Telefono", 7);
        celular = getNumber("Telefono celular", 10);
        direccion = getString("Direccion", 10, 30, true);
            
        Contact e = new Contact(nombre, apellido, correos, telefono, celular, direccion);
        contactos.put(e.getKey(), e);
        
    }
    
    public static void removeContact(){
        printKeys(contactos);
        contactos.remove(getKey(contactos, "remover"));
    }
    
    public static void updateContact(){
        printKeys(contactos);
        contactos.remove(getKey(contactos, "actualizar"));
        addContact();
    }
    
    public static void printAllContacts(){
        for (Contact contacto : contactos.values()) {
            ContactsBookUI.printContact(contacto);
        }
    }
    
    public static void printContactsKeys(){
        printKeys(contactos);
        printContact(contactos.get(getKey(contactos, "ver")));
    }
    
    public static void main(String[] args) {
        while(args.length != 0){
            menu();
        }
        GUI gui = new GUI();
    }
}