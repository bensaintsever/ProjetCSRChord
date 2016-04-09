import java.rmi.Remote;

/**
 * @author Benjamin Saint-Sever.
 */
public interface iNœud extends Remote {
    int get(double key);

    void deleteValue(double key, int value);

    void insertValue(double key, int value);

    ServeurNode localiser(double key);
}


