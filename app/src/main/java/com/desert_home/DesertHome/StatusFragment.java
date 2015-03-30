package com.desert_home.DesertHome;

/**
 * Created by dave on 3/3/2015.
 */
import android.app.Fragment;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by dave on 3/3/2015.
 */
public class StatusFragment extends Fragment {
    private static View savedView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // I'm retaining the state to make it far easier
        // to keep using this fragment
        this.setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //Inflate the layout for this fragment
        View view = inflater.inflate(
                R.layout.status_fragment, container, false);
        savedView = view;
        _fillItIn(view);
        return view;
    }
    void showGauges(View view) {
        String url, whichOne;
        if(view.getResources().getBoolean(R.bool.isLandscape)){
            whichOne = "threegaugesland.html";
        } else {
            whichOne = "threegauges.html";
        }
        WebView www = (WebView) view.findViewById(R.id.sWebview);
        www.getSettings().setJavaScriptEnabled(true);
//        www.getSettings().setBuiltInZoomControls(true);
//        www.getSettings().setDisplayZoomControls(false);
        url = "file:///android_asset/" + whichOne + "?"
                + "temperature=" + GetDataFromHouse.oTemp
                + "&itemperature=" + GetDataFromHouse.iTemp
                + "&power=" + GetDataFromHouse.rPower;
        www.loadUrl(url);
        www.setBackgroundColor(Color.TRANSPARENT);
        www.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState){
        savedView = view;
        _fillItIn(view);
    }

    private void _fillItIn(View view){
        TextView statusText = (TextView) view.findViewById(R.id.sStatus);
        statusText.setText("Last Contact: " + GetDataFromHouse.lastContact);
        showGauges(view);
    }

    public void fillItIn(){
        _fillItIn(savedView);

    }
}