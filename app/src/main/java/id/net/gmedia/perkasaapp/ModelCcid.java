package id.net.gmedia.perkasaapp;

public class ModelCcid {
    private String ccid;
    private String nama;
    private int harga;

    ModelCcid(String ccid, String nama, int harga){
        this.ccid = ccid;
        this.nama = nama;
        this.harga = harga;
    }

    public String getCcid() {
        return ccid;
    }

    public String getNama() {
        return nama;
    }

    public int getHarga() {
        return harga;
    }
}
