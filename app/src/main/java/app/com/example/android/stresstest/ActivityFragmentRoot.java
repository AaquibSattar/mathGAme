package app.com.example.android.stresstest;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import static app.com.example.android.stresstest.ActivityFragmentSquares.CURRENT_LEVEL_SQ_CB;
import static app.com.example.android.stresstest.ActivityFragmentSquares.LEVEL_SELECTED_SQ_CB;

/**
 * Created by aaquib on 24-Dec-16.
 */

public class ActivityFragmentRoot extends SingleFragmentActivity  implements ScoreDialogFragment.MyInterface  {

    public static final String LEVEL_SELECTED_SQ_ROOT = "app.com.example.android.stresstest_SELECTED_LVL_SQ_ROOT";
    public static final String CURRENT_LEVEL_CB_ROOT = "app.com.example.android.stresstest_CURRENTLEVEL_CUBE_ROOT";

    public static Intent newIntent(Context context, int position, int currentLevel){
        Intent intent = new Intent(context, ActivityFragmentRoot.class);
        intent.putExtra(LEVEL_SELECTED_SQ_ROOT,position);
        intent.putExtra(CURRENT_LEVEL_CB_ROOT,currentLevel);
        return intent;
    }

    public void closeRootActivity(){
        finish();
    }


    @Override
    protected Fragment createFragment() {
        return new RootGameFragment();
    }

    @Override
    public void onChoose() {
        finish();
    }
}
