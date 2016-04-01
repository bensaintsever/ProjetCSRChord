import java.util.ArrayList;

/**
 * @author Benjamin Saint-Sever
 */
public class GestionNœuds {
    private static final int MAXKEY = 65535;
    private static final int MINKEY = 0;
    private ArrayList<Nœud> liste_Nœud;
    private int key;
    private int nombreMaxNœud;


    public GestionNœuds() {
        liste_Nœud = new ArrayList<Nœud>();
        key = MINKEY;
    }

    public int getNewKey() {
        if (this.key < MAXKEY)
            return this.key++;
        return 65536;
    }

    public void ajoutNœud(String adresse) {
        Nœud n = new Nœud(this.key, adresse);
    }
}
