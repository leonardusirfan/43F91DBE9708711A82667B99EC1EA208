package id.net.gmedia.perkasaapp;

import java.util.List;

public class PerdanaModel {
    private String nama;
    private int stok;
    private TransaksiModel nota1;
    private TransaksiModel nota2;

    public PerdanaModel(String nama, int stok){
        this.nama = nama;
        this.stok = stok;
    }

    PerdanaModel(String nama, int stok, TransaksiModel nota1, TransaksiModel nota2){
        this.nama = nama;
        this.stok = stok;
        this.nota1 = nota1;
        this.nota2 = nota2;
    }

    public int getStok() {
        return stok;
    }

    public String getNama() {
        return nama;
    }

    public TransaksiModel getNota1() {
        return nota1;
    }

    public TransaksiModel getNota2() {
        return nota2;
    }
}
