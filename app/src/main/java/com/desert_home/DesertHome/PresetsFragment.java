package com.desert_home.DesertHome;

/**
 * Created by dave on 3/3/2015.
 */
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

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
        ((Button)view.findViewById(R.id.preButtonLeaveLeft)).setOnClickListener(this);
        ((Button)view.findViewById(R.id.preButtonLeaveRight)).setOnClickListener(this);

        return view;
    }

    private void moveMe(){
        PresetsFragment fragment;
        FragmentManager fm = getFragmentManager();
        fragment = (PresetsFragment) getFragmentManager().findFragmentByTag("presetsTag");
        GetDataFromHouse.presetsSetToVisible = false;
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
            case R.id.preAcOff:
                Log.v("DHInfo", "Preset A/C Off");
                sender.sendIt(getActivity(), getString(R.string.commandUrl),
                        getString(R.string.preAcOff),
                        GetDataFromHouse.SecretWord);
                break;
            case R.id.preTemp98:
                Log.v("DHInfo", "Preset Temp = 98");
                sender.sendIt(getActivity(), getString(R.string.commandUrl),
                        getString(R.string.preTemp98),
                        GetDataFromHouse.SecretWord);
                break;
            case R.id.preFansRecirc:
                Log.v("DHInfo", "Preset Fans Recirc");
                sender.sendIt(getActivity(), getString(R.string.commandUrl),
                        getString(R.string.preRecirc),
                        GetDataFromHouse.SecretWord);
                break;
            case R.id.preSummerPM:
                Log.v("DHInfo", "Preset Summer Night");
                sender.sendIt(getActivity(), getString(R.string.commandUrl),
                        getString(R.string.preSummerNight),
                        GetDataFromHouse.SecretWord);
                break;
            case R.id.preWinterPM:
                Log.v("DHInfo", "Preset Winter Night");
                sender.sendIt(getActivity(), getString(R.string.commandUrl),
                        getString(R.string.preWinterNight),
                        GetDataFromHouse.SecretWord);
                break;
            case R.id.preResetHouse:
                Log.v("DHInfo", "Preset Reset House");
                sender.sendIt(getActivity(), getString(R.string.commandUrl),
                        getString(R.string.preReset),
                        GetDataFromHouse.SecretWord);
                break;
            case R.id.prePeakOn:
                Log.v("DHInfo", "Preset Peak On");
                sender.sendIt(getActivity(), getString(R.string.commandUrl),
                        getString(R.string.prePeakOn),
                        GetDataFromHouse.SecretWord);
                break;
            case R.id.prePeakOff:
                Log.v("DHInfo", "Preset Peak Off");
                sender.sendIt(getActivity(), getString(R.string.commandUrl),
                        getString(R.string.prePeakOff),
                        GetDataFromHouse.SecretWord);
                break;
            case R.id.prePassword:
                Log.v("DHInfo", "Preset Password");
                doPasswordDialog();
                break;
            case R.id.preButtonLeaveLeft:
            case R.id.preButtonLeaveRight:
                v.startAnimation(swellAnim);
                moveMe();
                break;
        }
    }

    private void doPasswordDialog(){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // set title
        alertDialogBuilder.setTitle("Enter Password");
        View view = inflater.inflate(R.layout.password_alert, null);
        final EditText value = (EditText) view.findViewById(R.id.prePassword);

        // set dialog message
        alertDialogBuilder.setView(view);
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Set",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int id) {
                // if this button is clicked,
                String gotback = value.getText().toString();
                Toast.makeText(getActivity(), "Setting Password to  " + gotback, Toast.LENGTH_LONG).show();
                SharedPreferences settings = getActivity().getSharedPreferences("Stuff", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString("secret", gotback);
                editor.commit();
                GetDataFromHouse.SecretWord = (String) settings.getString("secret", "");
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
        // show it
        alertDialog.show();
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
