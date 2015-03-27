package com.desert_home.DesertHome;

/**
 * Created by dave on 3/3/2015.
 */
import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.TreeMap;

/**
 * This is the fragment
 */
public class WeatherFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // I'm retaining the state to make it far easier
        // to keep using this fragment
        this.setRetainInstance(true);
    }

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //Inflate the layout for this fragment
        View view = inflater.inflate(
                R.layout.weather_fragment, container, false);
        _fillItIn(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState == null) {
        }
    }

    private void showGauges(View view){
        // This is just silly, There's no such thing as a static dictionary.
        // And they don't have anything except a \ for adding quotes to a string
        // So, I fell back to my old standby of a JSON string. I put it in the
        // string resources and read it out. This gives me the rough equivalent of
        // the python dictionary and I don't have to type \" for every stinking
        // string.
        //
        // Also, using their various maps would have required me to do a put() for
        // every single item every time I needed the darn thing. Silly.
/*        JSONObject jsonObject=null;
        float direction = 0f;
        float lastDirection = 0f;
       try {
            jsonObject = new JSONObject(getString(R.string.wCardinals));
            direction = (float)jsonObject.getDouble(GetDataFromHouse.windDirection);
            lastDirection = (float)jsonObject.getDouble(GetDataFromHouse.windDirectionLast.toString());
        } catch (JSONException je){
            Log.e("something horrible", "error in Write", je);
        } */

        Log.e("something horrible", "last "+GetDataFromHouse.windDirectionLast+" new "+ GetDataFromHouse.windDirection);

        String url;
        WebView wwv1 = (WebView) view.findViewById(R.id.wWebview1);
        wwv1.getSettings().setJavaScriptEnabled(true);
        url = "file:///android_asset/windspeed.html?"
                + "windspeed=" + GetDataFromHouse.windSpeed
                + "&winddirection=" + GetDataFromHouse.windDirection
                + "&winddirectionlast=" + GetDataFromHouse.windDirectionLast
                + "&humidity=" + GetDataFromHouse.humidity
                + "&rooftoptemp=" + GetDataFromHouse.roofTopTemp
                + "&pressure=" + GetDataFromHouse.barometricPressure
                + "&mpressure=" + GetDataFromHouse.mBarometricPressure;
        wwv1.loadUrl(url);
        wwv1.setBackgroundColor(Color.TRANSPARENT);
        wwv1.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);

        WebView wwv2 = (WebView) view.findViewById(R.id.wWebview2);
        wwv2.getSettings().setJavaScriptEnabled(true);
        url = "file:///android_asset/winddirection.html?"
                + "windspeed=" + GetDataFromHouse.windSpeed
                + "&winddirection=" + GetDataFromHouse.windDirection
                + "&winddirectionlast=" + GetDataFromHouse.windDirectionLast
                + "&humidity=" + GetDataFromHouse.humidity
                + "&rooftoptemp=" + GetDataFromHouse.roofTopTemp
                + "&pressure=" + GetDataFromHouse.barometricPressure
                + "&mpressure=" + GetDataFromHouse.mBarometricPressure;
        wwv2.loadUrl(url);
        wwv2.setBackgroundColor(Color.TRANSPARENT);
        wwv2.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);

        WebView wwv3 = (WebView) view.findViewById(R.id.wWebview3);
        wwv3.getSettings().setJavaScriptEnabled(true);
        url = "file:///android_asset/humidity.html?"
                + "windspeed=" + GetDataFromHouse.windSpeed
                + "&winddirection=" + GetDataFromHouse.windDirection
                + "&winddirectionlast=" + GetDataFromHouse.windDirectionLast
                + "&humidity=" + GetDataFromHouse.humidity
                + "&rooftoptemp=" + GetDataFromHouse.roofTopTemp
                + "&pressure=" + GetDataFromHouse.barometricPressure
                + "&mpressure=" + GetDataFromHouse.mBarometricPressure;
        wwv3.loadUrl(url);
        wwv3.setBackgroundColor(Color.TRANSPARENT);
        wwv3.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);

        WebView wwv4 = (WebView) view.findViewById(R.id.wWebview4);
        wwv4.getSettings().setJavaScriptEnabled(true);
        url = "file:///android_asset/temp.html?"
                + "windspeed=" + GetDataFromHouse.windSpeed
                + "&winddirection=" + GetDataFromHouse.windDirection
                + "&winddirectionlast=" + GetDataFromHouse.windDirectionLast
                + "&humidity=" + GetDataFromHouse.humidity
                + "&rooftoptemp=" + GetDataFromHouse.roofTopTemp
                + "&pressure=" + GetDataFromHouse.barometricPressure
                + "&mpressure=" + GetDataFromHouse.mBarometricPressure;
        wwv4.loadUrl(url);
        wwv4.setBackgroundColor(Color.TRANSPARENT);
        wwv4.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);

        WebView wwv5 = (WebView) view.findViewById(R.id.wWebview5);
        wwv5.getSettings().setJavaScriptEnabled(true);
        url = "file:///android_asset/barometer.html?"
                + "windspeed=" + GetDataFromHouse.windSpeed
                + "&winddirection=" + GetDataFromHouse.windDirection
                + "&winddirectionlast=" + GetDataFromHouse.windDirectionLast
                + "&humidity=" + GetDataFromHouse.humidity
                + "&rooftoptemp=" + GetDataFromHouse.roofTopTemp
                + "&pressure=" + GetDataFromHouse.barometricPressure
                + "&mpressure=" + GetDataFromHouse.mBarometricPressure;
        wwv5.loadUrl(url);
        wwv5.setBackgroundColor(Color.TRANSPARENT);
        wwv5.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
    }

    private void _fillItIn(View view){
        TextView settingsText = (TextView) view.findViewById(R.id.wSettings);
        settingsText.setText("None Yet");
        showGauges(view);
    }

    public void fillItIn(){
        View view = getView().findViewById(R.id.weatherLayout);
        _fillItIn(view);
    }
}