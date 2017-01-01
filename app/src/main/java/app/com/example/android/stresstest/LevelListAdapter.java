package app.com.example.android.stresstest;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import app.com.example.android.stresstest.data.dataDbHelper;

import static android.content.ContentValues.TAG;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static app.com.example.android.stresstest.data.dataContract.database.COLUMN_NAME_SCORE_MULTIPLY;
import static app.com.example.android.stresstest.data.dataContract.database.COLUMN_NAME_SCORE_ROOT;
import static app.com.example.android.stresstest.data.dataContract.database.COLUMN_NAME_SCORE_SQ_CUBE;
import static app.com.example.android.stresstest.data.dataContract.database.TABLE_MULTIPLICATION;
import static app.com.example.android.stresstest.data.dataContract.database.TABLE_ROOT;
import static app.com.example.android.stresstest.data.dataContract.database.TABLE_SQ_CUBE;

/**
 * Created by aaquib on 27-Dec-16.
 */

public class LevelListAdapter extends ArrayAdapter<String> {
    private static final String TAG = "LevelListAdapter_TAG";

    int activityCode;
    int mColor;
    public LevelListAdapter(Context context, ArrayList<String> levelList, int code, int colorCode) {
        super(context, 0, levelList);
        Log.v(TAG, "inside levelListAdapter");
        activityCode = code;
        mColor = colorCode;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View returnView = convertView;
        if (returnView == null) {
            returnView = LayoutInflater.from(getContext()).inflate(R.layout.layout_level_list, parent, false);
        }
        String currentObject = getItem(position);
        TextView textView = (TextView) returnView.findViewById(R.id.level_List);
        textView.setText(currentObject);
        ImageView imageList = (ImageView) returnView.findViewById(R.id.image);

        GradientDrawable magnitudeCircle = (GradientDrawable) imageList.getBackground();

       int level= checkLevel();
        if(position>level){
            imageList.setImageResource(R.drawable.draw_lock);
            magnitudeCircle.setColor(Color.rgb(244,67,54));
        }else{
            imageList.setImageResource(R.drawable.drawable_done);
            //find the color that the resouce id maps
            int color = ContextCompat.getColor(getContext(),mColor);
            textView.setBackgroundColor(color);
            magnitudeCircle.setColor(Color.rgb(102,187,106));
        }

        return returnView;
    }

    private int checkLevel(){
        dataDbHelper mDbHelper = new dataDbHelper(getContext());
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String projectionValue = null;
        String tableName = null;
        switch(activityCode){
            case 1:
                projectionValue = COLUMN_NAME_SCORE_MULTIPLY;
                tableName = TABLE_MULTIPLICATION;
                break;
            case 2: projectionValue = COLUMN_NAME_SCORE_SQ_CUBE;
                tableName = TABLE_SQ_CUBE;
                break;
            case 3: projectionValue = COLUMN_NAME_SCORE_ROOT;
                tableName = TABLE_ROOT;
                break;
        }

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = { projectionValue };

        Cursor cursor = db.query(
                tableName,                     // The table to query
                projection,                      // The columns to return
                null,
                null,                          // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                               // The sort order
        );
        return (cursor.getCount());
    }
}