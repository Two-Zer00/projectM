package dev.twozer00.projectm;

import android.os.Bundle;
import android.util.Log;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import dev.twozer00.projectm.api.TheMovieDb;
import dev.twozer00.projectm.model.MovieDetails;
import dev.twozer00.projectm.model.Person;
import org.w3c.dom.Text;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

import static dev.twozer00.projectm.utils.Constants.API_BASE_URL;
import static dev.twozer00.projectm.utils.Constants.API_KEY;


public class PeopleDetailsFragment extends Fragment {

    private static final String TAG = "PEOPLE DETAILS FRAGMENT";
    private TextView birthday;
    private TextView deathday;
    private TableRow deathday_container;
    private TextView place_of_birth;
    private TableRow place_of_birth_container;
    private TextView homepage;
    private TextView name;
    private TextView department;
    private TableRow homepage_container;
    private TextView biography;
    private Person person;

    private int person_id;

    public PeopleDetailsFragment(int person_id) {
        this.person_id = person_id;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_people_details, container, false);
        birthday = view.findViewById(R.id.birthday);
        deathday = view.findViewById(R.id.deathday);
        place_of_birth = view.findViewById(R.id.place_of_birth);
        biography = view.findViewById(R.id.biography);
        homepage = view.findViewById(R.id.homepage);
        homepage_container = view.findViewById(R.id.homepage_container);
        place_of_birth_container = view.findViewById(R.id.place_of_birth_container);
        deathday_container = view.findViewById(R.id.deathday_container);
        name = view.findViewById(R.id.name);
        department = view.findViewById(R.id.department);
        loadDetails(person_id);
        return view;
    }

    private void loadDetails(int person_id) {
        Log.d(TAG, "loadDetails: " + person_id);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TheMovieDb movieApi = retrofit.create(TheMovieDb.class);
        Call<Person> call = movieApi.getPersonDetails(person_id,API_KEY);
        call.enqueue(new Callback<Person>() {
            @Override
            public void onResponse(Call<Person> call, Response<Person> response) {
                Log.d("RETROFIT", "onResponse: movieDetails" + response.body().toString() );
                person = response.body();
                bindData(person);
            }

            @Override
            public void onFailure(Call<Person> call, Throwable t) {
                Log.d("RETROFIT", "onFailure: movieDetails" + t.getMessage());
            }
        });
    }

    private void bindData(Person person) {
        name.setText(person.getName());
        if(person.getKnown_for_department()!=null){
            department.setText(person.getKnown_for_department());
        }
        if(person.getBirthday()!=null){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
            Period period = Period.between(LocalDate.now(), LocalDate.parse(person.getBirthday(),formatter));
            int years = Math.abs(period.getYears());
            String birthDay = person.getBirthday();
            TextView textView = getActivity().findViewById(R.id.age);
            //textView.setText(String.valueOf(years));
            birthday.setText(String.format("%s(%s)", person.getBirthday(), years));
        }
        else{
            ((TableRow)birthday.getParent()).setVisibility(View.GONE);
        }
        if (person.getDeathday()!=null){
            deathday.setText(person.getDeathday());
        }
        else{
            deathday_container.setVisibility(View.GONE);
        }
        if (person.getPlace_of_birth()!=null){
            place_of_birth.setText(person.getPlace_of_birth());
        }
        else{
            place_of_birth_container.setVisibility(View.GONE);
        }
        if (person.getBiography()!=null || !person.getBiography().isEmpty()){
            biography.setText(person.getBiography());
        }
        else{
            biography.setText(R.string.empty_fields);
        }
        if (person.getHomepage()!=null){
            homepage.setText(person.getHomepage());
        }
        else{
            homepage_container.setVisibility(View.GONE);
        }
    }

}