package dev.twozer00.projectm.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
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
import dev.twozer00.projectm.MovieDetails;
import dev.twozer00.projectm.PeopleDetails;
import dev.twozer00.projectm.R;
import dev.twozer00.projectm.model.Cast;
import dev.twozer00.projectm.model.Crew;
import dev.twozer00.projectm.model.PersonCredit;
import dev.twozer00.projectm.utils.DiffUtilCredits;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class CreditAdapter extends RecyclerView.Adapter<CreditAdapter.ViewHolder> {
    private ArrayList dataset;
    private Context context;
    private Cast cast;
    private Crew crew;
    private String creditsType;

    public CreditAdapter(Context context,String creditsType) {
        dataset = new ArrayList<>();
        this.context = context;
        this.creditsType = creditsType;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.credit_item_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        String uriImage = "";
        if(creditsType.equals("cast")){
            cast = (Cast) dataset.get(position);
            uriImage = cast.getProfile_path().replace("/w500/","/w185/");//replace poster size to profile size
            holder.actorName.setText(cast.getName());
            holder.characterName.setText(cast.getCharacter());
        }
        else{
            crew = (Crew) dataset.get(position);
            uriImage = crew.getProfile_path().replace("/w500/","/w185/");//replace poster size to profile size
            holder.actorName.setText(crew.getName());
            holder.characterName.setText(crew.getJob());
        }
        Picasso.get().load(Uri.parse(uriImage)).placeholder(R.drawable.ic_placeholder).resize(185,0).into(holder.poster);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
    public void addElemList(ArrayList credits){
//        if(creditsType.equals("cast")){
//            ArrayList<Cast> newList = (ArrayList<Cast>) dataset.clone();
//            newList.addAll((ArrayList<Cast>)credits);
//            Log.d("LISTS", "addElemList: " + dataset.toString() + " " + newList);
//            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtilCredits(dataset,newList,creditsType));
//            result.dispatchUpdatesTo(this);
//            dataset = newList;
//        }
//        else{
//            ArrayList<Crew> newList = (ArrayList<Crew>) dataset.clone();
//            newList.addAll((ArrayList<Crew>)credits);
//            Log.d("LISTS", "addElemList: " + dataset.toString() + " " + newList);
//            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtilCredits(dataset,newList,creditsType));
//            result.dispatchUpdatesTo(this);
//            dataset = newList;
//        }
        dataset = credits;
        notifyDataSetChanged(); // Old data bind to recyclerview, freezes UI
    }

    public void setCreditsType(String creditsType) {
        this.creditsType = creditsType;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView poster;
        private TextView actorName;
        private TextView characterName;
        private CardView item;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.profile_poster);
            actorName = itemView.findViewById(R.id.actor_name);
            characterName = itemView.findViewById(R.id.character_name);
            item = itemView.findViewById(R.id.item);
            item.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.d("credit adapter", "onClick: click ");
            PersonCredit personCredit;
            switch (creditsType){
                case "crew":
                    crew = (Crew) dataset.get(getAdapterPosition());
                    personCredit  = new PersonCredit();
                    personCredit.setId(cast.getId());
                    personCredit.setName(cast.getName());
                    personCredit.setProfile_path(cast.getProfile_path());
                    personCredit.setKnown_for_department(cast.getKnown_for_department());
                    if (v.getId() == R.id.item) {
                        Intent i = new Intent(v.getContext(), PeopleDetails.class);
                        i.putExtra("media", personCredit);
                        v.getContext().startActivity(i);
                    }
                break;
                case "cast":
                    cast = (Cast) dataset.get(getAdapterPosition());
                    personCredit  = new PersonCredit();
                    personCredit.setId(cast.getId());
                    personCredit.setProfile_path(cast.getProfile_path());
                    personCredit.setKnown_for_department(cast.getKnown_for_department());
                    personCredit.setName(cast.getName());
                    if (v.getId() == R.id.item) {
                        Intent i = new Intent(v.getContext(), PeopleDetails.class);
                        i.putExtra("media", personCredit);
                        v.getContext().startActivity(i);
                    }
            }
        }
    }
}
