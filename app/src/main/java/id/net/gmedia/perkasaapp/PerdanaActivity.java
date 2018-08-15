package id.net.gmedia.perkasaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class PerdanaActivity extends AppCompatActivity {

    private List<PerdanaOutletModel> listPerdana = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perdana);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Order Perdana");
        }

        RecyclerView rcy_perdana = findViewById(R.id.rcy_perdana);
        PerdanaOutletAdapter adapter = new PerdanaOutletAdapter(listPerdana);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rcy_perdana.setLayoutManager(layoutManager);
        rcy_perdana.setItemAnimator(new DefaultItemAnimator());
        rcy_perdana.setAdapter(adapter);

        initPerdana();
    }

    private void initPerdana() {
        PerdanaOutletModel perdana = new PerdanaOutletModel("MUSA CELL", "jl. KH Hasan Munadi");
        listPerdana.add(perdana);

        perdana = new PerdanaOutletModel("NOWO CELL", "jambu kulon");
        listPerdana.add(perdana);

        perdana = new PerdanaOutletModel("WILDAN CELL", "jambu kulon");
        listPerdana.add(perdana);

        perdana = new PerdanaOutletModel("REDBERRI CELL", "jl. duren");
        listPerdana.add(perdana);

        perdana = new PerdanaOutletModel("DOI CELL", "lendoh");
        listPerdana.add(perdana);

        perdana = new PerdanaOutletModel("P-TECK CELL", "Randugunting");
        listPerdana.add(perdana);

        perdana = new PerdanaOutletModel("FENS CARD CELL", "jln dokter cipto");
        listPerdana.add(perdana);

        perdana = new PerdanaOutletModel("PINK CELL", "pasar bedono");
        listPerdana.add(perdana);

        perdana = new PerdanaOutletModel("WAFFA 2", "Komplek Padar Pringapus");
        listPerdana.add(perdana);

        perdana = new PerdanaOutletModel("354 CELL", "gedang anak");
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
