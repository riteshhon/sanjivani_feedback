package com.sanjivani.sanjivani_feedback.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.sanjivani.sanjivani_feedback.R;
import com.sanjivani.sanjivani_feedback.WebviewActivity;
import com.sanjivani.sanjivani_feedback.model.FeedbackModel;
import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.ViewHolder> {

    Context context;
    List<FeedbackModel> list;

    public FeedbackAdapter(Context context, List<FeedbackModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_feeback, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(list.get(position).getFeedbackImage()).into(holder.ivFeedbackImage);
        holder.tvFeedbackTitle.setText(list.get(position).getFeedbackTitle());
        holder.tvFeedbackDescribe.setText(list.get(position).getFeedbackDescribe());

        holder.llFeedback.setOnClickListener(view -> {
            WebviewActivity.feedbackFormUrl = list.get(position).getFeedbackUrl();
            WebviewActivity.toolbarTitle = list.get(position).getFeedbackTitle();
            context.startActivity(new Intent(context, WebviewActivity.class));
        });

        if (!list.get(position).getFeedbackTitle().equals("")){
            holder.tvFeedbackTitle.setVisibility(View.VISIBLE);
        } else {
            holder.tvFeedbackTitle.setVisibility(View.GONE);
        }

        if (!list.get(position).getFeedbackDescribe().equals("")){
            holder.tvFeedbackDescribe.setVisibility(View.VISIBLE);
        } else {
            holder.tvFeedbackDescribe.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView  ivFeedbackImage;
        TextView tvFeedbackTitle, tvFeedbackDescribe;
        RelativeLayout llFeedback;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivFeedbackImage = itemView.findViewById(R.id.ivFeedbackImage);
            tvFeedbackTitle = itemView.findViewById(R.id.tvFeedbackTitle);
            tvFeedbackDescribe = itemView.findViewById(R.id.tvFeedbackDescribe);
            llFeedback = itemView.findViewById(R.id.llFeedback);
        }
    }

}
