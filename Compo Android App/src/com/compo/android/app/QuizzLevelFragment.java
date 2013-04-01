package com.compo.android.app;

import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
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

    private GridView _gridview;
    private Pack _selectPack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	final View rootView = inflater.inflate(R.layout.fragment_quizz_menu, container, false);

	_gridview = (GridView) rootView.findViewById(R.id.quizzGrid);
	_selectPack = (Pack) getActivity().getIntent().getSerializableExtra(SelectPackActivity.EXTRA_MESSAGE);

	Bundle args = getArguments();
	int levelIndex = args.getInt(EXTRA_MESSAGE_ARG);
	Level level = Level.getLevel(levelIndex);

	Object[] params = { _selectPack, level };
	new LoadQuizzTask().execute(params);

	return rootView;
    }

    private class LoadQuizzTask extends AsyncTask<Object, Void, List<Quizz>> {
	@Override
	protected List<Quizz> doInBackground(Object... params) {
	    QuizzDao dao = new QuizzDao(getActivity());
	    List<Quizz> _quizzList = dao.getAllQuizz(((Pack) params[0]).getId(), (Level) params[1]);
	    return _quizzList;
	}

	@Override
	protected void onPostExecute(final List<Quizz> aQuizzList) {
	    _gridview.setAdapter(new SelectQuizzAdapter(getActivity(), aQuizzList));
	    _gridview.setOnItemClickListener(new OnItemClickListener() {
		
		@Override
		public void onItemClick(AdapterView<?> parent, View v, int aPosition, long id) {
		    
		    Quizz selectQuizz = aQuizzList.get(aPosition);
		    Intent intent = new Intent(getActivity(), QuizzActivity.class);
		    intent.putExtra(EXTRA_MESSAGE_QUIZZ, selectQuizz);
		    intent.putExtra(EXTRA_MESSAGE_GAME, _selectPack);
		    startActivity(intent);
		}
	    });
	}
    }
}