package dev.twozer00.projectm.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import dev.twozer00.projectm.DetailsActivity;
import dev.twozer00.projectm.R;
import dev.twozer00.projectm.model.Movie;
import dev.twozer00.projectm.model.Trending;
import dev.twozer00.projectm.utils.ImageHandler;
import dev.twozer00.projectm.utils.MyDiffUtil;
import org.jetbrains.annotations.NotNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>  {
    private static final String BOOK_DETAIL_KEY = "";
    private ArrayList<Trending> dataset;
    private Context context;
    private Trending movie;
    private String query;
    private Target target;

    public MovieAdapter(Context context) {
        dataset = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull @NotNull MovieAdapter.ViewHolder holder, int position) {
        movie = dataset.get(position);
        Picasso.get().load(Uri.parse(movie.getPoster_path())).into(holder.Poster, new Callback() {
            @Override
            public void onSuccess() {
                Bitmap bitmap = ((BitmapDrawable)holder.Poster.getDrawable()).getBitmap();
                holder.releaseDate.setBackgroundColor((Palette.from(bitmap)).generate().getDominantColor(context.getColor(R.color.releaseBackground)));
            }
            @Override
            public void onError(Exception e) {
                if(query.equals("Popular")){
                    if(movie.getMedia_type().equals("movie")){
                    holder.title.setText(movie.getTitle());
                    holder.title.setVisibility(View.VISIBLE);
                    }
                    else{
                        holder.title.setText(movie.getName());
                        holder.title.setVisibility(View.VISIBLE);
                    }
                }
                else{
                    holder.title.setText(movie.getTitle());
                    holder.title.setVisibility(View.VISIBLE);
                }
            }
        });

        Picasso.get().setLoggingEnabled(true);
        holder.Rate.setText(String.valueOf(movie.getVote_average()));


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
        Date convertedDate = new Date();

        try {
            if (movie.getRelease_date() != null) {
                Objects.requireNonNull(movie.getRelease_date());
                convertedDate = dateFormat.parse(movie.getRelease_date());
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        DateFormat Date = DateFormat.getDateInstance();
        Log.d("TIME", "onBindViewHolder: " + System.currentTimeMillis() + " " + convertedDate.getTime() );
        if(convertedDate.getTime()>System.currentTimeMillis()){
            holder.releaseDate.setText(Date.format(convertedDate));
            holder.releaseDate.setVisibility(View.VISIBLE);
        }





        if(movie.getVote_average()>0){
            if(movie.getVote_count()>100){
                holder.ratingBar.getProgressDrawable().setColorFilter(context.getColor(R.color.validRate), android.graphics.PorterDuff.Mode.SRC_IN);
            }
            holder.ratingBar.setProgress((int) (movie.getVote_average()*10),true);
        }
        else{
            holder.relativeLayout.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
    public void addElemList(ArrayList<Trending> Movies){
        ArrayList<Trending> newList = (ArrayList<Trending>) dataset.clone();
        newList.addAll(Movies);
        Log.d("LISTS", "addElemList: " + dataset.toString() + " " + newList);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new MyDiffUtil(dataset,newList));
        result.dispatchUpdatesTo(this);
        dataset = newList;
//        dataset.addAll(Movies);
//        notifyDataSetChanged();
    }

    public void setQuery(String query){
        this.query = query;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener,View.OnClickListener {
        private ImageView Poster;
        private CardView item;
        private TextView Rate;
        private TextView title;
        private ProgressBar ratingBar;
        private RelativeLayout relativeLayout;
        private TextView releaseDate;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            Poster = itemView.findViewById(R.id.poster);
            Rate = itemView.findViewById(R.id.rate);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            relativeLayout = itemView.findViewById(R.id.rateBar);
            releaseDate = itemView.findViewById(R.id.releaseDate);
            item = itemView.findViewById(R.id.items);
            title = itemView.findViewById(R.id.title);
            item.setOnLongClickListener(this);
            item.setOnClickListener(this);
        }
//
//        @Override
//        public void onClick(View view) {
//            Poster = itemView.findViewById(R.id.poster);
//            movie = dataset.get(getAdapterPosition());
//            switch (view.getId()) {
//                case R.id.items:
//                    Snackbar.make(itemView, "Replace with your own action " + movie.getTitle(), Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
////                    Intent i = new Intent(view.getContext(), DetailsActivity.class);
////                    i.putExtra(BOOK_DETAIL_KEY, movie);
////                    view.getContext().startActivity(i);
//                    break;
//            }
//        }

        @Override
        public void onClick(View v) {

        }

        @Override
        public boolean onLongClick(View v) {
            movie = dataset.get(getAdapterPosition());
            switch (v.getId()) {
                case R.id.items:
//                    Snackbar.make(itemView, movie.getTitle(), Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
                    Toast.makeText(context,""+movie.getOriginal_title(),Toast.LENGTH_LONG).show();
                break;
            }
            return false;
        }
    }
}
