package app.com.example.android.stresstest;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by aaquib on 21-Dec-16.
 */

public class ActivityFragmentSquares extends SingleFragmentActivity implements ScoreDialogFragment.MyInterface {

    public static final String LEVEL_SELECTED_SQ_CB = "app.com.example.android.stresstest_SELECTED_LVL_SQ_CBE";
    public static final String CURRENT_LEVEL_SQ_CB = "app.com.example.android.stresstest_CURRENTLEVEL_SQ_CUBE";

    public static Intent newIntent(Context context, int position, int currentLevel){
        Intent intent = new Intent(context, ActivityFragmentSquares.class);
        intent.putExtra(LEVEL_SELECTED_SQ_CB,position);
        intent.putExtra(CURRENT_LEVEL_SQ_CB,currentLevel);
        return intent;
    }

    @Override
    public void onChoose() {
        finish();
    }


    public void closeSq_Cube_Activity(){
        finish();
    }





    @Override
    protected Fragment createFragment() {
        return new SquareGameFragment();
    }
}
