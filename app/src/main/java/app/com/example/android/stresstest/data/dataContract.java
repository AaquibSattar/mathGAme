package app.com.example.android.stresstest.data;

import android.provider.BaseColumns;

/**
 * Created by aaquib on 31-Dec-16.
 */

public final class dataContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private dataContract(){}

   /* Inner class that defines the table contents */
    public static class database implements BaseColumns{
       public static final String TABLE_MULTIPLICATION = "multiply_Table";
       public static final String COLUMN_NAME_SCORE_MULTIPLY = "tables";

       public static final String TABLE_SQ_CUBE = "Sq_Cube_Table";
       public static final String COLUMN_NAME_SCORE_SQ_CUBE = "score_sq_Cube";

       public static final String TABLE_ROOT = "Root_Table";
       public static final String COLUMN_NAME_SCORE_ROOT = "score_Root";

       public static final String TABLE_OTHER = "other_Table";
       public static final String COLUMN_NAME_SCORE_OTHER = "other";


   }
}
