package paquete.chatcliente;

//Interfaz del cliente para las llamadas remotas
public interface ICallbackCliente {
 
    //Metodo que otro usuario invoca para enviar un mensaje a este cliente
    public void enviarMensaje(Usuario usuario, String mensaje);
    
    //Metodo que el servidor invoca para informar que un usuario se ha conectado
    public void amigoConectado(Usuario usuario);
    
    //Metodo que el servidor invoca para informar que un usuario se ha desconectado
    public void amigoDesconectado(Usuario usuario);
    
    //Metodo que el servidor invoca para preguntar si el cliente acepta la solicitud
        //de amistad de otro usuario. Devuelve True si acepta y False si rechaza
    public Boolean solicitudAmistadNueva(Usuario usuario);
    
    //Metodo que el servidor invoca para informar al cliente que el y el nuevo
        //usuario han sido registrados como amigos
    public void amigoNuevo(Usuario usuario);
    
    //Metodo que el servidor invoca para informar al cliente de que un usuario 
        //ha cancelado su amistad
    public void amigoEliminado(Usuario usuario);
    
}
