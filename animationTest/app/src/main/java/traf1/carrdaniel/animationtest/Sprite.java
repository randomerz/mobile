package traf1.carrdaniel.animationtest;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import static android.graphics.Bitmap.createBitmap;

class Sprite {
    public float x, y;
    public float vX, vY;
    public float aX, aY;
    public int width, height;

    public int sheet_rows, sheet_cols;
    private int cropWidth, cropHeight;
    private Bitmap bitmap;
    private int currentFrame = 0, animationDelay, baseAnimationDelay;


    private int color;

    public Sprite() {
        this(0, 0,  Color.RED);
    }
    public Sprite(int dX, int dY, int color) {
        this(1, 1, 11, 11, dX, dY, color);
    }

    public Sprite(float x, float y, int width, int height) {
        this(x, y, width, height,0,0,Color.RED);
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
        if(animationDelay-- < 0 && bitmap != null) {
            currentFrame = ++currentFrame % sheet_cols; //cycles current image with boundary proteciton
            animationDelay = baseAnimationDelay;
        }
    }

    public void updateDrag(Canvas canvas) {
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

        float r = width / 2;
        if (x < 0) {x = 0; vX = -vX * .5f; vY *= .5f;}
        if (y < 0) {y = 0; vY = -vY * .5f; vX *= .5f;}
        if (x + width  > canvas.getWidth())  {x = canvas.getWidth()  - width;  vX = -vX * .5f; vY *= .5f;}
        if (y + height > canvas.getHeight()) {y = canvas.getHeight() - height; vY = -vY * .5f; vX *= .5f;}

        // Sprite sheet
        if(animationDelay-- < 0 && bitmap != null) {
            currentFrame = ++currentFrame % sheet_cols; //cycles current image with boundary proteciton
            animationDelay = baseAnimationDelay;
        }
    }

    public void draw(Canvas canvas) {
        if(bitmap == null) {
            Paint paint = new Paint();
            paint.setColor(color);
            float r = width / 2;
            canvas.drawCircle(x + r, y + r, r, paint); //draws circle
        }
        else {
            cropWidth = bitmap.getWidth() / sheet_cols;   //calculate width of 1 image
            cropHeight = bitmap.getHeight() / sheet_rows; //calculate height of 1 image
            int srcX = currentFrame * cropWidth;       //set x to the Nth frame
            //int srcY = getAnimationRow() * iconHeight; //set y to row of bitmap based on direction
            int srcY = 0 * cropHeight; // only 1 row please
            //Rect crop = new Rect(srcX, srcY, srcX + cropWidth, srcY + cropHeight);  //defines the rectangle inside of heroBmp to displayed
            Bitmap crop = createBitmap(bitmap, srcX, srcY, cropWidth, cropHeight);
            canvas.drawBitmap(crop, x, y, null); //draw an image
        }
    }

    public void setSpriteSheetStuff(Bitmap sheet, int rows, int cols, int delay) {
        this.bitmap = sheet;
        this.sheet_rows = rows;
        this.sheet_cols = cols;
        this.baseAnimationDelay = this.animationDelay = delay;
    }
    /*
    private int getAnimationRow() {
        if (Math.abs(vX)>Math.abs(vY)){         //if magnitude of x is bigger than magnitude y
            if(Math.abs(vX)==vX) return RIGHT;  //if x is positive return row 2 for right
            else return LEFT;                          //if x is negative return row 1 for left
        } else if(Math.abs(vY)==vY) return DOWN;      //if y is positive return row 0 for up
        else return UP;                                 //if y is positive return row 3 for up

    }
    */

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

    public boolean intersectCircle(Sprite other) {
        float r1 = width / 2;
        float r2 = other.width / 2;
        double dist = Math.pow(Math.pow(x + r1 - other.x - r2, 2) + Math.pow(y + r1 - other.y - r2, 2), 0.5);
        double rads = r1 + r2;
        return dist < rads; // collide if dist is less than rads combined
    }
}