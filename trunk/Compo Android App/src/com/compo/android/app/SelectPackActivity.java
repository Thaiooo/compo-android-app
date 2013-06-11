package com.compo.android.app;

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
import com.compo.android.app.model.Pack;
import com.compo.android.app.model.User;
import com.compo.android.app.utils.UserFactory;

import java.util.List;

public class SelectPackActivity extends Activity {

    public final static String MESSAGE_SELECTED_PACK = "com.compo.android.app.SelectGameActivity.MESSAGE1";

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

    private class LoadUserTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            User u = UserFactory.getInstance().getUser();
            _userCredit.setText(u.getCredit() + "");
            _userPoint.setText(u.getPoint() + " pts");
            return null;
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
                    Intent intent = new Intent(SelectPackActivity.this, PackDetailsActivity.class);
                    intent.putExtra(MESSAGE_SELECTED_PACK, selectPack);
                    startActivity(intent);
                }
            });

        }
    }

}
