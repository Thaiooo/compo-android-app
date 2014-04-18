package com.compo.android.app;

import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

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
import android.widget.Toast;

import com.compo.android.app.model.Match;
import com.compo.android.app.model.Play;
import com.compo.android.app.model.QuizzPlayer;
import com.compo.android.app.model.Team;
import com.compo.android.app.utils.FontEnum;

public class MatchView extends View {

	// private static final String TAG = MatchView.class.getName();

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
	protected static final int START_REPERE_X = 34;

	private static Typeface font;

	private static final String USER_PREFIX = "player_";

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
	protected Bitmap _start;
	protected Bitmap _cercle;
	protected Paint _paint;
	protected Matrix _matrix;
	protected boolean _completed = false;

	protected Match _selectedMatch;
	protected Map<Long, Play> _mapQuizzToPlay;

	private boolean _colorHomeForAwayTeam = true;

	public MatchView(Context context, AttributeSet attrs) {
		super(context, attrs);
		_context = context;

		if (!isInEditMode()) {

			font = Typeface.createFromAsset(_context.getAssets(), FontEnum.FIELD_TITLE.getName());

			_coachRaw = ((BitmapDrawable) _context.getResources().getDrawable(R.drawable.coach)).getBitmap();
			_terrainRaw = ((BitmapDrawable) _context.getResources().getDrawable(R.drawable.soccer_field_grass))
					.getBitmap();
			_ballRaw = ((BitmapDrawable) _context.getResources().getDrawable(R.drawable.goal)).getBitmap();
			_ballRedRaw = ((BitmapDrawable) _context.getResources().getDrawable(R.drawable.csc)).getBitmap();
			_start = ((BitmapDrawable) _context.getResources().getDrawable(R.drawable.start)).getBitmap();
			_cercle = ((BitmapDrawable) _context.getResources().getDrawable(R.drawable.hide)).getBitmap();

			float densityMultiplier = getContext().getResources().getDisplayMetrics().density;
			_paint = new Paint();
			_paint.setTextSize(10.0f * densityMultiplier);
			_paint.setTypeface(font);

			Intent intent = ((Activity) context).getIntent();
			_selectedMatch = (Match) intent.getSerializableExtra(MatchActivity.REQ_MESSAGE_MATCH);

			loadShirtImg();

		}
	}

	private void loadShirtImg() {
		Team home = null;
		Team away = null;
		for (QuizzPlayer qp : _selectedMatch.getQuizzs()) {
			if (qp.isHome()) {
				home = qp.getTeam();
			} else {
				away = qp.getTeam();
			}
			if (home != null && away != null) {
				break;
			}
		}

		int idHome = getResources().getIdentifier(
				USER_PREFIX + home.getHomeJerseyColor().name().toLowerCase(Locale.US), "drawable",
				getContext().getPackageName());
		int idAway = getResources().getIdentifier(
				USER_PREFIX + away.getHomeJerseyColor().name().toLowerCase(Locale.US), "drawable",
				getContext().getPackageName());
		if (idAway == idHome) {
			_colorHomeForAwayTeam = false;
			idAway = getResources().getIdentifier(
					USER_PREFIX + away.getAwayJerseyColor().name().toLowerCase(Locale.US), "drawable",
					getContext().getPackageName());
		}

		_playerHomeRaw = ((BitmapDrawable) _context.getResources().getDrawable(idHome)).getBitmap();
		_playerAwayRaw = ((BitmapDrawable) _context.getResources().getDrawable(idAway)).getBitmap();

		_playerHome = null;
		_playerAway = null;

		_matrix = new Matrix();
	}

