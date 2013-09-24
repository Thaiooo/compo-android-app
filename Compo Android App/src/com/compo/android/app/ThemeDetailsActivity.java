package com.compo.android.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.compo.android.app.model.Theme;

public class ThemeDetailsActivity extends Activity {
    public final static String MESSAGE_SELECTED_THEME = "com.compo.android.app.ThemeDetailsActivity.MESSAGE1";

    private static Typeface _font;
    private Theme _selectTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.activity_pack_details);

	if (_font == null) {
	    _font = Typeface.createFromAsset(getAssets(), "MyLuckyPenny.ttf");
	}

	Intent intent = getIntent();
	_selectTheme = (Theme) intent.getSerializableExtra(MESSAGE_SELECTED_THEME);

	TextView name = (TextView) findViewById(R.id.pack_name);
	name.setTypeface(_font);
	name.setText(_selectTheme.getName());

	// TextView desciption = (TextView) findViewById(R.id.pack_description);
	// desciption.setTypeface(_font);
	// desciption.setText(_selectTheme.getDescription());

	TextView creditPrice = (TextView) findViewById(R.id.credit_price);
	creditPrice.setTypeface(_font);
	creditPrice.setText(_selectTheme.getCreditLimit() + " credits");

    }

    public void unlockPack(View view) {
	Intent intent = new Intent(ThemeDetailsActivity.this, StoreActivity.class);
	startActivity(intent);
	this.finish();
    }

    public void cancel(View view) {
	this.finish();
    }

}