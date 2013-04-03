package com.compo.android.app;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.compo.android.app.dao.UserDao;
import com.compo.android.app.model.User;

public class SelectQuizzActivity extends FragmentActivity {

    private ViewPager _mViewPager;
    private TextView _userCredit;
    private TextView _userPoint;

    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_select_quizz);

	_userCredit = (TextView) findViewById(R.id.user_credit);
	_userPoint = (TextView) findViewById(R.id.user_point);
	_mViewPager = (ViewPager) findViewById(R.id.pager);

	SelectQuizzLevelAdapter collectionQuizzLevelPagerAdapter = new SelectQuizzLevelAdapter(
		getSupportFragmentManager());
	_mViewPager.setAdapter(collectionQuizzLevelPagerAdapter);

	new LoadUserTask().execute();
    }

    private class LoadUserTask extends AsyncTask<Void, Void, User> {
	@Override
	protected User doInBackground(Void... params) {
	    UserDao dao = new UserDao(SelectQuizzActivity.this);
	    User u = dao.getUser();
	    return u;
	}

	@Override
	protected void onPostExecute(User anUser) {
	    _userCredit.setText(anUser.getCredit() + "");
	    _userPoint.setText(anUser.getPoint() + " pts");
	}
    }

}
