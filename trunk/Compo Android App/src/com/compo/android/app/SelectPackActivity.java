package com.compo.android.app;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.compo.android.app.dao.PackDao;
import com.compo.android.app.dao.UserDao;
import com.compo.android.app.model.Pack;
import com.compo.android.app.model.User;

public class SelectPackActivity extends Activity {

    public final static String EXTRA_MESSAGE = "com.compo.android.app.SelectGameActivity.MESSAGE";

    private GridView _gridview;
    private TextView _userCredit;
    private TextView _userPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_select_pack);

	_userCredit = (TextView) findViewById(R.id.user_credit);
	_userPoint = (TextView) findViewById(R.id.user_point);
	_gridview = (GridView) findViewById(R.id.gridview);

	new LoadUserTask().execute();
	new LoadPackTask().execute();
    }

    private class LoadUserTask extends AsyncTask<Void, Void, User> {
	@Override
	protected User doInBackground(Void... params) {
	    UserDao dao = new UserDao(SelectPackActivity.this);
	    User u = dao.getUser();
	    return u;
	}

	@Override
	protected void onPostExecute(User anUser) {
	    _userCredit.setText(anUser.getCredit() + "");
	    _userPoint.setText(anUser.getPoint() + " pts");
	}
    }

    private class LoadPackTask extends AsyncTask<Void, Void, List<Pack>> {
	@Override
	protected List<Pack> doInBackground(Void... params) {
	    PackDao dao = new PackDao(SelectPackActivity.this);
	    List<Pack> gamePacks = dao.getAllPack();
	    return gamePacks;
	}

	@Override
	protected void onPostExecute(final List<Pack> aPacks) {
	    _gridview.setAdapter(new SelectPackAdapter(SelectPackActivity.this, aPacks));
	    _gridview.setOnItemClickListener(new OnItemClickListener() {
		public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

		    Pack selectPack = aPacks.get(position);
		    if (selectPack.isLock()) {
			Intent intent = new Intent(SelectPackActivity.this, PackDetailsActivity.class);
			intent.putExtra(EXTRA_MESSAGE, selectPack);
			startActivity(intent);

		    } else {
			Intent intent = new Intent(SelectPackActivity.this, SelectQuizzActivity.class);
			intent.putExtra(EXTRA_MESSAGE, selectPack);
			startActivity(intent);

		    }

		}
	    });

	}
    }

}
