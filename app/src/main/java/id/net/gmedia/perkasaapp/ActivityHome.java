package id.net.gmedia.perkasaapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import id.net.gmedia.perkasaapp.ActKunjungan.ActivityKunjungan;

public class ActivityHome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private boolean exit = false;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Perkasa App");
        }

        //Inisialisasi button UI
        LinearLayout btn_mkios, btn_perdana, btn_hari_ini, btn_stok, btn_piutang, btn_komplain,
                btn_tcash, btn_verifikasi, btn_lokasi, btn_riwayat, btn_customer, btn_preorder, btn_kunjungan;
        btn_mkios = findViewById(R.id.btn_mkios);
        btn_perdana = findViewById(R.id.btn_perdana);
        btn_hari_ini = findViewById(R.id.btn_hari_ini);
        btn_stok = findViewById(R.id.btn_stok);
        btn_piutang = findViewById(R.id.btn_piutang);
        btn_komplain = findViewById(R.id.btn_komplain);
        btn_tcash = findViewById(R.id.btn_tcash);
        btn_verifikasi = findViewById(R.id.btn_verifikasi);
        btn_lokasi = findViewById(R.id.btn_lokasi);
        btn_riwayat = findViewById(R.id.btn_riwayat);
        btn_customer = findViewById(R.id.btn_customer);
        btn_preorder = findViewById(R.id.btn_preorder);
        btn_kunjungan = findViewById(R.id.btn_kunjungan);

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

        btn_stok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityHome.this, ActivityStokPerdana.class));
            }
        });

        btn_piutang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityHome.this, ActivityPiutang.class));
            }
        });

        btn_komplain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityHome.this, ActivityKomplain1.class));
            }
        });

        btn_tcash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityHome.this, ActivityOrderTcash1.class));
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
        btn_customer.setOnClickListener(new View.OnClickListener() {
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
        btn_kunjungan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityHome.this, ActivityKunjungan.class));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
            case R.id.nav_logout:
                AppSharedPreferences.LogOut(ActivityHome.this);
                startActivity(new Intent(ActivityHome.this, ActivityLogin.class));
                finish();break;

                default:
                    System.out.println(id);
                    break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
