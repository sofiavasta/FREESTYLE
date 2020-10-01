import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Negozio {

    Socket socket;
    private String indirizzo;
    private int porta;
    private String nome;

    public Negozio(String indirizzo, int porta, String nome) {
        this.indirizzo = indirizzo;
        this.porta = porta;
        this.nome=nome;
    }

    public static void main(String args[]) {

        if (args.length!=3)  {
            System.out.println("Usa: java Negozio <indirizzo> <porta> <nome_negozio>");
            return;
        }

        Negozio client = new Negozio(args[0], Integer.parseInt(args[1]),args[2]);
        client.start();
    }

    public void start(){
        System.out.println("Connessione del Client: "+indirizzo+":"+porta);

        try {
            socket = new Socket(indirizzo,porta);
            System.out.println("Inizio connessione del Client "+indirizzo+":"+porta);

            PrintWriter pw = new PrintWriter(socket.getOutputStream());
            Scanner server_scanner = new Scanner(socket.getInputStream());
            Scanner user_scanner = new Scanner(System.in);

            String messaggio_da_inviare;
            String messaggio_ricevuto;

            boolean go = true;
            int choice;

            while (go) {

                System.out.println("----------------FREESTYLE---------------");
                System.out.println("0 - RICHIESTA CAPO IN MAGAZZINO");
                System.out.println("1 - ELENCO ARTICOLI IN NEGOZIO");
                System.out.println("2 - ESCI");
                System.out.println("----------------FREESTYLE---------------");
                System.out.print("Inserisci scelta->");
                choice = user_scanner.nextInt();

                switch (choice) {

                    case 0: // Richiesta capo in magazzino
                        System.out.print("Inserisci Id_Capo:");
                        int id_capo_da_inserire = user_scanner.nextInt();
                        messaggio_da_inviare="ADD_CAPO "+id_capo_da_inserire+" "+this.nome;
                        System.out.println("DEBUG: Invio "+ messaggio_da_inviare);
                        pw.println(messaggio_da_inviare);
                        pw.flush();
                        messaggio_ricevuto = server_scanner.nextLine();
                        if (messaggio_ricevuto.equals("ADD_CAPO_RICEZIONE_ACK")) {
                            System.out.println("CAPO AGGIUNTO CORRETTAMENTE ALLA GIACENZA!");

                        }
                        else if (messaggio_ricevuto.equals("ADD_CAPO_ERRORE")) {
                            System.out.println("ERRORE: Non è stato possibile ricevere il capo dal magazzino");
                        }
                        else {
                            System.out.println("ERRORE: valore sconosciuto->"+messaggio_ricevuto);
                        }
                        break;
                    case 1: // Elenco Articoli Magazzino
                        messaggio_da_inviare = "VISUALIZZA_LISTA "+this.nome;
                        pw.println(messaggio_da_inviare);
                        pw.flush();

                        messaggio_ricevuto = server_scanner.nextLine();
                        boolean listing = true;
                        if (messaggio_ricevuto.equals("INIZIO")) {
                            System.out.println("Sto ricevendo gli articoli presenti nel magazzino...");
                            while (listing) {
                                messaggio_ricevuto = server_scanner.nextLine();
                                if (messaggio_ricevuto.equals("FINE")) {
                                    listing = false;
                                    System.out.println("Fine lista");
                                } else {
                                    // stampo il vestito
                                    System.out.println(messaggio_ricevuto);
                                }
                            }
                        }
                        else if(messaggio_ricevuto.equals("VISUALIZZA_LISTA_ERRORE")) {
                            System.out.println("NON HAI NESSUN ARTICOLO IN GIACENZA");
                        }
                        else{
                            System.out.println("Risposta inaspettata"+messaggio_ricevuto);
                        }
                        break;


                    case 2: // Uscita
                        go = false;
                        System.out.println("Chiusura del Client...");
                        messaggio_da_inviare = "ESCI";
                        pw.println(messaggio_da_inviare);
                        pw.flush();
                        break;

                }


            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

