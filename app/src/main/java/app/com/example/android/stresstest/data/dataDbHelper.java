package app.com.example.android.stresstest.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.os.Build.VERSION_CODES.N;
import static android.provider.BaseColumns._ID;
;
import static app.com.example.android.stresstest.data.dataContract.database.COLUMN_NAME_SCORE_MULTIPLY;
import static app.com.example.android.stresstest.data.dataContract.database.COLUMN_NAME_SCORE_OTHER;
import static app.com.example.android.stresstest.data.dataContract.database.COLUMN_NAME_SCORE_ROOT;
import static app.com.example.android.stresstest.data.dataContract.database.COLUMN_NAME_SCORE_SQ_CUBE;
import static app.com.example.android.stresstest.data.dataContract.database.TABLE_MULTIPLICATION;

import static app.com.example.android.stresstest.data.dataContract.database.TABLE_OTHER;
import static app.com.example.android.stresstest.data.dataContract.database.TABLE_ROOT;
import static app.com.example.android.stresstest.data.dataContract.database.TABLE_SQ_CUBE;

/**
 * Created by aaquib on 31-Dec-16.
 */

public class dataDbHelper  extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "dataScore.db";
    public dataDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_DATA_TABLE_MUTIPLY = "CREATE TABLE " + TABLE_MULTIPLICATION + "(" +
                _ID + " INTEGER PRIMARY KEY, " +
                COLUMN_NAME_SCORE_MULTIPLY + " INTEGER "+ ")";

        String SQL_CREATE_DATA_TABLE_SQ_CUBES = "CREATE TABLE " + TABLE_SQ_CUBE + "(" +
                _ID + " INTEGER PRIMARY KEY, " +
                COLUMN_NAME_SCORE_SQ_CUBE + " INTEGER "+ ")";

        String SQL_CREATE_DATA_TABLE_ROOTS = "CREATE TABLE " + TABLE_ROOT+ "(" +
                _ID + " INTEGER PRIMARY KEY, " +
                COLUMN_NAME_SCORE_ROOT+ " INTEGER "+ ")";

        String SQL_CREATE_DATA_TABLE_OTHER = "CREATE TABLE " + TABLE_OTHER + "(" +
                _ID + " INTEGER PRIMARY KEY, " +
                COLUMN_NAME_SCORE_OTHER + " INTEGER "+ ")";


        db.execSQL(SQL_CREATE_DATA_TABLE_MUTIPLY);

        db.execSQL(SQL_CREATE_DATA_TABLE_SQ_CUBES);

        db.execSQL(SQL_CREATE_DATA_TABLE_ROOTS);

        db.execSQL(SQL_CREATE_DATA_TABLE_OTHER);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}
