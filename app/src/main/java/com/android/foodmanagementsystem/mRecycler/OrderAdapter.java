package com.android.foodmanagementsystem.mRecycler;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.foodmanagementsystem.R;
import com.android.foodmanagementsystem.mData.AcceptItem;
import com.android.foodmanagementsystem.mDetailDeleveryBoy.DetailsDeleveryBoyFragment;
import com.android.foodmanagementsystem.mDetailUser.DetailUserFragment;

import java.util.ArrayList;

/**
 * Created by Techbuzz22 on 06-11-2017.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    private ArrayList<AcceptItem> list = new ArrayList<>();
    private Context context;
    public OrderAdapter(Context context) {
        this.context = context;
    }
    private SharedPreferences preferences;

    public void setData(ArrayList<AcceptItem> list) {
        this.list = list;
        notifyItemRangeChanged(0, list.size());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_order_user_layout, parent, false);
        return new MyViewHolder(view,context,list);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.name.setText(list.get(position).getName());
        holder.higgintime.setText(list.get(position).getHiggintime());
        holder.itemprice.setText(list.get(position).getPrice());
        holder.itemquantity.setText(list.get(position).getItemquantity());
        holder.itemtotalprice.setText(list.get(position).getTotoalitemprice());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ArrayList<AcceptItem> list;
        Context context;
       // TextView name,itemprice;
        TextView name,itemprice,itemquantity,itemtotalprice,higgintime;
        public MyViewHolder(View itemView,Context context,ArrayList<AcceptItem> list) {
            super(itemView);
            this.context = context;
            this.list = list;
            itemView.setOnClickListener(this);
            name = (TextView)itemView.findViewById(R.id.name);
            higgintime = (TextView)itemView.findViewById(R.id.higginTime);
            itemprice = (TextView)itemView.findViewById(R.id.itemPrice);
            itemquantity = (TextView)itemView.findViewById(R.id.itemQuantity);
            itemtotalprice = (TextView)itemView.findViewById(R.id.itemTotalPrice);
        }

        @Override
        public void onClick(View v) {

            FragmentManager FM;
            FragmentTransaction FT;
            DetailUserFragment detailUserFragment = new DetailUserFragment();
            Bundle bundle = new Bundle();
            bundle.putString("id", list.get(getAdapterPosition()).getId());
            bundle.putString("order_id",list.get(getAdapterPosition()).getOrderId());
            bundle.putString("Name", list.get(getAdapterPosition()).getName());
            bundle.putString("Item_Price", list.get(getAdapterPosition()).getPrice());
            bundle.putString("total", list.get(getAdapterPosition()).getTotoalitemprice());

            bundle.putString("User_name", list.get(getAdapterPosition()).getUserName());
            bundle.putString("User_email", list.get(getAdapterPosition()).getUserEmail());
            bundle.putString("User_contact", list.get(getAdapterPosition()).getUserContact());
            bundle.putString("User_address", list.get(getAdapterPosition()).getUserAddress());

            detailUserFragment.setArguments(bundle);
            FM = ((FragmentActivity) context).getSupportFragmentManager();
            FT = FM.beginTransaction();
            FT.replace(R.id.containerViewUser, detailUserFragment)
                    .commit();

        }
    }
}
