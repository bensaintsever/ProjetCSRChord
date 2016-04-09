import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Classe permettant à un nœud d'interragir avec ses nœuds voisins.
 *
 * @author Benjamin Saint-Sever
 */
public class ClientNode {
    private static final double MAXKEY = 65535;
    private static final double MINKEY = 0;
    private double idNœud;
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
     * Méthode à utilisé lors de l'insertion d'un nouveau nœud, on cherche à
     * communiquer le nœud response de la clée, on commence par interroger un
     * nœud qui transmettra à ses voisins jusqu'a trouver le nœud responsable.
     * Ce dernier contactera notre nœud pour lui affecter les responsabilités
     * des données lié à la clée et devient le successeur de notre nœud.
     * Le predecesseur du nœud interrogé devient notre predecesseur, et on
     * devient predecesseur du nœud contacté.
     * @param keyNode
     */
    public void requeteNouvelArrivant(double keyNode){

    }


    /**
     * Permet de recuperer le dernier identifiant du serveur nœud traité.
     *
     * @return identifiant d'un nœud serveur, renvoi 0 si pas de serveur actif.
     */
    public double getIdNœud() {
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
    public void getInfoServeurNœud(double idNœud) {
        try {
            this.stub = (iNœud) registry.lookup(String.valueOf(this.getIdNœud
                    ()));
        } catch (Exception e) {
            System.out.println("Recupération des informations serveur : " +
                    "echec");
        }
    }


}
