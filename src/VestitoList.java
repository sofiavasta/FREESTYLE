
import java.util.ArrayList;
import java.util.Date;

public class VestitoList {

    String id_archivio;
    String ultima_modifica;
    private ArrayList<Vestito> list;

    public VestitoList() {
        this.list = new ArrayList<Vestito>();
    }

    public synchronized void add(Vestito v) {
        ultima_modifica = new Date().toString();
        list.add(v);
    }

    public synchronized void remove(int id_capo_rimozione, int id_quantita_rimozione) {
        ultima_modifica = new Date().toString();
        Vestito vestito_da_rimuovere = null;
        int nuova_quantita=0, id_quantita=0;
        for( Vestito v: list){
            if(v.getId_capo()==id_capo_rimozione) {
                vestito_da_rimuovere = v;
                System.out.println("TROVATO!" + vestito_da_rimuovere.toString());
                id_quantita = vestito_da_rimuovere.getId_quantita(); //quantità prima della rimozione
                if (id_quantita < id_quantita_rimozione) {
                    System.out.println("ERRORE! Quantità da rimuovere superiore all'attuale giacenza!");
                } else {
                    nuova_quantita = id_quantita - id_quantita_rimozione;  //nuova quantità dopo la rimozione
                    vestito_da_rimuovere.setId_quantita(nuova_quantita);   //aggiorna l'oggetto dopo il calcolo della nuova quantità
                    System.out.println("Quantitativo aggiornato: "+vestito_da_rimuovere.toString());
                }
            }
        }
        if(vestito_da_rimuovere.getId_quantita()<=1){     //quantità 1 e ne rimuovo 1
            list.remove(vestito_da_rimuovere);
            System.out.println("RIMOSSO!"+vestito_da_rimuovere);
            System.out.println("Fine quantità di questo prodotto, con il seguente id_capo: "+id_capo_rimozione);
        }

    }

    public synchronized void restock(int id_capo_rifornire, int numero_capi){
        ultima_modifica = new Date().toString();
        int id_quantita=0, nuova_quantita=0;
        for( Vestito v: list){
            if(v.getId_capo()==id_capo_rifornire) {
                System.out.println("TROVATO!" + v.toString());
                id_quantita = v.getId_quantita(); //quantità prima dell' aggiunta
                nuova_quantita = id_quantita + numero_capi;  //nuova quantità dopo la rifornitura
                v.setId_quantita(nuova_quantita);   //aggiorna l'oggetto dopo il calcolo della nuova quantità
                System.out.println("Quantitativo aggiornato: "+v.toString());

            }
        }
    }

    public ArrayList<Vestito> getListCopy() {
        ArrayList<Vestito> a_list = new ArrayList<>();
        a_list.addAll(list);
        return a_list;
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
}

