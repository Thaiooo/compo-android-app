package com.compo.android.app;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.compo.android.app.model.Pack;
import com.compo.android.app.model.Player;
import com.compo.android.app.model.Quizz;
import com.compo.android.app.model.Team;

public class SelectPackActivity extends Activity {

	public final static String EXTRA_MESSAGE = "com.compo.android.app.SelectGameActivity.MESSAGE";

	private List<Pack> gamePacks;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_pack);

		gamePacks = loadGamePacks();

		GridView gridview = (GridView) findViewById(R.id.gridview);
		gridview.setAdapter(new SelectPackAdapter(this, gamePacks));

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {

				Pack selectPack = gamePacks.get(position);
				if (selectPack.isLock()) {
					Toast.makeText(SelectPackActivity.this,
							"Ce pack est bloqué", Toast.LENGTH_SHORT).show();
					return;
				}
				Intent intent = new Intent(SelectPackActivity.this,
						SelectQuizzActivity.class);
				intent.putExtra(EXTRA_MESSAGE, selectPack);
				startActivity(intent);
			}
		});

	}

	private List<Pack> loadGamePacks() {
		// TODO: Chargement des packs
		List<Pack> gamePacks = new ArrayList<Pack>();

		/*
		Pack pack = createPack("Ligue 1 2013", false);

		Quizz quizz = new Quizz();
		quizz.setSuccess(true);
		quizz.setName("PSG 2 - 0 OM");
		pack.getQuizzList().add(quizz);
		gamePacks.add(pack);

		Team dom = new Team("PSG");
		Player j = new Player("SIRIGU", 30, 0.5f, 0.0f);
		dom.getJoueurs().add(j);

		j = new Player("JALLET", 26, 0.1f, 0.25f);
		dom.getJoueurs().add(j);
		j = new Player("ALEX", 13, 0.36f, 0.25f);
		dom.getJoueurs().add(j);
		j = new Player("ARMAND", 22, 0.64f, 0.25f);
		dom.getJoueurs().add(j);
		j = new Player("MAXWELL", 17, 0.9f, 0.25f);
		dom.getJoueurs().add(j);

		j = new Player("LUCAS", 29, 0.1f, 0.5f);
		dom.getJoueurs().add(j);
		j = new Player("VERRATI", 24, 0.36f, 0.5f);
		dom.getJoueurs().add(j);
		j = new Player("MATUIDI", 14, 0.64f, 0.5f);
		dom.getJoueurs().add(j);
		j = new Player("PASTORE", 27, 0.9f, 0.5f);
		dom.getJoueurs().add(j);

		j = new Player("LAVEZZI", 11, 0.33f, 0.75f);
		dom.getJoueurs().add(j);
		j = new Player("IBRAHIMOVIC", 10, 0.66f, 0.75f);
		dom.getJoueurs().add(j);

		Team ext = new Team("OM");
		quizz.setEquipeDomicile(dom);
		quizz.setEquipeExterieur(ext);

		quizz = new Quizz();
		quizz.setSuccess(false);
		quizz.setName("OL 3 - 1 Lorient");
		pack.getQuizzList().add(quizz);
		gamePacks.add(pack);

		for (int i = 2; i < 5; i++) {
			pack = new Pack();
			pack.setLock(false);
			pack.setName("Pack " + i);
			pack.setQuizzList(loadQuizzList());
			gamePacks.add(pack);
		}
		for (int i = 5; i < 12; i++) {
			pack = new Pack();
			pack.setLock(true);
			pack.setName("Pack " + i);
			gamePacks.add(pack);
		}
		*/
		
		return gamePacks;
	}

	private Pack createPack(String aName, boolean isLock) {
		Pack pack = new Pack();
		pack.setLock(isLock);
		pack.setName(aName);
		return pack;
	}

	private List<Quizz> loadQuizzList() {
		// TODO: Chargement des quizz
		List<Quizz> gamePacks = new ArrayList<Quizz>();
		/*
		for (int i = 1; i < 2; i++) {
			Quizz quizz = new Quizz();
			quizz.setSuccess(true);
			quizz.setName("Match " + i);
			gamePacks.add(quizz);
		}
		for (int i = 2; i < 16; i++) {
			Quizz pack = new Quizz();
			pack.setSuccess(false);
			pack.setName("Match " + i);
			gamePacks.add(pack);
		}
		*/
		return gamePacks;
	}

}
