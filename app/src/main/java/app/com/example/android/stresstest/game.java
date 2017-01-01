package app.com.example.android.stresstest;

/**
 * Created by aaquib on 29-Dec-16.
 */

public class game {
    String mName;
    int mDescription;
    int mColor;

    public game(String name, int description, int color ){
       mName = name;
        mDescription = description;
        mColor = color;
    }

    public String getName(){
        return mName;
    }

    public int getDescription(){
        return mDescription;
    }

    public int getColor(){
        return mColor;
    }
}
