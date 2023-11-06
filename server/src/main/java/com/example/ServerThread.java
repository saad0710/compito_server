package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerThread extends Thread{
    private Socket s;
    private List<ServerThread> threads;
    public ServerThread(Socket s, List<ServerThread> threads){
        this.s = s;
        this.threads = threads;
    }

    @Override
    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            DataOutputStream out = new DataOutputStream(s.getOutputStream());

            String stringaRicevuta = new String();
            List<String> note = new ArrayList<String>();

            do {
                stringaRicevuta = in.readLine();
                System.out.println("["+currentThread().getName() + "] ha inviato: " + stringaRicevuta);
                if (stringaRicevuta.equals("LISTA")) {
                    String lista = "";
                    lista=note.toString();
                    out.writeBytes(stringaRicevuta + '\n');
                    out.writeBytes(lista + '\n');
                    
                } else {
                    note.add(stringaRicevuta);
                }

            } while (!stringaRicevuta.equals("ESCI"));
            out.writeBytes("ESCI" + '\n');
            System.out.println("["+currentThread().getName() + "] Client chiude la connessione");
            s.close();
            threads.remove(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            threads.remove(this);
        }
    }
}
