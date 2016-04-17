import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;

import static java.lang.Thread.sleep;

/**
 * Classe client permettant d'intéragir sur le réseau de chord, en ayant la
 * possibilité de modifier les nœuds et d'en rajouter.
 *
 * @author Benjamin Saint-Sever.
 */
public class ClientAdmin {

    /**
     * Clée maximale dans le réseau de chord.
     */
    private static final int MAXKEY = 65535;
    /**
     * Clée minimale dans le réseau de chord.
     */
    private static final int MINKEY = 0;

    /**
     * Contient la liste des nœuds présent dans le réseau de chord.
     */
    private HashMap liste_Nœud;


    private String adresseIP;
    private Registry registry;
    private iNœud stub;

    /**
     * Variable qui recense le nombre de serveur de nœud actif dans le réseau.
     */
    private int nbServeur;

    /**
     * Premier serveur de nœud arrivé dans le réseau, il sert de référant aux
     * nouveaux nœuds entrants.
     */
    private int idPremierServeurNœud;


    private String adresseIpServeurReferant;

    /**
     * Nœud référent pour initier la premiere communication inter nœuds.
     */
    private int keyNœudReferant;

    /**
     * Constructeur du client administrateur.
     */
    public ClientAdmin() {
        this.liste_Nœud = new HashMap();
        this.adresseIP = "";
        this.registry = null;
        this.stub = null;
        this.keyNœudReferant = 0;
        this.nbServeur = 0;
        this.idPremierServeurNœud = 0;
        this.adresseIpServeurReferant = "";
    }

    public static void main(String args[]) {

        ClientAdmin client = new ClientAdmin();
        try {
            //On recupere l'adresse IP de la machine.
            String adresseIPServeur = InetAddress.getLocalHost()
                    .getHostAddress();

            //On creer un ou plusieurs serveur(s) sur une même machine.
            client.ajouterServeurNode(adresseIPServeur, 0);
            //client.ajouterServeurNode(adresseIPServeur, 1);

        } catch (UnknownHostException e) {
            System.out.println("Creation serveur : adresse ip non trouvé");
        }


        /**
         * Lors d'une connexion avec nœud distant.
         */

        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /**
         * Initialisation client
         */
        try {
            System.out.println("Client : Init Client");
            client.initClient(InetAddress.getLocalHost()
                    .getHostAddress());
        } catch (UnknownHostException e) {
            System.out.println("Creation client : adresse ip non trouvé");
        }


        /**
         * Le client peut maintenant intéragir avec le nœud.
         */
        System.out.println("Client : getInfoServeur");
        client.getInfoServeurNœud(0);
        System.out.println("Client : Apres getInfoServeur");


        /**
         * Exemples:
         * client.stub.insertValue();
         * client.stub...
         */
        try {
            client.stub.methodeBidonTest();
            //ServeurNode n = client.stub.localiser(0);
            //System.out.println("Valeur max du serveur node :" + n.getMaxkey
            // ());
        } catch (RemoteException e) {
            System.err.println("ClientAdmin exception: " + e.toString());
            e.printStackTrace();
        }

        /**
         * Lorsque l'on rajoute un nœud dans un réseau de chord existant :
         *
         *
         */

        try {
            client.stub.insertValue(0, 0);
            client.stub.insertValue(1, 1);
            client.stub.insertValue(7, 7);
            client.stub.insertValue(11, 11);
        } catch (RemoteException e) {
            System.out.println("Probleme insertion de valeur !");
            e.printStackTrace();
        }

        client.ajouterServeurNode("127.0.0.2", 1);
        try {
            client.stub.insertValue(1, 1);
        } catch (RemoteException e) {
            System.out.println("Probleme insertion de valeur (second serveur)" +
                    "!");
            e.printStackTrace();
        }

    }

    /**
     * Ajout d'un serveur de nœud.
     *
     * @param adresseIP adresse ip du serveur.
     * @param id        clée permettant d'identifier le serveur de nœud.
     */
    public void ajouterServeurNode(String adresseIP, int id) {
        if (nbServeur < MAXKEY) {
            nbServeur++;
        }

        //DESIGNATION DU NOEUD REFERANT
        if (nbServeur == 1) {
            this.keyNœudReferant = id;
            this.idPremierServeurNœud = id;
            this.adresseIpServeurReferant = adresseIP;
        } else {
            this.keyNœudReferant = this.idPremierServeurNœud;
        }

        System.out.println("ClientAdmin : Ajout d'un nouveau serveur " + id +
                " avec pour referant " + this.keyNœudReferant);
        liste_Nœud.put(id, new ServeurNode(adresseIP, id,
                this.keyNœudReferant, adresseIpServeurReferant));
    }

    public void supprimerServeurNode(int idNœud) {
        liste_Nœud.remove(idNœud);
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

    /**
     * On désigne un nœud référant qui initie le premier dialogue entre nœud.
     *
     * @return la clée identifiant le nœud référant à contacter.
     */
    public int getKeyNœudReferant() {
        return this.keyNœudReferant;
    }


    /**
     * A definir pour les tests.
     */
    public void generateKey() {

    }

}
