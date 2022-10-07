package com.example.asmmob202;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.asmmob202.fragment.ChiFrgment;
import com.example.asmmob202.fragment.GioiThieuFragment;
import com.example.asmmob202.fragment.KhoanChiFragment;
import com.example.asmmob202.fragment.KhoanThuFragment;
import com.example.asmmob202.fragment.TrangChuFragment;
import com.example.asmmob202.fragment.ThongKeFragment;
import com.example.asmmob202.fragment.ThuFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    BottomNavigationView bottom;
    Toolbar toolbar;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
         navigationView = findViewById(R.id.nav_view);
        bottom = findViewById(R.id.bottom);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment fragment;
                switch (item.getItemId()){
                    case R.id.home:
                        fragment = new TrangChuFragment();
                        bottom.setSelectedItemId(R.id.home);
                        break;
                    case R.id.khoanthu:
                        fragment = new ThuFragment();
                        bottom.setSelectedItemId(R.id.khoanthu);
                        break;
                    case R.id.Khoanchi:
                        fragment = new ChiFrgment();
                        bottom.setSelectedItemId(R.id.Khoanchi);
                        break;
                    case R.id.Thongke:
                        fragment = new ThongKeFragment();
                        bottom.setSelectedItemId(R.id.Thongke);
                        break;
                    case R.id.gioithieu:
                        fragment = new GioiThieuFragment();
                        bottom.setSelectedItemId(R.id.gioithieu);
                        break;
                    case  R.id.back:
                       fragment = new ThongKeFragment();
                        break;
                    default:
                        fragment = new ThuFragment();
                        break;
                }
                fragmentManager.beginTransaction()
                        .replace(R.id.maincontent, fragment)
                        .commit();
                drawerLayout.closeDrawer(GravityCompat.START);
                setTitle(item.getTitle());
                return false;
            }
        });

        bottom.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            FragmentManager fragmentManager = getSupportFragmentManager();
            Fragment fragment;
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        fragment = new TrangChuFragment();
                        toolbar.setTitle("Trang Chủ");
                        break;
                    case R.id.khoanthu:
                        fragment = new ThuFragment();
                       toolbar.setTitle("Thu");
                        break;
                    case R.id.Khoanchi:
                      fragment = new ChiFrgment();
                        toolbar.setTitle("Chi");
                        break;
                    case R.id.Thongke:
                        fragment = new ThongKeFragment();
                        toolbar.setTitle("Thống kê");
                        break;
                    case R.id.gioithieu:
                        fragment = new GioiThieuFragment();
                        toolbar.setTitle("Giới thiệu");
                        break;
                    default:
                        fragment = new TrangChuFragment();
                        break;
                }
                fragmentManager.beginTransaction()
                        .replace(R.id.maincontent, fragment)
                        .commit();
                return true;
            }
        });

        TrangChuFragment trangchuFragment = new TrangChuFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .add(R.id.maincontent, trangchuFragment)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
}