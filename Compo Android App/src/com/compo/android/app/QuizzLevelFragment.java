package com.compo.android.app;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.compo.android.app.model.Quizz;

public class QuizzLevelFragment extends Fragment {

	public final static String EXTRA_MESSAGE = "com.compo.android.app.QuizzLevelFragment.MESSAGE";
	private List<Quizz> quizzList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_quizz_menu,
				container, false);

		quizzList = loadQuizzList();

		GridView gridview = (GridView) rootView.findViewById(R.id.quizzGrid);
		gridview.setAdapter(new QuizzSelectAdapter(getActivity(), quizzList));

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {

				Quizz selectQuizz = quizzList.get(position);
				Intent intent = new Intent(getActivity(), QuizzActivity.class);
				intent.putExtra(EXTRA_MESSAGE, selectQuizz);
				startActivity(intent);
			}
		});

		// Bundle args = getArguments();
		// ((TextView)
		// rootView.findViewById(android.R.id.text1)).setText(Integer
		// .toString(args.getInt(ARG_OBJECT)));

		return rootView;
	}

	private List<Quizz> loadQuizzList() {
		// TODO: Chargement des packs
		List<Quizz> gamePacks = new ArrayList<Quizz>();
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
		return gamePacks;
	}
}