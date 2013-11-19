package jp.kamoc.lib.rcp;

import java.io.Serializable;

import android.graphics.Color;

public class PaletteDto implements Serializable{
	private static final long serialVersionUID = 2823525777303143800L;
	public static final String TABLE_NAME = "custom_palette";
	public static final String ID = "id";
	public static final String H_FROM = "hFrom";
	public static final String H_TO = "hTo";
	public static final String S_FROM = "sFrom";
	public static final String S_TO = "sTo";
	public static final String V_FROM ="vFrom";
	public static final String V_TO = "vTo";
	public Long rowId;
	public double hFrom;
	public double hTo;
	public double sFrom;
	public double sTo;
	public double vFrom;
	public double vTo;
	public PaletteDto(double hFrom, double hTo, double sFrom, double sTo, double vFrom, double vTo) {
		this.hFrom = hFrom;
		this.hTo = hTo;
		this.sFrom = sFrom;
		this.sTo = sTo;
		this.vFrom = vFrom;
		this.vTo = vTo;
	}
	
	public int getRandomColor(){
		return getRandomColor(hFrom, hTo, sFrom, sTo, vFrom, vTo);
	}
	
	public static int getRandomColor(double hFrom, double hTo, double sFrom,
			double sTo, double vFrom, double vTo) {
		double h;
		double s;
		double v;
		if (hFrom <= hTo) {
			h = 360 - (Math.random() * (hTo - hFrom) + hFrom);
		} else {
			h = 360 - ((Math.random() * ((hTo + 360) - hFrom)) + hFrom) % 360;
		}
		if (sFrom <= sTo) {
			s = Math.random() * (sTo - sFrom) + sFrom;
		} else {
			s = (Math.random() * ((sTo + 1) - sFrom) + sFrom) % 1;
		}
		if (vFrom <= vTo) {
			v = Math.random() * (vTo - vFrom) + vFrom;
		} else {
			v = (Math.random() * ((vTo + 1) - vFrom) + vFrom) % 1;
		}
		float[] hsv = { (float) h, (float) s, (float) v };
		int color = Color.HSVToColor(hsv);
		return color;
	}
}
