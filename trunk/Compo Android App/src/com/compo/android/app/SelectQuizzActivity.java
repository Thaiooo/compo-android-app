package com.compo.android.app;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.compo.android.app.model.User;
import com.compo.android.app.utils.UserFactory;

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

    private class LoadUserTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            User u = UserFactory.getInstance().getUser();
            _userCredit.setText(u.getCredit() + "");
            _userPoint.setText(u.getPoint() + " pts");
            return null;
        }

    }

}
