package traf1.carrdaniel.quizapp;

import android.content.Intent;
import android.os.Bundle;
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


        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent resultIntent) {
        //super.onActivityResult(requestCode, resultCode, resultIntent);
        if(resultCode == 100){

            // Storing result in a variable called myvar
            // get("website") 'website' is the key value result data
            int scoreValue = resultIntent.getIntExtra("score", 0);
            String reason = resultIntent.getStringExtra("gameOverReason");
            Guy targetGuy = (Guy) resultIntent.getSerializableExtra("targetGuy");
            Guy hitGuy    = (Guy) resultIntent.getSerializableExtra("youHitGuy");

            scoreText.setText(String.format("* %s: %d *", getString(R.string.score), scoreValue));
            if (scoreValue > highScore) {
                highScore = scoreValue;
                highScoreText.setText(String.format("* %s: %d *", getString(R.string.high_score), scoreValue));
            }
            else
                highScoreText.setText(String.format("%s: %d", getString(R.string.high_score), scoreValue));

            targetc.setImageResource(mouthSprite[g.mouthInd]);
            targete.setImageResource(eyeSprite[g.eyeInd]);
            targetm.setImageResource(colorSprite[g.colorInd]);
            if (hitGuy == null) {
                nothingText.setVisibility(View.VISIBLE);
                hitc.setVisibility(View.GONE);
                hite.setVisibility(View.GONE);
                hitm.setVisibility(View.GONE);
            }
            else {
                nothingText.setVisibility(View.GONE);
                hitc.setVisibility(View.VISIBLE);
                hite.setVisibility(View.VISIBLE);
                hitm.setVisibility(View.VISIBLE);
                hitc.setImageResource(mouthSprite[g.mouthInd]);
                hite.setImageResource(eyeSprite[g.eyeInd]);
                hitm.setImageResource(colorSprite[g.colorInd]);
            }
        }

    }
}
