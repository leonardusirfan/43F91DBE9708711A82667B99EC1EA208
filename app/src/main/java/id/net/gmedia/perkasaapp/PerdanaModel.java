package id.net.gmedia.perkasaapp;

public class PerdanaModel {
    private String nama;
    private int harga;
    private int stok;

    PerdanaModel(String nama, int harga, int stok){
        this.nama = nama;
        this.stok = stok;
        this.harga = harga;
    }

    PerdanaModel(String nama, int stok){
        this.nama = nama;
        this.stok = stok;
        this.harga = 0;
    }

    public int getHarga(){
        return harga;
    }

    public int getStok() {
        return stok;
    }

    public String getNama() {
        return nama;
    }
}