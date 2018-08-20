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

    private List<OutletModel> outletList = new ArrayList<>();
    
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
        OutletModel tcash = new OutletModel("MUSA CELL", "", "081215297734");
        outletList.add(tcash);

        tcash = new OutletModel("NOWO CELL","", "081215297748");
        outletList.add(tcash);

        tcash = new OutletModel("WILDAN CELL","", "081215297751");
        outletList.add(tcash);

        tcash = new OutletModel("REDBERRI CELL","", "081215297768");
        outletList.add(tcash);

        tcash = new OutletModel("DOI CELL","", "081215297781");
        outletList.add(tcash);

        tcash = new OutletModel("P-TECK CELL","", "081215297797");
        outletList.add(tcash);

        tcash = new OutletModel("FENS CARD CELL","", "081215365096");
        outletList.add(tcash);

        tcash = new OutletModel("PINK CELL","", "081215370119");
        outletList.add(tcash);

        tcash = new OutletModel("WAFFA 2","", "");
        outletList.add(tcash);

        tcash = new OutletModel("354 CELL","", "081215604903");
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
