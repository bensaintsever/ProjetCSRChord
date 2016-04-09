import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;

/**
 * Classe client permettant d'intéragir sur le réseau de chord, en ayant la
 * possibilité de modifier les nœuds et d'en rajouter.
 *
 * @author Benjamin Saint-Sever.
 */
public class ClientAdmin {

    private static final double MAXKEY = 65535;
    private static final double MINKEY = 0;
    private HashMap liste_Nœud;
    private double idNœud;

    private String adresseIP;
    private Registry registry;
    private iNœud stub;

    /**
     * Nœud référent pour initier la premiere communication inter nœuds.
     */
    private double keyNœudReferant;

    /**
     * Constructeur du client administrateur.
     */
    public ClientAdmin() {
        this.liste_Nœud = new HashMap();
        this.idNœud = MINKEY;
        this.adresseIP = "";
        this.registry = null;
        this.stub = null;
        this.keyNœudReferant = 0;
    }

    public static void main(String args[]) {

        ClientAdmin client = new ClientAdmin();
        try {
            //On recupere l'adresse IP de la machine.
            String adresseIPServeur = InetAddress.getLocalHost()
                    .getHostAddress();

            //On creer un ou plusieurs serveur sur une même machine.
            client.ajouterServeurNode(adresseIPServeur, client.getIdNœud());

        } catch (UnknownHostException e) {
            System.out.println("Creation serveur : adresse ip non trouvé");
        }





        /**
         * Lors d'une connexion avec nœud distant
         */


        /**
         * Initialisation client
         */
         /*   try {
                client.initClient(InetAddress.getLocalHost()
                        .getHostAddress());
            } catch (UnknownHostException e) {
                System.out.println("Creation client : adresse ip non trouvé");
            }
         */


        /**
         * Le client peut maintenant intéragir avec le nœud.
         */
        //client.getInfoServeurNœud(0);


        /**
         * Exemples:
         * client.stub.insertValue();
         * client.stub...
         */


        /**
         * Lorsque l'on rajoute un nœud dans un réseau de chord existant :
         *
         *
         */


    }

    /**
     * Ajout d'un serveur de nœu, attention ne marche qu'en local !!!
     *
     * @param adresseIP adresse ip du serveur.
     * @param id        clée permettant d'identifier le serveur de nœud.
     */
    public void ajouterServeurNode(String adresseIP, double id) {


        if (this.idNœud < MAXKEY)
            this.idNœud++;

        if (this.idNœud == 1)
            this.keyNœudReferant = idNœud;

        liste_Nœud.put(this.idNœud, new ServeurNode(adresseIP, id,
                this.keyNœudReferant));
    }

    public void supprimerServeurNode(int idNœud) {
        if (this.idNœud >= 0) {
            liste_Nœud.remove(idNœud);
            this.idNœud--;
        }
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

    /**
     * On désigne un nœud référant qui initie le premier dialogue entre nœud.
     *
     * @return la clée identifiant le nœud référant à contacter.
     */
    public double getKeyNœudReferant() {
        return this.keyNœudReferant;
    }


}
