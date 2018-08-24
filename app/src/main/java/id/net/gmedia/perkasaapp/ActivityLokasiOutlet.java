package id.net.gmedia.perkasaapp;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class ActivityLokasiOutlet extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lokasi_outlet);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Lokasi Outlet");
        }

        TabLayout tab = findViewById(R.id.tab_outlet);
        ViewPager view = findViewById(R.id.view_outlet);

        AdapterLokasiOutlet adapter = new AdapterLokasiOutlet(getSupportFragmentManager());
        adapter.addFragment(new FragmentLokasiOutletVerifikasi(), "Terverifikasi");
        adapter.addFragment(new FragmentLokasiOutletBelumVerifikasi(), "Butuh Verifikasi");
        view.setAdapter(adapter);
        tab.setupWithViewPager(view);
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

    class AdapterLokasiOutlet extends FragmentPagerAdapter {
        private final List<Fragment> listFragment = new ArrayList<>();
        private final List<String> listTitle = new ArrayList<>();

        AdapterLokasiOutlet(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return listFragment.get(position);
        }

        @Override
        public int getCount() {
            return listFragment.size();
        }

        void addFragment(Fragment fragment, String title) {
            listFragment.add(fragment);
            listTitle.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return listTitle.get(position);
        }
    }
}
