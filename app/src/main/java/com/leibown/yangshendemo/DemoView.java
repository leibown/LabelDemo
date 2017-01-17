package com.leibown.yangshendemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2017/1/16.
 */

public class DemoView extends View {

    private int mWidth, mHeight;

    private float mRadius;//大圆圈的半径
    private Paint mCenterCirclePaint;//中间大圆圈的画笔

    private Paint mLabelPaint;
    private Paint mLabelBorderPaint;

    private Path mPath;//大圆圈的路径
    private Bitmap mBitmap;//大圆圈的图片
    private Matrix mMatrix;//中间大圆圈的矩阵

    private List<SmallLabelInfo> mLocationIndexs;//坐标位置的集合
    private List<SmallLabelInfo> smallLabelInfos;

    private String[] mSmallLabels;//小标签的集合

    private int mColumnNum = 3;//有多少列


    public DemoView(Context context) {
        this(context, null);
    }

    public DemoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DemoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mLocationIndexs = new ArrayList<>();
        smallLabelInfos = new ArrayList<>();
        setColumnNum(mColumnNum);

        mCenterCirclePaint = new Paint();
        mCenterCirclePaint.setColor(Color.BLACK);
        mCenterCirclePaint.setStrokeWidth(30);
        mCenterCirclePaint.setAntiAlias(true);
        mCenterCirclePaint.setStyle(Paint.Style.STROKE);

        mLabelPaint = new Paint();
        mLabelPaint.setColor(Color.parseColor("#32eeeeee"));
        mLabelPaint.setStrokeWidth(50);
        mLabelPaint.setAntiAlias(true);
        mLabelPaint.setStyle(Paint.Style.FILL);

        mLabelBorderPaint = new Paint();
        mLabelBorderPaint.setColor(Color.WHITE);
        mLabelBorderPaint.setStrokeWidth(5);
        mLabelBorderPaint.setAntiAlias(true);
        mLabelBorderPaint.setStyle(Paint.Style.STROKE);

        mPath = new Path();
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.touxiang);
        mMatrix = new Matrix();

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
        mRadius = Math.min(mWidth / 4, mHeight / 4) * 0.6f;

        mPath.addCircle(0, 0, mRadius, Path.Direction.CCW);

        if (mSmallLabels == null || mSmallLabels.length == 0) {
            return;
        }

        Region globalRegion = new Region(-w, -h, w, h);
        int length;
        if (mSmallLabels.length > mLocationIndexs.size()) {
            length = mLocationIndexs.size();
        } else
            length = mSmallLabels.length;

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int randomNum = random.nextInt(mLocationIndexs.size());
            SmallLabelInfo smallLabelInfo = mLocationIndexs.get(randomNum);
            mLocationIndexs.remove(randomNum);
            initSmallLabels(smallLabelInfo, globalRegion, mSmallLabels[i]);
        }
