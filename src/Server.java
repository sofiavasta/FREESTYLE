import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    ServerSocket socket;
    Socket client_socket;
    private int porta;

    int client_id = 0;

    VestitoList list = new VestitoList();

    public static void main(String args[]) {

        if (args.length!=1) {
            System.out.println("Usa java Server <porta>");
            return;
        }

        Server server = new Server(Integer.parseInt(args[0]));
        server.start();
    }

    public Server(int port) {
        System.out.println("Inizializzo il Server con porta "+port);
        this.porta = port;
    }

    public void start() {
        try {
            System.out.println("Inizializzazione del Server sulla porta: "+porta);
            socket = new ServerSocket(porta);
            System.out.println("Start del Server sulla porta "+porta);
            while (true) {
                System.out.println("In ascolto sulla porta " + porta);
                client_socket = socket.accept();
                System.out.println("Connessione accettata da " + client_socket.getRemoteSocketAddress());

                ManagerNegozio cm = new ManagerNegozio(client_socket,list);
                Thread t = new Thread(cm,"client_"+client_id);
                client_id++;
                t.start();
            }

        } catch (IOException e) {
            System.out.println("Non e' stato possibile avviare il server sulla porta "+porta);
            e.printStackTrace();
        }

    }
}
