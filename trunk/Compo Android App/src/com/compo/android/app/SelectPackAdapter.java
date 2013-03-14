package com.compo.android.app;

import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.compo.android.app.model.Pack;

public class SelectPackAdapter extends BaseAdapter {
	private static Typeface font;

	private static LayoutInflater inflater = null;
	private List<Pack> gamesPack;

	public SelectPackAdapter(Context c, List<Pack> aGamesPack) {
		inflater = (LayoutInflater) c
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		gamesPack = aGamesPack;

		if (font == null) {
			font = Typeface.createFromAsset(c.getAssets(), "MyLuckyPenny.ttf");
		}
	}

	public int getCount() {
		return gamesPack.size();
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		System.out.println("=====>" + convertView);
		if (convertView == null) {
			vi = inflater.inflate(R.layout.game_element, null);
		}

		Pack pack = gamesPack.get(position);

		TextView title = (TextView) vi.findViewById(R.id.game_name);
		title.setTypeface(font);
		title.setText(pack.getName());
		// ImageView image = (ImageView) vi.findViewById(R.id.game_image);
		// if (!pack.isLock()) {
		// image.setVisibility(View.INVISIBLE);
		// }
		return vi;
	}

}