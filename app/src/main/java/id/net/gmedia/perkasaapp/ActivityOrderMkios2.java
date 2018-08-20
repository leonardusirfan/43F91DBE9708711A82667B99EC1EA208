package id.net.gmedia.perkasaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.TextView;

public class ActivityOrderMkios2 extends AppCompatActivity {

    private int total5 = 0, total10 = 0, total20 = 0, total25 = 0, total50 = 0, total100 = 0, totalbulk = 0;
    private TextView txt_total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_mkios2);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Order Mkios");
        }

        TextView txt_nama = findViewById(R.id.txt_nama);
        txt_total = findViewById(R.id.txt_total);
        final TextView txt_jumlah_s5,txt_jumlah_s10, txt_jumlah_s20, txt_jumlah_s25, txt_jumlah_s50, txt_jumlah_s100, txt_jumlah_bulk;
        final TextView txt_total_s5,txt_total_s10, txt_total_s20, txt_total_s25, txt_total_s50, txt_total_s100, txt_total_bulk;

        txt_jumlah_s5 = findViewById(R.id.txt_jumlah_s5);
        txt_jumlah_s10 = findViewById(R.id.txt_jumlah_s10);
        txt_jumlah_s20 = findViewById(R.id.txt_jumlah_s20);
        txt_jumlah_s25 = findViewById(R.id.txt_jumlah_s25);
        txt_jumlah_s50 = findViewById(R.id.txt_jumlah_s50);
        txt_jumlah_s100 = findViewById(R.id.txt_jumlah_s100);
        txt_jumlah_bulk = findViewById(R.id.txt_jumlah_bulk);

        txt_total_s5 = findViewById(R.id.txt_total_s5);
        txt_total_s10 = findViewById(R.id.txt_total_s10);
        txt_total_s20 = findViewById(R.id.txt_total_s20);
        txt_total_s25 = findViewById(R.id.txt_total_s25);
        txt_total_s50 = findViewById(R.id.txt_total_s50);
        txt_total_s100 = findViewById(R.id.txt_total_s100);
        txt_total_bulk = findViewById(R.id.txt_total_bulk);

        txt_jumlah_s5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().equals("")){
                    total5 = Integer.parseInt(s.toString()) * 5600;
                    txt_total_s5.setText(RupiahFormatterUtil.getRupiah(total5));
                    updateTotal();
                }
                else{
                    total5 = 0;
                    txt_total_s5.setText(R.string.rupiah_0);
                    updateTotal();
                }
            }
        });

        txt_jumlah_s10.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().equals("")){
                    total10 = Integer.parseInt(s.toString()) * 10600;
                    txt_total_s10.setText(RupiahFormatterUtil.getRupiah(total10));
                    updateTotal();
                }
                else{
                    total10 = 0;
                    txt_total_s10.setText(R.string.rupiah_0);
                    updateTotal();
                }
            }
        });

        txt_jumlah_s20.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().equals("")){
                    total20 = Integer.parseInt(s.toString()) * 20050;
                    txt_total_s20.setText(RupiahFormatterUtil.getRupiah(total20));
                    updateTotal();
                }
                else{
                    total20 = 0;
                    txt_total_s20.setText(R.string.rupiah_0);
                    updateTotal();
                }
            }
        });

        txt_jumlah_s25.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().equals("")){
                    total25 = Integer.parseInt(s.toString()) * 25100;
                    txt_total_s25.setText(RupiahFormatterUtil.getRupiah(total25));
                    updateTotal();
                }
                else{
                    total25 = 0;
                    txt_total_s25.setText(R.string.rupiah_0);
                    updateTotal();
                }
            }
        });

        txt_jumlah_s50.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().equals("")){
                    total50 = Integer.parseInt(s.toString()) * 49000;
                    txt_total_s50.setText(RupiahFormatterUtil.getRupiah(total50));
                    updateTotal();
                }
                else{
                    total50 = 0;
                    txt_total_s50.setText(R.string.rupiah_0);
                    updateTotal();
                }
            }
        });

        txt_jumlah_s100.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().equals("")){
                    total100 = Integer.parseInt(s.toString()) * 97100;
                    txt_total_s100.setText(RupiahFormatterUtil.getRupiah(total100));
                    updateTotal();
                }
                else{
                    total100 = 0;
                    txt_total_s100.setText(R.string.rupiah_0);
                    updateTotal();
                }
            }
        });

        txt_jumlah_bulk.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().equals("")){
                    totalbulk = Integer.parseInt(s.toString());
                    txt_total_bulk.setText(RupiahFormatterUtil.getRupiah(totalbulk));
                    updateTotal();
                }
                else{
                    totalbulk = 0;
                    txt_total_bulk.setText(R.string.rupiah_0);
                    updateTotal();
                }
            }
        });


        if(getIntent().hasExtra("mkios")){
            OutletModel mkios = getIntent().getParcelableExtra("mkios");
            txt_nama.setText(mkios.getNama());
        }
    }

    private void updateTotal(){
        txt_total.setText(RupiahFormatterUtil.getRupiah(total5 + total10 + total20 + total25 + total50 + total100 + totalbulk));
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
