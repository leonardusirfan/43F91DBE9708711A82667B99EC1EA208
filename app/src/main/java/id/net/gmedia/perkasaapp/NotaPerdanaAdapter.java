package id.net.gmedia.perkasaapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NotaPerdanaAdapter extends RecyclerView.Adapter<NotaPerdanaAdapter.NotaPerdanaViewHolder>{

    private List<TransaksiModel> listTransaksi;

    public NotaPerdanaAdapter(List<TransaksiModel> listTransaksi){
        this.listTransaksi = listTransaksi;
    }

    @NonNull
    @Override
    public NotaPerdanaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_nota_perdana_stok, parent, false);
        return new NotaPerdanaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NotaPerdanaViewHolder holder, int position) {
        TransaksiModel transaksi = listTransaksi.get(position);
        holder.txt_nama.setText(transaksi.getNama());
        holder.txt_jumlah.setText(transaksi.getJumlah());
    }

    @Override
    public int getItemCount() {
        return listTransaksi.size();
    }

    class NotaPerdanaViewHolder extends RecyclerView.ViewHolder{
        private TextView txt_nama, txt_jumlah;

        public NotaPerdanaViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_nama = itemView.findViewById(R.id.txt_nama);
            txt_jumlah = itemView.findViewById(R.id.txt_jumlah);
        }
    }
}
