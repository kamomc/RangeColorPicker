package jp.kamoc.lib.rcp;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.SweepGradient;

public class ColorWheel extends Drawable {
	private final int[] shaderColors;
	private Shader shader;
	private float strokeWidth;
	private RectF bounds;
	
	public ColorWheel(Paint paint, float width, float r) {
		super(paint);
		shaderColors = new int[] { 0xFFFF0000, 0xFFFF00FF, 0xFF0000FF,
				0xFF00FFFF, 0xFF00FF00, 0xFFFFFF00, 0xFFFF0000 };
		shader = new SweepGradient(0, 0, shaderColors, null);
		strokeWidth = width;
		bounds = new RectF(-r, -r, r, r);
	}

	@Override
	public void drawCanvas(Canvas canvas) {
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(strokeWidth);
		paint.setShader(shader);
		
		canvas.drawOval(bounds, paint);
	}
}
