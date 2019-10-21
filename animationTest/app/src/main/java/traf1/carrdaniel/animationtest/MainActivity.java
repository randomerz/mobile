package traf1.carrdaniel.animationtest;

import androidx.appcompat.app.AppCompatActivity;

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

        final TextView angleView = (TextView) findViewById(R.id.tv_angle);
        final TextView offsetView = (TextView) findViewById(R.id.tv_offset);

        Joystick joystick = (Joystick) findViewById(R.id.joystick);
        joystick.setJoystickListener(new JoystickListener() {
            @Override
            public void onDown() {

            }

            @Override
            public void onDrag(float degrees, float offset) {
                angleView.setText("Angle: " + degrees);
                offsetView.setText(String.format("Offset: %s", offset));

                double rads = Math.toRadians(degrees);
                drawView.aX =  speed * offset * (float) Math.cos(rads);
                drawView.aY = -speed * offset * (float) Math.sin(rads);
                //drawView.theta = rads;
            }

            @Override
            public void onUp() {
                angleView.setText("Angle: none");
                offsetView.setText("Offset: none");

                drawView.aX = 0;
                drawView.aY = 0;
            }
        });
    }
}