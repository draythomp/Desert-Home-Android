package com.desert_home.DesertHome;

/**
 * Created by dave on 3/3/2015.
 */
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This is the fragment
 */
public class ThermoFragment extends Fragment implements View.OnClickListener{
    private String thermoName;
    private int lastImage = 0;

    @Override
     public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thermoName = this.getArguments().getString("Name");
        // I'm retaining the state to make it far easier
        // to keep using this fragment
        this.setRetainInstance(true);
     }

    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //Inflate the layout for this fragment
        View view = inflater.inflate(
                R.layout.thermo_fragment, container, false);
        lastImage = 0;

        ((Button)view.findViewById(R.id.tButtonTempSet)).setOnClickListener(this);
        ((Button)view.findViewById(R.id.tButtonModeSet)).setOnClickListener(this);
        ((Button)view.findViewById(R.id.tButtonFanSet)).setOnClickListener(this);

        _fillItIn(view);
        return view;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tButtonFanSet:
                Log.v("DHInfo", thermoName + "Fan Set Button");
                doFanDialog();
                break;
            case R.id.tButtonModeSet:
                Log.v("DHInfo", thermoName + "Mode Set Button");
                doModeDialog();
                break;
            case R.id.tButtonTempSet:
                Log.v("DHInfo", thermoName + "Temp Set Button");
                doTempDialog();
                break;
        }
    }

    private void doTempDialog(){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // set title
        alertDialogBuilder.setTitle("Change Temperature");
        View view = inflater.inflate(R.layout.temp_alert, null);
        final TextView value = (TextView) view.findViewById(R.id.tAlertTemp);
        final SeekBar seeker = (SeekBar) view.findViewById((R.id.tTempSeekBar));
        // First set up the seek bar

        int i = Integer.valueOf(_getTempSetting().substring(0, 2)) - 50;
        seeker.setProgress(i);
        seeker.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                //Do something here with new value
                int i = progress + 50;
                value.setText(Integer.toString(i));
            }

            public void onStartTrackingTouch(SeekBar arg0) {
                // TODO Auto-generated method stub
            }
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }
        });
        // set dialog message
        alertDialogBuilder.setView(view);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Set",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {
                // if this button is clicked,
                String gotback = value.getText().toString();
                Toast.makeText(getActivity(), "Setting Temp to " + gotback, Toast.LENGTH_LONG).show();
                SendDataToHouse sender = new SendDataToHouse();
                sender.sendIt(getActivity(),getString(R.string.commandUrl),
                    String.format(getString(R.string.tTemp),_getThermoCmdName()) + gotback,
                    getString(R.string.commandSecret));
                dialog.cancel();
            }
        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // if this button is clicked, just close
                // the dialog box and do nothing
                dialog.cancel();
            }
        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        value.setText(_getTempSetting().substring(0, 2));

        // show it
        alertDialog.show();
    }

    private void doFanDialog(){
        final String[] choices = {
                "On",
                "Auto",
                "Recirc",
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Fan Setting")
                .setItems(choices, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Setting Fan to " + choices[which], Toast.LENGTH_LONG).show();
                        String action ="";
                        switch(choices[which]){
                            case "On":
                                action = getString(R.string.tFanOn);
                                break;
                            case "Auto":
                                action = getString(R.string.tFanAuto);
                                break;
                            case "Recirc":
                                action = getString(R.string.tFanRecirc);
                                break;
                        }
                        SendDataToHouse sender = new SendDataToHouse();
                        sender.sendIt(getActivity(),getString(R.string.commandUrl),
                                String.format(action,_getThermoCmdName()),
                                getString(R.string.commandSecret));
                        dialog.cancel();
                    }
                });
        builder.create();
        builder.show();
    }

    private void doModeDialog(){
        final String[] choices = {
                "Off",
                "Cool",
                "Heat",
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select Setting")
                .setItems(choices, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String action ="";
                        switch(choices[which]){
                            case "Off":
                                action = getString(R.string.tModeOff);
                                break;
                            case "Cool":
                                action = getString(R.string.tModeCool);
                                break;
                            case "Heat":
                                action = getString(R.string.tModeHeat);
                                break;
                        }
                        SendDataToHouse sender = new SendDataToHouse();
                        sender.sendIt(getActivity(),getString(R.string.commandUrl),
                                String.format(action,_getThermoCmdName()),
                                getString(R.string.commandSecret));
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


    public void _fillItIn(View view){
        TextView locationText = (TextView) view.findViewById(R.id.tLabel);
        TextView tempText = (TextView) view.findViewById(R.id.tTempSpot);
        ImageButton tactivity = (ImageButton) view.findViewById(R.id.tButtonActivitySpot);
        Button tempSetting = (Button) view.findViewById(R.id.tButtonTempSet);
        Button modeSetting = (Button) view.findViewById(R.id.tButtonModeSet);
        Button fanSetting = (Button) view.findViewById(R.id.tButtonFanSet);
        locationText.setText(thermoName + " Thermostat");
        tempText.setText(_getTemp());
        tempSetting.setText(_getTempSetting());
        modeSetting.setText(_getModeSetting());
        fanSetting.setText(_getFanSetting());
        //activityText.setText(_getActivity());
        if (_getActivity().equalsIgnoreCase("heating")) {
            if (lastImage != R.drawable.fanred0) {
                tactivity.setImageResource(R.drawable.fanred0);
                lastImage = R.drawable.fanred0;
                tactivity.startAnimation(
                        AnimationUtils.loadAnimation(getActivity(), R.anim.fananimate));
            }
        }
        else if (_getActivity().equalsIgnoreCase("recirc")) {
            if (lastImage != R.drawable.fangreen0) {
                tactivity.setImageResource(R.drawable.fangreen0);
                lastImage = R.drawable.fangreen0;
                tactivity.startAnimation(
                        AnimationUtils.loadAnimation(getActivity(), R.anim.fananimate));
            }
        }
        else if (_getActivity().equalsIgnoreCase("cooling")) {
            if (lastImage != R.drawable.fanblue0) {
                lastImage = R.drawable.fanblue0;
                tactivity.setImageResource(R.drawable.fanblue0);
                tactivity.startAnimation(
                        AnimationUtils.loadAnimation(getActivity(), R.anim.fananimate));
            }
        }
        else {
            if (lastImage != R.drawable.fanblack) {
                tactivity.setImageResource(R.drawable.fanblack);
                lastImage = R.drawable.fanblack;
                tactivity.clearAnimation();
            }
        }
    }

    private String _getTempSetting(){
        if (thermoName == "North") return(GetDataFromHouse.nThermoTempSetting + "\u00B0");
        else return(GetDataFromHouse.sThermoTempSetting + "\u00B0");
    }
    private String _getModeSetting(){
        if (thermoName == "North") return(GetDataFromHouse.nThermoModeSetting);
        else return(GetDataFromHouse.sThermoModeSetting);
    }
    private String _getFanSetting(){
        if (thermoName == "North") return(GetDataFromHouse.nThermoFanSetting);
        else return(GetDataFromHouse.sThermoFanSetting);
    }
    private String _getTemp(){
        if (thermoName == "North") return(GetDataFromHouse.nThermoCtemp + "\u00B0");
        else return(GetDataFromHouse.sThermoCtemp + "\u00B0");
    }
    private String _getActivity(){
        if (thermoName == "North") return(GetDataFromHouse.nThermoActivity);
        else return(GetDataFromHouse.sThermoActivity);
    }
    private String _getThermoCmdName(){
        if (thermoName == "North") return("nthermo");
        else return("sthermo");
    }


    public void fillItIn(){
        View view = getView().findViewById(R.id.thermoLayout);
        _fillItIn(view);

    }
}

