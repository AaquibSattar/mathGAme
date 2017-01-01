package app.com.example.android.stresstest;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import app.com.example.android.stresstest.data.dataDbHelper;
import static android.provider.BaseColumns._ID;
import static app.com.example.android.stresstest.data.dataContract.database.COLUMN_NAME_SCORE_SQ_CUBE;
import static app.com.example.android.stresstest.data.dataContract.database.TABLE_SQ_CUBE;

/**
 * Created by aaquib on 15-Dec-16.
 */

public class Squares_CubesActiivty extends AppCompatActivity {
    private ArrayList<String> levelList;
    private ListView listView;
    private Cursor cursor;
    private int checklvl;
    private LevelListAdapter list_Adapter;

    private static final String TAG ="Sq.Cb.Activity_TAG" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);

        levelList = new ArrayList<>();
        for(int i=1; i<=10; i++){
            levelList.add("Level "+i);
        }
        listView= (ListView) findViewById(R.id.list);

       displayLevelView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                checklvl = currentLevel();
                if((position+1)> checklvl){
                    Toast.makeText(Squares_CubesActiivty.this,"your current level is "+checklvl, Toast.LENGTH_SHORT).show();
                }else{
                Intent intent = ActivityFragmentSquares.newIntent(Squares_CubesActiivty.this, position,checklvl );
                startActivity(intent);
                }
            }
        });
    }

    private void displayLevelView() {
        list_Adapter = new LevelListAdapter(Squares_CubesActiivty.this,levelList, 2, R.color.category_squares);
        listView.setAdapter(list_Adapter);
    }


    private int currentLevel(){
        dataDbHelper mDbHelper = new dataDbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = { _ID,
                COLUMN_NAME_SCORE_SQ_CUBE
        };

        cursor = db.query(
                TABLE_SQ_CUBE,                     // The table to query
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
    public void onResume(){
        super.onResume();
        if(list_Adapter != null || (cursor.getCount()+1)>checklvl ){
            displayLevelView();
        }
        Log.i(TAG, "onResume: called ");
    }

}
