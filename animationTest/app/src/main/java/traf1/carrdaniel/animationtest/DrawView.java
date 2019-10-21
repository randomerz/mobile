package traf1.carrdaniel.animationtest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class DrawView extends View {
    Paint paint=new Paint();
    public float x=0;
    public float vX=0;
    public float aX = 0;
    public float y=0;
    public float vY=0;
    public float aY = 0;

    float maxV = 8;
    float radius = 0;

    boolean firstFrame = true;

    Sprite coinSprite;

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (firstFrame) {
            x = getWidth() / 2;
            y = getHeight() / 2;
            radius = getWidth()*.1f;
            coinSprite = generateSprite();
            firstFrame = false;
        }

        // Physics
        // drag
        double theta = Math.atan2(vY, vX);
        double d = .02 * Math.pow(vX * vX + vY * vY, .5);

        aX -= d * Math.cos(theta);
        aY -= d * Math.sin(theta);

        x += vX;
        y += vY;
        vX += aX;
        vY += aY;

        aX += d * Math.cos(theta);
        aY += d * Math.sin(theta);

        if (x - radius < 0) {x = radius; vX = -vX * .5f; vY *= .5f;}
        if (y - radius < 0) {y = radius; vY = -vY * .5f; vX *= .5f;}
        if (x + radius > getWidth())  {x = getWidth()  - radius; vX = -vX * .5f; vY *= .5f;}
        if (y + radius > getHeight()) {y = getHeight() - radius; vY = -vY * .5f; vX *= .5f;}

        //Log.i("jaunt",  String.format("vx / vx / v / ax / ay | %.3f\t%.3f\t%.3f\t%.3f\t%.3f", vX, vY, Math.pow(vX * vX + vY * vY, .5), aX, aY));


        // Drawing

        paint.setColor(Color.GRAY);
        canvas.drawRect(getLeft(),0,getRight(),getBottom(),paint);//paint background gray
        coinSprite.draw(canvas);
        paint.setColor(Color.RED);
        canvas.drawCircle(x, y, radius ,paint);
        invalidate();
    }

    private Sprite generateSprite() {
        float x = (float)(Math.random() * (getWidth() - .1*getWidth()));
        float y = (float)(Math.random() * (getHeight() - .1*getHeight()));
        int dX = (int)(Math.random() * 11 - 5);
        int dY = (int)(Math.random() * 11 - 5);
        return new Sprite(x, y, (int)(.1f * getWidth()),(int)(.1f * getWidth()), dX, dY, Color.YELLOW);
    }
}