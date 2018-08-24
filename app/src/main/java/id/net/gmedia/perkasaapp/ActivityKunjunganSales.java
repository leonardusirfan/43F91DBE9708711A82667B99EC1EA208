package id.net.gmedia.perkasaapp;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class ActivityKunjunganSales extends AppCompatActivity {

    private List<ModelSales> listSales = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kunjungan_sales);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Kunjungan Sales");
        }

        RecyclerView rcy_kunjungan_sales = findViewById(R.id.rcy_kunjungan_sales);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        AdapterKunjunganSales adapter = new AdapterKunjunganSales(listSales);
        rcy_kunjungan_sales.setLayoutManager(layoutManager);
        rcy_kunjungan_sales.setItemAnimator(new DefaultItemAnimator());
        rcy_kunjungan_sales.setAdapter(adapter);

        initSales();
    }

    private void initSales(){
        ModelSales sales = new ModelSales("Andi Kusworo", 20);
        listSales.add(sales);
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
