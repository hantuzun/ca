package com.androidbelieve.drawerwithswipetabs;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity{
    public static FragmentManager fragmentManager;
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();
        /**
         *Setup the DrawerLayout and NavigationView
         */

         mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
         mNavigationView = (NavigationView) findViewById(R.id.navigationView) ;

        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the TabFragment as the first Fragment
         */

         mFragmentManager = getSupportFragmentManager();
         mFragmentTransaction = mFragmentManager.beginTransaction();
         mFragmentTransaction.replace(R.id.containerView,new TabFragment()).commit();
        /**
         * Setup click events on the Navigation View Items.
         */

         mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
             @Override
             public boolean onNavigationItemSelected(MenuItem menuItem) {
                 mDrawerLayout.closeDrawers();

                 if (menuItem.getItemId() == R.id.nav_date) {
                     FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                     fragmentTransaction.replace(R.id.containerView, new PickDateFragment()).commit();
                 }

                 else if (menuItem.getItemId() == R.id.nav_city) {
                     FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                     fragmentTransaction.replace(R.id.containerView, new SearchCityFragment()).commit();
                 }

                 else if (menuItem.getItemId() == R.id.nav_love) {
                     FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                     fragmentTransaction.replace(R.id.containerView, new PreferencesFragment()).commit();
                 }

                 else if (menuItem.getItemId() == R.id.nav_settings) {
                     FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                     fragmentTransaction.replace(R.id.containerView, new SettingsFragment()).commit();
                 }

                 else if (menuItem.getItemId() == R.id.nav_main) {
                    FragmentTransaction xfragmentTransaction = mFragmentManager.beginTransaction();
                    xfragmentTransaction.replace(R.id.containerView,new TabFragment()).commit();
                 }

                return false;
            }

        });

        /**
         * Setup Drawer Toggle of the Toolbar
         */

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, toolbar,R.string.app_name,
                R.string.app_name);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();

    }
    public void preferenceOnClick(View view){
        GridView gv = (GridView)findViewById(R.id.gridview);
        int pos = gv.getPositionForView( view);
        if( PreferencesFragment.allPreferences == null){
            //Precaution of nullptrException
            //Somethings are terribly wrong.
            return;
        }
        if( PreferencesFragment.allPreferences.get(pos).isSelected()){
            view.findViewById( R.id.preferenceTick).setVisibility(View.INVISIBLE);
            PreferencesFragment.allPreferences.get(pos).setIsSelected(false);
        }
        else{
            view.findViewById( R.id.preferenceTick).setVisibility(View.VISIBLE);
            PreferencesFragment.allPreferences.get(pos).setIsSelected(true);
        }

    }

    public void activityOnClick( View view){
        int pos = PrimaryFragment.getInstance().getListView().getPositionForView(view);
        if( view.getId() == R.id.activityRelativeLayout){
            Intent intent = new Intent(this, CogeqActivityViewActivity.class);
            intent.putExtra( "cogeqActivity", pos);
            startActivity(intent);
        }
        else if( view.getId() == R.id.crossImageView){
            PrimaryFragment.getInstance().getActivities().remove(pos);
            PrimaryFragment.getInstance().setListAdapter( PrimaryFragment.getInstance().getListAdapter());
        }
    }
}