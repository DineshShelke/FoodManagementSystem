package com.android.foodmanagementsystem.mRecycler;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.foodmanagementsystem.R;
import com.android.foodmanagementsystem.mData.AcceptItem;
import com.android.foodmanagementsystem.mDetailAdmin.AdminAcceptFragment;
import com.android.foodmanagementsystem.mDetailUser.AllPostFragment;
import com.android.foodmanagementsystem.mDetailUser.DetailUserFragment;

import java.util.ArrayList;

/**
 * Created by Techbuzz22 on 27-10-2017.
 */

public class UserPostAdapter extends RecyclerView.Adapter<UserPostAdapter.MyViewHolder> {

    private ArrayList<AcceptItem> list = new ArrayList<>();
    AllPostFragment fragment;
    String Status = "0";
    private static int countItem = 0;

    public UserPostAdapter(AllPostFragment allPostFragment) {
        this.fragment = allPostFragment;
    }

    public void setData(ArrayList<AcceptItem> list) {
        this.list = list;
        notifyItemRangeChanged(0, list.size());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user_layout, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view, fragment,list);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AcceptItem item = list.get(position);
        holder.name.setText(item.getName());
        holder.itemprice.setText(item.getPrice());

      /* if (Status.equals(list.get(position).getStatus())) {
            holder.btnPost.setText("Order");
        } else {
            holder.btnPost.setText("You Order");
        }*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

        static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        //TextView name,higgintime,itemprice;

        private ImageButton imageButtonAdd,imageButtonSub;
        TextView name,itemprice;
        Button btnPost;
        ArrayList<AcceptItem> list;
        AllPostFragment fragment;
        TextView tvCounter;

        public MyViewHolder(final View itemView, AllPostFragment fragment, ArrayList<AcceptItem> list) {
            super(itemView);
            this.fragment=fragment;
            this.list=list;

            //tvCounter = (TextView) itemView.findViewById(R.id.itemQuantity);

            imageButtonAdd = (ImageButton) itemView.findViewById(R.id.itemaddition);
            imageButtonSub = (ImageButton) itemView.findViewById(R.id.itemsubstraction);

            name = (TextView)itemView.findViewById(R.id.name);
            //higgintime = (TextView)itemView.findViewById(R.id.higginTime);
            itemprice = (TextView)itemView.findViewById(R.id.itemPrice);
            btnPost = (Button)itemView.findViewById(R.id.btnPost);
            btnPost.setOnClickListener(this);

            imageButtonAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    countItem++;
                    ((TextView) itemView.findViewById(R.id.itemQuantity)).setText(String.valueOf(countItem));
                }
            });
            imageButtonSub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    countItem--;
                    ((TextView) itemView.findViewById(R.id.itemQuantity)).setText(String.valueOf(countItem));
                }
            });

        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();

            String id;
            String name;
            String qty;
            String price;
            String higgintime;
            String date;
            String orderid;
            String total_item_price;
            String user_name;
            String user_email;
            String user_contact;
            String user_address;

           /* switch (v.getId()){
                case R.id.itemaddition:
                    countItem++;
                    break;
                case R.id.itemsubstraction:
                    countItem--;
                    break;
            }*/

            if (pos != RecyclerView.NO_POSITION){
                id = list.get(pos).getId();
                name = list.get(pos).getName();
                qty = list.get(pos).getQty();
                price = list.get(pos).getPrice();
                higgintime = list.get(pos).getHiggintime();
                date = list.get(pos).getDate();
                orderid = list.get(pos).getOrderId();

                fragment.prepareSelection(v,id,name,qty,price,higgintime,date,countItem);
            }
        }
    }
}
