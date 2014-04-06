package com.compo.android.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class StoreActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.activity_store);

	// Button buttonCredit25 = (Button) findViewById(R.id.button_credit_25);
	// Button buttonCredit100 = (Button) findViewById(R.id.button_credit_100);
	// Button buttonCredit500 = (Button) findViewById(R.id.button_credit_500);
	// Button buttonCancel = (Button) findViewById(R.id.button_cancel);

    }

    public void cancel(View view) {
	finish();
    }

}
