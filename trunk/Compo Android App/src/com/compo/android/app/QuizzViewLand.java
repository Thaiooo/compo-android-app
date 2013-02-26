package com.compo.android.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;

public class QuizzViewLand extends QuizzView {

	protected static final int MARGE = 30;

	public QuizzViewLand(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	protected void printTerrain(Canvas canvas) {
		int screenW = this.getWidth();
		int screenH = this.getHeight();
		BitmapDrawable terrain = (BitmapDrawable) _context.getResources()
				.getDrawable(R.drawable.terrain_land);
		if (_terrain == null) {
			_terrain = scaleImage(terrain, screenW, screenH);
		}
		int terrainX = (screenW - _terrain.getBitmap().getWidth()) / 2;
		canvas.drawBitmap(_terrain.getBitmap(), terrainX, 0, null);
	}

	protected void printPlayerTeam1(Canvas canvas, float aXPercentPosition,
			float aYPercentPosition, String aName) {
		int screenW = this.getWidth();
		int terrainW = _terrain.getBitmap().getWidth();
		int terrainH = _terrain.getBitmap().getHeight();
		int lateralMarge = (screenW - terrainW) / 2;

		float xPlayer = (terrainW / 2) * aYPercentPosition + lateralMarge
				+ MARGE;
		float yPlayer = terrainH * aXPercentPosition - _playerH / 2;
		canvas.drawBitmap(_playerBleu.getBitmap(), xPlayer, yPlayer, null);
		canvas.drawText(aName, xPlayer, yPlayer + _playerH + 20, _paint);

	}

	protected void printPlayerTeam2(Canvas canvas, float aXPercentPosition,
			float aYPercentPosition, String aName) {
		int screenW = this.getWidth();
		int terrainW = _terrain.getBitmap().getWidth();
		int terrainH = _terrain.getBitmap().getHeight();
		int lateralMarge = (screenW - terrainW) / 2;

		float xPlayer = terrainW - (terrainW / 2) * aYPercentPosition
				+ lateralMarge - _playerW - MARGE;
		float yPlayer = terrainH * aXPercentPosition - _playerH / 2;
		canvas.drawBitmap(_playerRed.getBitmap(), xPlayer, yPlayer, null);
		canvas.drawText(aName, xPlayer, yPlayer + _playerH + 20, _paint);

	}
}
