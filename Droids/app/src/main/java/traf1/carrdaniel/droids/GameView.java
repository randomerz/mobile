package traf1.carrdaniel.droids;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    Paint paint = new Paint();
    Sprite ship;
    private GameManager thread;

    public GameView(Context context) {
        super(context);

        getHolder().addCallback(this);

        thread = new GameManager(getHolder(), this);

        setFocusable(true);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {

        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    public void update() {
        ship.update();
    }

    @Override
    public void draw(Canvas canvas)
    {
        super.draw(canvas);
        paint.setColor(getResources().getColor(R.color.colorPrimaryDark));
        canvas.drawRect(getLeft(),0,getRight(),getBottom(),paint);
        if(canvas!=null) {
            ship.draw(canvas);

        }
    }
}
