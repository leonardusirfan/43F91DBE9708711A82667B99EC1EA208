package id.net.gmedia.perkasaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivityOrderTcash3 extends AppCompatActivity {

    private int harga = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_tcash3);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Order Tcash");
        }

        Button btn_proses = findViewById(R.id.btn_proses);
        final TextView txt_nama, txt_nominal, txt_harga, txt_jarak, txt_keterangan;
        txt_nama = findViewById(R.id.txt_nama);
        txt_nominal = findViewById(R.id.txt_nominal);
        txt_harga = findViewById(R.id.txt_harga);
        txt_jarak = findViewById(R.id.txt_jarak);
        txt_keterangan = findViewById(R.id.txt_keterangan);

        txt_harga.setText(R.string.rupiah_0);
        txt_jarak.setText("0 Km");
        if(getIntent().hasExtra("tcash")){
            OutletModel tcash = getIntent().getParcelableExtra("tcash");
            txt_nama.setText(tcash.getNama());
        }

        txt_nominal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().equals("")){
                    txt_harga.setText(R.string.rupiah_0);
                }
                else{
                    txt_harga.setText(RupiahFormatterUtil.getRupiah(Integer.parseInt(s.toString()) * harga));
                }
            }
        });

        btn_proses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(txt_keterangan.getText().toString());
            }
        });

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
