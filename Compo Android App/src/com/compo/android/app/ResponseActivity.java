package com.compo.android.app;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Window;
import android.widget.EditText;

public class ResponseActivity extends Activity {

    private static Typeface _font;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.activity_response);

	EditText edit = (EditText) findViewById(R.id.edit_response);

	if (_font == null) {
	    _font = Typeface.createFromAsset(getAssets(), "MyLuckyPenny.ttf");
	}
	edit.setTypeface(_font);
    }

}
