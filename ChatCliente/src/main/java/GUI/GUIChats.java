package GUI;

import java.util.ArrayList;
import java.util.HashMap;
import paquete.chatcliente.Mensaje;
import socrates.user.User;

public class GUIChats extends javax.swing.JFrame {
    
    User usuario=null;
    HashMap<String,ArrayList<Mensaje>> conversaciones=null;

    /**
     * Creates new form GUIChats
     */
    public GUIChats() {
        initComponents();
    }
    
    public void anadirMensaje(String remitente, Mensaje mensaje){
        a;
    }
    
    public void amigoConectado(String amigo){
        a;
    }
    
    public void amigoDesconectado(String amigo){
        a;
    }
    
    public void anadirSolicitudAmistad(String nombreUsuario){
        a;
    }
    
    public void anadirAmigo(String amigo){
        a;
    }
    
    public void eliminarAmigo(String amigo){
        a;
    }
    
    public void setup(User usuario){
        
        this.usuario=usuario;
        this.conversaciones=new HashMap<>();
        a;
    
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Pestanas = new javax.swing.JTabbedPane();
        PestanaAmigos = new javax.swing.JPanel();
        PestanaSolicitudes = new javax.swing.JPanel();
        BotonEnviarSolicitud = new javax.swing.JButton();
        BotonCerrarSesion = new javax.swing.JButton();
        PanelConversacion = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        CampoMensaje = new javax.swing.JTextArea();
        BotonEnviar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Pestanas.setBackground(new java.awt.Color(204, 204, 204));
        Pestanas.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout PestanaAmigosLayout = new javax.swing.GroupLayout(PestanaAmigos);
        PestanaAmigos.setLayout(PestanaAmigosLayout);
        PestanaAmigosLayout.setHorizontalGroup(
            PestanaAmigosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 342, Short.MAX_VALUE)
        );
        PestanaAmigosLayout.setVerticalGroup(
            PestanaAmigosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 421, Short.MAX_VALUE)
        );

        Pestanas.addTab("Amigos", PestanaAmigos);

        javax.swing.GroupLayout PestanaSolicitudesLayout = new javax.swing.GroupLayout(PestanaSolicitudes);
        PestanaSolicitudes.setLayout(PestanaSolicitudesLayout);
        PestanaSolicitudesLayout.setHorizontalGroup(
            PestanaSolicitudesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 342, Short.MAX_VALUE)
        );
        PestanaSolicitudesLayout.setVerticalGroup(
            PestanaSolicitudesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 421, Short.MAX_VALUE)
        );

        Pestanas.addTab("Solicitudes de Amistad", PestanaSolicitudes);

        BotonEnviarSolicitud.setText("Enviar Solicitud de Amistad");

        BotonCerrarSesion.setText("Cerrar Sesión");

        PanelConversacion.setBackground(new java.awt.Color(51, 255, 0));

        javax.swing.GroupLayout PanelConversacionLayout = new javax.swing.GroupLayout(PanelConversacion);
        PanelConversacion.setLayout(PanelConversacionLayout);
        PanelConversacionLayout.setHorizontalGroup(
            PanelConversacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 584, Short.MAX_VALUE)
        );
        PanelConversacionLayout.setVerticalGroup(
            PanelConversacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 546, Short.MAX_VALUE)
        );

        CampoMensaje.setColumns(20);
        CampoMensaje.setRows(5);
        jScrollPane1.setViewportView(CampoMensaje);

        BotonEnviar.setText("Enviar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BotonEnviarSolicitud, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BotonCerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Pestanas, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PanelConversacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BotonEnviar)))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Pestanas, javax.swing.GroupLayout.PREFERRED_SIZE, 462, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BotonEnviarSolicitud, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(PanelConversacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(BotonCerrarSesion, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(BotonEnviar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(28, 28, 28))
        );

        Pestanas.getAccessibleContext().setAccessibleName("Amigos");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUIChats.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUIChats.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUIChats.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUIChats.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUIChats().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BotonCerrarSesion;
    private javax.swing.JButton BotonEnviar;
    private javax.swing.JButton BotonEnviarSolicitud;
    private javax.swing.JTextArea CampoMensaje;
    private javax.swing.JPanel PanelConversacion;
    private javax.swing.JPanel PestanaAmigos;
    private javax.swing.JPanel PestanaSolicitudes;
    private javax.swing.JTabbedPane Pestanas;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
