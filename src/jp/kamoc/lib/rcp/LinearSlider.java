package jp.kamoc.lib.rcp;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

public class LinearSlider extends Touchable {
	private float knobSize = 60.0f;

	private double value = 0;
	private double angle;
	private double dist;
	private double sliderAngle;

	private SliderKnob sliderKnob;

	public LinearSlider(Paint paint, double angle, double dist, float knobSize) {
		super(paint);
		this.angle = angle;
		this.dist = dist;
		this.knobSize = knobSize;
		sliderAngle = Math.PI / 2 + angle;
		sliderKnob = new SliderKnob(paint, knobSize, false);
		setValue(0);
	}

	@Override
	protected boolean collisionDetection(PointF point) {
		PointF absSliderPos = getAbsPointF(getSliderPos());
		PointF absSliderCenter = new PointF();
		absSliderCenter.x = (float) (absSliderPos.x + knobSize
				* Math.cos(Math.PI / 2 + angle));
		absSliderCenter.y = (float) (absSliderPos.y + knobSize
				* Math.sin(Math.PI / 2 + angle));
		float touchDist = GraphicUtil.dist(absSliderCenter, point);
		if (touchDist < knobSize)
			return true;
		return false;
	}

	@Override
	protected void actionDown(PointF point) {
		// Log.d("DEBUG", "Down (" + point.x + ", " + point.y + ")");

	}

	@Override
	protected void actionUp(PointF point) {
		// Log.d("DEBUG", "Up (" + point.x + ", " + point.y + ")");
	}

	@Override
	protected void actionMove(PointF point) {
		// Log.d("DEBUG", "Move (" + point.x + ", " + point.y + ")");
		double r = GraphicUtil.dist(center, point);
		double angleB = GraphicUtil.theta(point, center);
		double t = (r * Math.sin(Math.PI - (angleB + angle))) / dist;
		setValue(t);
	}

	@Override
	public void drawCanvas(Canvas canvas) {
		// paint.setStyle(Style.STROKE);
		// paint.setStrokeWidth(20);
		// paint.setColor(Color.WHITE);
		// canvas.drawLine(0, 0,
		// (float) (dist * Math.cos(angle)),
		// (float) (dist * Math.sin(angle)), paint);

		sliderKnob.draw(canvas);
	}

	public void setValue(double value) {
		if (value < 0) {
			this.value = 0;
		} else if (value > 1) {
			this.value = 1;
		} else {
			this.value = value;
		}
		// Log.d("DEBUG", "value=" + value);
		refleshSliderPos();
	}

	private void refleshSliderPos() {
		PointF sliderPos = getSliderPos();
		sliderKnob.setPosition(sliderPos, sliderAngle);
	}

	private PointF getSliderPos() {
		PointF sliderPos = new PointF();
		sliderPos.x = (float) ((dist * value) * Math.cos(angle));
		sliderPos.y = (float) ((dist * value) * Math.sin(angle));
		return sliderPos;
	}

	public double getValue() {
		return value;
	}

}
