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

import com.compo.android.app.dao.QuizzDao;
import com.compo.android.app.model.Level;
import com.compo.android.app.model.Pack;
import com.compo.android.app.model.Quizz;

public class QuizzLevelFragment extends Fragment {

    public static final String EXTRA_MESSAGE_ARG = "com.compo.android.app.QuizzLevelFragment.MESSAGE.ARG";
    public static final String EXTRA_MESSAGE_QUIZZ = "com.compo.android.app.QuizzLevelFragment.MESSAGE.QUIZZ";
    public static final String EXTRA_MESSAGE_GAME = "com.compo.android.app.QuizzLevelFragment.MESSAGE.GAME";
    private List<Quizz> _quizzList;
    private Pack _selectPack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	final View rootView = inflater.inflate(R.layout.fragment_quizz_menu, container, false);

	_selectPack = (Pack) getActivity().getIntent().getSerializableExtra(SelectPackActivity.EXTRA_MESSAGE);

	Bundle args = getArguments();
	int levelIndex = args.getInt(EXTRA_MESSAGE_ARG);
	Level level = Level.getLevel(levelIndex);

	loadQuizz(_selectPack.getId(), level);

	GridView gridview = (GridView) rootView.findViewById(R.id.quizzGrid);
	gridview.setAdapter(new SelectQuizzAdapter(getActivity(), _quizzList));

	gridview.setOnItemClickListener(new OnItemClickListener() {
	    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

		Quizz selectQuizz = _quizzList.get(position);
		Intent intent = new Intent(getActivity(), QuizzActivity.class);
		intent.putExtra(EXTRA_MESSAGE_QUIZZ, selectQuizz);
		intent.putExtra(EXTRA_MESSAGE_GAME, _selectPack);
		startActivity(intent);
	    }
	});

	return rootView;
    }

    private void loadQuizz(long aPackId, Level aLevel) {
	QuizzDao dao = new QuizzDao(getActivity());
	_quizzList = dao.getAllQuizz(aPackId, aLevel);
    }
}