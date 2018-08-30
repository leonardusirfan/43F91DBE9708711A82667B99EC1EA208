package id.net.gmedia.perkasaapp.ActPenjualanHariIni;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.maulana.custommodul.ApiVolley;
import com.maulana.custommodul.CustomItem;
import com.maulana.custommodul.CustomView.DialogBox;
import com.maulana.custommodul.ItemValidation;
import com.maulana.custommodul.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import id.net.gmedia.perkasaapp.ActPenjualanHariIni.Adapter.PenjualanHariIniAdapter;
import id.net.gmedia.perkasaapp.R;
import id.net.gmedia.perkasaapp.Utils.ServerURL;

public class ActivityPenjualanHariIni extends AppCompatActivity {

    private Context context;
    private ItemValidation iv = new ItemValidation();
    private DialogBox dialogBox;
    private ListView lvRiwayat;
    private EditText edtSearch;
    private String keyword = "";
    private List<CustomItem> listTransaksi = new ArrayList<>();
    private PenjualanHariIniAdapter adapter;
    private TextView tvTotal;

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
        initEvent();
        initData();
    }

    private void initUI() {

        lvRiwayat = (ListView) findViewById(R.id.lv_riwayat);
        edtSearch = (EditText) findViewById(R.id.edt_search);
        tvTotal = (TextView) findViewById(R.id.tv_total);
        keyword  = "";
        listTransaksi = new ArrayList<>();
        adapter = new PenjualanHariIniAdapter(context, listTransaksi);
        lvRiwayat.setAdapter(adapter);
    }

    private void initEvent() {

        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if(i == EditorInfo.IME_ACTION_SEARCH){

                    keyword = edtSearch.getText().toString();
                    listTransaksi.clear();
                    initData();

                    iv.hideSoftKey(context);
                    return true;
                }

                return false;
            }
        });
    }

    private void initData() {

        dialogBox.showDialog(true);
        JSONObject jBody = new JSONObject();

        try {
            jBody.put("keyword", keyword);
            jBody.put("start", "");
            jBody.put("count", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiVolley request = new ApiVolley(context, jBody, "POST", ServerURL.getTransaksiHariIni, new ApiVolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {

                dialogBox.dismissDialog();
                String message = "Terjadi kesalahan saat memuat data, harap ulangi";
                double totalAll = 0;

                try {

                    JSONObject response = new JSONObject(result);
                    String status = response.getJSONObject("metadata").getString("status");
                    message = response.getJSONObject("metadata").getString("message");
                    listTransaksi.clear();

                    if(iv.parseNullInteger(status) == 200){

                        JSONArray jsonArray = response.getJSONArray("response");
                        String lastJenis = "", lastFlag = "";
                        double totalPerNama = 0;

                        for(int i = 0; i < jsonArray.length(); i++){

                            JSONObject jo = jsonArray.getJSONObject(i);

                            //header
                            if(!lastJenis.equals(jo.getString("jenis")) && !lastFlag.equals("flag")){

                                lastJenis = jo.getString("jenis");
                                lastFlag = jo.getString("flag");
                                String keterangan = lastFlag;
                                if(lastJenis.equals("1")){

                                    keterangan = "Transaksi " + lastFlag;
                                }else if(lastJenis.equals("2")){

                                    keterangan = "Riwayat " + lastFlag;
                                }

                                listTransaksi.add(new CustomItem("H", keterangan));

                                totalPerNama = 0;
                            }

                            if(i < jsonArray.length() - 1){

                                JSONObject jo2 = jsonArray.getJSONObject(i+1);
                                if(jo2.getString("nama").equals(jo.getString("nama")) && lastJenis.equals(jo2.getString("jenis"))){

                                    listTransaksi.add(new CustomItem("I", jo.getString("nama"), jo.getString("total"), jo.getString("status")));
                                    totalPerNama += iv.parseNullLong(jo.getString("total"));
                                }else{

                                    listTransaksi.add(new CustomItem("I", jo.getString("nama"), jo.getString("total"), jo.getString("status")));
                                    totalPerNama += iv.parseNullLong(jo.getString("total"));
                                    listTransaksi.add(new CustomItem("F", String.valueOf(totalPerNama)));
                                    totalPerNama = 0;
                                }
                            }else{

                                listTransaksi.add(new CustomItem("I", jo.getString("nama"), jo.getString("total"), jo.getString("status")));
                                totalPerNama += iv.parseNullLong(jo.getString("total"));
                                listTransaksi.add(new CustomItem("F", String.valueOf(totalPerNama)));
                                totalPerNama = 0;
                            }

                            if(!jo.getString("jenis").equals("1")){
                                totalAll += iv.parseNullDouble(jo.getString("total"));
                            }
                        }

                    }else{

                        DialogBox.showDialog(context, 3, message);
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                    View.OnClickListener clickListener = new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            dialogBox.dismissDialog();
                            initData();
                        }
                    };

                    dialogBox.showDialog(clickListener, "Ulangi Proses", "Terjadi kesalahan, harap ulangi proses");
                }

                tvTotal.setText(iv.ChangeToRupiahFormat(totalAll));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String result) {

                dialogBox.dismissDialog();
                View.OnClickListener clickListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialogBox.dismissDialog();
                        initData();
                    }
                };

                dialogBox.showDialog(clickListener, "Ulangi Proses", "Terjadi kesalahan, harap ulangi proses");
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
