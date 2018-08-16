package id.net.gmedia.perkasaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ActivityKomplain2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_komplain2);

        TextView txt_nama = findViewById(R.id.txt_nama);
        txt_nama.setText(AppSharedPreferences.getUserId(this));
    }
}
