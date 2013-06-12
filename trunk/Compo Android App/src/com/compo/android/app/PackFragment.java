package com.compo.android.app;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.compo.android.app.model.Pack;

public class PackFragment extends Fragment {

    public static final String EXTRA_MESSAGE_ARG = "com.compo.android.app.PackFragment.MESSAGE.ARG";
    private static Typeface _font;
    private TextView _packName;
    private ImageView _packImage;
    private Pack _currentPack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pack, container, false);

        if (_font == null) {
            _font = Typeface.createFromAsset(getActivity().getAssets(), "MyLuckyPenny.ttf");
        }

        _packName = (TextView) rootView.findViewById(R.id.pack_name);
        _packImage = (ImageView) rootView.findViewById(R.id.pack_image_id);

        Bundle args = getArguments();
        _currentPack = (Pack) args.getSerializable(EXTRA_MESSAGE_ARG);
        _packName.setText(_currentPack.getName());
        _packName.setTypeface(_font);

        /*
        int id = getResources().getIdentifier(_currentTheme.getCode(), "drawable", getActivity().getPackageName());
        if (id != 0) {
            _packImage.setImageResource(id);
        } else {
            _packImage.setImageResource(R.drawable.world_cup);
        }
        */

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getActivity(), SelectPackActivity.class);
                //intent.putExtra(EXTRA_MESSAGE_ARG, _currentTheme);
                //startActivity(intent);
                //getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }
        );


        return rootView;
    }

}