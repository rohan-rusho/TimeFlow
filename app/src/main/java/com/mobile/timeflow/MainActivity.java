package com.mobile.timeflow;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);

        // Show PNG icons in original colors (no tint)
        bottomNavigationView.setItemIconTintList(null);

        // Load default fragment on start
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HoursToMinutesFragment())
                    .commit();
        }

        // Handle bottom navigation clicks
        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            int id = item.getItemId();
            if (id == R.id.nav_hours_to_minutes) {
                selectedFragment = new HoursToMinutesFragment();
            } else if (id == R.id.nav_minutes_to_seconds) {
                selectedFragment = new MinutesToSecondsFragment();
            } else if (id == R.id.nav_seconds_to_hours) {
                selectedFragment = new SecondsToHoursFragment();
            } else if (id == R.id.nav_hours_to_days) {
                selectedFragment = new HoursToDaysFragment();
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, selectedFragment)
                        .commit();
                return true;
            }
            return false;
        });
    }
}
