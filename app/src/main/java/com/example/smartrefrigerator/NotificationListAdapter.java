package com.example.smartrefrigerator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.ViewHolder> {
    ArrayList<notificationAttr> notificationAttrs;
    private Context context;
    Activity activity;

    public NotificationListAdapter(ArrayList<notificationAttr> notificationAttrs, Context context , Activity activity) {
        this.context = context;
        this.notificationAttrs = notificationAttrs;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.desc.setText(notificationAttrs.get(position).getDescription());
        holder.datetime.setText(notificationAttrs.get(position).getDatetime());
        holder.title.setText(notificationAttrs.get(position).getTitle());

        holder.itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String title = notificationAttrs.get(position).getTitle();
                // final String longitude = addServiceAttrs.get(position).getLon();
                if (title.equals("Please Refill Fruit Box")) {
                    Intent intent = new Intent(activity, FruitActivity.class);
                    activity.startActivity(intent);
                }else if(title.equals("Please Refill Egg Box")){
                    Intent intent = new Intent(activity, EggsActivity.class);
                    activity.startActivity(intent);
                }else {
                    Intent intent = new Intent(activity, VegetableActivity.class);
                    activity.startActivity(intent);
                }
            }
        } );
    }

    @Override
    public int getItemCount() {
        return notificationAttrs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView desc,datetime,title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.notification_title);
            desc = (TextView) itemView.findViewById(R.id.notification_description);
            datetime=(TextView) itemView.findViewById( R.id.notification_datetime );

        }
    }
}
