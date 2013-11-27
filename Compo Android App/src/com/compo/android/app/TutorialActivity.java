package com.compo.android.app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;

public class TutorialActivity extends FragmentActivity {

    private ViewPager _mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.activity_tutorial);

	_mViewPager = (ViewPager) findViewById(R.id.pager);
	TutorialPageAdapter collectionTutorialPagerAdapter = new TutorialPageAdapter(getSupportFragmentManager());
	_mViewPager.setAdapter(collectionTutorialPagerAdapter);
    }

    public void cancel(View view) {
	finish();
    }

}
