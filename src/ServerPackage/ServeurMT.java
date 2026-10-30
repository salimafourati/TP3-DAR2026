package ServerPackage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurMT {

    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = new ServerSocket(1234);

            System.out.println("Serveur en attente de connexions...");

            int compteur = 0;

            while (true) {

                // Accepter un nouveau client
                Socket socket = serverSocket.accept();

                // Attribuer un numéro d'ordre
                compteur++;

                // Afficher les informations du client
                System.out.println("Client n°" + compteur
                        + " connecté depuis : "
                        + socket.getRemoteSocketAddress());

                // Créer un thread pour gérer ce client
                ClientProcess client =
                        new ClientProcess(socket, compteur);

                // Démarrer le thread
                client.start();
            }

        } catch (IOException e) {
            System.out.println("Erreur côté serveur : " + e.getMessage());
        }
    }
}
