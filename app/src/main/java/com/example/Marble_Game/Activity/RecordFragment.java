package com.example.Marble_Game.Activity;

import android.content.ContentValues;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.Marble_Game.R;
import com.example.Marble_Game.User.UserAdapter;

public class RecordFragment extends Fragment implements AsyncTaskResult<ContentValues[]> {
    private RecyclerView listView;
    private TextView note;

    public RecordFragment() {
        // Required empty public constructor
    }


    public static RecordFragment newInstance() {
        return new RecordFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_record, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SearchDB SDB=new SearchDB(getActivity());
        SDB.ATR=this;
        note = (TextView)view.findViewById(R.id.note);
        note.setVisibility(View.INVISIBLE);
        listView=(RecyclerView)view.findViewById(R.id.UserList);
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));//这里用线性显示 类似于listview
        //        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));//这里用线性宫格显示 类似于grid view
        //        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));//这里用线性宫格显示 类似于瀑布流
        listView.setOverScrollMode(0);
        SDB.execute();
    }

    ContentValues[] result;

    @Override
    public void taskFinish(ContentValues[] result) {
        Message msg=new Message();
        listView.setAdapter(new UserAdapter(getActivity(),result));
        if(listView.getAdapter().getItemCount()==0){
            note.setVisibility(View.VISIBLE);
        }
    }

}
