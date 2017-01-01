package app.com.example.android.stresstest;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import app.com.example.android.stresstest.data.dataDbHelper;

import static android.R.attr.data;
import static android.support.v7.widget.AppCompatDrawableManager.get;
import static app.com.example.android.stresstest.data.dataContract.database.COLUMN_NAME_SCORE_ROOT;
import static app.com.example.android.stresstest.data.dataContract.database.TABLE_ROOT;

/**
 * Created by aaquib on 24-Dec-16.
 */
public class RootGameFragment extends Fragment {
    private static final int REQUEST_CODE = -17;
    private static final String START_DIALOG = "app.com.example.android.stresstest_RootGame" ;
    private static final String TAG = "ROOT_GAME_TAG";
    private static final String SCORE_DIALOG = "app.com.example.android.stresstest_RootGame_SCORE_DIALOG"  ;

    private int SCORE;
    private TextView textView_Question_Text;
    private TextView textView_Question;
    private TextView score;
    private double answer;
    private double ans_Recieved =-1;
    private Button optionA;
    private Button optionB;
    private android.os.Handler handler;
    private int timer;
    private String s;
    private int level_Selected;
    private int level_Current;
    private Runnable runnable;
   private ArrayList<RootGame> rootArrayList;
    RelativeLayout layOut_Relative ;

    Random r = new Random();

