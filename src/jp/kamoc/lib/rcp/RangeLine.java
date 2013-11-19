package jp.kamoc.lib.rcp;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class RangeLine extends Drawable {
	private RangeLinearSlider rangeLinearSlider;
	private float strokeWidth;

	public RangeLine(Paint paint, RangeLinearSlider rangeLinearSlider, float strokeWidth) {
		super(paint);
		this.rangeLinearSlider = rangeLinearSlider;
		this.strokeWidth = strokeWidth;
	}

	@Override
	public void drawCanvas(Canvas canvas) {
		paint.setStrokeWidth(strokeWidth);
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(Color.WHITE);
		
		float fromValue = rangeLinearSlider.getFromValue();
		float toValue = rangeLinearSlider.getToValue();
		double angle = rangeLinearSlider.getAngle();
		double dist = rangeLinearSlider.getDist();
		if( fromValue < toValue ){
			canvas.drawLine((float)((dist*fromValue)*Math.cos(angle)), 
					(float)((dist*fromValue)*Math.sin(angle)), 
					(float)((dist*toValue)*Math.cos(angle)), 
					(float)((dist*toValue)*Math.sin(angle)),
					paint);
		}else if( fromValue > toValue ){
			canvas.drawLine(0, 0, (float)((dist*toValue)*Math.cos(angle)), 
					(float)((dist*toValue)*Math.sin(angle)), paint);
			canvas.drawLine((float)((dist*fromValue)*Math.cos(angle)), 
					(float)((dist*fromValue)*Math.sin(angle)), 
					(float)(dist*Math.cos(angle)), 
					(float)(dist*Math.sin(angle)), paint);
		}
	}

}
