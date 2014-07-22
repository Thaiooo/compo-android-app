package com.compo.android.app;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.compo.android.app.utils.FontEnum;

public class KeyboardView extends View {

	/**
	 * Field width in meter
	 */
	protected static final int FIELD_WIDTH = 68;
	/**
	 * Field width in meter
	 */
	protected static final int FIELD_HEIGHT = 105;
	/**
	 * Field width in meter
	 */

	private static Typeface font;

	protected Context _context;

	Paint _paint;

	List<String> line1 = new ArrayList<String>();
	List<String> line2 = new ArrayList<String>();
	List<String> line3 = new ArrayList<String>();

	public KeyboardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		_context = context;
		if (!isInEditMode()) {
			font = Typeface.createFromAsset(_context.getAssets(), FontEnum.FIELD_TITLE.getName());
			Intent intent = ((Activity) context).getIntent();
			_paint = new Paint();

			// float densityMultiplier = getContext().getResources().getDisplayMetrics().density;
			// _paint.setTextSize(1000);
			// _paint.setTypeface(font);

			line1.add("A");
			line1.add("Z");
			line1.add("E");
			line1.add("R");
			line1.add("T");
			line1.add("Y");
			line1.add("U");
			line1.add("I");
			line1.add("O");
			line1.add("P");

			line2.add("Q");
			line2.add("S");
			line2.add("D");
			line2.add("F");
			line2.add("G");
			line2.add("H");
			line2.add("J");
			line2.add("K");
			line2.add("L");
			line2.add("M");

			line3.add("W");
			line3.add("X");
			line3.add("C");
			line3.add("V");
			line3.add("B");
			line3.add("N");

			// setOnTouchListener(new OnTouchListener() {
			//
			// @Override
			// public boolean onTouch(View v, MotionEvent event) {
			// System.out.println("Touch " + event.getAction());
			// return true;
			// }
			// });
		}
	}

	@Override
	public void invalidate() {
		super.invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (!isInEditMode()) {
			double screenW = this.getWidth();
			double screenH = this.getHeight();
			System.out.println("W=" + screenW + " H=" + screenH);

			int spacing = 10;
			double reste = screenW - ((line1.size() + 1) * spacing);
			int letterW = (int) (reste / line1.size());

			int y = 2;
			int x = spacing;

			_paint.setTextSize(50);

			for (String letter : line1) {
				_paint.setColor(Color.BLUE);
				canvas.drawRect(x, y, x + letterW, letterW + y, _paint);
				_paint.setColor(Color.RED);
				canvas.drawText(letter, x, 50, _paint);
				x = x + letterW + spacing;
			}

			y += letterW + spacing;
			x = spacing;
			for (String letter : line2) {
				_paint.setColor(Color.BLUE);
				canvas.drawRect(x, y, x + letterW, letterW + y, _paint);
				x = x + letterW + spacing;
			}

			y += letterW + spacing;
			x = spacing * 3 + letterW * 2;
			for (String letter : line3) {
				_paint.setColor(Color.BLUE);
				canvas.drawRect(x, y, x + letterW, letterW + y, _paint);
				x = x + letterW + spacing;
			}

			_paint.setColor(Color.BLUE);
			int cancelX = spacing * 9 + letterW * 8;
			canvas.drawRect(cancelX, y, cancelX + letterW * 2 + spacing, letterW * 2 + spacing + y, _paint);

			_paint.setColor(Color.BLUE);
			int spaceX = spacing * 3 + letterW * 2;
			y += letterW + spacing;
			canvas.drawRect(spaceX, y, spaceX + letterW * 6 + spacing * 5, letterW + y, _paint);

			_paint.setColor(Color.BLUE);
			int checkX = spacing;
			y += letterW + spacing;
			canvas.drawRect(checkX, y, checkX + letterW * 10 + spacing * 9, letterW * 2 + spacing + y, _paint);

		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		boolean b = super.onTouchEvent(event);

		int action = MotionEventCompat.getActionMasked(event);

		System.out.println("Touch X:" + event.getX() + " Y:" + event.getY() + " Action:" + action);

		invalidate();

		return true;
	}

}
