package id.net.gmedia.perkasaapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ActivityTambahCustomer1 extends AppCompatActivity{

    private List<ModelOutlet> listOutlet = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_customer1);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Tambah Customer");
        }

        FloatingActionButton float_tambah = findViewById(R.id.float_tambah);
        float_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityTambahCustomer1.this, ActivityTambahCustomer2.class));
            }
        });

        RecyclerView rcy_customer = findViewById(R.id.rcy_customer);
        AdapterTambahCustomer adapter = new AdapterTambahCustomer(listOutlet);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);

        rcy_customer.setLayoutManager(layoutManager);
        rcy_customer.setItemAnimator(new DefaultItemAnimator());
        rcy_customer.setAdapter(adapter);

        initOutlet();
    }

    private void initOutlet(){
        ModelOutlet outlet = new ModelOutlet("MUSA CELL", "jl. KH Hasan Munadi", "81215297734", "81215297734", -7.0213463,110.4270008);
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
