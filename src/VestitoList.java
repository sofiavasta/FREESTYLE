import java.io.*;
import java.util.ArrayList;


public class VestitoList {

    String ultima_modifica;
    private ArrayList<Vestito> list;

    public VestitoList() {
        this.list = new ArrayList<Vestito>();
    }


    public synchronized Vestito restock(int id_capo_rifornire,String nome_client){
        ArrayList<Vestito> negozio_list = new ArrayList<>();
        Vestito vestito_da_inviare=null;
        int presente=0;

        try {
            String file_client = nome_client + ".ser";
            System.out.println("NOME DEL FILE CLIENT-->" + file_client);
            //APRO IL MAGAZZINO
            FileInputStream fis = new FileInputStream("MAGAZZINO.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            list = (ArrayList<Vestito>) ois.readObject();
            ois.close();
            //VISUALIZZO LA LISTA MAGAZZINO
            System.out.println("**************************************");
            System.out.println(list.toString());
            System.out.println("**************************************");
            //APRO IL FILE DEL CLIENT

            FileInputStream fis1 = new FileInputStream(file_client);
            ObjectInputStream ois1 = new ObjectInputStream(fis1);
            negozio_list = (ArrayList<Vestito>) ois1.readObject();
            ois1.close();


        } catch(FileNotFoundException ex){
            System.out.println("ERRORE: LA TUA GIACENZA E' VUOTA!");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        for( Vestito v: list){
            if(v.getId_capo()==id_capo_rifornire) {
                System.out.println("TROVATO!" + v.toString());
                presente=1;
                vestito_da_inviare = v;


            }
        }

        if(presente==1) {
            list.remove(vestito_da_inviare); //RIMUOVO DAL MAGAZZINO
            negozio_list.add(vestito_da_inviare); //AGGIUNGO NELLA LISTA GIACENZA DEL NEGOZIO CHE L'HA RICHIESTO
            try {

                //AGGIORNO LA GIACENZA DEL CLIENT
                String file_client = nome_client + ".ser";
                System.out.println("NOME DEL FILE CLIENT-->" + file_client);
                FileOutputStream fos1 = new FileOutputStream(file_client);
                ObjectOutputStream oos1 = new ObjectOutputStream(fos1);
                oos1.writeObject(negozio_list);
                System.out.println("HO INSERITO NEL FILE DEL CLIENT->" + negozio_list.toString());
                oos1.close();


            } catch (IOException e) {
                e.printStackTrace();
            }


        }


        return vestito_da_inviare;
    }

    public ArrayList<Vestito> getListCopy(String nome_negozio) throws IOException {
        ArrayList<Vestito> negozio_list = new ArrayList<>();
        String file_client = nome_negozio + ".ser";
        //APRO IL FILE DEL NEGOZIO

        try {

            FileInputStream fis = new FileInputStream(file_client);
            ObjectInputStream ois = new ObjectInputStream(fis);
            negozio_list = (ArrayList<Vestito>) ois.readObject();


        } catch(FileNotFoundException ex) {
            System.out.println("ERRORE: LA TUA GIACENZA E' VUOTA!");
        }
        catch (ClassNotFoundException e) {

            e.printStackTrace();
        }

        return negozio_list;
    }

    @Override
    public String toString() {
        String d;
        d = "Inizio_Lista";
        d = d + "modifica_data: " + ultima_modifica;
        for (Vestito v: list){
            d = d + "Categoria: " + v.getCategoria();
            d = d + "Tipo_Abbigliamento: " + v.getTipo_abbigliamento();
            d = d + "Modello: " + v.getModello();
            d = d + "Stagione: " + v.getStagione();
            d = d + "Id_Capo:" + v.getId_capo();
            d = d + "Id_quantità:"+ v.getId_quantita();
            d = d + "Colore: " + v.getColore();
            d = d + "Taglia: " + v.getTaglia();
            d = d + "Prezzo: " + v.getPrezzo();
        }
        d = d + "Fine_Lista";
        return d;
    }

    public synchronized void salvaSuFile() throws IOException {
        if(list.isEmpty()){
            System.out.println("[THREAD AGGIORNAMENTO]--->LOADING.......");
        }else{
            FileOutputStream fos = new FileOutputStream("MAGAZZINO.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(list);
            oos.close();
            System.out.println("[THREAD AGGIORNAMENTO]--->LISTA SALVATA CORRETTAMENTE!!!!!!!");
            System.out.println("************************************************************");
            System.out.println(list.toString());
            System.out.println("************************************************************");

        }



    }


}

