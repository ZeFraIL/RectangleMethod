package zeev.fraiman.rectanglemethod;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GraphView extends View {

    private Paint axesPaint;
    private Paint axisLabelPaint;
    private Paint curvePaint;
    private Paint rectanglePaint;
    private Paint rectangleStrokePaint;

    private Path curvePath;
    private List<PointF> curvePoints = new ArrayList<>();
    private List<RectF> rectangles = new ArrayList<>();
    private boolean isEditable = true;

    public GraphView(Context context) {
        super(context);
        init();
    }

    public GraphView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public GraphView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        axesPaint = new Paint();
        axesPaint.setColor(Color.BLACK);
        axesPaint.setStrokeWidth(5f);

        axisLabelPaint = new Paint();
        axisLabelPaint.setColor(Color.BLACK);
        axisLabelPaint.setTextSize(40f);

        curvePaint = new Paint();
        curvePaint.setColor(Color.BLUE);
        curvePaint.setStyle(Paint.Style.STROKE);
        curvePaint.setStrokeWidth(7f);

        rectanglePaint = new Paint();
        rectanglePaint.setColor(Color.argb(100, 0, 255, 0)); // Semi-transparent green
        rectanglePaint.setStyle(Paint.Style.FILL);

        rectangleStrokePaint = new Paint();
        rectangleStrokePaint.setColor(Color.GRAY);
        rectangleStrokePaint.setStyle(Paint.Style.STROKE);
        rectangleStrokePaint.setStrokeWidth(2f);

        curvePath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw axes with arrows
        int width = getWidth();
        int height = getHeight();
        canvas.drawLine(50, height - 50, width - 50, height - 50, axesPaint); // X-axis
        canvas.drawLine(width - 50, height - 50, width - 70, height - 60, axesPaint); // X-axis arrow
        canvas.drawLine(width - 50, height - 50, width - 70, height - 40, axesPaint); // X-axis arrow
        canvas.drawText("X", width - 45, height - 60, axisLabelPaint);

        canvas.drawLine(50, height - 50, 50, 50, axesPaint); // Y-axis
        canvas.drawLine(50, 50, 40, 70, axesPaint); // Y-axis arrow
        canvas.drawLine(50, 50, 60, 70, axesPaint); // Y-axis arrow
        canvas.drawText("Y", 60, 60, axisLabelPaint);

        // Draw rectangles
        for (RectF rect : rectangles) {
            canvas.drawRect(rect, rectanglePaint);
            canvas.drawRect(rect, rectangleStrokePaint);
        }

        // Draw the curve
        canvas.drawPath(curvePath, curvePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEditable) {
            return super.onTouchEvent(event);
        }

        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                clearCurve();
                curvePath.moveTo(x, y);
                curvePoints.add(new PointF(x, y));
                return true;
            case MotionEvent.ACTION_MOVE:
                curvePath.lineTo(x, y);
                curvePoints.add(new PointF(x, y));
                break;
            default:
                return false;
        }

        invalidate();
        return true;
    }

    public List<PointF> getCurvePoints() {
        return curvePoints;
    }

    public void setRectangles(List<RectF> rectangles) {
        this.rectangles = rectangles;
        invalidate();
    }

    public void clearCurve() {
        curvePath.reset();
        curvePoints.clear();
        rectangles.clear();
        invalidate();
    }

    public void setEditable(boolean editable) {
        isEditable = editable;
    }

    public void setCurve(List<PointF> points) {
        curvePath.reset();
        if (points != null && !points.isEmpty()) {
            curvePath.moveTo(points.get(0).x, points.get(0).y);
            for (int i = 1; i < points.size(); i++) {
                curvePath.lineTo(points.get(i).x, points.get(i).y);
            }
        }
        invalidate();
    }
}
