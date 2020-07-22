import java.io.Serializable;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Date;

public class VestitoList implements Serializable {

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

    public synchronized void remove(int id_capo_rimozione) {
        ultima_modifica = new Date().toString();
        Vestito vestito_da_rimuovere = null;
        for( Vestito v: list){
            if(v.getId_capo()==id_capo_rimozione){
                vestito_da_rimuovere=v;
                System.out.println("TROVATO!"+vestito_da_rimuovere.toString());
            }
        }

        list.remove(vestito_da_rimuovere);
        System.out.println("RIMOSSO!"+vestito_da_rimuovere);

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

