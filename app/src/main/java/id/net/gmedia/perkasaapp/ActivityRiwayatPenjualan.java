package id.net.gmedia.perkasaapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

public class ActivityRiwayatPenjualan extends AppCompatActivity {

    DatePickerDialog.OnDateSetListener start_dateListener,end_dateListener;
    int start_day, start_month, start_year, end_day, end_month, end_year;
    TextView txt_start, txt_end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_penjualan);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Riwayat Penjualan");
        }

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

        LinearLayout btn_kalender_start, btn_kalender_end;
        ImageView btn_confirm = findViewById(R.id.btn_confirm);
        btn_kalender_start = findViewById(R.id.btn_kalender_start);
        btn_kalender_end = findViewById(R.id.btn_kalender_end);

        Calendar now = Calendar.getInstance();
        start_day = end_day = now.get(Calendar.DATE);
        start_month = end_month = now.get(Calendar.MONTH);
        start_year = end_year = now.get(Calendar.YEAR);
        txt_start.setText(String.format(Locale.getDefault(), "%d/%d/%d", start_day, start_month, start_year));
        txt_end.setText(String.format(Locale.getDefault(), "%d/%d/%d", end_day, end_month, end_year));

        start_dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                start_day = dayOfMonth;
                start_month = month;
                start_year = year;
                txt_start.setText(String.format(Locale.getDefault(), "%d/%d/%d", start_day, start_month, start_year));
            }
        };

        end_dateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                end_day = dayOfMonth;
                end_month = month;
                end_year = year;
                txt_end.setText(String.format(Locale.getDefault(), "%d/%d/%d", end_day, end_month, end_year));
            }
        };

        btn_kalender_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ActivityRiwayatPenjualan.this, start_dateListener, start_year, start_month, start_day).show();
                txt_start.setText(String.format(Locale.getDefault(), "%d/%d/%d", start_day, start_month, start_year));
            }
        });

        btn_kalender_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ActivityRiwayatPenjualan.this, end_dateListener, end_year, end_month, end_day).show();
                txt_end.setText(String.format(Locale.getDefault(), "%d/%d/%d", end_day, end_month, end_year));
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
                    Toast.makeText(ActivityRiwayatPenjualan.this, "Tanggal valid", Toast.LENGTH_SHORT).show();
                    //Tunjukkan histori penjualan
                    //Update total
                }
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

            TextView txt_nama = view.findViewById(R.id.txt_nama);
            txt_nama.setText(items[position]);
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
