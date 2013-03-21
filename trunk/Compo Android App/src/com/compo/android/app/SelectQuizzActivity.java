package com.compo.android.app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class SelectQuizzActivity extends FragmentActivity {

    private SelectQuizzLevelAdapter _collectionQuizzLevelPagerAdapter;
    private ViewPager _mViewPager;

    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_select_quizz);

	_collectionQuizzLevelPagerAdapter = new SelectQuizzLevelAdapter(getSupportFragmentManager());

	_mViewPager = (ViewPager) findViewById(R.id.pager);
	_mViewPager.setAdapter(_collectionQuizzLevelPagerAdapter);
    }

}
