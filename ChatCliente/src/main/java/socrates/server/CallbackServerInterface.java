package socrates.server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import paquete.chatcliente.User;
import paquete.chatcliente.ICallbackCliente;


public interface CallbackServerInterface extends Remote {

    User logIn(ICallbackCliente client, String name, String password) throws RemoteException; // logs in a user
    boolean logOut(String name, String password) throws RemoteException; // logs out a user

    User register (ICallbackCliente client, String name, String password) throws RemoteException; // registers a user
    boolean deleteAccount(String name, String password) throws RemoteException; // deletes an account

    boolean addFriend(String name, String friendName, String password) throws RemoteException; // adds a friend
    boolean removeFriend(String name, String friendName, String password) throws RemoteException; // removes a friend

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
