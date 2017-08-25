package com.example.amiraahabeeb.ntl_mouvie_app;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Amira A. habeeb on 24/04/2017.
 */
public class review_adapter extends BaseAdapter {

    List<String> review_data;
    public static Context mContext;

    TextView txt_item_review;
    String status;

    public review_adapter(Context mContext, List<String> review_data, String status) {
        this.status = status;
        this.review_data = review_data;
    }

    public int getCount() {
        return review_data.size();
    }

    public Object getItem(int position) {
        return review_data.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((LayoutInflater) (mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE))).inflate(R.layout.item_review, parent, false);
        }
        txt_item_review = (TextView) convertView.findViewById(R.id.txt_item_review);
        if (status.equals("trailer")) {

            txt_item_review.setText("Trailer" + (position + 1));
        } else {
            txt_item_review.setText(review_data.get(position));
        }

        return convertView;
    }
}

