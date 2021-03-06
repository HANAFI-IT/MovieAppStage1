package com.example.amiraahabeeb.ntl_mouvie_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Amira A. habeeb on 30/03/2017.
 */
public class ImageAdapter extends BaseAdapter {

    List<Mouvie_data> mouvie_data;
    private Context mContext;

    public ImageAdapter(Context c, List<Mouvie_data> mouvie_data) {
        mContext = c;
        this.mouvie_data = mouvie_data;
    }

    public int getCount() {
        return mouvie_data.size();
    }

    public Object getItem(int position) {
        return mouvie_data.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.item_movie,parent,false);
        }
        ImageView imgView = (ImageView) convertView.findViewById(R.id.img_item_poster);

        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w185/" + mouvie_data.get(position).getPoster_path()).into(imgView);
        //  imageView.setImageResource(mThumbIds[position]);
        return convertView;
    }

    // references to our images
    // private Integer[] mThumbIds = {
    //   R.drawable.sample_2, R.drawable.sample_3,
    //   R.drawable.sample_4, R.drawable.sample_5,
    //   R.drawable.sample_6, R.drawable.sample_7,
    //    R.drawable.sample_0, R.drawable.sample_1,
    //    R.drawable.sample_2, R.drawable.sample_3,
    //    R.drawable.sample_4, R.drawable.sample_5,
    //    R.drawable.sample_6, R.drawable.sample_7,
    //   R.drawable.sample_0, R.drawable.sample_1,
    //   R.drawable.sample_2, R.drawable.sample_3,
    //   R.drawable.sample_4, R.drawable.sample_5,
    //    R.drawable.sample_6, R.drawable.sample_7
    //  };
}
