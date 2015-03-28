package com.desert_home.DesertHome;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class GetDataFromHouse extends Service {
    public static JSONObject houseData = new JSONObject();
    private static Handler mHandler;
    private int mInterval = 15000; // 15 seconds by default, can be changed later

    // Global data obtained from the house that will be used all over the place
    // Note: These cannot be held inside the visible fragments, when they get recreated,
    // they would disappear and mess up. Things like rotation or unhiding them.

    // First, the major status values of Instantaneous Real Power usage, Average inside
    // temperature and Outside Temperature
    public static String rPower = "Wait";
    public static String iTemp = "Wait";
    public static String oTemp = "Wait";

    public static String nThermoCtemp = "Wait";
    public static String nThermoActivity = "Wait";
    public static String nThermoFanSetting = "Wait";
    public static String nThermoModeSetting = "Wait";
    public static String nThermoTempSetting = "Wait";

    public static String sThermoCtemp = "Wait";
    public static String sThermoActivity = "Wait";
    public static String sThermoFanSetting = "Wait";
    public static String sThermoModeSetting = "Wait";
    public static String sThermoTempSetting = "Wait";
    // The swimming pool data and control status
    public static String poolMotor = "Wait";
    public static String poolWaterfall = "Wait";
    public static String poolLight = "Wait";
    public static String poolFountain = "Wait";
    public static String poolTemperature = "Wait";
    public static String poolSolar = "Wait";
    // items fromm the garage controller
    public static String garageDoor1 = "Wait";
    public static String garageDoor2 = "Wait";
    public static String waterHeaterPower = "Wait";
    // The wemo controlled lights
    public static String lightFrontPorch = "Wait";
    public static String lightOutsideGarage = "Wait";
    public static String lightCactusSpot = "Wait";
    public static String lightWestPatio = "Wait";
    // This doesn't seem to fit anywhere
    public static String septicTankLevel = "Wait";
    // Weather Station readings from Acurite 5n1
    public static String windSpeed = "Wait";
    public static Float windDirection = 0f;
    public static Float windDirectionLast = 0f;
    public static String humidity = "Wait";
    public static String roofTopTemp = "Wait";
    public static String aBarometricPressure = "Wait";
    // barometric pressure from my fencepost device
    public static String barometricPressure = "Wait";
    public static String mBarometricPressure= "Wait";

    public static String lastContact = "Not Yet";

    // These are for controlling the display when hidden
    // or rotated.
    public static Boolean nThermoSetToVisible = false;
    public static Boolean sThermoSetToVisible = false;
    public static Boolean poolSetToVisible = false;
    public static Boolean garageSetToVisible = false;
    public static Boolean lightsSetToVisible = false;
    public static Boolean weatherSetToVisible = false;
    public static Boolean presetsSetToVisible = false;


    public GetDataFromHouse() {
    }

    // Have to return START_NOT_STICKY or else the systen
    // restarts the service when it destroys it. That seems silly,
    // but ...
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("DHInfo", "onStartCommand called starting web get");
        super.onStartCommand(intent,flags,startId);
        mHandler = new Handler(); //This is for the web update task
        mStatusChecker.run();
        // if it is started any other way, it hangs around after leaving
        // the app.
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    // This will run the update from the web every "mInterval" seconds
    // It's fun to watch the items change at the house over the phone
    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            new downloadWebpageTask().execute(getString(R.string.homeUrl));
            mHandler.postDelayed(mStatusChecker, mInterval);
        }
    };

    public static void stopCheck(){
        Log.v("DHInfo", "stopCheck called disabling web get");
        mHandler.removeCallbacksAndMessages(null);
    }

    // Here is the stuff to get the web data from the house
    // It runs in the background so you can push buttons to
    // your heart's content and still get updates
    private class downloadWebpageTask extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            // Setup is done here
        }

        protected void onProgressUpdate() {
            // Maybe update a progress bar here
        }

        protected void onCancelled() {
            // What happens if it is cancelled
        }

        protected void onPostExecute(String result) {
            // Post processing
            // here I update the various fields for the house devices.
            if(result != null) {
                Context context = getApplicationContext();
                CharSequence text = "Updated";
                int duration = Toast.LENGTH_SHORT;

                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                lastContact = df.format(c.getTime());
                // show a short message to keep me informed
                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                try {
                    houseData = new JSONObject(result);
                    // Fill in the global variables first
                    rPower = houseData.getString("power");
                    oTemp = houseData.getString("outsidetemp");
                    iTemp = String.valueOf( (Integer.valueOf(houseData.getString("ntt")) +
                            Integer.valueOf(houseData.getString("stt"))) / 2);
                    // Thermostat items
                    //  Status First
                    nThermoCtemp = houseData.getString("ntt");
                    nThermoActivity = houseData.getString("ntm");
                    // Now settings
                    nThermoFanSetting = houseData.getString("ntfs");
                    nThermoModeSetting = houseData.getString("ntms");
                    nThermoTempSetting = houseData.getString("ntts");
                    // Status
                    sThermoCtemp = houseData.getString("stt");
                    sThermoActivity = houseData.getString("stm");
                    // Settings
                    sThermoFanSetting = houseData.getString("stfs");
                    sThermoModeSetting = houseData.getString("stms");
                    sThermoTempSetting = houseData.getString("stts");
                    // The swimming pool data and control status
                    poolMotor = houseData.getString("pm");
                    poolWaterfall = houseData.getString("pw");
                    poolLight = houseData.getString("pl");
                    poolFountain = houseData.getString("pf");
                    poolTemperature = houseData.getString("pt");
                    poolSolar = houseData.getString("ps");
                    // items fromm the garage controller
                    garageDoor1 = houseData.getString("gd1");
                    garageDoor2 = houseData.getString("gd2");
                    waterHeaterPower = houseData.getString("wh");
                    // The wemo controlled lights
                    lightFrontPorch = houseData.getString("lfp");
                    lightOutsideGarage = houseData.getString("log");
                    lightCactusSpot = houseData.getString("lcs");
                    lightWestPatio = houseData.getString("lp");
                    // This doesn't seem to fit anywhere
                    septicTankLevel = houseData.getString("stl");
                    // Weather Station readings from Acurite 5n1
                    windSpeed = houseData.getString("ws");

                    // wind direction is weird
                    JSONObject jsonObject=null;
                    float temp = 0f;
                    try {
                        jsonObject = new JSONObject(context.getString(R.string.wCardinals));
                        temp = (float)jsonObject.getDouble(houseData.getString("wd"));
                    } catch (JSONException je){
                        Log.e("something horrible", "error in Write", je);
                    }
                    if (!windDirection.equals(temp))
                        windDirectionLast = new Float(windDirection); // save the last reading
                    windDirection = temp;
                    Log.e("something horrible", "last "+windDirectionLast+" new "+ windDirection);

                    humidity = houseData.getString("hy");
                    roofTopTemp = houseData.getString("rtt");
                    // barometric pressure from my fencepost device
                    barometricPressure = houseData.getString("bp");
                    // and its midnight reading
                    mBarometricPressure = houseData.getString("mb");
                    // Update the status on the screen
                    // Starting with the status area
                    MainActivity.status.fillItIn();
                    // Update the two thermostats
                    //  First the North one
                    MainActivity.nthermo.fillItIn();

                    //  Now the South one
                    MainActivity.sthermo.fillItIn();

                    // and the pool
                    MainActivity.pool.fillItIn();

                    // the garage (including septic tank)
                    MainActivity.garage.fillItIn();

                    // the various lights
                    MainActivity.lights.fillItIn();

                    // the weather station readings
                    MainActivity.weather.fillItIn();

                } catch (org.json.JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        // the actual work to get the data is done here
        @Override
        protected String doInBackground(String... params) {
            InputStream is = null;
            // Only display the first 500 characters of the retrieved
            // web page content.
            int len = 500;

            try {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                int response = conn.getResponseCode();
                Log.v(getResources().getString(R.string.Debug), "The response is: " + response);
                if (response == 200) {
                    is = conn.getInputStream();

                    // Convert the InputStream into a string
                    return getString(is, len);
                }
                // Makes sure that the InputStream is closed after the app is
                // finished using it.
            } catch(IOException e){
                e.printStackTrace();
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch(IOException e){
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }

        // suck up the data coming back from the URL here
        private String getString(InputStream stream, int len) throws IOException {
            Reader reader = null;
            reader = new InputStreamReader(stream, "UTF-8");
            char[] buffer = new char[len];
            reader.read(buffer);
            return new String(buffer);
        }
    }
}
