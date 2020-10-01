import java.io.IOException;

public class ThreadAggiornamento implements Runnable {

        VestitoList vl;

    public ThreadAggiornamento(VestitoList vl) {
        this.vl = vl;
    }

    public void run() {

            try {
                Thread.sleep(1000);
                vl.salvaSuFile();

            }  catch (InterruptedException | IOException e) {
                System.out.println("STOP Thread!");
                e.printStackTrace();
            }

    }
}
