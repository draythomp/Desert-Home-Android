package com.desert_home.DesertHome;

/**
 * Created by dave on 3/3/2015.
 */
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * This is the fragment
 */
public class GarageFragment extends Fragment implements View.OnClickListener{

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

        ((ImageButton)view.findViewById(R.id.gButtonGarageDoor1)).setOnClickListener(this);
        ((ImageButton)view.findViewById(R.id.gButtonGarageDoor2)).setOnClickListener(this);
        ((ImageButton)view.findViewById(R.id.gButtonWaterHeater)).setOnClickListener(this);
        ((Button)view.findViewById(R.id.gButtonLeaveLeft)).setOnClickListener(this);
        ((Button)view.findViewById(R.id.gButtonLeaveRight)).setOnClickListener(this);

        _fillItIn(view);
        return view;
    }

    private void moveMe(){
        GarageFragment fragment;
        FragmentManager fm = getFragmentManager();
        fragment = (GarageFragment) getFragmentManager().findFragmentByTag("garageTag");
        GetDataFromHouse.garageSetToVisible = false;
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.slide_in_from_right,
                R.animator.slide_out_to_right);
        fragmentTransaction.hide(fragment);
        fragmentTransaction.commit();
    }


    public void onClick(View v) {
        SendDataToHouse sender = new SendDataToHouse();
        Animation swellAnim = AnimationUtils.loadAnimation(getActivity(), R.anim.swell_up);
        switch (v.getId()) {
            // The two garage doors are toggles, so the same command
            // will open or close the door -- may change that someday
            case R.id.gButtonGarageDoor1:
                Log.v("DHInfo", "Garage Door 1 Button");
                sender.sendIt(getActivity(), getString(R.string.commandUrl),
                        getString(R.string.gDoor1Close),
                        GetDataFromHouse.SecretWord);
                break;
            case R.id.gButtonGarageDoor2:
                Log.v("DHInfo", "Garage Door 2 Button");
                sender.sendIt(getActivity(),getString(R.string.commandUrl),
                        getString(R.string.gDoor2Close),
                        GetDataFromHouse.SecretWord);
                break;
            // the water heater has an actual on and off
            case R.id.gButtonWaterHeater:
                Log.v("DHInfo", "Water Heater Power Button");
                if (GetDataFromHouse.waterHeaterPower.equalsIgnoreCase("off")) {
                    sender.sendIt(getActivity(), getString(R.string.commandUrl),
                            getString(R.string.gWaterHeaterOn),
                            GetDataFromHouse.SecretWord);
                } else {
                    sender.sendIt(getActivity(), getString(R.string.commandUrl),
                            getString(R.string.gWaterHeaterOff),
                            GetDataFromHouse.SecretWord);
                }
                break;
            case R.id.gButtonLeaveLeft:
            case R.id.gButtonLeaveRight:
                v.startAnimation(swellAnim);
                moveMe();
                break;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void _fillItIn(View view){
        ImageButton d1 = (ImageButton) view.findViewById(R.id.gButtonGarageDoor1);
        if (GetDataFromHouse.garageDoor1.equalsIgnoreCase("open"))
            d1.setImageResource(R.drawable.garageopen);
        else
            d1.setImageResource(R.drawable.garageclosed);

        ImageButton d2 = (ImageButton) view.findViewById(R.id.gButtonGarageDoor2);
        if (GetDataFromHouse.garageDoor2.equalsIgnoreCase("open"))
            d2.setImageResource(R.drawable.garageopen);
        else
            d2.setImageResource(R.drawable.garageclosed);

        ImageButton w = (ImageButton) view.findViewById(R.id.gButtonWaterHeater);
        if (GetDataFromHouse.waterHeaterPower.equalsIgnoreCase("on"))
            w.setImageResource(R.drawable.hotwaterheateron);
        else
            w.setImageResource(R.drawable.hotwatereateroff);

        TextView t = (TextView) view.findViewById(R.id.gSepticTank);
        t.setText("Septic Tank " + GetDataFromHouse.septicTankLevel);
    }

    public void fillItIn(){
        View view = getView().findViewById(R.id.garageLayout);
        _fillItIn(view);

    }
}
