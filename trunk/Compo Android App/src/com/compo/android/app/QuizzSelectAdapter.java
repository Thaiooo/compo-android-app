package com.compo.android.app;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.compo.android.app.model.Quizz;

public class QuizzSelectAdapter extends BaseAdapter {
	private static LayoutInflater inflater = null;
	private List<Quizz> quizzList;

	public QuizzSelectAdapter(Context c, List<Quizz> aQuizzList) {
		inflater = (LayoutInflater) c
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		quizzList = aQuizzList;
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
		title.setText(quizz.getName());
		ImageView image = (ImageView) vi.findViewById(R.id.quizz_image);
		if (quizz.isSuccess()) {
			image.setImageResource(R.drawable.quizz_unlock);
		} else {
			image.setImageResource(R.drawable.quizz);
		}

		return vi;
	}

}