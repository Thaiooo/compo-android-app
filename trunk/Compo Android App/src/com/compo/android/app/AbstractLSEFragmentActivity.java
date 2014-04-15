package com.compo.android.app;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

public abstract class AbstractLSEFragmentActivity extends FragmentActivity {

	// protected TextView _userCredit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		createDatabse();
		setContentView(getContentViewId());
		// _userCredit = (TextView) findViewById(R.id.user_credit);
		new LoadUserTask().execute();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// User u = UserFactory.getInstance().getUser(getBaseContext());
		// _userCredit.setText(Integer.toString(u.getCredit()));
	}

	public void home(View view) {
		Intent intent = new Intent(this, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		overridePendingTransition(android.R.anim.fade_in,
				android.R.anim.fade_out);
	}

	public void setting(View view) {
		Intent intent = new Intent(this, SettingActivity.class);
		startActivityForResult(intent, 1);
	}

	public void store(View view) {
		Intent intent = new Intent(this, StoreActivity.class);
		startActivityForResult(intent, 1);
	}

	// public void tutorial(View view) {
	// Intent intent = new Intent(MainActivity.this, TutorialActivity.class);
	// startActivity(intent);
	// }

	public void back(View view) {
		finish();
	}

	protected abstract void createDatabse();

	protected abstract int getContentViewId();

	private class LoadUserTask extends AsyncTask<Void, Void, Void> {
		@Override
		protected Void doInBackground(Void... params) {
			// User u = UserFactory.getInstance().getUser(getBaseContext());
			// _userCredit.setText(Integer.toString(u.getCredit()));
			return null;
		}
	}

}
