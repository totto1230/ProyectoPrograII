package proyectoprogramacióngrafic_1;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Date;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.ImageIcon;

public class Grafic extends JFrame {

    //Establecimiento del objeto m para poder usar los métodos y propiedades de la clase menú
    public final Menu m = new Menu();
    private final JPanel p = new JPanel();
    private final JFrame v = new JFrame();
    private final JPanel pa = new JPanel(); //panel main
    private final JFrame ve = new JFrame(); //Ventana main
    
    //Images stuff
    ImageIcon icon = new ImageIcon(Class.class.getResource("/proyectoprogramacióngrafic_1/dinnertime.png"));
    ImageIcon icon2 = new ImageIcon(Class.class.getResource("/proyectoprogramacióngrafic_1/fondo.jpg"));

    //
    private String email;
    double totalAPagarC = 0;
    double totalAPagarB = 0;
    String Comida = "";
    String Bebida = "";

    //DB Values
    int orden = 0;
    int Cantidad_C = 0;
    int Cantidad_B = 0;
    double Total;
    String Comidaa;
    String Bebidaa;

    //Email Stuff
    PreparedStatement pst;
    ResultSet result;
    PreparedStatement pst2;
    SimpleDateFormat sfd;

    //Declaracion de botones
    private JButton b1 = new JButton("Agregar Comida");
    private JButton b2 = new JButton("Agregar Bebida");
    private JButton bp = new JButton("Pagar");
    private JButton be = new JButton("Enviar email");
    private JButton etFinal = new JButton();
    private JButton etFinall = new JButton();
    private JButton siguiente = new JButton("Ordenar");

    //Declaracion de los botones para seleccionar
    private JRadioButton r = new JRadioButton("Hamburguesa de pollo", true);
    private JRadioButton r2 = new JRadioButton("Hamburguesa de carne", true);
    private JRadioButton r3 = new JRadioButton("Hamburguesa doble de pollo", true);
    private JRadioButton r4 = new JRadioButton("Hamburguesa doble angus", true);
    private JRadioButton r5 = new JRadioButton("Coca Cola", true);
    private JRadioButton r6 = new JRadioButton("Frutas", true);
    private JRadioButton r8 = new JRadioButton("Limonadas", true);
    private JRadioButton r9 = new JRadioButton("Te frio", true);
    private JRadioButton r7 = new JRadioButton("Mojitos", true);

    //Declaracion de etiquetas
    private JLabel et = new JLabel();
    private JLabel et2 = new JLabel();
    private JLabel etTot = new JLabel();
    private JLabel etTotB = new JLabel();

    //Declaracion de los sliders
    private JSlider c1 = new JSlider(1, 10, 1);
    private JSlider c2 = new JSlider(1, 10, 1);

    //Declaracion de grupos de botones
    private ButtonGroup comidas = new ButtonGroup();
    private ButtonGroup bebidas = new ButtonGroup();

    //Métodos gráficos:
    public void graphic() {
        ventana(); //Se llama al metodo privado panel para crear la ventana
        panel(); //Se llama al metodo privado panel para que se agregue el mismo a la ventana
        Slider();//Llamar motod para la creación del slider para seleccionar la cantidad
        botones();//Metodo para generar los precios y Stuff
        crearBoton(); //Se llama al metodo privado para crear los botones
        opcionesMenu();//Se llama al metodo para crear los botones radio
        crearEtiquetas();//Se llamaa para crear etiquetas
        DataBaseSfuff();//Llamada del método para generar la conexión entre database y netbeans
        email();//Metodo para llamar todo el proceso del email
        ventanaMain();
        panelMain();

    }

    private void ventanaMain() {
        ve.setSize(1000, 1000);//Tamaño ventana
        ve.setTitle("Menú"); //Titulo ventana
        ve.setLocationRelativeTo(null); //Poner en el centro
        ve.setDefaultCloseOperation(EXIT_ON_CLOSE);
        ve.setVisible(true);

    }

