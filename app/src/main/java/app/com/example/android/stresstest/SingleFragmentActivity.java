package app.com.example.android.stresstest;

import android.os.Bundle;
import android.support.v4.app.*;

/**
 * Created by aaquib on 15-Dec-16.
 */

public abstract class SingleFragmentActivity extends android.support.v4.app.FragmentActivity {
    protected abstract Fragment createFragment();

    @Override
    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

        if(fragment == null){
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container,fragment)
                    .commit();
        }
    }
}
