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

    private List<PerdanaModel> listPerdana = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perdana);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Order Perdana");
        }

        RecyclerView rcy_perdana = findViewById(R.id.rcy_perdana);
        PerdanaAdapter adapter = new PerdanaAdapter(listPerdana);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rcy_perdana.setLayoutManager(layoutManager);
        rcy_perdana.setItemAnimator(new DefaultItemAnimator());
        rcy_perdana.setAdapter(adapter);

        initPerdana();
    }

    private void initPerdana() {
        PerdanaModel perdana = new PerdanaModel("2 Maret Cell - Salam", "Jagang Lor RT 1 RW 2");
        listPerdana.add(perdana);

        perdana = new PerdanaModel("205 Gadget Cell - Kaliangkrik", "Kaliangkrik");
        listPerdana.add(perdana);

        perdana = new PerdanaModel("24 Celluler Cell - Kajoran", "Tunggangan");
        listPerdana.add(perdana);

        perdana = new PerdanaModel("3 in 1 Cell - Mungkid", "");
        listPerdana.add(perdana);

        perdana = new PerdanaModel("4S Cell - Grabag", "Paingan Grabag");
        listPerdana.add(perdana);

        perdana = new PerdanaModel("789 Cell", "Kwayuhan Gelangan Magelang Utara");
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
