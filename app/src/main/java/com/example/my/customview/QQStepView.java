package com.example.my.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ckh.
 * date : 2019/1/22 21:13
 * desc :
 */
public class QQStepView extends View {
    private Paint mInnerPaint;
    private Paint mOuterPaint;
    private Paint mTextPaint;
    private int mInnerColor = Color.BLUE;
    private int mOuterColor = Color.RED;
    private int mBorderWidth = 20;//20px
    private int mStepTextColor;
    private int mStepTextSize;

    private int mCurrentStep = 0;//当前步数
    private int mMaxStep = 0;//最大步数
    public QQStepView(Context context) {
        this(context,null);
    }

    public QQStepView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public QQStepView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.QQStepView);
        mInnerColor = typedArray.getColor(R.styleable.QQStepView_innerColor, mInnerColor);
        mOuterColor = typedArray.getColor(R.styleable.QQStepView_outerColor, mOuterColor);
        mBorderWidth = (int) typedArray.getDimension(R.styleable.QQStepView_borderWidth,mBorderWidth);
        mStepTextSize = typedArray.getDimensionPixelSize(R.styleable.QQStepView_stepTextSize,mStepTextSize);
        mStepTextColor = typedArray.getColor(R.styleable.QQStepView_stepTextColor,mStepTextColor);
        typedArray.recycle();

        mOuterPaint = new Paint();
        mOuterPaint.setAntiAlias(true);
        mOuterPaint.setStrokeWidth(mBorderWidth);
        mOuterPaint.setColor(mOuterColor);
        mOuterPaint.setStrokeCap(Paint.Cap.ROUND);
        mOuterPaint.setStyle(Paint.Style.STROKE);


        mInnerPaint = new Paint();
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setStrokeWidth(mBorderWidth);
        mInnerPaint.setColor(mInnerColor);
        mInnerPaint.setStrokeCap(Paint.Cap.ROUND);
        mInnerPaint.setStyle(Paint.Style.STROKE);//设置空心


        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(Color.RED);
        mTextPaint.setTextSize(mStepTextSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(widthSize > heightSize ? heightSize : widthSize,widthSize > heightSize ? heightSize : widthSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int center = getWidth() / 2;
        int radius = getWidth() / 2 - mBorderWidth / 2;
            //绘制外圆弧
        RectF rectF = new RectF(center - radius,center - radius,
                center + radius,center + radius);
        canvas.drawArc(rectF,135,270,false,mOuterPaint);
        //绘制内圆弧
        if (mMaxStep == 0)return;
        float angle = 270 * mCurrentStep / mMaxStep;
        canvas.drawArc(rectF,135,angle,false,mInnerPaint);
        //绘制文字
        String stepText = mCurrentStep + "";
        Rect textBound = new Rect();
        mTextPaint.getTextBounds(stepText,0,stepText.length(),textBound);
        int dx = getWidth() / 2 - textBound.width() / 2;
        //基线
        Paint.FontMetricsInt fontMetricsInt = mTextPaint.getFontMetricsInt();
        int dy = (fontMetricsInt.bottom - fontMetricsInt.top) / 2 - fontMetricsInt.descent;
        int baseLine = getHeight() / 2 + dy;
        canvas.drawText(stepText,dx,baseLine,mTextPaint);

    }

    public synchronized void setCurrentStep(int currentStep) {
        mCurrentStep = currentStep;
        //不断绘制
        invalidate();
    }

    public synchronized void setMaxStep(int maxStep) {
        mMaxStep = maxStep;

    }
}
