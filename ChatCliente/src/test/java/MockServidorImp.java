

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import paquete.chatcliente.ICallbackCliente;
import socrates.server.CallbackServerInterface;
import socrates.user.User;

/**
 *
 * @author xavi
 */
public class MockServidorImp extends UnicastRemoteObject implements CallbackServerInterface{

    public MockServidorImp() throws RemoteException{
        super();
    }
    
    @Override
    public User login(ICallbackCliente client, String name, String password) throws RemoteException {
        System.out.println("Solicitado login "+name);
        User usuario=new User(client,name);
        usuario.setClient(client);
        usuario.setConnected(true);
        usuario.setFriendRequests(new ArrayList<>());
        ArrayList<String> friendsC=new ArrayList<>();
        ArrayList<String> friends=new ArrayList<>();
        if(name.equals("Pepito")){
            friends.add("Jose");
            friendsC.add("Jose");
        }else{
            friends.add("Pepito");
            friendsC.add("Pepito");
        }
        usuario.setFriends(friends);
        usuario.setFriendsConnected(friendsC);
        return usuario;
    }

    @Override
    public boolean logOut(String name, String password) throws RemoteException {
        System.out.println("Logout");
        return true;
    }

    @Override
    public User register(ICallbackCliente client, String name, String password) throws RemoteException {
        System.out.println("Registrado");
        
        return this.login(client, name, password);
    }

    @Override
    public boolean deleteAccount(String name, String password) throws RemoteException {
        System.out.println("Borrando cuenta");
        return true;
    }

    @Override
    public ArrayList<String> obtainFriendList(String name, String password) throws RemoteException {
        return new ArrayList<>();
    }

    @Override
    public ArrayList<String> obtainConnectedFriendList(String name, String password) throws RemoteException {
        return new ArrayList<>();
    }

    @Override
    public boolean removeFriend(String name, String friendName, String password) throws RemoteException {
        System.out.println("Amigo borrado");
        return true;
    }

    @Override
    public boolean sendFriendRequest(String name, String friendName, String password) throws RemoteException {
        System.out.println("Solicitud enviada");
        return true;
    }

    @Override
    public boolean acceptFriendRequest(String name, String friendName, String password) throws RemoteException {
        System.out.println("Amigo aceptado");
        return true;
    }

    @Override
    public boolean rejectFriendRequest(String name, String friendName, String password) throws RemoteException {
        System.out.println("Amigo rechazado");
        return true;
    }

    @Override
    public ArrayList<String> obtainFriendRequests(String name, String password) throws RemoteException {
        return new ArrayList<>();
    }

    @Override
    public boolean changePassword(String name, String password, String newPassword) throws RemoteException {
        System.out.println("Contrasena cambiada");
        return true;
    }

    @Override
    public String startChat(String name, String friendName, String password) throws RemoteException {
        return "rmi://localhost/"+friendName;
    }

    @Override
    public boolean updateRMIAddress(String name, String password, ICallbackCliente client) {
        System.out.println("Actualizando RMI");
        return true;
    }

    
    
}
