package com.example;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class App 
{
    public static void main( String[] args )
    {
        try {
            System.out.println("Il server è stato avviato");
            ServerSocket server = new ServerSocket(3000);
            List<ServerThread> threads = new ArrayList<ServerThread>();

            for (;;) {
                Socket s = server.accept();
                ServerThread thread = new ServerThread(s, threads);
                threads.add(thread);
                thread.start();
                System.out.println(""+s.getPort()+" si è connesso");
                System.out.println("Il numero totale dei client è: " + threads.size());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Errore durante la creazione del server!");
        }
    }
}
