package com.desert_home.DesertHome;

/**
 * Created by dave on 3/3/2015.
 */
import android.app.Fragment;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * This is the fragment
 */
public class LightsFragment extends Fragment implements View.OnClickListener {

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
                R.layout.lights_fragment, container, false);

        ((ImageButton)view.findViewById(R.id.lButtonFrontPorch)).setOnClickListener(this);
        ((ImageButton)view.findViewById(R.id.lButtonOutsideGarage)).setOnClickListener(this);
        ((ImageButton)view.findViewById(R.id.lButtonCactusSpot)).setOnClickListener(this);
        ((ImageButton)view.findViewById(R.id.lButtonWestPatio)).setOnClickListener(this);
        ((Button)view.findViewById(R.id.lButtonOutsideOn)).setOnClickListener(this);
        ((Button)view.findViewById(R.id.lButtonOutsideOff)).setOnClickListener(this);

        _fillItIn(view);
        return view;
    }

    @Override
    public void onClick(View v) {
        SendDataToHouse sender = new SendDataToHouse();
        switch (v.getId()) {
            case R.id.lButtonFrontPorch:
                Log.v("DHInfo", "Front Porch Light Button");
                sender.sendIt(getActivity(), getString(R.string.commandUrl),
                        getString(R.string.lFrontPorchToggle),
                        getString(R.string.commandSecret));
                break;
            case R.id.lButtonOutsideGarage:
                Log.v("DHInfo", "Outside Garage Light Button");
                sender.sendIt(getActivity(),getString(R.string.commandUrl),
                        getString(R.string.lOutsideGarageToggle),
                        getString(R.string.commandSecret));
                break;
            case R.id.lButtonCactusSpot:
                Log.v("DHInfo", "Cactus Spot Light Button");
                sender.sendIt(getActivity(),getString(R.string.commandUrl),
                        getString(R.string.lCactusSpotToggle),
                        getString(R.string.commandSecret));
                break;
            case R.id.lButtonWestPatio:
                Log.v("DHInfo", "West Patio Light Button");
                sender.sendIt(getActivity(),getString(R.string.commandUrl),
                        getString(R.string.lWestPatioToggle),
                        getString(R.string.commandSecret));
                break;
            case R.id.lButtonOutsideOn:
                Log.v("DHInfo", "West Outside On Button");
                sender.sendIt(getActivity(),getString(R.string.commandUrl),
                        getString(R.string.lOutsideOn),
                        getString(R.string.commandSecret));
                break;
            case R.id.lButtonOutsideOff:
                Log.v("DHInfo", "West Outside On Button");
                sender.sendIt(getActivity(),getString(R.string.commandUrl),
                        getString(R.string.lOutsideOff),
                        getString(R.string.commandSecret));
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
        // Set the text correctly
        ((TextView) view.findViewById(R.id.lStatusFrontPorch)).setText("Front\nPorch");
        ((TextView) view.findViewById(R.id.lStatusOutsideGarage)).setText("Outside\nGarage");
        ((TextView) view.findViewById(R.id.lStatusCactusSpot)).setText("Cactus\nSpot");
        ((TextView) view.findViewById(R.id.lStatusWestPatio)).setText("West\nPatio");
        // set the light image correctly
        ImageButton b;
        b = (ImageButton)view.findViewById(R.id.lButtonFrontPorch);
        if(GetDataFromHouse.lightFrontPorch.equalsIgnoreCase("on")){
            b.setImageResource(R.drawable.bulbon);
        } else {
            b.setImageResource(R.drawable.bulboff);
        }
        b = (ImageButton)view.findViewById(R.id.lButtonOutsideGarage);
        if(GetDataFromHouse.lightOutsideGarage.equalsIgnoreCase("on")){
            b.setImageResource(R.drawable.bulbon);
        } else {
            b.setImageResource(R.drawable.bulboff);
        }
        b = (ImageButton)view.findViewById(R.id.lButtonCactusSpot);
        if(GetDataFromHouse.lightCactusSpot.equalsIgnoreCase("on")){
            b.setImageResource(R.drawable.bulbon);
        } else {
            b.setImageResource(R.drawable.bulboff);
        }
        b = (ImageButton)view.findViewById(R.id.lButtonWestPatio);
        if(GetDataFromHouse.lightWestPatio.equalsIgnoreCase("on")){
            b.setImageResource(R.drawable.bulbon);
        } else {
            b.setImageResource(R.drawable.bulboff);
        }
    }

    public void fillItIn(){
        View view = getView().findViewById(R.id.lightsLayout);
        _fillItIn(view);

    }
}
