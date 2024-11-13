package socrates.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import paquete.chatcliente.ICallbackCliente;

import socrates.user.User;

public interface CallbackServerInterface extends Remote {
    // TODO think about the return types of the methods. Is it necessary to return a User?
    User login(ICallbackCliente client, String name, String password) throws RemoteException; // logs in a user
    boolean logOut(String name, String password) throws RemoteException; // logs out a user

    User register (ICallbackCliente client, String name, String password) throws RemoteException; // registers a user
    boolean deleteAccount(String name, String password) throws RemoteException; // deletes an account

    ArrayList<String> obtainFriendList(String name, String password) throws RemoteException; // obtains friend list
    ArrayList<String> obtainConnectedFriendList(String name, String password) throws RemoteException; // obtains connected friend list
    boolean removeFriend(String name, String friendName, String password) throws RemoteException; // removes a friend

    boolean sendFriendRequest(String name, String friendName, String password) throws RemoteException; // adds a friend
    boolean acceptFriendRequest(String name, String friendName, String password) throws RemoteException; // accepts a friend request
    boolean rejectFriendRequest(String name, String friendName, String password) throws RemoteException; // rejects a friend request

    ArrayList<String> obtainFriendRequests(String name, String password) throws RemoteException; // obtains friend requests

    boolean changePassword(String name, String password, String newPassword) throws RemoteException; // changes password

    // metodo para iniciar chat con un amigo. Por ahora solo devuelve la interfaz para mandar mensajes
    // MessageInterface startChat(String name, String friendName, String password) throws RemoteException;

    // (el servidor debe proveer de las claves cifradas y las referencias)
    // igual hace falta una clase que almacene el nombre del amigo y la clave de cifrado para poder devolver ese
    // objeto y que el cliente pueda iniciar el chat o una clase "sobre digital"
}
