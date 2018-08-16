package id.net.gmedia.perkasaapp;

import java.util.ArrayList;
import java.util.List;

public class NotaModel {
    private String id;
    private List<TransaksiModel> listTransaksi = new ArrayList<>();

    public NotaModel(String id, List<TransaksiModel> listTransaksi){
        this.id = id;
        this.listTransaksi = listTransaksi;
    }

    NotaModel(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void addTransaksi(TransaksiModel transaksi){
        listTransaksi.add(transaksi);
    }

    public List<TransaksiModel> getListTransaksi() {
        return listTransaksi;
    }

    public TransaksiModel getTransaksi(int i){
        return listTransaksi.get(i);
    }
}
