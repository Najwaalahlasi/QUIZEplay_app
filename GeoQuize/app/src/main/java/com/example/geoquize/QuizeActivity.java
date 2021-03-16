package com.example.geoquize;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizeActivity extends AppCompatActivity {
    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final int REQUEST_CODE_CHEAT = 0;

    private TextView text_score;
    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mBackButton;
    private Button mCheatButton;

    int counter = 0;
    int flag=0;

    private TextView mQuestionTextView;
    private Question[] mQuestionBank = new Question[] {
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };
    private int mCurrentIndex = 0;
    private boolean mIsCheater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quize);





        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            //------------------------------------------------------------------------
            //LAND--------------------------------------------------------------------
            mIsCheater=savedInstanceState.getBoolean(KEY_INDEX,true);
        }

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        updateQuestion();
        mTrueButton = (Button) findViewById(R.id.true_button);
        mFalseButton = (Button) findViewById(R.id.false_button);
        final MediaPlayer mediaPlayer = MediaPlayer.create(this,R.raw.song);

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);


                mBackButton.setEnabled(false);
// Does nothing yet, but soon!
            }
        });
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);

                mBackButton.setEnabled(false);
// Does nothing yet, but soon!
            }
        });
        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mBackButton=(ImageButton) findViewById(R.id.back_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex == (mQuestionBank.length - 1)) {
                    mNextButton.setEnabled(false);
                   mBackButton.setEnabled(true);
                   mediaPlayer.start();
                    score();
                }
                else {


                    mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                mIsCheater = false;

                updateQuestion();

            }}
        });


        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex == 0) {
                    mBackButton.setEnabled(false);
                    mNextButton.setEnabled(true);
                    mediaPlayer.start();
                    counter = 0;
                }
                else {


                mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                mIsCheater = false;

                updateQuestion();
            }}
        });


        mCheatButton = (Button)findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag++;
                allowCheat();

// Start CheatActivity
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(QuizeActivity.this, answerIsTrue);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });


    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode,resultCode,data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            //--------------------------------------------------------
            else {
                Toast.makeText(this,"cheating is rong dear ",Toast.LENGTH_SHORT).show();

            } //------------------------------------------------------
        }
            mIsCheater = CheatActivity.wasAnswerShown(data);

    }
/*
@Override

protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if(resultCode != RESULT_OK){
        return ;
    }
    if(data==null)
        return;

    Toast.makeText(this,"cheating done ? "+CheatActivity.cheating(data),Toast.LENGTH_SHORT).show();
}*/

    private void updateQuestion() {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }
    private void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;
        if (mIsCheater) {

            Toast.makeText(this, "Cheater", Toast.LENGTH_SHORT)
                    .show();


        } else {

            if (userPressedTrue == answerIsTrue) {
            messageResId = R.string.correct_toast;
                counter++;
        } else {
            messageResId = R.string.incorrect_toast;
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
                .show();
    }}

    @Override

    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }
    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putInt(KEY_INDEX,counter);
        savedInstanceState.putInt(KEY_INDEX,REQUEST_CODE_CHEAT );
        savedInstanceState.putInt(KEY_INDEX,flag);
        savedInstanceState.putBoolean(KEY_INDEX,mIsCheater);
        //------------------------------------------------------------------------
        savedInstanceState.putInt(KEY_INDEX,REQUEST_CODE_CHEAT);

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
    public void score() {
        //song.start();
        Toast toast = new Toast(this);
        toast.makeText(getApplicationContext(), "Scor is :  " + String.valueOf(counter), Toast.LENGTH_LONG)
                .show();
    }
    public void allowCheat() {
        if (flag <= 2) {
            mCheatButton.setEnabled(true);
        } else {
            mCheatButton.setEnabled(false);
            Toast.makeText(this, "No Cheating more than THREE Times", Toast.LENGTH_SHORT)
                    .show();
        }


    }}