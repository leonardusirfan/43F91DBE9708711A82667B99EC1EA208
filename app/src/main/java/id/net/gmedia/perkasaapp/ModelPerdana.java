package id.net.gmedia.perkasaapp;

import android.os.Parcel;
import android.os.Parcelable;

public class ModelPerdana implements Parcelable{
    private String surat_jalan;
    private String nama;
    private int harga;
    private int stok;

    public ModelPerdana(String nama, String surat_jalan, int harga, int stok){
        this.nama = nama;
        this.surat_jalan = surat_jalan;
        this.stok = stok;
        this.harga = harga;
    }

    public ModelPerdana(String nama, int harga, int stok){
        this.nama = nama;
        this.stok = stok;
        this.harga = harga;
    }

    public ModelPerdana(String nama, int stok){
        this.nama = nama;
        this.stok = stok;
        this.harga = 0;
    }

    public String getSurat_jalan() {
        return surat_jalan;
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

    //METHODE PARCELABLE
    //Konstruktor Parcelable
    private ModelPerdana(Parcel in){
        this.nama = in.readString();
        this.surat_jalan = in.readString();
        this.harga = in.readInt();
        this.stok = in.readInt();
    }

    //Kelas CREATOR Parcelable
    public static final Parcelable.Creator<ModelPerdana> CREATOR = new Parcelable.Creator<ModelPerdana>(){

        @Override
        public ModelPerdana createFromParcel(Parcel source) {
            return new ModelPerdana(source);
        }

        @Override
        public ModelPerdana[] newArray(int size) {
            return new ModelPerdana[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nama);
        dest.writeString(this.surat_jalan);
        dest.writeInt(this.harga);
        dest.writeInt(this.stok);
    }
}