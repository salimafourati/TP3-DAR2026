package ServerPackage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServeurMT2 {

    private static int compteurOperations = 0;
    private static int compteurClients = 0;

    public static synchronized int incrementerCompteur() {
        compteurOperations++;
        return compteurOperations;
    }

    public static void main(String[] args) {

        try (ServerSocket serveur = new ServerSocket(1234)) {

            System.out.println("Serveur en attente de connexions...");

            while (true) {

                Socket socket = serveur.accept();

                compteurClients++;

                System.out.println("Client " + compteurClients
                        + " connecté : " + socket.getRemoteSocketAddress());

                ClientHandler handler =
                        new ClientHandler(socket, compteurClients);

                handler.start();
            }

        } catch (IOException e) {
            System.out.println("Erreur serveur : " + e.getMessage());
        }
    }
}
