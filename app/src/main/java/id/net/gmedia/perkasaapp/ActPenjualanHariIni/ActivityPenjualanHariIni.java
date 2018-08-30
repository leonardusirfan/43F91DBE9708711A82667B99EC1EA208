package id.net.gmedia.perkasaapp.ActPenjualanHariIni;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

import com.maulana.custommodul.CustomView.DialogBox;
import com.maulana.custommodul.ItemValidation;
import com.maulana.custommodul.SessionManager;

import id.net.gmedia.perkasaapp.R;

public class ActivityPenjualanHariIni extends AppCompatActivity {

    private Context context;
    private ItemValidation iv = new ItemValidation();
    private DialogBox dialogBox;
    private ListView lvRiwayat;
    private EditText edtSearch;
    private String keyword = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penjualan_hari_ini);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Penjualan Hari Ini");
        }

        context = this;
        dialogBox = new DialogBox(context);

        initUI();
    }

    private void initUI() {

        lvRiwayat = (ListView) findViewById(R.id.lv_riwayat);
        edtSearch = (EditText) findViewById(R.id.edt_search);
        keyword  = "";


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
