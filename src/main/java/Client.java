/**
 * Created by benjaminsaint-sever on 24/03/2016.
 */
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

    private Client() {
    }

    public static void main(String[] args) {

        String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry(host);

            iBank stub = (iBank) registry.lookup("iBank");

            //TEST de toutes les méthodes présentes dans l'interface iBank
            int numero_compte1 = stub.creerCompte();
            int numero_compte2 = stub.creerCompte();
            stub.ajouterSurCompte(numero_compte1,10);

            stub.getValeurCompte(numero_compte1);

            stub.enleverSurCompte(numero_compte1, 2);
            stub.setValeurCompte(numero_compte2, 4);
            stub.transfertEntreCompte(numero_compte1, numero_compte2, 3);
            
            stub.getValeurCompte(numero_compte2);

        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}