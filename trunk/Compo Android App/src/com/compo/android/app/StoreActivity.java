package com.compo.android.app;

import com.compo.android.app.utils.FontEnum;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class StoreActivity extends Activity {

    private static Typeface _fontTitle;
    private static Typeface _fontButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.activity_store);

	if (_fontTitle == null) {
	    _fontTitle = Typeface.createFromAsset(getAssets(),
		    FontEnum.DIALOG_TITLE.getName());
	}
	if (_fontButton == null) {
	    _fontButton = Typeface.createFromAsset(getAssets(),
		    FontEnum.BUTTON.getName());
	}

	TextView title = (TextView) findViewById(R.id.store_title);
	title.setTypeface(_fontTitle);

	Button cancel = (Button) findViewById(R.id.button_cancel);
	cancel.setTypeface(_fontButton);

	// Button buttonCredit25 = (Button) findViewById(R.id.button_credit_25);
	// Button buttonCredit100 = (Button)
	// findViewById(R.id.button_credit_100);
	// Button buttonCredit500 = (Button)
	// findViewById(R.id.button_credit_500);
	// Button buttonCancel = (Button) findViewById(R.id.button_cancel);

    }

    public void cancel(View view) {
	finish();
    }

}
