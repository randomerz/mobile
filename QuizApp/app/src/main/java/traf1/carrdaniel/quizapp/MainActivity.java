package traf1.carrdaniel.quizapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ImageButton b1mouth;
    ImageButton b2mouth;
    ImageButton b3mouth;
    ImageButton b4mouth;
    ImageButton b5mouth;
    ImageButton b6mouth;
    ImageButton b7mouth;
    ImageButton b8mouth;
    ImageButton b9mouth;
    ImageButton b10mouth;
    ImageButton b11mouth;
    ImageButton b12mouth;
    ImageButton b1eye;
    ImageButton b2eye;
    ImageButton b3eye;
    ImageButton b4eye;
    ImageButton b5eye;
    ImageButton b6eye;
    ImageButton b7eye;
    ImageButton b8eye;
    ImageButton b9eye;
    ImageButton b10eye;
    ImageButton b11eye;
    ImageButton b12eye;
    ImageButton b1color;
    ImageButton b2color;
    ImageButton b3color;
    ImageButton b4color;
    ImageButton b5color;
    ImageButton b6color;
    ImageButton b7color;
    ImageButton b8color;
    ImageButton b9color;
    ImageButton b10color;
    ImageButton b11color;
    ImageButton b12color;
    Button restartButton;
    ImageView pausePanel;
    boolean isPaused;
    boolean gameOver;

    ImageButton[] mouthButtons;
    int[] mouthSprite;
    String[] mouthDesc;
    ImageButton[] eyeButtons;
    int[] eyeSprite;
    String[] eyeDesc;
    ImageButton[] colorButtons;
    int[] colorSprite;
    String[] colorDesc;

    TextView targetDescriptionText;
    TextView scoreText;
    TextView timerText;
    TextView gameOverText;
    TextView highScoreText;
    ConstraintLayout cl;

    Guy[] guys;
    Guy guy1;
    Guy guy2;
    Guy guy3;
    Guy guy4;
    Guy guy5;
    Guy guy6;
    Guy guy7;
    Guy guy8;
    Guy guy9;
    Guy guy10;
    Guy guy11;
    Guy guy12;

    int[] newGuyThresh = {1, 2, 4, 7, 10, 14, 18, 22, 26, 30, 9999};
    //int[] newGuyThresh = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 9999};
    int numGuys;

    int targetNumber;

    int scoreValue;
    CountDownTimer timer;
    long gameTimeLeftInMillis = 5000;
    CountDownTimer bgColorTimer;

    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1mouth = findViewById(R.id.imageButton1c);
        b2mouth = findViewById(R.id.imageButton2c);
        b3mouth = findViewById(R.id.imageButton3c);
        b4mouth = findViewById(R.id.imageButton4c);
        b5mouth = findViewById(R.id.imageButton5c);
        b6mouth = findViewById(R.id.imageButton6c);
        b7mouth = findViewById(R.id.imageButton7c);
        b8mouth = findViewById(R.id.imageButton8c);
        b9mouth = findViewById(R.id.imageButton9c);
        b10mouth = findViewById(R.id.imageButton10c);
        b11mouth = findViewById(R.id.imageButton11c);
        b12mouth = findViewById(R.id.imageButton12c);
        b1eye = findViewById(R.id.imageButton1b);
        b2eye = findViewById(R.id.imageButton2b);
        b3eye = findViewById(R.id.imageButton3b);
        b4eye = findViewById(R.id.imageButton4b);
        b5eye = findViewById(R.id.imageButton5b);
        b6eye = findViewById(R.id.imageButton6b);
        b7eye = findViewById(R.id.imageButton7b);
        b8eye = findViewById(R.id.imageButton8b);
        b9eye = findViewById(R.id.imageButton9b);
        b10eye = findViewById(R.id.imageButton10b);
        b11eye = findViewById(R.id.imageButton11b);
        b12eye = findViewById(R.id.imageButton12b);
        b1color = findViewById(R.id.imageButton1);
        b2color = findViewById(R.id.imageButton2);
        b3color = findViewById(R.id.imageButton3);
        b4color = findViewById(R.id.imageButton4);
        b5color = findViewById(R.id.imageButton5);
        b6color = findViewById(R.id.imageButton6);
        b7color = findViewById(R.id.imageButton7);
        b8color = findViewById(R.id.imageButton8);
        b9color = findViewById(R.id.imageButton9);
        b10color = findViewById(R.id.imageButton10);
        b11color = findViewById(R.id.imageButton11);
        b12color = findViewById(R.id.imageButton12);
        restartButton = findViewById(R.id.restartButton);
        targetDescriptionText = findViewById(R.id.targetDescription);
        scoreText = findViewById(R.id.score);
        timerText = findViewById(R.id.timeLeft);
        gameOverText = findViewById(R.id.gameOver);
        highScoreText = findViewById(R.id.highScore);
        cl = findViewById(R.id.layout);
        pausePanel = findViewById(R.id.pauseBackground);

        b1mouth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(1);
            }
        });
        b2mouth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(2);
            }
        });
        b3mouth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(3);
            }
        });
        b4mouth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(4);
            }
        });
        b5mouth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(5);
            }
        });
        b6mouth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(6);
            }
        });
        b7mouth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(7);
            }
        });
        b8mouth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(8);
            }
        });
        b9mouth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(9);
            }
        });
        b10mouth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(10);
            }
        });
        b11mouth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(11);
            }
        });
        b12mouth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(12);
            }
        });
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPaused)
                    ResumeGame();
                else
                    RestartGame();
            }
        });

        guy1 = new Guy(0, 0, 0);
        guy2 = new Guy(0, 0, 0);
        guy3 = new Guy(0, 0, 0);
        guy4 = new Guy(0, 0, 0);
        guy5 = new Guy(0, 0, 0);
        guy6 = new Guy(0, 0, 0);
        guy7 = new Guy(0, 0, 0);
        guy8 = new Guy(0, 0, 0);
        guy9 = new Guy(0, 0, 0);
        guy10 = new Guy(0, 0, 0);
        guy11 = new Guy(0, 0, 0);
        guy12 = new Guy(0, 0, 0);
        guys = new Guy[12];
        guys[0] = guy1;
        guys[1] = guy2;
        guys[2] = guy3;
        guys[3] = guy4;
        guys[4] = guy5;
        guys[5] = guy6;
        guys[6] = guy7;
        guys[7] = guy8;
        guys[8] = guy9;
        guys[9] = guy10;
        guys[10] = guy11;
        guys[11] = guy12;

        mouthButtons = new ImageButton[12];
        mouthButtons[0] = b1mouth;
        mouthButtons[1] = b2mouth;
        mouthButtons[2] = b3mouth;
        mouthButtons[3] = b4mouth;
        mouthButtons[4] = b5mouth;
        mouthButtons[5] = b6mouth;
        mouthButtons[6] = b7mouth;
        mouthButtons[7] = b8mouth;
        mouthButtons[8] = b9mouth;
        mouthButtons[9] = b10mouth;
        mouthButtons[10] = b11mouth;
        mouthButtons[11] = b12mouth;
        eyeButtons = new ImageButton[12];
        eyeButtons[0] = b1eye;
        eyeButtons[1] = b2eye;
        eyeButtons[2] = b3eye;
        eyeButtons[3] = b4eye;
        eyeButtons[4] = b5eye;
        eyeButtons[5] = b6eye;
        eyeButtons[6] = b7eye;
        eyeButtons[7] = b8eye;
        eyeButtons[8] = b9eye;
        eyeButtons[9] = b10eye;
        eyeButtons[10] = b11eye;
        eyeButtons[11] = b12eye;
        colorButtons = new ImageButton[12];
        colorButtons[0] = b1color;
        colorButtons[1] = b2color;
        colorButtons[2] = b3color;
        colorButtons[3] = b4color;
        colorButtons[4] = b5color;
        colorButtons[5] = b6color;
        colorButtons[6] = b7color;
        colorButtons[7] = b8color;
        colorButtons[8] = b9color;
        colorButtons[9] = b10color;
        colorButtons[10] = b11color;
        colorButtons[11] = b12color;

        mouthSprite = new int[4];
        mouthSprite[0] = R.drawable.m1;
        mouthSprite[1] = R.drawable.m2;
        mouthSprite[2] = R.drawable.m3;
        mouthSprite[3] = R.drawable.m4;
        eyeSprite = new int[4];
        eyeSprite[0] = R.drawable.e1;
        eyeSprite[1] = R.drawable.e2;
        eyeSprite[2] = R.drawable.e3;
        eyeSprite[3] = R.drawable.e4;
        colorSprite = new int[4];
        colorSprite[0] = R.drawable.c1;
        colorSprite[1] = R.drawable.c2;
        colorSprite[2] = R.drawable.c3;
        colorSprite[3] = R.drawable.c4;

        mouthDesc = new String[4];
        mouthDesc[0] = "Happy mouth";
        mouthDesc[1] = "Surprised mouth";
        mouthDesc[2] = "Sad mouth";
        mouthDesc[3] = "Flat mouth";
        eyeDesc = new String[4];
        eyeDesc[0] = "Dot eyes";
        eyeDesc[1] = "Vertical eyes";
        eyeDesc[2] = "Horizontal eyes";
        eyeDesc[3] = "Rolling eyes";
        colorDesc = new String[4];
        colorDesc[0] = "Red";
        colorDesc[1] = "Green";
        colorDesc[2] = "Blue";
        colorDesc[3] = "Yellow";

        pausePanel.setVisibility(View.GONE);

        RestartGame();
    }

    @Override
    protected void onPause() {
        super.onPause();

        PauseGame();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (gameOver)
            RestartGame();
    }

    void checkAnswer(int choice) {
        if (choice == targetNumber + 1) {
            Log.i("quizResults", "Correct!");
            scoreValue += 1;
            scoreText.setText(String.format("%s: %d", getString(R.string.score), scoreValue));

            if (scoreValue >= newGuyThresh[numGuys - 2]) {
                numGuys++;
                AddNewGuyButton(numGuys);
            }
            RestartChoices();

            FlashScreenRedToWhite();
            return;
        }
        Log.i("quizResults", "Wrong");
        FlashScreenRedToRedLong();
        GameOver("Wrong target", guys[choice - 1]);
    }

    void RestartChoices() {
        for (int i = 0; i < numGuys; i++) {
            guys[i] = GenerateGuy(guys[i]);
            for (int j = 0; j < i; j++) {
                if (guys[i].equals(guys[j])) {
                    i -= 1;
                    break;
                }
            }
        }

        for (int i = 0; i < numGuys; i++)
            SetGuy(guys[i], i);

        targetNumber = new Random().nextInt(numGuys);
        SetDescription(guys[targetNumber]);

        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        Log.i("quizResults", "started t");
        timer = new CountDownTimer(5000, 10) {
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                int ms = (int) ((millisUntilFinished % 1000) / 10);
                timerText.setText(String.format("%d.%02d", seconds, ms));
                gameTimeLeftInMillis = millisUntilFinished;

                if (millisUntilFinished < 4000) {
                    float rate = 1 - ((float) millisUntilFinished / 4000);
                    float r = 255 - ((255 - 255) * rate);
                    float g = 255 - ((255 - 105) * rate);
                    float b = 255 - ((255 - 97) * rate);
                    cl.setBackgroundColor(Color.rgb((int) r, (int) g, (int) b));
                }
            }

            public void onFinish() {
                timerText.setText("0.00");
                GameOver("Out of time", null);
            }
        }.start();
    }

    Guy GenerateGuy(Guy g) {
        g.eyeInd = random.nextInt(4);
        g.mouthInd = random.nextInt(4);
        g.colorInd = random.nextInt(4);
        return g;
    }

    void SetGuy(Guy g, int buttonNum) {
        mouthButtons[buttonNum].setImageResource(mouthSprite[g.mouthInd]);
        eyeButtons[buttonNum].setImageResource(eyeSprite[g.eyeInd]);
        colorButtons[buttonNum].setImageResource(colorSprite[g.colorInd]);
    }

    void SetDescription(Guy g) {
        targetDescriptionText.setText(String.format("%s\n%s\n%s", mouthDesc[g.mouthInd], eyeDesc[g.eyeInd], colorDesc[g.colorInd]));
    }

    void AddNewGuyButton(int buttonNum) {
        mouthButtons[buttonNum - 1].setVisibility(View.VISIBLE);
        eyeButtons[buttonNum - 1].setVisibility(View.VISIBLE);
        colorButtons[buttonNum - 1].setVisibility(View.VISIBLE);

        /*
        switch (buttonNum) {
            case 3:
                mouthButtons[2].
        }
        */
    }

    void FlashScreenRedToWhite() {
        bgColorTimer = new CountDownTimer(400, 10) {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onTick(long millisUntilFinished) {
                float rate = 1 - ((float) millisUntilFinished / 400);
                float r = 220 + ((255 - 220) * rate);
                float g = 20  + ((255 - 20)  * rate);
                float b = 60  + ((255 - 60)  * rate);
                cl.setBackgroundColor(Color.rgb((int) r, (int) g, (int) b));
            }

            public void onFinish() {
                cl.setBackgroundColor(Color.rgb(255, 255, 255));
            }
        }.start();
    }

    void FlashScreenRedToRedLong() {
        bgColorTimer = new CountDownTimer(2000, 10) {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onTick(long millisUntilFinished) {
                float rate = 1 - ((float) millisUntilFinished / 2000);
                float r = 220 + ((205 - 220) * rate);
                float g = 20  + ((92  - 20)  * rate);
                float b = 60  + ((92  - 60)  * rate);
                cl.setBackgroundColor(Color.rgb((int) r, (int) g, (int) b));
            }

            public void onFinish() {
                cl.setBackgroundColor(Color.rgb(205, 92, 92));
            }
        }.start();
    }

    void setButtonsEnabled(boolean b) {
        for (int i = 0; i < colorButtons.length; i++) {
            colorButtons[i].setEnabled(b);
            eyeButtons[i].setEnabled(b);
            mouthButtons[i].setEnabled(b);
        }
    }

    void PauseGame() {
        if (!gameOver) {
            isPaused = true;
            Log.i("Guy", "paused game " + isPaused);
            pausePanel.setVisibility(View.VISIBLE);
            restartButton.setText(R.string.resume);
            timer.cancel();
        }
    }

    void ResumeGame() {
        if (!gameOver) {
            Log.i("Guy", "resumed game");
            isPaused = false;
            pausePanel.setVisibility(View.GONE);
            restartButton.setText(R.string.restart);

            timer = new CountDownTimer(gameTimeLeftInMillis, 10) {
                public void onTick(long millisUntilFinished) {
                    int seconds = (int) (millisUntilFinished / 1000);
                    int ms = (int) ((millisUntilFinished % 1000) / 10);
                    timerText.setText(String.format("%d.%02d", seconds, ms));
                    gameTimeLeftInMillis = millisUntilFinished;

                    if (millisUntilFinished < 4000) {
                        float rate = 1 - ((float) millisUntilFinished / 4000);
                        float r = 255 - ((255 - 255) * rate);
                        float g = 255 - ((255 - 105) * rate);
                        float b = 255 - ((255 - 97) * rate);
                        cl.setBackgroundColor(Color.rgb((int) r, (int) g, (int) b));
                    }
                }

                public void onFinish() {
                    timerText.setText("0.00");
                    GameOver("Out of time", null);
                }
            }.start();
        }
    }

    void GameOver(String reason, Guy hit) {
        Log.i("quizResults", "game over, reason:");
        Log.i("quizResults", reason);

        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        /*
        gameOverText.setVisibility(View.VISIBLE);
        highScoreText.setVisibility(View.VISIBLE);
        restartButton.setVisibility(View.VISIBLE);
         */
        gameOver = true;
        setButtonsEnabled(false);

        Intent i = new Intent(getApplicationContext(), game_over.class);
        i.putExtra("score", scoreValue);
        i.putExtra("gameOverReason", reason);
        i.putExtra("targetGuy", guys[targetNumber]);
        i.putExtra("targetGuyc", colorSprite[guys[targetNumber].colorInd]);
        i.putExtra("targetGuye", eyeSprite[guys[targetNumber].eyeInd]);
        i.putExtra("targetGuym", mouthSprite[guys[targetNumber].mouthInd]);
        i.putExtra("youHitGuy", hit);
        if (hit != null) {
            i.putExtra("youHitGuyc", colorSprite[hit.colorInd]);
            i.putExtra("youHitGuye", eyeSprite[hit.eyeInd]);
            i.putExtra("youHitGuym", mouthSprite[hit.mouthInd]);
        }
        startActivityForResult(i, 100);

        /*
        if (scoreValue > highScoreValue) {
            highScoreValue = scoreValue;
            highScoreText.setText(String.format("* %s: %d *", getString(R.string.score), scoreValue));
        }
        else
            highScoreText.setText(String.format("%s: %d", getString(R.string.score), scoreValue));
        */
    }

    void RestartGame() {
        scoreValue = 0;
        scoreText.setText(String.format("%s: %d", getString(R.string.score), scoreValue));
        //gameOverText.setVisibility(View.GONE);
        //highScoreText.setVisibility(View.GONE);
        //restartButton.setVisibility(View.GONE);
        for (int i = 2; i < colorButtons.length; i++) {
            mouthButtons[i].setVisibility(View.GONE);
            eyeButtons[i].setVisibility(View.GONE);
            colorButtons[i].setVisibility(View.GONE);
        }
        numGuys = 2;

        gameOver = false;
        setButtonsEnabled(true);
        if (bgColorTimer != null) {
            bgColorTimer.cancel();
            bgColorTimer = null;
        }
        cl.setBackgroundColor(Color.rgb(255, 255, 255));
        RestartChoices();
    }
}
