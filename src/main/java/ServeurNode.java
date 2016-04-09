import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Classe serveur unique à chaque nœud, qui permet de communiquer avec les
 * clients et les autres nœuds.
 *
 * @author Benjamin Saint-Sever.
 */
public class ServeurNode {

    /**
     * Adresse Ip de la machine serveur.
     */
    private String adressIPServeur;

    /**
     * clée du nœud referent dans le réseau de chord.
     */
    private double keyNœudReferant;

    /**
     * clée identifiant du nœud courant.
     */
    private double idNœud;

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
    public ServeurNode(String ip, double keyNœud, double keyNœudReferant) {
        this.adressIPServeur = ip;
        this.idNœud = keyNœud;
        this.keyNœudReferant = keyNœudReferant;
        this.c = null;


        this.nœudCourant = new Nœud(this, ip, keyNœud);

        //Si il y a deja d'autres nœud présent dans le réseau.
        if (keyNœudReferant != keyNœud) {
            //Le nœud va chercher sa place dans le réseau.
            ServeurNode nœud_sucesseur = this.nœudCourant.localiser
                    (keyNœudReferant);

            this.nœudCourant.setNœudSuivant(nœud_sucesseur.nœudCourant);
        }


    }





    /**
     * Premiere communication du nœud avec un autre nœud.
     */
    public void insertionNœudReseauDistant() {
        this.c = new ClientNode();
        c.initClient(adressIPServeur);
        //Connexion avec le Nœud referant
        c.getInfoServeurNœud(keyNœudReferant);

    }


    /**
     * En fait ya pas vraiment de serveur :(
     */
    public void ModeServeur() {
        try {

            //Creation d'un nœud
            this.nœudCourant = new Nœud(this, this.adressIPServeur, this
                    .idNœud);
            String sIdNœud = String.valueOf(this.idNœud);

            iNœud stub = (iNœud) UnicastRemoteObject.exportObject(this
                    .nœudCourant, 0);


            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind(sIdNœud, stub);

            System.err.println("Serveur du nœud " + sIdNœud + " ready");
        } catch (Exception e) {
            System.err.println("Serveur du nœud " + String.valueOf(this
                    .idNœud) + " exception: " + e
                    .toString
                            ());
            e.printStackTrace();
        }
    }


}
