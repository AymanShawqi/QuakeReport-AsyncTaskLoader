package com.example.android.quakereportasynctaskloader;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

public class EarthquakeLoader extends AsyncTaskLoader<List<EarthQuake>> {

    private static final String LOG_TAG = EarthquakeLoader.class.getName();

    private String mUrl;

    public EarthquakeLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<EarthQuake> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        List<EarthQuake> result = QueryUtils.fetchEarthquakeData(mUrl);
        return result;
    }
}
