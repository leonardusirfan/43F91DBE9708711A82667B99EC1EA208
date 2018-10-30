package id.net.gmedia.perkasaapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.maulana.custommodul.ApiVolley;
import com.maulana.custommodul.ItemValidation;
import com.maulana.custommodul.SessionManager;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.json.JSONException;
import org.json.JSONObject;

import id.net.gmedia.perkasaapp.ActBranding.DetailBranding;
import id.net.gmedia.perkasaapp.ActBranding.ListBranding;
import id.net.gmedia.perkasaapp.ActChangePassword.ActChangePassword;
import id.net.gmedia.perkasaapp.ActDirectSelling.DirectSellingPerdana;
import id.net.gmedia.perkasaapp.ActDirectSelling.DirectSellingPulsa;
import id.net.gmedia.perkasaapp.ActDirectSelling.ResellerDetailSelling;
import id.net.gmedia.perkasaapp.ActKunjungan.ActivityKunjungan;
import id.net.gmedia.perkasaapp.ActKunjungan.DetailKunjungan;
import id.net.gmedia.perkasaapp.ActMarketIntelligent.DetailMarketIntelligent;
import id.net.gmedia.perkasaapp.ActMarketIntelligent.ListMarketIntelligent;
import id.net.gmedia.perkasaapp.ActMarketSurvey.DetailMarketSurvey;
import id.net.gmedia.perkasaapp.ActMarketSurvey.ListMarketSurvey;
import id.net.gmedia.perkasaapp.ActNotePengajuan.DetailPerubahanDataRS;
import id.net.gmedia.perkasaapp.ActNotePengajuan.ListPengajuanPerubahanRS;
import id.net.gmedia.perkasaapp.ActOrderMkios.ActivityOrderMkios1;
import id.net.gmedia.perkasaapp.ActOrderMkios.ActivityOrderMkios2;
import id.net.gmedia.perkasaapp.ActOrderPerdana.ActivityOrderPerdana1;
import id.net.gmedia.perkasaapp.ActOrderPerdana.ActivityOrderPerdana3;
import id.net.gmedia.perkasaapp.ActOrderTcash.ActivityOrderTcash1;
import id.net.gmedia.perkasaapp.ActOrderTcash.ActivityOrderTcash2;
import id.net.gmedia.perkasaapp.ActOrderTcash.ActivityOrderTcash3;
import id.net.gmedia.perkasaapp.ActPelunasanPiutang.ActivityPiutang;
import id.net.gmedia.perkasaapp.ActPelunasanPiutang.DetailPelunasanPiutang;
import id.net.gmedia.perkasaapp.ActPenjualanHariIni.ActivityPenjualanHariIni;
import id.net.gmedia.perkasaapp.ActRiwayatPenjualan.ActivityRiwayatPenjualan;
import id.net.gmedia.perkasaapp.ActSetoran.ListSetoran;
import id.net.gmedia.perkasaapp.Services.LocationUpdater;
import id.net.gmedia.perkasaapp.SideAkunAnda.SideAccount;
import id.net.gmedia.perkasaapp.Utils.ServerURL;

