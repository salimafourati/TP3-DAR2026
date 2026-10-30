package ClientPackage;

import SharedPackage.Operation;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientMT2 {

    public static void main(String[] args) {

        try (
                Socket socket = new Socket("localhost", 1234);
                Scanner clavier = new Scanner(System.in)
        ) {

            // Création des flux objets
            ObjectOutputStream sortie =
                    new ObjectOutputStream(socket.getOutputStream());
            sortie.flush();

            ObjectInputStream entree =
                    new ObjectInputStream(socket.getInputStream());

            // Réception du message de bienvenue
            String bienvenue = (String) entree.readObject();
            System.out.println(bienvenue);

            while (true) {

                System.out.print("Premier opérande : ");
                double a = Double.parseDouble(clavier.nextLine());

                System.out.print("Opérateur (+, -, *, /) : ");
                String operateur = clavier.nextLine();

                System.out.print("Deuxième opérande : ");
                double b = Double.parseDouble(clavier.nextLine());

                // Création et envoi de l'objet Operation
                Operation op = new Operation(a, operateur, b);

                sortie.writeObject(op);
                sortie.flush();

                // Réception du résultat
                String resultat = (String) entree.readObject();

                System.out.println(resultat);

                System.out.print("Continuer ? (oui/non) : ");

                String choix = clavier.nextLine();

                if (choix.equalsIgnoreCase("non")) {

                    sortie.writeObject(new Operation(true));
                    sortie.flush();

                    System.out.println("Déconnexion du serveur...");
                    break;
                }
            }

        } catch (IOException | ClassNotFoundException e) {

            System.out.println("Erreur de communication : " + e.getMessage());

        } catch (NumberFormatException e) {

            System.out.println("Veuillez saisir des nombres valides.");
        }
    }
}