	@Override
	public void invalidate() {
		super.invalidate();
		loadShirtImg();
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

	public void setQuizz(Match quizz) {
		this._selectedMatch = quizz;
	}

	public void setMapQuizzToPlay(Map<Long, Play> aMapQuizzToPlay) {
		this._mapQuizzToPlay = aMapQuizzToPlay;
	}

	protected void setScaleMatrix() {

		int screenW = this.getWidth();
		int screenH = this.getHeight();
		int terrainW = _terrainRaw.getWidth();
		int terrainH = _terrainRaw.getHeight();

		double scaleX = (double) screenW / (double) terrainW;
		double scaleY = (double) screenH / (double) terrainH;

		double scale = scaleX;
		if (scaleY < scaleX) {
			scale = scaleY;
		}

		_matrix.postScale((float) scale, (float) scale);
	}

	protected void scaleTerrain() {
		if (_terrain == null) {
			Bitmap scaledBitmap = Bitmap.createBitmap(_terrainRaw, 0, 0, _terrainRaw.getWidth(),
					_terrainRaw.getHeight(), _matrix, true);
			_terrain = scaledBitmap;
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
		int terrainX = (this.getWidth() - _terrain.getWidth()) / 2;
		canvas.drawBitmap(_terrain, terrainX, 0, null);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (!isInEditMode()) {

			_completed = false;

			setScaleMatrix();
			scaleTerrain();
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
			for (QuizzPlayer qp : _selectedMatch.getQuizzs()) {
				if (qp.isCoach()) {
					printCoach(canvas, qp);
				} else {
					printPlayer(canvas, qp);
				}
			}

			_completed = true;
		}

	}

	protected void printCoach(Canvas canvas, QuizzPlayer qp) {
		double imageX = 10;
		double imageY;
		double textY;
		double textX = _coach.getWidth() + 15;
		if (qp.isHome()) {
			imageY = 10;
			textY = _paint.getTextSize() + 10;
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

	private boolean isDiscovered(QuizzPlayer qp) {
		Play play = _mapQuizzToPlay.get(qp.getId());
		if (play != null && StringUtils.equalsIgnoreCase(play.getResponse(), qp.getPlayer().getName())) {
			return true;
		} else {
			return false;
		}
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
		if (qp.isHide()) {
			Bitmap backImg;

			if (isDiscovered(qp)) {
				backImg = _start;
			} else {
				backImg = _cercle;
			}
			double backImgX = getPlayerX(qp, lateralMarge, metreX, backImg);
			double backImgY = getPlayerY(qp, terrainH, metreY, backImg);
			canvas.drawBitmap(backImg, (float) backImgX, (float) backImgY, null);

			if (isDiscovered(qp)) {
				canvas.drawBitmap(playerImg, (float) playerX, (float) playerY, null);
			}
		} else {
			canvas.drawBitmap(playerImg, (float) playerX, (float) playerY, null);
		}

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

		if (!qp.isHide() || isDiscovered(qp)) {
			double textWidth = _paint.measureText(qp.getPlayer().getName());
			double textDecal = textWidth / 2;

			double textX = playerX + ((double) playerImg.getWidth() / 2) - textDecal;
			double textY = playerY + playerImg.getHeight() + _paint.getTextSize();
			StringBuffer s = new StringBuffer(qp.getPlayer().getName());
			if (qp.isCaptain()) {
				s.append(" (C)");
			}
			canvas.drawText(s.toString(), (float) textX, (float) textY, _paint);
		}
	}

	protected double getPlayerX(QuizzPlayer aQuizzPlayer, double aLateralMarge, double aMetreX, Bitmap aPlayerImg) {
		double coordonneeX = (START_REPERE_X + aQuizzPlayer.getX()) * aMetreX;
		coordonneeX -= ((double) aPlayerImg.getWidth() / 2);
		coordonneeX += aLateralMarge;
		return coordonneeX;
	}

	protected double getPlayerY(QuizzPlayer aQuizzPlauer, double aTerainH, double aMetreY, Bitmap aPlayerImg) {
		double coordonneeY;

		coordonneeY = (aTerainH / 2) + (aQuizzPlauer.getY() * aMetreY * -1);
		if (aQuizzPlauer.isHome()) {
			coordonneeY += aMetreY * 4;
		} else {
			coordonneeY -= aMetreY * 4;
		}
		coordonneeY -= aPlayerImg.getHeight() / 2;
		return coordonneeY;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean b = super.onTouchEvent(event);
		if (!_completed) {
			return false;
		}
		double screenW = this.getWidth();
		double terrainW = _terrain.getWidth();
		double terrainH = _terrain.getHeight();
		double lateralMarge = (screenW - terrainW) / 2;
		double metreX = getPixelPerMeterX(terrainW);
		double metreY = getPixelPerMeterY(terrainH);

		for (QuizzPlayer qp : _selectedMatch.getQuizzs()) {
			if (qp.isCoach()) {
				continue;
			}

			Bitmap img = _cercle;
			double playerXMin = getPlayerX(qp, lateralMarge, metreX, img);
			double playerXMax = playerXMin + (double) img.getWidth();

			double playerYMin = getPlayerY(qp, terrainH, metreY, img);
			double playerYMax = playerYMin + img.getHeight() + _paint.getTextSize();

			if (event.getX() >= playerXMin && event.getX() <= playerXMax && event.getY() >= playerYMin
					&& event.getY() <= playerYMax) {

				if (!qp.isHide() || isDiscovered(qp)) {
					Toast.makeText(getContext(), qp.getPlayer().getName(), Toast.LENGTH_SHORT).show();
					return b;
				} else {
					Intent intent = new Intent(getContext(), ResponseActivity.class);
					intent.putExtra(ResponseActivity.EXTRA_MESSAGE_QUIZZ, qp);
					intent.putExtra(ResponseActivity.EXTRA_MESSAGE_PLAY, _mapQuizzToPlay.get(qp.getId()));
					if (qp.isHome()) {
						intent.putExtra(ResponseActivity.EXTRA_MESSAGE_HOME_COLOR, true);
					} else {
						intent.putExtra(ResponseActivity.EXTRA_MESSAGE_HOME_COLOR, _colorHomeForAwayTeam);
					}

					((MatchActivity) getContext()).startActivityForResult(intent,
							MatchActivity.REQUEST_CODE_RESPONSE);

					return b;
				}

			}
		}
		return b;
	}

}
