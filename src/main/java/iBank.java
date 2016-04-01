import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.logging.Logger;

public interface iBank extends Remote {
	public void ajouterSurCompte(int id_compte, int val) throws RemoteException;
	public void enleverSurCompte(int id_compte, int val) throws RemoteException;
	public void transfertEntreCompte(int c1, int c2, int val) throws RemoteException;
	public Integer getValeurCompte(int id_compte) throws RemoteException;
	public void setValeurCompte(int id_compte, int val) throws RemoteException;
	public int creerCompte() throws RemoteException;
	public Logger getLog() throws RemoteException;
}