package com.compo.android.app;

import java.util.ArrayList;
import java.util.List;

import com.compo.android.app.model.GamePack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class SelectGameActivity extends Activity {

	public final static String EXTRA_MESSAGE = "com.compo.android.app.MESSAGE";

	private List<GamePack> gamePacks;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_game);

		gamePacks = loadGamePacks();

		GridView gridview = (GridView) findViewById(R.id.gridview);
		gridview.setAdapter(new GameSelectAdapter(this, gamePacks));

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {

				GamePack selectPack = gamePacks.get(position);
				Intent intent = new Intent(SelectGameActivity.this,
						SelectQuizzActivity.class);
				intent.putExtra(EXTRA_MESSAGE, selectPack);
				startActivity(intent);
			}
		});
	}

	private List<GamePack> loadGamePacks() {
		// TODO: Chargement des packs
		List<GamePack> gamePacks = new ArrayList<GamePack>();
		for (int i = 1; i < 5; i++) {
			GamePack pack = new GamePack();
			pack.setLock(false);
			pack.setName("Pack " + i);
			gamePacks.add(pack);
		}
		for (int i = 5; i < 12; i++) {
			GamePack pack = new GamePack();
			pack.setLock(true);
			pack.setName("Pack " + i);
			gamePacks.add(pack);
		}
		return gamePacks;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}

}
