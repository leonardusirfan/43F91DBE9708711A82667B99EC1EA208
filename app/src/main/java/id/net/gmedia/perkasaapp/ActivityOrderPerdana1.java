package id.net.gmedia.perkasaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class ActivityOrderPerdana1 extends AppCompatActivity {

    private List<ModelOutlet> listPerdana = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_perdana1);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Order Perdana");
        }

        RecyclerView rcy_perdana = findViewById(R.id.rcy_perdana);
        AdapterOrderPerdana adapter = new AdapterOrderPerdana(listPerdana);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rcy_perdana.setLayoutManager(layoutManager);
        rcy_perdana.setItemAnimator(new DefaultItemAnimator());
        rcy_perdana.setAdapter(adapter);

        initPerdana();
    }

    private void initPerdana() {
        /*Tempat Inisialisasi Outlet*/
        ModelOutlet perdana = new ModelOutlet("MUSA CELL", "jl. KH Hasan Munadi", "");
        listPerdana.add(perdana);

        perdana = new ModelOutlet("NOWO CELL", "jambu kulon", "");
        listPerdana.add(perdana);

        perdana = new ModelOutlet("WILDAN CELL", "jambu kulon", "");
        listPerdana.add(perdana);

        perdana = new ModelOutlet("REDBERRI CELL", "jl. duren", "");
        listPerdana.add(perdana);

        perdana = new ModelOutlet("DOI CELL", "lendoh", "");
        listPerdana.add(perdana);

        perdana = new ModelOutlet("P-TECK CELL", "Randugunting", "");
        listPerdana.add(perdana);

        perdana = new ModelOutlet("FENS CARD CELL", "jln dokter cipto", "");
        listPerdana.add(perdana);

        perdana = new ModelOutlet("PINK CELL", "pasar bedono", "");
        listPerdana.add(perdana);

        perdana = new ModelOutlet("WAFFA 2", "Komplek Padar Pringapus", "");
        listPerdana.add(perdana);

        perdana = new ModelOutlet("354 CELL", "gedang anak", "");
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
