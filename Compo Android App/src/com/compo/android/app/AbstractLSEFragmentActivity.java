package com.compo.android.app;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.compo.android.app.model.User;
import com.compo.android.app.utils.UserFactory;

public abstract class AbstractLSEFragmentActivity extends FragmentActivity {

	public static final int REQUEST_CODE_SETTING = 1;
	public static final int REQUEST_CODE_STORE = 2;
	public static final int REQUEST_CODE_PACK = 3;
	public static final int REQUEST_CODE_RESPONSE = 4;
	public static final int REQUEST_CODE_HINT_DIALOG = 5;
	public static final int REQUEST_CODE_SUCCESS_DIALOG = 6;
	public static final int REQUEST_CODE_MATCH = 7;
	public static final int REQUEST_CODE_LIST_MATCH = 8;

	protected Button _userCredit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		createDatabse();
		setContentView(getContentViewId());
		_userCredit = (Button) findViewById(R.id.button_store);
		new LoadUserTask().execute();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		User u = UserFactory.getInstance().getUser(getBaseContext());
		_userCredit.setText(Integer.toString(u.getCredit()));

		switch (requestCode) {
		case REQUEST_CODE_SETTING:
			if (resultCode == RESULT_OK) {
				home(null);
			}
			break;

		default:
			break;
		}
	}

	public void home(View view) {
		Intent intent = new Intent(this, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}

	public void setting(View view) {
		Intent intent = new Intent(this, SettingActivity.class);
		startActivityForResult(intent, REQUEST_CODE_SETTING);
	}

	public void store(View view) {
		Intent intent = new Intent(this, StoreActivity.class);
		startActivityForResult(intent, REQUEST_CODE_STORE);
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
			User u = UserFactory.getInstance().getUser(getBaseContext());
			_userCredit.setText(Integer.toString(u.getCredit()));
			return null;
		}
	}

}
