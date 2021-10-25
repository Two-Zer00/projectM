package dev.twozer00.projectm.adapter;

import android.content.Context;
import android.icu.util.LocaleData;
import android.net.Uri;
import android.text.*;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.text.HtmlCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import dev.twozer00.projectm.R;
import dev.twozer00.projectm.model.Author_details;
import dev.twozer00.projectm.model.Review;
import dev.twozer00.projectm.model.Trending;
import dev.twozer00.projectm.utils.DiffUtilReviews;
import dev.twozer00.projectm.utils.MyDiffUtil;
import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static dev.twozer00.projectm.utils.Constants.BASE_URL_IMG;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    private ArrayList<Review> dataset;
    private Context context;
    private Review review;
    private final String TAG = "Review Adapter";

    public ReviewAdapter(Context context) {
        dataset = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item_recycler, parent, false);
        return new ViewHolder(view);
    }
    public void addElemList(ArrayList<Review> Movies){
        ArrayList<Review> newList = (ArrayList<Review>) dataset.clone();
        newList.addAll(Movies);
        Log.d("LISTS", "addElemList: " + dataset.toString() + " " + newList);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtilReviews(dataset,newList));
        result.dispatchUpdatesTo(this);
        dataset = newList;
        /*dataset.addAll(Movies);
        notifyDataSetChanged();*/ // Old data bind to recyclerview, freezes UI
    }
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        review = dataset.get(position);
        Log.d(TAG, "author: " + review.toString());
        Author_details author_details =  review.getAuthor_details();
        if(author_details.getAvatar_path()!=null){
            String uriAvatar = author_details.getAvatar_path();
            if(uriAvatar.contains("gravatar")){
                Picasso.get().load(Uri.parse(author_details.getAvatar_path().replaceFirst("/",""))).into(holder.avatar);

            }
            else{
                Picasso.get().load(Uri.parse((BASE_URL_IMG).replace("/w500","/w185")+author_details.getAvatar_path())).error(R.drawable.ic_placeholder).into(holder.avatar);
            }
        }
        else {
            holder.avatar.setImageResource(R.drawable.ic_placeholder);
        }
        if(author_details.getName().isEmpty()){
            holder.author.setText(author_details.getUsername());
        }
        else{
            holder.author.setText(author_details.getName());
        }

        //2021-08-13T21:55:39.781Z
        DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
        Instant instant = formatter.parse(review.getCreated_at(), Instant::from);
        Date date = Date.from(instant);
        holder.created_date.setText(date.toLocaleString());

        holder.content.setText(review.getContent().isEmpty()?context.getString(R.string.empty_fields):review.getContent());

        if(author_details.getRating()!= null){
            holder.rate_circle.setProgress(author_details.getRating());
            holder.rate_value.setText(String.valueOf(author_details.getRating()));
        }
        else{
            holder.rate_container.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView avatar;
        private TextView rate_value;
        private ProgressBar rate_circle;
        private TextView author;
        private TextView created_date;
        private TextView content;
        private RelativeLayout rate_container;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar);
            rate_value = itemView.findViewById(R.id.rate_value);
            rate_circle = itemView.findViewById(R.id.rate_circle);
            author = itemView.findViewById(R.id.author);
            created_date = itemView.findViewById(R.id.created_date);
            content = itemView.findViewById(R.id.content);
            rate_container = itemView.findViewById(R.id.rate_container);
        }
    }
}
