package ServerPackage;

import SharedPackage.Operation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler extends Thread {

    private Socket socket;
    private int numeroClient;

    public ClientHandler(Socket socket, int numeroClient) {
        this.socket = socket;
        this.numeroClient = numeroClient;
    }

    @Override
    public void run() {

        try {

            // Création des flux objets
            ObjectOutputStream sortie =
                    new ObjectOutputStream(socket.getOutputStream());
            sortie.flush();

            ObjectInputStream entree =
                    new ObjectInputStream(socket.getInputStream());

            sortie.writeObject("Bienvenue, client " + numeroClient);
            sortie.flush();

            while (true) {

                // Réception d'un objet Operation
                Operation op = (Operation) entree.readObject();

                // Vérification de la déconnexion
                if (op.isQuitter()) {
                    System.out.println("Client " + numeroClient
                            + " déconnecté.");
                    break;
                }

                double a = op.getOperande1();
                double b = op.getOperande2();
                String operateur = op.getOperateur();

                String resultat;

                try {

                    double valeur = calculer(a, operateur, b);

                    int compteur = ServeurMT2.incrementerCompteur();

                    resultat = "Résultat : " + a + " " + operateur
                            + " " + b + " = " + valeur;

                    System.out.println(resultat);
                    System.out.println("Nombre total d'opérations : "
                            + compteur);

                } catch (ArithmeticException | IllegalArgumentException e) {

                    resultat = "Erreur : " + e.getMessage();
                }

                // Envoi du résultat sous forme d'objet String
                sortie.writeObject(resultat);
                sortie.flush();
            }

            socket.close();

        } catch (IOException | ClassNotFoundException e) {

            System.out.println("Communication interrompue avec le client "
                    + numeroClient);

        }
    }

    private double calculer(double a, String operateur, double b) {

        switch (operateur) {

            case "+":
                return a + b;

            case "-":
                return a - b;

            case "*":
                return a * b;

            case "/":
                if (b == 0) {
                    throw new ArithmeticException(
                            "Division par zéro impossible.");
                }
                return a / b;

            default:
                throw new IllegalArgumentException(
                        "Opérateur invalide.");
        }
    }
}
