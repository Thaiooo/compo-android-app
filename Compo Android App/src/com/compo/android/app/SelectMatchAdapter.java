package com.compo.android.app;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.compo.android.app.model.Match;
import com.compo.android.app.model.QuizzPlayer;
import com.compo.android.app.model.Team;

import java.util.List;

public class SelectMatchAdapter extends BaseAdapter {
    private static Typeface _font;
    private static LayoutInflater _inflater = null;
    private List<Match> _matchs;

    public SelectMatchAdapter(Context c, List<Match> aMatchs) {
	_inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	_matchs = aMatchs;

	if (_font == null) {
	    _font = Typeface.createFromAsset(c.getAssets(), "MyLuckyPenny.ttf");
	}
    }

    public int getCount() {
	return _matchs.size();
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
	    vi = _inflater.inflate(R.layout.fragment_match, null);
	}

	Match match = _matchs.get(position);

	Team home = null;
	Team away = null;
	for (QuizzPlayer qp : match.getQuizzs()) {
	    if (home != null && away != null) {
		break;
	    }
	    if (qp.isHome()) {
		home = qp.getTeam();
	    } else {
		away = qp.getTeam();
	    }
	}

	TextView title = (TextView) vi.findViewById(R.id.match_title);
	title.setTypeface(_font);
	title.setText("Match " + (position + 1));

	TextView desc = (TextView) vi.findViewById(R.id.match_desc);
	desc.setTypeface(_font);
	desc.setText(match.getName());

	if (home != null) {
	    TextView scoreHome = (TextView) vi.findViewById(R.id.match_score_home);
	    scoreHome.setTypeface(_font);
	    scoreHome.setText(home.getName() + " : " + match.getScoreHome());
	}

	if (away != null) {
	    TextView scoreAway = (TextView) vi.findViewById(R.id.match_score_away);
	    scoreAway.setTypeface(_font);
	    scoreAway.setText(away.getName() + " : " + match.getScoreAway());
	}

	return vi;
    }

}