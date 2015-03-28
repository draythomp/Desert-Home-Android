package com.desert_home.DesertHome;

/**
 * Created by dave on 3/3/2015.
 */
import android.app.Fragment;
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
public class PresetsFragment extends Fragment implements View.OnClickListener{

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
                R.layout.presets_fragment, container, false);

        ((Button)view.findViewById(R.id.preAcOff)).setOnClickListener(this);
        ((Button)view.findViewById(R.id.preTemp98)).setOnClickListener(this);
        ((Button)view.findViewById(R.id.preFansRecirc)).setOnClickListener(this);
        ((Button)view.findViewById(R.id.preSummerPM)).setOnClickListener(this);
        ((Button)view.findViewById(R.id.preWinterPM)).setOnClickListener(this);
        ((Button)view.findViewById(R.id.preResetHouse)).setOnClickListener(this);
        ((Button)view.findViewById(R.id.prePeakOn)).setOnClickListener(this);
        ((Button)view.findViewById(R.id.prePeakOff)).setOnClickListener(this);
        ((Button)view.findViewById(R.id.prePassword)).setOnClickListener(this);

        return view;
    }

    public void onClick(View v) {
        SendDataToHouse sender = new SendDataToHouse();
        switch (v.getId()) {
            // The two garage doors are toggles, so the same command
            // will open or close the door -- may change that someday
            case R.id.preAcOff:
                Log.v("DHInfo", "Preset A/C Off");
                sender.sendIt(getActivity(), getString(R.string.commandUrl),
                        getString(R.string.preAcOff),
                        getString(R.string.commandSecret));
                break;
            case R.id.preTemp98:
                Log.v("DHInfo", "Preset Temp = 98");
                sender.sendIt(getActivity(), getString(R.string.commandUrl),
                        getString(R.string.preTemp98),
                        getString(R.string.commandSecret));
                break;
            case R.id.preFansRecirc:
                Log.v("DHInfo", "Preset Fans Recirc");
                sender.sendIt(getActivity(), getString(R.string.commandUrl),
                        getString(R.string.preRecirc),
                        getString(R.string.commandSecret));
                break;
            case R.id.preSummerPM:
                Log.v("DHInfo", "Preset Summer Night");
                sender.sendIt(getActivity(), getString(R.string.commandUrl),
                        getString(R.string.preSummerNight),
                        getString(R.string.commandSecret));
                break;
            case R.id.preWinterPM:
                Log.v("DHInfo", "Preset Winter Night");
                sender.sendIt(getActivity(), getString(R.string.commandUrl),
                        getString(R.string.preWinterNight),
                        getString(R.string.commandSecret));
                break;
            case R.id.preResetHouse:
                Log.v("DHInfo", "Preset Reset House");
                sender.sendIt(getActivity(), getString(R.string.commandUrl),
                        getString(R.string.preReset),
                        getString(R.string.commandSecret));
                break;
            case R.id.prePeakOn:
                Log.v("DHInfo", "Preset Peak On");
                sender.sendIt(getActivity(), getString(R.string.commandUrl),
                        getString(R.string.prePeakOn),
                        getString(R.string.commandSecret));
                break;
            case R.id.prePeakOff:
                Log.v("DHInfo", "Preset Peak Off");
                sender.sendIt(getActivity(), getString(R.string.commandUrl),
                        getString(R.string.prePeakOff),
                        getString(R.string.commandSecret));
                break;
            case R.id.prePassword:
                Log.v("DHInfo", "Preset Password");
                break;
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
