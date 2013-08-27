package com.compo.android.app;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class TutorialActivity extends FragmentActivity {

    private static Typeface _font;
    private TextView _title;
    private ViewPager _mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.activity_tutorial);

	if (_font == null) {
	    _font = Typeface.createFromAsset(getAssets(), "MyLuckyPenny.ttf");
	}

	_title = (TextView) findViewById(R.id.tutorial_title);
	_title.setTypeface(_font);

	_mViewPager = (ViewPager) findViewById(R.id.pager);
	TutorialPageAdapter collectionTutorialPagerAdapter = new TutorialPageAdapter(getSupportFragmentManager());
	_mViewPager.setAdapter(collectionTutorialPagerAdapter);
    }

    public void cancel(View view) {
	finish();
    }

}
