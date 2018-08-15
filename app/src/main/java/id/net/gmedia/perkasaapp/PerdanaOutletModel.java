package id.net.gmedia.perkasaapp;

import android.os.Parcel;
import android.os.Parcelable;

public class PerdanaOutletModel implements Parcelable{

    private String nama;
    private String alamat;

    PerdanaOutletModel(String nama, String alamat){
        this.nama = nama;
        this.alamat = alamat;
    }

    public String getNama(){
        return nama;
    }

    public String getAlamat(){
        return alamat;
    }

    private PerdanaOutletModel(Parcel in){
        this.nama = in.readString();
        this.alamat = in.readString();
    }

    public static final Parcelable.Creator<PerdanaOutletModel> CREATOR = new Parcelable.Creator<PerdanaOutletModel>() {
        @Override
        public PerdanaOutletModel createFromParcel(Parcel source) {
            return new PerdanaOutletModel(source);
        }

        @Override
        public PerdanaOutletModel[] newArray(int size) {
            return new PerdanaOutletModel[size];
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
    }
}
