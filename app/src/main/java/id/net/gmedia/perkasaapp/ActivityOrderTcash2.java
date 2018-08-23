package id.net.gmedia.perkasaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class ActivityOrderTcash2 extends AppCompatActivity {

    private List<ModelOutlet> outletList = new ArrayList<>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_tcash2);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Order Tcash");
        }

        RecyclerView rcy_reseller = findViewById(R.id.rcy_reseller);
        AdapterOrderTcash adapter = new AdapterOrderTcash(outletList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rcy_reseller.setLayoutManager(layoutManager);
        rcy_reseller.setItemAnimator(new DefaultItemAnimator());
        rcy_reseller.setAdapter(adapter);
        
        initOutlet();
    }
    
    private void initOutlet(){
        ModelOutlet tcash = new ModelOutlet("MUSA CELL", "", "081215297734");
        outletList.add(tcash);

        tcash = new ModelOutlet("NOWO CELL","", "081215297748");
        outletList.add(tcash);

        tcash = new ModelOutlet("WILDAN CELL","", "081215297751");
        outletList.add(tcash);

        tcash = new ModelOutlet("REDBERRI CELL","", "081215297768");
        outletList.add(tcash);

        tcash = new ModelOutlet("DOI CELL","", "081215297781");
        outletList.add(tcash);

        tcash = new ModelOutlet("P-TECK CELL","", "081215297797");
        outletList.add(tcash);

        tcash = new ModelOutlet("FENS CARD CELL","", "081215365096");
        outletList.add(tcash);

        tcash = new ModelOutlet("PINK CELL","", "081215370119");
        outletList.add(tcash);

        tcash = new ModelOutlet("WAFFA 2","", "");
        outletList.add(tcash);

        tcash = new ModelOutlet("354 CELL","", "081215604903");
        outletList.add(tcash);
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
