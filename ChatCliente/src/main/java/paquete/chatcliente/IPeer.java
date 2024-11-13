package paquete.chatcliente;

import java.rmi.RemoteException;
import socrates.user.User;

public interface IPeer extends java.rmi.Remote{
    
    //Metodo que otro usuario invoca para enviar un mensaje a este cliente
    public void recibirMensaje(String nombreUsuario, Mensaje mensaje) throws RemoteException;
    
}
