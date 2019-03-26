package com.example.timeupgrader;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
// import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class FocusCircleTimer extends View {
    private Paint paint;
    private int circleColor;
    private float circleWidth;
    private int progressColor;
    private float progressWidth;
    private int max;
    private int style;
    private int startAngle;
    public static final int STROKE = 0;
    public static final int FILL = 1;
    private int progress;
    /*private int textColor;
    private float textSize;
    private String text;
    private Rect rect;*/

    public FocusCircleTimer(Context context) {
        this(context, null);
    }

    public FocusCircleTimer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FocusCircleTimer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        paint = new Paint();

        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.FocusCircleTimer);
        circleColor = mTypedArray.getColor(R.styleable.FocusCircleTimer_circleColor, Color.RED);
        circleWidth = mTypedArray.getDimension(R.styleable.FocusCircleTimer_circleWidth, 5);
        progressColor = mTypedArray.getColor(R.styleable.FocusCircleTimer_progressColor, Color.GREEN);
        progressWidth = mTypedArray.getDimension(R.styleable.FocusCircleTimer_progressWidth, circleWidth);
        max = mTypedArray.getInteger(R.styleable.FocusCircleTimer_max, 100);
        style = mTypedArray.getInt(R.styleable.FocusCircleTimer_ci, 0);
        startAngle = mTypedArray.getInt(R.styleable.FocusCircleTimer_startAngle, 90);
        // textColor = mTypedArray.getColor(R.styleable.FocusCircleTimer_textColor, Color.RED);
        // textSize = mTypedArray.getDimension(R.styleable.FocusCircleTimer_textSize, 16);
        mTypedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int centerX = getWidth() / 2;
        int radius = (int) (centerX - circleWidth / 2);

        paint.setStrokeWidth(circleWidth);
        paint.setColor(circleColor);
        paint.setAntiAlias(true);
        switch (style) {
            case STROKE:
                paint.setStyle(Paint.Style.STROKE);
                break;
            case FILL:
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                break;
        }
        canvas.drawCircle(centerX, centerX, radius, paint);

        paint.setStrokeWidth(progressWidth);
        paint.setColor(progressColor);
        RectF oval = new RectF(centerX - radius , centerX - radius , centerX + radius , centerX + radius );

        int sweepAngle = 360 * progress / max;
        switch (style) {
            case STROKE:
                canvas.drawArc(oval, startAngle, sweepAngle, false, paint);
                break;
            case FILL:
                canvas.drawArc(oval, startAngle, sweepAngle, true, paint);
                break;
        }

        /*rect = new Rect();
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        paint.setStrokeWidth(0);
        text = getText();
        paint.getTextBounds(text, 0, text.length(), rect);
        Paint.FontMetricsInt fontMetrics = paint.getFontMetricsInt();
        int baseline = (getMeasuredHeight() - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
        canvas.drawText(text, getMeasuredWidth() / 2 - rect.width() / 2, baseline, paint);*/
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        if (max < 0) throw new IllegalArgumentException("Max can not be less than 0!");
        this.max = max;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        if (progress < 0) throw new IllegalArgumentException("Progress can not be less than 0!");
        if (progress > max) progress = max;
        this.progress = progress;
        postInvalidate();
    }

    private String getText() {
        return (int) ((progress / max) * 100) + "%";
    }
}