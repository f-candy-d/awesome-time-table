package com.d.candy.f.awesometimetable.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.d.candy.f.awesometimetable.R;

/**
 * TODO: document your custom view class.
 */
public class CircularTimeLineMarker extends View {

    private String mExampleString; // TODO: use a default from R.string...
    private int mTextColor = Color.RED; // TODO: use a default from R.color...
    private int mBackgroundTint = mTextColor;
    private int mOutlineColor = mTextColor;
    private float mFontSize = 0; // TODO: use a default from R.dimen...

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;
    private int mRadius = 0;
    private float mOutlineWidth = 0;

    private Paint mCircularPaint;
    private Paint mOutlinePaint;

    private float mRadiusRatioToFont = 1.15f;

    public CircularTimeLineMarker(Context context) {
        super(context);
        init(null, 0);
    }

    public CircularTimeLineMarker(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CircularTimeLineMarker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.CircularTimeLineMarker, defStyle, 0);

        mExampleString = a.getString(
                R.styleable.CircularTimeLineMarker_exampleString);
        mTextColor = a.getColor(
                R.styleable.CircularTimeLineMarker_textColor,
                mTextColor);
        mBackgroundTint = a.getColor(
                R.styleable.CircularTimeLineMarker_backgroundTint,
                mBackgroundTint);
        mOutlineColor = a.getColor(
                R.styleable.CircularTimeLineMarker_outlineColor,
                mOutlineColor);

        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        mFontSize = a.getDimension(
                R.styleable.CircularTimeLineMarker_fontSize,
                mFontSize);
        mOutlineWidth = a.getDimension(
                R.styleable.CircularTimeLineMarker_outlineWidth,
                mOutlineWidth);

        a.recycle();

        // Set up a default TextPaint/Paint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mCircularPaint = new Paint();
        mCircularPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        mOutlinePaint = new Paint();
        mOutlinePaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();
    }

    private void invalidateTextPaintAndMeasurements() {
        mTextPaint.setTextSize(mFontSize);
        mTextPaint.setColor(mTextColor);
        mTextWidth = mTextPaint.measureText(mExampleString);

        Paint.FontMetrics metrics = mTextPaint.getFontMetrics();
        mTextHeight = metrics.descent - metrics.ascent;

        mCircularPaint.setColor(mBackgroundTint);

        mOutlinePaint.setColor(mOutlineColor);

//        // Radius of the circle
//        mRadius = (mTextWidth < mTextHeight)
//                ? (int) (mTextHeight/2 * mRadiusRatioToFont)
//                : (int) (mTextWidth/2 * mRadiusRatioToFont);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

//        if(contentWidth < contentHeight) {
//            mRadius = contentHeight / 2;
//        } else {
//            mRadius = contentWidth / 2;
//        }

        // Draw the circle and outline
        float cxPos = paddingLeft + contentWidth/2;
        float cyPos = paddingTop + contentHeight/2;
        canvas.drawCircle(cxPos, cyPos, mRadius, mOutlinePaint);
        canvas.drawCircle(cxPos, cyPos, mRadius - mOutlineWidth, mCircularPaint);

        // Draw the text.
        Paint.FontMetrics metrics = mTextPaint.getFontMetrics();
        float xPos = getWidth()/2;
        float yPos = getHeight()/2 + mTextHeight/2 - metrics.descent;
        canvas.drawText(mExampleString, xPos, yPos, mTextPaint);

        // TODO; guide line
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(2);
        canvas.drawLine(0, getHeight()/2, getWidth(), getHeight()/2, paint);
        canvas.drawLine(getWidth()/2, 0, getWidth()/2, getHeight(), paint);

        // Call super.onDraw() at the last of onDraw() to overwrite the
        // text on the background circle
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int width;
        int height;

        // Measure width
        if(widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            int desiredWidth = (mTextWidth < mTextHeight)
                    ? (int) (mTextHeight * mRadiusRatioToFont)
                    : (int) (mTextWidth * mRadiusRatioToFont);
            desiredWidth += paddingLeft + paddingRight;

            if(widthMode == MeasureSpec.AT_MOST) {
                width = Math.min(desiredWidth, widthSize);
            } else {
                width = desiredWidth;
            }
        }

        // Measure height
        if(heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            int desiredHeight = (mTextWidth < mTextHeight)
                    ? (int) (mTextHeight * mRadiusRatioToFont)
                    : (int) (mTextWidth  * mRadiusRatioToFont);
            desiredHeight += paddingBottom + paddingTop;

            if(heightMode == MeasureSpec.AT_MOST) {
                height = Math.min(desiredHeight, widthSize);
            } else {
                height = desiredHeight;
            }
        }

        // Measure radius of the circle
        mRadius = (width < height)
                ? (width-paddingLeft-paddingRight) / 2
                : (height-paddingBottom-paddingTop) / 2;


        setMeasuredDimension(width, height);
    }

    /**
     * Gets the example string attribute value.
     *
     * @return The example string attribute value.
     */
    public String getExampleString() {
        return mExampleString;
    }

    /**
     * Sets the view's example string attribute value. In the example view, this string
     * is the text to draw.
     *
     * @param exampleString The example string attribute value to use.
     */
    public void setExampleString(String exampleString) {
        mExampleString = exampleString;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example color attribute value.
     *
     * @return The example color attribute value.
     */
    public int getTextColor() {
        return mTextColor;
    }

    /**
     * Sets the view's example color attribute value. In the example view, this color
     * is the font color.
     *
     * @param textColor The example color attribute value to use.
     */
    public void setTextColor(int textColor) {
        mTextColor = textColor;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example dimension attribute value.
     *
     * @return The example dimension attribute value.
     */
    public float getFontSize() {
        return mFontSize;
    }

    /**
     * Sets the view's example dimension attribute value. In the example view, this dimension
     * is the font size.
     *
     * @param fontSize The example dimension attribute value to use.
     */
    public void setFontSize(float fontSize) {
        mFontSize = fontSize;
        invalidateTextPaintAndMeasurements();
    }
}
