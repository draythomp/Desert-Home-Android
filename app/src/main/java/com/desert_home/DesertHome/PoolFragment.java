package com.desert_home.DesertHome;

/**
 * Created by dave on 3/3/2015.
 */
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

/**
 * This is the fragment
 */
public class PoolFragment extends Fragment implements View.OnClickListener{
    public TextView poolStatus;
    public TextView poolSettings;

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
                R.layout.pool_fragment, container, false);

        //((RadioGroup)view.findViewById(R.id.pRadioMotorGroup)).setOnClickListener(this);

        _fillItIn(view);
        return view;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pButtonLightSet:
                Log.v("DHInfo","Pool Light Set Button");
                //doLightDialog();
                break;
            case R.id.pButtonWaterfallSet:
                Log.v("DHInfo","Pool Waterfall Set Button");
                //doWaterfallDialog();
                break;
            case R.id.pButtonFountainSet:
                Log.v("DHInfo","Pool Fountain Set Button");
                //doFountainDialog();
                break;
        }
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState == null){
        }
    }
    private void _fillItIn(View view){
        RadioButton rb;
        String debug = GetDataFromHouse.poolMotor.toLowerCase();
        switch (debug){
            case "off":
                rb = (RadioButton) view.findViewById(R.id.pRadioMotorOff);
                rb.setChecked(true);
                break;
            case "low":
                rb = (RadioButton) view.findViewById(R.id.pRadioMotorLow);
                rb.setChecked(true);
                break;
            case "high":
                rb = (RadioButton) view.findViewById(R.id.pRadioMotorHigh);
                rb.setChecked(true);
                break;
            default:
                rb = (RadioButton) view.findViewById(R.id.pRadioMotorOff);
                rb.setChecked(true);
                break;
        }
        // Now set each of the switches
        Switch s;
        s = (Switch) view.findViewById(R.id.pButtonLightSet);
        if (GetDataFromHouse.poolLight.equalsIgnoreCase("on")){
            s.setChecked(true);
        } else {
            s.setChecked(false);
        }
        s = (Switch) view.findViewById(R.id.pButtonWaterfallSet);
        if (GetDataFromHouse.poolWaterfall.equalsIgnoreCase("on")){
            s.setChecked(true);
        } else {
            s.setChecked(false);
        }
        s = (Switch) view.findViewById(R.id.pButtonFountainSet);
        if (GetDataFromHouse.poolFountain.equalsIgnoreCase("on")){
            s.setChecked(true);
        } else {
            s.setChecked(false);
        }
        TextView t = (TextView) view.findViewById(R.id.pTemp);
        t.setText("Last Temperature Reading " + GetDataFromHouse.poolTemperature + "\u00B0");
    }

    public void fillItIn(){
       View view = getView().findViewById(R.id.poolLayout);
        _fillItIn(view);

    }
}

