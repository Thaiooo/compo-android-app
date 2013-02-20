package com.compo.android.app;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.compo.android.app.model.GamePack;

public class SelectGameAdapter extends BaseAdapter {
	private static LayoutInflater inflater = null;
	private List<GamePack> gamesPack;

	public SelectGameAdapter(Context c, List<GamePack> aGamesPack) {
		inflater = (LayoutInflater) c
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		gamesPack = aGamesPack;
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
		if (convertView == null) {
			vi = inflater.inflate(R.layout.game_element, null);
		}

		GamePack pack = gamesPack.get(position);

		TextView title = (TextView) vi.findViewById(R.id.game_name);
		title.setText(pack.getName());
		ImageView image = (ImageView) vi.findViewById(R.id.game_image);
		if (pack.isLock()) {
			image.setImageResource(R.drawable.game_pack_lock);
		} else {
			image.setImageResource(R.drawable.game_pack);
		}

		return vi;
	}

}