package traf1.carrdaniel.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.widget.Button;
import android.view.View;
import android.util.Log;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
    ImageButton b1eye;
    ImageButton b2eye;
    ImageButton b3eye;
    ImageButton b4eye;
    ImageButton b5eye;
    ImageButton b6eye;
    ImageButton b7eye;
    ImageButton b8eye;
    ImageButton b1color;
    ImageButton b2color;
    ImageButton b3color;
    ImageButton b4color;
    ImageButton b5color;
    ImageButton b6color;
    ImageButton b7color;
    ImageButton b8color;
    Button restartButton;

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

    Guy[] guys;
    Guy guy1;
    Guy guy2;
    Guy guy3;
    Guy guy4;
    Guy guy5;
    Guy guy6;
    Guy guy7;
    Guy guy8;

    int[] newGuyThresh = {1, 2, 4, 7, 10, 14};
    int numGuys;

    int targetNumber;

    int scoreValue;
    int highScoreValue;
    CountDownTimer timer;
    String[] correctMessages;

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
        b1eye = findViewById(R.id.imageButton1b);
        b2eye = findViewById(R.id.imageButton2b);
        b3eye = findViewById(R.id.imageButton3b);
        b4eye = findViewById(R.id.imageButton4b);
        b5eye = findViewById(R.id.imageButton5b);
        b6eye = findViewById(R.id.imageButton6b);
        b7eye = findViewById(R.id.imageButton7b);
        b8eye = findViewById(R.id.imageButton8b);
        b1color = findViewById(R.id.imageButton1);
        b2color = findViewById(R.id.imageButton2);
        b3color = findViewById(R.id.imageButton3);
        b4color = findViewById(R.id.imageButton4);
        b5color = findViewById(R.id.imageButton5);
        b6color = findViewById(R.id.imageButton6);
        b7color = findViewById(R.id.imageButton7);
        b8color = findViewById(R.id.imageButton8);
        restartButton = findViewById(R.id.restartButton);
        targetDescriptionText = findViewById(R.id.targetDescription);
        scoreText = findViewById(R.id.score);
        timerText = findViewById(R.id.timeLeft);
        gameOverText = findViewById(R.id.gameOver);
        highScoreText = findViewById(R.id.highScore);

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
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestartGame();
            }
        });

        correctMessages = new String[3];
        correctMessages[0] = getString(R.string.correct1);
        correctMessages[1] = getString(R.string.correct2);
        correctMessages[2] = getString(R.string.correct3);

        guy1 = new Guy(0, 0, 0);
        guy2 = new Guy(0, 0, 0);
        guy3 = new Guy(0, 0, 0);
        guy4 = new Guy(0, 0, 0);
        guy5 = new Guy(0, 0, 0);
        guy6 = new Guy(0, 0, 0);
        guy7 = new Guy(0, 0, 0);
        guy8 = new Guy(0, 0, 0);
        guys = new Guy[8];
        guys[0] = guy1;
        guys[1] = guy2;
        guys[2] = guy3;
        guys[3] = guy4;
        guys[4] = guy5;
        guys[5] = guy6;
        guys[6] = guy7;
        guys[7] = guy8;

        mouthButtons = new ImageButton[8];
        mouthButtons[0] = b1mouth;
        mouthButtons[1] = b2mouth;
        mouthButtons[2] = b3mouth;
        mouthButtons[3] = b4mouth;
        mouthButtons[4] = b5mouth;
        mouthButtons[5] = b6mouth;
        mouthButtons[6] = b7mouth;
        mouthButtons[7] = b8mouth;
        eyeButtons = new ImageButton[8];
        eyeButtons[0] = b1eye;
        eyeButtons[1] = b2eye;
        eyeButtons[2] = b3eye;
        eyeButtons[3] = b4eye;
        eyeButtons[4] = b5eye;
        eyeButtons[5] = b6eye;
        eyeButtons[6] = b7eye;
        eyeButtons[7] = b8eye;
        colorButtons = new ImageButton[8];
        colorButtons[0] = b1color;
        colorButtons[1] = b2color;
        colorButtons[2] = b3color;
        colorButtons[3] = b4color;
        colorButtons[4] = b5color;
        colorButtons[5] = b6color;
        colorButtons[6] = b7color;
        colorButtons[7] = b8color;

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

        RestartGame();
    }

    void checkAnswer(int choice) {
        if (choice == targetNumber + 1) {
            Log.i("quizResults", "Correct!");
            scoreValue += 1;
            scoreText.setText(String.format("%s: %d", getString(R.string.score), scoreValue));
            RestartChoices();

            Toast toast = Toast.makeText(getApplicationContext(), correctMessages[random.nextInt(correctMessages.length)], Toast.LENGTH_SHORT);
            toast.show();
            // toast.setGravity(Gravity.TOP| Gravity.LEFT, 0, 0);

            if (scoreValue >= newGuyThresh[numGuys - 2]) {
                numGuys++;
                AddNewGuyButton(numGuys);
            }
            return;
        }
        Log.i("quizResults", "Wrong");
        GameOver("Wrong target");
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
            }

            public void onFinish() {
                timerText.setText("0.00");
                GameOver("Out of time");
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

    void GameOver(String reason) {
        Log.i("quizResults", "game over, reason:");
        Log.i("quizResults", reason);
        gameOverText.setVisibility(View.VISIBLE);
        highScoreText.setVisibility(View.VISIBLE);
        restartButton.setVisibility(View.VISIBLE);
        for (int i = 0; i < 4; i++)
            mouthButtons[i].setEnabled(false);

        if (scoreValue > highScoreValue) {
            highScoreValue = scoreValue;
            highScoreText.setText(String.format("* %s: %d *", getString(R.string.score), scoreValue));
        }
        else
            highScoreText.setText(String.format("%s: %d", getString(R.string.score), scoreValue));

        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        //RestartGame();
    }

    void RestartGame() {
        scoreValue = 0;
        scoreText.setText(String.format("%s %d", getString(R.string.score), scoreValue));
        gameOverText.setVisibility(View.GONE);
        highScoreText.setVisibility(View.GONE);
        restartButton.setVisibility(View.GONE);
        for (int i = 2; i < 8; i++) {
            mouthButtons[i].setVisibility(View.GONE);
            eyeButtons[i].setVisibility(View.GONE);
            colorButtons[i].setVisibility(View.GONE);
        }
        numGuys = 2;
        for (int i = 0; i < 8; i++)
            mouthButtons[i].setEnabled(true);
        RestartChoices();
    }
}
