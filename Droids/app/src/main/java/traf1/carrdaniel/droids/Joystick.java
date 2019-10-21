package traf1.carrdaniel.droids;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

/**
 * A simple and flexible joystick.
 * Extends FrameLayout and should host one direct child to act as the draggable stick.
 * <p/>
 * Created by Justas on 22/02/2015.
 */
public class Joystick extends FrameLayout {
    private static final String LOG_TAG = Joystick.class.getSimpleName();

    private static final int STICK_SETTLE_DURATION_MS = 100;
    private static final Interpolator STICK_SETTLE_INTERPOLATOR = new DecelerateInterpolator();

    private float touchSlop;

    private float centerX, centerY;
    private float radius;

    private View draggedChild;
    private boolean detectingDrag;
    private boolean dragInProgress;

    private float downX, downY;
    private static final int INVALID_POINTER_ID = -1;
    private int activePointerId = INVALID_POINTER_ID;

    private JoystickListener listener;

    public Joystick(Context context) {
        super(context);
        init(context);
    }

    public Joystick(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Joystick(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @SuppressWarnings("unused")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Joystick(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        final ViewConfiguration configuration = ViewConfiguration.get(context);
        touchSlop = configuration.getScaledTouchSlop();
    }

    @Override
    public boolean shouldDelayChildPressedState() {
        return true;
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        centerX = (float) w / 2;
        centerY = (float) h / 2;

        radius = (float) Math.min(w, h) / 2;
    }

    public void setJoystickListener(JoystickListener listener) {
        this.listener = listener;

        if (!hasStick()) {
            Log.w(LOG_TAG, LOG_TAG + " has no draggable stick, and is therefore not functional. " +
                    "Consider adding a child view to act as the stick.");
        }
    }

    /*
    TOUCH EVENT HANDLING
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: {
                if (detectingDrag || !hasStick()) return false;

                downX = event.getX(0);
                downY = event.getY(0);
                activePointerId = event.getPointerId(0);

                onStartDetectingDrag();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if (!detectingDrag) return false;
                if (INVALID_POINTER_ID == activePointerId) break;
                final int pointerIndex = event.findPointerIndex(activePointerId);
                final float x = event.getX(pointerIndex);
                final float y = event.getY(pointerIndex);
                final float dx = Math.abs(x - downX);
                final float dy = Math.abs(y - downY);
                if (dx * dx + dy * dy > touchSlop * touchSlop) {
                    onDragStart();
                    return true;
                }
                break;
            }
            case MotionEvent.ACTION_POINTER_UP: {
                final int pointerIndex = event.getActionIndex();
                final int pointerId = event.getPointerId(pointerIndex);

                if (pointerId != activePointerId)
                    break; // if active pointer, fall through and cancel!
            }
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP: {
                onTouchEnded();

                onStopDetectingDrag();
                break;
            }
        }

        return false;
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN: {
                if (!detectingDrag) return false;
                onDragStart();
                return true;
            }
            case MotionEvent.ACTION_MOVE: {
                if (!dragInProgress) break;
                if (INVALID_POINTER_ID == activePointerId) break;

                int pointerIndex = event.findPointerIndex(activePointerId);
                float latestX = event.getX(pointerIndex);
                float latestY = event.getY(pointerIndex);

                float deltaX = latestX - downX;
                float deltaY = latestY - downY;

                onDrag(deltaX, deltaY);
                return true;
            }
            case MotionEvent.ACTION_POINTER_UP: {
                final int pointerIndex = event.getActionIndex();
                final int pointerId = event.getPointerId(pointerIndex);

                if (pointerId != activePointerId)
                    break; // if active pointer, fall through and cancel!
            }
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP: {
                onTouchEnded();

                if (dragInProgress) onDragStop();
                return true;
            }
        }
        return false;
    }

    private void onTouchEnded() {
        activePointerId = INVALID_POINTER_ID;
    }

    private boolean hasStick() {
        return getChildCount() > 0;
    }

    private void onStartDetectingDrag() {
        detectingDrag = true;
        if (null != listener) listener.onDown();
    }

    private void onStopDetectingDrag() {
        detectingDrag = false;
        if (null != listener) listener.onUp();
    }

    private void onDragStart() {
        dragInProgress = true;
        requestDisallowInterceptTouchEvent(true);
        draggedChild = getChildAt(0);
        draggedChild.animate().cancel();
        onDrag(0, 0);
    }

    private void onDragStop() {
        dragInProgress = false;
        onStopDetectingDrag();

        draggedChild.animate()
                .translationX(0).translationY(0)
                .setDuration(STICK_SETTLE_DURATION_MS)
                .setInterpolator(STICK_SETTLE_INTERPOLATOR)
                .start();
        draggedChild = null;
    }

    /**
     * Where most of the magic happens. What, basic trigonometry isn't magic?!
     */
    private void onDrag(float dx, float dy) {
        float x = downX + dx - centerX;
        float y = downY + dy - centerY;

        float offset = (float) Math.sqrt(x * x + y * y);
        if (x * x + y * y > radius * radius) {
            x = radius * x / offset;
            y = radius * y / offset;
            offset = radius;
        }

        final double radians = Math.atan2(-y, x);
        final float degrees = (float) (180 * radians / Math.PI);

        if (null != listener) listener.onDrag(degrees, offset / radius);

        draggedChild.setTranslationX(x);
        draggedChild.setTranslationY(y);
    }

    /*
    FORCE SQUARE
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int size;
        if (widthMode == MeasureSpec.EXACTLY && widthSize > 0) {
            size = widthSize;
        } else if (heightMode == MeasureSpec.EXACTLY && heightSize > 0) {
            size = heightSize;
        } else {
            size = widthSize < heightSize ? widthSize : heightSize;
        }

        int finalMeasureSpec = MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY);
        super.onMeasure(finalMeasureSpec, finalMeasureSpec);
    }

    /*
    CENTER CHILD BY DEFAULT
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        LayoutParams params = new LayoutParams(getContext(), attrs);
        params.gravity = Gravity.CENTER;
        return params;
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(@NonNull ViewGroup.LayoutParams p) {
        LayoutParams params = new LayoutParams(p);
        params.gravity = Gravity.CENTER;
        return params;
    }

    @Override
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, Gravity.CENTER);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof LayoutParams;
    }

    /*
    ENSURE MAX ONE DIRECT CHILD
     */
    @Override
    public void addView(@NonNull View child, int index, ViewGroup.LayoutParams params) {
        if (getChildCount() > 0) {
            throw new IllegalStateException(LOG_TAG + " can host only one direct child");
        }

        super.addView(child, index, params);
    }
}