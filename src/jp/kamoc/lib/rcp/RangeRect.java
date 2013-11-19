package jp.kamoc.lib.rcp;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class RangeRect extends Drawable {
	private RangeLinearSlider horizontal;
	private RangeLinearSlider vertical;
	private RectF bounds;
	private final float STROKE_WIDTH = 2f;

	public RangeRect(Paint paint, RangeLinearSlider horizontal,
			RangeLinearSlider vertical, RectF bounds) {
		super(paint);
		this.horizontal = horizontal;
		this.vertical = vertical;
		this.bounds = bounds;
	}

	@Override
	public void drawCanvas(Canvas canvas) {
		paint.setStrokeWidth(STROKE_WIDTH);
		paint.setStyle(Paint.Style.STROKE);
		paint.setColor(Color.RED);

		float left = bounds.left + bounds.width() * horizontal.getFromValue();
		float right = bounds.left + bounds.width() * horizontal.getToValue();
		float bottom = bounds.bottom - bounds.height()
				* vertical.getFromValue();
		float top = bounds.bottom - bounds.height() * vertical.getToValue();

		if (horizontal.getFromValue() > horizontal.getToValue()
				&& vertical.getFromValue() > vertical.getToValue()) {
			canvas.drawRect(bounds.left, top, right, bounds.bottom, paint);
			canvas.drawRect(bounds.left, bounds.top, right, bottom, paint);
			canvas.drawRect(left, top, bounds.right, bounds.bottom, paint);
			canvas.drawRect(left, bounds.top, bounds.right, bottom, paint);
		}else if(horizontal.getFromValue() > horizontal.getToValue()){
			canvas.drawRect(bounds.left, top, right, bottom, paint);
			canvas.drawRect(left, top, bounds.right, bottom, paint);
		}else if(vertical.getFromValue() > vertical.getToValue()){
			canvas.drawRect(left, bounds.top, right, bottom, paint);
			canvas.drawRect(left, top, right, bounds.bottom, paint);
		}else{
			canvas.drawRect(left, top, right, bottom, paint);
		}
	}

}
