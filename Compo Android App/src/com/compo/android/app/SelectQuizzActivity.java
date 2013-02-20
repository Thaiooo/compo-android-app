package com.compo.android.app;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.compo.android.app.model.GamePack;

public class SelectQuizzActivity extends FragmentActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments representing each object in a collection. We use a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter} derivative,
	 * which will destroy and re-create fragments as needed, saving and
	 * restoring their state in the process. This is important to conserve
	 * memory and is a best practice when allowing navigation between objects in
	 * a potentially large collection.
	 */
	private QuizzLevelSelectAdapter _collectionQuizzLevelPagerAdapter;

	/**
	 * The {@link android.support.v4.view.ViewPager} that will display the
	 * object collection.
	 */
	private ViewPager mViewPager;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_quizz);

		// Create an adapter that when requested, will return a fragment
		// representing an object in
		// the collection.
		// ViewPager and its adapters use support library fragments, so we must
		// use getSupportFragmentManager.
		_collectionQuizzLevelPagerAdapter = new QuizzLevelSelectAdapter(
				getSupportFragmentManager());

		// Set up action bar.
		ActionBar actionBar = getActionBar();

		Intent intent = getIntent();
		GamePack selectPack = (GamePack) intent
				.getSerializableExtra(SelectGameActivity.EXTRA_MESSAGE);
		actionBar.setTitle(selectPack.getName());

		// Specify that the Home button should show an "Up" caret, indicating
		// that touching the
		// button will take the user one step up in the application's hierarchy.
		actionBar.setDisplayHomeAsUpEnabled(true);

		// Set up the ViewPager, attaching the adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(_collectionQuizzLevelPagerAdapter);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// app icon in action bar clicked; go home
			Intent intent = new Intent(this, SelectGameActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
