package com.compo.android.app;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.compo.android.app.model.Joueur;
import com.compo.android.app.model.Quizz;

public class QuizzView extends View {

	protected static final int MARGE = 20;

	protected Context _context;
	protected BitmapDrawable _terrain;
	protected BitmapDrawable _playerBleu;
	protected BitmapDrawable _playerRed;
	protected Paint _paint;
	protected int _playerW;
	protected int _playerH;

	protected Quizz quizz;

	public QuizzView(Context context, AttributeSet attrs) {
		super(context, attrs);
		_context = context;

		_playerBleu = (BitmapDrawable) _context.getResources().getDrawable(
				R.drawable.player_bleu);
		_playerRed = (BitmapDrawable) _context.getResources().getDrawable(
				R.drawable.player_red);

		_playerW = _playerBleu.getBitmap().getWidth();
		_playerH = _playerBleu.getBitmap().getHeight();

		_paint = new Paint();
		_paint.setColor(Color.BLACK);
		_paint.setTextSize(20);

		Intent intent = ((Activity) context).getIntent();
		quizz = (Quizz) intent
				.getSerializableExtra(QuizzLevelFragment.EXTRA_MESSAGE_QUIZZ);

	}

	protected BitmapDrawable scaleImage(BitmapDrawable srcTerrain, int destW,
			int destH) {
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

		Bitmap scaledBitmap = Bitmap.createBitmap(srcTerrain.getBitmap(), 0, 0,
				terrainW, terrainH, matrix, true);
		return new BitmapDrawable(scaledBitmap);
	}

	public void setQuizz(Quizz quizz) {
		this.quizz = quizz;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		// =================================================================
		// Terrain
		// =================================================================
		printTerrain(canvas);

		// =================================================================
		// Equipe Domicile
		// =================================================================
		if (quizz.getEquipeDomicile() != null
				&& quizz.getEquipeDomicile().getJoueurs() != null) {
			List<Joueur> joueurs = quizz.getEquipeDomicile().getJoueurs();
			for (Joueur j : joueurs) {
				printPlayerTeam1(canvas, j.getPositionXPercent(),
						j.getPositionYPercent(), j.getName());
			}
		} else {
			// Gardien
			printPlayerTeam1(canvas, 0.5f, 0.0f, "Player 1");

			// Ligne 1
			printPlayerTeam1(canvas, 0.2f, 0.25f, "Player 2");
			printPlayerTeam1(canvas, 0.4f, 0.25f, "Player 3");
			printPlayerTeam1(canvas, 0.6f, 0.25f, "Player 4");
			printPlayerTeam1(canvas, 0.8f, 0.25f, "Player 5");

			// Ligne 2
			printPlayerTeam1(canvas, 0.25f, 0.5f, "Player 6");
			printPlayerTeam1(canvas, 0.5f, 0.5f, "Player 7");
			printPlayerTeam1(canvas, 0.75f, 0.5f, "Player 8");

			// Ligne 3
			printPlayerTeam1(canvas, 0.25f, 0.75f, "Player 9");
			printPlayerTeam1(canvas, 0.5f, 0.75f, "Player 10");
			printPlayerTeam1(canvas, 0.75f, 0.75f, "Player 11");
		}

		// =================================================================
		// Equipe ext
		// =================================================================
		if (quizz.getEquipeExterieur() != null
				&& quizz.getEquipeExterieur().getJoueurs() != null) {
			List<Joueur> joueurs = quizz.getEquipeExterieur().getJoueurs();
			for (Joueur j : joueurs) {
				printPlayerTeam2(canvas, j.getPositionXPercent(),
						j.getPositionYPercent(), j.getName());
			}
		} else {
			// Gardien
			printPlayerTeam2(canvas, 0.5f, 0.0f, "Player 1");

			// Ligne 1
			printPlayerTeam2(canvas, 0.2f, 0.25f, "Player 2");
			printPlayerTeam2(canvas, 0.4f, 0.25f, "Player 3");
			printPlayerTeam2(canvas, 0.6f, 0.25f, "Player 4");
			printPlayerTeam2(canvas, 0.8f, 0.25f, "Player 5");

			// Ligne 2
			printPlayerTeam2(canvas, 0.25f, 0.5f, "Player 6");
			printPlayerTeam2(canvas, 0.5f, 0.5f, "Player 7");
			printPlayerTeam2(canvas, 0.75f, 0.5f, "Player 8");

			// Ligne 3
			printPlayerTeam2(canvas, 0.25f, 0.75f, "Player 9");
			printPlayerTeam2(canvas, 0.5f, 0.75f, "Player 10");
			printPlayerTeam2(canvas, 0.75f, 0.75f, "Player 11");
		}

	}

	protected void printTerrain(Canvas canvas) {
		int screenW = this.getWidth();
		int screenH = this.getHeight();
		BitmapDrawable terrain = (BitmapDrawable) _context.getResources()
				.getDrawable(R.drawable.terrain);
		if (_terrain == null) {
			_terrain = scaleImage(terrain, screenW, screenH);
		}
		int terrainX = (screenW - _terrain.getBitmap().getWidth()) / 2;
		canvas.drawBitmap(_terrain.getBitmap(), terrainX, 0, null);
	}

	protected void printPlayerTeam1(Canvas canvas, float aXPercentPosition,
			float aYPercentPosition, String aName) {
		int screenW = this.getWidth();
		int terainW = _terrain.getBitmap().getWidth();
		int terainH = _terrain.getBitmap().getHeight();
		int lateralMarge = (screenW - terainW) / 2;

		float x = terainW * aXPercentPosition - _playerW / 2 + lateralMarge;
		float y = (terainH / 2) * aYPercentPosition + MARGE;
		canvas.drawBitmap(_playerBleu.getBitmap(), x, y, null);
		canvas.drawText(aName, x, y + _playerH + 20, _paint);
	}

	protected void printPlayerTeam2(Canvas canvas, float aXPercentPosition,
			float aYPercentPosition, String aName) {
		int screenW = this.getWidth();
		int terainW = _terrain.getBitmap().getWidth();
		int terainH = _terrain.getBitmap().getHeight();
		int lateralMarge = (screenW - terainW) / 2;

		float x = terainW * aXPercentPosition - _playerW / 2 + lateralMarge;
		float y = terainH - ((terainH / 2) * aYPercentPosition) - _playerH
				- MARGE;
		canvas.drawBitmap(_playerRed.getBitmap(), x, y, null);
		canvas.drawText(aName, x, y + _playerH + 20, _paint);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean b = super.onTouchEvent(event);
		int screenW = this.getWidth();
		int terainW = _terrain.getBitmap().getWidth();
		int terainH = _terrain.getBitmap().getHeight();
		int lateralMarge = (screenW - terainW) / 2;

		if (quizz.getEquipeDomicile() != null
				&& quizz.getEquipeDomicile().getJoueurs() != null) {
			List<Joueur> joueurs = quizz.getEquipeDomicile().getJoueurs();
			for (Joueur j : joueurs) {
				float minX = terainW * j.getPositionXPercent() - _playerW / 2
						+ lateralMarge;
				float maxX = minX + _playerW;
				float minY = (terainH / 2) * j.getPositionYPercent() + MARGE;
				float maxY = minY + _playerH;

				if (event.getX() >= minX && event.getX() <= maxX
						&& event.getY() >= minY && event.getY() <= maxY) {
					System.out.println(j.getName());
					Toast.makeText(getContext(), j.getName(),
							Toast.LENGTH_SHORT).show();
					return b;
				}
			}
		}

		return b;
	}
}
