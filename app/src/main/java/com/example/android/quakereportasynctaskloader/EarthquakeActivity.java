package com.example.android.quakereportasynctaskloader;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<EarthQuake>> {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    private static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=1&limit=50";

    private static final int EARTHQUAKE_LOADER_ID = 1;
    // private  ArrayList<EarthQuake> earthquakes;
    private EarthQuakeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        ListView list = (ListView) findViewById(R.id.list);
        mAdapter = new EarthQuakeAdapter(this, new ArrayList<EarthQuake>());
        list.setAdapter(mAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EarthQuake earthQuake = mAdapter.getItem(position);
                Uri earthQuakeUri = Uri.parse(earthQuake.getUrl());
                Intent webIntent = new Intent(Intent.ACTION_VIEW, earthQuakeUri);
                startActivity(webIntent);
            }
        });

        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.restartLoader(EARTHQUAKE_LOADER_ID, null, this);
    }


    @Override
    public Loader<List<EarthQuake>> onCreateLoader(int id, Bundle args) {
        return new EarthquakeLoader(this, USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<EarthQuake>> loader, List<EarthQuake> earthQuakes) {
        mAdapter.clear();
        if (earthQuakes != null && !earthQuakes.isEmpty())
            mAdapter.addAll(earthQuakes);
    }

    @Override
    public void onLoaderReset(Loader<List<EarthQuake>> loader) {
        mAdapter.clear();
    }
}
