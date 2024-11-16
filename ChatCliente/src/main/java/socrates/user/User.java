package socrates.user;


import java.io.Serializable;
import java.util.ArrayList;
import paquete.chatcliente.ICallbackCliente;

public class User implements Serializable{
    private ICallbackCliente client;
    private String username;
    private ArrayList<String> friends;
    private ArrayList<String> friendsConnected;
    private ArrayList<String> friendRequests;
    private boolean connected;
    private final static String BASE_ADDRESS = "rmi://localhost:1099/";
    // private MessageInterface messageInterface;

    // Constructor
    public User(ICallbackCliente client, String username) {
        this.username = username;
        this.friends = new ArrayList<>();
        this.friendsConnected = new ArrayList<>();
        this.friendRequests = new ArrayList<>();
        this.connected = false;
    }


    public User(ICallbackCliente client, String username, ArrayList<String> friends, ArrayList<String> friendsConnected, ArrayList<String> friendRequests) {
        this.client = client;
        this.username = username;
        this.friends = friends;
        this.friendsConnected = friendsConnected;
        this.friendRequests = friendRequests;
        this.connected = true;
    }

    // Getters and Setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<String> friends) {
        this.friends = friends;
    }

    public ArrayList<String> getFriendsConnected() {
        return friendsConnected;
    }

    public void setFriendsConnected(ArrayList<String> friendsConnected) {
        this.friendsConnected = friendsConnected;
    }

    public ArrayList<String> getFriendRequests() {
        return friendRequests;
    }

    public void setFriendRequests(ArrayList<String> friendRequests) {
        this.friendRequests = friendRequests;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public ICallbackCliente getClient() {
        return client;
    }

    public void setClient(ICallbackCliente client) {
        this.client = client;
    }

    // Get the user's RMI address
    public String getRMIAddress() {
        return BASE_ADDRESS + getUsername();
    }

    /** --- Methods --- **/

    /// Friend list

    // Add a friend to the user's friends list
    public void addFriend(String friend) {
        friends.add(friend);
    }

    // Remove a friend from the user's friends list
    public void removeFriend(String friend) {
        friends.remove(friend);
    }

    // Add a connected friend to the user's friend list
    public void addConnectedFriend(String friend) {
        if (friends.contains(getUsername())) {
            friendsConnected.add(getUsername());
        }
    }

    // Remove a connected friend from the user's friend list
    public void removeConnectedFriend(String friend) {
        if (friends.contains(getUsername())) {
            friendsConnected.remove(getUsername());
        }
    }

    // Check if the user has a friend
    public boolean hasFriend(String friend) {
        return friends.contains(friend);
    }

    // Check if the user has any friends
    public boolean hasFriends() {
        return !friends.isEmpty();
    }

    // Check if the user has any connected friends
    public boolean hasConnectedFriends() {
        return !friendsConnected.isEmpty();
    }


    /// Friend requests

    // Add a friend request to the user's friend requests list
    public void addFriendRequest(String friend) {
        friendRequests.add(friend);
    }

    // Remove a friend request from the user's friend requests list
    public void removeFriendRequest(String friend) {
        friendRequests.remove(friend);
    }

    // Check if the user has a friend request from a specific user
    public boolean hasFriendRequest(String friend) {
        return friendRequests.contains(friend);
    }

    // Check if the user has any friend requests
    public boolean hasFriendRequests() {
        return !friendRequests.isEmpty();
    }


    /// Connection

    // (etc)
}
