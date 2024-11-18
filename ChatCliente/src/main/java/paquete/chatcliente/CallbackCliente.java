package paquete.chatcliente;

import GUI.GUIChats;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import socrates.server.CallbackServerInterface;
import socrates.user.User;

//Objeto del cliente que implementa las llamadas remotas
public class CallbackCliente extends UnicastRemoteObject implements ICallbackCliente, IPeer{
    
    private User usuario=null;
    private CallbackServerInterface server=null;
    private GUIChats chats=null;
    private String contrasena=null;

    public CallbackCliente(CallbackServerInterface server, User usuario) throws RemoteException {
        super();
        
        this.server=server;
        this.usuario=usuario;
    }
    public CallbackCliente() throws RemoteException {
        super();
    }

    @Override
    public void amigoConectado(String nombreUsuario) {
        this.chats.amigoConectado(nombreUsuario);
    }

    @Override
    public void amigoDesconectado(String nombreUsuario) {
        this.chats.amigoDesconectado(nombreUsuario);
    }

    @Override
    public void recibirMensaje(String remitente, Mensaje mensaje) {
        
        this.chats.anadirMensaje(remitente, mensaje);
    }

    @Override
    public void amigoNuevo(String nombreUsuario) {
        this.chats.anadirAmigo(nombreUsuario);
    }

    @Override
    public void amigoEliminado(String nombreUsuario) {
        this.chats.eliminarAmigo(nombreUsuario);
    }

    @Override
    public void solicitudAmistadNueva(String nombreUsuario) {
        this.chats.anadirSolicitudAmistad(nombreUsuario);
    }

    @Override
    public void solicitudAmistadAceptada(String nombreUsuario) {
        this.chats.anadirNotificacion(nombreUsuario, true);
    }

    @Override
    public void solicitudAmistadRechazada(String nombreUsuario) {
        this.chats.anadirNotificacion(nombreUsuario, false);
    }

    public void setUsuario(User usuarioCliente) {
        this.usuario = usuarioCliente;
    }

    public User getUsuario() {
        return usuario;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getContrasena() {
        return contrasena;
    }
    
    
    
    
    //Funcion que complementa al constructor de GUIChats para darle la informacion del servidor
    public void setupChats(GUIChats chats) {
        //try {
            //Se asocia la ventana a este cliente
            this.chats = chats;
            
            /*//!Si no se hace ya
            //Se obtiene la lista de amigos
            usuario.setFriends(server.obtainFriendList(usuario.getUsername(), contrasena));
            //Se obtiene la lista de amigos conectados
            usuario.setFriendsConnected(server.obtainConnectedFriendList(usuario.getUsername(), contrasena));
            //Se obtienen las solicitudes de amistad
            usuario.setFriendRequests(server.obtainFriendRequests(usuario.getUsername(), contrasena));*/
            
            //Se pasa informacion del usuario a la ventana
            chats.setup(usuario,contrasena,server);
            
            //Se hace visible a la ventana
            chats.setVisible(true);
            
        /*} catch (RemoteException ex) {
            Logger.getLogger(CallbackCliente.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
    }
}
