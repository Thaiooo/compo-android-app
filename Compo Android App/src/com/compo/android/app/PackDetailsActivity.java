package com.compo.android.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.compo.android.app.model.Pack;
import com.compo.android.app.utils.FontEnum;

public class PackDetailsActivity extends Activity {
    public final static String MESSAGE_SELECTED_PACK = "com.compo.android.app.PackDetailsActivity.MESSAGE1";

    private static Typeface _font;
    private Pack _selectPack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	requestWindowFeature(Window.FEATURE_NO_TITLE);
	setContentView(R.layout.activity_pack_details);

	if (_font == null) {
	    _font = Typeface.createFromAsset(getAssets(), FontEnum.LUCKY_PENNY.getName());
	}

	Intent intent = getIntent();
	_selectPack = (Pack) intent.getSerializableExtra(MESSAGE_SELECTED_PACK);

	TextView name = (TextView) findViewById(R.id.pack_name);
	name.setTypeface(_font);
	name.setText(_selectPack.getName());

	TextView desciption = (TextView) findViewById(R.id.pack_description);
	desciption.setTypeface(_font);
	desciption.setText(_selectPack.getDescription());

    }

    public void unlockPack(View view) {
	Intent intent = new Intent(PackDetailsActivity.this, StoreActivity.class);
	startActivity(intent);
	this.finish();
    }

    public void cancel(View view) {
	this.finish();
    }

}