public class ActivityHome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private boolean exit = false;
    private NavigationView navigationView;
    private SessionManager session;
    private Context context;

    private boolean dialogActive = false;
    private String version = "";
    private String latestVersion = "";
    private String link = "";
    private boolean updateRequired = false;
    private AlertDialog dialogVersion;
    private ItemValidation iv = new ItemValidation();
    private SlidingUpPanelLayout suplContainer;
    private ImageView ivIcon;
    private TextView tvJabatan, tvNamaSales, tvTotalOmset
            , tvDsTargetMkios, tvDsOmsetMkios, tvDsGapMkios
            , tvDsTargetPerdana, tvDsOmsetPerdana, tvDsGapPerdana
            , tvDsTargetBulk, tvDsOmsetBulk, tvDsGapBulk
            , tvDsTargetSurvey, tvDsEffectiveCall;
    private LinearLayout btnSetoran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Perkasa App");
        }

        dialogActive = false;
        context = this;
        session = new SessionManager(context);

        //Inisialisasi button UI
        LinearLayout btn_mkios, btn_perdana, btn_hari_ini, btn_stok, btn_komplain,
                btn_tcash, btn_verifikasi, btn_lokasi, btn_riwayat, btn_customer, btn_preorder, btn_kunjungan
                , btn_market_survey, btn_direct_selling, btn_perubahan_data_rs, btn_pelunasan_piutang, btn_branding, btn_market_intelligent;

        btn_mkios = findViewById(R.id.btn_mkios);
        btn_perdana = findViewById(R.id.btn_perdana);
        btn_hari_ini = findViewById(R.id.btn_hari_ini);
        btn_tcash = findViewById(R.id.btn_tcash);
        btn_verifikasi = findViewById(R.id.btn_verifikasi);
        btn_lokasi = findViewById(R.id.btn_lokasi);
        btn_riwayat = findViewById(R.id.btn_riwayat);
        //btn_customer = findViewById(R.id.btn_customer);
        //btn_stok = findViewById(R.id.btn_stok);
        //btn_komplain = findViewById(R.id.btn_komplain);
        //btn_preorder = findViewById(R.id.btn_preorder);
        btn_market_survey = (LinearLayout) findViewById(R.id.btn_market_survey);
        btn_direct_selling = (LinearLayout) findViewById(R.id.btn_direct_selling);
        btn_perubahan_data_rs = (LinearLayout) findViewById(R.id.btn_perubahan_data_rs);
        btn_pelunasan_piutang = (LinearLayout) findViewById(R.id.btn_pelunasan_piutang);
        btn_branding = (LinearLayout) findViewById(R.id.btn_branding);
        btn_market_intelligent = (LinearLayout) findViewById(R.id.btn_market_intelligent);
        btn_kunjungan = findViewById(R.id.btn_kunjungan);
        btnSetoran = (LinearLayout) findViewById(R.id.btn_setoran);
        suplContainer = (SlidingUpPanelLayout) findViewById(R.id.supl_container);
        ivIcon = (ImageView) findViewById(R.id.iv_icon);

        tvJabatan = (TextView) findViewById(R.id.tv_jabatan);
        tvNamaSales = (TextView) findViewById(R.id.tv_nama_sales);
        tvTotalOmset = (TextView) findViewById(R.id.tv_total_omset);
        tvDsTargetMkios = (TextView) findViewById(R.id.tv_ds_target_mkios);
        tvDsOmsetMkios = (TextView) findViewById(R.id.tv_ds_omset_mkios);
        tvDsGapMkios = (TextView) findViewById(R.id.tv_ds_gap_mkios);
        tvDsTargetPerdana = (TextView) findViewById(R.id.tv_ds_target_perdana);
        tvDsOmsetPerdana = (TextView) findViewById(R.id.tv_ds_omset_perdana);
        tvDsGapPerdana = (TextView) findViewById(R.id.tv_ds_gap_perdana);
        tvDsTargetBulk = (TextView) findViewById(R.id.tv_ds_target_bulk);
        tvDsOmsetBulk = (TextView) findViewById(R.id.tv_ds_omset_bulk);
        tvDsGapBulk = (TextView) findViewById(R.id.tv_ds_gap_bulk);
        tvDsTargetSurvey = (TextView) findViewById(R.id.tv_ds_target_survey);
        tvDsEffectiveCall = (TextView) findViewById(R.id.tv_ds_effective_call);

        btn_mkios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityHome.this, ActivityOrderMkios1.class));
            }
        });

        btn_perdana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityHome.this, ActivityOrderPerdana1.class));
            }
        });

        btn_hari_ini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityHome.this, ActivityPenjualanHariIni.class));
            }
        });

        btn_tcash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityHome.this, ActivityOrderTcash2.class));
            }
        });

        btn_verifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityHome.this, ActivityVerifikasiOutlet1.class));
            }
        });

        btn_lokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityHome.this, ActivityLokasiOutlet1.class));
            }
        });

        btn_riwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityHome.this, ActivityRiwayatPenjualan.class));
            }
        });

        /*btn_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityHome.this, ActivityTambahCustomer1.class));
            }
        });
        btn_preorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityHome.this, ActivityPreorderPerdana1.class));
            }
        });

        btn_stok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityHome.this, ActivityStokPerdana.class));
            }
        });

        btn_komplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityHome.this, ActivityKomplain1.class));
            }
        });*/

        btn_market_survey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ActivityHome.this, ListMarketSurvey.class));
            }
        });

        btn_direct_selling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ActivityHome.this, ResellerDetailSelling.class));
            }
        });

        btn_perubahan_data_rs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ActivityHome.this, ListPengajuanPerubahanRS.class));
            }
        });

        btn_pelunasan_piutang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ActivityHome.this, ActivityPiutang.class));
            }
        });

        btn_branding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ActivityHome.this, ListBranding.class));
            }
        });

        btn_market_intelligent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ActivityHome.this, ListMarketIntelligent.class));
            }
        });

        if(!session.isLoggedIn()){
            Intent intent = new Intent(context, ActivityLogin.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }

        btn_kunjungan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityHome.this, ActivityKunjungan.class));
            }
        });

        btnSetoran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ActivityHome.this, ListSetoran.class));
            }
        });

        //Inisialisasi Drawer UI
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){

            String flag = bundle.getString("flag", "");
            if(flag.equals(ActivityOrderMkios2.flag)
                    || flag.equals(ActivityOrderPerdana3.flag)
                    || flag.equals(DirectSellingPulsa.flag)
                    || flag.equals(DirectSellingPerdana.flag)
                    || flag.equals(ActivityOrderTcash3.flag)
                    ){

                startActivity(new Intent(ActivityHome.this, ActivityPenjualanHariIni.class));
            }else if(flag.equals(DetailMarketSurvey.flag)){

                startActivity(new Intent(ActivityHome.this, ListMarketSurvey.class));
            }else if(flag.equals(DetailBranding.flag)){

                startActivity(new Intent(ActivityHome.this, ListBranding.class));
            }else if(flag.equals(DetailMarketIntelligent.flag)){

                startActivity(new Intent(ActivityHome.this, ListMarketIntelligent.class));
            }else if(flag.equals(DetailPerubahanDataRS.flag)){

                startActivity(new Intent(ActivityHome.this, ListPengajuanPerubahanRS.class));
            }else if(flag.equals(DetailPelunasanPiutang.flag)){

                startActivity(new Intent(ActivityHome.this, ActivityPiutang.class));
            }else if(flag.equals(DetailKunjungan.flag)){

                startActivity(new Intent(ActivityHome.this, ActivityKunjungan.class));
            }
        }

        suplContainer.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {

                if(newState == SlidingUpPanelLayout.PanelState.COLLAPSED){

                    ivIcon.setImageResource(R.mipmap.ic_up);
                }else{

                    ivIcon.setImageResource(R.mipmap.ic_down);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        session = new SessionManager(context);
        if(session.isLoggedIn()){

            if(iv.isServiceRunning(context, LocationUpdater.class)){
                //stopCurrentService();
            }

        }

        statusCheck();
        checkVersion();
    }

    private void checkVersion(){

        PackageInfo pInfo = null;
        version = "";

        try {
            pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        version = pInfo.versionName;
        getSupportActionBar().setSubtitle(getResources().getString(R.string.app_name) + " v "+ version);
        latestVersion = "";
        link = "";

        ApiVolley request = new ApiVolley(context, new JSONObject(), "GET", ServerURL.getVersionApp, new ApiVolley.VolleyCallback() {

            @Override
            public void onSuccess(String result) {

                JSONObject responseAPI;
                if(dialogVersion != null){
                    if(dialogVersion.isShowing()) dialogVersion.dismiss();
                }

                try {
                    responseAPI = new JSONObject(result);
                    String status = responseAPI.getJSONObject("metadata").getString("status");

                    if(iv.parseNullInteger(status) == 200){
                        latestVersion = responseAPI.getJSONObject("response").getString("version");
                        link = responseAPI.getJSONObject("response").getString("link");
                        updateRequired = (iv.parseNullInteger(responseAPI.getJSONObject("response").getString("wajib")) == 1) ? true : false;

                        if(!version.trim().equals(latestVersion.trim()) && link.length() > 0){

                            if(updateRequired){

                                dialogVersion = new AlertDialog.Builder(context)
                                        .setIcon(R.mipmap.ic_launcher)
                                        .setTitle("Update")
                                        .setMessage("Versi terbaru "+latestVersion+" telah tersedia, mohon download versi terbaru.")
                                        .setPositiveButton("Update Sekarang", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                try {
                                                    dialogVersion.dismiss();
                                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                                                    startActivity(browserIntent);
                                                }catch (Exception e){
                                                    e.printStackTrace();
                                                    Toast.makeText(context, "Hp anda tidak memiliki browser tertanam", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        })
                                        .setCancelable(false)
                                        .show();
                            }else{

                                dialogVersion = new AlertDialog.Builder(context)
                                        .setIcon(R.mipmap.ic_launcher)
                                        .setTitle("Update")
                                        .setMessage("Versi terbaru "+latestVersion+" telah tersedia, mohon download versi terbaru.")
                                        .setPositiveButton("Update Sekarang", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {

                                                try {
                                                    dialogVersion.dismiss();
                                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                                                    startActivity(browserIntent);
                                                }catch (Exception e){
                                                    e.printStackTrace();
                                                    Toast.makeText(context, "Hp anda tidak memiliki browser tertanam", Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        })
                                        .setNegativeButton("Update Nanti", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        }).show();
                            }
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String result) {

                if(dialogVersion != null){
                    if(dialogVersion.isShowing()) dialogVersion.dismiss();
                }
            }
        });
    }

    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            buildAlertMessageNoGps();
        }else{

            try {
                new CountDownTimer(4000, 1000) {

                    public void onTick(long millisUntilFinished) {
                        //here you can have your logic to set text to edittext
                    }

                    public void onFinish() {
                        if(!iv.isServiceRunning(context, LocationUpdater.class)){
                            startService(new Intent(getApplicationContext(), LocationUpdater.class));
                        }
                    }

                }.start();

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void buildAlertMessageNoGps() {
        if(!dialogActive){
            dialogActive = true;
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Mohon Hidupkan Akses Lokasi (GPS) Anda.")
                    .setCancelable(false)
                    .setPositiveButton("Hidupkan", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, final int id) {
                            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();

            alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    dialogActive = false;
                }
            });
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(navigationView != null){
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    @Override
    public void onBackPressed() {
        final int doublePress_delay = 2000;
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(exit){
                super.onBackPressed();
            }
            else{
                Toast.makeText(this, "Klik sekali lagi untuk keluar app", Toast.LENGTH_SHORT).show();
                exit = true;
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        exit = false;
                    }
                }, doublePress_delay);
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle Drawer Navigation
        int id = item.getItemId();

        switch (id){
            case R.id.nav_mkios:startActivity(new Intent(ActivityHome.this, ActivityOrderMkios1.class));break;
            case R.id.nav_perdana:startActivity(new Intent(ActivityHome.this, ActivityOrderPerdana1.class));break;
            case R.id.nav_hari_ini:startActivity(new Intent(ActivityHome.this, ActivityPenjualanHariIni.class));break;
            case R.id.nav_stok:startActivity(new Intent(ActivityHome.this, ActivityStokPerdana.class));break;
            case R.id.nav_piutang:startActivity(new Intent(ActivityHome.this, ActivityPiutang.class));break;
            case R.id.nav_komplain:startActivity(new Intent(ActivityHome.this, ActivityKomplain1.class));break;
            case R.id.nav_tcash:startActivity(new Intent(ActivityHome.this, ActivityOrderTcash1.class));break;
            case R.id.nav_verifikasi:startActivity(new Intent(ActivityHome.this, ActivityVerifikasiOutlet1.class));break;
            case R.id.nav_lokasi:startActivity(new Intent(ActivityHome.this, ActivityLokasiOutlet1.class));break;
            case R.id.nav_riwayat:startActivity(new Intent(ActivityHome.this, ActivityRiwayatPenjualan.class));break;
            case R.id.nav_customer:startActivity(new Intent(ActivityHome.this, ActivityTambahCustomer1.class));break;
            case R.id.nav_preorder:startActivity(new Intent(ActivityHome.this, ActivityPreorderPerdana1.class));break;
            case R.id.nav_kunjungan:startActivity(new Intent(ActivityHome.this, ActivityKunjungan.class));break;
            case R.id.nav_market_survey:startActivity(new Intent(ActivityHome.this, ListMarketSurvey.class));break;
            case R.id.nav_password:startActivity(new Intent(ActivityHome.this, ActChangePassword.class));break;
            case R.id.nav_akun:startActivity(new Intent(ActivityHome.this, SideAccount.class));break;
            case R.id.nav_logout:session.logoutUser(new Intent(context, ActivityLogin.class));break;
            default:
                //System.out.println(id);
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
