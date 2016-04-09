package TP1; /**
 * Created by benjaminsaint-sever on 24/03/2016.
 */
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class Server{

    public Server() {
    }
    public static void main(String args[]) {

        try {
            Bank obj = new Bank();

            iBank stub = (iBank) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();

            registry.bind("TP1.iBank",stub);

            System.err.println("TP1.Server ready");
        } catch (Exception e) {
            System.err.println("TP1.Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}