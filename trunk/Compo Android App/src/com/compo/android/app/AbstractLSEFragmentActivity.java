package com.compo.android.app;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.View;

public abstract class AbstractLSEFragmentActivity extends FragmentActivity {

    public void home(View view) {
	Intent intent = new Intent(this, MainActivity.class);
	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	startActivity(intent);
	overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public void back(View view) {
	finish();
    }

}
