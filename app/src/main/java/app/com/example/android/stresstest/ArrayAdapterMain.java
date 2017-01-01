package app.com.example.android.stresstest;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import static app.com.example.android.stresstest.R.color.answerText;

/**
 * Created by aaquib on 27-Dec-16.
 */

public class ArrayAdapterMain extends ArrayAdapter<game>{
    public ArrayAdapterMain(Context context, ArrayList<game> list) {
        super(context, 0, list);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View returnView = convertView;
        if(returnView == null){
            returnView = LayoutInflater.from(getContext()).inflate(R.layout.layout_listview_main, parent, false);
        }
        game currentObject = getItem(position);
        TextView textView = (TextView)returnView.findViewById(R.id.frameLayout_TextView);
        textView.setText(currentObject.getName());

       /* ImageView frameLayout = (ImageView)returnView.findViewById(R.id.frameView);
        frameLayout.setBackgroundColor(currentObject.getColor());
        final int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            frameLayout.setBackgroundResource(currentObject.getImage());
        } else {
            frameLayout.setBackgroundResource( currentObject.getImage());
        }
        */

        TextView descriptionText = (TextView)returnView.findViewById(R.id.description_TextView);
        descriptionText.setText(currentObject.getDescription());

        LinearLayout layoutRoot = (LinearLayout)returnView.findViewById(R.id.layout_Root);
        int color_BackGround = ContextCompat.getColor(getContext(), currentObject.getColor());
        layoutRoot.setBackgroundColor(color_BackGround);



        return returnView;
    }

}

