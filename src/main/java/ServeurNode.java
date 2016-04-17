import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Classe serveur unique à chaque nœud, qui permet de communiquer avec les
 * clients et les autres nœuds.
 *
 * @author Benjamin Saint-Sever.
 */
public class ServeurNode {

    private final static Logger log = Logger.getLogger(ServeurNode.class.getName
            ());
    private static final int MAXKEY = 65535;
    private static final int MINKEY = 0;
    /**
     * Adresse Ip de la machine serveur.
     */
    private String adressIPServeur;

    /**
     * clée du nœud referent dans le réseau de chord.
     */
    private int keyNœudReferant;

    /**
     * Adresse Ip du serveur référent.
     */
    private String adressIPServeurReferant;

    /**
     * clée identifiant du nœud courant.
     */
    private int idNœud;

    /**
     * Reference du nœud courant.
     */
    private Nœud nœudCourant;

    /**
     * Reference vers un objet ClientNode, permettant d'interroger d'autres
     * nœuds du réseau.
     */
    private ClientNode c;

    /**
     * Constructeur du serveur de nœud.
     *
     * @param ip              désignation de l'adresse ip du serveur de nœud.
     * @param keyNœud         clée identifiant du nœud.
     * @param keyNœudReferant clée identifiant du nœud référent du réseau chord.
     */
    public ServeurNode(String ip, int keyNœud, int keyNœudReferant, String
            adresseReferant) {
        this.adressIPServeur = ip;
        this.idNœud = keyNœud;
        this.keyNœudReferant = keyNœudReferant;
        this.adressIPServeurReferant = adresseReferant;
        this.c = null;
        boolean _premier = false;
        if (this.keyNœudReferant == this.idNœud)
            _premier = true;


        this.nœudCourant = new Nœud(this, ip, keyNœud, _premier);

        /** INSERTION NOUVEAU NOEUDS **/
        //Si il y a deja d'autres nœuds présents dans le réseau.
        if (keyNœudReferant != this.idNœud) {

            insertionNœudReseauDistant();


            //Le nœud va chercher sa place dans le réseau.
            ServeurNode nœud_sucesseur = null;
            try {
                System.out.println("ServeurNode " + this.idNœud + " : " +
                        "RequeteNouvel"
                        + "Arrivant avant");

                /************* PROBLEME ICI **************/
                nœud_sucesseur = c.requeteNouvelArrivant(this.idNœud);
                /*****************************************/
                System.out.println("ServeurNode : RequeteNouvel Arrivant " +
                        "apres");
            } catch (RemoteException e) {
                System.out.println("Problème requete Nouvel arrivant");
                e.printStackTrace();
            }


            this.nœudCourant.setNœudSuivant(nœud_sucesseur.getNœudCourant());
            this.nœudCourant.setNœudPrecedent(nœud_sucesseur.nœudCourant
                    .getNœudPrecedent());


            //On notifie le successeur pour recuperer ses responsabilités
            ArrayList<Integer> listeCléeRecupere = null;
            try {
                listeCléeRecupere = c
                        .redistributionDesResponsabilités(this.nœudCourant);
            } catch (RemoteException e) {
                log.info("Problème lors de la redistribution des " +
                        "responsabilités (nouvel arrivant)");
                e.printStackTrace();
            }

            for (int clee : listeCléeRecupere) {
                this.nœudCourant.insertValue(clee, clee);
                log.info("Ajout des clées recuperer : " + clee);
            }


        }


        ModeServeur();

    }


    /**
     * Première communication du nœud avec un autre nœud.
     */
    public void insertionNœudReseauDistant() {
        this.c = new ClientNode();
        c.initClient(this.adressIPServeurReferant);
        //Connexion avec le Nœud referant
        c.getInfoServeurNœud(keyNœudReferant);
    }


    /**
     * Permet de gerer l'initialisation du mode serveur d'un nœud.
     */
    public void ModeServeur() {
        try {

            String sIdNœud = String.valueOf(this.idNœud);

            iNœud stub = (iNœud) UnicastRemoteObject.exportObject(this
                    .nœudCourant, 0);


            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();


            registry.bind(sIdNœud, stub);

            System.err.println("Serveur du nœud " + sIdNœud + " ready");
        } catch (Exception e) {
            System.err.println("Serveur du nœud " + String.valueOf(this
                    .idNœud) + " exception: " + e.toString());
            e.printStackTrace();
        }
    }

    /**
     * Permet de récupérer la clée maximale possible.
     *
     * @return la clée maximale possible.
     */
    public int getMaxkey() {
        return MAXKEY;
    }

    /**
     * Permet de récupérer la clée minimale possible.
     *
     * @return la clée minimale possible.
     */
    public int getMinkey() {
        return MINKEY;
    }

    /**
     * Permet de récupérer le nœud utilisé par le serveur dédié.
     *
     * @return le nœud utilisé par le serveur dédié.
     */
    public Nœud getNœudCourant() {
        return this.nœudCourant;
    }

}
