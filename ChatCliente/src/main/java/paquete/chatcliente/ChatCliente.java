package paquete.chatcliente;

import GUI.GUIIniciarSesion;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import socrates.server.CallbackServerInterface;

//Aplicacion con la ejecucion principal en la parte de cliente y el servidor de objetos
public class ChatCliente {
    
    //Informacion sobre la localizacion del servidor de objetos
    private static final int RMIPORT = 1099;
    private static final String HOSTNAME = "localhost";
    public static String REGISTRY_URL = "rmi://" + HOSTNAME + ":" + RMIPORT + "/callback";
    
    //Configuracion del puerto que usara el usuario
    private static final int PUERTOCLIENTE = 1099;
    private static final String DIRECCIONCLIENTE = "rmi://localhost:" + PUERTOCLIENTE + "/ChatCliente";
    
    
    public static void main(){
        
        //Se inicia la conexion con el servidor de objetos
        CallbackServerInterface server=null;
        try{
            server = (CallbackServerInterface) Naming.lookup(REGISTRY_URL);
        }catch(Exception ex){
            System.out.println("No se ha podido conectar con el servidor de objetos.");
            System.exit(1);
        }
    
        //Se crea una pestana de inicio de sesion
        //Se pasara un usuario por referencia para obtener informacion del servidor
        User usuarioActual=null;
        GUIIniciarSesion iniciarSesion = new GUIIniciarSesion(server,usuarioActual);
        
        //Se espera a que se cierre la pestana
        while(iniciarSesion.isVisible()){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        if(usuarioActual==null){
            System.out.println("Error, no se ha podido iniciar sesion correctamente");
            System.exit(1);
        }
        
        //Se crea el cliente que se usara
        CallbackCliente clienteActual=null;
        try{
            clienteActual=new CallbackCliente(usuarioActual);
        }catch(Exception ex){
            System.out.println("No se ha podido crear un cliente remoto.");
            //!server.logOut(, );
            System.exit(1);
        }
        
        //Se registra el nuevo cliente en un nuevo servidor de objetos
        try{
            registrarObjeto(clienteActual);
        }catch(Exception ex){
            System.out.println("No se ha podido conectar con el servidor de objetos.");
            //!Server.logOut(,);
            System.exit(1);
        }
        
    
    }
    
    //Funcion que registra el objeto en el servidor de objetos del cliente
    //Si dicho servidor de objetos no existe tambien se crea
    private static void registrarObjeto(CallbackCliente cliente) throws RemoteException, MalformedURLException{
    
        //Se crea el servidor de objetos del cliente
        try {
            Registry registry= LocateRegistry.getRegistry(PUERTOCLIENTE);
            registry.list( );
            // The above call will throw an exception
            // if the registry does not already exist
        }
        catch (RemoteException ex) {
            // No valid registry at that port.
            System.out.println(
            "RMI registry cannot be located at port " + PUERTOCLIENTE);
            Registry registry= LocateRegistry.createRegistry(PUERTOCLIENTE);
            System.out.println(
            "RMI registry created at port " + PUERTOCLIENTE);
        }
         
        //Se registra el objeto en el servidor de objetos del cliente
        Naming.rebind(DIRECCIONCLIENTE, cliente);
        System.out.println("ChatCliente listo.");

         
    }
    
}
