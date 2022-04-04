package com.example.Marble_Game;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.Marble_Game.Activity.MainGamePage;

public class StageFragment extends Fragment {
    public StageFragment() {
        // Required empty public constructor
    }

    public static StageFragment newInstance() {
        return new StageFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stage, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView listView = (RecyclerView)view.findViewById(R.id.LevelStage);
        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        listView.setOverScrollMode(0);
        listView.setAdapter(new RView());
    }


    private class RView extends RecyclerView.Adapter<RView.StageHolder>{
        private final int stgCount = 3;
        private final int[] stgTitle = new int[]{
                R.drawable.stglv_ea,R.drawable.stglv_nor,R.drawable.stglv_ha
        };

        @Override
        public StageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new StageHolder(LayoutInflater.from(getActivity()).inflate(R.layout.stage_card_board,parent,false));
        }

        @Override
        public void onBindViewHolder(StageHolder holder, int position) {
            holder.title.setImageResource(stgTitle[position]);
            holder.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), MainGamePage.class);
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return stgCount;
        }

        class StageHolder extends RecyclerView.ViewHolder{
            private ImageView title;
            StageHolder(View itemView) {
                super(itemView);
                title = (ImageView) itemView.findViewById(R.id.mRootView);
            }
        }
    }
}
