package id.net.gmedia.perkasaapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class PerdanaOutletAdapter extends RecyclerView.Adapter<PerdanaOutletAdapter.PerdanaViewHolder> {

    private List<PerdanaOutletModel> perdanaList;

    PerdanaOutletAdapter(List<PerdanaOutletModel> mkiosList){
        this.perdanaList = mkiosList;
    }

    @NonNull
    @Override
    public PerdanaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_perdana, parent, false);
        return new PerdanaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PerdanaViewHolder holder, int position) {
        final PerdanaOutletModel perdana = perdanaList.get(position);
        holder.txt_nama.setText(perdana.getNama());
        holder.txt_alamat.setText(perdana.getAlamat());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), PerdanaOrderActivity.class);
                i.putExtra("perdana", perdana);
                view.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return perdanaList.size();
    }

    class PerdanaViewHolder extends RecyclerView.ViewHolder{

        private TextView txt_nama, txt_alamat;

        private PerdanaViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_nama = itemView.findViewById(R.id.txt_nama);
            txt_alamat = itemView.findViewById(R.id.txt_alamat);
        }
    }
}
