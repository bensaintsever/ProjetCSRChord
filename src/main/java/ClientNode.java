import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

/**
 * Classe permettant à un nœud d'interragir avec ses nœuds voisins.
 *
 * @author Benjamin Saint-Sever
 */
public class ClientNode {
    private static final int MAXKEY = 65535;
    private static final int MINKEY = 0;
    private int idNœud;
    private String adresseIP;
    private Registry registry;
    private iNœud stub;

    public ClientNode() {
        idNœud = MINKEY;
        this.adresseIP = "";
        this.registry = null;
        this.stub = null;
    }


    /**
     * Méthode à utiliser lors de l'insertion d'un nouveau nœud, on cherche à
     * communiquer le nœud response de la clée, on commence par interroger un
     * nœud qui transmettra à ses voisins jusqu'a trouver le nœud responsable.
     * Ce dernier contactera notre nœud pour lui affecter les responsabilités
     * des données lié à la clée et devient le successeur de notre nœud.
     * Le predecesseur du nœud interrogé devient notre predecesseur, et on
     * devient predecesseur du nœud contacté.
     *
     * @param keyNode
     */
    public ServeurNode requeteNouvelArrivant(int keyNode) throws
            RemoteException {
        return stub.localiser(keyNode);
    }


    public ArrayList<Integer> redistributionDesResponsabilités(Nœud nœud)
            throws RemoteException {
        return stub.notifyInsertionSucesseur(nœud);
    }


    /**
     * Permet de recuperer le dernier identifiant du serveur nœud traité.
     *
     * @return identifiant d'un nœud serveur, renvoi 0 si pas de serveur actif.
     */
    public int getIdNœud() {
        return this.idNœud;
    }

    /**
     * Initialise la connexion client - RMI registrer.
     *
     * @param adresseIP adresseIp du serveur RMI.
     */
    public void initClient(String adresseIP) {
        this.adresseIP = adresseIP;
        try {
            this.registry = LocateRegistry.getRegistry(adresseIP);

        } catch (Exception e) {
            System.out.println("Intialisation RMI Registery");
        }
    }

    /**
     * Permet de recuperer les informations d'un serveur nœud grâce au
     * serveur RMI.
     *
     * @param idNœud clée identifiant le nœud que l'on souhaite intéroger.
     */
    public void getInfoServeurNœud(int idNœud) {
        try {
            this.stub = (iNœud) registry.lookup(String.valueOf(idNœud));
        } catch (Exception e) {
            System.out.println("Recupération des informations serveur : " +
                    "echec");
        }
    }


}
