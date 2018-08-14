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
        MkiosModel mkios = new MkiosModel("Toko Yumna Jaya Cell", "08121151777716", "");
        mkiosList.add(mkios);

        mkios = new MkiosModel("Erlangga Cell", "08121151777716", "");
        mkiosList.add(mkios);

        mkios = new MkiosModel("Cinta Cell Borobudur", "08121151777716", "");
        mkiosList.add(mkios);

        mkios = new MkiosModel("Yohanes Darmasasi - Bank TCAS", "08121151777716", "");
        mkiosList.add(mkios);

        mkios = new MkiosModel("Novi Cell", "08121151777716", "");
        mkiosList.add(mkios);

        mkios = new MkiosModel("Kirana Phone", "08121151777716", "");
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
