package id.net.gmedia.perkasaapp.ActPengajuanRKP;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.maulana.custommodul.ApiVolley;
import com.maulana.custommodul.CustomItem;
import com.maulana.custommodul.CustomView.DialogBox;
import com.maulana.custommodul.FormatItem;
import com.maulana.custommodul.ItemValidation;
import com.maulana.custommodul.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import id.net.gmedia.perkasaapp.ActNotePengajuan.Adapter.ListPerubahanDataRSAdapter;
import id.net.gmedia.perkasaapp.ActPengajuanRKP.Adapter.ListPengajuanRKPAdapter;
import id.net.gmedia.perkasaapp.R;
import id.net.gmedia.perkasaapp.Utils.ServerURL;

public class ListPengajuanRKP extends AppCompatActivity {

    public static final String flag = "Custom RKP";
    private Context context;
    private DialogBox dialogBox;
    private ItemValidation iv = new ItemValidation();
    private TextView tvStart, tvEnd;
    private ImageView ivShow;
    private ListView lvPengajuan;
    private LinearLayout btnKalenderStart, btnKalenderEnd;
    private String keyword = "", dateStart = "", dateEnd = "";
    private EditText edtKeyword;
    private List<CustomItem> listPengajuan = new ArrayList<>();
    private ListPengajuanRKPAdapter adapter;
    private View footerList;
    private boolean isLoading = false;
    private int start = 0, count = 10;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pengajuan_rkp);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            getSupportActionBar().setTitle("Pengajuan Program");
        }

        context = this;
        session = new SessionManager(context);
        dialogBox = new DialogBox(context);
        initUI();
        initEvent();
        initData();
    }

    private void initUI() {

        tvStart = (TextView) findViewById(R.id.tv_start);
        tvEnd = (TextView) findViewById(R.id.tv_end);
        ivShow = (ImageView) findViewById(R.id.iv_show);
        lvPengajuan = (ListView) findViewById(R.id.lv_pengajuan);
        btnKalenderStart = findViewById(R.id.btn_kalender_start);
        btnKalenderEnd = findViewById(R.id.btn_kalender_end);
        edtKeyword = (EditText) findViewById(R.id.edt_keyword);
        LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        footerList = li.inflate(R.layout.footer_list, null);

        keyword = "";
        isLoading = false;

        dateStart = iv.getCurrentDate(FormatItem.formatDate);
        dateEnd = iv.getCurrentDate(FormatItem.formatDate);
        tvStart.setText(iv.ChangeFormatDateString(dateStart, FormatItem.formatDate, FormatItem.formatDateDisplay));
        tvEnd.setText(iv.ChangeFormatDateString(dateEnd, FormatItem.formatDate, FormatItem.formatDateDisplay));

        listPengajuan = new ArrayList<>();
        adapter = new ListPengajuanRKPAdapter((Activity) context, listPengajuan,0);
        lvPengajuan.addFooterView(footerList);
        lvPengajuan.setAdapter(adapter);
        lvPengajuan.removeFooterView(footerList);

        lvPengajuan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                CustomItem item = (CustomItem) adapterView.getItemAtPosition(i);
                final String nobukti = item.getItem1();
                String keterangan = item.getItem2();
                String nominal = iv.ChangeToCurrencyFormat(item.getItem3());

                final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
                View viewDialog = inflater.inflate(R.layout.dialog_pengajuan_rkp, null);
                builder.setView(viewDialog);
                builder.setCancelable(false);

                final TextView tvText1 = (TextView) viewDialog.findViewById(R.id.tv_text1);
                tvText1.setText("Setujui pengajuan "+ keterangan+" dengan nominal "+ nominal+"?");
                final Button btnSetujui = (Button) viewDialog.findViewById(R.id.btn_setujui);
                final Button btnTolak = (Button) viewDialog.findViewById(R.id.btn_tolak);

                final AlertDialog alert = builder.create();
                alert.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

                    btnSetujui.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view2) {

                        if(!isLoading){

                            if(alert != null) {
                                try {

                                    alert.dismiss();
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }

                            saveData(nobukti, "2");
                        }else{

                            Toast.makeText(context, "Harap coba sesaat lagi", Toast.LENGTH_LONG).show();
                        }

                    }
                });

                btnTolak.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view2) {

                        if(!isLoading){

                            if(alert != null) {
                                try {

                                    alert.dismiss();
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }

                            saveData(nobukti, "9");
                        }else{

                            Toast.makeText(context, "Harap coba sesaat lagi", Toast.LENGTH_LONG).show();
                        }
                    }
                });

                try {
                    alert.show();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        lvPengajuan.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

                int threshold = 1;
                int total = lvPengajuan.getCount();

                if (i == SCROLL_STATE_IDLE) {
                    if (lvPengajuan.getLastVisiblePosition() >= total - threshold && !isLoading) {

                        isLoading = true;
                        start += count;
                        initData();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {

            }
        });
    }

    private void initEvent() {

        btnKalenderStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SimpleDateFormat sdf = new SimpleDateFormat(FormatItem.formatDateDisplay);
                Date dateValue = null;
                final Calendar customDate;

                try {
                    dateValue = sdf.parse(iv.ChangeFormatDateString(dateStart, FormatItem.formatDate, FormatItem.formatDateDisplay));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                customDate = Calendar.getInstance();
                final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        customDate.set(Calendar.YEAR,year);
                        customDate.set(Calendar.MONTH,month);
                        customDate.set(Calendar.DATE,date);

                        SimpleDateFormat sdFormat = new SimpleDateFormat(FormatItem.formatDateDisplay, Locale.US);
                        dateStart = iv.ChangeFormatDateString(sdFormat.format(customDate.getTime()), FormatItem.formatDateDisplay, FormatItem.formatDate);
                        tvStart.setText(sdFormat.format(customDate.getTime()));
                    }
                };

                SimpleDateFormat yearOnly = new SimpleDateFormat("yyyy");
                new DatePickerDialog(context,date, iv.parseNullInteger(yearOnly.format(dateValue)),dateValue.getMonth(),dateValue.getDate()).show();
            }
        });

        btnKalenderEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SimpleDateFormat sdf = new SimpleDateFormat(FormatItem.formatDateDisplay);
                Date dateValue = null;
                final Calendar customDate;

                try {
                    dateValue = sdf.parse(iv.ChangeFormatDateString(dateEnd, FormatItem.formatDate, FormatItem.formatDateDisplay));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                customDate = Calendar.getInstance();
                final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        customDate.set(Calendar.YEAR,year);
                        customDate.set(Calendar.MONTH,month);
                        customDate.set(Calendar.DATE,date);

                        SimpleDateFormat sdFormat = new SimpleDateFormat(FormatItem.formatDateDisplay, Locale.US);
                        dateEnd = iv.ChangeFormatDateString(sdFormat.format(customDate.getTime()), FormatItem.formatDateDisplay, FormatItem.formatDate);
                        tvEnd.setText(sdFormat.format(customDate.getTime()));
                    }
                };

                SimpleDateFormat yearOnly = new SimpleDateFormat("yyyy");
                new DatePickerDialog(context,date, iv.parseNullInteger(yearOnly.format(dateValue)),dateValue.getMonth(),dateValue.getDate()).show();
            }
        });

        ivShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                initData();
            }
        });

        edtKeyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                keyword = editable.toString();
            }
        });

        edtKeyword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                if(i == EditorInfo.IME_ACTION_SEARCH){

                    keyword = edtKeyword.getText().toString();
                    start = 0;
                    listPengajuan.clear();
                    initData();

                    iv.hideSoftKey(context);
                    return true;
                }

                return false;
            }
        });
    }

    private void initData() {

        isLoading = true;
        if(start == 0) dialogBox.showDialog(true);
        JSONObject jBody = new JSONObject();
        lvPengajuan.addFooterView(footerList);

        try {
            jBody.put("kode_omo", "");
            jBody.put("status", "1");
            jBody.put("keyword", keyword);
            jBody.put("start", start);
            jBody.put("count", count);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ApiVolley request = new ApiVolley(context, jBody, "POST", ServerURL.getPengajuanRKP, new ApiVolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {

                lvPengajuan.removeFooterView(footerList);
                if(start == 0) dialogBox.dismissDialog();
                String message = "";
                isLoading = false;

                try {

                    JSONObject response = new JSONObject(result);
                    String status = response.getJSONObject("metadata").getString("status");
                    message = response.getJSONObject("metadata").getString("message");

                    if(iv.parseNullInteger(status) == 200){

                        JSONArray ja = response.getJSONArray("response");

                        for(int i = 0; i < ja.length();i++){

                            JSONObject jo = ja.getJSONObject(i);
                            listPengajuan.add(new CustomItem(
                                    jo.getString("nobukti")
                                    ,jo.getString("keterangan")
                                    ,jo.getString("nominal")
                                    ,jo.getString("status")
                                    ,jo.getString("insert_at")
                                    ,jo.getString("user_insert")
                                    ,jo.getString("approved_by")
                                    ,jo.getString("approved_at")
                            ));

                        }
                    }else{

                        if(start == 0) DialogBox.showDialog(context, 3, message);
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

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String result) {

                lvPengajuan.removeFooterView(footerList);
                isLoading = false;
                if(start == 0) dialogBox.dismissDialog();

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

    private void saveData(String nobukti, String flag) {

        isLoading = true;
        final ProgressDialog progressDialog = new ProgressDialog(context, R.style.AppTheme_Login_Default_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Menyimpan...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        JSONObject jBody = new JSONObject();

        PackageInfo pInfo = null;
        String version = "";

        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        version = pInfo.versionName;

        try {
            jBody.put("nobukti", nobukti);
            jBody.put("flag", flag);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String method = "POST";
        ApiVolley request = new ApiVolley(context, jBody, method, ServerURL.savePengajuanRKP, new ApiVolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {

                isLoading = false;
                try {

                    progressDialog.dismiss();
                }catch (Exception e){
                    e.printStackTrace();
                }

                String superMessage = "Terjadi kesalahan saat menyimpan data, harap ulangi";
                try {

                    JSONObject response = new JSONObject(result);
                    String status = response.getJSONObject("metadata").getString("status");
                    superMessage = response.getJSONObject("metadata").getString("message");

                    if(iv.parseNullInteger(status) == 200){

                        Toast.makeText(context, superMessage, Toast.LENGTH_LONG).show();
                        start = 0;
                        listPengajuan.clear();
                        initData();
                    }else{
                        Toast.makeText(context, superMessage, Toast.LENGTH_LONG).show();
                        //showDialog(2, superMessage);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(context, superMessage, Toast.LENGTH_LONG).show();
                    //showDialog(4, "Laporan tidak masuk, harap tekan ulangi proses");
                }
            }

            @Override
            public void onError(String result) {

                isLoading = false;
                try {

                    progressDialog.dismiss();
                }catch (Exception e){
                    e.printStackTrace();
                }

                Toast.makeText(context, "Terjadi kesalahan saat menyimpan data, harap ulangi kembali", Toast.LENGTH_LONG).show();
                //showDialog(4, "Laporan tidak masuk, harap tekan ulangi proses");
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.nav_history:
                Intent intent = new Intent(context, HistoryPengajuanRKP.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);

        return super.onCreateOptionsMenu(menu);
    }
}
