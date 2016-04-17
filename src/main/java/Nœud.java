import java.util.ArrayList;
import java.util.Hashtable;
import java.util.logging.Logger;


/**
 * Classe correspondant à un nœud que l'on ajoute dans un réseau de chord.
 * Ce nœud contient une table de hashage permettant de stocker des valeurs.
 * Chaque nœud à pour responsabilité un nombre de valeur.
 *
 * @author Benjamin Saint-Sever
 */
public class Nœud implements iNœud {
    private final static Logger log = Logger.getLogger(Nœud.class.getName
            ());

    /**
     * Table de hashage permet à un nœud de connaître ses responsabilités sur
     * les valeurs.
     */
    private Hashtable hebergement;

    /**
     * Contient l'id du nœud.
     */
    private int idNœud;

    /**
     * Contient l'adresse du nœud.
     */
    private String adresseNœud;

    private Nœud nœudSuivant;
    private Nœud nœudPrecedent;


    /**
     * Le nœud conserve la reference de son Serveur Nœud.
     */
    private ServeurNode refServeur;


    public Nœud() {
        this.refServeur = null;
        this.adresseNœud = null;
        this.idNœud = 0;
        nœudPrecedent = null;
        nœudSuivant = null;
        hebergement = null;
    }

    /**
     * Initialisation d'un nœud.
     *
     * @param ref         reférence avec son serveur de nœud.
     * @param adresseNœud adresse du nœud courant.
     * @param idNœud      clée identifiant du nœud.
     */
    public Nœud(ServeurNode ref, String adresseNœud, int idNœud) {
        this.refServeur = ref;
        this.adresseNœud = adresseNœud;
        this.idNœud = idNœud;
        nœudPrecedent = null;
        nœudSuivant = null;
        hebergement = new Hashtable();
    }

    /**
     * Initialisation d'un nœud avec ses voisins predecesseur et suivant.
     *
     * @param ref         reférence avec son serveur de nœud.
     * @param adresseNœud adresse du nœud courant.
     * @param idNœud      clée identifiant du nœud.
     * @param nsuivant    référence vers le nœud suivant.
     * @param nprecedent  référence du nœud précedent.
     */
    public Nœud(ServeurNode ref, String adresseNœud, int idNœud, Nœud
            nsuivant, Nœud
                        nprecedent) {
        this.refServeur = ref;
        //Nœud courant
        this.adresseNœud = adresseNœud;
        //Nœud precedent
        this.nœudPrecedent = nprecedent;
        //Nœud suivant
        this.nœudSuivant = nsuivant;

        this.idNœud = idNœud;

        hebergement = new Hashtable();
    }

    /**
     * Permet de recuperer la clée identifiant le nœud.
     *
     * @return la clée d'identification du nœud.
     */
    public int getIdNœud() {
        return this.idNœud;
    }

    /**
     * Récupération de la valeur hébergé par le nœud, correspondant à la clée.
     *
     * @param key clée identifiant une valeur hébergé par le nœud.
     * @return la valeur identifié par la clée.
     */
    public int get(int key) {
        if (this.hebergement.containsKey(key))
            return ((Integer) this.hebergement.get(key));
        else
            return -1;
        //else
        //  return this.nœudSuivant.get(key); //A optimiser surement
    }

    /**
     * Mise à jour de la table de donnée avec l'ajout d'une valeur associé à
     * sa clée.
     *
     * @param key   clée d'une valeur.
     * @param value valeur.
     */
    public void insertValue(int key, int value) {
        this.hebergement.put(key, value);
        log.info("Insertion de la valeur : " + value + ", voici mon id :" +
                this.getIdNœud());
    }

    /**
     * Mise à jour de la table de donnée avec la suppression d'une valeur
     * associé à sa clée.
     *
     * @param key clée d'une valeur.
     */
    public void deleteValue(int key) {
        this.hebergement.remove(key);
    }

    public Nœud getNœudSuivant() {
        return this.nœudSuivant;
    }

    public void setNœudSuivant(Nœud n) {
        this.nœudSuivant = n;
    }

    public Nœud getNœudPrecedent() {
        return this.nœudPrecedent;
    }

    public void setNœudPrecedent(Nœud n) {
        this.nœudPrecedent = n;
    }

    /**
     * Permet de localiser un nœud cible.
     *
     * @param idNode clée du nœud recherché.
     * @return le serveur de nœud recherché.
     */
    public ServeurNode localiser(int idNode) {

        //Si je possede la valeur dans ma table alors je renvoi ma signature.
        if (this.get(idNode) == idNode) {
            System.out.println("Nœud : J'ai trouvé le nœud responsable");
            return (ServeurNode)this.refServeur;
        }

        //SI LA CLEE RECHERCHER EST PLUS GRANDE QUE MOI, JE PASSE AU NOEUD
        // SUCESSEUR
        if (this.idNœud < idNode) {
            return this.nœudSuivant.localiser(idNode);
        }





        return null;
    }


    /**
     * Méthode permettant de notifier à un nœud qu'il devient le sucesseur
     * d'un nouveau nœud, il doit donc modifier son nœud predecesseur en
     * conséquence (ajout du nouveau nœud).
     *
     * @param nœudPredecesseur
     */
    public ArrayList<Integer> notifyInsertionSucesseur(Nœud nœudPredecesseur) {
        this.nœudPrecedent = nœudPredecesseur;
        int idNœudPredecesseur = nœudPredecesseur.getIdNœud();
        ArrayList<Integer> listeValeur = null;



        /*
        Si P > C
            K>P ou k<= c
        Sinon
            P < k <= C
        */


        if (idNœudPredecesseur > this.idNœud) {
            //Clée > P

            for (int i = this.getIdNœud(); i < this.refServeur.getMaxkey();
                 ++i) {
                listeValeur.add(this.get(i));
                deleteValue(i);
            }
            //OU
            //Clée <= nœud courant
            for (int i = this.refServeur.getMinkey(); i < this.idNœud;
                 ++i) {
                listeValeur.add(this.get(i));
                deleteValue(i);
            }


        } else {
            //P < Clée <= nœud courant

            for (int i = idNœudPredecesseur; i <= this.idNœud; i++) {
                listeValeur.add(this.get(i));
                deleteValue(i);
            }


        }
        return listeValeur;
    }

    public void methodeBidonTest() {
        System.out.println("Méthode bidon : OK");
    }

}
