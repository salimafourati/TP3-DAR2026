package ServerPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientProcess extends Thread {

    private final Socket socket;
    private final int numero;

    public ClientProcess(Socket socket, int numero) {
        this.socket = socket;
        this.numero = numero;
    }

    @Override
    public void run() {

        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            PrintWriter pw = new PrintWriter(
                    socket.getOutputStream(), true);

            // Envoyer au client son numéro d'ordre
            pw.println("Bienvenue, vous etes le client numero" + numero);

            String message;

            // Recevoir les messages du client
            while ((message = br.readLine()) != null) {

                System.out.println("[Client n°" + numero
                        + "] dit : " + message);

                if (message.equalsIgnoreCase("quit")) {
                    break;
                }

                // Envoyer une confirmation au client
                pw.println("Message bien recu par le serveur "
                        + "(Client numero" + numero + ")");
            }

            System.out.println("Client n°" + numero
                    + " s'est déconnecté.");

        } catch (IOException e) {

            System.out.println("Déconnexion du client n°" + numero);

        } finally {

            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Erreur lors de la fermeture du socket.");
            }
        }
    }
}
