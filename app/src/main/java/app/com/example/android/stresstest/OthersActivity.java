package app.com.example.android.stresstest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by aaquib on 15-Dec-16.
 */

public class OthersActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);

        ArrayList<String> levelList = new ArrayList<>();
        for(int i=1; i<=10; i++){
            levelList.add("Level "+i);
        }

        ListView listView= (ListView) findViewById(R.id.list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.simple_list_item_1,levelList);

        listView.setAdapter(adapter);
    }

}
