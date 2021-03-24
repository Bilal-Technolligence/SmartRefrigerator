package com.example.smartrefrigerator;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.ViewHolder> {
    ArrayList<notificationAttr> notificationAttrs;
    private Context context;

    public NotificationListAdapter(ArrayList<notificationAttr> notificationAttrs, Context context) {
        this.context = context;
        this.notificationAttrs = notificationAttrs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_item, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.desc.setText(notificationAttrs.get(position).getDescription());
        holder.datetime.setText(notificationAttrs.get(position).getDatetime());
        holder.title.setText(notificationAttrs.get(position).getTitle());

        holder.itemView.setOnContextClickListener(new View.OnContextClickListener() {
            @Override
            public boolean onContextClick(View v) {
                Intent intent = new Intent(context,EggsActivity.class);
                context.startActivity(intent);
                return false;
            }
        });
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
