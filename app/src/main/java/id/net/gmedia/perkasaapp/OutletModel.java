package id.net.gmedia.perkasaapp;

public class OutletModel {
    private String nama;
    private String alamat;
    private String nomor;
    private double latitude;
    private double longitude;
    private String status;

    public OutletModel(String nama, String alamat, String nomor, double latitude, double longitude){
        this.nama = nama;
        this.alamat = alamat;
        this.nomor = nomor;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getNama(){
        return nama;
    }

    public String getAlamat(){
        return alamat;
    }

    public String getNomor() {
        return nomor;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getStatus(){
        return status;
    }
}
