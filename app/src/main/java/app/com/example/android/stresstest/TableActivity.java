package app.com.example.android.stresstest;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


import app.com.example.android.stresstest.data.dataDbHelper;

import static android.provider.BaseColumns._ID;
import static app.com.example.android.stresstest.data.dataContract.database.COLUMN_NAME_SCORE_MULTIPLY;
import static app.com.example.android.stresstest.data.dataContract.database.TABLE_MULTIPLICATION;


/**
 * Created by aaquib on 15-Dec-16.
 */

public class TableActivity extends AppCompatActivity {

    private static final String TAG = "TableActivity_TAG";
    private int checklvl;
    private LevelListAdapter list_Adapter;
    ArrayList<String> levelList;
    ListView listView;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate called");
        setContentView(R.layout.listview);

         levelList = new ArrayList<>();
        for(int i=1; i<=10; i++){
            levelList.add("Level# "+i);
        }

         listView = (ListView) findViewById(R.id.list);
       // ArrayAdapter<String> adapter = new ArrayAdapter<String>
                //(this,android.R.layout.simple_list_item_1,levelList);

        displayLevelList();
       // LevelListAdapter list_Adapter_Table = new LevelListAdapter(TableActivity.this,levelList, 1, R.color.category_tables);
        //listView.setAdapter(list_Adapter_Table);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                  checklvl =  currentLevel();
                if((position+1)<= checklvl){
                    Toast.makeText(TableActivity.this,"your current level is level#"+ checklvl ,Toast.LENGTH_SHORT).show();
              //  Intent intent  = new Intent(TableActivity.this, ActivityFragment.class);
                    Intent intent = ActivityFragment.newIntent(TableActivity.this, position, checklvl);
                    Log.v(TAG, "current level= "+checklvl);
                startActivity(intent);
                }else{
                    Toast.makeText(TableActivity.this,"your current stage is level#"+ checklvl ,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

      private void displayLevelList(){
        list_Adapter = new LevelListAdapter(TableActivity.this,levelList, 1, R.color.category_tables);
        listView.setAdapter(list_Adapter);
    }


    private int currentLevel(){
        dataDbHelper mDbHelper = new dataDbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = { _ID,
                COLUMN_NAME_SCORE_MULTIPLY
        };

         cursor = db.query(
                TABLE_MULTIPLICATION,                     // The table to query
                projection,                      // The columns to return
                null,
                null,                          // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
               null                               // The sort order
        );
        return (cursor.getCount()+1);
    }


    @Override
    public void onStart(){
        super.onStart();
        Log.i(TAG, "onStart: called");
    }


    @Override
    public void onResume(){
        super.onResume();
        if(list_Adapter != null || (cursor.getCount()+1)>checklvl ){
            displayLevelList();
        }
        Log.i(TAG, "onResume: called ");
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
