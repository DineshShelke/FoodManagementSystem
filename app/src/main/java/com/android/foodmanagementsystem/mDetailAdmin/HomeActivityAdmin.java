package com.android.foodmanagementsystem.mDetailAdmin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.foodmanagementsystem.MainActivity;
import com.android.foodmanagementsystem.R;
import com.android.foodmanagementsystem.mDetailHotel.DraftsFragment;
import com.android.foodmanagementsystem.mDetailHotel.SentFragment;
import com.android.foodmanagementsystem.mDetailHotel.TabFragment;

public class HomeActivityAdmin extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FragmentManager FM;
    FragmentTransaction FT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        FM = getSupportFragmentManager();
       // FM.addOnBackStackChangedListener(this);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        navigationView= (NavigationView) findViewById(R.id.shitstuff);

        FM = getSupportFragmentManager();
        FT= FM.beginTransaction();
        FT.replace(R.id.adminContainerView, new AdminTabFragment())
                .commit();

       /* getSupportFragmentManager().beginTransaction()
                .add(new AdminTabFragment(),"details")
                .disallowAddToBackStack()
                .commit();*/


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawers();

                if (item.getItemId()== R.id.nav_item_sent) {
                    FragmentTransaction fragmentTransaction= FM.beginTransaction();
                    fragmentTransaction.replace(R.id.adminContainerView, new AdminStockFragment());
                    fragmentTransaction.addToBackStack("AdminFrag");
                    fragmentTransaction .commit();
                }

                if (item.getItemId()== R.id.nav_item_inbox) {
                    FragmentTransaction fragmentTransaction1=FM.beginTransaction();
                        fragmentTransaction1.replace(R.id.adminContainerView,new AdminTabFragment());
                        fragmentTransaction1.addToBackStack("AdminTab");
                        fragmentTransaction1.commit();
                }

                if (item.getItemId()== R.id.nav_item_draft) {
                    FragmentTransaction fragmentTransaction= FM.beginTransaction();
                    fragmentTransaction.replace(R.id.adminContainerView, new AdminAcceptFragment());
                    fragmentTransaction.addToBackStack("AdminTab");
                    fragmentTransaction.commit();
                }
                return false;
            }
        });

        android.support.v7.widget.Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        ActionBarDrawerToggle toggle= new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.admin_home, menu);
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

   @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() == 0){
            startActivity(new Intent(HomeActivityAdmin.this,AdminLoginActivity.class));
        } else {
            super.onBackPressed();
        }
    }
}
