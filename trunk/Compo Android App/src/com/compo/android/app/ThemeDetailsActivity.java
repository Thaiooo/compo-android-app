package com.compo.android.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.compo.android.app.model.Theme;
import com.compo.android.app.utils.FontEnum;

public class ThemeDetailsActivity extends Activity {
    public final static String MESSAGE_SELECTED_THEME = "com.compo.android.app.ThemeDetailsActivity.MESSAGE1";

    private static Typeface _fontTitle;
    private static Typeface _fontContent;
    private static Typeface _fontButton;
    private Theme _selectTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.activity_theme_details);

	if (_fontTitle == null) {
	    _fontTitle = Typeface.createFromAsset(getAssets(),
		    FontEnum.DIALOG_TITLE.getName());
	}
	if (_fontContent == null) {
	    _fontContent = Typeface.createFromAsset(getAssets(),
		    FontEnum.DIALOG_CONTENT.getName());
	}
	if (_fontButton == null) {
	    _fontButton = Typeface.createFromAsset(getAssets(),
		    FontEnum.BUTTON.getName());
	}

	Intent intent = getIntent();
	_selectTheme = (Theme) intent
		.getSerializableExtra(MESSAGE_SELECTED_THEME);

	TextView name = (TextView) findViewById(R.id.activity_theme_name);
	name.setTypeface(_fontTitle);
	name.setText(_selectTheme.getName());

	Button cancel = (Button) findViewById(R.id.button_cancel);
	cancel.setTypeface(_fontButton);
	Button submit = (Button) findViewById(R.id.button_submit);
	submit.setTypeface(_fontButton);

	// TextView desciption = (TextView) findViewById(R.id.pack_description);
	// desciption.setTypeface(_fontContent);
	// desciption.setText(_selectTheme.getDescription());

	StringBuffer message = new StringBuffer();
	message.append(_selectTheme.getCreditLimit());
	message.append(" ");
	message.append(getString(R.string.label_credit));
	TextView creditPrice = (TextView) findViewById(R.id.credit_price);
	creditPrice.setTypeface(_fontContent);
	creditPrice.setText(message.toString());

    }

    public void unlockPack(View view) {
	Intent intent = new Intent(ThemeDetailsActivity.this,
		StoreActivity.class);
	startActivity(intent);
	this.finish();
    }

    public void cancel(View view) {
	this.finish();
    }

}