    private View v;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        level_Selected = getActivity().getIntent().getIntExtra(ActivityFragmentRoot.LEVEL_SELECTED_SQ_ROOT, -1)+1;
        level_Current = getActivity().getIntent().getIntExtra(ActivityFragmentRoot.CURRENT_LEVEL_CB_ROOT, -1);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState){
        timer = 42000;
         v = inflater.inflate(R.layout.rootgame, container,false);

        optionA = (Button)v.findViewById(R.id.option_A_Button);
        optionB = (Button)v.findViewById(R.id.option_B_Button);
       layOut_Relative= (RelativeLayout)v.findViewById(R.id.button_View);

        rootArrayList = new ArrayList<>();

        rootArrayList.add(new RootGame(2, 1.414, 1.260));
        rootArrayList.add(new RootGame(3, 1.732, 1.442));
        rootArrayList.add(new RootGame(4, 2.000, 1.587));
        rootArrayList.add(new RootGame(5, 2.236, 1.710));
        rootArrayList.add(new RootGame(6, 2.449, 1.817));
        rootArrayList.add(new RootGame(7, 2.646, 1.913));
        rootArrayList.add(new RootGame(8, 2.828, 2.000));
        rootArrayList.add(new RootGame(9, 3.000, 2.080));
        rootArrayList.add(new RootGame(10, 3.162, 2.154));
        rootArrayList.add(new RootGame(11, 3.317, 2.224));
        rootArrayList.add(new RootGame(12, 3.464, 2.289));
        rootArrayList.add(new RootGame(13, 3.606, 2.351));
        rootArrayList.add(new RootGame(14, 3.741, 2.410));
        rootArrayList.add(new RootGame(15, 3.873, 2.466));
        rootArrayList.add(new RootGame(16, 4.000, 2.520));
        rootArrayList.add(new RootGame(17, 4.123, 2.571));
        rootArrayList.add(new RootGame(18, 4.242, 2.621));
        rootArrayList.add(new RootGame(19, 4.359, 2.668));
        rootArrayList.add(new RootGame(20, 4.472, 2.714));
        rootArrayList.add(new RootGame(21, 4.583, 2.759));
        rootArrayList.add(new RootGame(22, 4.690, 2.802));
        rootArrayList.add(new RootGame(23, 4.800, 2.844));
        rootArrayList.add(new RootGame(24, 4.899,2.884 ));
        rootArrayList.add(new RootGame(25, 5.000, 2.924));
        rootArrayList.add(new RootGame(26, 5.100, 2.962));
        rootArrayList.add(new RootGame(27, 5.196, 3.000));
        rootArrayList.add(new RootGame(28, 5.291, 3.037));
        rootArrayList.add(new RootGame(29, 5.385, 3.072));
        rootArrayList.add(new RootGame(30, 5.477,3.107 ));


        FragmentManager manager_Start = getFragmentManager();
        StartDialogFragment start_dialog = new StartDialogFragment();
        start_dialog.setTargetFragment(RootGameFragment.this,REQUEST_CODE);
        start_dialog.setCancelable(false);
        start_dialog.show(manager_Start,START_DIALOG);

        LinearLayout rootView = (LinearLayout)v.findViewById(R.id.background_Parent);
        int color = ContextCompat.getColor(getContext(),R.color.category_roots);
        rootView.setBackgroundColor(color);

        return v;
    }




    private void continueGame() {
        score = (TextView) v.findViewById(R.id.Text_score);

        displayQuestion();

        handler = new android.os.Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                Log.d(TAG,"inside runnable");

                if (timer == 0) {
                    checkResult();
                    textView_Question.setVisibility(View.GONE);
                    textView_Question_Text.setVisibility(View.GONE);
                    layOut_Relative.setVisibility(View.GONE);


                    if(  level_Current==level_Selected && SCORE >=13  ){
                        updateLevel();
                    }
                    score.setVisibility(View.GONE);
                    // ScoreDialogFragment dialog = new ScoreDialogFragment();
                    ScoreDialogFragment dialog = ScoreDialogFragment.newInstance(SCORE, level_Selected,level_Current, 3);
                    FragmentManager manager = getFragmentManager();
                    dialog.show(manager, SCORE_DIALOG);
                } else {
                    checkResult();
                    displayQuestion();
                }
                timer = timer - 3000;
                if (timer >= 0) {
                    handler.postDelayed(this, 3000);
                }
            }
        };
        handler.postDelayed(runnable, 3000);
    }




    private void displayQuestion(){
        int n = selectNum();
        Log.v(TAG, "selected no. "+n);

        optionB.setBackgroundColor(Color.LTGRAY);
        optionA.setBackgroundColor(Color.LTGRAY);

        textView_Question_Text = (TextView) v.findViewById(R.id.question_Text);
        textView_Question =(TextView)v.findViewById(R.id.question_value);

        int x = r.nextInt(2);
        Log.v(TAG, "selected value of x "+x);
        switch (x) {
            case 0:
                int color = ContextCompat.getColor(getContext(),R.color.category_roots_Question_square);
                textView_Question_Text.setBackgroundColor(color);
                textView_Question_Text.setText("Sq. Root of");
                textView_Question.setBackgroundColor(color);
                textView_Question.setText(String.valueOf(n));
                answer = rootArrayList.get(n-2).getSqRoot();
                Log.v(TAG, "sq root of "+n+" is "+answer);
                displayOptions();
                break;

            case 1:
                int color_Cube = ContextCompat.getColor(getContext(),R.color.category_roots_Question_cube);
                textView_Question_Text.setBackgroundColor(color_Cube);
                textView_Question_Text.setText("Cube root of");
                textView_Question.setBackgroundColor(color_Cube);
                textView_Question.setText(String.valueOf(n));
                answer = rootArrayList.get(n-2).getCbroot();
                Log.v(TAG, "cube root of "+n+" is " +answer);
                displayOptions();
                break;
        }
    }

    private void displayOptions() {
        startCountDownTimer();
        String answerX;
        optionA.setEnabled(true);
        optionB.setEnabled(true);
        int a = r.nextInt(2);
        int difference = r.nextInt(85-50)+50;
        double diff=difference/1000.0;
        Log.v(TAG, " value of didfference & diff "+difference+" "+diff);
        DecimalFormat df = new DecimalFormat("###.###");
        int v = r.nextInt(2);
        if(v == 0){
        answerX =df.format(answer+diff);
        }else{
            answerX =df.format(answer-diff);
        }

        switch(a){
            case 0:
                optionA.setText(String.valueOf(answer));
                optionB.setText(answerX);
                Log.v(TAG, "options are: " +answer+"\n "+answerX);

                break;
            case 1:
                optionB.setText(String.valueOf(answer));
                optionA.setText(answerX);
                Log.v(TAG, "options are: " +answer+"\n "+answerX);

                break;
        }
        optionB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optionB.setBackgroundColor(Color.DKGRAY);
                ans_Recieved = Double.parseDouble(((Button) view).getText().toString());
                Log.v(TAG, "answerRecieved is " + ans_Recieved);
                optionA.setEnabled(false);
                optionB.setEnabled(false);
              }
        });
        optionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                optionA.setBackgroundColor(Color.DKGRAY);
                ans_Recieved = Double.parseDouble(((Button) view).getText().toString());
                Log.v(TAG, "answerRecieved is " + ans_Recieved);
                optionA.setEnabled(false);
                optionB.setEnabled(false);
            }

        });
    }






    private void startCountDownTimer() {
        final ProgressBar m_bar = (ProgressBar) v.findViewById(R.id.progressBarToday);
        int m_total = 0;


        m_bar.setProgress(m_total);

        final int totalMsecs = 3 * 1000; // 10 seconds in milli seconds
        int callInterval = 25;

        /** CountDownTimer */
        new CountDownTimer(totalMsecs, callInterval) {

            public void onTick(long millisUntilFinished) {

                int secondsRemaining = (int) millisUntilFinished / 1000;

                float fraction = millisUntilFinished / (float) totalMsecs;

// progress bar is based on scale of 1 to 100;
                m_bar.setProgress ( (int) (fraction * 100) );
            }

            public void onFinish() {

            }
        }.start();
    }

    /*
     setOptions() to set the optons on buttons
    */
    private int selectNum(){

        int n=0;
        switch (level_Selected){
            case 1:  n =r.nextInt(5)+2;
                break;
            case 2: n =r.nextInt(10)+5;
                break;
            case 3: n=r.nextInt(15)+10;
                break;
            case 4:  n =r.nextInt(20+15);
                break;
            case 5:  n =r.nextInt(25);
                break;
            case 6:  n =r.nextInt(30);
                break;
            case 7:  n =r.nextInt(10);
                break;
            case 8:  n =r.nextInt(20-10)+10;
                break;
            case 9:   n =r.nextInt(30-20)+20;
                break;
            case 10:  n=r.nextInt(30);
                break;
        }
        return  n;
    }




    private void checkResult(){
        if (ans_Recieved == answer) {
            SCORE = SCORE + 1;
            layOut_Relative.setBackgroundColor(Color.rgb(129,199,132));
        } else {
            SCORE = SCORE - 1;
            layOut_Relative.setBackgroundColor(Color.rgb(239, 83, 80));
        }
        score.setText("your score is: " + String.valueOf(SCORE));
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE) {
            s = data.getStringExtra(StartDialogFragment.EXTRA_RESPONSE);
            if(s.equals("ok")){
                continueGame();
            }
        }
    }



    /*
      updateLevel() called when user scores more or equal 13
      in the latest updated level.
      increases the current level by one.
     */
    private void updateLevel(){
        dataDbHelper mDbHelper = new dataDbHelper(getContext());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put( COLUMN_NAME_SCORE_ROOT, SCORE);
        long id = db.insert(TABLE_ROOT, null, values);
        Toast.makeText(getContext(),"dataBaseUpdated "+ id, Toast.LENGTH_SHORT).show();
    }




    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG,"inside onResume");
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public void onPause(){
        handler.removeCallbacks(runnable);
        super.onPause();
        Log.d(TAG,"inside onPause");
        ((ActivityFragmentRoot)getActivity()).closeRootActivity();
    }

}
