package traf1.carrdaniel.quizapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
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

    int scoreValue = 0;
    int highScore = 0;
    String highScoreName = "";

    SharedPreferences pref;

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
        scoreValue = intent.getIntExtra("score", 0);
        String reason = intent.getStringExtra("gameOverReason");
        Guy targetGuy = (Guy) intent.getSerializableExtra("targetGuy");
        Guy hitGuy    = (Guy) intent.getSerializableExtra("youHitGuy");
        int targetGuyc = intent.getIntExtra("targetGuyc", -1);
        int targetGuye = intent.getIntExtra("targetGuye", -1);
        int targetGuym = intent.getIntExtra("targetGuym", -1);
        int hitGuyc = intent.getIntExtra("youHitGuyc", -1);
        int hitGuye = intent.getIntExtra("youHitGuye", -1);
        int hitGuym = intent.getIntExtra("youHitGuym", -1);

        pref = getApplicationContext().getSharedPreferences("ScorePref", 0);
        highScoreName = pref.getString("hsInitials", "---");
        highScore = pref.getInt("hsValue", 0);

        scoreText.setText(String.format("%s: %d", getString(R.string.score), scoreValue));
        if (scoreValue > highScore) {
            // high score popup
            final EditText highScoreEditText = new EditText(game_over.this);
            /*
            highScoreEditText.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int i, KeyEvent k) {
                    if (String.valueOf(highScoreEditText.getText()).length() > 3)
                        highScoreEditText.setText(String.valueOf(highScoreEditText.getText().subSequence(0, 3)));
                    return false;
                }
            });
             */
            AlertDialog dialog = new AlertDialog.Builder(game_over.this)
                .setTitle("New high score!")
                .setMessage("Enter your initials")
                .setView(highScoreEditText)
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        highScoreName = String.valueOf(highScoreEditText.getText()).toUpperCase().substring(0, 3);
                        //if (highScoreName == null)
                        //    highScoreName = "-";

                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("hsInitials", highScoreName);
                        editor.apply();
                        highScoreText.setText(String.format("* %s: %s, %d *", getString(R.string.high_score), highScoreName, highScore));
                    }
                })
                .create();
            dialog.show();

            highScore = scoreValue;
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt("hsValue", highScore);
            editor.apply();
            Log.i("guy", highScoreName);
            highScoreText.setText(String.format("* %s: %s, %d *", getString(R.string.high_score), highScoreName, highScore));
        }
        else
            highScoreText.setText(String.format("%s: %s, %d", getString(R.string.high_score), highScoreName, highScore));

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
