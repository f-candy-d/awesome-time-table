package com.d.candy.f.awesometimetable.Adapters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.d.candy.f.awesometimetable.R;
import com.d.candy.f.awesometimetable.structure.MyVH;
import com.d.candy.f.awesometimetable.structure.Notification;

import java.util.ArrayList;

/**
 * Created by daichi on 7/22/17.
 */

public class NotificationCardAdapter extends EntityCardAdapter {

    @NonNull private final ArrayList<Notification> mNotifications;

    public NotificationCardAdapter(@NonNull final ArrayList<Notification> notifications) {
        this(notifications, null);
    }

    public NotificationCardAdapter(
            @NonNull final ArrayList<Notification> notifications,
            @Nullable final MyVH.BaseViewHolder.OnItemClickListener onItemClickListener) {

        super(onItemClickListener);

        // noinspection ConstantConditions
        if (notifications== null) {
            throw new NullPointerException();
        }

        mNotifications = notifications;
    }

    @Override
    public int getItemCount() {
        return mNotifications.size();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public MyVH.BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.notification_card, parent, false);

        return new MyVH.NotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyVH.BaseViewHolder holder, int position) {
        int adpPos = holder.getAdapterPosition();

        MyVH.NotificationViewHolder vh = (MyVH.NotificationViewHolder) holder;
        vh.bind(adpPos, mNotifications.get(adpPos), null);
    }
}
