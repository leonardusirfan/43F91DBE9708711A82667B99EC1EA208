package id.net.gmedia.perkasaapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class AdapterOrderPerdanaBarang extends RecyclerView.Adapter<AdapterOrderPerdanaBarang.OrderPerdanaBarangViewHolder> {

    private List<ModelPerdana> listPerdana;

    AdapterOrderPerdanaBarang(List<ModelPerdana> listPerdana){
        this.listPerdana = listPerdana;
    }

    @NonNull
    @Override
    public OrderPerdanaBarangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_perdana2, parent, false);
        return new OrderPerdanaBarangViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderPerdanaBarangViewHolder holder, final int position) {
        final ModelPerdana perdana = listPerdana.get(position);

        holder.txt_nama.setText(perdana.getNama());
        holder.txt_surat_jalan.setText(perdana.getSurat_jalan());
        holder.txt_harga.setText(RupiahFormatterUtil.getRupiah(perdana.getHarga()));
        holder.txt_stok.setText(String.valueOf(perdana.getStok()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), ActivityOrderPerdana3.class);
                i.putExtra("perdana", perdana);
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPerdana.size();
    }

    class OrderPerdanaBarangViewHolder extends RecyclerView.ViewHolder{

        private TextView txt_nama, txt_surat_jalan, txt_harga, txt_stok;

        private OrderPerdanaBarangViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_nama = itemView.findViewById(R.id.txt_nama);
            txt_surat_jalan = itemView.findViewById(R.id.txt_surat_jalan);
            txt_harga = itemView.findViewById(R.id.txt_harga);
            txt_stok = itemView.findViewById(R.id.txt_stok);
        }
    }
}
