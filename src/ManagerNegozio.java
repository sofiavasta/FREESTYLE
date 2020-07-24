import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ManagerNegozio implements Runnable{
    private Socket client_socket;
    private VestitoList list;

    public ManagerNegozio(Socket myclient, VestitoList list) {
        client_socket = myclient;
        this.list = list;
    }

    @Override
    public void run() {
        String tid = Thread.currentThread().getName();
        System.out.println(tid+"-> Connessione accettata da " + client_socket.getRemoteSocketAddress());

        Scanner client_scanner = null;
        PrintWriter pw = null;
        try {
            client_scanner = new Scanner(client_socket.getInputStream());
            pw = new PrintWriter(client_socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean go = true;
        while (go) {
            String message = client_scanner.nextLine();
            System.out.println("Il Server ha ricevuto: "+message);
            Scanner msg_scanner = new Scanner(message);

            String cmd = msg_scanner.next();
            System.out.println("Comando ricevuto:"+cmd);

            if (cmd.equals("AGGIUNGI")) {
                String categoria = msg_scanner.next();
                String tipo_abbigliamento = msg_scanner.next();
                String modello = msg_scanner.next();
                String stagione = msg_scanner.next();
                int id_capo = msg_scanner.nextInt();
                int id_quantita = msg_scanner.nextInt();
                String colore = msg_scanner.next();
                String taglia = msg_scanner.next();
                String prezzo = msg_scanner.next();
                try {

                    Vestito v = new Vestito(categoria, tipo_abbigliamento, modello, stagione, id_capo, id_quantita, colore, taglia, Float.parseFloat(prezzo));
                    //DA_VEDERE!
                    list.add(v);
                    System.out.println("Aggiunto " + v);
                    pw.println("AGGIUNTO_ACK");
                    pw.flush();
                } catch (NumberFormatException e) {
                    pw.println("AGGIUNTO_ERRORE");
                    pw.flush();
                    e.printStackTrace();
                }

            }
            else if (cmd.equals("RIMUOVI")){
                System.out.println("Eseguo il comando Rimuovi e calcolo la nuova quantita': ");
                int id_capo_rimozione = msg_scanner.nextInt();
                int id_quantita_rimozione = msg_scanner.nextInt();
                list.remove(id_capo_rimozione,id_quantita_rimozione);
                pw.println("RIMOZIONE_ACK");
                pw.flush();
            }
            else if (cmd.equals("VISUALIZZA_LISTA")) {
                pw.println("INIZIO");
                pw.flush();

                ArrayList<Vestito> tmp;

                tmp = list.getListCopy();
                for (Vestito v: tmp) {
                    pw.println(v);
                    pw.flush();
                }

                pw.println("FINE");
                pw.flush();
            }

            else if (cmd.equals("RIFORNIRE")){
                System.out.println("Eseguo la rifornitura: ");
                int id_capo_rifornire = msg_scanner.nextInt();
                int numero_capi = msg_scanner.nextInt();
                list.restock(id_capo_rifornire,numero_capi);
                pw.println("RIFORNIRE_ACK");
                pw.flush();

            }

            else if (cmd.equals("SALVA")) {

                File f =new File("archivio_negozio.txt");
                if (f.exists()) {
                    f.delete();
                }
                try {
                    f.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                FileWriter fw= null;
                try {
                    fw = new FileWriter(f, true);
                    ArrayList<Vestito> tmp;

                    tmp = list.getListCopy();
                    for (Vestito v: tmp) {
                        fw.append(v.toString());
                        fw.flush();
                    }
                    fw.close();
                    pw.println("SALVATAGGIO_ACK");
                    pw.flush();
                    System.out.println("Lista salvata correttamente");

                } catch (IOException e) {
                   pw.println("SALVATAGGIO_ERRORE");
                   pw.flush();
                   e.printStackTrace();
                }

            }
            else if (cmd.equals("ESCI")) {
                System.out.println("Chiusura di connessione con "+client_socket.getRemoteSocketAddress());
                try {
                    client_socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                go = false;
            }
            else {
                System.out.println("Comando sconosciuto "+ message);
                pw.println("ERRORE_COMANDO");
                pw.flush();
            }

        }
    }

}
