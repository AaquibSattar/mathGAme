package app.com.example.android.stresstest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.GridView;

import java.util.ArrayList;

import static android.media.CamcorderProfile.get;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity_TAG";

   private ArrayList<game> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG,"onCreate: called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = new ArrayList<>();
        list.add(new game("Tables",R.string.description_Table, R.color.category_tables));
        list.add(new game("Squares & Cubes",R.string.description_squares, R.color.category_squares));

        list.add(new game("Square Root & Cube Root",R.string.description_root, R.color.category_roots));
        list.add(new game("Other Games",R.string.description_others, R.color.category_others));

        GridView gridView = (GridView) findViewById(R.id.gridview);

        ArrayAdapterMain adapter = new ArrayAdapterMain(this,list);

        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Intent intent =null;
                String item_Clicked =  list.get(position).getName();
                switch(item_Clicked){
                   case "Tables":
                       intent = new Intent(MainActivity.this,TableActivity.class);
                       break;
                    case "Squares & Cubes":
                        intent = new Intent(MainActivity.this,Squares_CubesActiivty.class);
                        break;
                    case "Square Root & Cube Root":
                        intent = new Intent(MainActivity.this,SquareRoot_CubeRootActivty.class);
                        break;
                    case "Other Games":
                         intent = new Intent(MainActivity.this,OthersActivity.class);
                        break;
                }
                startActivity(intent);
            }
        });
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
