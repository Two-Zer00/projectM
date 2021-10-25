package dev.twozer00.projectm.adapter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import dev.twozer00.projectm.MovieDetails;
import dev.twozer00.projectm.R;
import dev.twozer00.projectm.TvShowDetails;
import dev.twozer00.projectm.model.Movie;
import dev.twozer00.projectm.model.TvShow;
import dev.twozer00.projectm.utils.DiffUtilMovie;
import dev.twozer00.projectm.utils.DiffUtilTvShow;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.ViewHolder> {
    private Context context;
    private ArrayList<TvShow> dataset;
    private TvShow tvShow;

    public TvShowAdapter(Context context) {
        this.dataset = new ArrayList();
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public TvShowAdapter.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler, parent, false);
        //view.findViewById(R.id.poster);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TvShowAdapter.ViewHolder holder, int position) {
        tvShow = dataset.get(position);
        holder.title.setText(tvShow.getName());
        Picasso.get().load(tvShow.getPoster_path()).into(holder.Poster, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {
                holder.title.setVisibility(View.VISIBLE);
            }
        });
        if(tvShow.getVote_count()<1){
            holder.relativeLayout.setVisibility(View.GONE);
        }
        else{
            if(tvShow.getVote_count()>100){
                holder.ratingBar.getProgressDrawable().setColorFilter(context.getColor(R.color.validRate), android.graphics.PorterDuff.Mode.SRC_IN);
            }
            holder.ratingBar.setProgress((int) (tvShow.getVote_average()*10),false);
            holder.Rate.setText(String.valueOf(tvShow.getVote_average()));
        }

    }
    public void addElemList(ArrayList<TvShow> tvShows){
        ArrayList<TvShow> newList = (ArrayList<TvShow>) dataset.clone();
        newList.addAll(tvShows);
        Log.d("LISTS", "addElemList: " + dataset.toString() + " " + newList);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtilTvShow(dataset,newList));
        result.dispatchUpdatesTo(this);
        dataset = newList;
        /*dataset.addAll(Movies);
        notifyDataSetChanged();*/ // Old data bind to recyclerview, freezes UI
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void replaceListElements(ArrayList<TvShow> tvShows){
        dataset = tvShows;
        notifyDataSetChanged(); // Old data bind to recyclerview, freezes UI
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
            item.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            tvShow = dataset.get(getAdapterPosition());
            Log.d("POSTER", "onClick: " + Poster.getId());
            switch (view.getId()) {
                case R.id.items:
                    Intent i = new Intent(view.getContext(), TvShowDetails.class);
                    Pair[] pair = new Pair[(tvShow.getVote_count()>0?2:1)]; // remove rating bar transation if there isnt votes
                    if (tvShow.getVote_count()>0){
                        Log.d("RATING BAR", "rating bar");
                        pair[0] = new Pair<View,String>(relativeLayout,"rating_container");
                        pair[1] = new Pair<View,String>(Poster,"poster");
                    }
                    else{
                        Log.d("RATING BAR", "no rating bar");
                        pair[0] = new Pair<View,String>(Poster,"poster");
                    }
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) view.getContext(),pair);
                    i.putExtra("media",tvShow);
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
    }
}
