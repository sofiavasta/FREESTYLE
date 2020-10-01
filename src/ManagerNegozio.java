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


        boolean go = true;
        while (go) {
            try {
                ThreadAggiornamento ts = new ThreadAggiornamento(list);
                Thread t = new Thread(ts);
                t.start();
                client_scanner = new Scanner(client_socket.getInputStream());
                pw = new PrintWriter(client_socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            String message = client_scanner.nextLine();
            System.out.println("Il Server ha ricevuto: "+message);
            Scanner msg_scanner = new Scanner(message);

            String cmd = msg_scanner.next();
            System.out.println("Comando ricevuto:"+cmd);

             if (cmd.equals("ADD_CAPO")){
                System.out.println("AGGIUNGO UN NUOVO CAPO ALLA GIACENZA DEL NEGOZIO");
                int id_capo_rifornire = msg_scanner.nextInt();
                String nome_client_add=msg_scanner.next();
                Vestito v_restock=list.restock(id_capo_rifornire,nome_client_add);

                if(v_restock!=null){
                    System.out.println("HO AGGIUNTO IL SEGUENTE VESTITO-->"+v_restock);
                    pw.println("ADD_CAPO_RICEZIONE_ACK");
                    pw.flush();
                }else{
                    System.out.println("ERRORE! VESTITO NON PRESENTE");
                    pw.println("ADD_CAPO_ERRORE");
                    pw.flush();

                }


            }
            else if (cmd.equals("VISUALIZZA_LISTA")) {
                String nome_client_visualizza=msg_scanner.next();

                ArrayList<Vestito> tmp;
                 try {

                     tmp = list.getListCopy(nome_client_visualizza);

                     if(tmp.size()>0){
                         pw.println("INIZIO");
                         pw.flush();
                         for (Vestito v: tmp) {
                             pw.println(v);
                             pw.flush();
                         }
                         pw.println("FINE");
                         pw.flush();
                     }else{
                         pw.println("VISUALIZZA_LISTA_ERRORE");
                         pw.flush();
                     }

                 } catch (IOException e) {
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