package paquete.chatcliente;

//Interfaz del cliente para las llamadas remotas del servidor

import java.rmi.RemoteException;

public interface ICallbackCliente extends java.rmi.Remote{
    
    //Metodo que el servidor invoca para informar que un usuario se ha conectado
    public void amigoConectado(String usuario) throws RemoteException;
    
    //Metodo que el servidor invoca para informar que un usuario se ha desconectado
    public void amigoDesconectado(String usuario) throws RemoteException;
    
    //Metodo que el servidor invoca para informar de una nueva solicitud de amistad
    public void solicitudAmistadNueva(String usuario) throws RemoteException;a;//!Se asume que ya sera amigo, o se espera a amigoNuevo?
    
    //Metodo que el servidor invoca para informar al cliente que el y el nuevo
        //usuario han sido registrados como amigos
    public void amigoNuevo(String usuario) throws RemoteException;
    
    //Metodo que el servidor invoca para informar al cliente de que un usuario 
        //ha cancelado su amistad
    public void amigoEliminado(String usuario) throws RemoteException;
    
    //Metodo que el servidor invoca para informar que una solicitud de amistad
        //anteriormente mandada a otro usuario fue aceptada
    public void solicitudAmistadAceptada(String usuario) throws RemoteException;a;//!Con amigoNuevo, ambas a parte o juntas?
    
    //Metodo que el servidor invoca para informar que una solicitud de amistad
        //anteriormente mandada a otro usuario fue rechazada
    public void solicitudAmistadRechazada(String usuario) throws RemoteException;a;
    
}
