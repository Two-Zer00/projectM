package dev.twozer00.projectm.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.util.Log;
import android.util.Pair;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import dev.twozer00.projectm.MainActivity;
import dev.twozer00.projectm.MovieDetails;
import dev.twozer00.projectm.R;
import dev.twozer00.projectm.TvShowDetails;
import dev.twozer00.projectm.model.Movie;
import dev.twozer00.projectm.model.Trending;
import dev.twozer00.projectm.model.TvShow;
import dev.twozer00.projectm.utils.DiffUtilMovie;
import dev.twozer00.projectm.utils.MyDiffUtil;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static dev.twozer00.projectm.utils.Utils.dpToPx;
import static dev.twozer00.projectm.utils.Utils.spToPx;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>  {
    private ArrayList<Movie> dataset;
    private Context context;
    private Movie movie;

    public MovieAdapter(Context context) {
        dataset = new ArrayList<>();
        this.context = context;
    }

    @Override
    public void onViewAttachedToWindow(@NonNull @NotNull ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent, false);
        view.findViewById(R.id.poster);
        return new ViewHolder(view);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull @NotNull MovieAdapter.ViewHolder holder, int position) {
        movie = dataset.get(position);
        holder.title.setText(movie.getTitle());
        Picasso.get().load(movie.getPoster_path()).into(holder.Poster, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {
                holder.title.setVisibility(View.VISIBLE);
            }
        });
        if(movie.getVote_count()<1){
            holder.relativeLayout.setVisibility(View.GONE);
        }
        else{
            if(movie.getVote_count()>100){
                holder.ratingBar.getProgressDrawable().setColorFilter(context.getColor(R.color.validRate), android.graphics.PorterDuff.Mode.SRC_IN);
            }
            holder.ratingBar.setProgress((int) (movie.getVote_average()*10),false);
            holder.Rate.setText(String.valueOf(movie.getVote_average()));
        }
