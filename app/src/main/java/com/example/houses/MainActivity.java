package com.example.houses;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.houses.fragments.HomeFragment;
import com.example.houses.fragments.InfoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    HomeFragment Home_fragment = new HomeFragment();
    InfoFragment Info_fragment = new InfoFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottom_nav);

        getSupportFragmentManager().beginTransaction().replace(R.id.container, Home_fragment).commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.homeMenu:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, Home_fragment).commit();
                        break;

                    case R.id.infoMenu:
                        bottomNavigationView.setSelected(true);

                        getSupportFragmentManager().beginTransaction().replace(R.id.container, Info_fragment).commit();
                        break;

                    default:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, Home_fragment).commit();
                }
                return true;
            }
        });
    }
}