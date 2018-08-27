package id.net.gmedia.perkasaapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class AdapterOrderMkios2 extends RecyclerView.Adapter<AdapterOrderMkios2.OrderMkios2ViewHolder> {

    private List<ModelPulsa> listPulsa;
    private List<Integer> listTotal;
    private TextView txt_total;
    private int total = 0;

    AdapterOrderMkios2(List<ModelPulsa> listPulsa, List<Integer> listTotal, TextView txt_total) {
        this.listPulsa = listPulsa;
        this.listTotal = listTotal;
        this.txt_total = txt_total;
    }

    @NonNull
    @Override
    public OrderMkios2ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_mkios2, parent, false);
        return new OrderMkios2ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderMkios2ViewHolder holder, int position) {
        final int current = position;
        final ModelPulsa pulsa = listPulsa.get(position);

        if(pulsa.getPulsa() == 1){
            holder.txt_nama_large.setText(R.string.bulk);
            holder.txt_nama_small.setText(R.string.bulk);
            holder.txt_jumlah.setFilters(new InputFilter[] {new InputFilter.LengthFilter(8)});
        }
        else
        {
            holder.txt_nama_large.setText(String.format(Locale.getDefault(), "%dS", pulsa.getPulsa()));
            holder.txt_nama_small.setText(String.format(Locale.getDefault(), "S%d", pulsa.getPulsa()));
            holder.txt_harga.setText(RupiahFormatterUtil.getRupiah(pulsa.getHarga()));
        }

        final TextView txt_total = holder.txt_total;
        holder.txt_jumlah.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().equals("")){
                    int new_total = Integer.parseInt(s.toString()) * pulsa.getHarga();
                    txt_total.setText(RupiahFormatterUtil.getRupiah(new_total));
                    updateTotal(current, new_total);
                }
                else{
                    int new_total = 0;
                    txt_total.setText(RupiahFormatterUtil.getRupiah(new_total));
                    updateTotal(current, new_total);
                }
            }
        });
    }

    private void updateTotal(int position, int new_value){
        total = total - listTotal.get(position);
        listTotal.set(position, new_value);
        total += new_value;
        txt_total.setText(RupiahFormatterUtil.getRupiah(total));
    }

    @Override
    public int getItemCount() {
        return listPulsa.size();
    }

    class OrderMkios2ViewHolder extends RecyclerView.ViewHolder{

        private TextView txt_nama_large, txt_nama_small, txt_jumlah, txt_harga, txt_total;

        private OrderMkios2ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_nama_large = itemView.findViewById(R.id.txt_nama_large);
            txt_nama_small = itemView.findViewById(R.id.txt_nama_small);
            txt_jumlah = itemView.findViewById(R.id.txt_jumlah);
            txt_harga = itemView.findViewById(R.id.txt_harga);
            txt_total = itemView.findViewById(R.id.txt_total);
        }
    }
}
