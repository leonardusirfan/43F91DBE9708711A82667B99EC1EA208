package id.net.gmedia.perkasaapp.ActOrderPerdana;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
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
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.maulana.custommodul.ApiVolley;
import com.maulana.custommodul.CustomView.DialogBox;
import com.maulana.custommodul.FormatItem;
import com.maulana.custommodul.ItemValidation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import id.net.gmedia.perkasaapp.ActOrderPerdana.Adapter.AdapterOrderPerdanaCcid;
import id.net.gmedia.perkasaapp.ActOrderPerdana.Adapter.AdapterOrderPerdanaCcidRentang;
import id.net.gmedia.perkasaapp.ModelCcid;
import id.net.gmedia.perkasaapp.ModelPerdana;
import id.net.gmedia.perkasaapp.R;
import id.net.gmedia.perkasaapp.RupiahFormatterUtil;
import id.net.gmedia.perkasaapp.Utils.ServerURL;

public class ActivityOrderPerdana3 extends AppCompatActivity {

    private Context context;
    private DialogBox dialogBox;
    private ItemValidation iv = new ItemValidation();

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

    private AdapterOrderPerdanaCcid adapter;

    private TextView txt_nama, txt_harga;
    private Button btn_list, btn_rentang, btn_scan;
    private String kdbrg = "", namabrg = "", hargabrg = "", suratJalan = "";
    private LinearLayout llTanggal;
    private EditText edtTanggal;
    private List<ModelCcid> selectedCcidRentang;
    private AdapterOrderPerdanaCcidRentang adapterRentang;
    private EditText txt_banyak_ccid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_perdana3);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Order Perdana");
        }

        context = this;
        dialogBox = new DialogBox(context);

        initUI();
        initEvent();
        //Mengisi allCcid yang berisi list CCID yang bisa dibeli
        initCcid(1, "", "", "");

        //Inisialisasi dialog list CCID
        dialog_list = new Dialog(ActivityOrderPerdana3.this, R.style.PopupTheme);
        dialog_list.setContentView(R.layout.dialog_order_perdana_list);
        layout_checkbox = dialog_list.findViewById(R.id.layout_checkbox);

        final EditText edtSearch = dialog_list.findViewById(R.id.edt_search);
        final TextView btn_simpan_list = dialog_list.findViewById(R.id.btn_simpan);
        final TextView btn_batal_list = dialog_list.findViewById(R.id.btn_batal);
        btn_batal_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_list.dismiss();
            }
        });

        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {

                if(id == EditorInfo.IME_ACTION_SEARCH){

                    checkBoxes.clear();
                    layout_checkbox.removeAllViews();

                    String keyword = edtSearch.getText().toString();

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

                                        boolean isExist = false;
                                        for(ModelCcid ccid: selectedCcid){
                                            if(c.getText().equals(ccid.getCcid())){

                                                isExist = true;
                                                break;
                                            }
                                        }

                                        c.setChecked(isExist);
                                    }
                                }
                            }
                        }
                    });

                    for(int i = 0; i < allCcid.size(); i++) {

                        if(allCcid.get(i).getCcid().contains(keyword)){

                            final CheckBox c = addCheckBox(allCcid.get(i).getCcid(), i + 1);
                            c.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(!c.isChecked()){
                                        all_checkbox.setChecked(false);
                                    }
                                }
                            });

                            boolean isExist = false;
                            for(ModelCcid ccid: selectedCcid){
                                if(c.getText().equals(ccid.getCcid())){

                                    isExist = true;
                                    break;
                                }
                            }

                            c.setChecked(isExist);

                            checkBoxes.add(c);
                        }
                    }

                    iv.hideSoftKey(context);
                    return true;
                }

                return false;
            }
        });

        btn_simpan_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i = 0; i < checkBoxes.size(); i++){

                    if(checkBoxes.get(i).isChecked()){

                        boolean isExist = false;
                        for(ModelCcid ccid: selectedCcid){

                            if(ccid.getCcid().equals(checkBoxes.get(i).getText())){
                                isExist = true;
                                break;
                            }
                        }

                        if(!isExist) selectedCcid.add(allCcid.get(i));
                    }else{

                        boolean isExist = false;
                        int j = 0;
                        for(ModelCcid ccid: selectedCcid){

                            if(ccid.getCcid().equals(checkBoxes.get(i).getText())){
                                isExist = true;
                                break;
                            }
                            j++;
                        }

                        if(isExist) selectedCcid.remove(j);
                    }
                }

                updateCcid();
                dialog_list.dismiss();
            }
        });

        // Selected CCID
        RecyclerView rcy_ccid = findViewById(R.id.rcy_ccid);
        adapter = new AdapterOrderPerdanaCcid(ActivityOrderPerdana3.this, selectedCcid, txt_no_ccid, txt_nama_ccid, txt_harga_ccid);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        rcy_ccid.setLayoutManager(layoutManager);
        rcy_ccid.setItemAnimator(new DefaultItemAnimator());
        rcy_ccid.setAdapter(adapter);

        // ambil dari list
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

                                    boolean isExist = false;
                                    for(ModelCcid ccid: selectedCcid){
                                        if(c.getText().equals(ccid.getCcid())){

                                            isExist = true;
                                            break;
                                        }
                                    }

                                    c.setChecked(isExist);
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

                    boolean isExist = false;
                    for(ModelCcid ccid: selectedCcid){
                        if(c.getText().equals(ccid.getCcid())){

                            isExist = true;
                            break;
                        }
                    }

                    c.setChecked(isExist);

                    checkBoxes.add(c);
                }

                dialog_list.show();
            }
        });

        // Ambil Dari rentang
        btn_rentang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showDialogRentangCCID();
            }
        });

        // Scan CCID
        btn_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(ActivityOrderPerdana3.this);
                integrator.setOrientationLocked(false);
                integrator.initiateScan();
            }
        });
    }

    private void showDialogRentangCCID() {

        // Ambil dengan Rentang
        dialog_rentang = new Dialog(ActivityOrderPerdana3.this, R.style.PopupTheme);
        dialog_rentang.setContentView(R.layout.dialog_order_perdana_rentang);
        final TextView txt_ccid_awal = dialog_rentang.findViewById(R.id.txt_ccid_awal);
        final TextView txt_ccid_akhir = dialog_rentang.findViewById(R.id.txt_ccid_akhir);
        txt_banyak_ccid = dialog_rentang.findViewById(R.id.txt_banyak_ccid);
        final TextView btn_simpan_rentang = dialog_rentang.findViewById(R.id.btn_simpan);
        final TextView btn_batal_rentang = dialog_rentang.findViewById(R.id.btn_batal);
        final Button btn_ambil_ccid = dialog_rentang.findViewById(R.id.btn_ambil_ccid);
        final RecyclerView rcy_ccid_rentang = dialog_rentang.findViewById(R.id.rcy_ccid);
        selectedCcidRentang = new ArrayList<>();
        adapterRentang = new AdapterOrderPerdanaCcidRentang(selectedCcidRentang);
        RecyclerView.LayoutManager layoutManagerRentang = new LinearLayoutManager(ActivityOrderPerdana3.this);
        rcy_ccid_rentang.setLayoutManager(layoutManagerRentang);
        rcy_ccid_rentang.setItemAnimator(new DefaultItemAnimator());
        rcy_ccid_rentang.setAdapter(adapterRentang);

        btn_ambil_ccid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ccid_awal = txt_ccid_awal.getText().toString();
                String ccid_akhir = txt_ccid_akhir.getText().toString();

                if(ccid_awal.isEmpty()){
                    txt_ccid_awal.setError("Harap diisi");
                    txt_ccid_awal.requestFocus();
                    return;
                }else{
                    txt_ccid_awal.setError(null);
                }

                if(ccid_akhir.isEmpty()){
                    txt_ccid_akhir.setError("Harap diisi");
                    txt_ccid_akhir.requestFocus();
                    return;
                }else{
                    txt_ccid_akhir.setError(null);
                }

                initCcid(2,"", ccid_awal, ccid_akhir);
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

                for(ModelCcid ccid : selectedCcidRentang){

                    boolean isExist = false;
                    for(ModelCcid selCCID : selectedCcid){

                        if(selCCID.getCcid().equals(ccid.getCcid())){
                            isExist = true;
                            break;
                        }
                    }

                    if(!isExist){
                        selectedCcid.add(ccid);
                    }
                }

                updateCcid();
                dialog_rentang.dismiss();
            }
        });

        selectedCcidRentang.clear();
        adapterRentang.notifyDataSetChanged();

        dialog_rentang.show();
    }

    private void initUI() {

        txt_nama = findViewById(R.id.txt_nama);
        txt_harga = findViewById(R.id.txt_harga);
        txt_no_ccid = findViewById(R.id.txt_no_ccid);
        txt_nama_ccid = findViewById(R.id.txt_nama_ccid);
        txt_harga_ccid = findViewById(R.id.txt_harga_ccid);
        txt_total = findViewById(R.id.txt_total);
        txt_total_harga = findViewById(R.id.txt_total_harga);
        llTanggal = (LinearLayout) findViewById(R.id.ll_tanggal);
        edtTanggal = (EditText) findViewById(R.id.edt_tanggal);

        btn_list = findViewById(R.id.btn_list);
        btn_rentang = findViewById(R.id.btn_rentang);
        btn_scan = findViewById(R.id.btn_scan);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){

            kdbrg = bundle.getString("kdbrg", "");
            namabrg = bundle.getString("namabrg", "");
            hargabrg = bundle.getString("harga", "");
            suratJalan = bundle.getString("suratjalan", "");

            txt_nama.setText(namabrg);
            txt_harga.setText(iv.ChangeToRupiahFormat(hargabrg));
        }
    }

    private void initEvent() {

        String curdate = iv.getCurrentDate(FormatItem.formatDateDisplay);
        edtTanggal.setText(curdate);

        llTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar customDate;
                customDate = Calendar.getInstance();
                final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        customDate.set(Calendar.YEAR,year);
                        customDate.set(Calendar.MONTH,month);
                        customDate.set(Calendar.DATE,date);

                        SimpleDateFormat sdFormat = new SimpleDateFormat(FormatItem.formatDateDisplay, Locale.US);
                        edtTanggal.setText(sdFormat.format(customDate.getTime()));
                    }
                };

                new DatePickerDialog(context,date,customDate.get(Calendar.YEAR),customDate.get(Calendar.MONTH),customDate.get(Calendar.DATE)).show();
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

    private void initCcid(final int flag, final String ccid, final String start, final String end){

        /*allCcid.add(new ModelCcid(
                "3289732497298423"
                , "3001"
                , "20000"
                , false));

        allCcid.add(new ModelCcid(
                "3289732497298424"
                , "3001"
                , "20000"
                , false));

        allCcid.add(new ModelCcid(
                "3289732497298425"
                , "3001"
                , "20000"
                , false));

        allCcid.add(new ModelCcid(
                "3289732497298426"
                , "3001"
                , "20000"
                , false));

        allCcid.add(new ModelCcid(
                "3289732497298427"
                , "3001"
                , "20000"
                , false));*/
        dialogBox.showDialog(true);
        JSONObject jBody = new JSONObject();

        try {

            jBody.put("ccid", ccid);
            jBody.put("ccid_awal", start);
            jBody.put("ccid_akhir", end);
            jBody.put("nobukti", suratJalan);
            jBody.put("keyword", "");
            jBody.put("start", "");
            jBody.put("count", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiVolley request = new ApiVolley(context, jBody, "POST", ServerURL.getListCCID, new ApiVolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {

                dialogBox.dismissDialog();
                String message = "Terjadi kesalahan saat memuat data, harap ulangi proses";
                switch (flag){
                    case 1:
                        allCcid.clear();
                        break;
                    case 2:
                        selectedCcidRentang.clear();
                        adapterRentang.notifyDataSetChanged();

                        break;
                    default:
                        break;
                }

                try {

                    JSONObject response = new JSONObject(result);
                    String status = response.getJSONObject("metadata").getString("status");
                    message = response.getJSONObject("metadata").getString("message");

                    if(iv.parseNullInteger(status) == 200){

                        JSONArray jsonArray = response.getJSONArray("response");

                        if(flag == 2){
                            txt_banyak_ccid.setText(iv.ChangeToCurrencyFormat(String.valueOf(jsonArray.length())));
                        }

                        for(int i = 0; i < jsonArray.length(); i++){

                            JSONObject jo = jsonArray.getJSONObject(i);
                            if(flag == 1){
                                allCcid.add(new ModelCcid(
                                        jo.getString("ccid")
                                        , jo.getString("namabrg")
                                        , jo.getString("harga")
                                        , false
                                ));
                            }else if(flag == 2){
                                selectedCcidRentang.add(new ModelCcid(
                                        jo.getString("ccid")
                                        , jo.getString("namabrg")
                                        , jo.getString("harga")
                                        , false
                                ));
                            }

                        }

                    }else{

                        DialogBox.showDialog(context, 3, message);
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                    View.OnClickListener clickListener = new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            dialogBox.dismissDialog();
                            initCcid(flag, ccid, start, end);
                        }
                    };

                    dialogBox.showDialog(clickListener, "Ulangi Proses", message);
                }

                if(flag == 1){
                    adapter.notifyDataSetChanged();
                }else if(flag == 2){
                    adapterRentang.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(String result) {

                dialogBox.dismissDialog();
                View.OnClickListener clickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialogBox.dismissDialog();
                        initCcid(flag, ccid, start, end);
                    }
                };

                dialogBox.showDialog(clickListener, "Ulangi Proses", "Terjadi kesalahan, harap ulangi proses");
            }
        });
    }

    public void updateCcid(){
        //Mengupdate tampilan informasi berdasarkan CCID yang dipilih
        //Update RecyclerView
        //System.out.println(this.selectedCcid.size());
        adapter.notifyDataSetChanged();

        //Update total
        txt_total.setText(String.valueOf(selectedCcid.size()));
        double total = 0;
        for(ModelCcid c : selectedCcid){
            total += iv.parseNullDouble(c.getHarga());
        }
        txt_total_harga.setText(iv.ChangeToCurrencyFormat(iv.doubleToString(total)));

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
                //System.out.println(result.getContents());

                //Menambahkan data CCID ke list
                selectedCcid.add(new ModelCcid(result.getContents(), "sdfsd", "3000"));
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
