package GUI;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
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
    ArrayList<GUIPanelNotificacion> panelesNotificaciones=null;

    /**
     * Creates new form GUIChats
     */
    public GUIChats() {
        initComponents();
        
        //Se configuran contenedores para que apilen objetos en una lista vertical
        PanelAmigos.setLayout(new BoxLayout(PanelAmigos, BoxLayout.Y_AXIS));
        PanelConversacion.setLayout(new BoxLayout(PanelConversacion, BoxLayout.Y_AXIS));
        PanelSolicitudes.setLayout(new BoxLayout(PanelSolicitudes, BoxLayout.Y_AXIS));
        PanelNotificaciones.setLayout(new BoxLayout(PanelNotificaciones, BoxLayout.Y_AXIS));
        
        //Se esconden textos de informacion
        TextoSolicitudEnviada.setVisible(false);
        TextoSolicitudEliminar.setVisible(false);
    }
    
    //Funcion que anade un mensaje a la conversacion
    public void anadirMensaje(String conversacion, Mensaje mensaje){
        ArrayList<Mensaje> conversacionActual=this.conversaciones.get(conversacion);
        //si se encontro la conversacion
        if(conversacionActual!=null){
            conversacionActual.add(mensaje);
        
            //Para redibujar se llama a cambiarConversacion, asi actualiza los mensajes presentes
            this.cambiarConversacion(conversacion);
        }
    }
    
    //Fucnion que marca a un amigo como conectado
    public void amigoConectado(String amigo){
        //si el amigo conectado esta guardado como amigo
        if(this.usuario.getFriends().contains(amigo)){
            //se anade a la lista de amigos conectados
            this.usuario.getFriendsConnected().add(amigo);
        
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

            //Se establece el panel del amigo encontrado como conectado
            if(buscando!=null)
                buscando.setConectado(true);
        }
    }
    //Fucnion que marca a un amigo como desconectado
    public void amigoDesconectado(String amigo){
        //si el amigo conectado esta guardado como amigo
        if(this.usuario.getFriends().contains(amigo)){
            //se elimina de la lista de amigos conectados
            this.usuario.getFriendsConnected().remove(amigo);
        
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

            //Se establece el panel del amigo encontrado como desconectado
            if(buscando!=null)
                buscando.setConectado(false);
        }
    }
    
    //Funcion que anade una solicitud de amistad a la GUI
    public void anadirSolicitudAmistad(String nombreUsuario){
        //Proteccion extra por si la solicitud ya existia
        if(!this.usuario.getFriendRequests().contains(nombreUsuario)){
            //Se anade la solicitud a la estructura de datos del usuario
            this.usuario.getFriendRequests().add(nombreUsuario);
            //Se anade el panel a la estructura de datos de la GUI
            GUISolicitudAmistad panel=new GUISolicitudAmistad(this,nombreUsuario);
            this.panelesSolicitudes.add(panel);

            //Se anade al contenedor grafico
            PanelSolicitudes.add(panel);
            PanelSolicitudes.revalidate();PanelSolicitudes.repaint();
        }
    }
    
    //Funcion que el servidor invoca para informar de un nuevo amigo
    public void anadirAmigo(String amigo){
        
        //proteccion extra por si ya era amigo, para no sobreescribir
        if(!this.usuario.getFriends().contains(amigo)){
            //Se crean los GUIPanelAmigo
            GUIPanelAmigo panel=new GUIPanelAmigo(amigo,this);
            panelesAmigos.add(panel);
            PanelAmigos.add(panel);

            //Se actualiza la lista de amigos y de amigos conectados
            try {
                this.usuario.setFriends(this.server.obtainFriendList(
                        this.usuario.getUsername(), this.contrasena));
                this.usuario.setFriendsConnected(this.server.obtainConnectedFriendList(
                        this.usuario.getUsername(), this.contrasena));
            } catch (RemoteException ex) {
                Logger.getLogger(GUIChats.class.getName()).log(Level.SEVERE, null, ex);
            }

            //Se enciende la luz del panel si el amigo esta conectado
            if(usuario.getFriendsConnected().contains(amigo)){
                panel.setConectado(true);
            }

            //Se crea una conversacion
            ArrayList<Mensaje> conversacion=new ArrayList<>();
            this.conversaciones.put(amigo, conversacion);
        }
    }
    
    //Funcion que el servidor invoca para informar de que un amigo rompio la amistad
    public void eliminarAmigo(String examigo){
        //proteccion extra por si no era amigo
        if(this.usuario.getFriends().contains(examigo)){
            //Se busca el panel del amigo y se borra
            GUIPanelAmigo buscando=null;
            for(GUIPanelAmigo panel:this.panelesAmigos){
                if(panel.getNombre().equals(examigo)){
                    this.panelesAmigos.remove(panel);
                    break;
                }
            }

            //Se actualiza la lista de amigos y de amigos conectados
            try {
                this.usuario.setFriends(this.server.obtainFriendList(
                        this.usuario.getUsername(), this.contrasena));
                this.usuario.setFriendsConnected(this.server.obtainConnectedFriendList(
                        this.usuario.getUsername(), this.contrasena));
            } catch (RemoteException ex) {
                Logger.getLogger(GUIChats.class.getName()).log(Level.SEVERE, null, ex);
            }

            //Se elimina la conversacion
            this.conversaciones.remove(examigo);
        }
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
                    mensaje.getHora(),(!mensaje.getRemitente().equals(usuario)));
            PanelConversacion.add(panelMensaje);
        }
        
        //Se dibuja
        ScrollPanelConversacion.revalidate();ScrollPanelConversacion.repaint();
    }
    
    //Funcion que el cliente invoca para anadir datos a los chats obtenidos del servidor
    public void setup(User usuario, String contrasena, CallbackServerInterface server){
        
        this.usuario=usuario;
        this.contrasena=contrasena;
        this.conversaciones=new HashMap<>();
        this.server=server;
        referencias=new HashMap<>();
        this.panelesAmigos=new ArrayList<>();
        this.panelesSolicitudes=new ArrayList<>();
        this.panelesNotificaciones=new ArrayList<>();
        
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
    
    //Funcion que una solicitud de amistad invoca para aceptar su propia solicitud
    public void aceptoSolicitud(String usuario){
        try {
            server.acceptFriendRequest(this.usuario.getUsername(), usuario, contrasena);
        } catch (RemoteException ex) {
            Logger.getLogger(GUIChats.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Se busca al panel de la solicitud para eliminarlo
        GUISolicitudAmistad buscando=null;
        for(GUISolicitudAmistad panel: panelesSolicitudes){
            if(panel.getUsuario().equals(usuario)){
                panelesSolicitudes.remove(panel);
                PanelSolicitudes.remove(panel);
                break;
            }
        }
        
        PestanaSolicitudes.revalidate();PestanaSolicitudes.repaint();
        
        //Se elimina la solicitud de la estructura del usuario
        this.usuario.getFriendRequests().remove(usuario);
    }
    
    //Funcion que una solicitud de amistad invoca para rechazar su propia solicitud
    public void rechazoSolicitud(String usuario){
        try {
            server.rejectFriendRequest(this.usuario.getUsername(), usuario, contrasena);
        } catch (RemoteException ex) {
            Logger.getLogger(GUIChats.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Se busca al panel de la solicitud para eliminarlo
        GUISolicitudAmistad buscando=null;
        for(GUISolicitudAmistad panel: panelesSolicitudes){
            if(panel.getUsuario().equals(usuario)){
                panelesSolicitudes.remove(panel);
                PanelSolicitudes.remove(panel);
                break;
            }
        }
        
        PestanaSolicitudes.revalidate();PestanaSolicitudes.repaint();
        
        //Se elimina la solicitud de la estructura del usuario
        this.usuario.getFriendRequests().remove(usuario);
    }
    
    //Esta funcion devuelve una direccion RMI actualizada de un usuario a partir de su nombre
    private String buscarRMI(String usuario){
        IPeer objetoRemoto=null;
        
        if(referencias.containsKey(usuario) && server!=null){
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
            
        }else if(server!=null){
            //Si no hay referencia, se obtiene y se guarda
            try {
                String nuevaReferencia=this.server.startChat(this.usuario.getUsername(), usuario, this.contrasena);
                objetoRemoto = (IPeer) Naming.lookup(nuevaReferencia);
                this.referencias.put(usuario, nuevaReferencia);
            } catch (Exception ex1) {
                Logger.getLogger(GUIChats.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
        
        if(objetoRemoto==null)
            return null;
        return this.referencias.get(usuario);
    }
    
    //Funcion que un GUIPanelNotificacion invoca para borrarse a si mismo
    public void notificacionOk(String usuario){
        //Se busca el panel y se elimina
        GUIPanelNotificacion buscando=null;
        for(GUIPanelNotificacion panel:this.panelesNotificaciones){
            if(panel.getNombre().equals(usuario)){
                panelesNotificaciones.remove(panel);
                PanelNotificaciones.remove(panel);
                break;
            }
        }
    }
    
    //Funcion que anade una notificacion sobre una solicitud de amistad anteriormente enviada
    public void anadirNotificacion(String usuario,Boolean aceptada){
        GUIPanelNotificacion panel=new GUIPanelNotificacion(this,usuario,aceptada);
        //Se anade a la estructura de datos
        this.panelesNotificaciones.add(panel);
        //Se anade a la interfaz grafica
        PanelNotificaciones.add(panel);
        
        ScrollPaneNotificaciones.revalidate();ScrollPaneNotificaciones.repaint();
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
        TabPanelSolicitudes = new javax.swing.JTabbedPane();
        ScrollPaneSolicitudes = new javax.swing.JScrollPane();
        PanelSolicitudes = new javax.swing.JPanel();
        ScrollPaneNotificaciones = new javax.swing.JScrollPane();
        PanelNotificaciones = new javax.swing.JPanel();
        TabPaneGestion = new javax.swing.JTabbedPane();
        PestanaAnadirAmigo = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        CampoNombreSolicitud = new javax.swing.JTextField();
        BotonEnviarSolicitud = new javax.swing.JButton();
        TextoSolicitudEnviada = new javax.swing.JTextField();
        PestanaAnadirAmigo1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        CampoNombreEliminar = new javax.swing.JTextField();
        BotonEnviarSolicitud1 = new javax.swing.JButton();
        TextoSolicitudEliminar = new javax.swing.JTextField();
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

        javax.swing.GroupLayout PanelSolicitudesLayout = new javax.swing.GroupLayout(PanelSolicitudes);
        PanelSolicitudes.setLayout(PanelSolicitudesLayout);
        PanelSolicitudesLayout.setHorizontalGroup(
            PanelSolicitudesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 382, Short.MAX_VALUE)
        );
        PanelSolicitudesLayout.setVerticalGroup(
            PanelSolicitudesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 460, Short.MAX_VALUE)
        );

        ScrollPaneSolicitudes.setViewportView(PanelSolicitudes);

        TabPanelSolicitudes.addTab("Solicitudes Pendientes", ScrollPaneSolicitudes);

        javax.swing.GroupLayout PanelNotificacionesLayout = new javax.swing.GroupLayout(PanelNotificaciones);
        PanelNotificaciones.setLayout(PanelNotificacionesLayout);
        PanelNotificacionesLayout.setHorizontalGroup(
            PanelNotificacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 382, Short.MAX_VALUE)
        );
        PanelNotificacionesLayout.setVerticalGroup(
            PanelNotificacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 460, Short.MAX_VALUE)
        );

        ScrollPaneNotificaciones.setViewportView(PanelNotificaciones);

        TabPanelSolicitudes.addTab("Solicitudes enviadas", ScrollPaneNotificaciones);

        PestanaSolicitudes.setViewportView(TabPanelSolicitudes);

        Pestanas.addTab("Solicitudes de Amistad", PestanaSolicitudes);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Perfil.png"))); // NOI18N

        jTextField1.setEditable(false);
        jTextField1.setText("Introduzca el nombre:");
        jTextField1.setBorder(null);
        jTextField1.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        BotonEnviarSolicitud.setBackground(new java.awt.Color(242, 242, 242));
        BotonEnviarSolicitud.setText("Enviar Solicitud");
        BotonEnviarSolicitud.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        BotonEnviarSolicitud.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonEnviarSolicitudActionPerformed(evt);
            }
        });

        TextoSolicitudEnviada.setEditable(false);
        TextoSolicitudEnviada.setText("Solicitud enviada correctamente");
        TextoSolicitudEnviada.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout PestanaAnadirAmigoLayout = new javax.swing.GroupLayout(PestanaAnadirAmigo);
        PestanaAnadirAmigo.setLayout(PestanaAnadirAmigoLayout);
        PestanaAnadirAmigoLayout.setHorizontalGroup(
            PestanaAnadirAmigoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PestanaAnadirAmigoLayout.createSequentialGroup()
                .addGroup(PestanaAnadirAmigoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PestanaAnadirAmigoLayout.createSequentialGroup()
                        .addGap(140, 140, 140)
                        .addComponent(jLabel1))
                    .addGroup(PestanaAnadirAmigoLayout.createSequentialGroup()
                        .addGap(115, 115, 115)
                        .addComponent(BotonEnviarSolicitud, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PestanaAnadirAmigoLayout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addGroup(PestanaAnadirAmigoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(TextoSolicitudEnviada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(PestanaAnadirAmigoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(PestanaAnadirAmigoLayout.createSequentialGroup()
                                    .addGap(49, 49, 49)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(CampoNombreSolicitud, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(86, Short.MAX_VALUE))
        );
        PestanaAnadirAmigoLayout.setVerticalGroup(
            PestanaAnadirAmigoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PestanaAnadirAmigoLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel1)
                .addGap(38, 38, 38)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(CampoNombreSolicitud, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addComponent(BotonEnviarSolicitud, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(TextoSolicitudEnviada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(66, Short.MAX_VALUE))
        );

        TabPaneGestion.addTab("Enviar Solicitud", PestanaAnadirAmigo);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Perfil.png"))); // NOI18N

        jTextField2.setEditable(false);
        jTextField2.setText("Introduzca el nombre:");
        jTextField2.setBorder(null);
        jTextField2.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        BotonEnviarSolicitud1.setBackground(new java.awt.Color(242, 242, 242));
        BotonEnviarSolicitud1.setText("Eliminar Amigo");
        BotonEnviarSolicitud1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        BotonEnviarSolicitud1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonEnviarSolicitud1ActionPerformed(evt);
            }
        });

        TextoSolicitudEliminar.setEditable(false);
        TextoSolicitudEliminar.setText("Solicitud enviada correctamente");
        TextoSolicitudEliminar.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        javax.swing.GroupLayout PestanaAnadirAmigo1Layout = new javax.swing.GroupLayout(PestanaAnadirAmigo1);
        PestanaAnadirAmigo1.setLayout(PestanaAnadirAmigo1Layout);
        PestanaAnadirAmigo1Layout.setHorizontalGroup(
            PestanaAnadirAmigo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PestanaAnadirAmigo1Layout.createSequentialGroup()
                .addGroup(PestanaAnadirAmigo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PestanaAnadirAmigo1Layout.createSequentialGroup()
                        .addGap(140, 140, 140)
                        .addComponent(jLabel2))
                    .addGroup(PestanaAnadirAmigo1Layout.createSequentialGroup()
                        .addGap(115, 115, 115)
                        .addComponent(BotonEnviarSolicitud1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PestanaAnadirAmigo1Layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addGroup(PestanaAnadirAmigo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(TextoSolicitudEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(PestanaAnadirAmigo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(PestanaAnadirAmigo1Layout.createSequentialGroup()
                                    .addGap(49, 49, 49)
                                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(CampoNombreEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(86, Short.MAX_VALUE))
        );
        PestanaAnadirAmigo1Layout.setVerticalGroup(
            PestanaAnadirAmigo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PestanaAnadirAmigo1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel2)
                .addGap(38, 38, 38)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(CampoNombreEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addComponent(BotonEnviarSolicitud1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(TextoSolicitudEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(66, Short.MAX_VALUE))
        );

        TabPaneGestion.addTab("Eliminar Amigo", PestanaAnadirAmigo1);

        Pestanas.addTab("Gestionar Amigos", TabPaneGestion);
        TabPaneGestion.getAccessibleContext().setAccessibleName("Eliminar Amigo");

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
        BotonEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotonEnviarActionPerformed(evt);
            }
        });

        ScrollPanelConversacion.setBackground(new java.awt.Color(240, 245, 245));
        ScrollPanelConversacion.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        PanelConversacion.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

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
                    .addComponent(ScrollPanelConversacion, javax.swing.GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
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
                System.exit(0);
        } catch (RemoteException ex) {
            Logger.getLogger(GUIChats.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowClosed

    private void BotonEnviarSolicitudActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonEnviarSolicitudActionPerformed
        String amigo=CampoNombreSolicitud.getText();
        
        //si aun no es amigo del usuario
        if(!this.usuario.getFriends().contains(amigo)){
            try {
                this.server.sendFriendRequest(this.usuario.getUsername(), amigo, contrasena);

                TextoSolicitudEnviada.setText("Solicitud enviada correctamente");
                TextoSolicitudEnviada.setVisible(true);
            } catch (RemoteException ex) {
                TextoSolicitudEnviada.setText("No se pudo enviar la solicitud");
                TextoSolicitudEnviada.setVisible(true);
            }
        }
    }//GEN-LAST:event_BotonEnviarSolicitudActionPerformed

    private void BotonEnviarSolicitud1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonEnviarSolicitud1ActionPerformed
        String examigo=CampoNombreEliminar.getText();
        
        //Solo si era amigo del usuario
        if(this.usuario.getFriends().contains(examigo)){
            try {
                this.server.removeFriend(this.usuario.getUsername(), examigo, contrasena);

                TextoSolicitudEliminar.setText("Solicitud enviada correctamente");
                TextoSolicitudEliminar.setVisible(true);
            } catch (RemoteException ex) {
                TextoSolicitudEliminar.setText("No se pudo enviar la solicitud");
                TextoSolicitudEliminar.setVisible(true);
            }
        }else{
            TextoSolicitudEliminar.setText("Esta persona no es un amigo suyo");
            TextoSolicitudEliminar.setVisible(true);
        }
    }//GEN-LAST:event_BotonEnviarSolicitud1ActionPerformed

    private void BotonEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BotonEnviarActionPerformed
        //Se construye el mensaje
        String contenido=CampoMensaje.getText();
        Mensaje mensaje=new Mensaje(contenido,this.usuario.getUsername());
        
        //solo si hay alguna conversacion seleccionada
        if(this.chatActual!=null){
            //Se obtiene la direccion del otro usuario
            String direccion= this.buscarRMI(this.chatActual);

            //Para enviar el mensaje el usuario debe ser accesible
            if(direccion!=null && this.usuario.getFriendsConnected().contains(this.chatActual)){
                try {
                    //Se envia el mensaje
                    IPeer usuarioRemoto=(IPeer) Naming.lookup(direccion);
                    usuarioRemoto.recibirMensaje(this.usuario.getUsername(), mensaje);System.out.println(this.usuario.getUsername());
                    
                    //Se actualiza la informacion a la GUI local
                    CampoMensaje.setText("");
                    this.anadirMensaje(this.chatActual, mensaje);
                    
                } catch (NotBoundException ex) {
                    Logger.getLogger(GUIChats.class.getName()).log(Level.SEVERE, null, ex);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(GUIChats.class.getName()).log(Level.SEVERE, null, ex);
                } catch (RemoteException ex) {
                    Logger.getLogger(GUIChats.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                //Se muestra que algo salio mal, el boton aparecera pulsado durante 3 segundos
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GUIChats.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_BotonEnviarActionPerformed

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
    private javax.swing.JButton BotonEnviarSolicitud1;
    private javax.swing.JTextArea CampoMensaje;
    private javax.swing.JTextField CampoNombreEliminar;
    private javax.swing.JTextField CampoNombreSolicitud;
    private javax.swing.JPanel PanelAmigos;
    private javax.swing.JPanel PanelConversacion;
    private javax.swing.JPanel PanelNotificaciones;
    private javax.swing.JPanel PanelSolicitudes;
    private javax.swing.JScrollPane PestanaAmigos;
    private javax.swing.JPanel PestanaAnadirAmigo;
    private javax.swing.JPanel PestanaAnadirAmigo1;
    private javax.swing.JScrollPane PestanaSolicitudes;
    private javax.swing.JTabbedPane Pestanas;
    private javax.swing.JScrollPane ScrollPaneNotificaciones;
    private javax.swing.JScrollPane ScrollPaneSolicitudes;
    private javax.swing.JScrollPane ScrollPanelConversacion;
    private javax.swing.JTabbedPane TabPaneGestion;
    private javax.swing.JTabbedPane TabPanelSolicitudes;
    private javax.swing.JTextField TextoSolicitudEliminar;
    private javax.swing.JTextField TextoSolicitudEnviada;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
