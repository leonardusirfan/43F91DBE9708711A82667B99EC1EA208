package id.net.gmedia.perkasaapp;

import android.os.Parcel;
import android.os.Parcelable;

public class MkiosModel implements Parcelable{
    private String nama;
    private String nomor;
    private String tanggal;

    MkiosModel(String nama, String nomor, String tanggal){
        this.nama = nama;
        this.nomor = nomor;
        this.tanggal = tanggal;
    }

    public String getNama(){
        return nama;
    }

    public String getNomor(){
        return nomor;
    }

    public String getTanggal(){
        return tanggal;
    }

    private MkiosModel(Parcel in){
        this.nama = in.readString();
        this.nomor = in.readString();
        this.tanggal = in.readString();
    }

    public static final Parcelable.Creator<MkiosModel> CREATOR = new Parcelable.Creator<MkiosModel>() {
        @Override
        public MkiosModel createFromParcel(Parcel source) {
            return new MkiosModel(source);
        }

        @Override
        public MkiosModel[] newArray(int size) {
            return new MkiosModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nama);
        dest.writeString(nomor);
        dest.writeString(tanggal);
    }
}
