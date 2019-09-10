package traf1.carrdaniel.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.CountDownTimer;
import android.widget.Button;
import android.view.View;
import android.util.Log;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    ImageButton b1mouth;
    ImageButton b2mouth;
    ImageButton b3mouth;
    ImageButton b4mouth;
    ImageButton b1eye;
    ImageButton b2eye;
    ImageButton b3eye;
    ImageButton b4eye;
    Button restartButton;

    ImageButton[] mouthButtons;
    int[] mouthSprite;
    String[] mouthDesc;
    ImageButton[] eyeButtons;
    int[] eyeSprite;
    String[] eyeDesc;

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

    int targetNumber;

    int scoreValue;
    int highScoreValue;
    CountDownTimer timer;

    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1mouth = findViewById(R.id.imageButton1);
        b2mouth = findViewById(R.id.imageButton2);
        b3mouth = findViewById(R.id.imageButton3);
        b4mouth = findViewById(R.id.imageButton4);
        b1eye = findViewById(R.id.imageButton1b);
        b2eye = findViewById(R.id.imageButton2b);
        b3eye = findViewById(R.id.imageButton3b);
        b4eye = findViewById(R.id.imageButton4b);
        restartButton = findViewById(R.id.restartButton);
        targetDescriptionText = findViewById(R.id.targetDescription);
        scoreText = findViewById(R.id.score);
        timerText = findViewById(R.id.timeLeft);
        gameOverText = findViewById(R.id.gameOver);
        highScoreText = findViewById(R.id.highScore);

        b1eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(1);
            }
        });
        b2eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(2);
            }
        });
        b3eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(3);
            }
        });
        b4eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(4);
            }
        });
        restartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestartGame();
            }
        });

        guy1 = new Guy(0, 0);
        guy2 = new Guy(0, 0);
        guy3 = new Guy(0, 0);
        guy4 = new Guy(0, 0);
        guys = new Guy[4];
        guys[0] = guy1;
        guys[1] = guy2;
        guys[2] = guy3;
        guys[3] = guy4;

        mouthButtons = new ImageButton[4];
        mouthButtons[0] = b1mouth;
        mouthButtons[1] = b2mouth;
        mouthButtons[2] = b3mouth;
        mouthButtons[3] = b4mouth;
        eyeButtons = new ImageButton[4];
        eyeButtons[0] = b1eye;
        eyeButtons[1] = b2eye;
        eyeButtons[2] = b3eye;
        eyeButtons[3] = b4eye;

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

        RestartGame();
    }

    void checkAnswer(int choice) {
        if (choice == targetNumber + 1) {
            Log.i("quizResults", "Correct!");
            scoreValue += 1;
            scoreText.setText(String.format("%s: %d", getString(R.string.score), scoreValue));
            RestartChoices();
            return;
        }
        Log.i("quizResults", "Wrong");
        GameOver("Wrong target");
    }

    void RestartChoices() {
        guy1 = GenerateGuy(guy1);
        guy2 = GenerateGuy(guy2);
        guy3 = GenerateGuy(guy3);
        guy4 = GenerateGuy(guy4);
        while (guy1.equals(guy2))
            guy2 = GenerateGuy(guy2);
        while (guy1.equals(guy3) || guy2.equals(guy3))
            guy3 = GenerateGuy(guy3);
        while (guy1.equals(guy4) || guy2.equals(guy4) || guy3.equals(guy4))
            guy4 = GenerateGuy(guy4);

        SetGuy(guy1, 0);
        SetGuy(guy2, 1);
        SetGuy(guy3, 2);
        SetGuy(guy4, 3);

        targetNumber = new Random().nextInt(4);
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
                GameOver("Out of time");
            }
        }.start();
    }

    Guy GenerateGuy(Guy g) {
        g.eyeInd = random.nextInt(4);
        g.mouthInd = random.nextInt(4);
        return g;
    }

    void SetGuy(Guy g, int buttonNum) {
        mouthButtons[buttonNum].setImageResource(mouthSprite[g.mouthInd]);
        eyeButtons[buttonNum].setImageResource(eyeSprite[g.eyeInd]);
    }

    void SetDescription(Guy g) {
        targetDescriptionText.setText(String.format("%s\n%s", mouthDesc[g.mouthInd], eyeDesc[g.eyeInd]));
    }

    void GameOver(String reason) {
        Log.i("quizResults", "game over, reason:");
        Log.i("quizResults", reason);
        gameOverText.setVisibility(View.VISIBLE);
        highScoreText.setVisibility(View.VISIBLE);
        restartButton.setVisibility(View.VISIBLE);
        for (int i = 0; i < 4; i++)
            eyeButtons[i].setEnabled(false);

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
        for (int i = 0; i < 4; i++)
            eyeButtons[i].setEnabled(true);
        RestartChoices();
    }
}
