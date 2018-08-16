package id.net.gmedia.perkasaapp;

public class PerdanaModel {
    private String nama;
    private int stok;

    PerdanaModel(String nama, int stok){
        this.nama = nama;
        this.stok = stok;
    }

    public int getStok() {
        return stok;
    }

    public String getNama() {
        return nama;
    }
}