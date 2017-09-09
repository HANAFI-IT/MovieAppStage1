package com.example.amiraahabeeb.ntl_mouvie_app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.amiraahabeeb.ntl_mouvie_app.fragments.DetailActivityFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Amira A. habeeb on 30/03/2017.
 */
public class JsonParser {

    ArrayList<Mouvie_data> data;
    List<String> movie_url;
    List<String> reviews;



    public ArrayList<Mouvie_data> JsonProcess(String jsonFile, final Activity context, String state) {

        if (state.equals("movie")) {
            movie_url = new ArrayList<>();

            try {

                JSONObject mObject = new JSONObject(jsonFile);

                JSONArray jsonArray = mObject.getJSONArray("results");

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject mainObjectArray = jsonArray.getJSONObject(i);

                    //  Mouvie_data mouvie_encap_data = new Mouvie_data(mainObjectArray.getString(Key_tags.poster_path),
                    //      mainObjectArray.getString(Key_tags.overview), mainObjectArray.getString(Key_tags.release_date), mainObjectArray.getString(Key_tags.original_title),mainObjectArray.getString(Key_tags.popularity),mainObjectArray.getString(Key_tags.id));
                    movie_url.add(mainObjectArray.getString("id"));
                }
                //  data.add(mouvie_encap_data);

                review_adapter review_adapter = new review_adapter(context, movie_url, "trailer");
                DetailActivityFragment.movie.setAdapter(review_adapter);
                DetailActivityFragment.movie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + movie_url.get(position)));
                        context.startActivity(intent);
                    }
                });
                DetailActivityFragment.runReviews();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        if (state.equals("poster")) {
            data = new ArrayList<>();


            try {

                JSONObject mainObject = new JSONObject(jsonFile);

                JSONArray jsonArray = mainObject.getJSONArray("results");

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject mainObjectArray = jsonArray.getJSONObject(i);

                    Mouvie_data mouvie_encap_data = new Mouvie_data(mainObjectArray.getString(Key_tags.poster_path),
                            mainObjectArray.getString(Key_tags.overview), mainObjectArray.getString(Key_tags.release_date), mainObjectArray.getString(Key_tags.original_title), mainObjectArray.getString(Key_tags.popularity), mainObjectArray.getString(Key_tags.id));

                    data.add(mouvie_encap_data);

                }
                ImageAdapter adapter = new ImageAdapter(context, data);
                GridView imageGridView = (GridView) context.findViewById(R.id.gridview);
                imageGridView.setAdapter(adapter);
                imageGridView.setSelection(MainActivity.position);
                imageGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i = new Intent(context, DetailActivity.class);
                        DetailActivityFragment.model = data.get(position);
                        context.startActivity(i);
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (state.equals("review")) {
            reviews = new ArrayList<>();

            try {

                JSONObject mObject = new JSONObject(jsonFile);

                JSONArray jsonArray = mObject.getJSONArray("results");

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject mainObjectArray = jsonArray.getJSONObject(i);

                    //  Mouvie_data mouvie_encap_data = new Mouvie_data(mainObjectArray.getString(Key_tags.poster_path),
                    //      mainObjectArray.getString(Key_tags.overview), mainObjectArray.getString(Key_tags.release_date), mainObjectArray.getString(Key_tags.original_title),mainObjectArray.getString(Key_tags.popularity),mainObjectArray.getString(Key_tags.id));
                    reviews.add(mainObjectArray.getString("content"));
                }
                //  data.add(mouvie_encap_data);

                review_adapter review_adapter = new review_adapter(context, reviews, "review");
                DetailActivityFragment.review.setAdapter(review_adapter);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return data;
    }
}