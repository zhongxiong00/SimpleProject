package com.simpleproject.android.widgets;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ProgressBar;

import com.simpleproject.android.R;


public class HorizontalProgressBar extends ProgressBar {
    //默认值
    private int DEAFUALT_PROGRESS_UNREACH_HEIGHH = 10;//dp
    protected int DEAFUALT_PROGRESS_UNREACH_CORLOR = 0xFFD3D6DA;
    private int DEAFUALT_PROGRESS_REACH_HEIGHH = 10;//dp
    protected int DEAFUALT_PROGRESS_REACH_CORLOR = 0xFFFC00D1;
    private int DEAFUALT_PROGRESS_VIEW_WIDTH = 200;//进度条默认宽度

    protected int HorizontalProgresUnReachColor;//总进度颜色
    private int HorizontalProgresUnReachHeight; //总进度高度
    protected int HorizontalProgresReachColor; //已完成进度颜色
    private int HorizontalProgresReachHeight;//已完成进度高度
    private ValueAnimator mAnimator;
    protected Paint mPaint = new Paint();
    protected Path path;

    public HorizontalProgressBar(Context context) {
        this(context, null);
    }

    public HorizontalProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getStyleabletAttr(attrs);
        path = new Path();
    }


    /**
     * 获取自定义属性
     */
    protected void getStyleabletAttr(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.HorizontalProgressBar);
        HorizontalProgresUnReachColor = typedArray.getColor(R.styleable.HorizontalProgressBar_unReachColor, DEAFUALT_PROGRESS_UNREACH_CORLOR);
        HorizontalProgresReachColor = typedArray.getColor(R.styleable.HorizontalProgressBar_reachColor, DEAFUALT_PROGRESS_REACH_CORLOR);
        HorizontalProgresReachHeight = (int) typedArray.getDimension(R.styleable.HorizontalProgressBar_reachHeight, DEAFUALT_PROGRESS_REACH_HEIGHH);
        HorizontalProgresUnReachHeight = (int) typedArray.getDimension(R.styleable.HorizontalProgressBar_unReachHeight, DEAFUALT_PROGRESS_UNREACH_HEIGHH);
        typedArray.recycle();
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = measureWidth(widthMeasureSpec);//计算宽高
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);//设置宽高
    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();//save、restore 图层的保存和回滚相关的方法 详见 http://blog.csdn.net/tianjian4592/article/details/45234419
        canvas.translate(0, getHeight() / 2);//移动图层到垂直居中位置
        mPaint.setColor(HorizontalProgresUnReachColor);
        mPaint.setStrokeWidth(HorizontalProgresUnReachHeight);
        float realWidth = getWidth() - getPaddingLeft() - getPaddingRight();
        RectF mRectF2 = new RectF(0, getPaddingTop() - HorizontalProgresUnReachHeight / 2, realWidth, HorizontalProgresUnReachHeight / 2);//圆角 int left, int top, int right, int bottom
        canvas.drawRoundRect(mRectF2, 15, 15, mPaint);//圆角矩形
        float radio = getProgress() * 1.0f / getMax();
        float progressX = radio * realWidth;
        //绘制走完的进度线
        mPaint.setColor(HorizontalProgresReachColor);
        mPaint.setStrokeWidth(HorizontalProgresReachHeight);
        //canvas.drawLine(getPaddingLeft(),getPaddingTop(),progressX,getPaddingTop(),mPaint);//直角 垂直在同一高度 float startY, float stopY 一样
        RectF mRectF = new RectF(getPaddingLeft(), getPaddingTop() - HorizontalProgresReachHeight / 2, (int) progressX, HorizontalProgresReachHeight / 2);//圆角 int left, int top, int right, int bottom
        canvas.drawRoundRect(mRectF, 15, 15, mPaint);//圆角矩形
        canvas.restore();
    }

    public void setProgressWithAnimation(int progress) {
        if (mAnimator == null) {
            mAnimator = ValueAnimator.ofInt(0, progress);
        }
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int now = (int) animation.getAnimatedValue();
                setProgress(now);
                invalidate();
            }
        });
        mAnimator.setDuration(500);
        mAnimator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mAnimator != null) {
            mAnimator.cancel();
            mAnimator = null;
        }
    }

    /**
     * Determines the width of this view
     *
     * @param measureSpec A measureSpec packed into an int
     * @return The width of the view, honoring constraints from measureSpec
     */
    protected int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            result = specSize;
        } else {
            // Measure the text
            result = DEAFUALT_PROGRESS_VIEW_WIDTH;
            if (specMode == MeasureSpec.AT_MOST) {
                // Respect AT_MOST value if that was what is called for by measureSpec
                result = Math.min(result, specSize);
            }
        }

        return result;
    }

    /**
     * Determines the height of this view
     *
     * @param measureSpec A measureSpec packed into an int
     * @return The height of the view, honoring constraints from measureSpec
     */
    protected int measureHeight(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            result = specSize;
        } else {
            // Measure the text (beware: ascent is a negative number)
            //此处高度为走完的进度高度和未走完的机大以及文字的高度的最大值
            int textHeight = (int) (mPaint.descent() - mPaint.ascent());//得到字的高度有二种方式,第一种是React,第二种这个
            result = Math.max(textHeight, Math.max(HorizontalProgresReachHeight, HorizontalProgresUnReachHeight)) + getPaddingTop()
                    + getPaddingBottom();
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

}
