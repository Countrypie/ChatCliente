package GUI;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import paquete.chatcliente.IPeer;
import paquete.chatcliente.Mensaje;
import socrates.server.CallbackServerInterface;
import socrates.user.User;

public class GUIChats extends javax.swing.JFrame {
    
    User usuario=null;
    String contrasena=null;
    HashMap<String,ArrayList<Mensaje>> conversaciones=null;
    HashMap<String,String> referencias=null;
    CallbackServerInterface server=null;
    
    ArrayList<GUIPanelAmigo> panelesAmigos=null;
    String chatActual=null;
    ArrayList<GUISolicitudAmistad> panelesSolicitudes=null;

    /**
     * Creates new form GUIChats
     */
    public GUIChats() {
        initComponents();
        
        PanelAmigos.setLayout(new BoxLayout(PanelAmigos, BoxLayout.Y_AXIS));
        PanelConversacion.setLayout(new BoxLayout(PanelConversacion, BoxLayout.Y_AXIS));

    }
    
    public void anadirMensaje(String conversacion, Mensaje mensaje){
        ArrayList<Mensaje> conversacionActual=this.conversaciones.get(conversacion);
        conversacionActual.add(mensaje);
        
        this.revalidate();this.repaint();
    }
    
    public void amigoConectado(String amigo){
        //se busca el panel del amigo conectado
        GUIPanelAmigo buscando=null;
        Iterator iterador= panelesAmigos.iterator();
        while(iterador.hasNext()){
            GUIPanelAmigo amigoActual=(GUIPanelAmigo)iterador.next();
            if(amigoActual.getNombre().equals(amigo)){
                buscando=amigoActual;
                break;
            }
        }
        
        //Se establece al amigo encontrado como conectado
        if(buscando!=null)
            buscando.setConectado(true);
    }
    
    public void amigoDesconectado(String amigo){
        //se busca el panel del amigo conectado
        GUIPanelAmigo buscando=null;
        Iterator iterador= panelesAmigos.iterator();
        while(iterador.hasNext()){
            GUIPanelAmigo amigoActual=(GUIPanelAmigo)iterador.next();
            if(amigoActual.getNombre().equals(amigo)){
                buscando=amigoActual;
                break;
            }
        }
        
        //Se establece al amigo encontrado como desconectado
        if(buscando!=null)
            buscando.setConectado(false);
    }
    
    public void anadirSolicitudAmistad(String nombreUsuario){
        //a;
    }
    
    public void anadirAmigo(String amigo){
        //a;
    }
    
    public void eliminarAmigo(String amigo){
        //a;
    }
    
    //Esta funcion cambia en el panel de conversaciones los mensajes que se muestran
    public void cambiarConversacion(String usuario){
        //Se obtienen los mensajes de la conversacion
        ArrayList<Mensaje> mensajes=this.conversaciones.get(usuario);
        //Se guarda que conversacion esta presente ahora
        this.chatActual=usuario;
        
        //Se anaden los mensajes
        PanelConversacion.removeAll();
        for(Mensaje mensaje : mensajes){
            GUIPanelMensaje panelMensaje=new GUIPanelMensaje(mensaje.getContenido(),
                    mensaje.getHora(),(mensaje.getRemitente().equals(usuario)? false:true));
            PanelConversacion.add(panelMensaje);
        }
        
        //Se dibuja
        this.revalidate();this.repaint();
    }
    
    public void setup(User usuario, String contrasena, CallbackServerInterface server){
        
        this.usuario=usuario;
        this.contrasena=contrasena;
        this.conversaciones=new HashMap<>();
        this.server=server;
        referencias=new HashMap<>();
        this.panelesAmigos=new ArrayList<>();
        this.panelesSolicitudes=new ArrayList<>();
        
        for(String amigo : usuario.getFriends()){
            //Se crean los GUIPanelAmigo y se marcan los conectados
            GUIPanelAmigo panel=new GUIPanelAmigo(amigo,this);
            panelesAmigos.add(panel);
            PanelAmigos.add(panel);
            if(usuario.getFriendsConnected().contains(amigo)){
                panel.setConectado(true);
            }
            
            //Se crea una conversacion
            ArrayList<Mensaje> conversacion=new ArrayList<>();
            this.conversaciones.put(amigo, conversacion);
        }
        
        this.revalidate();
        this.repaint();
    
    }
    
    public void aceptoSolicitud(String usuario){
        
    }
    
    public void rechazoSolicitud(String usuario){
        
    }
    
