package com.training.cst.quanlytienantrua.UserInterface.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.training.cst.quanlytienantrua.R;
import com.training.cst.quanlytienantrua.UserInterface.Fragment.FragmentDrawer;
import com.training.cst.quanlytienantrua.UserInterface.Fragment.FragmentLogout;
import com.training.cst.quanlytienantrua.UserInterface.Fragment.FragmentOverview;
import com.training.cst.quanlytienantrua.UserInterface.Fragment.FragmentPay;
import com.training.cst.quanlytienantrua.UserInterface.Fragment.FragmentPeople;
import com.training.cst.quanlytienantrua.UserInterface.Fragment.FragmentRecharge;
import com.training.cst.quanlytienantrua.UserInterface.Fragment.FragmentWarning;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    private static String TAG = MainActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private DrawerLayout drawerFragment;
    private TextView tvTiltToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        tvTiltToolbar = (TextView) findViewById(R.id.toolbar_title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        drawerFragment = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
//        drawerFragment.setDrawerListener(this);

        // display the first navigation drawer view on app launch
        displayView(0);
        final View leftmenu = (View) findViewById(R.id.left_menu);
        findViewById(R.id.open_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerFragment.openDrawer(leftmenu);
            }
        });
    }

    public void closeMenu(){
        drawerFragment.closeDrawer(Gravity.LEFT);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        if(id == R.id.action_search){
//            Toast.makeText(getApplicationContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

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
                fragment = new FragmentRecharge();
                title = getString(R.string.title_recharge);
                break;
            case 3:
                fragment = new FragmentPeople();
                title = getString(R.string.title_people);
                break;
            case 4:
                fragment = new FragmentWarning();
                title = getString(R.string.title_warning);
                break;
            case 5:
                fragment = new FragmentLogout();
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

            // set the toolbar title
            tvTiltToolbar.setText(title);
        }
    }

    public void replaceFragment(Fragment fm){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container_body, fm);
        fragmentTransaction.commit();
    }
}