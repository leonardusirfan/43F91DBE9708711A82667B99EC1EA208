package id.net.gmedia.perkasaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class ActivityVerifikasiOutlet1 extends AppCompatActivity {

    private List<ModelOutlet> listOutlet = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifikasi_outlet1);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Verifikasi Outlet");
        }

        RecyclerView rcy_outlet = findViewById(R.id.rcy_outlet);
        AdapterVerifikasiOutlet adapter = new AdapterVerifikasiOutlet(listOutlet);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rcy_outlet.setLayoutManager(layoutManager);
        rcy_outlet.setItemAnimator(new DefaultItemAnimator());
        rcy_outlet.setAdapter(adapter);

        initOutlet();
    }

    private void initOutlet(){
        ModelOutlet outlet = new ModelOutlet("MUSA CELL", "jl. KH Hasan Munadi", "81215297734", "81215297734", -7.0213463,110.4270008);
        listOutlet.add(outlet);

        outlet = new ModelOutlet("NOWO CELL", "jambu kulon", "81215297748", "81215297748", -7.0213463,110.4270008);
        listOutlet.add(outlet);

        outlet = new ModelOutlet("WILDAN CELL", "jambu kulon", "81215297751", "81215297751", -7.0213463,110.4270008);
        listOutlet.add(outlet);

        outlet = new ModelOutlet("REDBERRI CELL", "jl. duren", "81215297768", "81215297768", -7.0213463,110.4270008);
        listOutlet.add(outlet);

        outlet = new ModelOutlet("DOI CELL", "lendoh", "81215297781", "81215297781", -7.0213463,110.4270008);
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
