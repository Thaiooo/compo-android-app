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
import com.compo.android.app.model.QuizzPlayer;
import com.compo.android.app.model.Team;

public class SelectThemeAdapter extends BaseAdapter {
    private static Typeface font;
    private static LayoutInflater inflater = null;
    private List<Quizz> quizzList;

    public SelectThemeAdapter(Context c, List<Quizz> aQuizzList) {
	inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

	Team home = null;
	Team away = null;
	for (QuizzPlayer qp : quizz.getQuizzList()) {
	    if (home != null && away != null) {
		break;
	    }
	    if (qp.isHome()) {
		home = qp.getTeam();
	    } else {
		away = qp.getTeam();
	    }
	}

	TextView title = (TextView) vi.findViewById(R.id.quizz_title);
	title.setTypeface(font);
	if (home != null && away != null) {
	    title.setText(home.getName() + " - " + away.getName());
	}
	
	TextView desc = (TextView) vi.findViewById(R.id.quizz_desc);
	desc.setTypeface(font);
	desc.setText(quizz.getName());

	TextView score = (TextView) vi.findViewById(R.id.quizz_score);
	score.setTypeface(font);
	score.setText(quizz.getScoreHome() + " - " + quizz.getScoreAway());

	// ImageView image = (ImageView) vi.findViewById(R.id.quizz_image);
	// if (quizz.isSuccess()) {
	// image.setImageResource(R.drawable.theme1_background_pack);
	// } else {
	// image.setImageResource(R.drawable.quizz);
	// }

	return vi;
    }

}