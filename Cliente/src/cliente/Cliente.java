package cliente;

import java.awt.Toolkit;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import javax.swing.JOptionPane;

public class Cliente extends javax.swing.JFrame {

    private ObjectOutputStream salida; // Objeto para enviar mensajes 
    private ObjectInputStream entrada; // Objeto para recibir mensajes
    private String mensaje = ""; // Variable para almacenar el mensaje
    private String servidorIP; // Variable para almacenar la dirección IP del servidor a conectar
    private Socket conexion;
    private int puerto = 6789; // Puerto usado para la conexión
    
    public Cliente(String s) {
        initComponents();
        // Diseño del frame
        this.setTitle("Cliente");
        this.setVisible(true);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("monitor.png")));
        jEstado.setVisible(true);
        servidorIP = s; // El servidor será definido en la clase main cliente mediante el constructor
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jMensaje = new javax.swing.JTextField();
        jEnviar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jChat = new javax.swing.JTextArea();
        jIns = new javax.swing.JLabel();
        jEstado = new javax.swing.JLabel();
        jFondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setForeground(new java.awt.Color(60, 63, 65));
        setIconImages(null);
        setPreferredSize(new java.awt.Dimension(417, 399));
        setResizable(false);

        jPanel1.setLayout(null);

        jMensaje.setBackground(new java.awt.Color(0, 0, 0));
        jMensaje.setForeground(new java.awt.Color(255, 255, 255));
        jMensaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMensajeActionPerformed(evt);
            }
        });
        jPanel1.add(jMensaje);
        jMensaje.setBounds(20, 46, 270, 24);

        jEnviar.setBackground(new java.awt.Color(0, 0, 0));
        jEnviar.setForeground(new java.awt.Color(255, 255, 255));
        jEnviar.setText("Enviar");
        jEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jEnviarActionPerformed(evt);
            }
        });
        jPanel1.add(jEnviar);
        jEnviar.setBounds(310, 40, 70, 30);

        jChat.setBackground(new java.awt.Color(0, 0, 0));
        jChat.setColumns(20);
        jChat.setForeground(new java.awt.Color(255, 255, 255));
        jChat.setRows(5);
        jChat.setFocusable(false);
        jScrollPane1.setViewportView(jChat);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(20, 110, 370, 270);

        jIns.setForeground(new java.awt.Color(255, 255, 255));
        jIns.setText("Escribe el texto aquí");
        jPanel1.add(jIns);
        jIns.setBounds(20, 30, 150, 10);

        jEstado.setForeground(new java.awt.Color(255, 255, 255));
        jEstado.setText("...");
        jPanel1.add(jEstado);
        jEstado.setBounds(20, 90, 300, 20);

        jFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/cliente/Untitled-1.jpg"))); // NOI18N
        jFondo.setOpaque(true);
        jPanel1.add(jFondo);
        jFondo.setBounds(0, -10, 420, 430);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(406, 444));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMensajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMensajeActionPerformed
        // Validación para evitar campos vacíos 
        if (jMensaje.getText().equals("")) {
            return;
       }else{
        enviarMensaje(jMensaje.getText());
	jMensaje.setText("");
        }
    }//GEN-LAST:event_jMensajeActionPerformed

    private void jEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jEnviarActionPerformed
        if (jMensaje.getText().equals("")) {
            return;
        }else{
        enviarMensaje(jMensaje.getText());
	jMensaje.setText("");
        }
    }//GEN-LAST:event_jEnviarActionPerformed

    public void ejecutar() {
        try {
            jEstado.setText("Intentando conectar...");
            try {
                conexion = new Socket(InetAddress.getByName(servidorIP), puerto);//Definidr socket para la conexion
            } catch(IOException e) {
                    // Si no conecta al servidor, cerrar la aplicación
                    JOptionPane.showMessageDialog(null,"Servidor no disponible.","Aviso",JOptionPane.WARNING_MESSAGE);
                    System.exit(1);
            }
            jEstado.setText("Conectado a: " + conexion.getInetAddress().getHostName());
            //Mostrar conexion realizada

            salida = new ObjectOutputStream(conexion.getOutputStream());//Definicion de flujo de salida
            salida.flush();
            entrada = new ObjectInputStream(conexion.getInputStream());//Definicion de flujo de entrada
            // Ejecutar método para realizar acciones mientras se comunica
            chat();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    private void chat() throws IOException {
      jMensaje.setEditable(true); // Habilitar el campo de texto para mensaje
      do {
        try {
             mensaje = (String) entrada.readObject(); // Si llega un mensaje convertirlo a string
             jChat.append("\n" + mensaje); // Agregar el mensaje recibido al jtextArea
        } catch(ClassNotFoundException e) {}
      } while(!mensaje.equals("Client - END"));
    }
  
    private void enviarMensaje(String mensaje) {
        try {
            salida.writeObject("Cliente - " + mensaje); // Enviar mensaje escrito en el campo de texto
            salida.flush();
            jChat.append("\nCliente - " + mensaje); // Agregar mensaje enviado al jtextarea
        } catch(IOException e) {
            jChat.append("\n No se pudo enviar el mensaje");
        }
    }
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea jChat;
    private javax.swing.JButton jEnviar;
    private javax.swing.JLabel jEstado;
    private javax.swing.JLabel jFondo;
    private javax.swing.JLabel jIns;
    private javax.swing.JTextField jMensaje;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
