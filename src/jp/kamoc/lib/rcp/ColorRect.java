package jp.kamoc.lib.rcp;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;

public class ColorRect extends Drawable {
	private RectF bounds;
	private float hue;
	private List<Shader> shaderList;
	
	public void setHue(float hue) {
		this.hue = hue;
		this.init();
	}

	public ColorRect(Paint paint, RectF bounds, float hue) {
		super(paint);
		this.bounds = bounds;
		this.hue = hue;
		init();
	}

	private void init() {
		List<Shader> tempShaderList = new ArrayList<Shader>();
		int height = (int) bounds.height();
		for (int i = 0; i <= height; i++) {
			float value = 1.0f - (float) i / height;
			float[] leftHSV = { hue, 0.0f, value };
			float[] rightHSV = { hue, 1.0f, value };
			int leftColor = Color.HSVToColor(leftHSV);
			int rightColor = Color.HSVToColor(rightHSV);
			Shader shader = new LinearGradient(bounds.left, bounds.top+i, bounds.right, bounds.top+i, leftColor,
					rightColor, Shader.TileMode.CLAMP);
			tempShaderList.add(shader);
		}
		shaderList = tempShaderList;
	}
	
	public void drawCanvas(Canvas canvas) {
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(1);
		
		int height = (int) bounds.height();
		for (int i = 0; i <= height; i++) {
			paint.setShader(shaderList.get(i));
			canvas.drawLine(bounds.left, bounds.top+i, bounds.right, bounds.top+i, paint);
		}
	};
}
