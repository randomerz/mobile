package traf1.carrdaniel.quizapp;

import android.content.Intent;
import android.os.Bundle;
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

        Intent intent = getIntent();
        //String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
    }
}
