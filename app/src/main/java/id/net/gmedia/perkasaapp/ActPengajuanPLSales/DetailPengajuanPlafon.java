package id.net.gmedia.perkasaapp.ActPengajuanPLSales;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.maulana.custommodul.ApiVolley;
import com.maulana.custommodul.CustomView.DialogBox;
import com.maulana.custommodul.ItemValidation;
import com.maulana.custommodul.OptionItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import id.net.gmedia.perkasaapp.ActivityHome;
import id.net.gmedia.perkasaapp.R;
import id.net.gmedia.perkasaapp.Utils.ServerURL;

public class DetailPengajuanPlafon extends AppCompatActivity {

    private Context context;
    private ItemValidation iv = new ItemValidation();
    private DialogBox dialogBox;
    private TextView tvNama;
    private Spinner spJenis;
    private EditText edtKeterangan;
    private Button btnProses;
    private String nik = "";
    private EditText edtNominal;
    private String currentJmlString = "";
    public static final String flag = "PENGAJUANPLAFONSALES";
    private List<OptionItem> listJenis = new ArrayList<>();
    private ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pengajuan_plafon);

        if(getSupportActionBar() != null){
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Detail Pengajuan Plafon");
        }

        context = this;
        dialogBox = new DialogBox(context);

        initUI();
        initEvent();
    }

    private void initUI() {

        tvNama = (TextView) findViewById(R.id.tv_nama);
        edtNominal = (EditText) findViewById(R.id.edt_nominal);
        edtKeterangan = (EditText) findViewById(R.id.edt_keterangan);
        btnProses = (Button) findViewById(R.id.btn_proses);
        spJenis = (Spinner) findViewById(R.id.sp_jenis);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){

            nik = bundle.getString("nik", "");
            String nama = bundle.getString("nama", "");

            tvNama.setText(nama);

        }

        listJenis.add(new OptionItem("mkios","Mkios"));
        listJenis.add(new OptionItem("perdana","Perdana"));

        adapter = new ArrayAdapter(context, R.layout.layout_simple_list, listJenis);
        spJenis.setAdapter(adapter);
    }

    private void initEvent() {

        edtNominal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(!editable.toString().equals(currentJmlString)){

                    String cleanString = editable.toString().replaceAll("[,.]", "");
                    edtNominal.removeTextChangedListener(this);

                    String formatted = iv.ChangeToCurrencyFormat(cleanString);
                    cleanString = formatted;
                    edtNominal.setText(formatted);
                    edtNominal.setSelection(formatted.length());
                    edtNominal.addTextChangedListener(this);
                }
            }
        });

        btnProses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Validasi

                if(iv.parseNullDouble(edtNominal.getText().toString().replaceAll("[,.]", "")) <= 0){

                    edtNominal.setError("Kete");
                    edtNominal.requestFocus();
                    return;
                }

                if(edtKeterangan.getText().toString().isEmpty()){

                    edtKeterangan.setError("Keterangan harap diisi");
                    edtKeterangan.requestFocus();
                    return;
                }else{

                    edtKeterangan.setError(null);
                }

                AlertDialog dialog = new AlertDialog.Builder(context)
                        .setTitle("Konfirmasi")
                        .setMessage("Apakah anda yakin ingin menyimpan permohonan perubahan data reseller?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                saveData();
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();

            }
        });
    }

    private void saveData() {

        btnProses.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(context, R.style.AppTheme_Login_Default_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Menyimpan...");
        progressDialog.setCancelable(false);
        progressDialog.show();


        JSONObject jBody = new JSONObject();
        try {
            jBody.put("sales", nik);
            jBody.put("nominal", edtNominal.getText().toString().replaceAll("[,.]", ""));
            jBody.put("jenis", ((OptionItem) spJenis.getSelectedItem()).getValue());
            jBody.put("keterangan", edtKeterangan.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiVolley request = new ApiVolley(context, jBody, "POST", ServerURL.savePengajuanPlafonSales, new ApiVolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {

                String message = "Terjadi kesalahan saat menyimpan data, harap ulangi";
                btnProses.setEnabled(true);

                try {

                    JSONObject response = new JSONObject(result);
                    String status = response.getJSONObject("metadata").getString("status");
                    message = response.getJSONObject("metadata").getString("message");
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    if(iv.parseNullInteger(status) == 200){

                        Intent intent = new Intent(context, ActivityHome.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.putExtra("flag", flag);
                        startActivity(intent);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                }

                if(progressDialog != null && progressDialog.isShowing()) progressDialog.dismiss();
            }

            @Override
            public void onError(String result) {
                Toast.makeText(context, "Terjadi kesalahan koneksi, harap ulangi kembali nanti", Toast.LENGTH_SHORT).show();
                if(progressDialog != null && progressDialog.isShowing()) progressDialog.dismiss();
                btnProses.setEnabled(true);
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
