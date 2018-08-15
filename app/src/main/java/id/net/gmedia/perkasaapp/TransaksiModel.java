package id.net.gmedia.perkasaapp;

public class TransaksiModel {
    private String nama;
    private int jumlah;

    TransaksiModel(String nama, int jumlah){
        this.nama = nama;
        this.jumlah = jumlah;
    }

    public String getNama(){
        return nama;
    }

    public int getJumlah() {
        return jumlah;
    }
}
