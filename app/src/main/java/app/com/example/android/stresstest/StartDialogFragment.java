package app.com.example.android.stresstest;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

/**
 * Created by aaquib on 17-Dec-16.
 */

public class StartDialogFragment extends DialogFragment {

    public static final String EXTRA_RESPONSE = "package app.com.example.android.stresstest_RESPONSE";
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_start);
         builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String s = "ok";
                sendResult(Activity.RESULT_OK, s);
            }
        });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    private void sendResult(int resultCode, String s){
        if(getTargetFragment() == null){
            return;
        }
        Intent intent = new Intent();
        intent.putExtra(EXTRA_RESPONSE,s);

        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,intent);
    }

}
