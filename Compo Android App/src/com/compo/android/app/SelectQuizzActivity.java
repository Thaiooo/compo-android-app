package com.compo.android.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.compo.android.app.model.GamePack;

public class SelectQuizzActivity extends FragmentActivity {

	private SelectQuizzLevelAdapter _collectionQuizzLevelPagerAdapter;
	private ViewPager mViewPager;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_quizz);

		_collectionQuizzLevelPagerAdapter = new SelectQuizzLevelAdapter(
				getSupportFragmentManager());

		Intent intent = getIntent();
		GamePack selectPack = (GamePack) intent
				.getSerializableExtra(SelectGameActivity.EXTRA_MESSAGE);

		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(_collectionQuizzLevelPagerAdapter);
	}

}
