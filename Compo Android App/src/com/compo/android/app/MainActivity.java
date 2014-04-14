package com.compo.android.app;

import java.io.IOException;
import java.util.List;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.compo.android.app.dao.DataBaseHelper;
import com.compo.android.app.dao.ThemeDao;
import com.compo.android.app.model.Theme;
import com.compo.android.app.utils.FontEnum;

public class MainActivity extends AbstractLSEFragmentActivity {
    private static final String TAG = MainActivity.class.getName();

    public static final int EXTRA_MESSAGE_REQUEST_CODE = 1;
    
    
    private static Typeface _fontTitle;
    private ViewPager _mViewPager;
    private TextView _activity_theme_title;
    private LinearLayout _themeIndicatorListLayout;
    private Button _button_preview;
    private Button _button_next;
    private int pageSize = 0;
    
    
    @Override
    protected int getContentViewId() {
	return R.layout.activity_main;
    }
    
    @Override
    protected void createDatabse() {
	DataBaseHelper myDbHelper = new DataBaseHelper(this);
	try {
	    myDbHelper.createDataBase();
	} catch (IOException ioe) {
	    throw new Error("Unable to create database");
	}
    }
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	if (_fontTitle == null) {
	    _fontTitle = Typeface.createFromAsset(getAssets(), FontEnum.TITLE.getName());
	}

	_mViewPager = (ViewPager) findViewById(R.id.pager);
	_themeIndicatorListLayout = (LinearLayout) findViewById(R.id.theme_indicator_list_layout);
	_button_preview = (Button) findViewById(R.id.button_preview);
	_button_next = (Button) findViewById(R.id.button_next);

	_activity_theme_title = (TextView) findViewById(R.id.activity_theme_title);
	_activity_theme_title.setTypeface(_fontTitle);

	_mViewPager.setOnPageChangeListener(new PageListener());

	new LoadThemeTask().execute();
	
	
	
	

//	Button btnBack = (Button) findViewById(R.id.button_back);
//	btnBack.setVisibility(View.INVISIBLE);
//	Button btnHome = (Button) findViewById(R.id.button_home);
//	btnHome.setVisibility(View.INVISIBLE);

//	_userCredit = (TextView) findViewById(R.id.user_credit);

//	Button buttonPlay = (Button) findViewById(R.id.button_play);
//	Button buttonStore = (Button) findViewById(R.id.button_store);
//	Button buttonSetting = (Button) findViewById(R.id.button_setting);
//
//	if (_font == null) {
//	    _font = Typeface.createFromAsset(getAssets(), FontEnum.LUCKY_PENNY.getName());
//	}
//	buttonPlay.setTypeface(_font);
//	buttonStore.setTypeface(_font);
//	buttonSetting.setTypeface(_font);

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//	return false;
//    }
//
//    @Override
//    protected void onRestart() {
//	super.onRestart();
//	new LoadUserTask().execute();
//    }

//    public void store(View view) {
//	Intent intent = new Intent(MainActivity.this, StoreActivity.class);
//	startActivityForResult(intent, 1);
//    }
//
//    public void setting(View view) {
//	Intent intent = new Intent(MainActivity.this, SettingActivity.class);
//	startActivityForResult(intent, 1);
//    }
//
//    public void tutorial(View view) {
//	Intent intent = new Intent(MainActivity.this, TutorialActivity.class);
//	startActivity(intent);
//    }


    
    
    public void previewTheme(View view) {
   	_mViewPager.setCurrentItem(_mViewPager.getCurrentItem() - 1, true);

   	_themeIndicatorListLayout.removeAllViews();
   	for (int i = 0; i < pageSize; i++) {
   	    ImageView ball = new ImageView(MainActivity.this);
   	    if (i == _mViewPager.getCurrentItem()) {
   		ball.setImageResource(R.drawable.element_selected);
   	    } else {
   		ball.setImageResource(R.drawable.element_unselected);
   	    }
   	    _themeIndicatorListLayout.addView(ball);
   	}

   	if (_mViewPager.getCurrentItem() == 0) {
   	    _button_preview.setVisibility(View.INVISIBLE);
   	}
   	_button_next.setVisibility(View.VISIBLE);
       }

       public void nextTheme(View view) {
   	_mViewPager.setCurrentItem(_mViewPager.getCurrentItem() + 1, true);
   	_themeIndicatorListLayout.removeAllViews();
   	for (int i = 0; i < pageSize; i++) {
   	    ImageView ball = new ImageView(MainActivity.this);
   	    if (i == _mViewPager.getCurrentItem()) {
   		ball.setImageResource(R.drawable.element_selected);
   	    } else {
   		ball.setImageResource(R.drawable.element_unselected);
   	    }
   	    _themeIndicatorListLayout.addView(ball);
   	}

   	if (_mViewPager.getCurrentItem() == (pageSize - 1)) {
   	    _button_next.setVisibility(View.INVISIBLE);
   	}
   	_button_preview.setVisibility(View.VISIBLE);
       }

       private class LoadThemeTask extends AsyncTask<Void, Void, List<Theme>> {
   	@Override
   	protected List<Theme> doInBackground(Void... params) {
   	    ThemeDao dao = new ThemeDao(MainActivity.this);
   	    List<Theme> themes = dao.getAllTheme();
   	    return themes;
   	}

   	@Override
   	protected void onPostExecute(final List<Theme> aThemes) {
   	    SelectThemeAdapter collectionThemeLevelPagerAdapter = new SelectThemeAdapter(getSupportFragmentManager(),
   		    aThemes);
   	    _mViewPager.setAdapter(collectionThemeLevelPagerAdapter);

   	    for (int i = 0; i < aThemes.size(); i++) {
   		ImageView ball = new ImageView(MainActivity.this);
   		if (i == 0) {
   		    ball.setImageResource(R.drawable.element_selected);
   		} else {
   		    ball.setImageResource(R.drawable.element_unselected);
   		}
   		_themeIndicatorListLayout.addView(ball);
   	    }

   	    if (aThemes.size() == 0) {
   		_button_next.setVisibility(View.INVISIBLE);
   	    }

   	    pageSize = aThemes.size();
   	}
       }

       private class PageListener extends SimpleOnPageChangeListener {
   	@Override
   	public void onPageSelected(int position) {
   	    super.onPageSelected(position);
   	    _themeIndicatorListLayout.removeAllViews();
   	    for (int i = 0; i < pageSize; i++) {
   		ImageView ball = new ImageView(MainActivity.this);
   		if (i == position) {
   		    ball.setImageResource(R.drawable.element_selected);
   		} else {
   		    ball.setImageResource(R.drawable.element_unselected);
   		}
   		_themeIndicatorListLayout.addView(ball);
   	    }

   	    if (_mViewPager.getCurrentItem() == (pageSize - 1)) {
   		_button_next.setVisibility(View.INVISIBLE);
   		_button_preview.setVisibility(View.VISIBLE);
   	    } else if (_mViewPager.getCurrentItem() == 0) {
   		_button_next.setVisibility(View.VISIBLE);
   		_button_preview.setVisibility(View.INVISIBLE);
   	    } else {
   		_button_next.setVisibility(View.VISIBLE);
   		_button_preview.setVisibility(View.VISIBLE);
   	    }
   	}
       }

}
