package com.compo.android.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

public class QuizzView extends View {

	private Context _context;
	private BitmapDrawable _terrain;
	private BitmapDrawable _playerBleu;
	private BitmapDrawable _playerRed;
	private int _playerW;
	private int _playerH;

	public QuizzView(Context context, AttributeSet attrs) {
		super(context, attrs);
		_context = context;

		_playerBleu = (BitmapDrawable) _context.getResources().getDrawable(
				R.drawable.player_bleu);
		_playerRed = (BitmapDrawable) _context.getResources().getDrawable(
				R.drawable.player_red);

		_playerW = _playerBleu.getBitmap().getWidth();
		_playerH = _playerBleu.getBitmap().getHeight();
	}

	private BitmapDrawable scaleImage(BitmapDrawable srcTerrain, int destW,
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

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int screenW = this.getWidth();
		int screenH = this.getHeight();

		// =================================================================
		// Terrain
		// =================================================================
		BitmapDrawable terrain = (BitmapDrawable) _context.getResources()
				.getDrawable(R.drawable.terrain);
		_terrain = scaleImage(terrain, screenW, screenH);
		int terrainX = (screenW - _terrain.getBitmap().getWidth()) / 2;
		canvas.drawBitmap(_terrain.getBitmap(), terrainX, 0, null);

		// =================================================================
		// Equipe 1
		// =================================================================

		int terainW = _terrain.getBitmap().getWidth();
		int terainH = _terrain.getBitmap().getHeight();
		int marge = (screenW - terainW) / 2;

		// Gardien
		int xPlayer = terainW / 2 - _playerW / 2 + marge;
		int yPlayer = 20;
		canvas.drawBitmap(_playerBleu.getBitmap(), xPlayer, yPlayer, null);

		// Ligne 1
		xPlayer = terainW / 5 - _playerW / 2 + marge;
		yPlayer = terainH / 2 / 4 + 20;
		canvas.drawBitmap(_playerBleu.getBitmap(), xPlayer, yPlayer, null);
		xPlayer = (terainW / 5) * 2 - _playerW / 2 + marge;
		canvas.drawBitmap(_playerBleu.getBitmap(), xPlayer, yPlayer, null);
		xPlayer = (terainW / 5) * 3 - _playerW / 2 + marge;
		canvas.drawBitmap(_playerBleu.getBitmap(), xPlayer, yPlayer, null);
		xPlayer = (terainW / 5) * 4 - _playerW / 2 + marge;
		canvas.drawBitmap(_playerBleu.getBitmap(), xPlayer, yPlayer, null);

		// Ligne 2
		xPlayer = (terainW / 4) * 1 - _playerW / 2 + marge;
		yPlayer = (terainH / 2 / 4) * 2 + 20;
		canvas.drawBitmap(_playerBleu.getBitmap(), xPlayer, yPlayer, null);
		xPlayer = (terainW / 4) * 2 - _playerW / 2 + marge;
		canvas.drawBitmap(_playerBleu.getBitmap(), xPlayer, yPlayer, null);
		xPlayer = (terainW / 4) * 3 - _playerW / 2 + marge;
		canvas.drawBitmap(_playerBleu.getBitmap(), xPlayer, yPlayer, null);

		// Ligne 3
		xPlayer = (terainW / 4) * 1 - _playerW / 2 + marge;
		yPlayer = (terainH / 2 / 4) * 3 + 20;
		canvas.drawBitmap(_playerBleu.getBitmap(), xPlayer, yPlayer, null);
		xPlayer = (terainW / 4) * 2 - _playerW / 2 + marge;
		canvas.drawBitmap(_playerBleu.getBitmap(), xPlayer, yPlayer, null);
		xPlayer = (terainW / 4) * 3 - _playerW / 2 + marge;
		canvas.drawBitmap(_playerBleu.getBitmap(), xPlayer, yPlayer, null);

		// =================================================================
		// Equipe 2
		// =================================================================
		// Gardien
		xPlayer = terainW / 2 - _playerW / 2 + marge;
		yPlayer = terainH - _playerH - 20;
		canvas.drawBitmap(_playerRed.getBitmap(), xPlayer, yPlayer, null);

		// Ligne 1
		xPlayer = terainW / 5 - _playerW / 2 + marge;
		yPlayer = terainH - (terainH / 2 / 4) - _playerH - 20;
		canvas.drawBitmap(_playerRed.getBitmap(), xPlayer, yPlayer, null);
		xPlayer = (terainW / 5) * 2 - _playerW / 2 + marge;
		canvas.drawBitmap(_playerRed.getBitmap(), xPlayer, yPlayer, null);
		xPlayer = (terainW / 5) * 3 - _playerW / 2 + marge;
		canvas.drawBitmap(_playerRed.getBitmap(), xPlayer, yPlayer, null);
		xPlayer = (terainW / 5) * 4 - _playerW / 2 + marge;
		canvas.drawBitmap(_playerRed.getBitmap(), xPlayer, yPlayer, null);

		// Ligne 2
		xPlayer = (terainW / 4) * 1 - _playerW / 2 + marge;
		yPlayer = terainH - (terainH / 2 / 4) * 2 - _playerH - 20;
		canvas.drawBitmap(_playerRed.getBitmap(), xPlayer, yPlayer, null);
		xPlayer = (terainW / 4) * 2 - _playerW / 2 + marge;
		canvas.drawBitmap(_playerRed.getBitmap(), xPlayer, yPlayer, null);
		xPlayer = (terainW / 4) * 3 - _playerW / 2 + marge;
		canvas.drawBitmap(_playerRed.getBitmap(), xPlayer, yPlayer, null);

		// Ligne 3
		xPlayer = (terainW / 4) * 1 - _playerW / 2 + marge;
		yPlayer = terainH - (terainH / 2 / 4) * 3 - _playerH - 20;
		canvas.drawBitmap(_playerRed.getBitmap(), xPlayer, yPlayer, null);
		xPlayer = (terainW / 4) * 2 - _playerW / 2 + marge;
		canvas.drawBitmap(_playerRed.getBitmap(), xPlayer, yPlayer, null);
		xPlayer = (terainW / 4) * 3 - _playerW / 2 + marge;
		canvas.drawBitmap(_playerRed.getBitmap(), xPlayer, yPlayer, null);
	}
}
