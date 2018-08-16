package id.net.gmedia.perkasaapp;

import android.os.Parcel;
import android.os.Parcelable;

public class OutletModel implements Parcelable {
    private String nama;
    private String alamat;
    private String nomor;
    private double latitude;
    private double longitude;

    OutletModel(String nama, String alamat, String nomor){
        this.nama = nama;
        this.alamat = alamat;
        this.nomor = nomor;

        latitude = 0;
        longitude = 0;
    }

    OutletModel(String nama, String alamat, String nomor, double latitude, double longitude){
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

    //PARCELABLE

    //konstruktor Parceable
    private OutletModel(Parcel in){
        this.nama = in.readString();
        this.nomor = in.readString();
        this.alamat = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
    }

    //kelas Creator Parcelable
    public static final Parcelable.Creator<OutletModel> CREATOR = new Parcelable.Creator<OutletModel>(){
        @Override
        public OutletModel createFromParcel(Parcel source) {
            return new OutletModel(source);
        }

        @Override
        public OutletModel[] newArray(int size) {
            return new OutletModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nama);
        dest.writeString(alamat);
        dest.writeString(nomor);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }
}
