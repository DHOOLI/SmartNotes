package com.example.niranjan.smartnotes;

/**
 * Created by niranjan on 11/4/18.
 */

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class CustomAdapter2 extends RecyclerView.Adapter<CustomAdapter2.MyViewHolder> {
    CartHandler cartHandler;
    public ArrayList<DataModel> dataSet;
    Context mContext;
    boolean flag=false;
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewSubject;
        public TextView textViewNotesTitle;
        public TextView textViewPrice;
        public Button remove_button;


        public MyViewHolder(View itemView) {
            super(itemView);
            textViewSubject= (TextView) itemView.findViewById(R.id.cart_subject);
            textViewNotesTitle = (TextView) itemView.findViewById(R.id.cart_notes_title);
            textViewPrice = (TextView) itemView.findViewById(R.id.cart_price);
            remove_button=(Button)itemView.findViewById(R.id.remove);



        }
    }

    public CustomAdapter2(Context context, ArrayList<DataModel> data) {

        mContext = context;
        this.dataSet = data;
    }

    @Override
    public CustomAdapter2.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_list_item, parent, false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CustomAdapter2.MyViewHolder holder, final int listPosition) {


        holder.textViewSubject.setText("SUBJECT : "+dataSet.get(listPosition).getSubject_name());
        holder.textViewNotesTitle.setText("TITLE : "+dataSet.get(listPosition).getNotes_title());
        holder.textViewPrice.setText("PRICE : "+dataSet.get(listPosition).getPrice()+"Rs");


        holder.remove_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                cartHandler=new CartHandler(v.getContext());
                if(flag==false) {
                    cartHandler.deleteorder(dataSet.get(listPosition));
                    holder.remove_button.setText("Add back to cart");
                    flag=true;
                    Toast.makeText(mContext,"Removed From cart",Toast.LENGTH_LONG).show();
                }
                else if (flag==true)
                {
                    cartHandler.addtocart(dataSet.get(listPosition));
                    holder.remove_button.setText("Remove from Cart");
                    Toast.makeText(mContext,"Added back to cart",Toast.LENGTH_LONG).show();
                    flag=false;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}