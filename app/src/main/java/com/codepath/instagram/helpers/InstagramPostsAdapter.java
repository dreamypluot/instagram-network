package com.codepath.instagram.helpers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.instagram.R;
import com.codepath.instagram.models.InstagramPost;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by achow on 12/3/15.
 */
public class InstagramPostsAdapter extends RecyclerView.Adapter<InstagramPostsAdapter.ViewHolder> {
    private List<InstagramPost> posts;
    private Context context;
    DateFormat df = DateFormat.getDateInstance();
    public InstagramPostsAdapter(List<InstagramPost> posts, Context context) {
        this.posts = posts;
        this.context = context;
    }

    //inflate the layout
    //return a holder instance
    @Override
    public InstagramPostsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_post, parent, false);
        return new InstagramPostsAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(InstagramPostsAdapter.ViewHolder holder, int position) {
        InstagramPost post = posts.get(position);
        holder.tvUsername.setText(post.user.userName);
        if (!TextUtils.isEmpty(post.user.profilePictureUrl)) {
            Picasso.with(context).load(post.user.profilePictureUrl).into(holder.ivAvatar);
        }
        long now = new Date().getTime();
        long postDate = post.createdTime * 1000;
        long diff = now - postDate;
        long diffSeconds = diff / 1000;
        long diffMinutes = diff / (60 * 1000);
        long diffHours = diff / (60 * 60 * 1000);
        long diffDays = diff / (60 * 60 * 1000 * 24);
        String holderText = "";
        String postDateString = df.format(new Date(post.createdTime * 1000));
        if (diffDays > 0) {
            holderText = diffDays + " days ago";
        } else if (diffHours > 0) {
            holderText = diffHours + " hours ago";
        } else if (diffMinutes > 0) {
            holderText = diffMinutes + " minutes ago";
        } else {
            holderText = diffSeconds + " seconds ago";
        }
        holder.tvDate.setText(holderText);
        Picasso.with(context).load(post.image.imageUrl).resize(DeviceDimensionsHelper.getDisplayWidth(context), 0)
                .into(holder.ivImage);
        holder.tvLikes.setText("" + post.likesCount + " Likes");
        if (!TextUtils.isEmpty(post.caption)) {
            //holder.tvCaption.setText(post.caption);
            // Create a span that will make the text red
            ForegroundColorSpan blueForegroundColorSpan = new ForegroundColorSpan(
                    context.getResources().getColor(R.color.blue_text));

            // Use a SpannableStringBuilder so that both the text and the spans are mutable
            SpannableStringBuilder ssb = new SpannableStringBuilder(post.user.userName);

            // Apply the color span
            ssb.setSpan(
                    blueForegroundColorSpan,            // the span to add
                    0,                                 // the start of the span (inclusive)
                    ssb.length(),                      // the end of the span (exclusive)
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); // behavior when text is later inserted into the SpannableStringBuilder
            // SPAN_EXCLUSIVE_EXCLUSIVE means to not extend the span when additional
            // text is added in later

            // Add a blank space
            ssb.append(" ");

            // Create a span that will gray the text
            SpannableStringBuilder csb = new SpannableStringBuilder(post.caption);

            // Add the secondWord and apply the strikethrough span to only the second word
            ssb.append(post.caption);
            ssb.setSpan(
                    csb,
                    ssb.length() - post.caption.length(),
                    ssb.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            // Set the TextView text and denote that it is Editable
            // since it's a SpannableStringBuilder
            holder.tvCaption.setText(ssb, TextView.BufferType.NORMAL);
        }

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // list the fields
        TextView tvUsername;
        ImageView ivAvatar;
        ImageView ivImage;
        TextView tvDate;
        TextView tvLikes;
        TextView tvCaption;
        public ViewHolder(View rowLayout) {
            super(rowLayout);
            tvUsername = (TextView) rowLayout.findViewById(R.id.tvUsername);
            ivAvatar = (ImageView) rowLayout.findViewById(R.id.ivAvatar);
            tvDate = (TextView) rowLayout.findViewById(R.id.tvDate);
            ivImage = (ImageView) rowLayout.findViewById(R.id.ivImage);
            tvLikes = (TextView) rowLayout.findViewById(R.id.tvLike);
            tvCaption = (TextView) rowLayout.findViewById(R.id.tvCaption);
        }
    }
}
