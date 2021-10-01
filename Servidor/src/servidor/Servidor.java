package servidor;

import java.awt.Toolkit;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor extends javax.swing.JFrame {

    private ObjectOutputStream salida; // Objeto para salida de datos
    private ObjectInputStream entrada; // Objeto para entrada de datos
    private Socket conexion; // Socket para conexión con el cliente
    private ServerSocket servidor; // Socket del servidor
    private int totalClientes = 100; // Cantidad máxima de clientes a conectar
    private int puerto = 6789; // Puerto usado para la conexión
  
    public Servidor() {
        //Diseños del frame
        initComponents();
        this.setTitle("Servidor");
        this.setVisible(true);
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("ratio.png")));
        jEstado.setVisible(true);
    }
    
    public void ejecutar() {
        try {
            // Inicializar cliente especificando puerto y número maximo de conexiones
            servidor = new ServerSocket(puerto, totalClientes);
            while(true) {
                try {
                    // Esperar conexiones y aceptarlas
                    jEstado.setText(" Esperando peticiones...");
                    conexion = servidor.accept();
                    // Mostrar la conexión realizada
                    jEstado.setText(" Conectado a: " + conexion.getInetAddress().getHostName());
                    // Definicion de objetos para entrada y salida de datos 
                    salida = new ObjectOutputStream(conexion.getOutputStream());
                    salida.flush();
                    entrada = new ObjectInputStream(conexion.getInputStream());
                    // Invocar metodo para realizar acciones mientras se comunica
                    chat();
                } catch(EOFException e) {}
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    private void chat() throws IOException {
        String mensaje = "";    
        jMensaje.setEditable(true);
        do {
            try {
                // Convertir mensaje que llega a String
                mensaje = (String) entrada.readObject();
                jChat.append("\n" + mensaje); // Agregar el mensaje recibido al jtextArea
            } catch(ClassNotFoundException e) {}
        } while(!mensaje.equals("Client - END"));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jChat = new javax.swing.JTextArea();
        jMensaje = new javax.swing.JTextField();
        jEnviar = new javax.swing.JButton();
        jEstado = new javax.swing.JLabel();
        jIns = new javax.swing.JLabel();
        jFondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(null);

        jChat.setBackground(new java.awt.Color(0, 0, 0));
        jChat.setColumns(20);
        jChat.setForeground(new java.awt.Color(255, 255, 255));
        jChat.setRows(5);
        jChat.setFocusable(false);
        jScrollPane1.setViewportView(jChat);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(20, 110, 370, 270);

        jMensaje.setBackground(new java.awt.Color(0, 0, 0));
        jMensaje.setForeground(new java.awt.Color(255, 255, 255));
        jMensaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMensajeActionPerformed(evt);
            }
        });
        jPanel1.add(jMensaje);
        jMensaje.setBounds(20, 50, 270, 24);

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

        jEstado.setForeground(new java.awt.Color(255, 255, 255));
        jEstado.setText("...");
        jPanel1.add(jEstado);
        jEstado.setBounds(20, 90, 300, 20);

        jIns.setForeground(new java.awt.Color(255, 255, 255));
        jIns.setText("Escribe el texto aquí");
        jPanel1.add(jIns);
        jIns.setBounds(20, 20, 150, 30);

        jFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/servidor/Untitled-1.jpg"))); // NOI18N
        jPanel1.add(jFondo);
        jFondo.setBounds(0, -10, 400, 430);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(416, 447));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jEnviarActionPerformed
        if (jMensaje.getText().equals("")) {
            return;
        }else{
        enviarMensaje(jMensaje.getText());
	jMensaje.setText("");
        }
    }//GEN-LAST:event_jEnviarActionPerformed

    private void jMensajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMensajeActionPerformed
        // Validación para evitar campos vacíos
        if (jMensaje.getText().equals("")) {
            return;
        }else{
        enviarMensaje(jMensaje.getText());
	jMensaje.setText("");
        }
    }//GEN-LAST:event_jMensajeActionPerformed

    private void enviarMensaje(String mensaje) {
        try {
            // Enviar mensaje con lo escrito en el jtext 
            salida.writeObject("Servidor - " + mensaje);
            salida.flush();
            jChat.append("\nServidor - " + mensaje); // Agregar el mensaje enviado al jtextarea
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
