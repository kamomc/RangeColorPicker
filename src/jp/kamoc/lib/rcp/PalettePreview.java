package jp.kamoc.lib.rcp;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

public class PalettePreview extends Drawable {
	private RangeWheelSlider hue;
	private RangeLinearSlider saturation;
	private RangeLinearSlider value;
	private RectF bounds;
	private int h;
	private int v;
	private List<Integer> colorList;
	private double hFrom;
	private double hTo;
	private double sFrom;
	private double sTo;
	private double vFrom;
	private double vTo;
	private float strokeWidth;
	private float fontSize;

	public PalettePreview(Paint paint, RangeWheelSlider hue,
			RangeLinearSlider saturation, RangeLinearSlider value,
			RectF bounds, int h, int v, float strokeWidth, float fontSize) {
		super(paint);
		this.hue = hue;
		this.saturation = saturation;
		this.value = value;
		this.bounds = bounds;
		this.h = h;
		this.v = v;
		this.strokeWidth = strokeWidth;
		this.fontSize = fontSize;
		reflesh();
	}

	@Override
	public void drawCanvas(Canvas canvas) {
		paint.setStyle(Paint.Style.FILL);
		paint.setTextSize(fontSize);
		paint.setColor(Color.WHITE);
		canvas.drawText("Palette Preview", 0, -fontSize/2, paint);
		
		paint.setStyle(Paint.Style.FILL);
		float width = bounds.width() / h;
		float height = bounds.height() / v;

		if (isUpdated())
			reflesh();
		

		for (int i = 0; i < h; i++) {
			for (int j = 0; j < v; j++) {
				int color = colorList.get(i * h + j);
				paint.setColor(color);
				canvas.drawRect(bounds.left + width * j, bounds.top + height
						* i, bounds.left + width * (j + 1), bounds.left
						+ height * (i + 1), paint);
			}
		}
		
		paint.setColor(Color.DKGRAY);
		paint.setStrokeWidth(strokeWidth);
		paint.setStyle(Paint.Style.STROKE);
		for (int i = 0; i < h; i++) {
			for (int j = 0; j < v; j++) {
				canvas.drawRect(bounds.left + width * j, bounds.top + height
						* i, bounds.left + width * (j + 1), bounds.left
						+ height * (i + 1), paint);
			}
		}
	}

	private void reflesh() {
		hFrom = hue.getFromValue();
		hTo = hue.getToValue();
		sFrom = saturation.getFromValue();
		sTo = saturation.getToValue();
		vFrom = value.getFromValue();
		vTo = value.getToValue();
		List<Integer> tmpColorList = new ArrayList<Integer>();
		for (int i = 0; i < h * v; i++) {
			int color = PaletteDto.getRandomColor(hFrom, hTo, sFrom, sTo, vFrom, vTo);
			tmpColorList.add(color);
		}
		colorList = tmpColorList;
	}



	private boolean isUpdated() {
		double hFrom = hue.getFromValue();
		double hTo = hue.getToValue();
		double sFrom = saturation.getFromValue();
		double sTo = saturation.getToValue();
		double vFrom = value.getFromValue();
		double vTo = value.getToValue();
		if (hFrom == this.hFrom && hTo == this.hTo && sFrom == this.sFrom
				&& sTo == this.sTo && vFrom == this.vFrom && vTo == this.vTo)
			return false;
		return true;
	}

}