    //Esta funcion devuelve una direccion RMI actualizada de un usuario a partir de su nombre
    private String buscarRMI(String usuario){
        String resultado=null;
        IPeer objetoRemoto=null;
        
        if(referencias.containsKey(usuario)){
            //Si hay una referencia remota, se comprueba que sirva
            try {
                objetoRemoto = (IPeer) Naming.lookup(referencias.get(usuario));
            } catch (Exception ex) {
                //Si no sirve
                try {
                    //Se intenta actualizar
                    String nuevaReferencia=this.server.startChat(this.usuario.getUsername(), usuario, this.contrasena);
                    objetoRemoto = (IPeer) Naming.lookup(referencias.get(usuario));
                    this.referencias.replace(usuario, nuevaReferencia);
                } catch (Exception ex1) {
                    Logger.getLogger(GUIChats.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
            
        }else{
            //Si no hay referencia, se obtiene y se guarda
            try {
                String nuevaReferencia=this.server.startChat(this.usuario.getUsername(), usuario, this.contrasena);
                objetoRemoto = (IPeer) Naming.lookup(referencias.get(usuario));
                this.referencias.put(usuario, nuevaReferencia);
            } catch (Exception ex1) {
                Logger.getLogger(GUIChats.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
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
        PestanaAmigos = new javax.swing.JScrollPane();
        PanelAmigos = new javax.swing.JPanel();
        PestanaSolicitudes = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        BotonCerrarSesion = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        CampoMensaje = new javax.swing.JTextArea();
        BotonEnviar = new javax.swing.JButton();
        ScrollPanelConversacion = new javax.swing.JScrollPane();
        PanelConversacion = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        Pestanas.setBackground(new java.awt.Color(204, 204, 204));
        Pestanas.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        Pestanas.setMaximumSize(new java.awt.Dimension(178, 117));

        PestanaAmigos.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        javax.swing.GroupLayout PanelAmigosLayout = new javax.swing.GroupLayout(PanelAmigos);
        PanelAmigos.setLayout(PanelAmigosLayout);
        PanelAmigosLayout.setHorizontalGroup(
            PanelAmigosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 384, Short.MAX_VALUE)
        );
        PanelAmigosLayout.setVerticalGroup(
            PanelAmigosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 497, Short.MAX_VALUE)
        );

        PestanaAmigos.setViewportView(PanelAmigos);

        Pestanas.addTab("Amigos", PestanaAmigos);
        Pestanas.addTab("Solicitudes Pendientes", PestanaSolicitudes);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 386, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 499, Short.MAX_VALUE)
        );

        Pestanas.addTab("Enviar Solicitud", jPanel1);

        BotonCerrarSesion.setText("Cerrar Sesión");
        BotonCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonCerrarSesionActionPerformed(evt);
            }
        });

        CampoMensaje.setColumns(20);
        CampoMensaje.setRows(5);
        jScrollPane1.setViewportView(CampoMensaje);

        BotonEnviar.setText("Enviar");

        ScrollPanelConversacion.setBackground(new java.awt.Color(240, 245, 245));

        PanelConversacion.setBackground(new java.awt.Color(242, 247, 247));

        javax.swing.GroupLayout PanelConversacionLayout = new javax.swing.GroupLayout(PanelConversacion);
        PanelConversacion.setLayout(PanelConversacionLayout);
        PanelConversacionLayout.setHorizontalGroup(
            PanelConversacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 559, Short.MAX_VALUE)
        );
        PanelConversacionLayout.setVerticalGroup(
            PanelConversacionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 538, Short.MAX_VALUE)
        );

        ScrollPanelConversacion.setViewportView(PanelConversacion);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addComponent(BotonCerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(Pestanas, javax.swing.GroupLayout.PREFERRED_SIZE, 392, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 472, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BotonEnviar)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(ScrollPanelConversacion))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Pestanas, javax.swing.GroupLayout.PREFERRED_SIZE, 540, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ScrollPanelConversacion))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(BotonCerrarSesion, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(BotonEnviar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(28, 28, 28))
        );

        Pestanas.getAccessibleContext().setAccessibleName("Amigos");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BotonCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonCerrarSesionActionPerformed
        this.dispose();
    }//GEN-LAST:event_BotonCerrarSesionActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        try {
            if(this.server!=null)
                this.server.logOut(this.usuario.getUsername(), contrasena);
        } catch (RemoteException ex) {
            Logger.getLogger(GUIChats.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowClosed

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
    private javax.swing.JTextArea CampoMensaje;
    private javax.swing.JPanel PanelAmigos;
    private javax.swing.JPanel PanelConversacion;
    private javax.swing.JScrollPane PestanaAmigos;
    private javax.swing.JScrollPane PestanaSolicitudes;
    private javax.swing.JTabbedPane Pestanas;
    private javax.swing.JScrollPane ScrollPanelConversacion;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