//        if(query.equals("Upcoming")){
//            holder.Poster.setTransitionName("poster");
//        }
//        else {
//            holder.Poster.setTransitionName("backdrop");
//        }
//        if(uriImage.contains("null") || query.equals("Upcoming")){
//            uriImage = movie.getPoster_path();
//        }
//        Picasso.get().load(Uri.parse(uriImage)).into(holder.Poster, new Callback() {
//            @Override
//            public void onSuccess() {
//                Bitmap bitmap = ((BitmapDrawable)holder.Poster.getDrawable()).getBitmap();
//                int color = Palette.from(bitmap).generate().getDominantColor(context.getColor(R.color.purple_500));
//                GradientDrawable drawable = new GradientDrawable();
//                drawable.setColors(new int[]{Color.argb(0,1,1,1),color});
//                holder.linearLayout.setBackground(drawable);
//                if (query.equals("Upcoming")){
//                    holder.title.setVisibility(View.GONE);
//                }
//                else{
//                    holder.title.setVisibility(View.VISIBLE);
//                }
//            }
//            @Override
//            public void onError(Exception e) {
//                Log.d("Title", "onError: ");
//                holder.title.setVisibility(View.VISIBLE);
//                holder.title.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
//            }
//        });
//
//        if(query.equals("Popular")){
//            if(movie.getMedia_type().equals("movie")){
//                holder.title.setText(movie.getTitle());
//            }
//            else{
//                holder.title.setText(movie.getName());
//            }
//        }
//        else{
//            holder.title.setText(movie.getTitle());
//            holder.ratingBar.getLayoutParams().width =dpToPx(150,context)/5;
//            holder.Rate.setTextSize(spToPx(150,context)/5f);
//            holder.releaseDate.setTextSize(spToPx(7,context));
//            holder.item.getLayoutParams().height = dpToPx(200,context);
//        }
//        Picasso.get().setLoggingEnabled(true);
//        Picasso.get().setIndicatorsEnabled(true);
//        holder.Rate.setText(String.valueOf(movie.getVote_average()));
//
//
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH);
//        Date convertedDate = new Date();
//
//        try {
//            if (movie.getRelease_date() != null) {
//                Objects.requireNonNull(movie.getRelease_date());
//                convertedDate = dateFormat.parse(movie.getRelease_date());
//            }
//        } catch (ParseException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        DateFormat Date = DateFormat.getDateInstance();
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(convertedDate);
//        Log.d("TIME", "onBindViewHolder: " + System.currentTimeMillis() + " " + convertedDate.getTime() );
//        if(convertedDate.getTime()>System.currentTimeMillis()){
//            holder.releaseDate.setText(Date.format(convertedDate));
//            holder.releaseDate.setVisibility(View.VISIBLE);
//            if(query.equals("Popular")){
//                holder.releaseDate.setTextSize(spToPx(15    ,context));
//                holder.releaseDate.setAutoSizeTextTypeWithDefaults(TextView.AUTO_SIZE_TEXT_TYPE_NONE);
//                holder.releaseDate.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
//            }
//        }
//
//        if(movie.getVote_average()>0){
//            if(movie.getVote_count()>100){
//                holder.ratingBar.getProgressDrawable().setColorFilter(context.getColor(R.color.validRate), android.graphics.PorterDuff.Mode.SRC_IN);
//            }
//            holder.ratingBar.setProgress((int) (movie.getVote_average()*10),true);
//        }
//        else{
//            holder.relativeLayout.setTransitionName("");
//            holder.relativeLayout.setVisibility(View.INVISIBLE);
//        }
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
    public void addElemList(ArrayList<Movie> Movies){
        ArrayList<Movie> newList = (ArrayList<Movie>) dataset.clone();
        newList.addAll(Movies);
        Log.d("LISTS", "addElemList: " + dataset.toString() + " " + newList);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtilMovie(dataset,newList));
        result.dispatchUpdatesTo(this);
        dataset = newList;
        /*dataset.addAll(Movies);
        notifyDataSetChanged();*/ // Old data bind to recyclerview, freezes UI
    }
    public void replaceListElements(ArrayList<Movie> movies){
        dataset = movies;
        notifyDataSetChanged(); // Old data bind to recyclerview, freezes UI
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener,View.OnClickListener {
        private ImageView Poster;
        private CardView item;
        private TextView Rate;
        private TextView title;
        private ProgressBar ratingBar;
        private RelativeLayout relativeLayout;
        private TextView releaseDate;
        private LinearLayout linearLayout;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            Poster = itemView.findViewById(R.id.poster);
            Rate = itemView.findViewById(R.id.rate);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            relativeLayout = itemView.findViewById(R.id.rateBar);
            releaseDate = itemView.findViewById(R.id.releaseDate);
            item = itemView.findViewById(R.id.items);
            title = itemView.findViewById(R.id.title);
            linearLayout = itemView.findViewById(R.id.titleContainer);
            item.setOnLongClickListener(this);
            item.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            movie = dataset.get(getAdapterPosition());
            Log.d("POSTER", "onClick: " + Poster.getId());
            switch (view.getId()) {
                case R.id.items:
                    Intent i = new Intent(view.getContext(), MovieDetails.class);
                    Pair[] pair = new Pair[(movie.getVote_count()>0?2:1)]; // remove rating bar transation if there isnt votes
                    if (movie.getVote_count()>0){
                        Log.d("RATING BAR", "rating bar");
                        pair[0] = new Pair<View,String>(relativeLayout,"rating_container");
                        pair[1] = new Pair<View,String>(Poster,"poster");
                    }
                    else{
                        Log.d("RATING BAR", "no rating bar");
                        pair[0] = new Pair<View,String>(Poster,"poster");
                    }
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) view.getContext(),pair);
                    i.putExtra("media",movie);
                    Log.d("POSTER", "onClick: ");
                    view.getContext().startActivity(i,options.toBundle());
                    /*if(query.equals("Popular")){
                        Intent i = null;
                        Pair[] pair = null;
                        ActivityOptions options = null;
                        switch (movie.getMedia_type()){
                            case "movie":
                                i = new Intent(view.getContext(), MovieDetails.class);
                                pair = new Pair[(movie.getVote_count()>0?2:1)]; // remove rating bar transation if there isnt votes
                                if (movie.getVote_count()>0){
                                    pair[0] = new Pair<View,String>(relativeLayout,"rating_container");
                                    pair[1] = new Pair<View,String>(Poster,"backdrop");
                                }
                                else{
                                    pair[0] = new Pair<View,String>(relativeLayout,"backdrop");
                                }
                                options = ActivityOptions.makeSceneTransitionAnimation((MainActivity)view.getContext(),pair);
                                i.putExtra("media",(Movie) movie.getMediaObject());
                                view.getContext().startActivity(i,options.toBundle());
                            break;
                            case "tv":
                                i = new Intent(view.getContext(), TvShowDetails.class);
                                pair = new Pair[(movie.getVote_count()>0?2:1)]; // remove rating bar transation if there isnt votes
                                if (movie.getVote_count()>0){
                                    pair[0] = new Pair<View,String>(relativeLayout,"rating_container");
                                    pair[1] = new Pair<View,String>(Poster,"backdrop");
                                }
                                else{
                                    pair[0] = new Pair<View,String>(relativeLayout,"backdrop");
                                }
                                options = ActivityOptions.makeSceneTransitionAnimation((MainActivity)view.getContext(),pair);
                                i.putExtra("media",(TvShow) movie.getMediaObject());
                                view.getContext().startActivity(i,options.toBundle());
                            break;
                        }
                    }
                    else{
                        Intent i = new Intent(view.getContext(), MovieDetails.class);
                        Pair[] pair = new Pair[(movie.getVote_count()>0?2:1)]; // remove rating bar transation if there isnt votes
                        if (movie.getVote_count()>0){
                            Log.d("RATING BAR", "rating bar");
                            pair[0] = new Pair<View,String>(relativeLayout,"rating_container");
                            pair[1] = new Pair<View,String>(Poster,"poster");
                        }
                        else{
                            Log.d("RATING BAR", "no rating bar");
                            pair[0] = new Pair<View,String>(Poster,"poster");
                        }
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((MainActivity)view.getContext(),pair);
                        i.putExtra("media",(Movie) movie.getMediaObject());
                        Log.d("POSTER", "onClick: ");
                        view.getContext().startActivity(i,options.toBundle());
                    }*/
                break;
            }

        }

        @Override
        public boolean onLongClick(View v) {
            movie = dataset.get(getAdapterPosition());
            switch (v.getId()) {
                case R.id.items:
//                    Snackbar.make(itemView, movie.getTitle(), Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
                    /*String title = "";
                    if(query.equals("Popular")){
                        if (movie.getMedia_type().equals("movie")){
                            title = movie.getTitle();
                        }
                        else{
                            title = movie.getName();
                        }
                    }
                    else{
                        title = movie.getTitle();
                    }
                    Toast.makeText(context,title,Toast.LENGTH_LONG).show();*/
                break;
            }
            return false;
        }
    }
}
