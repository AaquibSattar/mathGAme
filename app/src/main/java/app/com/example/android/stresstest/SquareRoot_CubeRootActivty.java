package app.com.example.android.stresstest;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import app.com.example.android.stresstest.data.dataDbHelper;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.provider.BaseColumns._ID;
import static app.com.example.android.stresstest.data.dataContract.database.COLUMN_NAME_SCORE_ROOT;
import static app.com.example.android.stresstest.data.dataContract.database.TABLE_ROOT;


/**
 * Created by aaquib on 15-Dec-16.
 */

public class SquareRoot_CubeRootActivty extends AppCompatActivity {

    private static final String TAG ="SqrRoot_CbRoot_TAG" ;
    private ArrayList<String> levelList;
    private ListView listView;
    private int currentLvl;
    private Cursor cursor;
    private LevelListAdapter list_Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview);

        levelList = new ArrayList<>();
        for(int i=1; i<=10; i++){
            levelList.add("Level "+i);
        }
        listView= (ListView) findViewById(R.id.list);
        dispalyLevelList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                 currentLvl = currentLevel();
                if((position+1)>currentLvl){
                    Toast.makeText(SquareRoot_CubeRootActivty.this, "Your Current level is "+currentLvl, Toast.LENGTH_SHORT).show();
                }else{
                Intent intent = ActivityFragmentRoot.newIntent(SquareRoot_CubeRootActivty.this, position, currentLvl);
                startActivity(intent);
                }
            }
        });
    }

    private void dispalyLevelList() {
         list_Adapter = new LevelListAdapter(SquareRoot_CubeRootActivty.this,levelList, 3, R.color.category_roots);
        listView.setAdapter(list_Adapter);
    }

    private int currentLevel(){
        dataDbHelper mDbHelper = new dataDbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = { _ID,
                COLUMN_NAME_SCORE_ROOT };

         cursor = db.query(
                TABLE_ROOT,                     // The table to query
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
        if(list_Adapter != null || (cursor.getCount()+1)>currentLvl ){
            dispalyLevelList();
        }
        Log.i(TAG, "onResume: called ");
    }

}
