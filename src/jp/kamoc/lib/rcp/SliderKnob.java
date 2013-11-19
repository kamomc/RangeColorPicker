package jp.kamoc.lib.rcp;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;

public class SliderKnob extends Drawable {
	private final float SQRT2 = 1.0f / (float) Math.sqrt(2);
	private float knobScale;
	private final float RECT_SCALE = 0.9f;
	private PointF[] points = { new PointF(0, 0), new PointF(SQRT2, SQRT2),
			new PointF(SQRT2, SQRT2 + 1.0f), new PointF(-SQRT2, SQRT2 + 1.0f),
			new PointF(-SQRT2, SQRT2) };
	private RectF rect = new RectF(-SQRT2 * RECT_SCALE, SQRT2
			+ (1.0f * RECT_SCALE), SQRT2 * RECT_SCALE, SQRT2
			+ (1.0f * (1.0f - RECT_SCALE)));
	private Path path;
	private boolean colorSampleAvailable;
	private int color;
	private double angle;
	private final float STROKE_WIDTH = 2f;

	public SliderKnob(Paint paint, float scale, boolean colorSampleAvailable) {
		super(paint);
		path = new Path();
		knobScale = scale;
		this.colorSampleAvailable = colorSampleAvailable;
		init();
	}

	@Override
	public void drawCanvas(Canvas canvas) {
		paint.setColor(Color.WHITE);
		paint.setStyle(Paint.Style.FILL);
		paint.setShader(null);		
		paint.setColor(Color.DKGRAY);
		canvas.scale(knobScale, knobScale);
		canvas.rotate(-90.0f);
		canvas.rotate((float) (angle * 180f / Math.PI));
		canvas.drawPath(path, paint);
		
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(STROKE_WIDTH/knobScale);
		paint.setColor(Color.LTGRAY);
		canvas.drawPath(path, paint);

		if(!colorSampleAvailable) return;
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(color);
		canvas.drawRect(rect, paint);
	}
	
	private void init() {
		path.moveTo(points[0].x, points[0].y);
		path.lineTo(points[1].x, points[1].y);
		path.lineTo(points[2].x, points[2].y);
		path.lineTo(points[3].x, points[3].y);
		path.lineTo(points[4].x, points[4].y);
		path.lineTo(points[0].x, points[0].y);
	}

	public void setColor(int color) {
		this.color = color;
	}
	
	public void setPosition(PointF center, double angle){
		setPosition(center);
		this.angle = angle;
	}
	

}
