package com.li.mykotlinapp.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.apkfuns.logutils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/************************************************************************
 *@Project: MyKotlinApp
 *@Package_Name: com.li.mykotlinapp.test
 *@Descriptions:
 *@Author: zhouli
 *@Date: 2023/9/21
 *@Copyright:(C)2023 苏州易程创新科技有限公司. All rights reserved.
 *************************************************************************/
public class CircleDrawView extends View {
    //滴定
    public static final int MODE_TITRATION = 1;
    //自由填充
    public static final int MODE_FREE_FILL = 2;
    //矩形填充
    public static final int MODE_RECT_FILL = 4;
    //橡皮擦
    public static final int MODE_ERASER = 5;
    //当前绘图模式
    public int currentMode = 2;
    //是否为交错模式。交错模式所有小圆点之间距离相等
    public boolean isCrossMode = false;

    private Paint paint;
    private List<PointF> pointList;

    private Path path;
    private Paint pathPaint;
    private Paint circlePaint;
    private float radius;
    //间距
    private float spacing;
    private List<CircleBean> circleCenters;
    private List<CircleBean> tmpCircleCenters;

    //是否开始画封闭圈内的小圆点
    private boolean isDrawCircleInPath = false;
    //矩形填充
    private boolean isDrawingRect;
    private float startRectX;
    private float startRectY;
    private RectF rect;
    private float currentRectX;
    private float currentRectY;

    // 禁止区域矩形
    private RectF forbiddenRect;
    //橡皮擦大小,默认50
    private int eraserSize=50;
    private int selectedCircleIndex = -1;


    public CircleDrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        pointList = new ArrayList<>();

        path = new Path();
        pathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pathPaint.setColor(Color.BLUE);
        pathPaint.setStyle(Paint.Style.STROKE);
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(Color.BLACK);
        circlePaint.setStyle(Paint.Style.STROKE);
        radius = 20; // 小圆圈的半径
        spacing = 40; // 小圆圈的间距(边界距离)
        circleCenters = new ArrayList<>();
        tmpCircleCenters = new ArrayList<>();
        //矩形填充
        isDrawingRect = false;
        // 定义禁止区域矩形的坐标
        forbiddenRect = new RectF(300, 300, 400, 400);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                PointF downPoint = new PointF(event.getX(), event.getY());
//                pointList.add(downPoint);
//                invalidate();
//                return true;
//            case MotionEvent.ACTION_MOVE:
//                if (pointList.size() > 0) {
//                    PointF lastPoint = pointList.get(pointList.size() - 1);
//                    float distance = calculateDistance(event.getX(), event.getY(), lastPoint.x, lastPoint.y);
//                    if (distance >= 100) {
//                        PointF movePoint = new PointF(event.getX(), event.getY());
//                        pointList.add(movePoint);
//                        invalidate();
//                    }
//                }
//                return true;
//            case MotionEvent.ACTION_UP:
//                return true;
//        }
//        return super.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                tmpCircleCenters.clear();
                switch (currentMode) {
                    //滴定
                    case MODE_TITRATION: {
                        tmpCircleCenters.add(new CircleBean(new PointF(x, y), MODE_TITRATION));
                        removeTooClosePoint(circleCenters, tmpCircleCenters);
                        break;
                    }
                    //自由填充
                    case MODE_FREE_FILL: {
                        path.reset();
                        path.moveTo(x, y);
                        break;
                    }
                    //矩形填充
                    case MODE_RECT_FILL: {
                        startRectX = x;
                        startRectY = y;
                        currentRectX = x;
                        currentRectY = y;
                        isDrawingRect = true;
                        break;
                    }
                    //橡皮擦
                    case MODE_ERASER: {
                        float touchX = event.getX();
                        float touchY = event.getY();
                        eraseCirclesAtPoint(touchX, touchY);
                        invalidate();
                        break;
                    }
                }

                break;
            case MotionEvent.ACTION_MOVE:
                switch (currentMode) {
                    //自由填充
                    case MODE_FREE_FILL: {
                        path.lineTo(x, y);
                        break;
                    }
                    //矩形填充
                    case MODE_RECT_FILL: {
                        currentRectX = x;
                        currentRectY = y;
                        break;
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                switch (currentMode) {
                    //自由填充
                    case MODE_FREE_FILL: {
//                        circleCenters.addAll(tmpCircleCenters);
                        removeTooClosePoint(circleCenters, tmpCircleCenters);
                        path.close();
                        isDrawCircleInPath = true;
                        break;
                    }
                    //矩形填充
                    case MODE_RECT_FILL: {
                        isDrawCircleInPath = true;

                        isDrawingRect = false;
                        generateCircles();
//                        circleCenters.addAll(tmpCircleCenters);
                        removeTooClosePoint(circleCenters, tmpCircleCenters);
                        break;
                    }

                }
                break;
        }

        invalidate();
        return true;

    }

