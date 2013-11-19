package jp.kamoc.lib.rcp;

import android.graphics.Paint;

public class RangeWheelSlider extends RangeSlider{
	private ColorRect colorRect;
	private double radius;
	

	public double getRadius() {
		return radius;
	}


	public RangeWheelSlider(Paint paint, double r, ColorRect colorRect, float knobSize) {
		super(paint);
		radius = r;
		this.colorRect = colorRect;
		
		from = new WheelSlider(paint, r, knobSize);
		((WheelSlider) from).setAngle(0);
		to = new WheelSlider(paint, r, knobSize);
		((WheelSlider) to).setAngle(Math.PI);
		from.setNext(to);
	}

	@Override
	public void onMoved() {
		colorRect.setHue(((WheelSlider) from).getHue());
	}


	@Override
	public float getFromValue() {
		float fromAngle = 360.0f-((WheelSlider) from).getHue();
		return fromAngle;
	}


	@Override
	public float getToValue() {
		float toAngle = 360.0f-((WheelSlider) to).getHue();
		return toAngle;
	}

}
