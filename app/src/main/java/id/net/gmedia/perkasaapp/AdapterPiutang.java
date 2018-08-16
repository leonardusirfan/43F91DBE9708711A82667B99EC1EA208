package id.net.gmedia.perkasaapp;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class AdapterPiutang extends RecyclerView.Adapter<AdapterPiutang.PiutangViewHolder> {

    private List<OutletModel> listOutlet;

    AdapterPiutang(List<OutletModel> listOutlet){
        this.listOutlet = listOutlet;
    }

    @NonNull
    @Override
    public PiutangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_piutang, parent, false);
        return new PiutangViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PiutangViewHolder holder, int position) {
        OutletModel outlet = listOutlet.get(position);

        holder.txt_nama.setText(outlet.getNama());
        holder.txt_alamat.setText(outlet.getAlamat());
        holder.txt_piutang.setText(RupiahFormatterUtil.getRupiah(outlet.getPiutang()));
    }

    @Override
    public int getItemCount() {
        return listOutlet.size();
    }


    class PiutangViewHolder extends RecyclerView.ViewHolder{
        private TextView txt_nama, txt_alamat, txt_piutang;

        private PiutangViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_nama = itemView.findViewById(R.id.txt_nama);
            txt_alamat = itemView.findViewById(R.id.txt_alamat);
            txt_piutang = itemView.findViewById(R.id.txt_piutang);
        }
    }
}
