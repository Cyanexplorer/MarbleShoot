package com.example.Marble_Game;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.Marble_Game.Activity.RecordFragment;

import static com.example.Marble_Game.util.Constants.RDFRG;
import static com.example.Marble_Game.util.Constants.STGFRG;
import static com.example.Marble_Game.util.Constants.STRFRG;


public class StartPageFragment extends Fragment {

    private TextView StartBtn, RecordBtn, ExitBtn;
    public StartPageFragment() {
        // Required empty public constructor
    }


    public static StartPageFragment newInstance() {
        return new StartPageFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start_page, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        StartBtn = (TextView) view.findViewById(R.id.StartButton);
        RecordBtn = (TextView) view.findViewById(R.id.Record);
        ExitBtn = (TextView)view.findViewById(R.id.ExitButton);

        StartBtn.setOnClickListener(click);
        RecordBtn.setOnClickListener(click);
        ExitBtn.setOnClickListener(click);
    }

    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.StartButton:
                    setFragment(StartPageFragment.this.getId(), StageFragment.newInstance(),STGFRG);
                    break;
                case R.id.Record:
                    setFragment(StartPageFragment.this.getId(), RecordFragment.newInstance(),RDFRG);
                    break;
                case R.id.ExitButton:
                    getActivity().finish();
                    break;
                default:break;
            }
        }
    };

    private void setFragment(int id, Fragment fragment, String tag){
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction().addToBackStack(STRFRG).replace(id,fragment,tag).commit();
    }

}
