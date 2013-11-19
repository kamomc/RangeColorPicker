package jp.kamoc.lib.rcp;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

public class RangeColorPickerActivity extends Activity {
	private RangeColorPickerSurfaceView testSurfaceView;
	private SurfaceView surfaceView;
	public static final String RESULT_DTO = "RangeColorPickerDto";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.color_picker);

		surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
		testSurfaceView = new RangeColorPickerSurfaceView(this, surfaceView);
		
		Button okButton = (Button)findViewById(R.id.buttonOk);
		okButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				PaletteDto paletteDto = testSurfaceView.getPaletteDto();
				Intent intent = new Intent();
				intent.putExtra(RESULT_DTO, paletteDto);
				setResult(RESULT_OK, intent);
				finish();
			}
		});
		
		Button cancelButton = (Button) findViewById(R.id.buttonCancel);
		cancelButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				setResult(RESULT_CANCELED, intent);
				finish();
			}
		});
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		PointF offset = new PointF();
		Rect rect = new Rect();
		surfaceView.getGlobalVisibleRect(rect);
		offset.x = rect.left;
		offset.y = rect.top;
		return testSurfaceView.onTouchEvent(event, offset);
	}

	private class RangeColorPickerSurfaceView implements Runnable, SurfaceHolder.Callback {
		private boolean destroy = false;
		private boolean pause = false;
		private SurfaceHolder surfaceHolder;

		private Paint paint = new Paint();
		private Point center;
		private PointF centerF;
		private ColorWheel colorWheel;
		private ColorRect colorRect;
		private RangeWheelSlider rangeWheelSlider;
		private RangeArc rangeArc;
		private RangeLinearSlider rangeLinearSliderS;
		private RangeLine rangeLineS;
		private RangeLinearSlider rangeLinearSliderV;
		private RangeLine rangeLineV;
		private RangeRect rangeRect;
		private PalettePreview palettePreview;
		private Touchable head;
		private float radius;
		private Thread thread;

		public RangeColorPickerSurfaceView(Context context, SurfaceView sv) {
			center = new Point();
			centerF = new PointF();

			surfaceHolder = sv.getHolder();
			surfaceHolder.addCallback(this);
		}

		@Override
		public void run() {
			while (!destroy) {
				if (!pause) {
					Canvas canvas = surfaceHolder.lockCanvas();
					try {
						canvas.drawColor(Color.BLACK);
						rangeArc.draw(canvas);
						rangeLineS.draw(canvas);
						rangeLineV.draw(canvas);
						colorWheel.draw(canvas);
						colorRect.draw(canvas);
						rangeWheelSlider.draw(canvas);
						rangeLinearSliderS.draw(canvas);
						rangeLinearSliderV.draw(canvas);
						rangeRect.draw(canvas);
						palettePreview.draw(canvas);
					} catch (Exception ex) {
						;
					}
					if (canvas != null)
						surfaceHolder.unlockCanvasAndPost(canvas);
				} else {
					try {
						Thread.sleep(10);
					} catch (Exception ex) {
						;
					}
				}
			}

		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			center.set(width / 2, width / 2);
			centerF.set(width / 2.0f, width / 2.0f);
			radius = center.x / 1.53f;

			RectF colorRectBounds = new RectF(-radius / 2, -radius / 2,
					radius / 2, radius / 2);

			colorWheel = new ColorWheel(paint, 64, radius);
			colorRect = new ColorRect(paint, colorRectBounds, 0.0f);
			rangeWheelSlider = new RangeWheelSlider(paint, radius, colorRect,
					radius / 3.5f);
			rangeArc = new RangeArc(paint, rangeWheelSlider, radius / 4.5f);
			rangeLinearSliderS = new RangeLinearSlider(paint, 0, radius,
					radius / 6.0f);
			rangeLineS = new RangeLine(paint, rangeLinearSliderS,
					radius / 18.0f);
			rangeLinearSliderV = new RangeLinearSlider(paint, -Math.PI / 2,
					radius, radius / 6.0f);
			rangeLineV = new RangeLine(paint, rangeLinearSliderV,
					radius / 18.0f);
			rangeRect = new RangeRect(paint, rangeLinearSliderS,
					rangeLinearSliderV, colorRectBounds);
			palettePreview = new PalettePreview(paint, rangeWheelSlider,
					rangeLinearSliderS, rangeLinearSliderV, new RectF(0, 0,
							width, (height - width) * 0.9f), 8, 8, 2f,
					radius / 8f);

			head = rangeWheelSlider;
			rangeWheelSlider.setNext(rangeLinearSliderS);
			rangeLinearSliderS.setNext(rangeLinearSliderV);

			colorWheel.setPosition(centerF);
			colorRect.setPosition(centerF);
			rangeWheelSlider.setPosition(centerF);
			rangeArc.setPosition(centerF);
			rangeLinearSliderS.setPosition(new PointF(centerF.x - radius / 2,
					centerF.y + radius / 2));
			rangeLineS.setPosition(new PointF(centerF.x - radius / 2, centerF.y
					+ radius / 2));
			rangeLinearSliderV.setPosition(new PointF(centerF.x + radius / 2,
					centerF.y + radius / 2));
			rangeLineV.setPosition(new PointF(centerF.x + radius / 2, centerF.y
					+ radius / 2));
			rangeRect.setPosition(centerF);
			palettePreview.setPosition(new PointF(0f, centerF.y * 2));
		}

		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			thread = new Thread(this);
			thread.start();
		}

		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
			thread = null;
		}

		public boolean onTouchEvent(MotionEvent event, PointF offset) {
			PointF touchPoint = new PointF(event.getX(), event.getY());
			touchPoint.x -= offset.x;
			touchPoint.y -= offset.y;
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				head.onActionDown(touchPoint);
				break;
			case MotionEvent.ACTION_UP:
				head.onActionUp(touchPoint);
				break;
			case MotionEvent.ACTION_MOVE:
				head.onActionMove(touchPoint);
				break;
			default:
				;
				break;
			}

			return true;
		}
		
		public PaletteDto getPaletteDto(){
			double hFrom = rangeWheelSlider.getFromValue();
			double hTo = rangeWheelSlider.getToValue();
			double sFrom = rangeLinearSliderS.getFromValue();
			double sTo = rangeLinearSliderS.getToValue();
			double vFrom = rangeLinearSliderV.getFromValue();
			double vTo = rangeLinearSliderV.getToValue();
			PaletteDto paletteDto = new PaletteDto(hFrom, hTo, sFrom, sTo, vFrom, vTo);
			return paletteDto;
		}

	}

}
