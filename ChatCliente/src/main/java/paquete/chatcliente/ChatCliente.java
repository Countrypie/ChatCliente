package paquete.chatcliente;

//Aplicacion con la ejecucion principal en la parte de cliente

import java.rmi.Naming;
import java.rmi.RemoteException;

public class ChatCliente {
    
    //Informacion sobre la localizacion del servidor de objetos para el callback de este cliente
    private static final int RMIPORT = 1099;
    private static final String HOSTNAME = "localhost";
    public static String REGISTRY_URL = "rmi://" + HOSTNAME + ":" + RMIPORT + "/callback";//!cambiar si tal el nombre callback
    
    
    public static void main(){
        
        //Se inicia la conexion con el servidor de objetos
        try{
            /*!IServidor*/ h = (/*!IServidor*/) Naming.lookup(REGISTRY_URL);
        }catch(Exception ex){
            System.out.println("No se ha podido conectar con el servidor de objetos.");
            System.exit(1);
        }
    
        //!Crear pestana de inicio de sesion
        
        
        //!Se obtiene un usuario
        
        //Se crea el cliente que se usara
        try{
            CallbackCliente clienteActual=new CallbackCliente(/*!usuario*/);
        }catch(RemoteException ex){
            System.out.println("No se ha podido crear un cliente remoto.");
        }
        
        //Se registra el nuevo cliente en un nuevo servidor de objetos
        
        
    
    }
    
}
