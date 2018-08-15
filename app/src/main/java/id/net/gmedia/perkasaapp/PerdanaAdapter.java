package id.net.gmedia.perkasaapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PerdanaAdapter extends RecyclerView.Adapter<PerdanaAdapter.PerdanaViewHolder> {

    private List<PerdanaModel> listPerdana;

    PerdanaAdapter(List<PerdanaModel> listPerdana){
        this.listPerdana = listPerdana;
    }

    @NonNull
    @Override
    public PerdanaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_perdana_stok, parent, false);
        return new PerdanaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PerdanaViewHolder holder, int position) {
        PerdanaModel perdana = listPerdana.get(position);
        holder.txt_nama.setText(perdana.getNama());
        holder.txt_sisa.setText(String.valueOf(perdana.getStok()));
        holder.txt_nota1.setText(perdana.getNota1().getNama());
        holder.txt_jumlah1.setText(String.valueOf(perdana.getNota1().getJumlah()));
        holder.txt_nota2.setText(perdana.getNota2().getNama());
        holder.txt_jumlah2.setText(String.valueOf(perdana.getNota2().getJumlah()));
    }

    @Override
    public int getItemCount() {
        return listPerdana.size();
    }

    class PerdanaViewHolder extends RecyclerView.ViewHolder{

        private TextView txt_nama, txt_nota1, txt_nota2, txt_jumlah1, txt_jumlah2, txt_sisa;
        //private ListView list_nota;

        private PerdanaViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_nama = itemView.findViewById(R.id.txt_nama);
            txt_sisa = itemView.findViewById(R.id.txt_sisa);
            txt_nota1 = itemView.findViewById(R.id.txt_nota1);
            txt_nota2 = itemView.findViewById(R.id.txt_nota2);
            txt_jumlah1 = itemView.findViewById(R.id.txt_jumlah1);
            txt_jumlah2 = itemView.findViewById(R.id.txt_jumlah2);
        }
    }
}
