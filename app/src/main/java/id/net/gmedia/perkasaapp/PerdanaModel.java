package id.net.gmedia.perkasaapp;

import android.os.Parcel;
import android.os.Parcelable;

public class PerdanaModel implements Parcelable{

    private String nama;
    private String alamat;

    PerdanaModel(String nama, String alamat){
        this.nama = nama;
        this.alamat = alamat;
    }

    public String getNama(){
        return nama;
    }

    public String getAlamat(){
        return alamat;
    }

    private PerdanaModel(Parcel in){
        this.nama = in.readString();
        this.alamat = in.readString();
    }

    public static final Parcelable.Creator<PerdanaModel> CREATOR = new Parcelable.Creator<PerdanaModel>() {
        @Override
        public PerdanaModel createFromParcel(Parcel source) {
            return new PerdanaModel(source);
        }

        @Override
        public PerdanaModel[] newArray(int size) {
            return new PerdanaModel[size];
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
