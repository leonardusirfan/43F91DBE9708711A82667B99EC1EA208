package id.net.gmedia.perkasaapp.ActDeposit.AdapterDeposit;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.maulana.custommodul.CustomItem;

import java.util.List;

import id.net.gmedia.perkasaapp.R;

public class ListAdapterPembelian extends ArrayAdapter {
    private Activity context;
    private List<CustomItem> items;

    public ListAdapterPembelian(@NonNull Activity context, List<CustomItem> items) {
        super(context, R.layout.adapter_penjualan_header);
        this.context = context;
        this.items = items;
    }

    private static class ViewHolder {
    private TextView tvItem1;
    }

    public void addMoreData(List<CustomItem> itemsToAdd) {
    items.addAll(itemsToAdd);
    notifyDataSetChanged();

 }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = new ViewHolder();

        if(convertView == null){
            LayoutInflater inflater = context.getLayoutInflater();
            convertView = inflater.inflate(R.layout.adapter_header_pengajuan_deposit, null);
            holder.tvItem1 = (TextView) convertView.findViewById(R.id.tv_item1);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        final CustomItem itemSelected = items.get(position);
        holder.tvItem1.setText(itemSelected.getItem2());

        return convertView;

    }

}