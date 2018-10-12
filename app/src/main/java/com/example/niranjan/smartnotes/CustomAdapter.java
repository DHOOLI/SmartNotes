package com.example.niranjan.smartnotes;

/**
 * Created by niranjan on 10/4/18.
 */


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

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    CartHandler cartHandler;
   public ArrayList<DataModel> dataSet;
    Context mContext;
    public static class MyViewHolder extends RecyclerView.ViewHolder {

     public    TextView textViewSubject;
       public TextView textViewNotesTitle;
       public TextView textViewPrice;
      public   TextView addCart_button,view_button;


        public MyViewHolder(View itemView) {
            super(itemView);
           textViewSubject= (TextView) itemView.findViewById(R.id.subject);
           textViewNotesTitle = (TextView) itemView.findViewById(R.id.notes_title);
            textViewPrice = (TextView) itemView.findViewById(R.id.price);
            addCart_button=(TextView)itemView.findViewById(R.id.add_cart);
            view_button=(TextView)itemView.findViewById(R.id.view_notes);


        }
    }

    public CustomAdapter(Context context, ArrayList<DataModel> data) {

        mContext = context;
        this.dataSet = data;
    }

    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CustomAdapter.MyViewHolder holder, final int listPosition) {


        holder.textViewSubject.setText(dataSet.get(listPosition).getSubject_name());
        holder.textViewNotesTitle.setText(dataSet.get(listPosition).getNotes_title());
        holder.textViewPrice.setText(dataSet.get(listPosition).getPrice()+"Rs");

        holder.view_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = dataSet.get(listPosition).getLink();
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                v.getContext().startActivity(i);

            }
        });
        holder.addCart_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartHandler=new CartHandler(v.getContext());


                    cartHandler.addtocart(dataSet.get(listPosition));
                    holder.addCart_button.setText("Added to cart");
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}