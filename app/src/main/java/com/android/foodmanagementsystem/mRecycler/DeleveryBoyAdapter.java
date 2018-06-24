package com.android.foodmanagementsystem.mRecycler;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.foodmanagementsystem.MainActivity;
import com.android.foodmanagementsystem.R;
import com.android.foodmanagementsystem.mData.AcceptItem;
import com.android.foodmanagementsystem.mDetailDeleveryBoy.BarcodeFragment;
import com.android.foodmanagementsystem.mDetailDeleveryBoy.BoyDraftskFragment;
import com.android.foodmanagementsystem.mDetailDeleveryBoy.BoySentFragment;
import com.android.foodmanagementsystem.mDetailDeleveryBoy.DetailsDeleveryBoyFragment;

import java.util.ArrayList;

/**
 * Created by Techbuzz22 on 06-11-2017.
 */

public class DeleveryBoyAdapter extends RecyclerView.Adapter<DeleveryBoyAdapter.MyViewHolder> {

    private ArrayList<AcceptItem> list = new ArrayList<>();
    private Context context;
    public DeleveryBoyAdapter(Context context) {
        this.context = context;
    }

    public void setData(ArrayList<AcceptItem> list) {
        this.list = list;
        notifyItemRangeChanged(0, list.size());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_order_layout, parent, false);
        return new MyViewHolder(view,context,list);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.name.setText(list.get(position).getName());
        holder.higgintime.setText(list.get(position).getHiggintime());
        holder.itemprice.setText(list.get(position).getPrice());
        holder.quantity.setText(list.get(position).getItemquantity());
        holder.totalPrice.setText(list.get(position).getTotoalitemprice());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        Button button;
        TextView name,higgintime,itemprice,quantity,totalPrice;
        Context context;
        ArrayList<AcceptItem> list;

        public MyViewHolder(View itemView, Context context,ArrayList<AcceptItem> list) {
            super(itemView);

            this.context = context;
            this.list = list;
            itemView.setOnClickListener(this);
            name = (TextView)itemView.findViewById(R.id.name);
            higgintime = (TextView)itemView.findViewById(R.id.higginTime);
            itemprice = (TextView)itemView.findViewById(R.id.itemPrice);
            quantity = (TextView)itemView.findViewById(R.id.itemQuantity);
            totalPrice = (TextView)itemView.findViewById(R.id.itemTotalPrice);
            button = (Button)itemView.findViewById(R.id.btnPost);
        }

        @Override
        public void onClick(View v) {
            FragmentManager FM;
            FragmentTransaction FT;
            DetailsDeleveryBoyFragment detailsDeleveryBoyFragment = new DetailsDeleveryBoyFragment();
            Bundle bundle = new Bundle();
            bundle.putString("name", list.get(getAdapterPosition()).getName());
            bundle.putString("higgintime", list.get(getAdapterPosition()).getHiggintime());
            bundle.putString("itemprice", list.get(getAdapterPosition()).getPrice());

            bundle.putString("User_name", list.get(getAdapterPosition()).getUserName());
            bundle.putString("User_email", list.get(getAdapterPosition()).getUserEmail());
            bundle.putString("User_contact", list.get(getAdapterPosition()).getUserContact());
            bundle.putString("User_address", list.get(getAdapterPosition()).getUserAddress());
            bundle.putString("User_role", list.get(getAdapterPosition()).getUserRole());
            bundle.putString("User_password", list.get(getAdapterPosition()).getUserPassword());

            detailsDeleveryBoyFragment.setArguments(bundle);
            FM = ((FragmentActivity) context).getSupportFragmentManager();
            FT = FM.beginTransaction();
            FT.replace(R.id.containerDeleveryBoy, detailsDeleveryBoyFragment).commit();

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager FM;
                    FragmentTransaction FT;
                    BarcodeFragment barcodeFragment = new BarcodeFragment();
                    FM = ((FragmentActivity) context).getSupportFragmentManager();
                    FT = FM.beginTransaction();
                    FT.replace(R.id.containerDeleveryBoy, barcodeFragment)
                            .commit();
                }
            });

        }
    }
}