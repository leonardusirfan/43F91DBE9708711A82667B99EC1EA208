package id.net.gmedia.perkasaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MkiosActivity extends AppCompatActivity {

    private List<MkiosModel> mkiosList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mkios);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Order Mkios");
        }

        RecyclerView rcy_mkios = findViewById(R.id.rcy_mkios);
        MkiosAdapter adapter = new MkiosAdapter(mkiosList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rcy_mkios.setLayoutManager(layoutManager);
        rcy_mkios.setItemAnimator(new DefaultItemAnimator());
        rcy_mkios.setAdapter(adapter);

        initMkios();
    }

    private void initMkios(){
        MkiosModel mkios = new MkiosModel("MUSA CELL", "081215297734", "");
        mkiosList.add(mkios);

        mkios = new MkiosModel("NOWO CELL", "081215297748", "");
        mkiosList.add(mkios);

        mkios = new MkiosModel("WILDAN CELL", "081215297751", "");
        mkiosList.add(mkios);

        mkios = new MkiosModel("REDBERRI CELL", "081215297768", "");
        mkiosList.add(mkios);

        mkios = new MkiosModel("DOI CELL", "081215297781", "");
        mkiosList.add(mkios);

        mkios = new MkiosModel("P-TECK CELL", "081215297797", "");
        mkiosList.add(mkios);

        mkios = new MkiosModel("FENS CARD CELL", "081215365096", "");
        mkiosList.add(mkios);

        mkios = new MkiosModel("PINK CELL", "081215370119", "");
        mkiosList.add(mkios);

        mkios = new MkiosModel("WAFFA 2", "", "");
        mkiosList.add(mkios);

        mkios = new MkiosModel("354 CELL", "081215604903", "");
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
