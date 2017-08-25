package com.example.amiraahabeeb.ntl_mouvie_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.amiraahabeeb.ntl_mouvie_app.database.data.FavouriteContract;
import com.example.amiraahabeeb.ntl_mouvie_app.database.data.FavouriteDbHelper;
import com.example.amiraahabeeb.ntl_mouvie_app.fragments.DetailActivityFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    JsonParser parser = new JsonParser();
    SharedPreferences.Editor editor;
    final static String api_popular = "http://api.themoviedb.org/3/movie/popular?api_key=580f90d7628d7abbfba2cec11823fbcc";
    final static String api_toprated = "http://api.themoviedb.org/3/movie/top_rated?api_key=580f90d7628d7abbfba2cec11823fbcc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!CheckNetwork.isConnected(getBaseContext())) {
            Toast.makeText(this, "No internet Connection", Toast.LENGTH_SHORT).show();
            return;
        }
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String status = sharedPreferences.getString("staus", api_popular);

        editor = sharedPreferences.edit();
        if (status.equals("-")) {
            fav();
        } else {
            Connect_Async connector = new Connect_Async();
            try {
            /*api_popular).get()*/
                ArrayList<Mouvie_data> arrayList = parser.JsonProcess(connector.execute(status).get(), MainActivity.this, "poster");

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    private void main_grid() {
        GridView gridview = (GridView) findViewById(R.id.gridview);
        //   gridview.setAdapter(new ImageAdapter(this, parser.getlist()));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(getBaseContext(), "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_top_rated) {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            editor = sharedPreferences.edit();
            try {
                if (!CheckNetwork.isConnected(getBaseContext())) {
                    Toast.makeText(this, "No internet Connection", Toast.LENGTH_SHORT).show();
                } else {
                    Connect_Async connector = new Connect_Async();
                    ArrayList<Mouvie_data> arrayList = parser.JsonProcess(connector.execute(api_toprated).get(), MainActivity.this, "poster");
                    editor.putString("staus", api_toprated);
                    editor.commit();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return true;
        }
        if (id == R.id.action_popular) {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            editor = sharedPreferences.edit();
            try {
                if (!CheckNetwork.isConnected(getBaseContext())) {
                    Toast.makeText(this, "No internet Connection", Toast.LENGTH_SHORT).show();

                } else {
                    Connect_Async connector = new Connect_Async();
                    ArrayList<Mouvie_data> arrayList = parser.JsonProcess(connector.execute(api_popular).get(), MainActivity.this, "poster");
                    editor.putString("staus", api_popular);
                    editor.commit();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return true;
        }

        if (id == R.id.action_fav)

        {
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            editor = sharedPreferences.edit();
            editor.putString("staus", "-");
            editor.commit();
            fav();
            return true;
        }

        return super.

                onOptionsItemSelected(item);

    }

    private void fav() {


        String[] column = {FavouriteDbHelper.UID, FavouriteDbHelper.POSTER, FavouriteDbHelper.TITEL, FavouriteDbHelper.DATE, FavouriteDbHelper.VOTE, FavouriteDbHelper.OVERVIEW, FavouriteDbHelper.POSTER_ID};
        Cursor cursor = getContentResolver().query(
                FavouriteContract.FavouriteEntry.CONTENT_URI,   // The content URI of the words table
                column,                        // The columns to return for each row
                null,                   // Selection criteria
                null,                     // Selection criteria
                null);

        final List<Mouvie_data> movieDataList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                String POSTER = cursor.getString(1);
                String TITEL = cursor.getString(2);
                String DATE = cursor.getString(3);
                String VOTE = cursor.getString(4);
                String OVERVIE = cursor.getString(5);
                String poster_id = cursor.getInt(6) + "";
                movieDataList.add(new Mouvie_data(POSTER, OVERVIE, DATE, TITEL, VOTE, poster_id));
            } while (cursor.moveToNext());
        }
        ImageAdapter adapter = new ImageAdapter(getBaseContext(), movieDataList);
        GridView imageGridView = (GridView) findViewById(R.id.gridview);
        imageGridView.setAdapter(adapter);
        imageGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getBaseContext(), DetailActivity.class);
                DetailActivityFragment.model = movieDataList.get(position);
                startActivity(i);
            }
        });
    }

}
