package proyectoprogramación2consola;

import java.util.Scanner;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JPanel;

public class Main extends JPanel{

    public static void main(String[] args) {

        //Declaraciön de objetos
        Menu m = new Menu();
        Scanner s = new Scanner(System.in);

        //Declaraciön de variables
        int seleccion;
        int cont = 1;
        int cant = 1;
        int cont2 = 1;
        String comida = "";
        String bebida = "";

        
        
        
        
        
        
        
        while (cont == 1) {
            System.out.println("Qué desea comer? ");
            System.out.println(" 1. Hamburguesa de pollo" + " 2. Hamburguesa de carne" + " 3. Hamburguesa doble de pollo" + " 4. Hamburguesa doble angus");
            seleccion = s.nextInt();

            if (seleccion == 1) {
                comida = m.comidas[0];

            } else if (seleccion == 2) {
                comida = m.comidas[1];

            } else if (seleccion == 3) {
                comida = m.comidas[2];

            } else if (seleccion == 4) {
                comida = m.comidas[3];

            }

            System.out.println("Cuántas desea agregar a la orden? ");
            cant = s.nextInt();
            m.sacarPrecios(seleccion, m.preciosC, cant);
            System.out.println("Desea pedir algo más");
            System.out.println("1. Sí 2. No");
            cont = s.nextInt();
        }

        while (cont2 == 1) {
            System.out.println("Qué desea tomar? ");
            System.out.println("1 Coca Cola 2 Frutas 3 Mojitos 4 Limonadas 5 Te frio");
            seleccion = s.nextInt();

            if (seleccion == 1) {
                bebida = m.bebidas[0];

            } else if (seleccion == 2) {
                bebida = m.bebidas[1];

            } else if (seleccion == 3) {
                bebida = m.bebidas[2];

            } else if (seleccion == 4) {
                bebida = m.bebidas[3];

            } else if (seleccion == 5) {
                bebida = m.bebidas[4];

            }

            System.out.println("Cuántos refrescos desea agregar a la orden? ");
            cant = s.nextInt();
            m.sacarPrecios(seleccion, m.preciosB, cant);
            System.out.println("Desea pedir algo más");
            System.out.println("1. Sí 2. No");
            cont2 = s.nextInt();
        }

        double total = m.totalB + m.totalC;

        System.out.println("Total: " + total);

        System.out.println("Agregar valor a la DB test");
        int t = s.nextInt();

        if (t == 1) {
            try {
                Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/prueba", "root", "");
                PreparedStatement pst = cn.prepareStatement("insert into orden values(?,?,?,?)");
                pst.setString(1, "0");
                pst.setString(2, comida);
                pst.setString(3, bebida);
                pst.setDouble(4, total);
                pst.executeUpdate();

            } catch (Exception e) {

            }

        } else if (t == 2) {
            Properties propiedad = new Properties(); //La declaración de un objeto de tipo propiedades para el seteo  de propiedades que se utilizaran más adelante
            propiedad.setProperty("mail.smtp.host", "smtp.gmail.com" );//Decalaración de la 
             propiedad.setProperty("mail.smtp.starttls.enable", "true"); //Autentificación segura por medio del protocolo TLS 
             propiedad.setProperty("mail.smtp.port", "587"); //puerto UDP del protocolo smtp (POP3)
            propiedad.setProperty("mail.smtp.auth", "true"); //Mediante una variable boolean se activa la authentificación para que gmail entienda que debe hacer una verificación antes de enviar un correo
            
            Session sesion= Session.getDefaultInstance(propiedad);   //Se usa el objeto sesion de la librería Session y se le indica cuáles parámetros tiene que usar: (propiedades)
            //Propiedades para la autentificación del user en gmail 
            String correoEnvia = "testingjoseph123@gmail.com";
            String contrasena= "Testing123!";
            String destino= "danitrasolorzano@gmail.com";
            String asunto = "Menu";
            String mensaje = comida+bebida;
            String mensaje2 = bebida;
            
            MimeMessage mail= new MimeMessage(sesion);//Creación de un objeto tipo mail de la librería MimeMessage donde se le pasan el objeto sesion anteriormente mejorado.
            try {
                mail.setFrom(new InternetAddress(correoEnvia));
                mail.addRecipient(Message.RecipientType.TO, new InternetAddress(destino));
                mail.setSubject(asunto);
                mail.setText(mensaje2);
                
                
                Transport transporte = sesion.getTransport("smtp"); //Objeto de la librería transport para enviar los parametros a gmail
                transporte.connect(correoEnvia, contrasena);
                transporte.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));
                transporte.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));
                transporte.close();
                
       
                //Son son catchs para el address exception y el messaging.
            } catch (AddressException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (MessagingException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }

}
