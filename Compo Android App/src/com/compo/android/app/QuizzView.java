package com.compo.android.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.compo.android.app.model.Quizz;
import com.compo.android.app.model.QuizzPlayer;

public class QuizzView extends View {

    /** Field width in meter */
    protected static final int FIELD_WIDTH = 68;
    /** Field width in meter */
    protected static final int FIELD_HEIGHT = 105;
    /** Field width in meter */
    protected static final int START_REPERE = 34;
    /** Marge in meter */
    protected static final int MARGE_METER = 6;
    protected static final int TEXT_HIGHT = 20;
    private static Typeface font;

    protected Context _context;
    protected Bitmap _terrainRaw;
    protected Bitmap _terrain;
    protected Bitmap _playerHomeRaw;
    protected Bitmap _playerHome;
    protected Bitmap _playerAwayRaw;
    protected Bitmap _playerAway;
    protected Bitmap _coachRaw;
    protected Bitmap _coach;
    protected Bitmap _ballRaw;
    protected Bitmap _ball;
    protected Bitmap _ballRedRaw;
    protected Bitmap _ballRed;
    protected Bitmap _greenMappingRaw;
    protected Bitmap _greenMapping;
    protected Paint _paint;
    protected Matrix _matrix;
    protected Matrix _matrixGreenMapping;
    protected Quizz _quizz;

    protected boolean completed = false;

    public QuizzView(Context context, AttributeSet attrs) {
	super(context, attrs);
	_context = context;

	font = Typeface.createFromAsset(_context.getAssets(), "MyLuckyPenny.ttf");

	_coachRaw = ((BitmapDrawable) _context.getResources().getDrawable(R.drawable.coach)).getBitmap();
	_terrainRaw = ((BitmapDrawable) _context.getResources().getDrawable(R.drawable.football_field)).getBitmap();
	_greenMappingRaw = ((BitmapDrawable) _context.getResources().getDrawable(R.drawable.green_mapping)).getBitmap();
	_ballRaw = ((BitmapDrawable) _context.getResources().getDrawable(R.drawable.ball)).getBitmap();
	_ballRedRaw = ((BitmapDrawable) _context.getResources().getDrawable(R.drawable.ball_red)).getBitmap();

	float densityMultiplier = getContext().getResources().getDisplayMetrics().density;
	_paint = new Paint();
	_paint.setTextSize(12.0f * densityMultiplier);
	_paint.setTypeface(font);

	Intent intent = ((Activity) context).getIntent();
	_quizz = (Quizz) intent.getSerializableExtra(QuizzLevelFragment.EXTRA_MESSAGE_QUIZZ);

	_playerHomeRaw = ((BitmapDrawable) _context.getResources().getDrawable(R.drawable.player_bleu)).getBitmap();
	_playerAwayRaw = ((BitmapDrawable) _context.getResources().getDrawable(R.drawable.player_white)).getBitmap();
	// _playerHomeRaw = getBitmapFromAsset("player_bleu.png");
	// _playerAwayRaw = getBitmapFromAsset("player_white.png");

	_matrix = new Matrix();
	_matrixGreenMapping = new Matrix();
    }

    // private Bitmap getBitmapFromAsset(String strName) {
    // AssetManager assetManager = _context.getAssets();
    // InputStream istr = null;
    // try {
    // istr = assetManager.open(strName);
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // Bitmap bitmap = BitmapFactory.decodeStream(istr);
    // return bitmap;
    // }

    protected BitmapDrawable scaleImage(BitmapDrawable srcTerrain, int destW, int destH) {
	int terrainW = srcTerrain.getBitmap().getWidth();
	int terrainH = srcTerrain.getBitmap().getHeight();

	double scaleX = 1;
	if (terrainW > destW) {
	    scaleX = (double) destW / (double) terrainW;
	}
	double scaleY = 1;
	if (terrainH > destH) {
	    scaleY = (double) destH / (double) terrainH;
	}

	double scale = scaleX;
	if (scaleY < scaleX) {
	    scale = scaleY;
	}

	Matrix matrix = new Matrix();
	matrix.postScale((float) scale, (float) scale);

	Bitmap scaledBitmap = Bitmap.createBitmap(srcTerrain.getBitmap(), 0, 0, terrainW, terrainH, matrix, true);
	return new BitmapDrawable(scaledBitmap);
    }

