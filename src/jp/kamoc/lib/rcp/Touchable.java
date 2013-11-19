package jp.kamoc.lib.rcp;

import android.graphics.Paint;
import android.graphics.PointF;

abstract public class Touchable extends Drawable {

	private Touchable next;
	private boolean isSelected = false;
	
	public Touchable(Paint paint) {
		super(paint);
	}
	
	public Touchable setNext(Touchable next){
		this.next = next;
		return next;
	}
	
	public final void onActionDown(PointF point){
		if(collisionDetection(point)){
			isSelected = true;
			actionDown(point);
			return;
		}
		if(next == null) return;
		next.onActionDown(point);
	}
	
	public final void onActionUp(PointF point){
		if(isSelected){
			isSelected = false;
			actionUp(point);
			return;
		}
		if(next == null) return;
		next.onActionUp(point);
	}
	
	public final void onActionMove(PointF point){
		if(isSelected){
			actionMove(point);
			return;
		}
		if(next == null) return;
		next.onActionMove(point);
	}
	
	public final PointF getAbsPointF(PointF point){
		PointF result = new PointF();
		result.x = point.x + center.x;
		result.y = point.y + center.y;
		return result;
	}
	
	public final PointF getRelPointF(PointF point){
		PointF result = new PointF();
		result.x = point.x - center.x;
		result.y = point.y - center.y;
		return result;
	}
	
	
	protected abstract boolean collisionDetection(PointF point);
	
	protected abstract void actionDown(PointF point);
	
	protected abstract void actionUp(PointF point);
	
	protected abstract void actionMove(PointF point);
}
