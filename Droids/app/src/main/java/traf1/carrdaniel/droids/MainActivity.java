package traf1.carrdaniel.droids;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

public class MainActivity extends Activity {
    //Canvas c;
    SurfaceHolder surfaceHolder;

    GameView gv;
    GameManager gm;
    Sprite ship;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //c = this.surfaceHolder.lockCanvas();
        //gm = new GameManager(this);
        ship = new Sprite(BitmapFactory.decodeResource(getResources(), R.drawable.ship));
        ship.resizedBitmap(112, 112);
        gv = new GameView(this);
        setContentView(gv);
        gv.ship = ship;
    }
}
