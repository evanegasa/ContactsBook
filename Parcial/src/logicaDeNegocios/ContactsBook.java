package logicaDeNegocios;

import ui.*;
import java.io.*;
import data.Contact;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import static ui.ContactsBookUI.*;

public class ContactsBook implements Serializable {

    private static TreeMap<String, Contact> contactos = new TreeMap<>();

    private static ObjectOutputStream output;
    private static ObjectInputStream input;
    
    
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
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        input = new ObjectInputStream(new FileInputStream("C:\\Users\\Estudiante\\Documents\\NetBeansProjects\\Parcial\\src\\data\\contacts.db"));
        
        try {
            contactos = (TreeMap<String, Contact>) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        while(args.length != 0){
            menu();
        }
        
        GUI gui = new GUI(contactos);
    }
    
    public static void saveContacts(TreeMap<String, Contact> a) throws IOException {
            
        output = new ObjectOutputStream(new FileOutputStream("C:\\Users\\Estudiante\\Documents\\NetBeansProjects\\Parcial\\src\\data\\contacts.db"));

        try {
            output.writeObject(a);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        output.close();
    }
}