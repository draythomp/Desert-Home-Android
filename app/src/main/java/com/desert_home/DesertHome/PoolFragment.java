package com.desert_home.DesertHome;

/**
 * Created by dave on 3/3/2015.
 */
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

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

        ((Button) view.findViewById(R.id.pButtonSetMotor)).setOnClickListener(this);
        ((Button)view.findViewById(R.id.pButtonSetLight)).setOnClickListener(this);
        ((Button)view.findViewById(R.id.pButtonSetWaterfall)).setOnClickListener(this);
        ((Button)view.findViewById(R.id.pButtonSetFountain)).setOnClickListener(this);
        ((Button)view.findViewById(R.id.pButtonLeaveLeft)).setOnClickListener(this);
        ((Button)view.findViewById(R.id.pButtonLeaveRight)).setOnClickListener(this);

        _fillItIn(view);
        return view;
    }

    private void moveMe(){
        PoolFragment fragment;
        FragmentManager fm = getFragmentManager();
        fragment = (PoolFragment) getFragmentManager().findFragmentByTag("poolTag");
        GetDataFromHouse.poolSetToVisible = false;
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.slide_in_from_right,
                R.animator.slide_out_to_right);
        fragmentTransaction.hide(fragment);
        fragmentTransaction.commit();
    }

    public void onClick(View v) {
        SendDataToHouse sender = new SendDataToHouse();
        Animation swellAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.swell_up);
        Button s = null;
        switch (v.getId()) {
            case R.id.pButtonSetMotor:
                Log.v("DHInfo","Pool Motor Off Button");
                doMotorDialog();
                break;
            case R.id.pButtonSetLight:
                Log.v("DHInfo","Pool Light Set Button");
                doLightDialog();
                break;
            case R.id.pButtonSetWaterfall:
                Log.v("DHInfo","Pool Waterfall Set Button");
                doWaterfallDialog();
                break;
            case R.id.pButtonSetFountain:
                Log.v("DHInfo","Pool Fountain Set Button");
                doFountainDialog();
                break;
            case R.id.pButtonLeaveLeft:
            case R.id.pButtonLeaveRight:
                v.startAnimation(swellAnim);
                moveMe();
                break;
        }
    }

    private void doMotorDialog(){
        final String[] choices = {
                "Off",
                "Low",
                "High",
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Setting")
                .setItems(choices, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String action = "";
                        switch (choices[which]) {
                            case "Off":
                                action = getString(R.string.pMotorOff);
                                break;
                            case "Low":
                                action = getString(R.string.pMotorLow);
                                break;
                            case "High":
                                action = getString(R.string.pMotorHigh);
                                break;
                        }
                        Log.v("DHInfo", "Pool Motor Set to " + choices[which] + " " + action + "<");
                        SendDataToHouse sender = new SendDataToHouse();
                        sender.sendIt(getActivity(), getString(R.string.commandUrl),
                                action,
                                GetDataFromHouse.SecretWord);
                        dialog.cancel();
                    }
                });
        builder.create();
        builder.show();
    }
    private void doLightDialog(){
        final String[] choices = {
                "Off",
                "On",
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Setting")
                .setItems(choices, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String action ="";
                        switch(choices[which]){
                            case "Off":
                                action = getString(R.string.pLightOff);
                                break;
                            case "On":
                                action = getString(R.string.pLightOn);
                                break;
                        }
                        Log.v("DHInfo","Pool Light Set to " + choices[which] + " " + action + "<");
                        SendDataToHouse sender = new SendDataToHouse();
                        sender.sendIt(getActivity(),getString(R.string.commandUrl),
                                action,
                                GetDataFromHouse.SecretWord);
                        dialog.cancel();
                    }
                });
        builder.create();
        builder.show();
    }
    private void doWaterfallDialog(){
        final String[] choices = {
                "Off",
                "On",
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Setting")
                .setItems(choices, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String action ="";
                        switch(choices[which]){
                            case "Off":
                                action = getString(R.string.pWaterfallOff);
                                break;
                            case "On":
                                action = getString(R.string.pWaterfallOn);
                                break;
                        }
                        Log.v("DHInfo","Pool Motor Set to " + choices[which] + " " + action + "<");
                        SendDataToHouse sender = new SendDataToHouse();
                        sender.sendIt(getActivity(),getString(R.string.commandUrl),
                                action,
                                GetDataFromHouse.SecretWord);
                        dialog.cancel();
                    }
                });
        builder.create();
        builder.show();
    }
    private void doFountainDialog(){
        final String[] choices = {
                "Off",
                "On",
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Setting")
                .setItems(choices, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String action ="";
                        switch(choices[which]){
                            case "Off":
                                action = getString(R.string.pFountainOff);
                                break;
                            case "On":
                                action = getString(R.string.pFountainOn);
                                break;
                        }
                        Log.v("DHInfo","Pool Motor Set to " + choices[which] + " " + action + "<");
                        SendDataToHouse sender = new SendDataToHouse();
                        sender.sendIt(getActivity(),getString(R.string.commandUrl),
                                action,
                                GetDataFromHouse.SecretWord);
                        dialog.cancel();
                    }
                });
        builder.create();
        builder.show();
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState == null){
        }
    }
    private void _fillItIn(View view){
        Button b;
        // Now set each of the Buttons
        b = (Button) view.findViewById(R.id.pButtonSetMotor);
        b.setText(GetDataFromHouse.poolMotor);
        b = (Button) view.findViewById(R.id.pButtonSetLight);
        if (GetDataFromHouse.poolLight.equalsIgnoreCase("on")){
            b.setText("Light On");
        } else {
            b.setText("Light Off");
        }
        b = (Button) view.findViewById(R.id.pButtonSetWaterfall);
        if (GetDataFromHouse.poolWaterfall.equalsIgnoreCase("on")){
            b.setText("Waterfall On");
        } else {
            b.setText("Waterfall Off");
        }
        b = (Button) view.findViewById(R.id.pButtonSetFountain);
        if (GetDataFromHouse.poolFountain.equalsIgnoreCase("on")){
            b.setText("Fountain On");
        } else {
            b.setText("Fountain Off");
        }
        TextView t = (TextView) view.findViewById(R.id.pTemp);
        t.setText("Last Temperature Reading " + GetDataFromHouse.poolTemperature + "\u00B0");
    }

    public void fillItIn(){
       View view = getView().findViewById(R.id.poolLayout);
        _fillItIn(view);

    }
}

