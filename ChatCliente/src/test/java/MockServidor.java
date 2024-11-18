


import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author xavi
 */
public class MockServidor {
    
    private static final int RMIPORT = 1099;
    private static final String HOSTNAME = "localhost";
    public static String REGISTRY_URL = "rmi://" + HOSTNAME + ":" + RMIPORT + "/callback";
    
     public static void main(String[] args){

        try{
            // code for port number value to be supplied
            MockServidorImp exportedObj = new MockServidorImp();
            startRegistry(RMIPORT);
            // register the object under the name “some”
            String registryURL = "rmi://localhost:" + RMIPORT + "/callback";
            Naming.rebind(registryURL, exportedObj);
            System.out.println("Servidor listo.");

        }catch(Exception ex){
                System.out.println(ex.getMessage());
        }
    }
    
    // This method starts a RMI registry on the local host, if it
    // does not already exists at the specified port number.
    private static void startRegistry(int RMIPortNum) throws RemoteException{
        try {
            Registry registry= LocateRegistry.getRegistry(RMIPortNum);
            registry.list( );
            // The above call will throw an exception
            // if the registry does not already exist
        }
        catch (RemoteException ex) {
            // No valid registry at that port.
            System.out.println(
            "RMI registry cannot be located at port " + RMIPortNum);
            Registry registry= LocateRegistry.createRegistry(RMIPortNum);
            System.out.println(
            "RMI registry created at port " + RMIPortNum);
        }
    }
    
}