    //Método que crea un panel, lo mete en la ventana y a su vez crea los botones para seleccionar
    private void panelMain() {
        //Agregación del panel a la ventana
        ve.getContentPane().add(pa);//Agregación del panel a la ventana creada arriba
        pa.setLayout(null); //Se le quita el layout por defecto al panel 
    }

    //Método que crea una ventana
    private void ventana() {
        v.setSize(1000, 1000);//Tamaño ventana
        v.setTitle("Menú"); //Titulo ventana
        v.setLocationRelativeTo(null); //Poner en el centro
        v.setDefaultCloseOperation(EXIT_ON_CLOSE);
        v.setVisible(false);
    }

    //Método que crea un panel, lo mete en la ventana y a su vez crea los botones para seleccionar
    private void panel() {
        //Agregación del panel a la ventana
        v.getContentPane().add(p);//Agregación del panel a la ventana creada arriba
        p.setLayout(null); //Se le quita el layout por defecto al panel 
    }

    //Método que crea botones
    private void crearBoton() {
        //Creación de los que sumete lo seleccionado

        b1.setBounds(600, 300, 150, 80);
        b1.setVisible(true);
        p.add(b1);

        b2.setBounds(600, 450, 150, 80);
        b2.setVisible(true);
        p.add(b2);

        bp.setBounds(600, 800, 150, 80);
        bp.setVisible(true);
        p.add(bp);

        be.setBounds(200, 800, 150, 80);
        be.setVisible(true);
        p.add(be);

        etFinall.setBounds(350, 100, icon.getIconWidth(), icon.getIconHeight());
        etFinall.setVisible(true);
        etFinall.setIcon(icon);
        etFinall.setContentAreaFilled(false);
        p.add(etFinall);

        etFinal.setBounds(350, 100, icon.getIconWidth(), icon.getIconHeight());
        etFinal.setVisible(true);
        etFinal.setIcon(icon);
        etFinal.setContentAreaFilled(false);
        pa.add(etFinal);

        siguiente.setBounds(350, 300, 281, 102);
        siguiente.setVisible(true);
        pa.add(siguiente);

    }

    private void crearEtiquetas() {

        //Creación de etiquetas
        et.setBounds(700, 100, 250, 80);
        et.setVisible(true);
        p.add(et);

        et2.setBounds(700, 140, 200, 80);
        et2.setVisible(true);
        p.add(et2);

        etTot.setBounds(700, 200, 200, 80);
        etTot.setVisible(true);
        p.add(etTot);

        etTotB.setBounds(700, 400, 200, 80);
        etTotB.setVisible(true);
        p.add(etTotB);

        /*  etFinal.setBounds(700, 10, 200, 80);
        etFinal.setVisible(true);
        etFinal.setText("Dinner Time");
        p.add(etFinal);*/
    }

    private void opcionesMenu() {

        //Creación y agregación de los botones con sus opciones para seleccionar
        r.setBounds(50, 300, 200, 20);
        r.setVisible(true);
        p.add(r);

        r2.setBounds(50, 320, 200, 20);
        r2.setVisible(true);
        p.add(r2);

        r3.setBounds(50, 340, 200, 20);
        r3.setVisible(true);
        p.add(r3);

        r4.setBounds(50, 360, 200, 20);
        r4.setVisible(true);
        p.add(r4);

        r5.setBounds(50, 450, 200, 20);
        r5.setVisible(true);
        p.add(r5);

        r6.setBounds(50, 470, 200, 20);
        r6.setVisible(true);
        p.add(r6);

        r7.setBounds(50, 490, 200, 20);
        r7.setVisible(true);
        p.add(r7);

        r8.setBounds(50, 510, 200, 20);
        r8.setVisible(true);
        p.add(r8);

        r9.setBounds(50, 530, 200, 20);
        r9.setVisible(true);
        p.add(r9);

        //Creación de dos grupos de botones para que solo se seleccione uno a la vez
        comidas.add(r);
        comidas.add(r2);
        comidas.add(r3);
        comidas.add(r4);

        bebidas.add(r5);
        bebidas.add(r6);
        bebidas.add(r7);
        bebidas.add(r8);
        bebidas.add(r9);

    }

