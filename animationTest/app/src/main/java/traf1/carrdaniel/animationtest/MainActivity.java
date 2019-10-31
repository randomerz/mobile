package traf1.carrdaniel.animationtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    DrawView drawView;
    final float speed = 1f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawView = findViewById(R.id.drawView);
        drawView.scoreText1 = findViewById(R.id.score_text);
        drawView.scoreText2 = findViewById(R.id.score_text2);
        drawView.colorWinsText = findViewById(R.id.color_wins);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Joystick joystick1 = (Joystick) findViewById(R.id.joystick);
        joystick1.setJoystickListener(new JoystickListener() {
            @Override
            public void onDown() {

            }

            @Override
            public void onDrag(float degrees, float offset) {
                double rads = Math.toRadians(degrees);
                drawView.playerSprite1.aX =  speed * offset * (float) Math.cos(rads);
                drawView.playerSprite1.aY = -speed * offset * (float) Math.sin(rads);
                //drawView.theta = rads;
            }

            @Override
            public void onUp() {
                drawView.playerSprite1.aX = 0;
                drawView.playerSprite1.aY = 0;
            }
        });

        Joystick joystick2 = (Joystick) findViewById(R.id.joystick2);
        joystick2.setJoystickListener(new JoystickListener() {
            @Override
            public void onDown() {

            }

            @Override
            public void onDrag(float degrees, float offset) {
                double rads = Math.toRadians(degrees);
                drawView.playerSprite2.aX =  speed * offset * (float) Math.cos(rads);
                drawView.playerSprite2.aY = -speed * offset * (float) Math.sin(rads);
                //drawView.theta = rads;
            }

            @Override
            public void onUp() {
                drawView.playerSprite2.aX = 0;
                drawView.playerSprite2.aY = 0;
            }
        });
    }
}