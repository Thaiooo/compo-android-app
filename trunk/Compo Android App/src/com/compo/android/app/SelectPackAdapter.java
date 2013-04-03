package com.compo.android.app;

import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.compo.android.app.model.Pack;

public class SelectPackAdapter extends BaseAdapter {
    private static Typeface _font;

    private static LayoutInflater _inflater = null;
    private List<Pack> _gamesPack;

    public SelectPackAdapter(Context c, List<Pack> aGamesPack) {
	_inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	_gamesPack = aGamesPack;

	if (_font == null) {
	    _font = Typeface.createFromAsset(c.getAssets(), "MyLuckyPenny.ttf");
	}
    }

    public int getCount() {
	return _gamesPack.size();
    }

    public Object getItem(int position) {
	return null;
    }

    public long getItemId(int position) {
	return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
	View vi = convertView;
	if (convertView == null) {
	    vi = _inflater.inflate(R.layout.pack_element, null);
	}

	Pack pack = _gamesPack.get(position);

	TextView name = (TextView) vi.findViewById(R.id.pack_name);
	name.setTypeface(_font);
	name.setText(pack.getName());

	TextView desciption = (TextView) vi.findViewById(R.id.pack_description);
	desciption.setTypeface(_font);
	desciption.setText(pack.getDescription());

	TextView score = (TextView) vi.findViewById(R.id.pack_score);
	ImageView image = (ImageView) vi.findViewById(R.id.image_coins);
	TextView scoreLimit = (TextView) vi.findViewById(R.id.pack_score_cost);
	TextView creditLimit = (TextView) vi.findViewById(R.id.pack_credit_cost);
	if (!pack.isLock()) {
	    vi.setBackgroundResource(R.drawable.post_it);

	    score.setVisibility(View.VISIBLE);
	    score.setTypeface(_font);
	    score.setText("0/20");

	    image.setVisibility(View.INVISIBLE);

	    scoreLimit.setVisibility(View.INVISIBLE);

	    creditLimit.setVisibility(View.INVISIBLE);

	} else {
	    vi.setBackgroundResource(R.drawable.post_it_red);
	    score.setVisibility(View.INVISIBLE);

	    image.setVisibility(View.VISIBLE);

	    scoreLimit.setVisibility(View.VISIBLE);
	    scoreLimit.setTypeface(_font);
	    scoreLimit.setText(pack.getScoreLimit() + " pts required");

	    creditLimit.setVisibility(View.VISIBLE);
	    creditLimit.setTypeface(_font);
	    creditLimit.setText(pack.getCreditLimit() + "");
	}

	return vi;
    }

}