//        for (int i = 0; i < mLocationIndexs.size(); i++) {
//            SmallLabelInfo smallLabelInfo = mLocationIndexs.get(i);
//            initSmallLabels(smallLabelInfo, globalRegion, "天气");
//        }
    }

    /**
     * 初始化小标签的集合
     *
     * @param smallLabelInfo 小标签的位置信息
     * @param globalRegion
     * @param title          小标签的文字
     */
    private void initSmallLabels(SmallLabelInfo smallLabelInfo, Region globalRegion, String title) {
        Path smallLabelPath = new Path();
        Path smallLabelBorderPath = new Path();

        Region circleRegion = new Region();
        float smallLabelWidth;//小标签的外边框宽度
        float smallLabelHeight;//小标签的外边框高度度
        float smallLabelLocationX;//小标签的外边框起点X坐标
        float smallLabelLocationY;//小标签的外边框起点Y坐标

        float smallWidth = (mWidth - 2 * mRadius) / 2;
        float smallHeight = (mHeight - 2 * mRadius) / 2;

        if (smallLabelInfo.getLocationX() == 0) {
            smallLabelWidth = smallWidth;
            smallLabelLocationX = 0;
            if (smallLabelInfo.getLocationY() == 0) {
                smallLabelLocationY = 0;
                smallLabelHeight = smallHeight;
                Log.e("leibown", "1111111");
            } else if (smallLabelInfo.getLocationY() == mColumnNum - 1) {
                smallLabelLocationY = mHeight / 2 + mRadius;
                smallLabelHeight = smallHeight;
                Log.e("leibown", "222222222");
            } else {
                smallLabelLocationY = smallHeight + (smallLabelInfo.getLocationY() - 1) * mRadius;
                smallLabelHeight = 2 * mRadius / (mColumnNum - 2);
                Log.e("leibown", "333333");
            }
        } else if (smallLabelInfo.getLocationX() == mColumnNum - 1) {
            smallLabelWidth = smallWidth;
            smallLabelLocationX = smallWidth + 2 * mRadius;
            if (smallLabelInfo.getLocationY() == 0) {
                smallLabelLocationY = 0;
                smallLabelHeight = smallHeight;
                Log.e("leibown", "4444444");
            } else if (smallLabelInfo.getLocationY() == mColumnNum - 1) {
                smallLabelLocationY = mHeight / 2 + mRadius;
                smallLabelHeight = smallHeight;
                Log.e("leibown", "55555555");
            } else {
                smallLabelLocationY = smallHeight + (smallLabelInfo.getLocationY() - 1) * mRadius;
                smallLabelHeight = 2 * mRadius / (mColumnNum - 2);
                Log.e("leibown", "6666666");
            }
        } else {
            smallLabelLocationX = smallWidth + (smallLabelInfo.getLocationX() - 1) * mRadius;
            smallLabelWidth = 2 * mRadius / (mColumnNum - 2);

            if (smallLabelInfo.getLocationY() == 0) {
                smallLabelLocationY = 0;
                smallLabelHeight = smallHeight;
                Log.e("leibown", "77777");
            } else if (smallLabelInfo.getLocationY() == mColumnNum - 1) {
                smallLabelLocationY = mHeight / 2 + mRadius;
                smallLabelHeight = smallHeight;
                Log.e("leibown", "88888");
            } else {
                smallLabelLocationY = smallHeight + (smallLabelInfo.getLocationY() - 1) * mRadius;
                smallLabelHeight = smallHeight;
                Log.e("leibown", "999999");
            }
        }

        if (smallLabelWidth == 0 || smallLabelHeight == 0)
            return;

        float smallLabelRadius = Math.min(smallWidth, smallHeight) / 2 * 0.45F;
        //计算小标签圆心位置
        float x = getRandomLocation(smallLabelRadius, smallLabelLocationX, smallLabelWidth);
        float y = getRandomLocation(smallLabelRadius, smallLabelLocationY, smallLabelHeight);
        smallLabelPath.addCircle(x, y, smallLabelRadius, Path.Direction.CW);
        smallLabelBorderPath.addCircle(x, y, smallLabelRadius, Path.Direction.CW);
        circleRegion.setPath(smallLabelPath, globalRegion);

        SmallLabelInfo info = new SmallLabelInfo();
        info.setCirclePath(smallLabelPath);
        info.setRegion(circleRegion);
        info.setTitle(title);
        info.setCircleBorderPath(smallLabelBorderPath);
        smallLabelInfos.add(info);
    }

    /**
     * 根据起点终点的坐标算出小圆心的位置
     *
     * @param radius 小圆的半径
     * @param start  起点坐标（可以是X，可以是Y）
     * @param end    终点坐标（可以是X，可以是Y）
     * @return
     */
    private float getRandomLocation(float radius, float start, float end) {
        Log.e("leibown", "radius:" + radius + "_start:" + start + "_end:" + end);
        Random random = new Random();
        return random.nextInt((int) (end - 2 * radius)) + start + radius;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (SmallLabelInfo info : smallLabelInfos) {
            canvas.drawPath(info.getCirclePath(), mLabelPaint);
            canvas.drawPath(info.getCircleBorderPath(), mLabelBorderPaint);
        }

        canvas.save();
        canvas.translate(mWidth / 2, mHeight / 2);
        mLabelPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(mPath, mLabelPaint);
//        canvas.drawColor(Color.GRAY);
        canvas.clipPath(mPath, Region.Op.INTERSECT);
        float scale = 2 * mRadius / Math.min(mBitmap.getWidth(), mBitmap.getHeight());
        mMatrix.postScale(scale, scale);
        float translationX = mBitmap.getWidth() * scale / 2;
        float translationY = mBitmap.getHeight() * scale / 2;
        mMatrix.postTranslate(-translationX, -translationY);

        canvas.drawBitmap(mBitmap, mMatrix, mCenterCirclePaint);
        canvas.restore();

//        canvas.drawLine(0, (mHeight - 2 * mRadius) / 2, mWidth, (mHeight - 2 * mRadius) / 2, mLabelPaint);
//        canvas.drawLine(0, (mHeight - 2 * mRadius) / 2 + 2 * mRadius, mWidth, (mHeight - 2 * mRadius) / 2 + 2 * mRadius, mLabelPaint);
//
//        canvas.drawLine((mWidth - 2 * mRadius) / 2, 0, (mWidth - 2 * mRadius) / 2, mHeight, mLabelPaint);
//        canvas.drawLine((mWidth - 2 * mRadius) / 2 + 2 * mRadius, 0, (mWidth - 2 * mRadius) / 2 + 2 * mRadius, mHeight, mLabelPaint);

    }

    public void setData(String[] mSmallLabels) {
        this.mSmallLabels = mSmallLabels;
        Log.e("leibown", "mWidth:" + mWidth);
        invalidate();
    }

    public void setColumnNum(int columnNum) {
        this.mColumnNum = columnNum;
        mLocationIndexs.clear();
        for (int i = 0; i < mColumnNum; i++) {
            for (int j = 0; j < mColumnNum; j++) {
                if (i == 0 || i == mColumnNum - 1 || j == 0 || j == mColumnNum - 1) {
                    mLocationIndexs.add(new SmallLabelInfo(i, j));
                }
            }
        }
        Log.e("leibown", mLocationIndexs.toString());
        Log.e("leibown", mLocationIndexs.size() + "");
    }
}
