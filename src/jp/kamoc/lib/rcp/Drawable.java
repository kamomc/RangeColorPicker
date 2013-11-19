package jp.kamoc.lib.rcp;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

public abstract class Drawable {

	protected Paint paint;
	
	protected PointF center;

	public Drawable(Paint paint) {
		this.paint = paint;
		center = new PointF();
	}
	
	public void setPosition(PointF center){
		this.center = center;
	}

	public final void draw(Canvas canvas){
		canvas.save();
		canvas.translate(center.x, center.y);
		drawCanvas(canvas);
		canvas.restore();
	}
		
	abstract public void drawCanvas(Canvas canvas);
}