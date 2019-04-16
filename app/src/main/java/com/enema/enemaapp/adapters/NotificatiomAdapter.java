package com.enema.enemaapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.enema.enemaapp.R;
import com.enema.enemaapp.models.NotificationData;

import java.util.List;

public class NotificatiomAdapter extends RecyclerView.Adapter<NotificatiomAdapter.NotificationViewHolder> {

    private List<NotificationData> notificationDataList;
    private Context context;

    public NotificatiomAdapter(List<NotificationData> notificationDataList, Context context) {
        this.notificationDataList = notificationDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public NotificatiomAdapter.NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notification_list_item, viewGroup, false);
        return  new NotificationViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull NotificatiomAdapter.NotificationViewHolder notificationViewHolder, int i) {

        NotificationData notificationData = notificationDataList.get(i);
        notificationViewHolder.txtNotiMessage.setText(notificationData.getNoti_message());

    }

    @Override
    public int getItemCount() {
        return notificationDataList.size();
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {

        TextView txtNotiMessage;

        public NotificationViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNotiMessage = itemView.findViewById(R.id.txtNotiMessage);

        }
    }
}
