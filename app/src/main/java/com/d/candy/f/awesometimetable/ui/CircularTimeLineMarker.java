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

    private enum Orientation {
        VERTICAL(0),
        HORIZONTAL(1);

        int mID;

        Orientation(int id) {
            mID = id;
        }

        int toInt() {
            return mID;
        }

        static Orientation fromId(int id) {
            for(Orientation type : values()) {
                if(type.mID == id) {
                    return type;
                }
            }
            throw new IllegalArgumentException(
                    "id=" + String.valueOf(id) + "is not supported");
        }
    }

    private String mExampleString; // TODO: use a default from R.string...
    private int mTextColor = Color.RED; // TODO: use a default from R.color...
    private int mPrimaryColor = mTextColor;
    private int mAccentColor = mTextColor;
    private int mLineColor = mTextColor;
    private float mFontSize = 0; // TODO: use a default from R.dimen...

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;
    private int mRadius = 0;
    private float mOutlineWidth = 0;
    private float mLineWidth = 0;

    private Paint mCircularPaint;
    private Paint mOutlinePaint;
    private Paint mLinePaint;

    private float mRadiusRatioToFont = 1.15f;
    private float mLineRatioToRadius = 1.3f;

    private Orientation mOrientation = Orientation.VERTICAL;

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
                R.styleable.CircularTimeLineMarker_text);
        mOrientation = Orientation.fromId(a.getInt(
                R.styleable.CircularTimeLineMarker_orientation,
                Orientation.VERTICAL.toInt()));
        mTextColor = a.getColor(
                R.styleable.CircularTimeLineMarker_textColor,
                mTextColor);
        mPrimaryColor = a.getColor(
                R.styleable.CircularTimeLineMarker_circleColor,
                mPrimaryColor);
        mAccentColor = a.getColor(
                R.styleable.CircularTimeLineMarker_circleOutlineColor,
                mAccentColor);
        mLineColor = a.getColor(
                R.styleable.CircularTimeLineMarker_lineColor,
                mLineColor);

        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        mFontSize = a.getDimension(
                R.styleable.CircularTimeLineMarker_fontSize,
                mFontSize);
        mOutlineWidth = a.getDimension(
                R.styleable.CircularTimeLineMarker_circleOutlineWidth,
                mOutlineWidth);
        mLineWidth = a.getDimension(
                R.styleable.CircularTimeLineMarker_lineWidth,
                mLineWidth);

        a.recycle();

        // Set up a default TextPaint/Paint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mCircularPaint = new Paint();
        mCircularPaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        mOutlinePaint = new Paint();
        mOutlinePaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        mLinePaint = new Paint();
        mLinePaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();
    }

    private void invalidateTextPaintAndMeasurements() {
        mTextPaint.setTextSize(mFontSize);
        mTextPaint.setColor(mTextColor);
        mTextWidth = mTextPaint.measureText(mExampleString);

        Paint.FontMetrics metrics = mTextPaint.getFontMetrics();
        mTextHeight = metrics.descent - metrics.ascent;

        mCircularPaint.setColor(mPrimaryColor);

        mOutlinePaint.setColor(mAccentColor);

        mLinePaint.setColor(mLineColor);
        mLinePaint.setStrokeWidth(mLineWidth);

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

        // Draw the main line
        if(mOrientation == Orientation.VERTICAL) {
            canvas.drawLine(
                    paddingLeft+contentWidth/2,
                    paddingTop,
                    paddingLeft+contentWidth/2,
                    paddingTop+contentHeight,
                    mLinePaint);
        } else {
            canvas.drawLine(
                    paddingLeft,
                    paddingTop+contentHeight/2,
                    paddingLeft+contentWidth,
                    paddingTop+contentHeight/2,
                    mLinePaint);
        }

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

//        // TODO; guide line
//        Paint paint = new Paint();
//        paint.setColor(Color.RED);
//        paint.setStrokeWidth(2);
//        canvas.drawLine(0, getHeight()/2, getWidth(), getHeight()/2, paint);
//        canvas.drawLine(getWidth()/2, 0, getWidth()/2, getHeight(), paint);

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
            if(mOrientation == Orientation.HORIZONTAL) {
                desiredWidth *= mLineRatioToRadius;
            }
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
            if(mOrientation == Orientation.VERTICAL) {
                desiredHeight *= mLineRatioToRadius;
            }
            desiredHeight += paddingBottom + paddingTop;

            if(heightMode == MeasureSpec.AT_MOST) {
                height = Math.min(desiredHeight, heightSize);
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
