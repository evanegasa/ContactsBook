package ui;

import java.util.Scanner;
import data.Contact;
import java.util.InputMismatchException;
import java.util.TreeMap;
import static logicaDeNegocios.ContactsBook.*;

public class ContactsBookUI {

    private static final Scanner sc = new Scanner(System.in);

    public static void menu() {
        System.out.println("---Bienvenido al Contacts Book---");
        System.out.println("1. Añadir Contacto");
        System.out.println("2. Eliminar Contacto");
        System.out.println("3. Actualizar Contacto");
        System.out.println("4. Imprimir Todos los Contactos");
        System.out.println("5. Imprimir Contacto");
        System.out.println("6. Cerrar programa");

        int i = 0;
        while (true) {
            if (sc.hasNextInt()) {
                i = sc.nextInt();
                if (i > 6 || i < 1) {
                    System.err.println("Por favor elija un numero entre 1 y 6");
                } else {
                    break;
                }
            } else {
                System.err.println("Por favor elija un numero entre 1 y 6");
                sc.next();
            }
        }
        sc.nextLine();
        switch (i) {
            case 1:
                addContact();
                System.out.println("El contacto fue añadido exitosamente");
                break;
            case 2:
                removeContact();
                System.out.println("El contacto fue eliminado exitosamente");
                System.out.println("");
                break;
            case 3:
                updateContact();
                System.out.println("El contacto fue actualizado excitosamente");
                System.out.println("");
                break;
            case 4:
                printAllContacts();
                System.out.println("");
                break;
            case 5:
                printContactsKeys();
                System.out.println("");
                break;
            case 6:
                System.exit(0);
                System.out.println("");
                break;
        }
    }

    public static String getString(String atribute, int minValue, int maxValue, boolean acceptsNumbers) {
        String string = "";
        System.out.print(atribute + ": ");
        while (true) {
            string = sc.nextLine();
            if (string.length() > maxValue || string.length() < minValue) {
                System.err.print("La entrada debe tener entre " + minValue + " y " + maxValue + " letras ");
            } else if (!acceptsNumbers && string.matches(".*\\d.*")) {
                System.err.print("La entrada no debe contener numeros ");
            } else {
                break;
            }
        }
        return string;
    }

    public static long getNumber(String atribute, int length) {
        System.out.print(atribute + ": ");
        long numero;
        while (true) {
            while (true) {
                try {
                    numero = sc.nextLong();
                    break;
                } catch (InputMismatchException e) {
                    System.err.print("La entrada debe ser un numero ");
                    sc.nextLine();
                }
            }
            if (String.valueOf(numero).length() == length && numero >= 0) {
                break;
            }
            System.err.println("El numero debe tener " + length + " digitos");
        }
        sc.nextLine();
        return numero;
    }

    public static String[] getCorreos(String atribute) {
        System.out.println(atribute + ": ");
        System.out.print("Digite el numero de correos electronicos del usuario: ");

        String[] a = {};
        while (true) {
            try {
                a = new String[sc.nextInt()];
                break;
            } catch (NegativeArraySizeException e) {
                System.err.print("El numero debe ser positivo");
                sc.nextLine();
            }
        }
        sc.nextLine();

        for (int i = 0; i < a.length; i++) {
            while (true) {
                a[i] = getString(String.valueOf(i + 1), 11, 30, true);
                if (a[i].contains(".") && a[i].replaceAll("@", "").length() == a[i].length() - 1) {
                    break;
                }
                System.err.print("Correo debe contener al menos un punto y unicamente una arroba");
            }
        }
        return a;
    }

    public static String getKey(TreeMap<String, Contact> contactBook, String funcion) {
        System.out.println("Escriba el nombre y apellido del contacto que desea " + funcion);
        while (true) {
            String a = sc.nextLine();
            if (contactBook.containsKey(a)) {
                return a;
            }
            System.err.println("El contacto no existe, vuelva a intentarlo");
        }
    }

    public static void printContact(Contact contact) {
        System.out.println(contact);
    }

    public static void printKeys(TreeMap<String, Contact> contactBook) {
        for (Contact m : contactBook.values()) {
            System.out.println(m.getKey());
        }
    }
    
    
}