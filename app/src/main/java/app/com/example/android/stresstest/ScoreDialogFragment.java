package app.com.example.android.stresstest;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import static android.R.attr.level;
import static app.com.example.android.stresstest.R.string.score;

/**
 * Created by aaquib on 17-Dec-16.
 */

public class ScoreDialogFragment extends DialogFragment {

    private static final String ARG_DATA = "SCORE";
    private static final String LEVEL_CURRENT = "CURRENT_LEVEL";
    private static final String LEVEL_SELECTED = "SELECTED_LEVEL";
    private static final String ACTIVITY_CODE = "ACTIVITY_CODE";
    public static ScoreDialogFragment newInstance (int score, int level_Selected, int level_Current, int code){
        Bundle args = new Bundle();
        args.putInt(ARG_DATA,score);
        args.putInt(LEVEL_SELECTED, level_Selected);
        args.putInt(LEVEL_CURRENT,level_Current);
        args.putInt(ACTIVITY_CODE, code);
        ScoreDialogFragment fragment = new ScoreDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //recieve the score passed by TableGameFragment
        int gameScore = getArguments().getInt(ARG_DATA);

        //recieve selected level data
        final int level_Selected = (getArguments().getInt(LEVEL_SELECTED))-1;

        //recieve current level data
        final int level_Current = getArguments().getInt(LEVEL_CURRENT);

        //recieve code of game being played
        final int game_Code = getArguments().getInt(ACTIVITY_CODE);

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Your Score: " + "" + String.valueOf(gameScore))
                .setPositiveButton(R.string.play, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = null;
                        Toast.makeText(getContext(), "Play Again Button was pressed", Toast.LENGTH_SHORT).show();
                        switch (game_Code){
                            case 1:
                                mListener.onChoose();
                                intent = ActivityFragment.newIntent(getContext(),level_Selected, level_Current );
                                break;
                            case 2: mListener.onChoose();
                                intent = ActivityFragmentSquares.newIntent(getContext(),level_Selected, level_Current );
                                break;
                            case 3: mListener.onChoose();
                                intent = ActivityFragmentRoot.newIntent(getContext(),level_Selected, level_Current );
                                break;
                        }

                        startActivity(intent);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(getContext(), "Cancel Button was pressed", Toast.LENGTH_SHORT).show();
                        mListener.onChoose();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

    public static interface MyInterface {
        public void onChoose();
    }

    private MyInterface mListener;

    @Override
    public void onAttach(Activity activity) {
        mListener = (MyInterface) activity;
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }
}
