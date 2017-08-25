package com.example.amiraahabeeb.ntl_mouvie_app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
/*
    ImageView img;
    TextView txt1, txt2, txt3, txt4;
    Button favorite;
    ListView review;
    JsonParser parser = new JsonParser();
    static Activity context;

    static Mouvie_data model;
*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        review_adapter.mContext = DetailActivity.this;
       /* img = (ImageView) findViewById(R.id.poster_image);
        txt1 = (TextView) findViewById(R.id.title_a);
        txt2 = (TextView) findViewById(R.id.date);
        txt3 = (TextView) findViewById(R.id.vote_average);
        txt4 = (TextView) findViewById(R.id.original);
        Picasso.with(getBaseContext()).load("http://image.tmdb.org/t/p/w185/" + model.poster_path).error(R.drawable.sample_0).into(img);
        txt1.setText(model.getOriginal_title());
        txt2.setText(model.getRelease_date());
        txt3.setText(model.getPopularity());
        txt4.setText(model.getOverview());
        context = DetailActivity.this;

        //review= (ListView) findViewById(R.id.reviews_list);
        Connect_Async connector = new Connect_Async();
        try {
            *//*api_popular).get()*//*
            parser.JsonProcess(connector.execute("http://api.themoviedb.org/3/movie/" + model.getid() + "/videos?api_key=580f90d7628d7abbfba2cec11823fbcc").get(), DetailActivity.this, "movie");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

*/
    }

   /* public static void runReviews() throws ExecutionException, InterruptedException {
        JsonParser parser = new JsonParser();
        Connect_Async connector = new Connect_Async();
        parser.JsonProcess(connector.execute("http://api.themoviedb.org/3/movie/" + model.getid() + "/reviews?api_key=580f90d7628d7abbfba2cec11823fbcc").get(), context, "review");
    }*/
}
