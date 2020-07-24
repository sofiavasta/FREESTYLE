

public class Vestito {

    private String categoria;
    private String tipo_abbigliamento;
    private String modello;
    private String stagione;
    private int id_capo;
    private int id_quantita;
    private String colore;
    private String taglia;
    private float prezzo;

    public Vestito(String categoria, String tipo_abbigliamento, String modello, String stagione, int id_capo, int id_quantita, String colore, String taglia, float prezzo) {
        this.categoria = categoria;
        this.tipo_abbigliamento = tipo_abbigliamento;
        this.modello = modello;
        this.stagione = stagione;
        this.id_capo = id_capo;
        this.id_quantita = id_quantita;
        this.colore = colore;
        this.taglia = taglia;
        this.prezzo = prezzo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTipo_abbigliamento() {
        return tipo_abbigliamento;
    }

    public void setTipo_abbigliamento(String tipo_abbigliamento) {
        this.tipo_abbigliamento = tipo_abbigliamento;
    }

    public String getModello() {
        return modello;
    }

    public void setModello(String modello) {
        this.modello = modello;
    }

    public String getStagione() {
        return stagione;
    }

    public void setStagione(String stagione) {
        this.stagione = stagione;
    }

    public int getId_capo() {
        return id_capo;
    }

    public void setId_capo(int id_capo) {
        this.id_capo = id_capo;
    }

    public int getId_quantita() {
        return id_quantita;
    }

    public void setId_quantita(int id_quantita) {
        this.id_quantita = id_quantita;
    }

    public String getColore() {
        return colore;
    }

    public void setColore(String colore) {
        this.colore = colore;
    }

    public String getTaglia() {
        return taglia;
    }

    public void setTaglia(String taglia) {
        this.taglia = taglia;
    }

    public float getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(float prezzo) {
        this.prezzo = prezzo;
    }

    @Override
    public String toString() {
        return "Vestito{" +
                "categoria='" + categoria + '\'' +
                ", tipo_abbigliamento='" + tipo_abbigliamento + '\'' +
                ", modello='" + modello + '\'' +
                ", stagione='" + stagione + '\'' +
                ", id_capo=" + id_capo +
                ", id_quantita=" + id_quantita +
                ", colore='" + colore + '\'' +
                ", taglia='" + taglia + '\'' +
                ", prezzo=" + prezzo +
                '}';
    }
}
