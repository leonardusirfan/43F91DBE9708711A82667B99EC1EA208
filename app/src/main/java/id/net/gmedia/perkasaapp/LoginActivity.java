package id.net.gmedia.perkasaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText txt_username, txt_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txt_username = findViewById(R.id.txt_username);
        txt_password = findViewById(R.id.txt_password);

        Button btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = txt_username.getText().toString();
                String password = txt_password.getText().toString();

                if(username.equals("") || password.equals("")){
                    Toast.makeText(LoginActivity.this, "Username atau Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }
                else{
                    //Cek username dan password

                    //Jika valid
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    AppSharedPreferences.Login(LoginActivity.this, username);
                    finish();
                }
            }
        });
    }
}
