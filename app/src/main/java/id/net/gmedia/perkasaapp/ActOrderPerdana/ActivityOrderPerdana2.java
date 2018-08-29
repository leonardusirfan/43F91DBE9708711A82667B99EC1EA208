package id.net.gmedia.perkasaapp.ActOrderPerdana;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.maulana.custommodul.ItemValidation;
import com.maulana.custommodul.SessionManager;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.List;

import id.net.gmedia.perkasaapp.ActOrderPerdana.Adapter.AdapterOrderPerdanaBarang;
import id.net.gmedia.perkasaapp.ModelOutlet;
import id.net.gmedia.perkasaapp.ModelPerdana;
import id.net.gmedia.perkasaapp.R;

public class ActivityOrderPerdana2 extends AppCompatActivity {

    private List<ModelPerdana> listPerdana = new ArrayList<>();
    private TextView txt_nama;
    private Context context;
    private SessionManager session;
    private ItemValidation iv = new ItemValidation();
    private AdapterOrderPerdanaBarang adapter;
    private SlidingUpPanelLayout suplContainer;
    private ImageView ivIcon;
    private String nama = "", kdcus = "";
    private RecyclerView rcy_barang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_perdana2);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Order Perdana");
        }

        initUI();
        initEvent();
        initPerdana();
    }

    private void initUI() {

        txt_nama = (TextView) findViewById(R.id.txt_nama);
        ivIcon = (ImageView) findViewById(R.id.iv_icon);
        suplContainer = (SlidingUpPanelLayout) findViewById(R.id.supl_container);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){

            kdcus = bundle.getString("kdcus", "");
            nama = bundle.getString("nama", "");

            txt_nama.setText(nama);
        }

        adapter = new AdapterOrderPerdanaBarang(listPerdana);

        rcy_barang = findViewById(R.id.rcy_barang);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        rcy_barang.setLayoutManager(layoutManager);
        rcy_barang.setAdapter(adapter);
    }

    private void initEvent() {

        suplContainer.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {

                if(newState == SlidingUpPanelLayout.PanelState.COLLAPSED){

                    ivIcon.setImageResource(R.mipmap.ic_down);
                }else{

                    ivIcon.setImageResource(R.mipmap.ic_up);
                }
            }
        });
    }

    private void initPerdana(){
        //Tempat inisialisasi Perdana
        ModelPerdana perdana = new ModelPerdana("128k 4G LTE 2FF/3FF/4FF USIM MIGRATION (S100)", "SJ/MG/1806/0350", 3000, 1520);
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
