package id.net.gmedia.perkasaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class ActivityOrderMkios1 extends AppCompatActivity {

    private List<ModelOutlet> mkiosList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_mkios1);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Order Mkios");
        }

        RecyclerView rcy_mkios = findViewById(R.id.rcy_mkios);
        AdapterOrderMkios adapter = new AdapterOrderMkios(mkiosList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rcy_mkios.setLayoutManager(layoutManager);
        rcy_mkios.setItemAnimator(new DefaultItemAnimator());
        rcy_mkios.setAdapter(adapter);

        initMkios();
    }

    private void initMkios(){

        /*Tempat Inisialisasi Mkios Outlet*/
        ModelOutlet mkios = new ModelOutlet("MUSA CELL", "", "081215297734");
        mkiosList.add(mkios);

        mkios = new ModelOutlet("NOWO CELL","", "081215297748");
        mkiosList.add(mkios);

        mkios = new ModelOutlet("WILDAN CELL","", "081215297751");
        mkiosList.add(mkios);

        mkios = new ModelOutlet("REDBERRI CELL","", "081215297768");
        mkiosList.add(mkios);

        mkios = new ModelOutlet("DOI CELL","", "081215297781");
        mkiosList.add(mkios);

        mkios = new ModelOutlet("P-TECK CELL","", "081215297797");
        mkiosList.add(mkios);

        mkios = new ModelOutlet("FENS CARD CELL","", "081215365096");
        mkiosList.add(mkios);

        mkios = new ModelOutlet("PINK CELL","", "081215370119");
        mkiosList.add(mkios);

        mkios = new ModelOutlet("WAFFA 2","", "");
        mkiosList.add(mkios);

        mkios = new ModelOutlet("354 CELL","", "081215604903");
        mkiosList.add(mkios);
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
