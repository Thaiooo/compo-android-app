package com.compo.android.app;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.compo.android.app.utils.FontEnum;

public class StoreActivity extends Activity {

    private static Typeface _font;
    private TextView _title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.activity_store);

	if (_font == null) {
	    _font = Typeface.createFromAsset(getAssets(), FontEnum.LUCKY_PENNY.getName());
	}

	Button buttonCredit25 = (Button) findViewById(R.id.button_credit_25);
	Button buttonCredit100 = (Button) findViewById(R.id.button_credit_100);
	Button buttonCredit500 = (Button) findViewById(R.id.button_credit_500);
	Button buttonCancel = (Button) findViewById(R.id.button_cancel);
	buttonCredit25.setTypeface(_font);
	buttonCredit100.setTypeface(_font);
	buttonCredit500.setTypeface(_font);
	buttonCancel.setTypeface(_font);

	_title = (TextView) findViewById(R.id.store_title);
	_title.setTypeface(_font);

    }

    public void cancel(View view) {
	finish();
    }

}
