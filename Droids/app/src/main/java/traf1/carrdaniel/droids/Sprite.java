package traf1.carrdaniel.droids;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.Log;

public class Sprite {
    public Bitmap image;
    private int x, y;
    private int Vx = 10;
    private int Vy = 5;
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    public Sprite (Bitmap bmp) {
        image = bmp;
        x = 100;
        y = 100;
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, y, null);
    }

    public void update() {
        x += Vx;
        y += Vy;
        if ((x > screenWidth - image.getWidth()) || (x < 0)) {
            Vx = Vx*-1;
        }
        if ((y > screenHeight - image.getHeight()) || (y < 0)) {
            Vy = Vy*-1;
        }
    }

    public void resizedBitmap(int newWidth, int newHeight) {
        int width = image.getWidth();
        int height = image.getHeight();
        Log.i("jaunt", width + " " + height);
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        image = Bitmap.createBitmap(image, 0, 0, width, height, matrix, false);
        //image.recycle();
        // = resizedBitmap;
        //return resizedBitmap;
    }
}
