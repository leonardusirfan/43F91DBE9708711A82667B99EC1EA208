package id.net.gmedia.perkasaapp.ActOrderPerdana.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import id.net.gmedia.perkasaapp.ActOrderPerdana.ActivityOrderPerdana3;
import id.net.gmedia.perkasaapp.ModelCcid;
import id.net.gmedia.perkasaapp.R;
import id.net.gmedia.perkasaapp.RupiahFormatterUtil;

public class AdapterOrderPerdanaCcid extends RecyclerView.Adapter<AdapterOrderPerdanaCcid.OrderPerdanaCcidViewHolder> {

    private Context context;
    private List<ModelCcid> listCcid;
    private TextView txt_no, txt_nama, txt_harga;

    public AdapterOrderPerdanaCcid(Context context, List<ModelCcid> listCcid, TextView txt_no, TextView txt_nama, TextView txt_harga){
        this.listCcid = listCcid;
        this.context = context;
        this.txt_no = txt_no;
        this.txt_nama = txt_nama;
        this.txt_harga = txt_harga;
    }

    @NonNull
    @Override
    public OrderPerdanaCcidViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_perdana3, parent, false);
        return new OrderPerdanaCcidViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderPerdanaCcidViewHolder holder, int position) {
        final int current_idx = position;
        final ModelCcid ccid = listCcid.get(current_idx);

        holder.txt_no_ccid.setText(String.valueOf(position + 1));
        holder.txt_nama_ccid.setText(ccid.getNama());
        holder.txt_harga_ccid.setText(RupiahFormatterUtil.getRupiah(ccid.getHarga()));

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listCcid.remove(current_idx);
                ((ActivityOrderPerdana3)context).updateCcid();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_no.setText(ccid.getCcid());
                txt_nama.setText(ccid.getNama());
                txt_harga.setText(RupiahFormatterUtil.getRupiah(ccid.getHarga()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listCcid.size();
    }

    class OrderPerdanaCcidViewHolder extends RecyclerView.ViewHolder{

        private TextView txt_no_ccid, txt_nama_ccid, txt_harga_ccid;
        private ImageView btn_delete;
        private OrderPerdanaCcidViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_no_ccid = itemView.findViewById(R.id.txt_no_ccid);
            txt_nama_ccid = itemView.findViewById(R.id.txt_nama_ccid);
            txt_harga_ccid = itemView.findViewById(R.id.txt_harga_ccid);

            btn_delete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
