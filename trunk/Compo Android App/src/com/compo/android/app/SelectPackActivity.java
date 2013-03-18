package com.compo.android.app;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.compo.android.app.dao.PackDao;
import com.compo.android.app.model.Pack;

public class SelectPackActivity extends Activity {

    public final static String EXTRA_MESSAGE = "com.compo.android.app.SelectGameActivity.MESSAGE";

    private List<Pack> gamePacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_select_pack);

	gamePacks = loadGamePacks();

	GridView gridview = (GridView) findViewById(R.id.gridview);
	gridview.setAdapter(new SelectPackAdapter(this, gamePacks));

	gridview.setOnItemClickListener(new OnItemClickListener() {
	    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

		Pack selectPack = gamePacks.get(position);
		if (selectPack.isLock()) {
		    Toast.makeText(SelectPackActivity.this, "Ce pack est bloqué", Toast.LENGTH_SHORT).show();
		    return;
		}
		Intent intent = new Intent(SelectPackActivity.this, SelectQuizzActivity.class);
		intent.putExtra(EXTRA_MESSAGE, selectPack);
		startActivity(intent);
	    }
	});

    }

    private List<Pack> loadGamePacks() {
	PackDao dao = new PackDao(this);
	gamePacks = dao.getAllPack();
	return gamePacks;
    }

}
