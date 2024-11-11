package paquete.chatcliente;

import java.rmi.RemoteException;

public interface IPeer extends java.rmi.Remote{
    
    //Metodo que otro usuario invoca para enviar un mensaje a este cliente
    public void enviarMensaje(User usuario, String mensaje) throws RemoteException;
    
}
