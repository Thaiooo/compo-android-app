package com.compo.android.app;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.compo.android.app.model.Match;
import com.compo.android.app.model.Play;
import com.compo.android.app.model.QuizzPlayer;
import com.compo.android.app.model.Team;
import com.compo.android.app.utils.FontEnum;

public class SelectMatchAdapter extends BaseAdapter {

    private static Typeface _fontMatchName;
    private static Typeface _fontMatchDesc;
    private static Typeface _fontMatchScore;

    private static LayoutInflater _inflater = null;
    private List<Match> _matchs;
    private Map<Long, Play> _mapQuizzToPlay;
    private LinearLayout _ballListLayout;

    private final static String MATCH_NAME_PREFIX = "Match";

    public SelectMatchAdapter(Context c, List<Match> aMatchs, Map<Long, Play> aMapQuizzToPlay) {
	_inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	_matchs = aMatchs;
	_mapQuizzToPlay = aMapQuizzToPlay;

	if (_fontMatchName == null) {
	    _fontMatchName = Typeface.createFromAsset(c.getAssets(), FontEnum.MATCH_NAME.getName());
	}
	if (_fontMatchDesc == null) {
	    _fontMatchDesc = Typeface.createFromAsset(c.getAssets(), FontEnum.MATCH_DESC.getName());
	}
	if (_fontMatchScore == null) {
	    _fontMatchScore = Typeface.createFromAsset(c.getAssets(), FontEnum.MATCH_SCORE.getName());
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
	int nbQuizz = 0;
	int nbResponse = 0;
	for (QuizzPlayer qp : match.getQuizzs()) {
	    if (qp.isHide()) {
		nbQuizz++;
	    }
	    Play play = _mapQuizzToPlay.get(qp.getId());
	    if (play != null) {
		if (StringUtils.equalsIgnoreCase(qp.getPlayer().getName(), play.getResponse())) {
		    nbResponse++;
		}
	    }
	    if (qp.isHome()) {
		home = qp.getTeam();
	    } else {
		away = qp.getTeam();
	    }
	}

	TextView title = (TextView) vi.findViewById(R.id.match_title);
	title.setTypeface(_fontMatchName);
	title.setText(MATCH_NAME_PREFIX + (position + 1));

	TextView desc = (TextView) vi.findViewById(R.id.match_desc);
	desc.setTypeface(_fontMatchDesc);
	desc.setText(match.getName());

	TextView scoreHome = (TextView) vi.findViewById(R.id.match_score_home);
	if (home != null) {
	    scoreHome.setTypeface(_fontMatchScore);
	    scoreHome.setText(home.getName() + " : " + match.getScoreHome());
	} else {
	    scoreHome.setText("");
	}

	TextView scoreAway = (TextView) vi.findViewById(R.id.match_score_away);
	if (away != null) {
	    scoreAway.setTypeface(_fontMatchScore);
	    scoreAway.setText(away.getName() + " : " + match.getScoreAway());
	} else {
	    scoreAway.setText("");
	}

	_ballListLayout = (LinearLayout) vi.findViewById(R.id.ball_list_layout);
	_ballListLayout.removeAllViews();
	for (int i = 0; i < nbQuizz; i++) {
	    ImageView ball = new ImageView(_ballListLayout.getContext());
	    if (nbResponse > i) {
		ball.setImageResource(R.drawable.ball_unlock);
	    } else {
		ball.setImageResource(R.drawable.ball_lock);
	    }
	    _ballListLayout.addView(ball);
	}

	return vi;
    }

    public void setMapQuizzToPlay(Map<Long, Play> _mapQuizzToPlay) {
	this._mapQuizzToPlay = _mapQuizzToPlay;
    }

}