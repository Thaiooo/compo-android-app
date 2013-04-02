package com.compo.android.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.compo.android.app.model.QuizzPlayer;

public class QuizzViewLand extends QuizzView {

    public QuizzViewLand(Context context, AttributeSet attrs) {
	super(context, attrs);
	_terrainRaw = ((BitmapDrawable) _context.getResources().getDrawable(R.drawable.football_field_land))
		.getBitmap();
	_greenMappingRaw = ((BitmapDrawable) _context.getResources().getDrawable(R.drawable.green_mapping_land))
		.getBitmap();
    }

    @Override
    protected double getPixelPerMeterX(double terrainW) {
	double metreX = terrainW / FIELD_HEIGHT;
	return metreX;
    }

    @Override
    protected double getPixelPerMeterY(double terrainH) {
	double metreY = terrainH / FIELD_WIDTH;
	return metreY;
    }

    @Override
    protected double getPlayerX(QuizzPlayer aQuizzPlayer, double aLateralMarge, double aMetreX, Bitmap aPlayerImg) {
	double coordonneeX = 0;
	double marge = aMetreX * MARGE_METER;
	if (aQuizzPlayer.isHome()) {
	    coordonneeX = aQuizzPlayer.getY() * aMetreX + marge + aMetreX * 2.5d;
	} else {
	    coordonneeX = aMetreX * FIELD_HEIGHT - aQuizzPlayer.getY() * aMetreX - marge - aMetreX * 2.5d;
	}
	coordonneeX -= ((double) aPlayerImg.getWidth() / 2);
	coordonneeX += aLateralMarge;
	return coordonneeX;
    }

    @Override
    protected double getPlayerY(QuizzPlayer aQuizzPlayer, double aTerainH, double aMetreY, Bitmap aPlayerImg) {
	double coordonneeY = 0;
	if (aQuizzPlayer.isHome()) {
	    coordonneeY = (START_REPERE - aQuizzPlayer.getX()) * aMetreY;
	} else {
	    coordonneeY = (START_REPERE + aQuizzPlayer.getX()) * aMetreY;
	}
	coordonneeY -= ((double) aPlayerImg.getHeight() / 2);
	return coordonneeY;
    }

    @Override
    protected void printCoach(Canvas canvas, QuizzPlayer qp) {
	double imageX;
	double imageY = 10;
	double textX;
	double textY = _coach.getHeight() + 30;

	if (qp.isHome()) {
	    imageX = 10;
	    textX = imageX;
	} else {
	    imageX = this.getWidth() - _coach.getWidth() - 10;
	    double textWidth = _paint.measureText(qp.getPlayer().getName());
	    textX = this.getWidth() - textWidth - 10;
	}
	canvas.drawBitmap(_coach, (float) imageX, (float) imageY, null);
	canvas.drawText(qp.getPlayer().getName(), (float) textX, (float) textY, _paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
	boolean b = super.onTouchEvent(event);
	return b;
    }

}
