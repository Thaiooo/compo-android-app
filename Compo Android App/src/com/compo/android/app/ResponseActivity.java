package com.compo.android.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.compo.android.app.model.QuizzPlayer;

public class ResponseActivity extends Activity {
    public static final String EXTRA_MESSAGE_ARG = "com.compo.android.app.ResponseActivity.MESSAGE.ARG";
    private static final String TAG = ResponseActivity.class.getName();
    private static Typeface _font;
    private EditText edit;
    private QuizzPlayer currentQuizz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_response);

        Intent intent = getIntent();
        currentQuizz = (QuizzPlayer) intent.getSerializableExtra(EXTRA_MESSAGE_ARG);

        edit = (EditText) findViewById(R.id.edit_response);
        edit.setText(currentQuizz.getPlayer().getName());

        if (_font == null) {
            _font = Typeface.createFromAsset(getAssets(), "Eraser.ttf");
        }
        edit.setTypeface(_font);
    }

    public void check(View view) {
        String response = edit.getText().toString().trim();
        if (response.equals(currentQuizz.getPlayer().getName())) {
            currentQuizz.setDiscovered(true);
            Intent newIntent = new Intent();
            newIntent.putExtra("Selected", "Yess");
            setResult(RESULT_OK, newIntent);
            finish();
        } else {
            Log.v(TAG, "Nooo");
        }


    }
}