    private void Slider() {
        //Agregación de la cantidad de agregar más cantidad al menú

        c1.setBounds(300, 300, 150, 80);
        c1.setMajorTickSpacing(1);
        c1.setMinorTickSpacing(1);
        c1.setPaintLabels(true);
        p.add(c1);

        c2.setBounds(300, 450, 150, 80);
        c2.setMajorTickSpacing(1);
        c2.setMinorTickSpacing(1);
        c2.setPaintLabels(true);
        p.add(c2);

    }

    private void botones() {
        /*ActionListener
        
                 Mediante action listener (s) se establece el valor que se debe agregar a las variables
         */
        ActionListener oye = new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {

                //Dentro de la interfaz propiamente del action listener se hace una serie de ifs para sacar el valor de los arreglos desde la clase JAVA
                if (r.isSelected()) {

                    et.setText("Agregado: " + m.comidas[0] + " " + c1.getValue());
                    double cant = c1.getValue();
                    Comida = m.comidas[0];
                    totalAPagarC = totalAPagarC + (m.preciosC[0] * cant);
                    etTot.setText("Total a pagar en comida: " + totalAPagarC);
                    JOptionPane.showMessageDialog(null, "Agregado: " + m.comidas[0] + "\n" + "Cantidad: " + c1.getValue() + "\n" + "Total a pagar en comida: " + totalAPagarC);

                } else if (r2.isSelected()) {

                    et.setText("Agregado: " + m.comidas[1] + " " + c1.getValue());
                    double cant = c1.getValue();
                    Comida = m.comidas[1];
                    totalAPagarC = totalAPagarC + (m.preciosC[1] * cant);
                    etTot.setText("Total a pagar en comida: " + totalAPagarC);
                    JOptionPane.showMessageDialog(null, "Agregado: " + m.comidas[1] + "\n" + "Cantidad: " + c1.getValue() + "\n" + "Total a pagar en comida: " + totalAPagarC);

                } else if (r3.isSelected()) {

                    et.setText("Agregado: " + m.comidas[2] + " " + c1.getValue());
                    double cant = c1.getValue();
                    Comida = m.comidas[2];
                    totalAPagarC = totalAPagarC + (m.preciosC[2] * cant);
                    etTot.setText("Total a pagar en comida: " + totalAPagarC);
                    JOptionPane.showMessageDialog(null, "Agregado: " + m.comidas[2] + "\n" + "Cantidad: " + c1.getValue() + "\n" + "Total a pagar en comida: " + totalAPagarC);

                } else if (r4.isSelected()) {

                    et.setText("Agregado: " + m.comidas[3] + " " + c1.getValue());
                    double cant = c1.getValue();
                    Comida = m.comidas[3];
                    totalAPagarC = totalAPagarC + (m.preciosC[3] * cant);
                    etTot.setText("Total a pagar en comida: " + totalAPagarC);
                    JOptionPane.showMessageDialog(null, "Agregado: " + m.comidas[3] + "\n" + "Cantidad: " + c1.getValue() + "\n" + "Total a pagar en comida: " + totalAPagarC);

                } else {
                    et.setText("Vacío, ingrese un valor válido");

                }

            }
        };

        b1.addActionListener(oye);

