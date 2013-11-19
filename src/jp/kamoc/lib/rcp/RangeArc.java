package jp.kamoc.lib.rcp;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class RangeArc extends Drawable {
	private RectF bounds;
	private RangeWheelSlider rangeWheelSlider;
	private float strokeWidth;



	public RangeArc(Paint paint, RangeWheelSlider rangeWheelSlider, float strokeWidth) {
		super(paint);
		this.rangeWheelSlider = rangeWheelSlider;
		this.strokeWidth = strokeWidth;
		float radiusF = (float)rangeWheelSlider.getRadius();
		bounds = new RectF(-radiusF, -radiusF, radiusF, radiusF);
	}

	@Override
	public void drawCanvas(Canvas canvas) {
		paint.setStrokeWidth(strokeWidth);
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(Color.WHITE);
		
		float fromAngle = rangeWheelSlider.getFromValue();
		float toAngle = rangeWheelSlider.getToValue();
		float sweepAngle = 0;
		if(fromAngle <= toAngle){
			sweepAngle = toAngle - fromAngle;
		}else{
			sweepAngle = 360f + toAngle - fromAngle;
		}
		canvas.drawArc(bounds, rangeWheelSlider.getFromValue(), sweepAngle, false, paint);
	}

}
