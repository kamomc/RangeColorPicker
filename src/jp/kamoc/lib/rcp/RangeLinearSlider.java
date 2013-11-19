package jp.kamoc.lib.rcp;

import android.graphics.Paint;

public class RangeLinearSlider extends RangeSlider {
	private double angle;
	private double dist;

	public RangeLinearSlider(Paint paint, double angle, double dist, float knobSize) {
		super(paint);
		this.angle = angle;
		this.dist = dist;
		from = new LinearSlider(paint, angle, dist, knobSize);
		((LinearSlider) from).setValue(0);
		to = new LinearSlider(paint, angle, dist, knobSize);
		((LinearSlider) to).setValue(1);
	}

	@Override
	public void onMoved() {
		;
	}

	@Override
	public float getFromValue() {
		return (float) ((LinearSlider) from).getValue();
	}

	@Override
	public float getToValue() {
		return (float) ((LinearSlider) to).getValue();
	}

	public double getAngle() {
		return angle;
	}

	public double getDist() {
		return dist;
	}

}