    private float calculateDistance(float x1, float y1, float x2, float y2) {
        float dx = x2 - x1;
        float dy = y2 - y1;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    //撤销
    public void undo() {
        if (pointList.size() > 0) {
            pointList.remove(pointList.size() - 1);
            invalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        for (PointF point : pointList) {
//            canvas.drawCircle(point.x, point.y, 50, paint);
//        }

        //画圆
        for (CircleBean center : circleCenters) {
            canvas.drawCircle(center.getPointF().x, center.getPointF().y, radius, circlePaint);
        }

        if (isDrawCircleInPath) {
            isDrawCircleInPath = false;
            path.reset();

        } else {
            //正在画路径
            switch (currentMode) {

                //自由填充
                case MODE_FREE_FILL: {
                    canvas.drawPath(path, pathPaint);
                    if (!path.isEmpty()) {
                        canvas.clipPath(path);
                        canvas.drawPath(path, pathPaint);

                        RectF bounds = new RectF();
                        path.computeBounds(bounds, true);

                        float left = bounds.left;
                        float top = bounds.top;
                        float right = bounds.right;
                        float bottom = bounds.bottom;

                        Region region = new Region();
                        region.setPath(path, new Region((int) left, (int) top, (int) right, (int) bottom));

                        tmpCircleCenters.clear();

                        for (float y = top + radius; y < bottom; y += spacing  + radius * 2) {
                            for (float x = left + radius; x < right; x += spacing  + radius * 2) {
                                if (region.contains((int) x, (int) y)) {
                                    tmpCircleCenters.add(new CircleBean(new PointF(x, y), MODE_FREE_FILL));
                                }
                            }
                        }
                    }
                    break;
                }
                //矩形填充
                case MODE_RECT_FILL: {
                    LogUtils.e("isDrawingRect:" + isDrawingRect);
                    if (isDrawingRect) {
                        float left = Math.min(startRectX, currentRectX);
                        float top = Math.min(startRectY, currentRectY);
                        float right = Math.max(startRectX, currentRectX);
                        float bottom = Math.max(startRectY, currentRectY);
                        rect = new RectF(left, top, right, bottom);
                        canvas.drawRect(rect, paint);

                        if (rect != null) {
                            canvas.clipRect(rect);

//                            float leftRect = rect.left + radius + spacing;
//                            float topRect = rect.top + radius + spacing;
//                            float rightRect = rect.right - radius - spacing;
//                            float bottomRect = rect.bottom - radius - spacing;
//
//                            float x = leftRect;
//                            float y = topRect;
//
//                            while (y <= bottomRect) {
//                                while (x <= rightRect) {
//                                    circleCenters.add(new PointF(x, y));
//                                    x += radius * 2 + spacing * 2;
//                                }
//                                x = leftRect;
//                                y += radius * 2 + spacing * 2;
//                            }
                        }
                    }
                    break;
                }

                //橡皮擦
                case MODE_ERASER: {
                    for (int i = 0; i < circleCenters.size(); i++) {
                        PointF circleCenter = circleCenters.get(i).getPointF();
                        if (i != selectedCircleIndex) {
                            canvas.drawCircle(circleCenter.x, circleCenter.y, radius, circlePaint);
                        } else {
                            canvas.drawCircle(circleCenter.x, circleCenter.y, eraserSize, circlePaint);
                        }
                    }
                    break;
                }


            }
        }


//        switch (currentMode) {
//            //自由填充
//            case MODE_FREE_FILL: {
//                if (isDrawCircleInPath) {
//                    path.reset();
//                    for (CircleBean center : circleCenters) {
//                        canvas.drawCircle(center.getPointF().x, center.getPointF().y, radius / 2, circlePaint);
//                    }
//                    isDrawCircleInPath = false;
//                } else {
//                    canvas.drawPath(path, pathPaint);
//                    if (!path.isEmpty()) {
//                        canvas.clipPath(path);
//                        canvas.drawPath(path, pathPaint);
//
//                        RectF bounds = new RectF();
//                        path.computeBounds(bounds, true);
//
//                        float left = bounds.left;
//                        float top = bounds.top;
//                        float right = bounds.right;
//                        float bottom = bounds.bottom;
//
//                        Region region = new Region();
//                        region.setPath(path, new Region((int) left, (int) top, (int) right, (int) bottom));
//
////                        circleCenters.clear();
//
//                        for (float y = top + radius; y < bottom; y += spacing) {
//                            for (float x = left + radius; x < right; x += spacing) {
//                                if (region.contains((int) x, (int) y) && isValidCircleCenter(x, y)) {
//                                    //                            if (!forbiddenRect.contains(x, y)) { // 排除禁止区域
////                                circleCenters.add(new PointF(x, y));
////                            }
//
//                                    circleCenters.add(new PointF(x, y));
//                                }
//                            }
//                        }
//                    }
//                }
//                break;
//            }
//            //矩形填充
//            case MODE_RECT_FILL: {
//                if (isDrawingRect) {
//                    float left = Math.min(startRectX, currentRectX);
//                    float top = Math.min(startRectY, currentRectY);
//                    float right = Math.max(startRectX, currentRectX);
//                    float bottom = Math.max(startRectY, currentRectY);
//
//                    rect = new RectF(left, top, right, bottom);
//                    canvas.drawRect(rect, paint);
//                }
//
//                if (rect != null) {
//                    canvas.clipRect(rect);
//
////                    circleCenters.clear();
//
//                    float left = rect.left + radius + spacing;
//                    float top = rect.top + radius + spacing;
//                    float right = rect.right - radius - spacing;
//                    float bottom = rect.bottom - radius - spacing;
//
//                    float x = left;
//                    float y = top;
//
//                    while (y <= bottom) {
//                        while (x <= right) {
////                            if (!forbiddenRect.contains(x, y)) { // 排除禁止区域
////                                circleCenters.add(new PointF(x, y));
////                            }
//                            circleCenters.add(new PointF(x, y));
//                            x += radius * 2 + spacing * 2;
//                        }
//                        x = left;
//                        y += radius * 2 + spacing * 2;
//                    }
//
//                    for (PointF center : circleCenters) {
//                        canvas.drawCircle(center.x, center.y, radius, circlePaint);
//                    }
//                }
//                break;
//            }
//        }
    }

    private boolean isValidCircleCenter(float x, float y) {
        for (CircleBean center : tmpCircleCenters) {
            float distance = (float) Math.sqrt(Math.pow(center.getPointF().x - x, 2) + Math.pow(center.getPointF().y - y, 2));
            if (distance < spacing) {
                return false;
            }
        }
        return true;
    }

    //手指拖动，画出矩形,并生成临时小圆点
    private void generateCircles() {
        if (rect != null) {
//            circleCenters.clear();

            float left = rect.left + radius + spacing;
            float top = rect.top + radius + spacing;
            float right = rect.right - radius - spacing;
            float bottom = rect.bottom - radius - spacing;

            float x = left;
            float y = top;

            while (y <= bottom) {
                while (x <= right) {
                    tmpCircleCenters.add(new CircleBean(new PointF(x, y), MODE_RECT_FILL));
                    x += radius * 2 + spacing;
                }
                x = left;
                y += radius * 2 + spacing;
            }
        }
    }

    //清除点
    public void clear() {
        pointList.clear();
        circleCenters.clear();
        invalidate();
    }

    //找到当前这一笔操作中，距离屏幕上已有点小于目标距离的点并移除。满足要求的点，加进最后list
    private List<CircleBean> removeTooClosePoint(List<CircleBean> arrayBig, List<CircleBean> arraySmall) {
        //最终满足距离要求的小数组
        List<CircleBean> arrayFinal = new ArrayList<>();
        if (arrayBig.size() == 0) {
            arrayBig.addAll(arraySmall);
            return arrayBig;
        }
        // 将数组B中的元素添加到数组A中
        for (int i = 0; i < arraySmall.size(); i++) {
            CircleBean pointB = arraySmall.get(i);
            boolean valid = true;

            // 遍历数组A中的元素，检查与数组B中的元素的距离是否满足要求
            for (int j = 0; j < arrayBig.size(); j++) {
                CircleBean pointA = arrayBig.get(j);
                float distance = calculateDistance(pointA.getPointF(), pointB.getPointF());
                float miniSpace = radius * 2 + spacing;
                LogUtils.e("pointB.x:" + pointB.getPointF().x + ";y:" + pointB.getPointF().y);
                LogUtils.e("miniSpace:" + miniSpace + ";distance:" + distance);
                if (distance < miniSpace) {
                    valid = false;
                    break;
                }
            }

            // 如果与数组A中的所有元素的距离都满足要求，则将数组B中的元素添加到数组A中
            if (valid) {
                arrayFinal.add(pointB);
            } else {
            }
        }

        arrayBig.addAll(arrayFinal);
        return arrayBig;

    }

    // 计算两个点之间的距离
    private float calculateDistance(PointF pointA, PointF pointB) {
        float dx = pointB.x - pointA.x;
        float dy = pointB.y - pointA.y;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    //橡皮擦
    private void eraseCirclesAtPoint(float x, float y) {
        for (int i = circleCenters.size() - 1; i >= 0; i--) {
            PointF circleCenter = circleCenters.get(i).getPointF();
            float dx = x - circleCenter.x;
            float dy = y - circleCenter.y;
            float distanceSquared = dx * dx + dy * dy;
            if (distanceSquared <= eraserSize * eraserSize) {
                circleCenters.remove(i);
            }
        }
    }

}
