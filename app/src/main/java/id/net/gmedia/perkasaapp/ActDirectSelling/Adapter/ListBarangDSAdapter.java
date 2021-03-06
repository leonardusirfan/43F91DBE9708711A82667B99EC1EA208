package id.net.gmedia.perkasaapp.ActDirectSelling.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.maulana.custommodul.CustomItem;
import com.maulana.custommodul.ItemValidation;

import java.util.List;

import id.net.gmedia.perkasaapp.ActDirectSelling.DirectSellingPulsa;
import id.net.gmedia.perkasaapp.R;


/**
 * Created by Shin on 1/8/2017.
 */

public class ListBarangDSAdapter extends ArrayAdapter{

    private Activity context;
    private List<CustomItem> items;
    private ItemValidation iv = new ItemValidation();
    public static int selectedItem = 0;

    public ListBarangDSAdapter(Activity context, List<CustomItem> items) {
        super(context, R.layout.adapter_barang_ds, items);
        this.context = context;
        this.items = items;
        this.selectedItem = 0;
    }

    private static class ViewHolder {
        private TextView tvItem;
        private RadioButton rbItem;
    }

    public List<CustomItem> getData(){

        return items;
    }

    public void setSelected(int position){

        selectedItem = position;
        notifyDataSetChanged();
    }

    public void addMoreData(List<CustomItem> moreData){

        items.addAll(moreData);
        notifyDataSetChanged();
    }

    public void addData(CustomItem moreData){

        items.add(moreData);
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
            convertView = inflater.inflate(R.layout.adapter_barang_ds, null);
            holder.tvItem = (TextView) convertView.findViewById(R.id.tv_item);
            holder.rbItem = (RadioButton) convertView.findViewById(R.id.rb_item);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        final CustomItem itemSelected = items.get(position);
        holder.tvItem.setText(itemSelected.getItem2());
        if(!itemSelected.getItem3().equals("0")){
            holder.rbItem.setText(iv.ChangeToRupiahFormat(itemSelected.getItem3()));
        }else{
            holder.rbItem.setText("");
        }


        if(selectedItem == position){

            holder.rbItem.setChecked(true);
            //DetailInjectPulsa.setSelectedItem(itemSelected);
        }else{
            holder.rbItem.setChecked(false);
        }

        holder.rbItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedItem = position;
                ((DirectSellingPulsa)context).setSelectedItem(itemSelected);
                notifyDataSetChanged();
            }
        });

        return convertView;

    }
}
