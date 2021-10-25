package dev.twozer00.projectm.adapter;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;
import dev.twozer00.projectm.MainActivity;
import dev.twozer00.projectm.MovieDetails;
import dev.twozer00.projectm.PeopleDetails;
import dev.twozer00.projectm.R;
import dev.twozer00.projectm.TvShowDetails;
import dev.twozer00.projectm.model.*;
import dev.twozer00.projectm.utils.DiffUtilCombined;
import dev.twozer00.projectm.utils.MyDiffUtil;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Text;

import java.util.ArrayList;

import static dev.twozer00.projectm.utils.Utils.dpToPx;

public class CombinedAdapter extends RecyclerView.Adapter<CombinedAdapter.ViewHolder> {
    private ArrayList<Combined> dataset;
    private Context context;
    private String media_type;
    private Combined combined;

    public CombinedAdapter(Context context) {
        this.dataset = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.combined_item_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        combined = dataset.get(position);
        if(combined.getMedia_type().equals("person")){
            Picasso.get().load(combined.getProfile_path()).into(holder.image);
            holder.title.setText(combined.getName());
            holder.image.setTransitionName("");
        }
        else{
            Picasso.get().load(combined.getPoster_path()).into(holder.image);
            holder.title.setVisibility(View.GONE);
           if(combined.getMedia_type().equals("tv")){
               //holder.title.setText(combined.getName());
           }
           else{
               //holder.title.setText(combined.getTitle());
           }
        }
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }


    public void addList(ArrayList<Combined> combineds){
        ArrayList<Combined> newList = (ArrayList<Combined>) dataset.clone();
        newList.addAll(combineds);
        Log.d("LISTS", "addElemList: " + dataset.toString() + " " + newList);
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtilCombined(dataset,newList));
        result.dispatchUpdatesTo(this);
        dataset = newList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView image;
        private TextView title;
        private CardView item;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            item = itemView.findViewById(R.id.item);
            item.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            combined = dataset.get(getAdapterPosition());
            Intent i = null;
            Pair[] pair = null;
            ActivityOptions options = null;
            switch (combined.getMedia_type()){
                case "tv":
                    i = new Intent(view.getContext(), TvShowDetails.class);
                    pair = new Pair[1];
                    pair[0] = new Pair<View,String>(image,"poster");
                    options = ActivityOptions.makeSceneTransitionAnimation((MainActivity)view.getContext(),pair);
                    i.putExtra("media",(TvShow) combined.getMediaObject());
                    view.getContext().startActivity(i,options.toBundle());
                break;
                case "movie":
                    i = new Intent(view.getContext(), MovieDetails.class);
                    pair = new Pair[1];
                    pair[0] = new Pair<View,String>(image,"poster");
                    options = ActivityOptions.makeSceneTransitionAnimation((MainActivity)view.getContext(),pair);
                    i.putExtra("media",(Movie) combined.getMediaObject());
                    view.getContext().startActivity(i,options.toBundle());
                break;
                case "person":
                    i = new Intent(view.getContext(), PeopleDetails.class);
                    pair = new Pair[1];
                    pair[0] = new Pair<View,String>(image,"poster");
                    options = ActivityOptions.makeSceneTransitionAnimation((MainActivity)view.getContext(),pair);
                    PersonCredit person = new PersonCredit();
                    person.setId(combined.getId());
                    person.setName(combined.getName());
                    person.setProfile_path(combined.getProfile_path());
                    person.setKnown_for_department(combined.getKnown_for_department());
                    i.putExtra("media",(PersonCredit) person);
                    view.getContext().startActivity(i,options.toBundle());
                break;
            }
        }
    }
}
