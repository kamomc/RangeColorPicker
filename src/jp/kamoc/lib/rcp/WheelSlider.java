package jp.kamoc.lib.rcp;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;

public class WheelSlider extends Touchable {
	private float knobSize = 100.0f;
	
	private double angle = 0;
	private double radius;
	private int color = Color.RED;
	private SliderKnob sliderKnob;


	public WheelSlider(Paint paint, double r, float knobSize) {
		super(paint);
		this.knobSize = knobSize;
		sliderKnob = new SliderKnob(paint, knobSize, true);
		radius = r;
		refleshColor();
		sliderKnob.setPosition(getSelectedCenter(), angle);
	}

	public void setAngle(double angle) {
		this.angle = angle;
		refleshColor();
		sliderKnob.setPosition(getSelectedCenter(), angle);
	}

	public double getAngle() {
		return angle;
	}



	private PointF getSelectedCenter() {
		float x = (float) (radius * Math.cos(angle));
		float y = (float) (radius * Math.sin(angle));
		return new PointF(x, y);
	}

	@Override
	public void drawCanvas(Canvas canvas) {
		sliderKnob.draw(canvas);
	}

	@Override
	protected boolean collisionDetection(PointF point) {
		PointF center = getAbsPointF(getSelectedCenter());
		center.x += knobSize * Math.cos(angle);
		center.y += knobSize * Math.sin(angle);
		float dist = GraphicUtil.dist(point, center);
		if (dist < knobSize)
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
		PointF relPoint = getRelPointF(point);
		angle = Math.atan2(relPoint.y, relPoint.x);
		refleshColor();
		sliderKnob.setPosition(getSelectedCenter(), angle);
	}

	private void refleshColor() {
		float hue = getHue();
		float[] hsv = { hue, 1.0f, 1.0f };
		color = Color.HSVToColor(hsv);
		sliderKnob.setColor(color);
	}

	public float getHue() {
		double tmpAngle = angle;
		if (tmpAngle < 0) {
			tmpAngle = 2 * Math.PI + tmpAngle;
		}
		float hue = 360f - (float) ((tmpAngle / (2 * Math.PI)) * 360.0);
		return hue;
	}
	
	

}
