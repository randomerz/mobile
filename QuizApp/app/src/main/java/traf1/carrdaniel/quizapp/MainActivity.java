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
    ImageButton button1;
    ImageButton button2;
    ImageButton button3;
    ImageButton button4;
    Button restartButton;

    TextView targetDescriptionText;
    TextView scoreText;
    TextView timerText;
    TextView gameOverText;
    TextView highScoreText;

    Guy[] guyPool;
    Guy guy1;
    Guy guy2;
    Guy guy3;
    Guy guy4;

    int targetNumber;

    int scoreValue;
    int highScoreValue;
    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = findViewById(R.id.imageButton1);
        button2 = findViewById(R.id.imageButton2);
        button3 = findViewById(R.id.imageButton3);
        button4 = findViewById(R.id.imageButton4);
        restartButton = findViewById(R.id.restartButton);
        targetDescriptionText = findViewById(R.id.targetDescription);
        scoreText = findViewById(R.id.score);
        timerText = findViewById(R.id.timeLeft);
        gameOverText = findViewById(R.id.gameOver);
        highScoreText = findViewById(R.id.highScore);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(1);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(2);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(3);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
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

        guyPool = new Guy[5];
        guyPool[0] = new Guy(R.drawable.guy1, getString(R.string.confused));
        guyPool[1] = new Guy(R.drawable.guy2, getString(R.string.hap));
        guyPool[2] = new Guy(R.drawable.guy3, getString(R.string.pog));
        guyPool[3] = new Guy(R.drawable.guy4, getString(R.string.tired));
        guyPool[4] = new Guy(R.drawable.guy5, getString(R.string.cool));

        RestartGame();
    }

    void checkAnswer(int choice) {
        if (choice == targetNumber) {
            Log.i("quizResults", "Correct!");
            scoreValue += 1;
            scoreText.setText(String.format("%s: %d", getString(R.string.score), scoreValue));
            RestartChoices();
            return;
        }
        Log.i("quizResults", "Wrong");
        GameOver();
    }

    void RestartChoices() {
        int i1 = new Random().nextInt(guyPool.length);
        int i2 = new Random().nextInt(guyPool.length);
        int i3 = new Random().nextInt(guyPool.length);
        int i4 = new Random().nextInt(guyPool.length);
        while (i1 == i2)
            i2 = new Random().nextInt(guyPool.length);
        while (i1 == i3 || i2 == i3)
            i3 = new Random().nextInt(guyPool.length);
        while (i1 == i4 || i2 == i4 || i3 == i4)
            i4 = new Random().nextInt(guyPool.length);
        guy1 = guyPool[i1];
        button1.setImageResource(guy1.imgSrc);
        guy2 = guyPool[i2];
        button2.setImageResource(guy2.imgSrc);
        guy3 = guyPool[i3];
        button3.setImageResource(guy3.imgSrc);
        guy4 = guyPool[i4];
        button4.setImageResource(guy4.imgSrc);

        targetNumber = new Random().nextInt(4) + 1;
        if (targetNumber == 1)
            targetDescriptionText.setText(guy1.face);
        if (targetNumber == 2)
            targetDescriptionText.setText(guy2.face);
        if (targetNumber == 3)
            targetDescriptionText.setText(guy3.face);
        if (targetNumber == 4)
            targetDescriptionText.setText(guy4.face);

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
                GameOver();
            }
        }.start();
    }

    void GameOver() {
        Log.i("quizResults", "game over start");
        gameOverText.setVisibility(View.VISIBLE);
        highScoreText.setVisibility(View.VISIBLE);
        restartButton.setVisibility(View.VISIBLE);
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
        Log.i("quizResults", "game over done");
        //RestartGame();
    }

    void RestartGame() {
        scoreValue = 0;
        scoreText.setText(String.format("%s %d", getString(R.string.score), scoreValue));
        gameOverText.setVisibility(View.GONE);
        highScoreText.setVisibility(View.GONE);
        restartButton.setVisibility(View.GONE);
        RestartChoices();
    }
}
