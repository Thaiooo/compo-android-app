package com.compo.android.app;

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

import java.util.List;

public class SelectQuizzAdapter extends BaseAdapter {
    private static Typeface font;
    private static LayoutInflater inflater = null;
    private List<Quizz> quizzList;

    public SelectQuizzAdapter(Context c, List<Quizz> aQuizzList) {
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
            vi = inflater.inflate(R.layout.fragment_quizz, null);
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
        title.setText("Match " + (position + 1));

        TextView desc = (TextView) vi.findViewById(R.id.quizz_desc);
        desc.setTypeface(font);
        desc.setText(quizz.getName());

        if (home != null) {
            TextView scoreHome = (TextView) vi.findViewById(R.id.quizz_score_home);
            scoreHome.setTypeface(font);
            scoreHome.setText(home.getName() + " : " + quizz.getScoreHome());
        }

        if (away != null) {
            TextView scoreAway = (TextView) vi.findViewById(R.id.quizz_score_away);
            scoreAway.setTypeface(font);
            scoreAway.setText(away.getName() + " : " + quizz.getScoreAway());
        }

        return vi;
    }

}