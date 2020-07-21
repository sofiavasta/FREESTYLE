import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Negozio {

    Socket socket;
    private String indirizzo;
    private int porta;

    public Negozio(String indirizzo, int porta) {
        this.indirizzo = indirizzo;
        this.porta = porta;
    }

    public static void main(String args[]) {

        if (args.length!=2)  {
            System.out.println("Usa: java Negozio <indirizzo> <porta>");
            return;
        }

        Negozio client = new Negozio(args[0], Integer.parseInt(args[1]));
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
                System.out.println("0 - Aggiungi Vestito");
                System.out.println("1 - Rimuovi Vestito");
                System.out.println("2 - Elenco Articoli Magazzino");
                System.out.println("3 - Richiesta Fornitura");
                System.out.println("4 - Salva");
                System.out.println("5 - Esci");
                System.out.println("----------------FREESTYLE---------------");
                System.out.print("Inserisci scelta->");
                choice = user_scanner.nextInt();

                switch (choice) {
                    case 0: //Aggiungi Vestito
                        System.out.print("Inserisci Categoria:");
                        String categoria = user_scanner.next();
                        System.out.print("Inserisci Tipo_Abbigliamento:");
                        String tipo_abbigliamento = user_scanner.next();
                        System.out.print("Inserisci Modello:");
                        String modello = user_scanner.next();
                        System.out.print("Inserisci Stagione:");
                        String stagione = user_scanner.next();
                        System.out.print("Inserisci Id_Capo:");
                        String id_capo = user_scanner.next();
                        System.out.print("Inserisci Colore:");
                        String colore = user_scanner.next();
                        System.out.print("Inserisci Taglia:");
                        String taglia = user_scanner.next();
                        System.out.print("Inserisci Prezzo:");
                        int prezzo = user_scanner.nextInt();

                        messaggio_da_inviare = "AGGIUNGI "+categoria+" "+tipo_abbigliamento+" "+modello+" "+stagione+" "+id_capo+" "+colore+" "+taglia+" "+prezzo;
                        System.out.println("DEBUG: Invio "+messaggio_da_inviare);
                        pw.println(messaggio_da_inviare);
                        pw.flush();

                        messaggio_ricevuto = server_scanner.nextLine();
                        if (messaggio_ricevuto.equals("AGGIUNTO_ACK")) {
                            System.out.println("Vestito aggiunto correttamente!");
                        }
                        else if (messaggio_ricevuto.equals("AGGIUNTO_ERRORE")) {
                            System.out.println("ERRORE il Vestito non e' stato aggiunto correttamente!!!");
                        }
                        else {
                            System.out.println("ERRORE: valore sconosciuto->"+messaggio_ricevuto);
                        }
                        break;
                    case 1: // remove
                        break;
                    case 2: // Elenco Articoli Magazzino
                        messaggio_da_inviare = "VISUALIZZA_LISTA";
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
                        else {
                            System.out.println("Risposta sconosciuta:"+messaggio_ricevuto);
                        }
                        break;
                    case 3: //Richiesta Fornitura
                        break;
                    case 4: // Salvataggio
                        pw.println("SALVA");
                        pw.flush();

                        messaggio_ricevuto = server_scanner.nextLine();
                        if (messaggio_ricevuto.equals("SALVATAGGIO_ACK")) {
                            System.out.println("File salvato correttamente!");
                        }
                        else if (messaggio_ricevuto.equals("SALVATAGGIO_ERRORE")) {
                            System.out.println("ERRORE durante il salvataggio del File");
                        }
                        else {
                            System.out.println("Messaggio sconosciuto: "+messaggio_ricevuto);
                        }
                        break;
                    case 5: // Uscita
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

