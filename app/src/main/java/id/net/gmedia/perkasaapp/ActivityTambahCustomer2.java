package id.net.gmedia.perkasaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class ActivityTambahCustomer2 extends AppCompatActivity implements OnMapReadyCallback{

    private ModelOutlet outlet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_customer2);

        TextView txt_nama, txt_alamat, txt_nomor, txt_nomorhp;
        txt_nama = findViewById(R.id.txt_nama);
        txt_alamat = findViewById(R.id.txt_alamat);
        txt_nomor = findViewById(R.id.txt_nomor);
        txt_nomorhp = findViewById(R.id.txt_nomorhp);

        if(getIntent().hasExtra("outlet")){
            outlet = getIntent().getParcelableExtra("outlet");
            txt_nama.setText(outlet.getNama());
            txt_alamat.setText(outlet.getAlamat());
            txt_nomor.setText(outlet.getNomor());
            txt_nomorhp.setText(outlet.getNomorHp());
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        final ScrollView layout_scroll = findViewById(R.id.layout_scroll);
        ((CustomMapView) getSupportFragmentManager().findFragmentById(R.id.map)).setListener(new CustomMapView.OnTouchListener() {
            @Override
            public void onTouch() {
                layout_scroll.requestDisallowInterceptTouchEvent(true);
            }
        });

        LatLng lokasi;
        if(outlet != null){
            lokasi =  new LatLng(outlet.getLatitude(),outlet.getLongitude());
        }
        else{
            //Jika menambah baru, ambil lokasi dari posisi user sekarang
            lokasi =  new LatLng(-7.0213463,110.4270008);
        }

        googleMap.addMarker(new MarkerOptions().position(lokasi).title("Posisi Outlet"));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(lokasi));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 1000, null);
    }
}
