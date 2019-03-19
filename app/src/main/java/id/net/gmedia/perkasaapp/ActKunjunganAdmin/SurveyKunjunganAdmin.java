package id.net.gmedia.perkasaapp.ActKunjunganAdmin;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowManager;
import android.webkit.WebView;

import com.maulana.custommodul.SessionManager;

import id.net.gmedia.perkasaapp.R;

public class SurveyKunjunganAdmin extends AppCompatActivity {

    private Context context;
    private SessionManager session;
    private WebView wvSurvey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_kunjungan_admin);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            getSupportActionBar().setTitle("Survey Kunjungan Admin");
        }

        context = this;
        session = new SessionManager(context);
        initUI();
    }

    private void initUI() {

        wvSurvey = (WebView) findViewById(R.id.wv_survey);
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
