package com.android.foodmanagementsystem.mRecycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.foodmanagementsystem.R;
import com.android.foodmanagementsystem.mData.AcceptItem;
import com.android.foodmanagementsystem.mData.Item;
import com.android.foodmanagementsystem.mDetailAdmin.AdminAcceptFragment;

import java.util.ArrayList;

/**
 * Created by Techbuzz22 on 27-10-2017.
 */

public class PostAcceptAdapter extends RecyclerView.Adapter<PostAcceptAdapter.MyViewHolder> {

    private ArrayList<AcceptItem> list = new ArrayList<>();
    private Context context;
    public PostAcceptAdapter(Context context){
        this.context = context;
    }

    public void setData(ArrayList<AcceptItem> list){
        this.list=list;
        notifyItemRangeChanged(0, list.size());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_layout_accept,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AcceptItem item = list.get(position);
        holder.name.setText(item.getName());
        //holder.higgintime.setText("Item Higgintime : "+item.getHiggintime());
        holder.itemprice.setText(item.getPrice());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        //TextView name,higgintime,itemprice;
        TextView name,itemprice;
        public MyViewHolder(View itemView) {
            super(itemView);
            name = (TextView)itemView.findViewById(R.id.name);
            //higgintime = (TextView)itemView.findViewById(R.id.higginTime);
            itemprice = (TextView)itemView.findViewById(R.id.itemPrice);
        }
    }

}
