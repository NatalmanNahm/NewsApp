package com.example.android.newsapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.content.AsyncTaskLoader;

import java.util.List;

/**
 * Loads a list of News by using an AsyncTask to perform the
 * network request to the given URL.
 */

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    /**
     * Query URL
     */
    private String mUrl;

    /**
     * Constructs a new {@link News}.
     */
    public NewsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<News> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of news.
        List<News> news = QueryUtils.fetchNewsData(mUrl);
        return news;
    }
}
