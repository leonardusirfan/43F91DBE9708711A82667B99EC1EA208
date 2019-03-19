package id.net.gmedia.perkasaapp.ActPencapaianSales;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.webkit.WebView;

import com.maulana.custommodul.SessionManager;

import id.net.gmedia.perkasaapp.R;

public class DetailPencapaianSales extends AppCompatActivity {

    private Context context;
    private SessionManager session;
    private WebView wvPencapaian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pencapaian_sales);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            getSupportActionBar().setTitle("Pencapaian Sales");
        }

        context = this;
        session = new SessionManager(context);
        initUI();
    }

    private void initUI() {

        wvPencapaian = (WebView) findViewById(R.id.wv_pencapaian);
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
