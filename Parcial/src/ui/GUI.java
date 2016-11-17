package ui;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.regex.*;
import javax.swing.event.*;

import data.Contact;
import java.io.File;
import java.io.IOException;
import java.util.TreeMap;
import static ui.GUI.validateInput;

public class GUI {
    
    private static TreeMap<String, Contact> contactos = new TreeMap<>();

    static JFrame f = new JFrame("Contacts book");
    static JPanel menu = new JPanel();
    static JLabel errorLabel = new JLabel();
    static JFileChooser fc = new JFileChooser();
    static File selectedFile = new File("");
    static Color color = Color.LIGHT_GRAY;
    static Color backColor = Color.WHITE;
    static boolean canSend = true;

    public GUI(TreeMap<String, Contact> contactos) {
        this.contactos = contactos;
        init();
        menu();
    }

    public static void menu() {
        f.add(menu);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setSize(f.getWidth() + 80, f.getHeight() + 50);
        f.setVisible(true);
    }
    
    public static void init(){
        f.setLayout(new GridBagLayout());
        f.getContentPane().setBackground(backColor);
        f.setPreferredSize(new Dimension(300, 239));
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setResizable(true);
        
        JButton b1 = new JButton("Añadir Contacto");
            b1.setBackground(color);
            b1.addActionListener((ActionEvent ae) -> {
                addContact();
            });
        JButton b2 = new JButton("Eliminar Contacto");
            b2.setBackground(color);
            b2.addActionListener((ActionEvent ae) -> {
                printKeys(true, false);
            });
        JButton b3 = new JButton("Actualizar Contacto");
            b3.setBackground(color);
            b3.addActionListener((ActionEvent ae) -> {
                printKeys(true, true);
            });
        JButton b4 = new JButton("Imprimir todos los Contactos");
            b4.setBackground(color);
            b4.addActionListener((ActionEvent ae) -> {
                printData(null);
            });
        JButton b5 = new JButton("Imprimir Contacto");
            b5.setBackground(color);
            b5.addActionListener((ActionEvent ae) -> {
                printKeys(false, false);
            });
            
        JButton b6 = new JButton("Exportar Contactos");
            b6.setBackground(color);
            b6.addActionListener((ActionEvent ae) -> {
                fc.setCurrentDirectory(new File(System.getProperty("user.home")));
                int result = fc.showOpenDialog(menu);
                if (result == JFileChooser.APPROVE_OPTION) {
                    try {
                        logicaDeNegocios.ContactsBook.exportSelectedFile(contactos, fc.getSelectedFile());
                        System.out.println("Selected file: " + selectedFile.getAbsolutePath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    
                }
            });

        JButton b7 = new JButton("Salir");
            b7.setBackground(color);
            b7.addActionListener((ActionEvent ae) -> {
                try {
                    logicaDeNegocios.ContactsBook.saveContacts(contactos);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.exit(0);
            });

        menu.setLayout(new GridLayout(7, 3, 0, 10));
        menu.setBackground(backColor);

        menu.add(b1);
        menu.add(b2);
        menu.add(b3);
        menu.add(b4);
        menu.add(b5);
        menu.add(b6);
        menu.add(b7);
    }
    
    public static void addContact() {
        errorLabel.setForeground(f.getBackground());
        errorLabel.setText("");
        
        JPanel contactPanel = new JPanel();
            contactPanel.setBackground(backColor);
            contactPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();

        JTextField f1 = new JTextField(),
                   f2 = new JTextField(),
                   f3 = new JTextField(),
                   f4 = new JTextField(),
                   f5 = new JTextField(),
                   f6 = new JTextField();
        
        JTextField[] fields = {f1, f2, f3, f4, f5, f6};
        JLabel[] labels = {new JLabel("Nombres"), new JLabel("Apellidos"), new JLabel("Correo"), new JLabel("Telefono"), new JLabel("Celular"), new JLabel("Direccion")};
        
        f1.getDocument().addDocumentListener(new FieldListener(f1, "[a-zA-Z]+", 3, 10));
        f2.getDocument().addDocumentListener(new FieldListener(f2, "[a-zA-Z]+", 3, 10));
        f3.getDocument().addDocumentListener(new FieldListener(f3, ".+@{1}.+", 11, 30));
        f4.getDocument().addDocumentListener(new FieldListener(f4, "[0-9]+", 7, 7));
        f5.getDocument().addDocumentListener(new FieldListener(f5, "[0-9]+", 10, 10));
        f6.getDocument().addDocumentListener(new FieldListener(f6, ".+",10, 30));
        
        for (int i = 0; i < 6; i++) {
            labels[i].setPreferredSize(new Dimension(100, 20));
            gbc.gridx = 0;
            gbc.gridy = i;
            contactPanel.add(labels[i], gbc);
            
            JTextField field = new JTextField();
            fields[i].setPreferredSize(new Dimension(200, 20));
            gbc.gridx = 1;
            contactPanel.add(fields[i], gbc);
        }
        
        JButton b1 = new JButton("Añadir");
            b1.addActionListener((ActionEvent ae) -> {
                boolean isNull = false;
                for (JTextField field : fields) {
                    if(field.getText().equals("")){
                        isNull = true;
                    } else {
                        isNull = false;
                        errorLabel.setForeground(Color.RED);
                        errorLabel.setText("Los campos no pueden estar vacios");
                    }
                }
                if(canSend && !isNull){
                    String[] s = new String[5];
                    s[0] = f3.getText();
                    Contact contact = new Contact(f1.getText(), f2.getText(), s, Integer.parseInt(f4.getText()), Long.parseLong(f5.getText()), f6.getText());
                    contactos.put(contact.getKey(), contact);
                    f.remove(contactPanel);
                    f.remove(contactPanel);
                    f.remove(contactPanel);
                    completionMessage("El contacto se ha añadido exitosamente");
                    menu();
                }
            });
        
        JButton b2 = new JButton("Descartar");
            b2.addActionListener((ActionEvent ae) -> {
                f.remove(contactPanel);
                menu();
            });
            
            gbc.insets = new Insets(7, 0, 0, 0);
            gbc.gridx = 0;
        
            gbc.gridy = 6;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.HORIZONTAL;
        contactPanel.add(b1, gbc);
            
            gbc.gridy = 7;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.HORIZONTAL;
        contactPanel.add(b2, gbc);
        
            gbc.gridy = 8;
            gbc.gridwidth = 2;
            gbc.fill = GridBagConstraints.HORIZONTAL;
        contactPanel.add(errorLabel, gbc);
        
        f.remove(menu);
        f.add(contactPanel);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setSize(f.getWidth() + 80, f.getHeight() + 50);
        f.setVisible(true);
    }
 
    public static void printKeys(boolean delete, boolean newContact){
        JPanel keyPanel = new JPanel();
            keyPanel.setBackground(backColor);
            keyPanel.setLayout(new GridLayout(contactos.size()+1, 1, 0, 7));
        
        for (Contact m : contactos.values()) {
            JButton b = new JButton(m.getKey());
            b.addActionListener((ActionEvent ae) -> {
                keyPanel.removeAll();
                if(delete){
                    contactos.remove(m.getKey());
                    completionMessage("El contacto se eliminó exitosamente");
                }
                if(newContact){
                    addContact();
                }else{
                    printData(m);
                }
            });
            keyPanel.add(b);
        }
        JButton b = new JButton("Volver al menu");
            keyPanel.add(b);
            b.addActionListener((ActionEvent ae) -> {
                f.remove(keyPanel);
                menu();
            });
            
        f.remove(menu);
        f.add(keyPanel);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setSize(f.getWidth() + 80, f.getHeight() + 50);
        f.setVisible(true);
    }
    
    private static void printData(Contact m) {
        
        JPanel dataPanel = new JPanel();
            dataPanel.setBackground(backColor);
        
        if(m != null){
            dataPanel.setLayout(new GridLayout(2, 1, 0, 7));
            dataPanel.add(new JLabel(m.toString()));
        } else {
            dataPanel.setLayout(new GridLayout(contactos.size()+1, 1, 0, 7));
            for (Contact c : contactos.values()) {
                dataPanel.add(new JLabel(c.toString()));
            }
        }
                
        JButton q = new JButton("Volver al menu");
            q.addActionListener((ActionEvent ae) -> {
                f.remove(dataPanel);
                menu();
            });
            
        dataPanel.add(q);
        f.remove(menu);
        f.add(dataPanel);
        f.pack();
        f.setSize(f.getWidth() + 80, f.getHeight() + 50);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
    
    public static void validateInput(JTextField field, String string, int minlength, int maxLength){
        Pattern r = Pattern.compile(string);
        Matcher m = r.matcher(field.getText());
        if (m.matches() && field.getText().length() >= minlength && field.getText().length() <= maxLength){
            errorLabel.setForeground(f.getBackground());
            canSend = true;
        }else{
            errorLabel.setForeground(Color.RED);
            errorLabel.setText(string + "El tamaño debe ser entre: " + minlength + " y " + maxLength);
            canSend = false;
        }
    }
    
    private static void completionMessage(String message){
        JOptionPane.showMessageDialog(null, message , "", JOptionPane.DEFAULT_OPTION);
    }

}

class FieldListener implements DocumentListener{

    private JTextField field;
    private String string;
    private int minLength;
    private int maxLength;
    
    public FieldListener(JTextField field, String string, int minLength, int maxLength) {
        this.field = field;
        this.string = string;
        this.minLength = minLength;
        this.maxLength = maxLength;
    }
    
    @Override
    public void removeUpdate(DocumentEvent e) {
        validateInput(field, string, minLength, maxLength);
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        validateInput(field, string, minLength, maxLength);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    }
}