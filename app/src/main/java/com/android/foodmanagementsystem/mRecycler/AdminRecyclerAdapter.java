package com.android.foodmanagementsystem.mRecycler;

import android.content.Context;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.foodmanagementsystem.MainActivity;
import com.android.foodmanagementsystem.R;
import com.android.foodmanagementsystem.mData.Item;
import com.android.foodmanagementsystem.mDetailAdmin.AdminAcceptFragment;

import java.util.ArrayList;

/**
 * Created by Techbuzz22 on 06-10-2017.
 */

public class AdminRecyclerAdapter extends RecyclerView.Adapter<AdminRecyclerAdapter.MyViewHolder> {

    private ArrayList<Item> list = new ArrayList<>();
    AdminAcceptFragment fragment;
    String Status = "0";

    public AdminRecyclerAdapter(AdminAcceptFragment adminAcceptFragment){
        this.fragment = adminAcceptFragment;
    }

    public void setData(ArrayList<Item> list){
        this.list=list;
        notifyItemRangeChanged(0, list.size());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_row,parent,false);
        MyViewHolder my = new MyViewHolder(view, fragment,list);
        return my;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        Item item = list.get(position);
        holder.name.setText(item.getName());
        //holder.higgintime.setText("Item Higgintime : "+item.getHiggintime());
        holder.itemprice.setText(item.getPrice());

        /*holder.name.setText(list.get(position).getName());
        holder.higgintime.setText(list.get(position).getHiggintime());
        holder.itemprice.setText(list.get(position).getPrice());*/

        if (Status.equals(list.get(position).getStatus())){
            holder.btnPost.setText("Post");
        }
        else {
            holder.btnPost.setText("Accept");
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ArrayList<Item> list;
        //TextView name,higgintime,itemprice;
        TextView name,itemprice;
        Button btnPost;
        AdminAcceptFragment fragment;

        public MyViewHolder(View itemView, AdminAcceptFragment adminAcceptFragment, ArrayList<Item> list) {
            super(itemView);
            this.fragment = adminAcceptFragment;
            this.list=list;
            name = (TextView)itemView.findViewById(R.id.name);
            //higgintime = (TextView)itemView.findViewById(R.id.higginTime);
            itemprice = (TextView)itemView.findViewById(R.id.itemPrice);
            btnPost = (Button)itemView.findViewById(R.id.btnPost);
            btnPost.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String id;
            String name;
            String qty;
            String price;
            String higgintime;
            String date;

            int pos = getAdapterPosition();

            if (pos != RecyclerView.NO_POSITION){
                id = list.get(pos).getId();
                name = list.get(pos).getName();
                qty = list.get(pos).getQty();
                price = list.get(pos).getPrice();
                higgintime = list.get(pos).getHiggintime();
                date = list.get(pos).getDate();
                fragment.prepareSelection(v,id,name,qty,price,higgintime,date);
            }
        }
    }
}
