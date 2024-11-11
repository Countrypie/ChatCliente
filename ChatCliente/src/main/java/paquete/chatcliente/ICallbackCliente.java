package paquete.chatcliente;

//Interfaz del cliente para las llamadas remotas del servidor

import java.rmi.RemoteException;

public interface ICallbackCliente extends java.rmi.Remote{
    
    //Metodo que el servidor invoca para informar que un usuario se ha conectado
    public void amigoConectado(User usuario) throws RemoteException;
    
    //Metodo que el servidor invoca para informar que un usuario se ha desconectado
    public void amigoDesconectado(User usuario) throws RemoteException;
    
    //Metodo que el servidor invoca para informar de una nueva solicitud de amistad
    public void solicitudAmistadNueva(User usuario) throws RemoteException;
    
    //Metodo que el servidor invoca para informar al cliente que el y el nuevo
        //usuario han sido registrados como amigos
    public void amigoNuevo(User usuario) throws RemoteException;
    
    //Metodo que el servidor invoca para informar al cliente de que un usuario 
        //ha cancelado su amistad
    public void amigoEliminado(User usuario) throws RemoteException;
    
    //Metodo que el servidor invoca para informar que una solicitud de amistad
        //anteriormente mandada a otro usuario fue aceptada
    public void solicitudAmistadAceptada(User usuario) throws RemoteException;
    
    //Metodo que el servidor invoca para informar que una solicitud de amistad
        //anteriormente mandada a otro usuario fue rechazada
    public void solicitudAmistadRechazada(User usuario) throws RemoteException;
    
}
