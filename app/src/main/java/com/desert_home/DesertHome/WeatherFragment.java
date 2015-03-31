package com.desert_home.DesertHome;

/**
 * Created by dave on 3/3/2015.
 */
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

/**
 * This is the fragment
 */
public class WeatherFragment extends Fragment implements View.OnClickListener{

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

        ((Button)view.findViewById(R.id.wButtonLeaveLeft)).setOnClickListener(this);
        ((Button)view.findViewById(R.id.wButtonLeaveRight)).setOnClickListener(this);


        _fillItIn(view);
        return view;
    }

    public void onClick(View v) {
        SendDataToHouse sender = new SendDataToHouse();
        Animation swellAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.swell_up);
        switch (v.getId()) {
            case R.id.wButtonLeaveLeft:
            case R.id.wButtonLeaveRight:
                v.startAnimation(swellAnim);
                moveMe();
                break;
        }
    }


    private void moveMe(){
        WeatherFragment fragment;
        FragmentManager fm = getFragmentManager();
        fragment = (WeatherFragment) getFragmentManager().findFragmentByTag("weatherTag");
        GetDataFromHouse.weatherSetToVisible = false;
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.slide_in_from_right,
                R.animator.slide_out_to_right);
        fragmentTransaction.hide(fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState == null) {
        }
    }

    private void showGauges(View view){
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
        wwv1.setLayerType(WebView.LAYER_TYPE_HARDWARE, null);

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
        wwv2.setLayerType(WebView.LAYER_TYPE_HARDWARE, null);

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
        wwv3.setLayerType(WebView.LAYER_TYPE_HARDWARE, null);

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
        wwv4.setLayerType(WebView.LAYER_TYPE_HARDWARE, null);

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
        wwv5.setLayerType(WebView.LAYER_TYPE_HARDWARE, null);
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