    public void setQuizz(Quizz quizz) {
	this._quizz = quizz;
    }

    protected void setScaleMatrix() {

	int screenW = this.getWidth();
	int screenH = this.getHeight();
	int terrainW = _terrainRaw.getWidth();
	int terrainH = _terrainRaw.getHeight();

	double scaleX = 1;
	if (terrainW > screenW) {
	    scaleX = (double) screenW / (double) terrainW;
	}
	double scaleY = 1;
	if (terrainH > screenH) {
	    scaleY = (double) screenH / (double) terrainH;
	}

	double scale = scaleX;
	if (scaleY < scaleX) {
	    scale = scaleY;
	}

	_matrix.postScale((float) scale, (float) scale);

	// -----------------

	int greenMappingW = _greenMappingRaw.getWidth();
	int greenMappingH = _greenMappingRaw.getHeight();

	scaleX = (double) screenW / (double) greenMappingW;
	scaleY = (double) screenH / (double) greenMappingH;

	_matrixGreenMapping.postScale((float) scaleX, (float) scaleY);
    }

    protected void scaleTerrain() {
	if (_terrain == null) {
	    Bitmap scaledBitmap = Bitmap.createBitmap(_terrainRaw, 0, 0, _terrainRaw.getWidth(),
		    _terrainRaw.getHeight(), _matrix, true);
	    _terrain = scaledBitmap;
	}
    }

    protected void scaleGreenMapping() {
	if (_greenMapping == null) {
	    Bitmap scaledBitmap = Bitmap.createBitmap(_greenMappingRaw, 0, 0, _greenMappingRaw.getWidth(),
		    _greenMappingRaw.getHeight(), _matrixGreenMapping, true);
	    _greenMapping = scaledBitmap;
	}
    }

    protected void scalePlayerHome() {
	if (_playerHome == null) {
	    Bitmap scaledBitmap = Bitmap.createBitmap(_playerHomeRaw, 0, 0, _playerHomeRaw.getWidth(),
		    _playerHomeRaw.getHeight(), _matrix, true);
	    _playerHome = scaledBitmap;
	}
    }

    protected void scalePlayerAway() {
	if (_playerAway == null) {
	    Bitmap scaledBitmap = Bitmap.createBitmap(_playerAwayRaw, 0, 0, _playerAwayRaw.getWidth(),
		    _playerAwayRaw.getHeight(), _matrix, true);
	    _playerAway = scaledBitmap;
	}
    }

    protected void scaleCoach() {
	if (_coach == null) {
	    Bitmap scaledBitmap = Bitmap.createBitmap(_coachRaw, 0, 0, _coachRaw.getWidth(), _coachRaw.getHeight(),
		    _matrix, true);
	    _coach = scaledBitmap;
	}
    }

    protected void scaleBall() {
	if (_ball == null) {
	    Bitmap scaledBitmap = Bitmap.createBitmap(_ballRaw, 0, 0, _ballRaw.getWidth(), _ballRaw.getHeight(),
		    _matrix, true);
	    _ball = scaledBitmap;
	}
	if (_ballRed == null) {
	    Bitmap scaledBitmap = Bitmap.createBitmap(_ballRedRaw, 0, 0, _ballRedRaw.getWidth(),
		    _ballRedRaw.getHeight(), _matrix, true);
	    _ballRed = scaledBitmap;
	}
    }

