package com.desert_home.DesertHome;

import android.app.Activity;
import android.app.Fragment;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SendDataToHouse {
    // The context has to be passed in so this can display things
    private static Context myinstance;

    public void sendIt(Context context, String... command) {
        // The context here is so a Toast can be presented when
        // command is sent.
        myinstance = context;
        new downloadWebpageTask().execute(command);
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
            Toast.makeText(myinstance, "Sent Command", Toast.LENGTH_SHORT).show();

            if(result != null) {
                // show a short message to keep me informed
                Log.v("DHInfo", "Command send completed" );
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
                URL url = new URL(params[0] + "?" + params[1] + "&" + params[2] + '\n');
                Log.v("DHInfo", url.toString() );
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                int response = conn.getResponseCode();
                Log.v("DHInfo", "The response is: " + response);
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
