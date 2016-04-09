import java.util.Hashtable;

/**
 * Classe correspondant à un nœud que l'on ajoute dans un réseau de chord.
 * Ce nœud contient une table de hashage permettant de stocker des valeurs.
 * Chaque nœud à pour responsabilité un nombre de valeur.
 *
 * @author Benjamin Saint-Sever
 */
public class Nœud implements iNœud {

    /**
     * Table de hashage permet à un nœud de connaître ses responsabilités sur
     * les valeurs.
     */
    private Hashtable hebergement;

    /**
     * Contient l'id du nœud.
     */
    private double idNœud;

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


    /**
     * Initialisation d'un nœud.
     *
     * @param ref         reférence avec son serveur de nœud.
     * @param adresseNœud adresse du nœud courant.
     * @param idNœud      clée identifiant du nœud.
     */
    public Nœud(ServeurNode ref, String adresseNœud, double idNœud) {
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
    public Nœud(ServeurNode ref, String adresseNœud, double idNœud, Nœud
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
    public double getIdNœud() {
        return this.idNœud;
    }

    /**
     * Récupération de la valeur hébergé par le nœud, correspondant à la clée.
     *
     * @param key clée identifiant une valeur hébergé par le nœud.
     * @return la valeur identifié par la clée.
     */
    public int get(double key) {
        if (this.hebergement.containsKey(key))
            return ((Integer) this.hebergement.get(key));
        else
            return this.nœudSuivant.get(key); //A optimiser surement
    }

    /**
     * Mise à jour de la table de donnée avec l'ajout d'une valeur associé à
     * sa clée.
     *
     * @param key   clée d'une valeur.
     * @param value valeur.
     */
    public void insertValue(double key, int value) {
        this.hebergement.put(key, value);
    }

    /**
     * Mise à jour de la table de donnée avec la suppression d'une valeur
     * associé à sa clée.
     *
     * @param key   clée d'une valeur.
     * @param value valeur.
     */
    public void deleteValue(double key, int value) {
        this.hebergement.remove(key);
    }

    public Nœud getNœudSuivant() {
        return this.nœudSuivant;
    }

    public Nœud getNœudPrecedent() {
        return this.nœudPrecedent;
    }

    public void setNœudSuivant(Nœud n){
        this.nœudSuivant = n;
    }

    public void setNœudPrecedent(Nœud n){
        this.nœudPrecedent = n;
    }

    /**
     * Permet de localiser un nœud cible.
     * @param idNode clée du nœud recherché.
     * @return le serveur de nœud recherché.
     */
    public ServeurNode localiser(double idNode){
        //SI LA CLEE RECHERCHER EST PLUS GRANDE QUE MOI, JE PASSE AU NOEUD
        // SUCESSEUR
        if (this.idNœud < idNode){
            return this.nœudSuivant.localiser(idNode);
        }
        return this.refServeur;
    }


}
