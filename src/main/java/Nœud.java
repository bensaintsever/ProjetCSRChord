import java.util.Hashtable;

/**
 * @author Benjamin Saint-Sever
 */
public class Nœud {

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

    /**
     * Contient l'id du nœud précedent.
     */
    private int nœudPrecedent;

    /**
     * Contient l'adresse du nœud précedent.
     */
    private String adresseNœudPrecedent;
    /**
     * Contient l'id et l'adresse du nœud suivant.
     */
    private int nœudSuivant;

    /**
     * Contient l'adresse du nœud suivant.
     */
    private String adresseNœudSuivant;

    //KEY entre 0 et 65535

    /**
     * Constructeur de nœud.
     *
     * @param id                      identifiant du nœud courant.
     * @param adresseNœud             adresse du nœud courant.
     * @param idNœudSuivant           identifiant du nœud suivant.
     * @param adresseNœudSuivant      adresse du nœud suivant.
     * @param idNœudPredecesseur      identifiant du nœud precedent.
     * @param adresseNœudPredecesseur adresse du nœud precedent.
     */
    public Nœud(int id, String adresseNœud, int idNœudSuivant, String
            adresseNœudSuivant, int
                        idNœudPredecesseur, String
                        adresseNœudPredecesseur) {
        //Nœud courant
        this.idNœud = id;
        this.adresseNœud = adresseNœud;
        //Nœud precedent
        this.nœudPrecedent = idNœudPredecesseur;
        this.adresseNœudPrecedent = adresseNœudPredecesseur;
        //Nœud suivant
        this.nœudSuivant = idNœudSuivant;
        this.adresseNœudSuivant = adresseNœudSuivant;

        hebergement = new Hashtable();

    }

    /**
     * Constructeur de nœud.
     *
     * @param id          identifiant du nœud courant.
     * @param adresseNœud adresse du nœud courant.
     */
    public Nœud(int id, String adresseNœud) {
        //Nœud courant
        this.idNœud = id;
        this.adresseNœud = adresseNœud;
        hebergement = new Hashtable();
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
            return requeteNœudSuivant(key);

    }

    /**
     * Mise à jour de la table de donnée avec l'ajout d'une valeur associé à
     * sa clée.
     *
     * @param key   clée d'une valeur.
     * @param value valeur.
     */
    public void update(int key, int value) {
        this.hebergement.put(key, value);
    }


    public int requeteNœudSuivant(int key){
        return
    }
}
