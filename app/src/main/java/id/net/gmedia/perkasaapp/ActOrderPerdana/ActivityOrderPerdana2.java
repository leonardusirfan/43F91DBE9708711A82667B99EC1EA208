package id.net.gmedia.perkasaapp.ActOrderPerdana;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import id.net.gmedia.perkasaapp.ActOrderPerdana.Adapter.AdapterOrderPerdanaBarang;
import id.net.gmedia.perkasaapp.ModelOutlet;
import id.net.gmedia.perkasaapp.ModelPerdana;
import id.net.gmedia.perkasaapp.R;

public class ActivityOrderPerdana2 extends AppCompatActivity {

    private List<ModelPerdana> listPerdana = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_perdana2);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Order Perdana");
        }

        TextView txt_nama = findViewById(R.id.txt_nama);
        ModelOutlet perdana;
        if(getIntent().hasExtra("perdana")){
            perdana = getIntent().getParcelableExtra("perdana");
            txt_nama.setText(perdana.getNama());
        }

        AdapterOrderPerdanaBarang adapter = new AdapterOrderPerdanaBarang(listPerdana);

        RecyclerView rcy_barang = findViewById(R.id.rcy_barang);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rcy_barang.setLayoutManager(layoutManager);
        rcy_barang.setAdapter(adapter);

        initPerdana();
    }

    private void initPerdana(){
        //Tempat inisialisasi Perdana
        ModelPerdana perdana = new ModelPerdana("128k 4G LTE 2FF/3FF/4FF USIM MIGRATION (S100)", "SJ/MG/1806/0350", 3000, 1520);
        listPerdana.add(perdana);
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
