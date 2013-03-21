package com.compo.android.app;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.compo.android.app.dao.PackDao;
import com.compo.android.app.dao.UserDao;
import com.compo.android.app.model.Pack;
import com.compo.android.app.model.User;

public class SelectPackActivity extends Activity {

    public final static String EXTRA_MESSAGE = "com.compo.android.app.SelectGameActivity.MESSAGE";

    private List<Pack> _gamePacks;
    private User _user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_select_pack);

	loadGamePacks();
	loadUser();

	TextView userCredit = (TextView) findViewById(R.id.user_credit);
	userCredit.setText(_user.getCredit() + "");
	TextView userPoint = (TextView) findViewById(R.id.user_score);
	userPoint.setText(_user.getPoint() + " pts");

	GridView gridview = (GridView) findViewById(R.id.gridview);
	gridview.setAdapter(new SelectPackAdapter(this, _gamePacks));

	gridview.setOnItemClickListener(new OnItemClickListener() {
	    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

		Pack selectPack = _gamePacks.get(position);
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

    private void loadGamePacks() {
	PackDao dao = new PackDao(this);
	_gamePacks = dao.getAllPack();
    }

    private void loadUser() {
	UserDao dao = new UserDao(this);
	_user = dao.getUser();
    }

}
