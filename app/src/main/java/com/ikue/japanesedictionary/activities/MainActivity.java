package com.ikue.japanesedictionary.activities;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ikue.japanesedictionary.R;
import com.ikue.japanesedictionary.fragments.FavouritesFragment;
import com.ikue.japanesedictionary.fragments.HistoryFragment;
import com.ikue.japanesedictionary.fragments.HomeFragment;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;
    private SearchView searchView;
    private MenuItem searchMenuItem;

    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // When false, the system sets the default values only if this method has never been called in the past
        PreferenceManager.setDefaultValues(getApplicationContext(), R.xml.preferences, false);

        // Get the shared preferences
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        // Add Toolbar to Main Screen
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_history:
                                switchFragment(new HistoryFragment());
                                return true;
                            case R.id.action_home:
                                switchFragment(new HomeFragment());
                                return true;
                            case R.id.action_favourites:
                                switchFragment(new FavouritesFragment());
                                return true;
                        }
                        return false;
                    }
                });

        setStartupPage();

        // Create Navigation drawer and inflate
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        // Add menu icon to Toolbar
        ActionBar supportActionBar = getSupportActionBar();
        if(supportActionBar != null) {
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white);
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Set behaviour of Navigation drawer
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Set item as checked
                item.setChecked(true);

                switch(item.getItemId()) {
                    case R.id.nav_history_fragment:
                        switchFragment(new HistoryFragment());
                        break;
                    case R.id.nav_home_fragment:
                        switchFragment(new HomeFragment());
                        break;
                    case R.id.nav_favourites_fragment:
                        switchFragment(new FavouritesFragment());
                        break;
                    case R.id.nav_settings_activity:
                        startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                        break;
                    case R.id.nav_about_activity:
                        startActivity(new Intent(MainActivity.this, AboutActivity.class));
                        break;
                    default:
                        break;
                }

                // Closing drawer on item click
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void setStartupPage() {
        int defaultStartupPage = Integer.parseInt(sharedPref.getString("pref_startupPage", "1"));
        switch(defaultStartupPage) {
            case 0:
                switchFragment(new HistoryFragment());
                break;
            case 1:
                switchFragment(new HomeFragment());
                break;
            case 2:
                switchFragment(new FavouritesFragment());
                break;
        }
    }

    private void switchFragment(Fragment newFragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, newFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflates menu and adds to action if present
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchMenuItem.getActionView();

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean queryTextFocused) {
                if(!queryTextFocused) {
                    // Close the SearchView when the user closes the keyboard
                    MenuItemCompat.collapseActionView(searchMenuItem);
                }
            }
        });

        ComponentName cn = new ComponentName(this, SearchResultActivity.class);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(cn));

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
        } else if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
