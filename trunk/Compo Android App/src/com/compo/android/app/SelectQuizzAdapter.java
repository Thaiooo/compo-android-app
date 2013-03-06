package com.compo.android.app;

import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.compo.android.app.model.Quizz;

public class SelectQuizzAdapter extends BaseAdapter {
	private static Typeface font;
	private static LayoutInflater inflater = null;
	private List<Quizz> quizzList;

	public SelectQuizzAdapter(Context c, List<Quizz> aQuizzList) {
		inflater = (LayoutInflater) c
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		quizzList = aQuizzList;
		
		if (font == null) {
			font = Typeface.createFromAsset(c.getAssets(), "MyLuckyPenny.ttf");
		}
	}

	public int getCount() {
		return quizzList.size();
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
			vi = inflater.inflate(R.layout.quizz_element, null);
		}

		Quizz quizz = quizzList.get(position);

		TextView title = (TextView) vi.findViewById(R.id.quizz_name);
		title.setTypeface(font);
		title.setText(quizz.getName());
//		ImageView image = (ImageView) vi.findViewById(R.id.quizz_image);
//		if (quizz.isSuccess()) {
//			image.setImageResource(R.drawable.theme1_background_pack);
//		} else {
//			image.setImageResource(R.drawable.quizz);
//		}

		return vi;
	}

}