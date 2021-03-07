package com.example.smartrefrigerator;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {
    ArrayList<notificationAttr> notificationAttrs;
    Activity context;

    public NotificationAdapter(ArrayList<notificationAttr> notificationAttrs, Activity context) {
        this.context = context;
        this.notificationAttrs = notificationAttrs;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.desc.setText(notificationAttrs.get(position).getDescription());
        holder.datetime.setText(notificationAttrs.get(position).getDatetime());
        holder.title.setText(notificationAttrs.get(position).getTitle());

        holder.itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String title = notificationAttrs.get(position).getTitle();
               // final String longitude = addServiceAttrs.get(position).getLon();
                if (title.equals("Please Refill Fruit Box")) {
                    Intent intent = new Intent(context, FruitActivity.class);
                    context.startActivity(intent);
                }else if(title.equals("Please Refill Egg Box")){
                    Intent intent = new Intent(context, EggsActivity.class);
                    context.startActivity(intent);
                }else {
                    Intent intent = new Intent(context, VegetableActivity.class);
                    context.startActivity(intent);
                }
            }
        } );

    }

    @Override
    public int getItemCount() {
        return notificationAttrs.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView desc,datetime,title;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.notification_title);
            desc = (TextView) itemView.findViewById(R.id.notification_description);
            datetime=(TextView) itemView.findViewById( R.id.notification_datetime );
        }
    }

}
