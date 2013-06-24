package com.compo.android.app;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.compo.android.app.dao.QuizzDao;
import com.compo.android.app.model.Pack;
import com.compo.android.app.model.Quizz;
import com.compo.android.app.model.Theme;
import com.compo.android.app.model.User;
import com.compo.android.app.utils.UserFactory;

import java.util.List;

public class SelectQuizzActivity extends FragmentActivity {

    public static final String EXTRA_MESSAGE_ARG = "com.compo.android.app.QuizzLevelFragment.MESSAGE.ARG";
    public static final String EXTRA_MESSAGE_QUIZZ = "com.compo.android.app.QuizzLevelFragment.MESSAGE.QUIZZ";
    public static final String EXTRA_MESSAGE_GAME = "com.compo.android.app.QuizzLevelFragment.MESSAGE.GAME";
    private static Typeface _fontTitle;
    private TextView _userCredit;
    private TextView _userPoint;
    private TextView _theme_name;
    private TextView _pack_name;
    private GridView _gridview;
    private Pack _selectPack;
    private Theme _selectTheme;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_quizz);

        if (_fontTitle == null) {
            _fontTitle = Typeface.createFromAsset(getAssets(), "Eraser.ttf");
        }

        Intent intent = getIntent();
        _selectTheme = (Theme) intent.getSerializableExtra(PackFragment.EXTRA_MESSAGE_THEME);
        _selectPack = (Pack) intent.getSerializableExtra(PackFragment.EXTRA_MESSAGE_PACK);

        _userCredit = (TextView) findViewById(R.id.user_credit);
        _userPoint = (TextView) findViewById(R.id.user_point);

        _theme_name = (TextView) findViewById(R.id.theme_name);
        _theme_name.setTypeface(_fontTitle);
        _theme_name.setText(_selectTheme.getName());

        _pack_name = (TextView) findViewById(R.id.pack_name);
        _pack_name.setText(_selectPack.getName());
        _pack_name.setTypeface(_fontTitle);

        _gridview = (GridView) findViewById(R.id.quizzGrid);

        new LoadUserTask().execute();

        Object[] params = {_selectPack};
        new LoadQuizzTask().execute(params);
    }

    private class LoadUserTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            User u = UserFactory.getInstance().getUser();
            _userCredit.setText(u.getCredit() + "");
            _userPoint.setText(u.getPoint() + " pts");
            return null;
        }

    }

    private class LoadQuizzTask extends AsyncTask<Object, Void, List<Quizz>> {
        @Override
        protected List<Quizz> doInBackground(Object... params) {
            QuizzDao dao = new QuizzDao(SelectQuizzActivity.this);
            List<Quizz> quizzList = dao.getAllQuizz(((Pack) params[0]).getId());
            return quizzList;
        }

        @Override
        protected void onPostExecute(final List<Quizz> aQuizzList) {
            _gridview.setAdapter(new SelectQuizzAdapter(SelectQuizzActivity.this, aQuizzList));
            _gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View v, int aPosition, long id) {
                    Quizz selectQuizz = aQuizzList.get(aPosition);
                    Intent intent = new Intent(SelectQuizzActivity.this, QuizzActivity.class);
                    intent.putExtra(EXTRA_MESSAGE_QUIZZ, selectQuizz);
                    intent.putExtra(EXTRA_MESSAGE_GAME, _selectPack);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            });
        }
    }

}
