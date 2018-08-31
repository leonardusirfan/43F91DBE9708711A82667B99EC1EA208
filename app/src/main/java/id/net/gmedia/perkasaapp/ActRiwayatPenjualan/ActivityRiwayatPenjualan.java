package id.net.gmedia.perkasaapp.ActRiwayatPenjualan;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.maulana.custommodul.ApiVolley;
import com.maulana.custommodul.CustomItem;
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

import id.net.gmedia.perkasaapp.ActRiwayatPenjualan.Adapter.RiwayatPenjualanAdapter;
import id.net.gmedia.perkasaapp.R;
import id.net.gmedia.perkasaapp.Utils.ServerURL;

public class ActivityRiwayatPenjualan extends AppCompatActivity {

    private Context context;
    private ItemValidation iv = new ItemValidation();
    private DialogBox dialogBox;
    private int start_day, start_month, start_year, end_day, end_month, end_year;
    private TextView txt_start, txt_end;
    private ListView lvRiwayat;
    private LinearLayout btn_kalender_start, btn_kalender_end;
    private ImageView btn_confirm;
    private List<CustomItem> listRiwayat = new ArrayList<>();
    private RiwayatPenjualanAdapter adapterRiwayat;
    private EditText txt_nama;
    private String keyword = "";
    private TextView tvTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_penjualan);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Riwayat Penjualan");
        }

        context = this;
        dialogBox = new DialogBox(context);
        initUI();
        initEvent();
        initData();
    }

    private void initUI() {

        //Inisialisasi UI Spinner
        String[] arraySpinner = new String[] {
                "Gmedia Test", "Andi Kusworo"
        };
        Spinner s = findViewById(R.id.spn_sales);
        SpinnerAdapter adapter = new SpinnerAdapter(this, R.layout.item_riwayat_penjualan_spinner, arraySpinner);
        s.setAdapter(adapter);

        //Inisialisasi UI Kalender
        txt_start = findViewById(R.id.txt_start);
        txt_end = findViewById(R.id.txt_end);
        keyword = "";

        btn_confirm = findViewById(R.id.btn_confirm);
        btn_kalender_start = findViewById(R.id.btn_kalender_start);
        btn_kalender_end = findViewById(R.id.btn_kalender_end);
        lvRiwayat = (ListView) findViewById(R.id.lv_riwayat);
        txt_nama = (EditText) findViewById(R.id.txt_nama);
        tvTotal = (TextView) findViewById(R.id.tv_total);

        Calendar now = Calendar.getInstance();
        start_day = end_day = now.get(Calendar.DATE);
        start_month = end_month = now.get(Calendar.MONTH);
        start_year = end_year = now.get(Calendar.YEAR);
        txt_start.setText(iv.getCurrentDate(FormatItem.formatDateDisplay));
        txt_end.setText(iv.getCurrentDate(FormatItem.formatDateDisplay));

        listRiwayat = new ArrayList<>();
        adapterRiwayat = new RiwayatPenjualanAdapter(context, listRiwayat);
        lvRiwayat.setAdapter(adapterRiwayat);
    }

    private void initEvent() {

        btn_kalender_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar customDate;
                customDate = Calendar.getInstance();
                final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        customDate.set(Calendar.YEAR,year);
                        customDate.set(Calendar.MONTH,month);
                        customDate.set(Calendar.DATE,date);

                        SimpleDateFormat sdFormat = new SimpleDateFormat(FormatItem.formatDateDisplay, Locale.US);
                        txt_start.setText(sdFormat.format(customDate.getTime()));
                    }
                };

                new DatePickerDialog(context,date,customDate.get(Calendar.YEAR),customDate.get(Calendar.MONTH),customDate.get(Calendar.DATE)).show();
            }
        });

        btn_kalender_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar customDate;
                customDate = Calendar.getInstance();
                final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        customDate.set(Calendar.YEAR,year);
                        customDate.set(Calendar.MONTH,month);
                        customDate.set(Calendar.DATE,date);

                        SimpleDateFormat sdFormat = new SimpleDateFormat(FormatItem.formatDateDisplay, Locale.US);
                        txt_end.setText(sdFormat.format(customDate.getTime()));
                    }
                };

                new DatePickerDialog(context,date,customDate.get(Calendar.YEAR),customDate.get(Calendar.MONTH),customDate.get(Calendar.DATE)).show();
            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean valid = false;
                if(end_year == start_year){
                    if(end_month == start_month){
                        if(end_day >= start_day){
                            valid = true;
                        }
                    }
                    else if(end_month > start_month){
                        valid = true;
                    }
                }
                else if(end_year > start_year){
                    valid = true;
                }

                if(!valid){
                    Toast.makeText(ActivityRiwayatPenjualan.this, "Tanggal mulai tidak boleh melebihi tanggal akhir", Toast.LENGTH_SHORT).show();
                }
                else{
                    //Toast.makeText(ActivityRiwayatPenjualan.this, "Tanggal valid", Toast.LENGTH_SHORT).show();

                    //Tunjukkan histori penjualan
                    //Update total
                    initData();
                }
            }
        });

        txt_nama.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if(i == EditorInfo.IME_ACTION_SEARCH){

                    keyword = txt_nama.getText().toString();
                    listRiwayat.clear();
                    initData();

                    iv.hideSoftKey(context);
                    return true;
                }

                return false;
            }
        });

        txt_nama.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                keyword = editable.toString();
            }
        });
    }

    private void initData() {

        dialogBox.showDialog(true);
        JSONObject jBody = new JSONObject();

        try {
            jBody.put("datestart", iv.ChangeFormatDateString(txt_start.getText().toString(), FormatItem.formatDateDisplay, FormatItem.formatDate));
            jBody.put("dateend", iv.ChangeFormatDateString(txt_end.getText().toString(), FormatItem.formatDateDisplay, FormatItem.formatDate));
            jBody.put("keyword", keyword);
            jBody.put("start", "");
            jBody.put("count", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiVolley request = new ApiVolley(context, jBody, "POST", ServerURL.getRiwayatTransaksi, new ApiVolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {

                dialogBox.dismissDialog();
                String message = "Terjadi kesalahan saat memuat data, harap ulangi";
                double totalAll = 0;

                try {

                    JSONObject response = new JSONObject(result);
                    String status = response.getJSONObject("metadata").getString("status");
                    message = response.getJSONObject("metadata").getString("message");
                    listRiwayat.clear();

                    if(iv.parseNullInteger(status) == 200){

                        JSONArray jsonArray = response.getJSONArray("response");
                        String lastTgl = "";
                        double totalPerNama = 0;

                        for(int i = 0; i < jsonArray.length(); i++){

                            JSONObject jo = jsonArray.getJSONObject(i);

                            //header
                            if(!lastTgl.equals(jo.getString("tgl"))){

                                lastTgl = jo.getString("tgl");
                                listRiwayat.add(new CustomItem("H", iv.ChangeFormatDateString(lastTgl, FormatItem.formatDate, FormatItem.formatDateDisplay)));

                                totalPerNama = 0;
                            }

                            if(i < jsonArray.length() - 1){

                                JSONObject jo2 = jsonArray.getJSONObject(i+1);
                                if(jo2.getString("nama").equals(jo.getString("nama")) && lastTgl.equals(jo2.getString("tgl"))){

                                    listRiwayat.add(new CustomItem("I", jo.getString("nama"), jo.getString("piutang"), jo.getString("flag") + "("+jo.getString("nonota") + ")"));
                                    totalPerNama += iv.parseNullLong(jo.getString("piutang"));
                                }else{

                                    listRiwayat.add(new CustomItem("I", jo.getString("nama"), jo.getString("piutang"), jo.getString("flag") + "("+jo.getString("nonota") + ")"));
                                    totalPerNama += iv.parseNullLong(jo.getString("piutang"));
                                    listRiwayat.add(new CustomItem("F", String.valueOf(totalPerNama)));
                                    totalPerNama = 0;
                                }
                            }else{

                                listRiwayat.add(new CustomItem("I", jo.getString("nama"), jo.getString("piutang"), jo.getString("flag") + "("+jo.getString("nonota") + ")"));
                                totalPerNama += iv.parseNullLong(jo.getString("piutang"));
                                listRiwayat.add(new CustomItem("F", String.valueOf(totalPerNama)));
                                totalPerNama = 0;
                            }

                            totalAll += iv.parseNullDouble(jo.getString("piutang"));
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
                            initData();
                        }
                    };

                    dialogBox.showDialog(clickListener, "Ulangi Proses", "Terjadi kesalahan, harap ulangi proses");
                }

                tvTotal.setText(iv.ChangeToRupiahFormat(totalAll));
                adapterRiwayat.notifyDataSetChanged();
            }

            @Override
            public void onError(String result) {

                dialogBox.dismissDialog();
                View.OnClickListener clickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialogBox.dismissDialog();
                        initData();
                    }
                };

                dialogBox.showDialog(clickListener, "Ulangi Proses", "Terjadi kesalahan, harap ulangi proses");
            }
        });
    }

    class SpinnerAdapter extends ArrayAdapter<String> {
        private String[] items;
        private int res_view;

        SpinnerAdapter(Context context, int res_view, String[] objects) {
            super(context, res_view, objects);
            this.res_view = res_view;
            this.items = objects;
        }

        @Override
        public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        private View getCustomView(final int position, View convertView, ViewGroup parent) {
            final View view = LayoutInflater.from(parent.getContext()).inflate(res_view, parent, false);

            /*TextView txt_nama = view.findViewById(R.id.txt_nama);
            txt_nama.setText(items[position]);*/
            return view;
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
