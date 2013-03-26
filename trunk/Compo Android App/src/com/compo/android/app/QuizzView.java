package com.compo.android.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.compo.android.app.model.Player;
import com.compo.android.app.model.Quizz;
import com.compo.android.app.model.QuizzPlayer;

public class QuizzView extends View {

    /** Field width in meter */
    protected static final int FIELD_WIDTH = 68;
    /** Field width in meter */
    protected static final int START_REPERE = 34;
    protected static final int MARGE = 80;
    private static Typeface font;

    protected Context _context;
    protected BitmapDrawable _terrainRaw;
    protected BitmapDrawable _terrain;
    protected BitmapDrawable _playerBleuRaw;
    protected BitmapDrawable _playerBleu;
    protected BitmapDrawable _playerRedRaw;
    protected BitmapDrawable _playerRed;
    protected BitmapDrawable _greenMappingRaw;
    protected BitmapDrawable _greenMapping;
    protected Paint _paint;
    protected Matrix _matrix;
    protected Matrix _matrixGreenMapping;
    protected Quizz quizz;

    public QuizzView(Context context, AttributeSet attrs) {
	super(context, attrs);
	_context = context;

	font = Typeface.createFromAsset(_context.getAssets(), "MyLuckyPenny.ttf");

	_playerBleuRaw = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.player_bleu);
	_playerRedRaw = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.player_red);
	_terrainRaw = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.football_field);
	_greenMappingRaw = (BitmapDrawable) _context.getResources().getDrawable(R.drawable.green_mapping);

	float densityMultiplier = getContext().getResources().getDisplayMetrics().density;
	_paint = new Paint();
	_paint.setTextSize(12.0f * densityMultiplier);
	_paint.setTypeface(font);

	Intent intent = ((Activity) context).getIntent();
	quizz = (Quizz) intent.getSerializableExtra(QuizzLevelFragment.EXTRA_MESSAGE_QUIZZ);

	_matrix = new Matrix();
	_matrixGreenMapping = new Matrix();

    }

    protected BitmapDrawable scaleImage(BitmapDrawable srcTerrain, int destW, int destH) {
	int terrainW = srcTerrain.getBitmap().getWidth();
	int terrainH = srcTerrain.getBitmap().getHeight();

	float scaleX = 1;
	if (terrainW > destW) {
	    scaleX = (float) destW / (float) terrainW;
	}
	float scaleY = 1;
	if (terrainH > destH) {
	    scaleY = (float) destH / (float) terrainH;
	}

	float scale = scaleX;
	if (scaleY < scaleX) {
	    scale = scaleY;
	}

	Matrix matrix = new Matrix();
	matrix.postScale(scale, scale);

	Bitmap scaledBitmap = Bitmap.createBitmap(srcTerrain.getBitmap(), 0, 0, terrainW, terrainH, matrix, true);
	return new BitmapDrawable(scaledBitmap);
    }

    public void setQuizz(Quizz quizz) {
	this.quizz = quizz;
    }

    protected void setScaleMatrix() {
	int screenW = this.getWidth();
	int screenH = this.getHeight();
	int terrainW = _terrainRaw.getBitmap().getWidth();
	int terrainH = _terrainRaw.getBitmap().getHeight();

	float scaleX = 1;
	if (terrainW > screenW) {
	    scaleX = (float) screenW / (float) terrainW;
	}
	float scaleY = 1;
	if (terrainH > screenH) {
	    scaleY = (float) screenH / (float) terrainH;
	}

	float scale = scaleX;
	if (scaleY < scaleX) {
	    scale = scaleY;
	}

	_matrix.postScale(scale, scale);

	// -----------------

	int greenMappingW = _greenMappingRaw.getBitmap().getWidth();
	int greenMappingH = _greenMappingRaw.getBitmap().getHeight();

	scaleX = 1;
	if (greenMappingW > screenW) {
	    scaleX = (float) screenW / (float) greenMappingW;
	}
	scaleY = 1;
	if (greenMappingH > screenH) {
	    scaleY = (float) screenH / (float) greenMappingH;
	}

	_matrixGreenMapping.postScale(scaleX, scaleY);
    }

    protected void scaleTerrain() {
	if (_terrain == null) {
	    Bitmap scaledBitmap = Bitmap.createBitmap(_terrainRaw.getBitmap(), 0, 0,
		    _terrainRaw.getBitmap().getWidth(), _terrainRaw.getBitmap().getHeight(), _matrix, true);
	    _terrain = new BitmapDrawable(scaledBitmap);
	}
    }

    protected void scaleGreenMapping() {
	if (_greenMapping == null) {
	    Bitmap scaledBitmap = Bitmap.createBitmap(_greenMappingRaw.getBitmap(), 0, 0, _greenMappingRaw.getBitmap()
		    .getWidth(), _greenMappingRaw.getBitmap().getHeight(), _matrixGreenMapping, true);
	    _greenMapping = new BitmapDrawable(scaledBitmap);
	}
    }

    protected void scalePlayerBleu() {
	if (_playerBleu == null) {
	    Bitmap scaledBitmap = Bitmap.createBitmap(_playerBleuRaw.getBitmap(), 0, 0, _playerBleuRaw.getBitmap()
		    .getWidth(), _playerBleuRaw.getBitmap().getHeight(), _matrix, true);
	    _playerBleu = new BitmapDrawable(scaledBitmap);
	}
    }

    protected void scalePlayerRed() {
	if (_playerRed == null) {
	    Bitmap scaledBitmap = Bitmap.createBitmap(_playerRedRaw.getBitmap(), 0, 0, _playerRedRaw.getBitmap()
		    .getWidth(), _playerRedRaw.getBitmap().getHeight(), _matrix, true);
	    _playerRed = new BitmapDrawable(scaledBitmap);
	}
    }

    protected void printTerrain(Canvas canvas) {
	canvas.drawBitmap(_greenMapping.getBitmap(), 0, 0, null);
	int terrainX = (this.getWidth() - _terrain.getBitmap().getWidth()) / 2;
	canvas.drawBitmap(_terrain.getBitmap(), terrainX, 0, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
	super.onDraw(canvas);

	setScaleMatrix();
	scaleTerrain();
	scaleGreenMapping();
	scalePlayerBleu();
	scalePlayerRed();

	// =================================================================
	// Terrain
	// =================================================================
	printTerrain(canvas);

	// =================================================================
	// Equipe Domicile
	// =================================================================
	for (QuizzPlayer qp : quizz.getQuizzList()) {
	    if (qp.isHome()) {
		printHomePlayer(canvas, qp.getX(), qp.getY(), qp.getPlayer());
	    }
	}

	// =================================================================
	// Equipe ext
	// =================================================================
	for (QuizzPlayer qp : quizz.getQuizzList()) {
	    if (!qp.isHome()) {
		printAwayPlayer(canvas, qp.getX(), qp.getY(), qp.getPlayer());
	    }
	}
    }

    protected void printHomePlayer(Canvas canvas, float aRealPositionX, float aRealPositionY, Player aName) {
	double screenW = this.getWidth();
	double terrainW = _terrain.getBitmap().getWidth();
	double terainH = _terrain.getBitmap().getHeight();
	double lateralMarge = (screenW - terrainW) / 2;

	double metreX = terrainW / FIELD_WIDTH;
	double coordonneeX = 0;
	if (aRealPositionX >= 0) {
	    coordonneeX = aRealPositionX * metreX;
	    coordonneeX += START_REPERE * metreX;
	} else {
	    coordonneeX = (START_REPERE + aRealPositionX) * metreX;
	}

	double metreY = terainH / 100;
	double coordonneeY = aRealPositionY * metreY;

	double x = coordonneeX - ((double) _playerBleu.getBitmap().getWidth() / 2) + lateralMarge;
	double y = coordonneeY + MARGE;
	canvas.drawBitmap(_playerBleu.getBitmap(), (float) x, (float) y, null);

	double textWidth = _paint.measureText(aName.getName());
	double textDecal = textWidth / 2;
	double textX = coordonneeX + lateralMarge - textDecal;
	_paint.setColor(Color.BLACK);
	canvas.drawText(aName.getName(), (float) textX, (float) y + _playerBleu.getBitmap().getHeight() + 20, _paint);
    }

    protected void printAwayPlayer(Canvas canvas, float aRealPositionX, float aRealPositionY, Player aName) {
	double screenW = this.getWidth();
	double terainW = _terrain.getBitmap().getWidth();
	double terainH = _terrain.getBitmap().getHeight();
	double lateralMarge = (screenW - terainW) / 2;

	double metreX = terainW / FIELD_WIDTH;
	double coordonneeX = 0;
	if (aRealPositionX <= 0) {
	    coordonneeX = Math.abs(aRealPositionX) * metreX;
	    coordonneeX += START_REPERE * metreX;
	} else {
	    coordonneeX = (START_REPERE - aRealPositionX) * metreX;
	}

	double metreY = terainH / 100;
	double coordonneeY = terainH - (aRealPositionY * metreY);

	double x = coordonneeX - _playerBleu.getBitmap().getWidth() / 2 + lateralMarge;
	double y = coordonneeY - _playerBleu.getBitmap().getHeight() - 20 - MARGE;
	canvas.drawBitmap(_playerRed.getBitmap(), (float) x, (float) y, null);

	double textWidth = _paint.measureText(aName.getName());
	double textDecal = textWidth / 2;
	double textX = coordonneeX + lateralMarge - textDecal;
	_paint.setColor(Color.BLACK);
	canvas.drawText(aName.getName(), (float) textX, (float) y + _playerBleu.getBitmap().getHeight() + 20, _paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
	boolean b = super.onTouchEvent(event);
	int screenW = this.getWidth();
	int terainW = _terrain.getBitmap().getWidth();
	int terainH = _terrain.getBitmap().getHeight();
	int lateralMarge = (screenW - terainW) / 2;

	/*
	 * if (quizz.getEquipeDomicile() != null && quizz.getEquipeDomicile().getJoueurs() != null) { List<Player>
	 * joueurs = quizz.getEquipeDomicile().getJoueurs(); for (Player j : joueurs) { float minX = terainW *
	 * j.getPositionXPercent() - _playerBleu.getBitmap().getWidth() / 2 + lateralMarge; float maxX = minX +
	 * _playerBleu.getBitmap().getWidth(); float minY = (terainH / 2) * j.getPositionYPercent() + MARGE; float maxY
	 * = minY + _playerBleu.getBitmap().getHeight();
	 * 
	 * if (event.getX() >= minX && event.getX() <= maxX && event.getY() >= minY && event.getY() <= maxY) {
	 * Toast.makeText(getContext(), j.getName(), Toast.LENGTH_SHORT).show();
	 * 
	 * Intent intent = new Intent(_context, ResponseActivity.class); _context.startActivity(intent);
	 * 
	 * return b; } } }
	 */

	return b;
    }
}