    protected void printTerrain(Canvas canvas) {
	canvas.drawBitmap(_greenMapping, 0, 0, null);
	int terrainX = (this.getWidth() - _terrain.getWidth()) / 2;
	canvas.drawBitmap(_terrain, terrainX, 0, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
	super.onDraw(canvas);

	completed = false;

	setScaleMatrix();
	scaleTerrain();
	scaleGreenMapping();
	scalePlayerHome();
	scalePlayerAway();
	scaleCoach();
	scaleBall();

	// =================================================================
	// Terrain
	// =================================================================
	printTerrain(canvas);

	// =================================================================
	// Player
	// =================================================================
	for (QuizzPlayer qp : _quizz.getQuizzList()) {
	    if (qp.isCoach()) {
		printCoach(canvas, qp);
	    } else {
		printPlayer(canvas, qp);
	    }
	}

	completed = true;
    }

    protected void printCoach(Canvas canvas, QuizzPlayer qp) {
	double imageX = 10;
	double imageY;
	double textX = 10;
	double textY;

	textX = _coach.getWidth() + 15;
	if (qp.isHome()) {
	    imageY = 10;
	    textY = _coach.getHeight() + 10;
	} else {
	    imageY = this.getHeight() - _coach.getHeight() - 10;
	    textY = this.getHeight() - 10;
	}
	canvas.drawBitmap(_coach, (float) imageX, (float) imageY, null);
	canvas.drawText(qp.getPlayer().getName(), (float) textX, (float) textY, _paint);
    }

    protected double getPixelPerMeterX(double terrainW) {
	double metreX = terrainW / FIELD_WIDTH;
	return metreX;
    }

    protected double getPixelPerMeterY(double terrainH) {
	double metreY = terrainH / FIELD_HEIGHT;
	return metreY;
    }

    protected void printPlayer(Canvas canvas, QuizzPlayer qp) {
	double screenW = this.getWidth();
	double terrainW = _terrain.getWidth();
	double terrainH = _terrain.getHeight();
	double lateralMarge = (screenW - terrainW) / 2;

	double metreX = getPixelPerMeterX(terrainW);
	double metreY = getPixelPerMeterY(terrainH);
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
	double textY = playerY + playerImg.getHeight() + TEXT_HIGHT;
	canvas.drawText(qp.getPlayer().getName(), (float) textX, (float) textY, _paint);
    }

    protected double getPlayerX(QuizzPlayer aQuizzPlayer, double aLateralMarge, double aMetreX, Bitmap aPlayerImg) {
	double coordonneeX = 0;
	if (aQuizzPlayer.isHome()) {
	    coordonneeX = (START_REPERE + aQuizzPlayer.getX()) * aMetreX;
	} else {
	    coordonneeX = (START_REPERE - aQuizzPlayer.getX()) * aMetreX;
	}
	coordonneeX -= ((double) aPlayerImg.getWidth() / 2);
	coordonneeX += aLateralMarge;
	return coordonneeX;
    }

    protected double getPlayerY(QuizzPlayer aQuizzPlauer, double aTerainH, double aMetreY, Bitmap aPlayerImg) {
	double coordonneeY;
	double marge = aMetreY * MARGE_METER;
	if (aQuizzPlauer.isHome()) {
	    coordonneeY = aQuizzPlauer.getY() * aMetreY + marge + aMetreY * 2.5;
	} else {
	    coordonneeY = aTerainH - (aQuizzPlauer.getY() * aMetreY) - marge - aMetreY * 2.5;
	    coordonneeY -= TEXT_HIGHT;
	}
	coordonneeY -= aPlayerImg.getHeight() / 2;
	return coordonneeY;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
	boolean b = super.onTouchEvent(event);
	if (!completed) {
	    return false;
	}
	double screenW = this.getWidth();
	double terrainW = _terrain.getWidth();
	double terrainH = _terrain.getHeight();
	double lateralMarge = (screenW - terrainW) / 2;
	double metreX = getPixelPerMeterX(terrainW);
	double metreY = getPixelPerMeterY(terrainH);

	for (QuizzPlayer qp : _quizz.getQuizzList()) {

	    Bitmap playerImg;
	    if (qp.isHome()) {
		playerImg = _playerHome;
	    } else {
		playerImg = _playerAway;
	    }

	    double playerXMin = getPlayerX(qp, lateralMarge, metreX, playerImg);
	    double playerXMax = playerXMin + (double) playerImg.getWidth();

	    double playerYMin = getPlayerY(qp, terrainH, metreY, playerImg);
	    double playerYMax = playerYMin + playerImg.getHeight() + TEXT_HIGHT;

	    if (event.getX() >= playerXMin && event.getX() <= playerXMax && event.getY() >= playerYMin
		    && event.getY() <= playerYMax) {

		Intent intent = new Intent(getContext(), ResponseActivity.class);
		getContext().startActivity(intent);

		return b;
	    }
	}
	return b;
    }

}