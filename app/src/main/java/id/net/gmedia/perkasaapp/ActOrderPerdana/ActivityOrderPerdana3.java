package id.net.gmedia.perkasaapp.ActOrderPerdana;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.widget.CompoundButtonCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

import id.net.gmedia.perkasaapp.ActOrderPerdana.Adapter.AdapterOrderPerdanaCcid;
import id.net.gmedia.perkasaapp.ActOrderPerdana.Adapter.AdapterOrderPerdanaCcidRentang;
import id.net.gmedia.perkasaapp.ModelCcid;
import id.net.gmedia.perkasaapp.ModelPerdana;
import id.net.gmedia.perkasaapp.R;
import id.net.gmedia.perkasaapp.RupiahFormatterUtil;

public class ActivityOrderPerdana3 extends AppCompatActivity {

    //List semua Ccid yang bisa dipilih
    private List<ModelCcid> allCcid = new ArrayList<>();
    //List Ccid yang sudah dipilih atau akan dibeli
    private List<ModelCcid> selectedCcid = new ArrayList<>();

    //UI yang menampilkan CCID
    private TextView txt_total, txt_total_harga, txt_no_ccid, txt_nama_ccid, txt_harga_ccid;

    //UI Checkbox
    private LinearLayout layout_checkbox;
    private CheckBox all_checkbox;
    private List<CheckBox> checkBoxes = new ArrayList<>();

    //Popup dialog pembelian
    private Dialog dialog_list, dialog_rentang;