        ActionListener oye1 = new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {

                if (r5.isSelected()) {

                    et2.setText("Agregado: " + m.bebidas[0] + " " + c2.getValue());
                    double cant = c2.getValue();
                    Bebida = m.bebidas[0];
                    totalAPagarB = totalAPagarB + (m.preciosB[0] * cant);
                    etTotB.setText("Total a pagar en bebidas: " + totalAPagarB);
                    JOptionPane.showMessageDialog(null, "Agregado: " + m.bebidas[0] + "\n" + "Cantidad: " + c2.getValue() + "\n" + "Total a pagar en bebidas: " + totalAPagarB);

                } else if (r6.isSelected()) {

                    et2.setText("Agregado: " + m.bebidas[1] + " " + c2.getValue());
                    double cant = c2.getValue();
                    Bebida = m.bebidas[1];
                    totalAPagarB = totalAPagarB + (m.preciosB[1] * cant);
                    etTotB.setText("Total a pagar en bebidas: " + totalAPagarB);
                    JOptionPane.showMessageDialog(null, "Agregado: " + m.bebidas[1] + "\n" + "Cantidad: " + c2.getValue() + "\n" + "Total a pagar en bebidas: " + totalAPagarB);

                } else if (r7.isSelected()) {

                    et2.setText("Agregado: " + m.bebidas[2] + " " + c2.getValue());
                    Bebida = m.bebidas[2];
                    double cant = c2.getValue();
                    totalAPagarB = totalAPagarB + (m.preciosB[2] * cant);
                    JOptionPane.showMessageDialog(null, "Agregado: " + m.bebidas[2] + "\n" + "Cantidad: " + c2.getValue() + "\n" + "Total a pagar en bebidas: " + totalAPagarB);

                } else if (r8.isSelected()) {

                    et2.setText("Agregado: " + m.bebidas[3] + " " + c2.getValue());
                    Bebida = m.bebidas[3];
                    double cant = c2.getValue();
                    totalAPagarB = totalAPagarB + (m.preciosB[3] * cant);
                    JOptionPane.showMessageDialog(null, "Agregado: " + m.bebidas[3] + "\n" + "Cantidad: " + c2.getValue() + "\n" + "Total a pagar en bebidas: " + totalAPagarB);

                } else if (r9.isSelected()) {

                    et2.setText("Agregado: " + m.bebidas[4] + " " + c2.getValue());
                    Bebida = m.bebidas[4];
                    double cant = c2.getValue();
                    totalAPagarB = totalAPagarB + (m.preciosB[4] * cant);
                    etTotB.setText("Total a pagar en bebidas: " + totalAPagarB);
                    JOptionPane.showMessageDialog(null, "Agregado: " + m.bebidas[4] + "\n" + "Cantidad: " + c2.getValue() + "\n" + "Total a pagar en bebidas: " + totalAPagarB);

                } else {
                    et.setText("Vacío, ingrese un valor válido");

                }

            }
        };

        b2.addActionListener(oye1);//Agregación del objeto oye1 para que sirve cuando se seleccione el botón

        ActionListener next = new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent e) {
                ve.setVisible(false);
                v.setVisible(true);
            }
        };

        siguiente.addActionListener(next);//Agregación del objeto oye1 para que sirve cuando se seleccione el botón

    }

    private void DataBaseSfuff() {

        //Action listener para que enseñe el total a pagar y agregar orden a la base de datos
        ActionListener oye3 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Total = totalAPagarB + totalAPagarC; //Suma de ambos totales
                JOptionPane.showMessageDialog(null, "Total a pagar: " + Total);
                //Integración de NetBeans con la base de datos
                try {
                    /*Utilización de la función: Connection y creación de un objeto. Se usa la función .getConnection para el establecimiento de la misma, se usan tres fields:
                 (el path o la dirección, user, contraseña)*/
                    Connection cn = DriverManager.getConnection("jdbc:mysql://localhost/proyecto", "root", "");
                    /* Se utiliza la función: PreparedStatement de la librería para poder indicarle o darle instrucciones a la base de datos que agregue los valores. Se usa la siguiente estructura:
                insert into orden values (Se ponen el total de columnas de la base de datos y como no se conoce el valor, se pone un signo de pregunta momentáneamente)
                En este caso se usa el insert para el establecimiento de valores dentro de la base de datos*/
                    pst = cn.prepareStatement("insert into orden values(?,?,?,?,?,?,?,?)");
                    pst.setString(1, "0");
                    pst.setString(2, Comida);
                    pst.setInt(3, c1.getValue());
                    pst.setString(4, Bebida);
                    pst.setInt(5, c2.getValue());
                    pst.setDouble(6, totalAPagarC);
                    pst.setDouble(7, totalAPagarB);
                    pst.setDouble(8, Total);
                    pst.executeUpdate(); //Ejecución de las líneas de arriba (Agregación de datos a la base de datos)
                    pst.close();//Cerrar la connection

                } catch (Exception Ex) {

                }

            }
        };
        bp.addActionListener(oye3);

    }

    private void email() {
        /*ActionListener
        
          Mediante action listener (s) se establece el valor que se debe agregar a las variables
         */
        //Action listener para que enseñe el total a pagar y agregar orden a la base de datos
        ActionListener oye3 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                email = JOptionPane.showInputDialog("Enter Email");
                try {
                    Properties propiedad = new Properties(); //La declaración de un objeto de tipo propiedades para el seteo  de propiedades que se utilizaran más adelante

                    propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");//Decalaración del host o domain que usa Google para habilitar el envío de correos desde un lugar externo

                    propiedad.setProperty("mail.smtp.starttls.enable", "true"); //Autentificación segura por medio del protocolo TLS 

                    propiedad.setProperty("mail.smtp.port", "587"); //puerto UDP del protocolo smtp (POP3)

                    propiedad.setProperty("mail.smtp.auth", "true"); //Mediante una variable boolean se activa la authentificación para que gmail entienda que debe hacer una verificación antes de enviar un correo

                    Session sesion = Session.getDefaultInstance(propiedad);   //Se usa el objeto sesion de la librería Session y se le indica cuáles parámetros tiene que usar: (propiedades)

                    //Propiedades para la autentificación del user en gmail 
                    String correoEnvia = "dinnertimenoreply@gmail.com";

                    String contrasena = "Testing123!";

                    String destino = email;

                    String asunto = "Menú";

                    //Declaracion de fechas
                    Date f = new Date();

                    double totalToString = Total;

                    String mensaje = " Comida comprada: " + Comida + " , " + " Bebida comprada: " + Bebida + ", Total pagado: " + totalToString + ", Fecha de pago: " + f;

                    MimeMessage mail = new MimeMessage(sesion);//Creación de un objeto tipo mail de la librería MimeMessage donde se le pasan el objeto sesion anteriormente mejorado.
                    try {
                        mail.setFrom(new InternetAddress(correoEnvia));
                        mail.addRecipient(Message.RecipientType.TO, new InternetAddress(destino));
                        mail.setSubject(asunto);
                        mail.setText(mensaje);

                        Transport transporte = sesion.getTransport("smtp"); //Objeto de la librería transport para enviar los parametros a gmail
                        transporte.connect(correoEnvia, contrasena);
                        transporte.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));
                        transporte.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));
                        transporte.close();

                        //Son son catchs para el address exception y el messaging.
                    } catch (AddressException ex) {
                        Logger.getLogger(proyectoprogramación2consola.Main.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Error fatal, favor contactar al dueño del local lo antes posible");
                    } catch (MessagingException ex) {
                        Logger.getLogger(proyectoprogramación2consola.Main.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Error fatal, favor contactar al dueño del local lo antes posible");
                    }

                    //Catch propiamente del action listener
                } catch (Exception Ex) {
                    JOptionPane.showMessageDialog(null, "Error fatal, favor contactar al dueño del local lo antes posible");
                }
            }
        };
        be.addActionListener(oye3);

    }
}
