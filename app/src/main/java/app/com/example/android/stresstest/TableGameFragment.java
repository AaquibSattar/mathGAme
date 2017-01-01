package app.com.example.android.stresstest;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;


import app.com.example.android.stresstest.data.dataDbHelper;
import static app.com.example.android.stresstest.data.dataContract.database.COLUMN_NAME_SCORE_MULTIPLY;
import static app.com.example.android.stresstest.data.dataContract.database.TABLE_MULTIPLICATION;


/**
 * Created by aaquib on 15-Dec-16.
 */

public class TableGameFragment extends Fragment {

    private static final String TAG = "TableFragment_Tag";
    private int SCORE = 0;
    private static final int REQUEST_CODE = 0;

    private static final String SCORE_DIALOG = "SCORE_BOARD";
    private static final String START_DIALOG = "START_DIALOG";

    private TextView textView_Question;
    private EditText answerText;
    private TextView score;
    private int n1;
    private int n2;
    private int answer;
    private int ans_Recieved ;
    private Handler handler;
    private int timer;
    private String s;
    private int level_Selected;
    private int level_Current;
    private Runnable runnable;

    private View v;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        level_Selected = getActivity().getIntent().getIntExtra(ActivityFragment.EXTRA_LEVEL_SELECTED_ID, -1)+1;
        level_Current = getActivity().getIntent().getIntExtra(ActivityFragment.EXTRA_LEVEL_ID, -1);

        Log.d(TAG,"inside onCreate"+level_Selected+" "+level_Current);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState){
        Log.d(TAG,"inside onCreateView");
        timer =42000;
        v = inflater.inflate(R.layout.table_game,container,false);

        FragmentManager manager_Start = getFragmentManager();
        StartDialogFragment start_dialog = new StartDialogFragment();
        start_dialog.setTargetFragment(TableGameFragment.this,REQUEST_CODE);
        start_dialog.setCancelable(false);
        start_dialog.show(manager_Start,START_DIALOG);


        LinearLayout rootView = (LinearLayout)v.findViewById(R.id.game);
        int color = ContextCompat.getColor(getContext(),R.color.category_tables);
        rootView.setBackgroundColor(color);

        answerText = (EditText) v.findViewById(R.id.answer_Text);
        int color_Answer = ContextCompat.getColor(getContext(),R.color.answerText);
        answerText.setBackgroundColor(color_Answer);

        return v;
    }






    private void continueGame() {
        score = (TextView) v.findViewById(R.id.Text_score);

        displayQuestion();

        handler = new Handler();
         runnable = new Runnable() {
            @Override
            public void run() {
                Log.d(TAG,"inside runnable");

                if (timer == 0) {
                    checkResult();
                    // answerText = (EditText)v.findViewById(R.id.answer_Text);
                    answerText.setVisibility(View.GONE);
                    textView_Question.setVisibility(View.GONE);

                    if(  level_Current==level_Selected && SCORE >=13  ){
                    updateLevel();
                    }
                    score.setVisibility(View.GONE);
                    // ScoreDialogFragment dialog = new ScoreDialogFragment();
                    ScoreDialogFragment dialog = ScoreDialogFragment.newInstance(SCORE, level_Selected,level_Current, 1);
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




    private void updateLevel(){
        dataDbHelper mDbHelper = new dataDbHelper(getContext());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(  COLUMN_NAME_SCORE_MULTIPLY , SCORE);
       long id = db.insert(TABLE_MULTIPLICATION, null, values);
        Toast.makeText(getContext(),"dataBaseUpdated "+ id, Toast.LENGTH_SHORT).show();
    }




    private void displayQuestion(){
        Random r = new Random();

        int nR1= selectFirstNum();
        n1 = r.nextInt(nR1+5-nR1)+nR1;

        int nR2 = selectSecondNum();
        n2 = r.nextInt(nR2+10-nR2)+nR2;

        startCountDownTimer();

        textView_Question=(TextView)v.findViewById(R.id.question_Text);
        int color = ContextCompat.getColor(getContext(),R.color.category_tables_question);
        textView_Question.setBackgroundColor(color);

        textView_Question.setText(n1+" * "+n2);
    }

    private int selectFirstNum(){
        int n=0;
        switch (level_Selected){
            case 1:  n =0;
                break;
            case 2: n =5;
                break;
            case 3: n=10;
                break;
            case 4:  n =15;
                break;
            case 5:  n =20;
                break;
            case 6:  n =10;
                break;
            case 7:  n =25;
                break;
            case 8:  n =15;
                break;
            case 9:   n =20;
                break;
            case 10:  n =25;
                break;
        }
        return  n;
    }
    private int selectSecondNum(){

        int n=0;
        switch (level_Selected){
            case 1:  n = 0;
                break;
            case 2:  n = 0;
                break;
            case 3:  n = 0;
                break;
            case 4:  n = 0;
                break;
            case 5:  n = 0;
                break;
            case 6: n =10;
                break;
            case 7: n = 0;
                break;
            case 8: n = 10;
                break;
            case 9:  n = 15;
                break;
            case 10: n = 20;
                break;
        }
        return  n;
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





    private void checkResult() {
       answer = n1 * n2;
       answerText = (EditText) v.findViewById(R.id.answer_Text);
       try {
           ans_Recieved = Integer.parseInt(answerText.getText().toString());
       } catch (NumberFormatException e) {
           ans_Recieved = -1;
       } finally {
           if (ans_Recieved == answer) {
               Log.v(TAG, "recieved ans is " + ans_Recieved + "for" + ((45000 - timer) / 3000) + "Question" + n1 + "*" + n2);
               SCORE = SCORE + 1;
               answerText.setBackgroundColor(Color.rgb(129,199,132));
           } else {
               Log.v(TAG, "recieved ans is " + ans_Recieved + "for" + ((45000 - timer) / 3000) + "Question" + n1 + "*" + n2);
               SCORE = SCORE - 1;
               answerText.setBackgroundColor(Color.rgb(239, 83, 80));
           }
           answerText.setText("", TextView.BufferType.EDITABLE);
           score.setText("your score is: " + String.valueOf(SCORE));
       }
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
        ((ActivityFragment)getActivity()).closeTableActivity();
    }
}
