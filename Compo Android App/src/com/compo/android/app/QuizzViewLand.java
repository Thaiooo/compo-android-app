package com.compo.android.app;

import com.compo.android.app.model.QuizzPlayer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;

public class QuizzViewLand extends QuizzView {

    protected static final int MARGE = 30;

    public QuizzViewLand(Context context, AttributeSet attrs) {
	super(context, attrs);
	_terrainRaw = ((BitmapDrawable) _context.getResources().getDrawable(R.drawable.football_field_land))
		.getBitmap();
	_greenMappingRaw = ((BitmapDrawable) _context.getResources().getDrawable(R.drawable.green_mapping_land))
		.getBitmap();
    }

    protected void printPlayer(Canvas canvas, QuizzPlayer qp) {
	double screenW = this.getWidth();
	double terrainW = _terrain.getWidth();
	double terrainH = _terrain.getHeight();
	double lateralMarge = (screenW - terrainW) / 2;

	double metreX = terrainW / FIELD_HEIGHT;
	double metreY = terrainH / FIELD_WIDTH;
	Bitmap playerImg;

	if (qp.isHome()) {
	    playerImg = _playerHome;
	} else {
	    playerImg = _playerAway;
	}

	double playerX = getPlayerX(qp, lateralMarge, metreX, playerImg);
	double playerY = getPlayerY(qp, terrainH, metreY, playerImg);
	canvas.drawBitmap(playerImg, (float) playerX, (float) playerY, null);

	int ballNumber = 0;
	if (qp.getGoal() > 0) {
	    for (int i = 0; i < qp.getGoal(); i++) {
		double ballX = playerX + playerImg.getWidth() - _ball.getWidth();
		ballX += ballNumber * _ball.getWidth();
		double ballY = playerY + playerImg.getHeight() - _ball.getHeight();
		canvas.drawBitmap(_ball, (float) ballX, (float) ballY, null);
		ballNumber++;
	    }
	}
	if (qp.getCsc() > 0) {
	    for (int i = 0; i < qp.getCsc(); i++) {
		double ballX = playerX + playerImg.getWidth() - _ballRed.getWidth();
		ballX += ballNumber * _ball.getWidth();
		double ballY = playerY + playerImg.getHeight() - _ballRed.getHeight();
		canvas.drawBitmap(_ballRed, (float) ballX, (float) ballY, null);
		ballNumber++;
	    }
	}

	double textWidth = _paint.measureText(qp.getPlayer().getName());
	double textDecal = textWidth / 2;

	double textX = playerX + ((double) playerImg.getWidth() / 2) - textDecal;
	double textY = playerY + playerImg.getHeight() + 20;
	canvas.drawText(qp.getPlayer().getName(), (float) textX, (float) textY, _paint);
    }

    protected double getPlayerX(QuizzPlayer aQuizzPlayer, double aLateralMarge, double aMetreX, Bitmap aPlayerImg) {
	double coordonneeX = 0;
	if (aQuizzPlayer.isHome()) {
	    coordonneeX = aQuizzPlayer.getY() * aMetreX;
	    coordonneeX += aMetreX * 2.5d;
	} else {
	    coordonneeX = aMetreX * FIELD_HEIGHT - aQuizzPlayer.getY() * aMetreX;
	    coordonneeX -= aMetreX * 2.5d;
	}

	double playerX = coordonneeX - ((double) aPlayerImg.getWidth() / 2) + aLateralMarge;
	return playerX;
    }

    protected double getPlayerY(QuizzPlayer aQuizzPlayer, double aTerainH, double aMetreY, Bitmap aPlayerImg) {
	double coordonneeY = 0;
	if (aQuizzPlayer.isHome()) {
	    coordonneeY = (START_REPERE - aQuizzPlayer.getX()) * aMetreY;
	} else {
	    coordonneeY = (START_REPERE + aQuizzPlayer.getX()) * aMetreY;
	}

	double playerY = coordonneeY - ((double) aPlayerImg.getHeight() / 2);
	return playerY;
    }

}
