package com.compo.android.app;

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

import com.compo.android.app.model.GamePack;
import com.compo.android.app.model.Quizz;

public class QuizzLevelFragment extends Fragment {

	public final static String EXTRA_MESSAGE_QUIZZ = "com.compo.android.app.QuizzLevelFragment.MESSAGE.QUIZZ";
	public final static String EXTRA_MESSAGE_GAME = "com.compo.android.app.QuizzLevelFragment.MESSAGE.GAME";
	private List<Quizz> _quizzList;
	private GamePack _selectPack;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final View rootView = inflater.inflate(R.layout.fragment_quizz_menu,
				container, false);

		_selectPack = (GamePack) getActivity().getIntent()
				.getSerializableExtra(SelectGameActivity.EXTRA_MESSAGE);
		_quizzList = _selectPack.getQuizzList();

		GridView gridview = (GridView) rootView.findViewById(R.id.quizzGrid);
		gridview.setAdapter(new SelectQuizzAdapter(getActivity(), _quizzList));

		gridview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {

				Quizz selectQuizz = _quizzList.get(position);
				Intent intent = new Intent(getActivity(), QuizzActivity.class);
				intent.putExtra(EXTRA_MESSAGE_QUIZZ, selectQuizz);
				intent.putExtra(EXTRA_MESSAGE_GAME, _selectPack);
				startActivity(intent);
			}
		});

		// Bundle args = getArguments();
		// ((TextView)
		// rootView.findViewById(android.R.id.text1)).setText(Integer
		// .toString(args.getInt(ARG_OBJECT)));

		return rootView;
	}
}