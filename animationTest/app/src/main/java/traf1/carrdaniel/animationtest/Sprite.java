package traf1.carrdaniel.animationtest;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import static android.graphics.Bitmap.createBitmap;

class Sprite {
    public float x, y;
    public float vX, vY;
    public float aX, aY;
    public int width, height;
    float mass = 1;

    public int sheet_rows, sheet_cols;
    private int cropWidth, cropHeight;
    private Bitmap bitmap, rotatedBitmap;
    private int currentFrame = 0, animationDelay, baseAnimationDelay;
    private boolean doAnimations;
float deg =22;

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
        if(y + vY < 0 || y + height + vY > canvas.getHeight())
            vY *= -1;

        x += vX;
        y += vY;


        // Sprite sheet
        if(doAnimations && animationDelay-- < 0 && bitmap != null) {
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
        if(doAnimations && animationDelay-- < 0 && bitmap != null) {
            currentFrame = ++currentFrame % sheet_cols; //cycles current image with boundary proteciton
            animationDelay = baseAnimationDelay;
        }
    }

    public void draw(Canvas canvas) {
        if(bitmap == null) {
            Paint paint = new Paint();
            paint.setColor(color);
            float r = width / 2;
            canvas.drawCircle(x + r, y + r, r, paint);
        }
        else if (rotatedBitmap != null) {
            Paint paint = new Paint();
            paint.setColor(color);
            paint.setStyle(Paint.Style.STROKE);
            float r = width / 2;
            canvas.drawCircle(x + r, y + r, r, paint);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);

            Rect target= new Rect((int)x,(int)y, (int)(x+width),(int)(y+height));
            canvas.save();
            canvas.rotate((float)Math.toDegrees(Math.atan2(vY, vX)),x+r,y+r);
            canvas.drawBitmap(bitmap, null,target, null);
            canvas.restore();
            rotatedBitmap = null;
        }
        else {
            cropWidth = bitmap.getWidth() / sheet_cols;   //calculate width of 1 image
            cropHeight = bitmap.getHeight() / sheet_rows; //calculate height of 1 image
            int srcX = currentFrame * cropWidth;       //set x to the Nth frame
            //int srcY = getAnimationRow() * iconHeight; //set y to row of bitmap based on direction
            int srcY = 0 * cropHeight; // only 1 row please
            //Rect crop = new Rect(srcX, srcY, srcX + cropWidth, srcY + cropHeight);  //defines the rectangle inside of heroBmp to displayed
            Bitmap crop = createBitmap(bitmap, srcX, srcY, cropWidth, cropHeight);
            canvas.drawBitmap(crop, x, y, null);
        }
    }

    public void setSpriteSheetStuff(Bitmap sheet, int rows, int cols, int delay) {
        this.bitmap = sheet;
        this.sheet_rows = rows;
        this.sheet_cols = cols;
        this.baseAnimationDelay = this.animationDelay = delay;
        this.doAnimations = delay != -1;
    }

    public void rotateSprite() {
        rotateSprite((float) Math.atan2(vY, vX));
    }

    public void rotateSprite(float theta) {
        Matrix matrix = new Matrix();
        matrix.setRotate((float)Math.toDegrees(theta), width / 2, height / 2); // rotate around center
        rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
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
        //bitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
    }

    public boolean intersectCircle(Sprite other) {
        float r1 = width / 2;
        float r2 = other.width / 2;
        double dist = Math.pow(x + r1 - other.x - r2, 2) + Math.pow(y + r1 - other.y - r2, 2);
        double rads = Math.pow(r1 + r2, 2);
        return dist < rads; // collide if dist is less than rads combined
    }

    public void bounceCircle(Sprite other) {
//        float dx = this.x - other.x;
//        float dy = this.y - other.y;
//        double theta = Math.atan2(dy, dx);
//        double newV = Math.pow(Math.pow(vX, 2) + Math.pow(vY, 2), 0.5) + Math.pow(Math.pow(other.vX, 2) + Math.pow(other.vY, 2), 0.5);
//        newV /= 2;
//        this.vX  += (float)(newV * Math.cos(theta));
//        this.vY  += (float)(newV * Math.sin(theta));
//        other.vX -= (float)(newV * Math.cos(theta));
//        other.vX -= (float)(newV * Math.sin(theta));

        double d = Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
        double nx = (other.x - this.x) / d;
        double ny = (other.y - this.y) / d;
        double p = 2 * (vX * nx + this.vY * ny - other.vX * nx - other.vY * ny) /
                (this.mass + other.mass);
        this.vX  = (float)(this.vX - p  * this.mass  * nx);
        this.vY  = (float)(this.vY - p  * this.mass  * ny);
        other.vX = (float)(other.vX + p * other.mass * nx);
        other.vY = (float)(other.vY + p * other.mass * ny);
    }
}