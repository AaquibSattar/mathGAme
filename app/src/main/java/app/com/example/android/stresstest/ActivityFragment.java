package app.com.example.android.stresstest;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.util.Log;

import static android.R.attr.level;

/**
 * Created by aaquib on 15-Dec-16.
 */

public class ActivityFragment extends SingleFragmentActivity implements ScoreDialogFragment.MyInterface {

    public static final String EXTRA_LEVEL_SELECTED_ID = "app.com.example.android.stresstest_LEVEL_SELECTED_ID";
    public static final String EXTRA_LEVEL_ID = "app.com.example.android.stresstest_LEVEL_CURRENT_ID";

    private static final String TAG ="ActivityFragment_TAG" ;

    public static Intent newIntent(Context packageContext,int level_Slected, int level_Current){
        Log.v(TAG,"newIntent Method called");
        Intent intent = new Intent(packageContext, ActivityFragment.class);
        intent.putExtra(EXTRA_LEVEL_SELECTED_ID, level_Slected);
        intent.putExtra(EXTRA_LEVEL_ID,level_Current);
        return intent;
    }

    public void closeTableActivity(){
        finish();
    }


    @Override
    public void onChoose() {
        finish();
    }


    @Override
    protected Fragment createFragment() {

        return new TableGameFragment();
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.i(TAG, "onStart: called");
    }


    @Override
    public void onResume(){
        super.onResume();
        Log.i(TAG, "onResume: called");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.i(TAG, "onPause: called");
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.v(TAG, "onStop: called");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.i(TAG, "onDestroy: called");
    }

}
