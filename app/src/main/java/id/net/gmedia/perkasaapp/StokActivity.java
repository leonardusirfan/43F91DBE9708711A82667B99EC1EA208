package id.net.gmedia.perkasaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class StokActivity extends AppCompatActivity {

    private List<PerdanaModel> listPerdana = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stok);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Stok Perdana");
        }

        RecyclerView rcy_mkios = findViewById(R.id.rcy_stok);
        PerdanaAdapter adapter = new PerdanaAdapter(listPerdana);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rcy_mkios.setLayoutManager(layoutManager);
        rcy_mkios.setItemAnimator(new DefaultItemAnimator());
        rcy_mkios.setAdapter(adapter);

        initPerdana();
    }

    private void initPerdana(){
        PerdanaModel perdana = new PerdanaModel("128k 4G LTE 2FF/3FF USIM MIGRATION (S097)", 4200, new TransaksiModel("PD0001", 20), new TransaksiModel("PD0002", 22));
        listPerdana.add(perdana);

        perdana = new PerdanaModel("SPV 32K SIMPATI 10000 NEW (S087)", 2300, new TransaksiModel("PD0002", 10), new TransaksiModel("PD0005", 20));
        listPerdana.add(perdana);

        perdana = new PerdanaModel("SIMPATI PREMIUM 50K (S087)", 1220, new TransaksiModel("PD0005", 20), new TransaksiModel("PD0007", 22));
        listPerdana.add(perdana);

        perdana = new PerdanaModel("SIMPATI PREMIUM 50K NEW (S089)", 1750, new TransaksiModel("PD0003", 55), new TransaksiModel("PD0002", 50));
        listPerdana.add(perdana);

        perdana = new PerdanaModel("PERDANA KARTU AS 5000 (A067)", 2120, new TransaksiModel("PD0002", 40), new TransaksiModel("PD0004", 45));
        listPerdana.add(perdana);

        perdana = new PerdanaModel("PERDANA KARTU AS PANTURA 5000 (A082)", 1010, new TransaksiModel("PD0006", 10), new TransaksiModel("PD0009", 25));
        listPerdana.add(perdana);

        perdana = new PerdanaModel("PERDANA KARTU AS 5000 NEW (A069)", 2450, new TransaksiModel("PD0010", 25), new TransaksiModel("PD0211", 10));
        listPerdana.add(perdana);

        perdana = new PerdanaModel("LOOP 2GB 1 BLN", 1520, new TransaksiModel("PD0011", 70), new TransaksiModel("PD0012", 40));
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
