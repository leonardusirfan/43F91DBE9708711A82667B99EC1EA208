package id.net.gmedia.perkasaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ActivityOrderMkios2 extends AppCompatActivity {

    List<ModelPulsa> listPulsa = new ArrayList<>();
    List<Integer> listTotal = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_mkios2);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Order Mkios");
        }

        TextView txt_nama, txt_total;
        txt_nama = findViewById(R.id.txt_nama);
        txt_total = findViewById(R.id.txt_total);

        if(getIntent().hasExtra("mkios")){
            ModelOutlet mkios = getIntent().getParcelableExtra("mkios");
            txt_nama.setText(mkios.getNama());
        }

        //Inisialisasi Recycler View Pulsa
        AdapterOrderMkios2 adapter = new AdapterOrderMkios2(listPulsa, listTotal, txt_total);
        RecyclerView rcy_pulsa = findViewById(R.id.rcy_pulsa);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rcy_pulsa.setLayoutManager(layoutManager);
        rcy_pulsa.setItemAnimator(new DefaultItemAnimator());
        rcy_pulsa.setAdapter(adapter);
        initPulsa();
    }

    private void initPulsa(){
        ModelPulsa pulsa = new ModelPulsa(5, 5600);
        listPulsa.add(pulsa);
        listTotal.add(0);

        pulsa = new ModelPulsa(10, 10600);
        listPulsa.add(pulsa);
        listTotal.add(0);

        pulsa = new ModelPulsa(20, 20050);
        listPulsa.add(pulsa);
        listTotal.add(0);

        pulsa = new ModelPulsa(25, 25100);
        listPulsa.add(pulsa);
        listTotal.add(0);

        pulsa = new ModelPulsa(50, 49000);
        listPulsa.add(pulsa);
        listTotal.add(0);

        pulsa = new ModelPulsa(100, 97100);
        listPulsa.add(pulsa);
        listTotal.add(0);

        pulsa = new ModelPulsa(1, 1);
        listPulsa.add(pulsa);
        listTotal.add(0);
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
