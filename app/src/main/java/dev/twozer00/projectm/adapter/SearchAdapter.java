package dev.twozer00.projectm.adapter;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import dev.twozer00.projectm.*;
import dev.twozer00.projectm.model.Combined;
import dev.twozer00.projectm.model.Movie;
import dev.twozer00.projectm.model.PersonCredit;
import dev.twozer00.projectm.model.TvShow;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private ArrayList<Combined> dataset;
    private Context context;
    private Combined element;

    public SearchAdapter(Context context) {
        dataset = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_search,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        element = dataset.get(position);
        if(element.getMedia_type().equals("person")){
            holder.title.setText(element.getName());
        }
        else if(element.getMedia_type().equals("movie")){
            holder.title.setText(element.getTitle());
        }
        else{
            holder.title.setText(element.getName());
        }
        holder.media.setText(element.getMedia_type());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void addList(ArrayList<Combined> combineds){
        dataset = combineds;
        notifyDataSetChanged(); // Old data bind to recyclerview, freezes UI
    }

    public void cleanList(){
        dataset.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title;
        private TextView media;
        private CardView item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            media = itemView.findViewById(R.id.media);
            item = itemView.findViewById(R.id.cardSearch);
            item.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            element = dataset.get(getAdapterPosition());
            Intent i = null;
            //Pair[] pair = null;
            ActivityOptions options = null;
            switch (element.getMedia_type()){
                case "tv":
                    i = new Intent(view.getContext(), TvShowDetails.class);
                    TvShow tvShow = (TvShow) element.getMediaObject();
                    options = ActivityOptions.makeCustomAnimation(view.getContext(),R.anim.slide_from_right,R.anim.slide_to_left);
                    Log.d("SEARCH ADAPTER", tvShow.toString());
                    i.putExtra("media",(TvShow) tvShow);
                    view.getContext().startActivity(i,options.toBundle());
                    break;
                case "movie":
                    i = new Intent(view.getContext(), MovieDetails.class);
                    Movie movie = (Movie) element.getMediaObject();
                    options = ActivityOptions.makeCustomAnimation(view.getContext(),R.anim.slide_from_right,R.anim.slide_to_left);
                    Log.d("SEARCH ADAPATER", movie.toString());
                    i.putExtra("media",(Movie) movie);
                    view.getContext().startActivity(i,options.toBundle());
                    break;
                case "person":
                    i = new Intent(view.getContext(), PeopleDetails.class);
                    PersonCredit person = new PersonCredit();
                    person.setId(element.getId());
                    person.setName(element.getName());
                    person.setProfile_path(element.getProfile_path());
                    person.setKnown_for_department(element.getKnown_for_department());
                    options = ActivityOptions.makeCustomAnimation(view.getContext(),R.anim.slide_from_right,R.anim.slide_to_left);
                    Log.d("SEARCH ADAPTER",person.toString());
                    i.putExtra("media",(PersonCredit) person);
                    view.getContext().startActivity(i,options.toBundle());
                    break;
            }
        }
    }
}
