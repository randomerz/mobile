package traf1.carrdaniel.animationtest;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

class Sprite {
    public float x, y;
    public float vX, vY;
    public int width, height;

    private static final int BMP_COLUMNS = 4;
    private static final int BMP_ROWS = 4;
    private static final int DOWN=0, LEFT=1, RIGHT=2, UP=3;
    private int color;
    private Bitmap bitmap;
    private int currentFrame=0, iconWidth, iconHeight, animationDelay=20;
    public Sprite() {
        this(1,2, Color.RED);
    }
    public Sprite(int dX, int dY, int color) {
        this(1,1,11,11,dX,dY,color);
    }

    public Sprite(float left, float top, float right, float bottom) {
        //this(left, top, right, bottom,1,2,Color.RED);
    }

    public Sprite(float x, float y, int width, int height, int dX, int dY, int color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.vX = dX;
        this.vY = dY;
        this.color = color;
    }

    public void update(Canvas canvas){
        // Physics
        if(x + vX < 0 || x + width + vX > canvas.getWidth())
            vX *= -1;
        if(y + vY > canvas.getHeight())
            y = 0; //teleport to top of screen
        if(y + vY < 0)
            y = canvas.getHeight();

        x += vX;
        y += vY;


        // Sprite sheet
        if(animationDelay-- < 0) {
            currentFrame = ++currentFrame % BMP_COLUMNS;//cycles current image with boundary proteciton
            animationDelay = 20;
        }
    }

    public void draw(Canvas canvas){
        if(bitmap == null) {
            Paint paint = new Paint();
            paint.setColor(color);
            float r = width / 2;
            canvas.drawCircle(x + r, y + r, r, paint); //draws circle
        }
        else {
            iconWidth = bitmap.getWidth() / BMP_COLUMNS;//calculate width of 1 image
            iconHeight = bitmap.getHeight() / BMP_ROWS; //calculate height of 1 image
            int srcX = currentFrame * iconWidth;       //set x of source rectangle inside of bitmap based on current frame
            int srcY = getAnimationRow() * iconHeight; //set y to row of bitmap based on direction
            Rect src = new Rect(srcX, srcY, srcX + iconWidth, srcY + iconHeight);  //defines the rectangle inside of heroBmp to displayed
            canvas.drawBitmap(bitmap, x, y,null); //draw an image
        }
    }

    private int getAnimationRow() {
        if (Math.abs(vX)>Math.abs(vY)){         //if magnitude of x is bigger than magnitude y
            if(Math.abs(vX)==vX) return RIGHT;  //if x is positive return row 2 for right
            else return LEFT;                          //if x is negative return row 1 for left
        } else if(Math.abs(vY)==vY) return DOWN;      //if y is positive return row 0 for up
        else return UP;                                 //if y is positive return row 3 for up

    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void grow(int i) {
        width += i;
        height += i;
    }
}