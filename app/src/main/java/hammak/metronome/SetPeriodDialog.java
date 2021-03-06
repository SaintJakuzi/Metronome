package hammak.metronome;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.shawnlin.numberpicker.NumberPicker;


/*
*Created by Hammak on 14.08.2016 for Metronome.
*/

public class SetPeriodDialog extends DialogFragment implements OnClickListener,
        android.widget.NumberPicker.OnValueChangeListener {

    NumberPicker npInteger, npFractional;
    long periodMSec;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Set Period");
        View v = inflater.inflate(R.layout.set_period_dialog, null);

        v.findViewById(R.id.tvComa);
        v.findViewById(R.id.tvSec);

        npInteger = (NumberPicker) v.findViewById(R.id.npInteger);
        npFractional = (NumberPicker) v.findViewById(R.id.npFractional);

        npInteger.setOnValueChangedListener(this);

        v.findViewById(R.id.bCancel).setOnClickListener(this);
        v.findViewById(R.id.bApply).setOnClickListener(this);

        return v;
     }

    @Override
    public void onValueChange(android.widget.NumberPicker picker, int oldVal, int newVal) {
        if (newVal == 0){
            npFractional.setMinValue(3);
        }
        else if (oldVal == 0){
            npFractional.setMinValue(0);
        }
    }

    public interface OnCompleteListener {
        void onComplete(long periodMSec);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bCancel:
                dismiss();
                break;
            case R.id.bApply:
                periodMSec = (long) ((npInteger.getValue() * 1000) + (npFractional.getValue() * 100));
                mListener.onComplete(periodMSec);
                dismiss();
                break;
        }
    }

    private OnCompleteListener mListener;

    // make sure the Activity implemented it
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (OnCompleteListener)activity;
        }
        catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }
}
