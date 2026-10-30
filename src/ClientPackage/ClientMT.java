package ClientPackage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientMT {

    public static void main(String[] args) {

        try {
            System.out.println("Je suis un client pas encore connecté...");

            Socket socket = new Socket("localhost", 1234);

            System.out.println("Je suis un client connecté.");

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            PrintWriter pw = new PrintWriter(
                    socket.getOutputStream(), true);

            Scanner sc = new Scanner(System.in);

            // Recevoir le numéro d'ordre attribué par le serveur
            String identification = br.readLine();

            System.out.println(identification);

            String message;

            while (true) {

                System.out.print("Donnez un message (ou quit) : ");

                message = sc.nextLine();

                // Envoyer le message au serveur
                pw.println(message);

                if (message.equalsIgnoreCase("quit")) {
                    break;
                }

                // Recevoir la réponse du serveur
                String reponse = br.readLine();

                System.out.println("Serveur : " + reponse);
            }

            System.out.println("Fin de la communication.");

            sc.close();
            socket.close();

        } catch (IOException e) {

            System.out.println("Erreur côté client : " + e.getMessage());
        }
    }
}
