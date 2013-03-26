package com.compo.android.app;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;

public class QuizzViewLand extends QuizzView {

    protected static final int MARGE = 30;

    public QuizzViewLand(Context context, AttributeSet attrs) {
	super(context, attrs);
	// _terrainRaw = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.football_field_land);
    }

    protected void printPlayerTeam1(Canvas canvas, float aXPercentPosition, float aYPercentPosition, String aName) {
	int screenW = this.getWidth();
	// int terrainW = _terrain.getBitmap().getWidth();
	// int terrainH = _terrain.getBitmap().getHeight();
	// int lateralMarge = (screenW - terrainW) / 2;

	// float xPlayer = (terrainW / 2) * aYPercentPosition + lateralMarge + MARGE;
	// float yPlayer = terrainH * aXPercentPosition - _playerHome.getBitmap().getHeight() / 2;
	// canvas.drawBitmap(_playerHome.getBitmap(), xPlayer, yPlayer, null);
	// _paint.setColor(Color.BLACK);
	// canvas.drawText(aName, xPlayer, yPlayer + _playerHome.getBitmap().getHeight() + 20, _paint);

    }

    protected void printPlayerTeam2(Canvas canvas, float aXPercentPosition, float aYPercentPosition, String aName) {
	int screenW = this.getWidth();
	// int terrainW = _terrain.getBitmap().getWidth();
	// int terrainH = _terrain.getBitmap().getHeight();
	// int lateralMarge = (screenW - terrainW) / 2;

	// float xPlayer = terrainW - (terrainW / 2) * aYPercentPosition + lateralMarge
	// - _playerHome.getBitmap().getWidth() - MARGE;
	// float yPlayer = terrainH * aXPercentPosition - _playerHome.getBitmap().getHeight() / 2;
	// canvas.drawBitmap(_playerAway.getBitmap(), xPlayer, yPlayer, null);
	// _paint.setColor(Color.BLACK);
	// canvas.drawText(aName, xPlayer, yPlayer + _playerHome.getBitmap().getHeight() + 20, _paint);

    }
}
