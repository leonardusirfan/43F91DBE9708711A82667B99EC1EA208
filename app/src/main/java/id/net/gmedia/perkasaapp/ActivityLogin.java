package id.net.gmedia.perkasaapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.maulana.custommodul.ApiVolley;
import com.maulana.custommodul.FormatItem;
import com.maulana.custommodul.ItemValidation;
import com.maulana.custommodul.RuntimePermissionsActivity;
import com.maulana.custommodul.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import id.net.gmedia.perkasaapp.NotificationUtils.InitFirebaseSetting;
import id.net.gmedia.perkasaapp.Utils.ServerURL;

public class ActivityLogin extends RuntimePermissionsActivity {

    private Context context;
    private SessionManager session;
    private ItemValidation iv = new ItemValidation();
    private EditText txt_username, txt_password;
    private Button btn_login;
    private static final int REQUEST_PERMISSIONS = 20;
    private String refreshToken = "";
    private String imei1 = "", imei2 = "";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        context = this;
        session = new SessionManager(this);

        InitFirebaseSetting.getFirebaseSetting(context);

        refreshToken = FirebaseInstanceId.getInstance().getToken();

        initPermission();
        initUI();
        initEvent();
        initData();
    }

    private void initData() {

        if(session.isLoggedIn()){

            //redirectToLogin();
            txt_username.setText(session.getUsername());
            txt_password.setText(iv.decodeBase64(session.getPassword()));
            saveData();
        }
    }

    private void initPermission() {

        if (
                ContextCompat.checkSelfPermission(ActivityLogin.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(ActivityLogin.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(ActivityLogin.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(ActivityLogin.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(ActivityLogin.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(ActivityLogin.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(ActivityLogin.this, Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(ActivityLogin.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(ActivityLogin.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                ) {

            ActivityLogin.super.requestAppPermissions(new
                            String[]{android.Manifest.permission.ACCESS_FINE_LOCATION
                    , Manifest.permission.ACCESS_COARSE_LOCATION
                    , Manifest.permission.READ_PHONE_STATE
                    , Manifest.permission.WRITE_EXTERNAL_STORAGE
                    , Manifest.permission.READ_EXTERNAL_STORAGE
                    , Manifest.permission.CALL_PHONE
                    , Manifest.permission.RECEIVE_SMS
                    , Manifest.permission.READ_SMS
                    , Manifest.permission.CAMERA
                    }, R.string
                            .runtime_permissions_txt
                    , REQUEST_PERMISSIONS);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode) {

    }

    private void initUI() {

        btn_login = findViewById(R.id.btn_login);
        txt_username = findViewById(R.id.txt_username);
        txt_password = findViewById(R.id.txt_password);
    }

    private void initEvent() {

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(txt_username.getText().toString().isEmpty()){

                    txt_username.setError("Username tidak boleh kosong");
                    txt_username.requestFocus();
                    return;
                }

                if(txt_password.getText().toString().isEmpty()){

                    txt_password.setError("Password tidak boleh kosong");
                    txt_password.requestFocus();
                    return;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        saveData();
                    }
                });


            }
        });
    }

    private void saveData() {

        btn_login.setEnabled(false);
        progressDialog = new ProgressDialog(context, R.style.AppTheme_Login_Default_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ArrayList<String> imeiList = iv.getIMEI(context);
        if(imeiList.size() > 1){ // dual sim
            imei1 = imeiList.get(0);
            imei2 = imeiList.get(1);
        }else if(imeiList.size() == 1){ // single sim
            imei1 = imeiList.get(0);
        }

        PackageInfo pInfo = null;
        String version = "";

        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        version = pInfo.versionName;

        String deviceName = android.os.Build.MODEL;
        String deviceMan = android.os.Build.MANUFACTURER;

        String curdate = iv.getCurrentDate(FormatItem.formatTimestamp);
        final String sessions = iv.encodeMD5(curdate);
        final String expiration = iv.sumDate(curdate, 7,FormatItem.formatTimestamp);

        JSONObject jBody = new JSONObject();
        try {
            jBody.put("username", txt_username.getText().toString());
            jBody.put("password", txt_password.getText().toString());
            jBody.put("fcm_id", refreshToken);
            jBody.put("imei1", imei1);
            jBody.put("imei2", imei2);
            jBody.put("phone_model", deviceMan +" "+ deviceName);
            jBody.put("version", version);
            jBody.put("session", sessions);
            jBody.put("expiration", expiration);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiVolley request = new ApiVolley(context, jBody, "POST", ServerURL.auth, new ApiVolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {

                String message = "";
                btn_login.setEnabled(true);
                if(progressDialog != null && progressDialog.isShowing()) progressDialog.dismiss();

                try {

                    JSONObject response = new JSONObject(result);
                    String status = response.getJSONObject("metadata").getString("status");
                    message = response.getJSONObject("metadata").getString("message");
                    if(iv.parseNullInteger(status) == 200){

                        String nikGa = response.getJSONObject("response").getString("nik_ga");
                        String nikMkios = response.getJSONObject("response").getString("nik_mkios");
                        String nama = response.getJSONObject("response").getString("nama");
                        String username = response.getJSONObject("response").getString("username");
                        String flagSuperuser = response.getJSONObject("response").getString("flag");
                        String nikHr = response.getJSONObject("response").getString("nik_hr");
                        String jabatan = response.getJSONObject("response").getString("posisi");
                        session.createLoginSession(username,
                                nikGa,
                                nikMkios,
                                nama,
                                iv.encodeBase64(txt_password.getText().toString()),
                                sessions,
                                expiration,
                                flagSuperuser,
                                nikHr,
                                jabatan);

                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

                        redirectToLogin();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onError(String result) {
                Snackbar.make(findViewById(android.R.id.content), result, Snackbar.LENGTH_LONG).show();
                if(progressDialog != null && progressDialog.isShowing()) progressDialog.dismiss();
                btn_login.setEnabled(true);
            }
        });
    }

    private void redirectToLogin() {

        Intent intent = new Intent(context, ActivityHome.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if ( progressDialog!=null && progressDialog.isShowing() ){
            progressDialog.cancel();
        }
    }
}
