package com.desert_home.DesertHome;

/**
 * Created by dave on 3/3/2015.
 */
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * This is the fragment
 */
public class GarageFragment extends Fragment {
    public TextView garageStatus;
    public TextView garageSettings;

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
                R.layout.garage_fragment, container, false);
        // This fragment comes in two varieties so this code decides which
        // set of variables to fill in when the device moves from landscape to
        // portrait.
        _fillItIn(view);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        garageStatus = (TextView) getView().findViewById(R.id.gStatus);
        garageSettings = (TextView) getView().findViewById(R.id.gSettings);

        if (savedInstanceState == null){
            garageStatus.setText("waiting");
        }
    }
    private void _fillItIn(View view){
        TextView statusText = (TextView) view.findViewById(R.id.gStatus);
        TextView settingsText = (TextView) view.findViewById(R.id.gSettings);
        statusText.setText("Garage Door 1: " + GetDataFromHouse.garageDoor1);
        statusText.append("\nGarage Door 2: " + GetDataFromHouse.garageDoor2);
        statusText.append("\nWater Heater: " + GetDataFromHouse.waterHeaterPower);
        statusText.append("\nSeptic Tank: " + GetDataFromHouse.septicTankLevel);
        settingsText.setText("None");
    }

    public void fillItIn(){
        View view = getView().findViewById(R.id.garageLayout);
        _fillItIn(view);

    }
}
