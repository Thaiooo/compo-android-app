package com.compo.android.app;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class SettingActivity extends Activity {

    private static Typeface _font;
    private TextView _title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setting);

        if (_font == null) {
            _font = Typeface.createFromAsset(getAssets(), "MyLuckyPenny.ttf");
        }

        _title = (TextView) findViewById(R.id.store_title);
        _title.setTypeface(_font);
    }

    public void changeLanguage(View view) {
        System.out.println("Change language");
    }

    public void changeSound(View view) {
        System.out.println("Change sound");
    }

}
