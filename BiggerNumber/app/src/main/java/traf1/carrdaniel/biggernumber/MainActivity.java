package traf1.carrdaniel.biggernumber;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button leftButton;
    Button rightButton;
    Button startButton;
    EditText getName;
    TextView timerText;
    TextView scoreText;
    ConstraintLayout cl;

    CountDownTimer timer;
    CountDownTimer bgColorTimer;

    String name;
    int score;

    int leftNum;
    int rightNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.startButton);
        leftButton = findViewById(R.id.leftButton);
        rightButton = findViewById(R.id.rightButton);
        getName = findViewById(R.id.getName);
        timerText = findViewById(R.id.timerText);
        scoreText = findViewById(R.id.scoreText);
        cl = findViewById(R.id.layout);


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = getName.getText().toString();
                StartGame();
            }
        });
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(0);
            }
        });
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(1);
            }
        });

        leftButton.setVisibility(View.GONE);
        rightButton.setVisibility(View.GONE);
        timerText.setVisibility(View.GONE);
        scoreText.setText(String.format("%s %d", getString(R.string.score), score));
    }

    void StartGame() {
        getName.setVisibility(View.GONE);
        startButton.setVisibility(View.GONE);
        leftButton.setVisibility(View.VISIBLE);
        rightButton.setVisibility(View.VISIBLE);
        timerText.setVisibility(View.VISIBLE);
        score = 0;

        timer = new CountDownTimer(30000, 10) {
            public void onTick(long millisUntilFinished) {
                int seconds = (int) (millisUntilFinished / 1000);
                int ms = (int) ((millisUntilFinished % 1000) / 10);
                timerText.setText(String.format("%02d.%02d", seconds, ms));
            }

            public void onFinish() {
                timerText.setText("00.00");
                GameOver();
            }
        }.start();

        scoreText.setText(String.format("%s %d", getString(R.string.score), score));

        StartRound();
    }

    void StartRound() {
        leftNum = (int)(Math.random() * 10);
        rightNum = (int)(Math.random() * 10);
        while (rightNum == leftNum)
            rightNum = (int)(Math.random() * 10);
        leftButton.setText(leftNum + "");
        rightButton.setText(rightNum + "");
    }

    void checkAnswer(int lr) {
        if ((lr == 0 && leftNum > rightNum) || (lr == 1 && leftNum < rightNum)) {
            score++;
            Toast toast = Toast.makeText(getApplicationContext(), "Correct", Toast.LENGTH_SHORT);
            toast.show();

            bgColorTimer = new CountDownTimer(400, 10) {
                @RequiresApi(api = Build.VERSION_CODES.O)
                public void onTick(long millisUntilFinished) {
                    // green is rgb(152,251,152)
                    // white is (255, 255, 255)
                    float r = 152 + ((255 - 152) * (1 - ((float) millisUntilFinished / 400)));
                    float g = 251 + ((255 - 251) * (1 - ((float) millisUntilFinished / 400)));
                    float b = 152 + ((255 - 152) * (1 - ((float) millisUntilFinished / 400)));
                    Log.i("colorStuff",millisUntilFinished + "");
                    Log.i("colorStuff",((255 - 152) * ((float) millisUntilFinished / 1000)) + "");
                    cl.setBackgroundColor(Color.rgb((int) r, (int) g, (int) b));
                }

                public void onFinish() {
                    cl.setBackgroundColor(Color.rgb(255, 255, 255));
                }
            }.start();
            // correct
        }
        else {
            // incorrect
            score--;
            Toast toast = Toast.makeText(getApplicationContext(), "Wrong", Toast.LENGTH_SHORT);
            toast.show();

            bgColorTimer = new CountDownTimer(400, 10) {
                @RequiresApi(api = Build.VERSION_CODES.O)
                public void onTick(long millisUntilFinished) {
                    // red is 220,20,60
                    // white is (255, 255, 255)
                    float r = 220 + ((255 - 220) * (1 - ((float) millisUntilFinished / 400)));
                    float g = 20 + ((255 - 20) * (1 - ((float) millisUntilFinished / 400)));
                    float b = 60 + ((255 - 60) * (1 - ((float) millisUntilFinished / 400)));
                    Log.i("colorStuff",millisUntilFinished + "");
                    Log.i("colorStuff",((255 - 152) * ((float) millisUntilFinished / 1000)) + "");
                    cl.setBackgroundColor(Color.rgb((int) r, (int) g, (int) b));
                }

                public void onFinish() {
                    cl.setBackgroundColor(Color.rgb(255, 255, 255));
                }
            }.start();
        }
        scoreText.setText(String.format("%s %d", getString(R.string.score), score));
        StartRound();
    }

    void GameOver() {
        leftButton.setVisibility(View.GONE);
        rightButton.setVisibility(View.GONE);
        timerText.setVisibility(View.GONE);
        getName.setVisibility(View.VISIBLE);
        startButton.setVisibility(View.VISIBLE);
    }
}
