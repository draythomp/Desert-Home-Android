package com.desert_home.DesertHome;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    public final static String EXTRA_MESSAGE = "com.desert_home.DesertHome.MESSAGE";
    static public StatusFragment status;
    static public ThermoFragment nthermo;
    static public ThermoFragment sthermo;
    static public PoolFragment pool;
    static public GarageFragment garage;
    static public LightsFragment lights;
    static public WeatherFragment weather;
    static public PresetsFragment presets;

    static int oldrotation = 99; // to keep track of rotation changes

    public MainActivity(){}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Fragment testFragment;
        FragmentManager fm;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        SharedPreferences settings = getSharedPreferences("Stuff", MODE_PRIVATE);
        GetDataFromHouse.SecretWord = (String) settings.getString("secret", "");

        Log.v("DHInfo", "MainActivity onCreate");
        status = (StatusFragment) getFragmentManager().findFragmentById(R.id.status_place);
        if(savedInstanceState == null) {
            fm = getFragmentManager();
            fm.executePendingTransactions();
            FragmentTransaction fragmentTransaction;
            // Getting this fragment will tell me if the various fragments have been
            // created already
            testFragment = (ThermoFragment)getFragmentManager().findFragmentByTag("nthermoTag");
            fm.executePendingTransactions();

            Context context = getApplicationContext();
            CharSequence text = "butthead";
            int duration = Toast.LENGTH_SHORT;
            // show a short message to keep me informed
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            if (testFragment == null){
                presets = new PresetsFragment();
                fragmentTransaction = fm.beginTransaction();
                if (GetDataFromHouse.presetsSetToVisible)
                    fragmentTransaction.show(presets);
                else
                    fragmentTransaction.hide(presets);
                fragmentTransaction.add(R.id.fragmentContainer, presets, "presetsTag");
                fragmentTransaction.commit();

            // create two fragments for the two thermostats
            // but first I have to add the name of the thermostat to
            // to the context so it knows who it is
                Bundle nBundle = new Bundle();
                String myMessage = "North";
                nBundle.putString("Name", myMessage);
                nthermo = new ThermoFragment();
                nthermo.setArguments(nBundle);
                // Now do exactly the same thing for the other one
                // Now, finally, add them to the screen
                // But I add them hidden so they can be shown individually
                // by choices from the options menu
                fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.add(R.id.fragmentContainer, nthermo, "nthermoTag");
                if (GetDataFromHouse.nThermoSetToVisible)
                    fragmentTransaction.show(nthermo);
                else
                    fragmentTransaction.hide(nthermo);
                fragmentTransaction.commit();
            }

            // Now the other thermostat
            Bundle sBundle = new Bundle();
            String myMessage = "South";
            sBundle.putString("Name", myMessage);
            sthermo = new ThermoFragment();
            sthermo.setArguments(sBundle);
            fragmentTransaction = fm.beginTransaction();
            if (GetDataFromHouse.sThermoSetToVisible)
                fragmentTransaction.show(sthermo);
            else
                fragmentTransaction.hide(sthermo);
            fragmentTransaction.add(R.id.fragmentContainer, sthermo, "sthermoTag");
            fragmentTransaction.commit();

            // now a fragment for the pool
            pool = new PoolFragment();
            fragmentTransaction = fm.beginTransaction();
            if (GetDataFromHouse.poolSetToVisible)
                fragmentTransaction.show(pool);
            else
                fragmentTransaction.hide(pool);
            fragmentTransaction.add(R.id.fragmentContainer, pool, "poolTag");
            fragmentTransaction.commit();

            // the garage
            garage = new GarageFragment();
            fragmentTransaction = fm.beginTransaction();
            if (GetDataFromHouse.garageSetToVisible)
                fragmentTransaction.show(garage);
            else
                fragmentTransaction.hide(garage);
            fragmentTransaction.add(R.id.fragmentContainer, garage, "garageTag");
            fragmentTransaction.commit();

            // the various lights
            lights = new LightsFragment();
            fragmentTransaction = fm.beginTransaction();
            if (GetDataFromHouse.lightsSetToVisible)
                fragmentTransaction.show(lights);
            else
                fragmentTransaction.hide(lights);
            fragmentTransaction.add(R.id.fragmentContainer, lights, "lightsTag");
            fragmentTransaction.commit();

            // the weather station
            weather = new WeatherFragment();
            fragmentTransaction = fm.beginTransaction();
            if (GetDataFromHouse.weatherSetToVisible)
                fragmentTransaction.show(weather);
            else
                fragmentTransaction.hide(weather);
            fragmentTransaction.add(R.id.fragmentContainer, weather, "weatherTag");
            fragmentTransaction.commit();

            // If the network is available, set up a recurring task to
            // get the data
            if (networkIsAvailable()) {
                Log.v(getString(R.string.Debug), "Set up recurring http get for data");
                // start and trigger a service to read house data
                Intent i = new Intent(this, GetDataFromHouse.class);
                startService(i);
            } else {
                    Log.v(getString(R.string.Debug), "Network not available");
            }
            oldrotation = getWindowManager().getDefaultDisplay()
                    .getRotation();
        }

        Log.v(getString(R.string.Debug), "On to the next step");
    }

    // This is here to stop the web update if
    // the user clicks on the back button or kills
    // the app
    @Override
    protected void onPause() {
        super.onPause();
        Log.v("DHInfo", "onPause called in main");
        // Now I have to decide if it's an actual pause or just a
        // change of rotation
        int rotation = getWindowManager().getDefaultDisplay()
                .getRotation();
        if (oldrotation != rotation){
            // It's just a change of rotation, so don't stop the web get
            Log.v("DHInfo", "Rotation changed from " + String.valueOf(rotation) +
                            " to " + String.valueOf(oldrotation));
            oldrotation = rotation;
            // Now check which fragments were visible and restore them
            // also.
            FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            if (GetDataFromHouse.nThermoSetToVisible) {
                ThermoFragment fragment = (ThermoFragment) fm.findFragmentByTag("nthermoTag");
                fragmentTransaction.show(fragment);
            }
            if (GetDataFromHouse.sThermoSetToVisible) {
                ThermoFragment fragment = (ThermoFragment) fm.findFragmentByTag("sthermoTag");
                fragmentTransaction.show(fragment);
            }
            if (GetDataFromHouse.poolSetToVisible){
                PoolFragment fragment = (PoolFragment) fm.findFragmentByTag("poolTag");
                fragmentTransaction.show(fragment);
            }
            if (GetDataFromHouse.garageSetToVisible){
                GarageFragment fragment = (GarageFragment) fm.findFragmentByTag("garageTag");
                fragmentTransaction.show(fragment);
            }
            if (GetDataFromHouse.lightsSetToVisible){
                LightsFragment fragment = (LightsFragment) fm.findFragmentByTag("lightsTag");
                fragmentTransaction.show(fragment);
            }
            if (GetDataFromHouse.weatherSetToVisible){
                WeatherFragment fragment = (WeatherFragment) fm.findFragmentByTag("weatherTag");
                fragmentTransaction.show(fragment);
            }
            if (GetDataFromHouse.presetsSetToVisible){
                PresetsFragment fragment = (PresetsFragment) fm.findFragmentByTag("presetsTag");
                fragmentTransaction.show(fragment);
            }

            fragmentTransaction.commit();
            return;
        }
        // This boolean stops the web get from being called in GetDataFromHouse
        // When onPause is called, the application is obscured (not visible) or
        // being stopped by the OS. That means web gets are useless, and just
        // waste data usage. The async task is still running periodically, and
        // setting the boolean to false will allow it to start again
        GetDataFromHouse.isPaused = true;
        Toast toast = Toast.makeText(getApplicationContext(), "Update Stopped", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        // see the note in this class about onPause to understand this boolean
        GetDataFromHouse.isPaused = false;
        Toast toast = Toast.makeText(getApplicationContext(), "Update resumed", Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (GetDataFromHouse.nThermoSetToVisible)
            menu.findItem(R.id.action_northThermostat).setTitle('\u2714' + getString(R.string.action_northThermostat));
        else
            menu.findItem(R.id.action_northThermostat).setTitle(getString(R.string.action_northThermostat));
        if (GetDataFromHouse.sThermoSetToVisible)
            menu.findItem(R.id.action_southThermostat).setTitle('\u2714' + getString(R.string.action_southThermostat));
        else
            menu.findItem(R.id.action_southThermostat).setTitle(getString(R.string.action_southThermostat));
        if (GetDataFromHouse.poolSetToVisible)
            menu.findItem(R.id.action_pool).setTitle('\u2714' + getString(R.string.action_pool));
        else
            menu.findItem(R.id.action_pool).setTitle(getString(R.string.action_pool));
        if (GetDataFromHouse.garageSetToVisible)
            menu.findItem(R.id.action_garage).setTitle('\u2714' + getString(R.string.action_garage));
        else
            menu.findItem(R.id.action_garage).setTitle(getString(R.string.action_garage));
        if (GetDataFromHouse.lightsSetToVisible)
            menu.findItem(R.id.action_lights).setTitle('\u2714' + getString(R.string.action_lights));
        else
            menu.findItem(R.id.action_lights).setTitle(getString(R.string.action_lights));
        if (GetDataFromHouse.presetsSetToVisible)
            menu.findItem(R.id.action_presets).setTitle('\u2714' + getString(R.string.action_presets));
        else
            menu.findItem(R.id.action_presets).setTitle(getString(R.string.action_presets));
        if (GetDataFromHouse.weatherSetToVisible)
            menu.findItem(R.id.action_weather).setTitle('\u2714' + getString(R.string.action_weather));
        else
            menu.findItem(R.id.action_weather).setTitle(getString(R.string.action_weather));
        super.onPrepareOptionsMenu(menu);
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
        else if (id == R.id.action_northThermostat){
            Log.v(getString(R.string.Debug), "North thermostat chosen");
            FragmentManager fm = getFragmentManager();
            ThermoFragment fragment = (ThermoFragment) getFragmentManager().findFragmentByTag("nthermoTag");
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.animator.slide_in_from_right,
                    R.animator.slide_out_to_right);
            if(fragment.isHidden()) {
                fragmentTransaction.show(fragment);
                GetDataFromHouse.nThermoSetToVisible = true;

            } else {
                fragmentTransaction.hide(fragment);
                GetDataFromHouse.nThermoSetToVisible = false;
            }
            fragmentTransaction.commit();
            return true;
        }
        else if (id == R.id.action_southThermostat){
            Log.v(getString(R.string.Debug), "South thermostat chosen");
            FragmentManager fm = getFragmentManager();
            ThermoFragment fragment = (ThermoFragment) getFragmentManager().findFragmentByTag("sthermoTag");
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.animator.slide_in_from_right,
                    R.animator.slide_out_to_right);
            if(fragment.isHidden()) {
                fragmentTransaction.show(fragment);
                GetDataFromHouse.sThermoSetToVisible = true;
            } else {
                fragmentTransaction.hide(fragment);
                GetDataFromHouse.sThermoSetToVisible = false;
            }
            fragmentTransaction.commit();
            return true;
        }
        else if (id == R.id.action_pool){
            Log.v(getString(R.string.Debug), "Pool chosen");
            FragmentManager fm = getFragmentManager();
            PoolFragment fragment = (PoolFragment) getFragmentManager().findFragmentByTag("poolTag");
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.animator.slide_in_from_right,
                    R.animator.slide_out_to_right);
            if(fragment.isHidden()) {
                fragmentTransaction.show(fragment);
                GetDataFromHouse.poolSetToVisible = true;
            } else {
                fragmentTransaction.hide(fragment);
                GetDataFromHouse.poolSetToVisible = false;
            }
            fragmentTransaction.commit();
            return true;
        }
        else if (id == R.id.action_garage){
            Log.v(getString(R.string.Debug), "Garage chosen");
            FragmentManager fm = getFragmentManager();
            GarageFragment fragment = (GarageFragment) getFragmentManager().findFragmentByTag("garageTag");
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.animator.slide_in_from_right,
                    R.animator.slide_out_to_right);
            if(fragment.isHidden()) {
                fragmentTransaction.show(fragment);
                GetDataFromHouse.garageSetToVisible = true;
            } else {
                fragmentTransaction.hide(fragment);
                GetDataFromHouse.garageSetToVisible = false;
            }
            fragmentTransaction.commit();
            return true;
        }
        else if (id == R.id.action_lights){
            Log.v(getString(R.string.Debug), "Lights chosen");
            FragmentManager fm = getFragmentManager();
            LightsFragment fragment = (LightsFragment) getFragmentManager().findFragmentByTag("lightsTag");
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.animator.slide_in_from_right,
                    R.animator.slide_out_to_right);
            if(fragment.isHidden()) {
                fragmentTransaction.show(fragment);
                GetDataFromHouse.lightsSetToVisible = true;
            } else {
                fragmentTransaction.hide(fragment);
                GetDataFromHouse.lightsSetToVisible = false;
            }
            fragmentTransaction.commit();
            return true;
        }
        else if (id == R.id.action_weather){
            Log.v(getString(R.string.Debug), "Weather chosen");
            FragmentManager fm = getFragmentManager();
            WeatherFragment fragment = (WeatherFragment) getFragmentManager().findFragmentByTag("weatherTag");
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.animator.slide_in_from_right,
                    R.animator.slide_out_to_right);
            if(fragment.isHidden()) {
                fragmentTransaction.show(fragment);
                GetDataFromHouse.weatherSetToVisible = true;
            } else {
                fragmentTransaction.hide(fragment);
                GetDataFromHouse.weatherSetToVisible = false;
            }
            fragmentTransaction.commit();
            return true;
        }
        else if (id == R.id.action_presets){
            Log.v(getString(R.string.Debug), "Presets chosen");
            FragmentManager fm = getFragmentManager();
            PresetsFragment fragment = (PresetsFragment) getFragmentManager().findFragmentByTag("presetsTag");
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.animator.slide_in_from_right,
                    R.animator.slide_out_to_right);
            if(fragment.isHidden()) {
                fragmentTransaction.show(fragment);
                GetDataFromHouse.presetsSetToVisible = true;
            } else {
                fragmentTransaction.hide(fragment);
                GetDataFromHouse.presetsSetToVisible = false;
            }
            fragmentTransaction.commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showToast(final String toast)
    {
        runOnUiThread(new Runnable() {
            public void run()
            {
                Toast.makeText(MainActivity.this, toast, Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Check to see if the network is available. Not much point to
    // continuing if you can't communicate
    private boolean networkIsAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

}
