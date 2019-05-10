package id.net.gmedia.perkasaapp.ActPengajuanPLSales.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maulana.custommodul.CustomItem;
import com.maulana.custommodul.FormatItem;
import com.maulana.custommodul.ItemValidation;

import java.util.List;

import id.net.gmedia.perkasaapp.ActPengajuanPLSales.DetailApprovalPL;
import id.net.gmedia.perkasaapp.R;

public class AdapterApprovalPlafon extends RecyclerView.Adapter<AdapterApprovalPlafon.PiutangViewHolder> {

    private List<CustomItem> listOutlet;
    private ItemValidation iv = new ItemValidation();
    private Context context;

    public AdapterApprovalPlafon(List<CustomItem> listOutlet, Context context){
        this.listOutlet = listOutlet;
        this.context = context;
    }

    @NonNull
    @Override
    public PiutangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_approval_pl, parent, false);
        return new PiutangViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PiutangViewHolder holder, int position) {

        final CustomItem outlet = listOutlet.get(position);

        holder.txtNama.setText(outlet.getItem4());
        holder.txtTgl.setText(iv.ChangeFormatDateString(outlet.getItem3(), FormatItem.formatTimestamp, FormatItem.formatDateTimeDisplay) + " "+ outlet.getItem2());
        holder.txtPengajuan.setText(iv.ChangeToRupiahFormat(iv.parseNullDouble(outlet.getItem5())));
        holder.txtStatus.setText(outlet.getItem6());
        holder.txtPengaju.setText(outlet.getItem7());

        holder.cvContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, DetailApprovalPL.class);
                intent.putExtra("id", outlet.getItem1());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listOutlet.size();
    }


    class PiutangViewHolder extends RecyclerView.ViewHolder{

        private TextView txtNama, txtTgl, txtPengajuan, txtPengaju, txtStatus;
        private CardView cvContainer;

        private PiutangViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNama = itemView.findViewById(R.id.txt_nama);
            txtTgl = itemView.findViewById(R.id.txt_tgl);
            txtPengaju = itemView.findViewById(R.id.txt_pengaju);
            txtStatus = itemView.findViewById(R.id.txt_status);
            txtPengajuan = itemView.findViewById(R.id.txt_pengajuan);
            cvContainer = itemView.findViewById(R.id.cv_container);
        }
    }
}
