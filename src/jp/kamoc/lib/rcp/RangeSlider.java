package jp.kamoc.lib.rcp;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;

public abstract class RangeSlider extends Touchable {
	protected Touchable from;
	protected Touchable to;
	protected Touchable selected;

	public RangeSlider(Paint paint) {
		super(paint);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	protected boolean collisionDetection(PointF point) {
		if (to.collisionDetection(point)) {
			selected = to;
			return true;
		}
		if (from.collisionDetection(point)) {
			selected = from;
			return true;
		}
		return false;
	}

	@Override
	protected void actionDown(PointF point) {
		;
	}

	@Override
	protected void actionUp(PointF point) {
		if(selected == from){
			onMoved();
		}
		selected = null;
	}

	@Override
	protected void actionMove(PointF point) {
		if(selected == null) return;
		selected.actionMove(point);
	}

	@Override
	public void drawCanvas(Canvas canvas) {
		canvas.save();
		from.drawCanvas(canvas);
		canvas.restore();
		canvas.save();
		to.drawCanvas(canvas);
		canvas.restore();
	}
	
	@Override
	public void setPosition(PointF center) {
		super.setPosition(center);
		from.setPosition(center);
		to.setPosition(center);
	}
	
	abstract public void onMoved();
	
	abstract public float getFromValue();
	
	abstract public float getToValue();

}
