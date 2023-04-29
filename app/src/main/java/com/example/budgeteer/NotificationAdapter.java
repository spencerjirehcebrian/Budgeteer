package com.example.budgeteer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.List;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.UserViewHolder> {
    private String globalEmail;
    private List<Notifications> listNotifications;
    private NotificationDatabaseHelper databaseHelper;


    public NotificationAdapter(List<Notifications> listNotifications, String globalEmail) {
        this.listNotifications = listNotifications;
        this.globalEmail = globalEmail;
    }
    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_item, parent, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {

        Integer amount = listNotifications.get(position).getPercent();

        if (amount >= 100) {
            holder.field_text.setText("Limit Exceeded:");
        } else {
            holder.field_text.setText("Close to Limit:");
        }

        String lol = amount + "%";
        holder.percentage_text.setText(lol);
        holder.class_text.setText(listNotifications.get(position).get_Class());
        holder.date_text.setText(listNotifications.get(position).getDate());
        holder.time_text.setText(listNotifications.get(position).getTime());

        holder.progress.setProgress(amount);
    }
    @Override
    public int getItemCount() {
        return listNotifications.size();
    }

    /**
     * ViewHolder class
     */
    public class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView field_text, class_text, percentage_text, date_text, time_text;

        public ImageView img_notif_logo;

        public ProgressBar progress;

        public UserViewHolder(View itemView) {
            super(itemView);

            field_text = (TextView) itemView.findViewById(R.id.field_text);
            date_text = (TextView) itemView.findViewById(R.id.date_text);
            time_text = (TextView) itemView.findViewById(R.id.time_text);
            class_text = (TextView) itemView.findViewById(R.id.class_text);
            percentage_text = (TextView) itemView.findViewById(R.id.percentage_text);

            img_notif_logo = (ImageView) itemView.findViewById(R.id.img_notif_logo);

            progress = (ProgressBar) itemView.findViewById(R.id.progess);
        }

    }
}
