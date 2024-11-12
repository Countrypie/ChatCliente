package paquete.chatcliente;

import GUI.GUIChats;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

//Objeto del cliente que implementa las llamadas remotas
public class CallbackCliente extends UnicastRemoteObject implements ICallbackCliente, IPeer{
    
    private User usuarioCliente=null;
    private GUIChats chats=null;

    public CallbackCliente(User usuario) throws RemoteException {
        super();
        
        this.usuarioCliente=usuario;
    }
    public CallbackCliente() throws RemoteException {
        super();
    }

    @Override
    public void amigoConectado(User usuario) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void amigoDesconectado(User usuario) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void enviarMensaje(User usuario, String mensaje) {
        System.out.println("Mensaje de "+usuario.getUsername()+": "+mensaje);
    }

    @Override
    public void amigoNuevo(User usuario) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void amigoEliminado(User usuario) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void solicitudAmistadNueva(User usuario) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void solicitudAmistadAceptada(User usuario) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void solicitudAmistadRechazada(User usuario) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setUsuario(User usuarioCliente) {
        this.usuarioCliente = usuarioCliente;
    }

    public User getUsuarioCliente() {
        return usuarioCliente;
    }

    public void setChats(GUIChats chats) {
        this.chats = chats;
    }

    
    

}
