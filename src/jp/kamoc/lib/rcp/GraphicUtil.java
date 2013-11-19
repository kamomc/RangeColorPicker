package jp.kamoc.lib.rcp;

import android.graphics.PointF;

public final class GraphicUtil {
	public static float dist(PointF p1, PointF p2){
		float dist = (float) Math.sqrt(Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2) );
		return dist;
	}
	
	public static double theta(PointF p){
		return Math.atan2(p.x, p.y);
	}
	
	public static double theta(PointF p, PointF o){
		PointF v = new PointF();
		v.x = p.x - o.x;
		v.y = p.y - o.y;
		return theta(v);
	};
}
