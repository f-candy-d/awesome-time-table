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

    private enum MarkerGravity {
        TOP_OR_LEFT(0),
        BOTTOM_OR_RIGHT(1),
        CENTER(2);

        int mID;

        MarkerGravity(int id) {
            mID = id;
        }

        int toInt() {
            return mID;
        }

        static MarkerGravity fromID(int id) {
            for(MarkerGravity type : values()) {
                if(type.mID == id) {
                    return type;
                }
            }
            throw new IllegalArgumentException(
                    "id=" + String.valueOf(id) + "is not supported");
        }
    }

    // TODO: Set default values...
    /**
     * Text
     */
    // attributes
    private String mText = "1";
    private float mFontSize = 0;
    private int mTextColor = Color.WHITE;

    // runtime
    private float mTextWidth;
    private float mTextHeight;

    /**
     * Marker
     */
    // attributes
    private int mMarkerColor = Color.BLUE;
    private int mMarkerOutlineColor = mMarkerColor;
    private float mMarkerOutlineWidth = 0;
    private float mMarkerSizeRatioToFont = 1.3f;
    private float mPaddingMarker = 0;
    private float mMarginMarkerStart = 0;
    private float mMarginMarkerEnd = 0;
    private MarkerGravity mMarkerGravity = MarkerGravity.TOP_OR_LEFT;

    // runtime
    private int mMarkerRadius = 0;

    /**
     * SubMarker
     */
    // attributes
    private float mSubMarkerSizeRatioToMarkerSize = 0.6f;
    private int mNumSubMarker = 0;
    private int mSubMarkerColor = mMarkerColor;

    // runtime
    private int mSubMarkerRadius = 0;

    /**
     * Line
     */
    // attributes
    private int mLineColor = mMarkerColor;
    private int mOutlineColor = mMarkerOutlineColor;
    private float mLineWidth = 0;
    private float mOutlineWidth = 0;
    private float mLineLengthRatioToMarkerSize = 1.3f;
    private boolean mDrawRunningOverLineStart = true;
    private boolean mDrawRunningOverLineEnd = true;

    /**
     * Paint objects
     */
    private TextPaint mTextPaint;
    private Paint mMarkerPaint;
    private Paint mMarkerOutlinePaint;
    private Paint mSubMarkerPaint;
    private Paint mLinePaint;
    private Paint mOutlinePaint;

    /**
     * Whole
     */
    // attributes
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

        loadAttributes(attrs, defStyle);

        // Set up a default TextPaint/Paint object
        // Text
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        // Marker
        mMarkerPaint = new Paint();
        mMarkerPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        // Sub Marker
        mSubMarkerPaint = new Paint();
        mSubMarkerPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        // Outline of the Marker
        mMarkerOutlinePaint = new Paint();
        mMarkerOutlinePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        // Line
        mLinePaint = new Paint();
        mLinePaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        // Outline of the Line
        mOutlinePaint = new Paint();
        mOutlinePaint.setFlags(Paint.ANTI_ALIAS_FLAG);

        // Update TextPaint/Paint object and some measurements from attributes
        invalidateTextPaintAndMeasurements();
        invalidateMarkerPaintAndMeasurements();
        invalidateSubMarkerPaintAndMeasurements();
        invalidateLinePaintAndMeasurements();
    }

    private void invalidateMarkerPaintAndMeasurements() {
        mMarkerPaint.setColor(mMarkerColor);

        mMarkerOutlinePaint.setColor(mMarkerOutlineColor);

    }

    private void invalidateSubMarkerPaintAndMeasurements() {
        mSubMarkerPaint.setColor(mSubMarkerColor);
    }

    private void invalidateLinePaintAndMeasurements() {
        mLinePaint.setColor(mLineColor);
        float actualLineWidth = mLineWidth - mOutlineWidth*2;
        actualLineWidth = (actualLineWidth < 0f) ? 0f : actualLineWidth;
        mLinePaint.setStrokeWidth(actualLineWidth);

        mOutlinePaint.setColor(mOutlineColor);
        float actualOutlineWidth = mLineWidth;
        mOutlinePaint.setStrokeWidth(actualOutlineWidth);
    }

    private void invalidateTextPaintAndMeasurements() {
        mTextPaint.setTextSize(mFontSize);
        mTextPaint.setColor(mTextColor);

        mTextWidth = mTextPaint.measureText(mText);

        Paint.FontMetrics metrics = mTextPaint.getFontMetrics();
        mTextHeight = metrics.descent - metrics.ascent;
    }

    private void loadAttributes(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.CircularTimeLineMarker, defStyle, 0);

        // Whole
        mOrientation = Orientation.fromId(a.getInt(
                R.styleable.CircularTimeLineMarker_orientation,
                mOrientation.toInt()));

        // Text
        mText = a.getString(
                R.styleable.CircularTimeLineMarker_text);
        mTextColor = a.getColor(
                R.styleable.CircularTimeLineMarker_textColor,
                mTextColor);
        mFontSize = a.getDimension(
                R.styleable.CircularTimeLineMarker_fontSize,
                mFontSize);

        // Marker
        mMarkerGravity = MarkerGravity.fromID(a.getInt(
                R.styleable.CircularTimeLineMarker_markerGravity,
                mMarkerGravity.toInt()));
        mMarkerColor = a.getColor(
                R.styleable.CircularTimeLineMarker_circleColor,
                mMarkerColor);
        mMarkerOutlineColor = a.getColor(
                R.styleable.CircularTimeLineMarker_circleOutlineColor,
                mMarkerOutlineColor);
        mMarkerOutlineWidth = a.getDimension(
                R.styleable.CircularTimeLineMarker_circleOutlineWidth,
                mMarkerOutlineWidth);
        mPaddingMarker = a.getDimension(
                R.styleable.CircularTimeLineMarker_paddingCircle,
                mPaddingMarker);
        mMarginMarkerStart = a.getDimension(
                R.styleable.CircularTimeLineMarker_marginMarkerStart,
                mMarginMarkerStart);
        mMarginMarkerEnd = a.getDimension(
                R.styleable.CircularTimeLineMarker_marginMarkerEnd,
                mMarginMarkerEnd);
        mMarkerSizeRatioToFont = a.getFloat(
                R.styleable.CircularTimeLineMarker_markerSizeRatioToFontSize,
                mMarkerSizeRatioToFont);


        // Sub Marker
        mSubMarkerColor = a.getColor(
                R.styleable.CircularTimeLineMarker_subMarkerColor,
                mSubMarkerColor);
        mNumSubMarker = a.getInt(
                R.styleable.CircularTimeLineMarker_numOfSubMarker,
                mNumSubMarker);
        mSubMarkerSizeRatioToMarkerSize = a.getFloat(
                R.styleable.CircularTimeLineMarker_subMarkerSizeRatioToMarker,
                mSubMarkerSizeRatioToMarkerSize);

        // Line
        mLineColor = a.getColor(
                R.styleable.CircularTimeLineMarker_lineColor,
                mLineColor);
        mOutlineColor = a.getColor(
                R.styleable.CircularTimeLineMarker_outlineColor,
                mOutlineColor);
        mDrawRunningOverLineStart = a.getBoolean(
                R.styleable.CircularTimeLineMarker_drawRunningOverLineStart,
                mDrawRunningOverLineStart);
        mDrawRunningOverLineEnd = a.getBoolean(
                R.styleable.CircularTimeLineMarker_drawRunningOverLineEnd,
                mDrawRunningOverLineEnd);
        mLineWidth = a.getDimension(
                R.styleable.CircularTimeLineMarker_lineWidth,
                mLineWidth);
        mOutlineWidth = a.getDimension(
                R.styleable.CircularTimeLineMarker_outlineWidth,
                mOutlineWidth);
        mLineLengthRatioToMarkerSize = a.getFloat(
                R.styleable.CircularTimeLineMarker_lineLengthRatioToMarkerSize,
                mLineLengthRatioToMarkerSize);

        a.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        // Center position of the marker
        float cxPos;
        float cyPos;
        if(mOrientation == Orientation.VERTICAL) {
            cxPos = paddingLeft + contentWidth/2;
            cyPos = paddingTop + mMarginMarkerStart;
            if(mMarkerGravity == MarkerGravity.TOP_OR_LEFT) {
                cyPos += mMarkerRadius;
            } else if(mMarkerGravity == MarkerGravity.CENTER) {
                cyPos += contentHeight/2;
            } else if(mMarkerGravity == MarkerGravity.BOTTOM_OR_RIGHT) {
                cyPos += contentHeight - mMarkerRadius - mMarginMarkerEnd;
            }

        } else {
            cyPos = paddingTop + contentHeight/2;
            cxPos = paddingLeft + mMarginMarkerStart;
            if(mMarkerGravity == MarkerGravity.TOP_OR_LEFT) {
                cxPos += mMarkerRadius;
            } else if(mMarkerGravity == MarkerGravity.CENTER) {
                cxPos += contentWidth/2;
            } else if(mMarkerGravity == MarkerGravity.BOTTOM_OR_RIGHT) {
                cxPos += contentWidth - mMarkerRadius - mMarginMarkerEnd;
            }
        }


        // Draw the Line
        if(mOrientation == Orientation.VERTICAL) {
            float lx = paddingLeft + contentWidth/2;
            float ly1 = (mDrawRunningOverLineStart) ? paddingTop : cyPos;
            float ly2 = (mDrawRunningOverLineEnd)
                    ? paddingTop + contentHeight
                    : cyPos;
            if(0 < mNumSubMarker) {
                float drawableAreaSize = contentHeight - mMarginMarkerStart - mMarginMarkerEnd;
                float diff = drawableAreaSize / (mNumSubMarker + 1);
                ly2 += diff * mNumSubMarker;
            }

            if(mOutlineWidth != 0f) {
                // Outline
                canvas.drawLine(lx, ly1, lx, ly2, mOutlinePaint);
            }
            // Line
            canvas.drawLine(lx, ly1, lx, ly2, mLinePaint);

        } else {
            float ly = paddingTop + contentHeight/2;
            float lx1 = (mDrawRunningOverLineStart) ? paddingLeft : cxPos;
            float lx2 = (mDrawRunningOverLineEnd)
                    ? paddingLeft + contentWidth
                    : cxPos;
            if (0 < mNumSubMarker) {
                float drawableAreaSize = contentWidth- mMarginMarkerStart - mMarginMarkerEnd;
                float diff = drawableAreaSize / (mNumSubMarker+1);
                lx2 += diff * mNumSubMarker;
            }

            if(mOutlineWidth != 0f) {
                // Outline
                canvas.drawLine(lx1, ly, lx2, ly, mOutlinePaint);
            }
            // Line
            canvas.drawLine(lx1, ly, lx2, ly, mLinePaint);
        }

        // Draw the Marker
        // Outline of the marker
        if(mMarkerOutlineWidth != 0f) {
            canvas.drawCircle(cxPos, cyPos, mMarkerRadius, mMarkerOutlinePaint);
        }
        // Marker
        canvas.drawCircle(cxPos, cyPos, mMarkerRadius - mMarkerOutlineWidth, mMarkerPaint);

        // Sub markers
        if(0 < mNumSubMarker) {
            if (mOrientation == Orientation.VERTICAL) {
                float drawableAreaSize = contentHeight - mMarginMarkerStart - mMarginMarkerEnd;
                float diff = drawableAreaSize / (mNumSubMarker + 1);

                for (int i = 1; i <= mNumSubMarker; ++i) {
                    canvas.drawCircle(cxPos, cyPos + diff * i, mSubMarkerRadius, mSubMarkerPaint);
                }

            } else {
                float drawableAreaSize = contentWidth- mMarginMarkerStart - mMarginMarkerEnd;
                float diff = drawableAreaSize / (mNumSubMarker+1);

                for(int i = 1; i <= mNumSubMarker; ++i) {
                    canvas.drawCircle(cxPos + diff * i, cyPos, mSubMarkerRadius, mSubMarkerPaint);
                }
            }
        }

        // Text
        if(mOrientation == Orientation.VERTICAL) {
            Paint.FontMetrics metrics = mTextPaint.getFontMetrics();
            float xPos = paddingLeft + contentWidth/2;
            float yPos = cyPos + mTextHeight/2 - metrics.descent;
            canvas.drawText(mText, xPos, yPos, mTextPaint);

        } else {
            Paint.FontMetrics metrics = mTextPaint.getFontMetrics();
            float xPos = paddingTop + contentHeight/2;
            float yPos = cxPos + mTextHeight/2 - metrics.descent;
            canvas.drawText(mText, xPos, yPos, mTextPaint);
        }
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
                    ? (int) (mTextHeight * mMarkerSizeRatioToFont)
                    : (int) (mTextWidth * mMarkerSizeRatioToFont);
            if(mOrientation == Orientation.HORIZONTAL) {
                // Consider the length of the line
                desiredWidth *= mLineLengthRatioToMarkerSize;
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
                    ? (int) (mTextHeight * mMarkerSizeRatioToFont)
                    : (int) (mTextWidth  * mMarkerSizeRatioToFont);
            if(mOrientation == Orientation.VERTICAL) {
                // Consider the length of the line
                desiredHeight *= mLineLengthRatioToMarkerSize;
            }
            desiredHeight += paddingBottom + paddingTop;

            if(heightMode == MeasureSpec.AT_MOST) {
                height = Math.min(desiredHeight, heightSize);
            } else {
                height = desiredHeight;
            }
        }

        // Measure radius of the circle
        mMarkerRadius = (width < height)
                ? (int) (width-paddingLeft-paddingRight- mPaddingMarker *2) / 2
                : (int) (height-paddingBottom-paddingTop- mPaddingMarker *2) / 2;
        mSubMarkerRadius = (int) (mMarkerRadius * mSubMarkerSizeRatioToMarkerSize);

        setMeasuredDimension(width, height);
    }

    /**
     * Accessor & Mutators for attribute variables
     */

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
        invalidateTextPaintAndMeasurements();
    }

    public float getFontSize() {
        return mFontSize;
    }

    public void setFontSize(float fontSize) {
        mFontSize = fontSize;
        invalidateTextPaintAndMeasurements();
    }

    public int getTextColor() {
        return mTextColor;
    }

    public void setTextColor(int textColor) {
        mTextColor = textColor;
        invalidateTextPaintAndMeasurements();
    }

    public int getMarkerColor() {
        return mMarkerColor;
    }

    public void setMarkerColor(int markerColor) {
        mMarkerColor = markerColor;
        invalidateMarkerPaintAndMeasurements();
    }

    public int getMarkerOutlineColor() {
        return mMarkerOutlineColor;
    }

    public void setMarkerOutlineColor(int markerOutlineColor) {
        mMarkerOutlineColor = markerOutlineColor;
        invalidateMarkerPaintAndMeasurements();
    }

    public float getMarkerOutlineWidth() {
        return mMarkerOutlineWidth;
    }

    public void setMarkerOutlineWidth(float markerOutlineWidth) {
        mMarkerOutlineWidth = markerOutlineWidth;
    }

    public float getMarkerSizeRatioToFont() {
        return mMarkerSizeRatioToFont;
    }

    public void setMarkerSizeRatioToFont(float radiusRatioToFont) {
        mMarkerSizeRatioToFont = radiusRatioToFont;
    }

    public float getPaddingMarker() {
        return mPaddingMarker;
    }

    public void setPaddingMarker(float paddingMarker) {
        mPaddingMarker = paddingMarker;
    }

    public float getMarginMarkerStart() {
        return mMarginMarkerStart;
    }

    public void setMarginMarkerStart(float marginMarkerStart) {
        mMarginMarkerStart = marginMarkerStart;
    }

    public float getMarginMarkerEnd() {
        return mMarginMarkerEnd;
    }

    public void setMarginMarkerEnd(float marginMarkerEnd) {
        mMarginMarkerEnd = marginMarkerEnd;
    }

    public MarkerGravity getMarkerGravity() {
        return mMarkerGravity;
    }

    public void setMarkerGravity(MarkerGravity markerGravity) {
        mMarkerGravity = markerGravity;
    }

    public float getSubMarkerSizeRatioToRadius() {
        return mSubMarkerSizeRatioToMarkerSize;
    }

    public void setSubMarkerSizeRatioToRadius(float subMarkerRadiusRatioToRadius) {
        mSubMarkerSizeRatioToMarkerSize = subMarkerRadiusRatioToRadius;
    }

    public int getNumSubMarker() {
        return mNumSubMarker;
    }

    public void setNumSubMarker(int numSubMarker) {
        mNumSubMarker = numSubMarker;
    }

    public int getSubMarkerColor() {
        return mSubMarkerColor;
    }

    public void setSubMarkerColor(int subMarkerColor) {
        mSubMarkerColor = subMarkerColor;
        invalidateSubMarkerPaintAndMeasurements();
    }

    public int getLineColor() {
        return mLineColor;
    }

    public void setLineColor(int lineColor) {
        mLineColor = lineColor;
        invalidateLinePaintAndMeasurements();
    }

    public int getOutlineColor() {
        return mOutlineColor;
    }

    public void setOutlineColor(int outlineColor) {
        mOutlineColor = outlineColor;
        invalidateLinePaintAndMeasurements();
    }

    public float getLineWidth() {
        return mLineWidth;
    }

    public void setLineWidth(float lineWidth) {
        mLineWidth = lineWidth;
        invalidateLinePaintAndMeasurements();
    }

    public float getOutlineWidth() {
        return mOutlineWidth;
    }

    public void setOutlineWidth(float outlineWidth) {
        mOutlineWidth = outlineWidth;
        invalidateLinePaintAndMeasurements();
    }

    public float getLineLengthRatioToMarkerSize() {
        return mLineLengthRatioToMarkerSize;
    }

    public void setLineLengthRatioToMarkerSize(float lineLengthRatioToRadius) {
        mLineLengthRatioToMarkerSize = lineLengthRatioToRadius;
    }

    public boolean isDrawRunningOverLineStart() {
        return mDrawRunningOverLineStart;
    }

    public void enableDrawRunningOverLineStart(boolean drawRunningOverLineStart) {
        mDrawRunningOverLineStart = drawRunningOverLineStart;
    }

    public boolean isDrawRunningOverLineEnd() {
        return mDrawRunningOverLineEnd;
    }

    public void enableDrawRunningOverLineEnd(boolean drawRunningOverLineEnd) {
        mDrawRunningOverLineEnd = drawRunningOverLineEnd;
    }

    public Orientation getOrientation() {
        return mOrientation;
    }

    public void setOrientation(Orientation orientation) {
        mOrientation = orientation;
    }
}
