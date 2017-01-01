package app.com.example.android.stresstest;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import app.com.example.android.stresstest.data.dataContract;
import app.com.example.android.stresstest.data.dataDbHelper;

import static app.com.example.android.stresstest.data.dataContract.database.COLUMN_NAME_SCORE_SQ_CUBE;
import static app.com.example.android.stresstest.data.dataContract.database.TABLE_SQ_CUBE;

/**
 * Created by aaquib on 21-Dec-16.
 */

public class SquareGameFragment extends Fragment {

    private static final int REQUEST_CODE_SQ_GAME = -4;
    private int SCORE;

    private static final String SCORE_DIALOG = "SQ_SCORE_BOARD";
    private static final String START_DIALOG_SQ_GAME = "SQ_START_DIALOG";
    private static final String TAG = "SQ_CUBE_GAME_TAG";

    private TextView textView_Question;
    private TextView score;
    private int ans_Recieved ;
    private Handler handler;
    private int timer =42000;
    private String s;
    private int level_Selected;
    private int level_Current;
    private Runnable runnable;
    private int ans;
    private EditText answerText;

    private View v;

    Random r = new Random();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        level_Selected = getActivity().getIntent().getIntExtra(ActivityFragmentSquares.LEVEL_SELECTED_SQ_CB, -1)+1;
        level_Current = getActivity().getIntent().getIntExtra(ActivityFragmentSquares.CURRENT_LEVEL_SQ_CB, -1);

        Log.d(TAG,"inside onCreate"+level_Selected+" "+level_Current);
    }


    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState){
         v = inflater.inflate(R.layout.table_game, container, false);

        FragmentManager manager = getFragmentManager();
        StartDialogFragment dialogFragment = new StartDialogFragment();
        dialogFragment.setTargetFragment(SquareGameFragment.this,REQUEST_CODE_SQ_GAME);
        dialogFragment.setCancelable(false);
        dialogFragment.show(manager,START_DIALOG_SQ_GAME);

        LinearLayout rootView = (LinearLayout)v.findViewById(R.id.game);
        int color = ContextCompat.getColor(getContext(),R.color.category_squares);
        rootView.setBackgroundColor(color);

        answerText = (EditText) v.findViewById(R.id.answer_Text);
        int color_Answer = ContextCompat.getColor(getContext(),R.color.category_squares_answer);
        answerText.setBackgroundColor(color_Answer);

        return v;
    }


    private void continueGame(){
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
                    textView_Question.setVisibility(View.GONE);
                    answerText.setVisibility(View.GONE);

                    if(  level_Current==level_Selected && SCORE >=13  ){
                        updateLevel();
                    }
                    score.setVisibility(View.GONE);
                    // ScoreDialogFragment dialog = new ScoreDialogFragment();
                    ScoreDialogFragment dialog = ScoreDialogFragment.newInstance(SCORE, level_Selected,level_Current,2);
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



    private void checkResult() {
        answerText = (EditText) v.findViewById(R.id.answer_Text);
        try {
            ans_Recieved = Integer.parseInt(answerText.getText().toString());
        } catch (NumberFormatException e) {
            ans_Recieved = -1;
        } finally {
            if (ans_Recieved == ans) {
                SCORE = SCORE + 1;
                answerText.setBackgroundColor(Color.rgb(129,199,132));
            } else {
                SCORE = SCORE - 1;
                answerText.setBackgroundColor(Color.rgb(239, 83, 80));
            }
            answerText.setText("", TextView.BufferType.EDITABLE);
            score.setText("your score is: " + String.valueOf(SCORE));
        }

    }





    /* display question with help of two methods
       displayQuestion() and setOptions()
    */
    private void displayQuestion() {
        int n = selectNum();
        Log.v(TAG, "selected no. "+n);

        startCountDownTimer();

        textView_Question = (TextView) v.findViewById(R.id.question_Text);
        int color = ContextCompat.getColor(getContext(),R.color.category_squares_question);
        textView_Question.setBackgroundColor(color);
        int x = r.nextInt(2);
        Log.v(TAG, "selected value of x "+x);
        switch (x) {
            case 0:
                textView_Question.setText(String.valueOf(n+"^2"));
                ans = n*n;
                break;
            case 1:
                textView_Question.setText(String.valueOf(n+"^3"));
                ans = n*n*n;
                break;
        }
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
        values.put( COLUMN_NAME_SCORE_SQ_CUBE, SCORE);
        long id = db.insert(TABLE_SQ_CUBE, null, values);
        Toast.makeText(getContext(),"dataBaseUpdated "+ id, Toast.LENGTH_SHORT).show();
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





    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_SQ_GAME) {
            s = data.getStringExtra(StartDialogFragment.EXTRA_RESPONSE);
            if(s.equals("ok")){
                continueGame();
            }

        }
    }


    @Override
    public void onPause(){
        handler.removeCallbacks(runnable);
        super.onPause();
        Log.d(TAG,"inside onPause");
        ((ActivityFragmentSquares)getActivity()).closeSq_Cube_Activity();
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG,"inside onResume");
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}
