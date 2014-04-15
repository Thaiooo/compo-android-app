package com.compo.android.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.compo.android.app.model.User;
import com.compo.android.app.model.User.Sound;
import com.compo.android.app.service.QuizzService;
import com.compo.android.app.utils.UserFactory;

public class SettingActivity extends Activity {

	public static final int EXTRA_MESSAGE_REQUEST_CODE = 1;

	private Button _sound;
	private User _currentUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_setting);

		_sound = (Button) findViewById(R.id.button_sound);

		_currentUser = UserFactory.getInstance().getUser(this);
		if (_currentUser.getSoundEnable() == Sound.OFF) {
			_sound.setCompoundDrawablesWithIntrinsicBounds(R.drawable.sound_off, 0, 0, 0);
			_sound.setText(R.string.button_setting_sound_off);
		} else {
			_sound.setCompoundDrawablesWithIntrinsicBounds(R.drawable.sound_on, 0, 0, 0);
			_sound.setText(R.string.button_setting_sound_on);
		}

	}

	public void eraseData(View view) {
		// Use the Builder class for convenient dialog construction
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.message_setting_erase_data_confirm)
				.setPositiveButton(R.string.button_validate, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {

						QuizzService service = new QuizzService(SettingActivity.this);
						service.resetAllUserData();
					}
				}).setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
					}
				});
		builder.create().show();
	}

	public void changeSound(View view) {
		if (_currentUser.getSoundEnable() == Sound.OFF) {
			_sound.setCompoundDrawablesWithIntrinsicBounds(R.drawable.sound_on, 0, 0, 0);
			_currentUser.setSoundEnable(Sound.ON);
			_sound.setText(R.string.button_setting_sound_on);
		} else {
			_sound.setCompoundDrawablesWithIntrinsicBounds(R.drawable.sound_off, 0, 0, 0);
			_currentUser.setSoundEnable(Sound.OFF);
			_sound.setText(R.string.button_setting_sound_off);
		}
		UserFactory.getInstance().updateUser(this);

	}

	public void cancel(View view) {
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}
}
