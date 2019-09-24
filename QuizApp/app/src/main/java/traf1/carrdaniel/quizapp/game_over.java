package traf1.carrdaniel.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class game_over extends AppCompatActivity {
    ImageButton targetc;
    ImageButton targete;
    ImageButton targetm;
    ImageButton hitc;
    ImageButton hite;
    ImageButton hitm;
    TextView nothingText;

    TextView scoreText;
    TextView highScoreText;

    Button resetButton;

    int highScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        targetc = findViewById(R.id.targetImage);
        targete = findViewById(R.id.targetImagec);
        targetm = findViewById(R.id.targetImageb);
        hitc = findViewById(R.id.hitImage);
        hite = findViewById(R.id.hitImagec);
        hitm = findViewById(R.id.hitImageb);
        nothingText = findViewById(R.id.nothingText);
        resetButton = findViewById(R.id.restartButton);
        scoreText = findViewById(R.id.yourScore);
        highScoreText = findViewById(R.id.highScore);

        Intent intent = getIntent();
        int scoreValue = intent.getIntExtra("score", 0);
        String reason = intent.getStringExtra("gameOverReason");
        Guy targetGuy = (Guy) intent.getSerializableExtra("targetGuy");
        Guy hitGuy    = (Guy) intent.getSerializableExtra("youHitGuy");
        int targetGuyc = intent.getIntExtra("targetGuyc", -1);
        int targetGuye = intent.getIntExtra("targetGuye", -1);
        int targetGuym = intent.getIntExtra("targetGuym", -1);
        int hitGuyc = intent.getIntExtra("youHitGuyc", -1);
        int hitGuye = intent.getIntExtra("youHitGuye", -1);
        int hitGuym = intent.getIntExtra("youHitGuym", -1);

        scoreText.setText(String.format("%s: %d", getString(R.string.score), scoreValue));
        if (scoreValue > highScore) {
            highScore = scoreValue;
            highScoreText.setText(String.format("* %s: %d *", getString(R.string.high_score), scoreValue));
        }
        else
            highScoreText.setText(String.format("%s: %d", getString(R.string.high_score), scoreValue));

        targetc.setImageResource(targetGuyc);
        targete.setImageResource(targetGuye);
        targetm.setImageResource(targetGuym);
        if (hitGuy == null) {
            nothingText.setVisibility(View.VISIBLE);
            hite.setVisibility(View.GONE);
            hitm.setVisibility(View.GONE);
            hitc.setImageResource(R.drawable.c0);
        }
        else {
            nothingText.setVisibility(View.GONE);
            hite.setVisibility(View.VISIBLE);
            hitm.setVisibility(View.VISIBLE);
            hitc.setImageResource(hitGuyc);
            hite.setImageResource(hitGuye);
            hitm.setImageResource(hitGuym);
        }

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Log.i("guy", hitGuyc + "");
    }
}
