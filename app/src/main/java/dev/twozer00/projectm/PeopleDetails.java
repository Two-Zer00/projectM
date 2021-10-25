package dev.twozer00.projectm;

import android.graphics.Color;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;
import dev.twozer00.projectm.model.People;
import dev.twozer00.projectm.model.PersonCredit;
import dev.twozer00.projectm.ui.main.PeopleDetailsPagerAdapter;
import dev.twozer00.projectm.ui.main.TvShowDetailsPagerAdapter;

public class PeopleDetails extends AppCompatActivity {

    private PersonCredit person;
    private ImageView profile_poster;
    private TextView name;
    private TextView department;
    private TextView birthday;
    private TextView deathday;
    private TextView place_of_birth;
    protected CollapsingToolbarLayout collapsingToolbarLayout;
    private Toolbar toolbar;
    private TabLayout tabs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_details);
        person = (PersonCredit) getIntent().getSerializableExtra("media");
        Log.d("PEOPLE DETAILS", "onCreate: "+person.toString());
        name = findViewById(R.id.name);
        department = findViewById(R.id.department);
        profile_poster = findViewById(R.id.profile_poster);
        collapsingToolbarLayout = findViewById(R.id.barTitle);
        toolbar = findViewById(R.id.toolbar);
        PeopleDetailsPagerAdapter sectionsPagerAdapter = new PeopleDetailsPagerAdapter(getSupportFragmentManager(), this ,(int)person.getId());
        ViewPager viewPager = findViewById(R.id.people_details_view_pager);
        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs = findViewById(R.id.tabs);
        collapsingToolbarLayout.setExpandedTitleColor(Color.argb(0,1,1,1));
        tabs.setSelectedTabIndicatorColor(getColor(R.color.white));
        tabs.setTabTextColors(getColor(R.color.tabs),getColor(R.color.white));
        tabs.setupWithViewPager(viewPager);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        bindData(person);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finishAfterTransition();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void bindData(PersonCredit person) {
        name.setText(person.getName());
        setTitle(person.getName());
        department.setText(person.getKnown_for_department());
        Picasso.get().load(person.getProfile_path()).into(profile_poster);
    }
}