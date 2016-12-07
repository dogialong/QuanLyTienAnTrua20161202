package com.training.cst.quanlytienantrua.UserInterface.Activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.training.cst.quanlytienantrua.R;
import com.training.cst.quanlytienantrua.UserInterface.Fragment.FragmentDrawer;
import com.training.cst.quanlytienantrua.UserInterface.Fragment.FragmentFood;
import com.training.cst.quanlytienantrua.UserInterface.Fragment.FragmentOverview;
import com.training.cst.quanlytienantrua.UserInterface.Fragment.FragmentPay;
import com.training.cst.quanlytienantrua.UserInterface.Fragment.FragmentPayBasedOnFood;
import com.training.cst.quanlytienantrua.UserInterface.Fragment.FragmentPeople;
import com.training.cst.quanlytienantrua.UserInterface.Fragment.FragmentRecharge;
import com.training.cst.quanlytienantrua.UserInterface.Fragment.FragmentHistory;
import com.training.cst.quanlytienantrua.UserInterface.Fragment.FragmentWarning;

;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private static String TAG = MainActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    private TextView tvTiltToolbar;
    boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // check permission
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {Manifest.permission.READ_CONTACTS,
                Manifest.permission.WRITE_CONTACTS,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_SMS, Manifest.permission.CAMERA};

        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        tvTiltToolbar = (TextView)findViewById(R.id.toolbar_title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);
        // display the first navigation drawer view on app launch
        displayView(0);
        // animation
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
    // required  permissons.
    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    public void displayView(int position) {
        Fragment fragment = null;
        String title = null;
        switch (position) {
            case 0:
                fragment = new FragmentOverview();
                title = getString(R.string.title_overview);
                break;
            case 1:
                fragment = new FragmentPay();
                title = getString(R.string.title_pay);
                break;
            case 2:
                fragment = new FragmentPayBasedOnFood();
                title = getString(R.string.title_paybaseon);
                break;
            case 3:
                fragment = new FragmentRecharge();
                title = getString(R.string.title_recharge);
                break;
            case 4:
                fragment = new FragmentPeople();
                title = getString(R.string.title_people);
                break;
            case 5:
                fragment = new FragmentFood();
                title= getString(R.string.title_food);
                break;
            case 6:
                fragment = new FragmentWarning();
                title = getString(R.string.title_warning);
                break;
            case 7:
                fragment = new FragmentHistory();
                title = getString(R.string.title_logout);
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();
            tvTiltToolbar.setText(title);
        }
    }

    public void replaceFragment(Fragment fm,String titleFragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_body, fm);
        fragmentTransaction.commit();
        tvTiltToolbar.setText(titleFragment);
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}