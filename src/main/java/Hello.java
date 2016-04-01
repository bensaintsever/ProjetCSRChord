/**
 * Created by benjaminsaint-sever on 24/03/2016.
 */

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Hello extends Remote {
    String sayHello() throws RemoteException;
}