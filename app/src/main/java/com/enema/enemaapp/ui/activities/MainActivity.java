package com.enema.enemaapp.ui.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.enema.enemaapp.R;
import com.enema.enemaapp.ui.fragments.AccountFragment;
import com.enema.enemaapp.ui.fragments.HomeFragment;
import com.enema.enemaapp.ui.fragments.MyCourseFragment;
import com.enema.enemaapp.ui.fragments.ReferAndEarnFragment;
import com.enema.enemaapp.ui.fragments.WishlistFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottamNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(MainActivity.this);
        bottomNavigationView.setSelectedItemId(R.id.home_menu_item);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;

        switch (menuItem.getItemId()){

            case R.id.home_menu_item :
                fragment = new HomeFragment();
                break;

            case R.id.wishlist_menu_item :
                fragment = new WishlistFragment();
                break;

            case R.id.mycourses_menu_item :
                fragment = new MyCourseFragment();
                break;

            case R.id.account_menu_item :
                fragment = new AccountFragment();
                break;


        }

        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmant_holder_FrameLayout, fragment)
                    .commit();
            return true;
        }

        return false;
    }
}
