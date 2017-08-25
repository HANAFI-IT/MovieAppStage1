package com.example.amiraahabeeb.ntl_mouvie_app.fragments;

import android.app.Activity;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.amiraahabeeb.ntl_mouvie_app.Connect_Async;
import com.example.amiraahabeeb.ntl_mouvie_app.JsonParser;
import com.example.amiraahabeeb.ntl_mouvie_app.Mouvie_data;
import com.example.amiraahabeeb.ntl_mouvie_app.R;
import com.example.amiraahabeeb.ntl_mouvie_app.database.data.FavouriteContract;
import com.example.amiraahabeeb.ntl_mouvie_app.database.data.FavouriteDbHelper;
import com.squareup.picasso.Picasso;

import java.util.concurrent.ExecutionException;

/**
 * Created by Amira A. habeeb on 08/05/2017.
 */
public class DetailActivityFragment extends Fragment {
    public static View view;
    ImageView img;
    TextView txt1, txt2, txt3, txt4;
    JsonParser parser = new JsonParser();
    public static Activity context;

    public static Mouvie_data model;
    public static ListView movie;
    public static ListView review;
    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_detail, container, false);
        img = (ImageView) view.findViewById(R.id.poster_image);
        txt1 = (TextView) view.findViewById(R.id.title_a);
        txt2 = (TextView) view.findViewById(R.id.date);
        txt3 = (TextView) view.findViewById(R.id.vote_average);
        txt4 = (TextView) view.findViewById(R.id.original);
        Picasso.with(getContext()).load("http://image.tmdb.org/t/p/w185/" + model.getPoster_path()).error(R.drawable.sample_0).into(img);
        txt1.setText(model.getOriginal_title());
        txt2.setText(model.getRelease_date());
        txt3.setText(model.getPopularity());
        txt4.setText(model.getOverview());
        context = getActivity();
        movie = (ListView) view.findViewById(R.id.trailer_list);
        review = (ListView) view.findViewById(R.id.reviews_list);
        //review= (ListView) view.findViewById(R.id.reviews_list);
        Connect_Async connector = new Connect_Async();
        try {
            /*api_popular).get()*/
            parser.JsonProcess(connector.execute("http://api.themoviedb.org/3/movie/" + model.getid() + "/videos?api_key=580f90d7628d7abbfba2cec11823fbcc").get(), context, "movie");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        view.findViewById(R.id.Favorite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favBtn();
            }
        });
        return view;
    }
    public static void runReviews() throws ExecutionException, InterruptedException {
        JsonParser parser = new JsonParser();
        Connect_Async connector = new Connect_Async();
        parser.JsonProcess(connector.execute("http://api.themoviedb.org/3/movie/" + model.getid() + "/reviews?api_key=580f90d7628d7abbfba2cec11823fbcc").get(), context, "review");
    }
    public static void favBtn() {

        ContentValues values = new ContentValues();
        values.put(FavouriteDbHelper.POSTER, model.getPoster_path());
        values.put(FavouriteDbHelper.TITEL, model.getOriginal_title());
        values.put(FavouriteDbHelper.DATE, model.getRelease_date());
        values.put(FavouriteDbHelper.VOTE, model.getPopularity());
        values.put(FavouriteDbHelper.OVERVIEW, model.getOverview());
        values.put(FavouriteDbHelper.POSTER_ID, model.getid());

        Uri uri = context.getContentResolver().insert(FavouriteContract.FavouriteEntry.CONTENT_URI, values);


    }

}
