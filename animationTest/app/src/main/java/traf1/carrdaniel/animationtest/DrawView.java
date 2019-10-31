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

    TextView scoreText1;
    TextView scoreText2;
    TextView colorWinsText;

    Sprite playerSprite1;
    Sprite playerSprite2;
    Sprite coinSprite1;
    Sprite coinSprite2;

    Bitmap[] cats = new Bitmap[5];

    public int score1 = 0;
    public int score2 = 0;
    int maxScore = 50;
    int catCollisionCounter = 0;
    int catCollisionCounterMax = 10;

    boolean isPaused;

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (firstFrame) {
            float x = getWidth() / 2;
            float y = getHeight() / 2;
            float radius = getWidth()*.1f;
            playerSprite1 = new Sprite(x - radius, getHeight() / 3     - radius, (int) radius * 2, (int) radius * 2);
            playerSprite2 = new Sprite(x - radius, getHeight() * 2 / 3 - radius, (int) radius * 2, (int) radius * 2);
            cats[0] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ginger_cat), (int) radius * 2, (int) radius * 2, true);
            cats[1] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.kitten_png), (int) radius * 2, (int) radius * 2, true);
            cats[2] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.cat_hd), (int) radius * 2, (int) radius * 2, true);
            cats[3] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.grey_cat), (int) radius * 2, (int) radius * 2, true);
            cats[4] = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.persian_cat), (int) radius * 2, (int) radius * 2, true);
            playerSprite1.setSpriteSheetStuff(cats[(int)(Math.random() * cats.length)], 1, 1, -1);
            playerSprite2.setSpriteSheetStuff(cats[(int)(Math.random() * cats.length)], 1, 1, -1);
            playerSprite2.setColor(Color.CYAN);
            coinSprite1 = generateSprite();
            coinSprite2 = generateSprite();
            Bitmap coinSpinSheet = BitmapFactory.decodeResource(getResources(), R.drawable.spinning_coin_sheet);
            coinSprite1.setSpriteSheetStuff(coinSpinSheet, 1, 10, 1);
            coinSprite2.setSpriteSheetStuff(coinSpinSheet, 1, 10, 1);
            scoreText1.setText("" + score1);
            scoreText2.setText("" + score2);

            colorWinsText.setVisibility(View.GONE);

            firstFrame = false;
        }

        if (isPaused) {
            invalidate();
            return;
        }

        // Physics
        playerSprite1.updateDrag(canvas);
        playerSprite2.updateDrag(canvas);
        playerSprite1.rotateSprite();
        playerSprite2.rotateSprite();
        coinSprite1.update(canvas);
        coinSprite2.update(canvas);

        if (playerSprite1.intersectCircle(coinSprite1)) {
            updateSprite(playerSprite1, playerSprite2, coinSprite1);
            score1++;
            scoreText1.setText("" + score1);
            playerSprite1.setSpriteSheetStuff(cats[(int)(Math.random() * cats.length)], 1, 1, -1);
            //playerSprite.width = playerSprite.height += 1;
            if (score1 >= maxScore)
                GameOver();
        }
        if (playerSprite1.intersectCircle(coinSprite2)) {
            updateSprite(playerSprite1, playerSprite2, coinSprite2);
            score1++;
            scoreText1.setText("" + score1);
            playerSprite1.setSpriteSheetStuff(cats[(int)(Math.random() * cats.length)], 1, 1, -1);
            //playerSprite.width = playerSprite.height += 1;
            if (score1 >= maxScore)
                GameOver();
        }
        if (playerSprite2.intersectCircle(coinSprite1)) {
            updateSprite(playerSprite1, playerSprite2, coinSprite1);
            score2++;
            scoreText2.setText("" + score2);
            playerSprite2.setSpriteSheetStuff(cats[(int)(Math.random() * cats.length)], 1, 1, -1);
            //playerSprite.width = playerSprite.height += 1;
            if (score2 >= maxScore)
                GameOver();
        }
        if (playerSprite2.intersectCircle(coinSprite2)) {
            updateSprite(playerSprite1, playerSprite2, coinSprite2);
            score2++;
            scoreText2.setText("" + score2);
            playerSprite2.setSpriteSheetStuff(cats[(int)(Math.random() * cats.length)], 1, 1, -1);
            //playerSprite.width = playerSprite.height += 1;
            if (score2 >= maxScore)
                GameOver();
        }
        if (playerSprite1.intersectCircle(playerSprite2)) {
            catCollisionCounter += 1;
            playerSprite1.bounceCircle(playerSprite2);
            if (catCollisionCounter >= catCollisionCounterMax) {
                float radius = getWidth() * .1f;
                float x = getWidth() / 2 - radius;
                float y = getHeight() / 2;
                playerSprite1.x = playerSprite2.x = x;
                playerSprite1.y = getHeight() / 3 - radius;
                playerSprite2.y = getHeight() * 2 / 3 - radius;
                catCollisionCounter = 0;
            }
        }
        else
            catCollisionCounter = 0;



        // Drawing
        paint.setColor(Color.GRAY);
        canvas.drawRect(getLeft(), 0, getRight(), getBottom(), paint); //paint background gray
        playerSprite1.draw(canvas);
        playerSprite2.draw(canvas);
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

    private void updateSprite(Sprite player1, Sprite player2, Sprite coin) {
        coin.x = (float)(Math.random() * (getWidth() - .1 * getWidth()));
        coin.y = (float)(Math.random() * (getHeight() - .1 * getHeight()));
        while (player1.intersectCircle(coin) || player2.intersectCircle(coin)) {  // doesn't spawn on top of each other
            coin.x = (float)(Math.random() * (getWidth() - .1 * getWidth()));
            coin.y = (float)(Math.random() * (getHeight() - .1 * getHeight()));
        }
        coin.vX = (int)(Math.random() * 11 - 5);
        coin.vY = (int)(Math.random() * 11 - 5);
    }

    private void GameOver() {
        colorWinsText.setVisibility(View.VISIBLE);
        if (score1 > score2)
            colorWinsText.setText("Red Wins!");
        else
            colorWinsText.setText("Blue Wins!");
    }

    public void RestartGame() {
        float radius = getWidth() * .1f;
        float x = getWidth() / 2 - radius;
        float y = getHeight() / 2;
        playerSprite1.x = playerSprite2.x = x;
        playerSprite1.y = getHeight() / 3 - radius;
        playerSprite2.y = getHeight() * 2 / 3 - radius;
        score1 = score2 = 0;
        updateSprite(playerSprite1, playerSprite2, coinSprite1);
        updateSprite(playerSprite1, playerSprite2, coinSprite2);
    }
}