package com.example.android.newsapplication;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    /**
     * Tag for the log messages
     */
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of {@link News} objects that has been built up from
     * parsing a JSON response.
     */

    public static ArrayList<News> extractFeatureFromJson(String newsJson) {

        //If the String json is empty then return early
        if (TextUtils.isEmpty(newsJson)) {
            return null;
        }

        //Create an empty ArrayList that we can add news to
        ArrayList<News> news = new ArrayList<>();

        //Try to parse the Json and if the is a problem the JSONException will be Thrown.
        //It will be then catch in the catch block, so the app doesn't crach
        try {

            //Build a is of news data with the correspondent data
            JSONObject rootJson = new JSONObject(newsJson);

            //One child down to get to the object interested in
            JSONObject response = rootJson.getJSONObject("response");

            //Get the instance of the object that contains our data
            JSONArray result = response.getJSONArray("results");

            //Iterate through the data to get our data we need
            for (int i = 0; i < result.length(); i++) {
                JSONObject jsonObject = result.getJSONObject(i);

                String type = jsonObject.getString("type");
                String section = jsonObject.getString("sectionName");
                String date = jsonObject.getString("webPublicationDate");
                String url = jsonObject.getString("webUrl");
                String overview = jsonObject.getString("webTitle");
                String pillar = jsonObject.getString("pillarName");
                String authorName = "";

                //Go to the tags
                JSONArray tags = jsonObject.getJSONArray("tags");

                //Go through the tag array to get author's info
                for (int f = 0; f < tags.length(); f++) {
                    JSONObject jsonObject1 = tags.getJSONObject(f);

                    String firstName = jsonObject1.getString("firstName");
                    String lastName = jsonObject1.getString("lastName");

                    authorName = firstName + " " + lastName;
                }

                news.add(new News(type, section, date, url, overview, pillar + ": ", authorName));
            }

        } catch (JSONException e) {
            //If there is a problem parsinthe Json object print this message
            Log.e("QueryUtils", "Error parsing the News Json object");
        }
        //Return a list of news
        return news;
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;

        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ");
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHTTPRequest(URL url) throws IOException {
        String jsonResponse = "";

        //if the url is null then return early
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "MakeHTTPRequest: Problem retrieving data from JSON result ", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Query the guardian dataset and return a list of {@link News} objects.
     */
    public static List<News> fetchNewsData(String requestUrl) {

        //Create a URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;

        try {
            jsonResponse = makeHTTPRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        //Extract the data need from the json response
        List<News> news = extractFeatureFromJson(jsonResponse);

        //Return the list
        return news;
    }
}
