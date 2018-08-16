package id.net.gmedia.perkasaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class ActivityPiutang extends AppCompatActivity {

    private List<OutletModel> listOutlet = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piutang);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Piutang");
        }

        RecyclerView rcy_piutang = findViewById(R.id.rcy_piutang);

        AdapterPiutang adapter = new AdapterPiutang(listOutlet);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rcy_piutang.setLayoutManager(layoutManager);
        rcy_piutang.setItemAnimator(new DefaultItemAnimator());
        rcy_piutang.setAdapter(adapter);

        initPiutang();
    }

    private void initPiutang(){
        OutletModel outlet = new OutletModel("MUSA CELL", "jl. KH Hasan Munadi", "");
        outlet.setPiutang(5000000);
        listOutlet.add(outlet);

        outlet = new OutletModel("NOWO CELL", "jambu kulon", "");
        outlet.setPiutang(1200000);
        listOutlet.add(outlet);

        outlet = new OutletModel("354 CELL", "gedang anak", "");
        outlet.setPiutang(700000);
        listOutlet.add(outlet);
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
