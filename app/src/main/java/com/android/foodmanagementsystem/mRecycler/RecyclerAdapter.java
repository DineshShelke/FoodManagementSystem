package com.android.foodmanagementsystem.mRecycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.foodmanagementsystem.R;
import com.android.foodmanagementsystem.mData.Item;
import java.util.ArrayList;

/**
 * Created by Techbuzz22 on 06-10-2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private ArrayList<Item> list = new ArrayList<>();
    private Context context;
    public RecyclerAdapter(Context context){
        this.context = context;
    }

    public void setData(ArrayList<Item> list){
        this.list=list;
        notifyItemRangeChanged(0, list.size());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_layout,parent,false);
        MyViewHolder my = new MyViewHolder(view);
        return my;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Item item = list.get(position);
        holder.name.setText("Item Name : "+item.getName());
        holder.higgintime.setText("Item Higgintime : "+item.getHiggintime());
        holder.itemprice.setText("Item Price : "+item.getPrice());

        /*holder.name.setText(list.get(position).getName());
        holder.higgintime.setText(list.get(position).getHiggintime());
        holder.itemprice.setText(list.get(position).getPrice());*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name,higgintime,itemprice;

        public MyViewHolder(View itemView) {
            super(itemView);

            name = (TextView)itemView.findViewById(R.id.name);
            higgintime = (TextView)itemView.findViewById(R.id.higginTime);
            itemprice = (TextView)itemView.findViewById(R.id.itemPrice);
        }
    }
}
