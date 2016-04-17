import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * @author Benjamin Saint-Sever.
 */
public interface iNœud extends Remote {


    public int get(int key) throws RemoteException;


    public void deleteValue(int key) throws RemoteException;


    public void insertValue(int key, int value) throws RemoteException;


    public ServeurNode localiser(int key) throws RemoteException;


    public ArrayList<Integer> notifyInsertionSucesseur(Nœud nœudPredecesseur)
            throws RemoteException;


}


