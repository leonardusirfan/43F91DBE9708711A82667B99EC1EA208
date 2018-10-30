package id.net.gmedia.perkasaapp.ActKonsinyasi;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.maulana.custommodul.CustomView.DialogBox;
import com.maulana.custommodul.ItemValidation;
import com.maulana.custommodul.SessionManager;

import id.net.gmedia.perkasaapp.R;

public class ActKonsinyasi extends AppCompatActivity {

    private Context context;
    private DialogBox dialogBox;
    private ItemValidation iv = new ItemValidation();
    private SessionManager session;
    private RelativeLayout rlMutasi, rlRekonsinyasi, rlInfoBarang, rlRetur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_konsinyasi);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Konsinyasi");
        }

        context = this;
        dialogBox = new DialogBox(context);
        session = new SessionManager(context);

        initUI();
        initEvent();
    }

    private void initUI() {

        rlMutasi = (RelativeLayout) findViewById(R.id.rl_mutasi);
        rlRekonsinyasi = (RelativeLayout) findViewById(R.id.rl_rekonsinyasi);
        rlInfoBarang = (RelativeLayout) findViewById(R.id.rl_info_barang);
        rlRetur = (RelativeLayout) findViewById(R.id.rl_retur);
    }

    private void initEvent() {

        rlMutasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(context, MutasiKonsinyasi.class));
            }
        });

        rlRekonsinyasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        rlInfoBarang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        rlRetur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
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
