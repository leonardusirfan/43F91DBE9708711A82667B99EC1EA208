package id.net.gmedia.perkasaapp;

public class ModelKomplain {
    private String nama;
    private String komplain;

    ModelKomplain(String nama, String komplain){
        this.nama = nama;
        this.komplain = komplain;
    }

    public String getNama(){
        return nama;
    }

    public String getKomplain() {
        return komplain;
    }
}
