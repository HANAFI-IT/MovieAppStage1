package com.example.amiraahabeeb.ntl_mouvie_app;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.amiraahabeeb.ntl_mouvie_app.fragments.DetailActivityFragment;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by Mahmoud Sadek on 2/16/2017.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    private final List<Mouvie_data> movieList;
    View itemView;
    private Context mContext;
    private int lastPosition = -1;


    public MoviesAdapter(Context mContext, List<Mouvie_data> movieList) {
        this.mContext = mContext;
        this.movieList = movieList;
        if (!movieList.isEmpty()) {
            DetailActivityFragment.model = movieList.get(0);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final Mouvie_data movie_item = movieList.get(position);

        holder.txt_title.setText(movie_item.getOriginal_title());
        String baseUrl = "http://image.tmdb.org/t/p/w185";
        Picasso.with(mContext).load(baseUrl + movie_item.getPoster_path()).into(holder.img_poster);

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(mContext, DetailActivity.class);
                DetailActivityFragment.model = movieList.get(position);
                mContext.startActivity(i);
            }
        });

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_title;
        ImageView img_poster;
        CardView mCardView;

        public MyViewHolder(View view) {
            super(view);
            img_poster = (ImageView) view.findViewById(R.id.img_item_poster);
            mCardView = (CardView) view.findViewById(R.id.card_view);

        }
    }


}