    private String nama_perdana = "";
    private AdapterOrderPerdanaCcid adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_perdana3);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Order Perdana");
        }

        TextView txt_nama, txt_harga;
        Button btn_list, btn_rentang, btn_scan;
        txt_nama = findViewById(R.id.txt_nama);
        txt_harga = findViewById(R.id.txt_harga);
        txt_no_ccid = findViewById(R.id.txt_no_ccid);
        txt_nama_ccid = findViewById(R.id.txt_nama_ccid);
        txt_harga_ccid = findViewById(R.id.txt_harga_ccid);
        txt_total = findViewById(R.id.txt_total);
        txt_total_harga = findViewById(R.id.txt_total_harga);

        btn_list = findViewById(R.id.btn_list);
        btn_rentang = findViewById(R.id.btn_rentang);
        btn_scan = findViewById(R.id.btn_scan);

        if(getIntent().hasExtra("perdana")){
            ModelPerdana perdana = getIntent().getParcelableExtra("perdana");
            nama_perdana = perdana.getNama();
            txt_nama.setText(perdana.getNama());
            txt_harga.setText(RupiahFormatterUtil.getRupiah(perdana.getHarga()));
        }

        //Mengisi allCcid yang berisi list CCID yang bisa dibeli
        initCcid();

        //Inisialisasi dialog list CCID
        dialog_list = new Dialog(ActivityOrderPerdana3.this, R.style.PopupTheme);
        dialog_list.setContentView(R.layout.dialog_order_perdana_list);
        layout_checkbox = dialog_list.findViewById(R.id.layout_checkbox);

        final TextView btn_simpan_list = dialog_list.findViewById(R.id.btn_simpan);
        final TextView btn_batal_list = dialog_list.findViewById(R.id.btn_batal);
        btn_batal_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_list.dismiss();
            }
        });
        btn_simpan_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(all_checkbox.isChecked()){
                    selectedCcid.addAll(allCcid);
                }
                else{
                    for(int i = 0; i < checkBoxes.size(); i++){
                        if(checkBoxes.get(i).isChecked()){
                            selectedCcid.add(allCcid.get(i));
                        }
                    }
                }
                updateCcid();
                dialog_list.dismiss();
            }
        });

        //Inisialisasi dialog rentang CCID
        dialog_rentang = new Dialog(ActivityOrderPerdana3.this, R.style.PopupTheme);
        dialog_rentang.setContentView(R.layout.dialog_order_perdana_rentang);
        final TextView txt_ccid_awal = dialog_rentang.findViewById(R.id.txt_ccid_awal);
        final TextView txt_ccid_akhir = dialog_rentang.findViewById(R.id.txt_ccid_akhir);
        final TextView txt_banyak_ccid = dialog_rentang.findViewById(R.id.txt_banyak_ccid);
        final TextView btn_simpan_rentang = dialog_rentang.findViewById(R.id.btn_simpan);
        final TextView btn_batal_rentang = dialog_rentang.findViewById(R.id.btn_batal);
        final Button btn_ambil_ccid = dialog_rentang.findViewById(R.id.btn_ambil_ccid);
        final RecyclerView rcy_ccid_rentang = dialog_rentang.findViewById(R.id.rcy_ccid);
        final List<ModelCcid> selectedCcidRentang = new ArrayList<>();
        final AdapterOrderPerdanaCcidRentang adapterRentang = new AdapterOrderPerdanaCcidRentang(selectedCcidRentang);
        RecyclerView.LayoutManager layoutManagerRentang = new LinearLayoutManager(ActivityOrderPerdana3.this);
        rcy_ccid_rentang.setLayoutManager(layoutManagerRentang);
        rcy_ccid_rentang.setItemAnimator(new DefaultItemAnimator());
        rcy_ccid_rentang.setAdapter(adapterRentang);

        btn_ambil_ccid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedCcidRentang.clear();
                adapterRentang.notifyDataSetChanged();

                String ccid_awal = txt_ccid_awal.getText().toString();
                String ccid_akhir = txt_ccid_akhir.getText().toString();

                if(ccid_awal.equals("") || ccid_akhir.equals("")){
                    selectedCcidRentang.addAll(allCcid);
                }
                else{
                    int banyak_ccid = 0;
                    int counter = 0;
                    if(!txt_banyak_ccid.getText().toString().equals("")){
                        banyak_ccid = Integer.parseInt(txt_banyak_ccid.getText().toString());
                    }

                    //Mengupdate CCID yang dipilih
                    for(ModelCcid c : allCcid){
                        if(c.getCcid().compareTo(ccid_awal) >= 0 && c.getCcid().compareTo(ccid_akhir) <= 0){
                            if(banyak_ccid == 0){
                                selectedCcidRentang.add(c);
                            }
                            else if(counter < banyak_ccid){
                                selectedCcidRentang.add(c);
                                counter += 1;
                            }
                            else{
                                break;
                            }
                        }
                    }
                }

                adapterRentang.notifyDataSetChanged();
            }
        });

        btn_batal_rentang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_rentang.dismiss();
            }
        });
        btn_simpan_rentang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedCcid.addAll(selectedCcidRentang);
                updateCcid();
                dialog_rentang.dismiss();
            }
        });

        RecyclerView rcy_ccid = findViewById(R.id.rcy_ccid);
        adapter = new AdapterOrderPerdanaCcid(ActivityOrderPerdana3.this, selectedCcid, txt_no_ccid, txt_nama_ccid, txt_harga_ccid);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        rcy_ccid.setLayoutManager(layoutManager);
        rcy_ccid.setItemAnimator(new DefaultItemAnimator());
        rcy_ccid.setAdapter(adapter);

        btn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Menginisialisasi semua CCID yang bisa dipilih pada dialog
                checkBoxes.clear();
                layout_checkbox.removeAllViews();

                //Menginisialisasi Checkbox CCID
                all_checkbox = addCheckBox("All", 0);
                all_checkbox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(all_checkbox.isChecked()){
                            if(checkBoxes.size() > 0){
                                for(CheckBox c : checkBoxes){
                                    c.setChecked(true);
                                }
                            }
                        }
                        else{
                            if(checkBoxes.size() > 0){
                                for(CheckBox c : checkBoxes){
                                    c.setChecked(false);
                                }
                            }
                        }
                    }
                });

                for(int i = 0; i < allCcid.size(); i++) {
                    final CheckBox c = addCheckBox(allCcid.get(i).getCcid(), i + 1);
                    c.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(!c.isChecked()){
                                all_checkbox.setChecked(false);
                            }
                        }
                    });
                    checkBoxes.add(c);
                }

                dialog_list.show();
            }
        });
        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(ActivityOrderPerdana3.this);
                integrator.setOrientationLocked(false);
                integrator.initiateScan();
            }
        });
        btn_rentang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedCcidRentang.clear();
                adapterRentang.notifyDataSetChanged();

                dialog_rentang.show();
            }
        });
    }

    private CheckBox addCheckBox(String text, int id){
        //Fungsi untuk Menambah item CheckBox baru ke Dialog
        CheckBox cb = new CheckBox(ActivityOrderPerdana3.this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int padding5 = (int) getResources().getDimension(R.dimen.padding5);
        params.setMargins(padding5, padding5, padding5, padding5);
        cb.setText(text);
        if (Build.VERSION.SDK_INT < 21) {
            CompoundButtonCompat.setButtonTintList(cb, ColorStateList.valueOf(Color.WHITE));
        } else {
            cb.setButtonTintList(ColorStateList.valueOf(Color.WHITE));
        }
        cb.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimension(R.dimen.text14));
        cb.setTypeface(Typeface.DEFAULT_BOLD);
        cb.setTextColor(Color.WHITE);

        cb.setId(id);
        layout_checkbox.addView(cb, params);
        return cb;
    }

    private void initCcid(){
        allCcid.clear();

        //Inisialisasi CCID yang bisa dipilih, bisa diganti dengan pembacaan Web Service
        ModelCcid ccid = new ModelCcid("0050000371774706","128k 4G LTE 2FF/3FF/4FF USIM MIGRATION (S100)", 3000);
        allCcid.add(ccid);

        ccid = new ModelCcid("0040000371774501","128k 4G LTE 2FF/3FF/4FF USIM MIGRATION (S100)", 3500);
        allCcid.add(ccid);

        ccid = new ModelCcid("0120000371779102","128k 4G LTE 2FF/3FF/4FF USIM MIGRATION (S100)", 2000);
        allCcid.add(ccid);

        ccid = new ModelCcid("0120000371779103","128k 4G LTE 2FF/3FF/4FF USIM MIGRATION (S100)", 2500);
        allCcid.add(ccid);

        ccid = new ModelCcid("0120000371779104","128k 4G LTE 2FF/3FF/4FF USIM MIGRATION (S100)", 2500);
        allCcid.add(ccid);
    }

    public void updateCcid(){
        //Mengupdate tampilan informasi berdasarkan CCID yang dipilih
        //Update RecyclerView
        System.out.println(this.selectedCcid.size());
        adapter.notifyDataSetChanged();

        //Update total
        txt_total.setText(String.valueOf(selectedCcid.size()));
        int total = 0;
        for(ModelCcid c : selectedCcid){
            total += c.getHarga();
        }
        txt_total_harga.setText(RupiahFormatterUtil.getRupiah(total));

        //Update CCID ditampilkan
        txt_no_ccid.setText("-");
        txt_nama_ccid.setText("-");
        txt_harga_ccid.setText("-");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //Hasil dari QR Code Scanner
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() != null){
                System.out.println(result.getContents());

                //Menambahkan data CCID ke list
                selectedCcid.add(new ModelCcid(result.getContents(), nama_perdana, 3000));
                updateCcid();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
