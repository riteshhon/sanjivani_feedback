package com.sanjivani.sanjivani_feedback.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;
import com.sanjivani.sanjivani_feedback.R;
import com.sanjivani.sanjivani_feedback.model.AboutUsModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AboutUsAdapter extends RecyclerView.Adapter<AboutUsAdapter.ViewHolder> {

    Context context;
    List<AboutUsModel> list;

    public AboutUsAdapter(Context context, List<AboutUsModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_aboutus, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(list.get(position).getImageUrl()).into(holder.ivTeamMember);
        holder.tvTeamMemberName.setText(list.get(position).getName());
        holder.tvTeamMemberInfo.setText(list.get(position).getDescribeMember());


        holder.cvLinkedIn.setOnClickListener(view -> {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(list.get(position).getLinkedInUrl())));
        });
        holder.cvTwitter.setOnClickListener(view -> {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(list.get(position).getTwitterUrl())));
        });
        holder.cvGithub.setOnClickListener(view -> {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(list.get(position).getGithubUrl())));
        });

        if (!list.get(position).getLinkedInUrl().equals("")){
            holder.cvLinkedIn.setVisibility(View.VISIBLE);
        } else {
            holder.cvLinkedIn.setVisibility(View.GONE);
        }
        if (!list.get(position).getTwitterUrl().equals("")){
            holder.cvTwitter.setVisibility(View.VISIBLE);
        } else {
            holder.cvTwitter.setVisibility(View.GONE);
        }
        if (!list.get(position).getGithubUrl().equals("")){
            holder.cvGithub.setVisibility(View.VISIBLE);
        } else {
            holder.cvGithub.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView ivTeamMember;
        TextView tvTeamMemberName, tvTeamMemberInfo;
        MaterialCardView cvLinkedIn, cvTwitter, cvGithub;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivTeamMember = itemView.findViewById(R.id.ivTeamMember);
            tvTeamMemberName = itemView.findViewById(R.id.tvTeamMemberName);
            tvTeamMemberInfo = itemView.findViewById(R.id.tvTeamMemberInfo);
            cvLinkedIn = itemView.findViewById(R.id.cvLinkedIn);
            cvTwitter = itemView.findViewById(R.id.cvTwitter);
            cvGithub = itemView.findViewById(R.id.cvGithub);

        }
    }

}
