package com.example.Marble_Game.User;

import android.content.ContentValues;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.Marble_Game.Activity.AsyncTaskResult;
import com.example.Marble_Game.R;

/**
 * Created by User on 2017/1/5.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.viewholder> {

    public static final int TYPE_HEADER = 0;  //说明是带有Header的
    public static final int TYPE_FOOTER = 1;  //说明是带有Footer的
    public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的
    private final ContentValues[] values;
    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    //HeaderView, FooterView
    private View mHeaderView;
    private View mFooterView;

    public static enum ITEM_TYPE {
        ITEM_TYPE_IMAGE,
        ITEM_TYPE_TEXT
    }

    public UserAdapter(Context context, ContentValues[] values) {
        this.values=values;
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public class viewholder extends RecyclerView.ViewHolder{

        public TextView PlayerName;
        public TextView Score;
        public TextView Date;

        public viewholder(View itemView) {
            super(itemView);
            if (itemView == mHeaderView){
                return;
            }
            if (itemView == mFooterView){
                return;
            }
            PlayerName = (TextView)itemView.findViewById(R.id.PlayerName);
            Score=(TextView)itemView.findViewById(R.id.Score);
            Date=(TextView)itemView.findViewById(R.id.Date);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null && mFooterView == null){
            return TYPE_NORMAL;
        }
        else if (mHeaderView!=null && position == 0){
            //第一个item应该加载Header
            return TYPE_HEADER;
        }
        else if (mFooterView !=null && position == getItemCount()-1){
            //最后一个,应该加载Footer
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;
    }

    //HeaderView和FooterView的get和set函数
    public View getHeaderView() {
        return mHeaderView;
    }
    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getFooterView() {
        return mFooterView;
    }
    public void setFooterView(View footerView) {
        mFooterView = footerView;
        notifyItemInserted(getItemCount()-1);
    }

    @Override
    public UserAdapter.viewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mHeaderView != null && viewType == TYPE_HEADER) {
            return new viewholder(mHeaderView);
        }
        if(mFooterView != null && viewType == TYPE_FOOTER){
            return new viewholder(mFooterView);
        }
        Context ct = parent.getContext();
        View v=mLayoutInflater.from(ct).inflate(R.layout.activity_record_loader,parent,false);
        viewholder vh= new viewholder(v) ;
        return vh;
    }

    private ContentValues MateUser;
    public class AsynFinish implements AsyncTaskResult<ContentValues> {
        @Override
        public void taskFinish(ContentValues result) {
            MateUser=result;
        }
    }

    @Override
    public void onBindViewHolder(viewholder holder, int position) {
        if(getItemViewType(position) == TYPE_NORMAL){
            if(holder!=null) {
                //这里加载数据的时候要注意，是从position-1开始，因为position==0已经被header占用了
                int index=position;
                Log.i("Index",Integer.toString(position));
                holder.PlayerName.setText("PlayerName: "+values[index].getAsString("PlayerName"));
                holder.Score.setText("Score:"+values[index].getAsString("Score"));
                holder.Date.setText(values[index].getAsString("_id")+"."+values[index].getAsString("Time"));
            }
        }
        else if(getItemViewType(position) == TYPE_HEADER){}
        else{}
        //TextView UserName=holder.UserName;UserName.setText(values[position].getAsString("UserName"));
    }

    //返回View中Item的个数，这个时候，总的个数应该是ListView中Item的个数加上HeaderView和FooterView
    @Override
    public int getItemCount() {
        if(mHeaderView == null && mFooterView == null){
            return values.length;
        }else if(mHeaderView == null && mFooterView != null){
            return values.length + 1;
        }else if (mHeaderView != null && mFooterView == null){
            return values.length + 1;
        }else {
            return values.length + 2;
        }
    }


}
