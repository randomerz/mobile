package traf1.carrdaniel.animationtest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class DrawView extends View {
    Paint paint=new Paint();
    boolean firstFrame = true;

    TextView scoreText;

    Sprite playerSprite;
    Sprite coinSprite1;
    Sprite coinSprite2;

    public int score = 0;

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (firstFrame) {
            float x = getWidth() / 2;
            float y = getHeight() / 2;
            float radius = getWidth()*.1f;
            playerSprite = new Sprite(x, y, (int) radius * 2, (int) radius * 2);
            coinSprite1 = generateSprite();
            coinSprite2 = generateSprite();
            Bitmap coinSpinSheet = BitmapFactory.decodeResource(getResources(), R.drawable.spinning_coin_sheet);
            coinSprite1.setSpriteSheetStuff(coinSpinSheet, 1, 10, 1);
            coinSprite2.setSpriteSheetStuff(coinSpinSheet, 1, 10, 1);
            firstFrame = false;
        }

        // Physics
        playerSprite.updateDrag(canvas);
        coinSprite1.update(canvas);
        coinSprite2.update(canvas);

        if (playerSprite.intersectCircle(coinSprite1)) {
            updateSprite(playerSprite, coinSprite1);
            score++;
            scoreText.setText("Score: " + score);
        }
        if (playerSprite.intersectCircle(coinSprite2)) {
            updateSprite(playerSprite, coinSprite2);
            score++;
            scoreText.setText("Score: " + score);
        }



        // Drawing
        paint.setColor(Color.GRAY);
        canvas.drawRect(getLeft(), 0, getRight(), getBottom(), paint); //paint background gray
        playerSprite.draw(canvas);
        coinSprite1.draw(canvas);
        coinSprite2.draw(canvas);
        invalidate();
    }

    private Sprite generateSprite() {
        float x = (float)(Math.random() * (getWidth() - .1*getWidth()));
        float y = (float)(Math.random() * (getHeight() - .1*getHeight()));
        int dX = (int)(Math.random() * 11 - 5);
        int dY = (int)(Math.random() * 11 - 5);
        return new Sprite(x, y, (int)(.1f * getWidth()),(int)(.1f * getWidth()), dX, dY, Color.YELLOW);
    }

    private void updateSprite(Sprite player, Sprite coin) {
        coin.x = (float)(Math.random() * (getWidth() - .1 * getWidth()));
        coin.y = (float)(Math.random() * (getHeight() - .1 * getHeight()));
        while (player.intersectCircle(coin)) {  // doesn't spawn on top of each other
            coin.x = (float)(Math.random() * (getWidth() - .1 * getWidth()));
            coin.y = (float)(Math.random() * (getHeight() - .1 * getHeight()));
        }
        coin.vX = (int)(Math.random() * 11 - 5);
        coin.vY = (int)(Math.random() * 11 - 5);
    }
}