package com.compo.android.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.compo.android.app.dao.PlayDao;
import com.compo.android.app.model.User;
import com.compo.android.app.model.User.Sound;
import com.compo.android.app.service.QuizzService;
import com.compo.android.app.utils.FontEnum;
import com.compo.android.app.utils.UserFactory;

public class SettingActivity extends Activity {

    public static final int EXTRA_MESSAGE_REQUEST_CODE = 1;

    // private static Typeface _font;
    private TextView _title;
    private Button _sound;
    private Button _trash;
    private User _currentUser;
    private Button _cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.activity_setting);

	// if (_font == null) {
	// _font = Typeface.createFromAsset(getAssets(), FontEnum.LUCKY_PENNY.getName());
	// }

	_title = (TextView) findViewById(R.id.store_title);
	// _title.setTypeface(_font);

	_cancel = (Button) findViewById(R.id.button_cancel);
	// _cancel.setTypeface(_font);

	_sound = (Button) findViewById(R.id.button_sound);

	_currentUser = UserFactory.getInstance().getUser(this);
	if (_currentUser.getSoundEnable() == Sound.OFF) {
	    _sound.setBackgroundResource(R.drawable.sound_off);
	} else {
	    _sound.setBackgroundResource(R.drawable.sound_on);
	}

	_trash = (Button) findViewById(R.id.button_trash);
	PlayDao dao = new PlayDao(this);
	int playNb = dao.count();
	if (playNb > 0) {
	    _trash.setBackgroundResource(R.drawable.trash_full);
	} else {
	    _trash.setBackgroundResource(R.drawable.trash_empty);
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

			_trash.setBackgroundResource(R.drawable.trash_empty);
		    }
		}).setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
		    public void onClick(DialogInterface dialog, int id) {
		    }
		});
	builder.create().show();
    }

    public void changeSound(View view) {
	if (_currentUser.getSoundEnable() == Sound.OFF) {
	    _sound.setBackgroundResource(R.drawable.sound_on);
	    _currentUser.setSoundEnable(Sound.ON);
	} else {
	    _sound.setBackgroundResource(R.drawable.sound_off);
	    _currentUser.setSoundEnable(Sound.OFF);
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
