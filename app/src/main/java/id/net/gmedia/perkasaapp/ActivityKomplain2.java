package id.net.gmedia.perkasaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ActivityKomplain2 extends AppCompatActivity {

    EditText txt_komplain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_komplain2);

        TextView txt_nama = findViewById(R.id.txt_nama);
        txt_komplain = findViewById(R.id.txt_komplain);
        txt_nama.setText(AppSharedPreferences.getUserId(this));


        Button btn_komplain;
        btn_komplain = findViewById(R.id.btn_komplain);
        btn_komplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(txt_komplain.getText().toString());
            }
        });
    }
}
