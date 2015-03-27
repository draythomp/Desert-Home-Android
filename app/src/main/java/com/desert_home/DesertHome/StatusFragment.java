package com.desert_home.DesertHome;

/**
 * Created by dave on 3/3/2015.
 */
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by dave on 3/3/2015.
 */
public class StatusFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //Inflate the layout for this fragment
        View view = inflater.inflate(
                R.layout.status_fragment, container, false);
        TextView statusText = (TextView) view.findViewById(R.id.sStatus);
        fillItIn(view);
        return view;
    }
    private void _fillItIn(TextView statusText){
        statusText.setText("Last Contact: " + GetDataFromHouse.lastContact);
        statusText.append("\nReal Power is: " + GetDataFromHouse.rPower + " Watts");
        statusText.append("\nOutside Temperature is: " + GetDataFromHouse.oTemp + "\u00B0");
        statusText.append("\nInside Temperature is: " + GetDataFromHouse.iTemp + "\u00B0");
    }

    private void fillItIn(View view){
        TextView statusText;
        statusText = (TextView) view.findViewById(R.id.sStatus);
        _fillItIn(statusText);
    }

    public void fillItIn(){
        TextView statusText;
        statusText = (TextView) getView().findViewById(R.id.sStatus);
        _fillItIn(statusText);

    }
}