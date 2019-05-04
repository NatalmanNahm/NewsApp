package com.example.android.newsapplication;

import android.app.Activity;

import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

public class NewsAdapter extends ArrayAdapter<News> {

    // to separate the string in 2 different date and time
    private static final String DATE_SEPARATOR = "T";

    /**
     * Creating a constructor for the news adapter
     */
    public NewsAdapter(Activity context, ArrayList<News> news) {
        super(context, 0, news);
    }

    //Getting the right color for type
    private int getRightTypeColor(String type) {
        int typeColorResource;

        if (type.equalsIgnoreCase("article")) {
            typeColorResource = R.color.colorArticle;
        } else if (type.equalsIgnoreCase("liveBlog")) {
            typeColorResource = R.color.liveBlog;
        } else if (type.equalsIgnoreCase("crossword")) {
            typeColorResource = R.color.crossword;
        } else {
            typeColorResource = R.color.colorArticle;
        }

        return ContextCompat.getColor(getContext(), typeColorResource);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Check if the existing view is being reused, otherwise inflate the view
        View listView = convertView;
        if (listView == null) {
            listView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_list, parent, false);
        }

        //Initializing the new date
        String theDate;

        //Get the object located at this position in the list
        News currentPosition = getItem(position);

        //Getting the type of the new from the news_list.xml
        TextView type = (TextView) listView.findViewById(R.id.type);

        //set new type to the textView
        type.setText(currentPosition.getmType());

        //Setting the right color for the type
        GradientDrawable typeCircle = (GradientDrawable) type.getBackground();

        // Get the appropriate background color based on the current news type
        int typeCol = getRightTypeColor(currentPosition.getmType());

        //Set the proper color on the type circle
        typeCircle.setColor(typeCol);

        //Getting the section of the new from the news_list.xml
        TextView section = (TextView) listView.findViewById(R.id.sectionName);

        //Set the new section to the TextView
        section.setText(currentPosition.getmSection());

        //Getting the date of the new from the news_list.xml
        TextView date = (TextView) listView.findViewById(R.id.date);

        //Getting the complete date
        String fullDate = currentPosition.getmDate();

        //Splitting the date into two
        if (fullDate.contains(DATE_SEPARATOR)) {
            String[] parts = fullDate.split(DATE_SEPARATOR);
            theDate = parts[0];
        } else {
            theDate = fullDate;
        }

        //Set new date it was published
        date.setText(theDate);

        //Getting the overview of the new from the news_list.xml
        TextView overview = (TextView) listView.findViewById(R.id.overview);

        //Setting the right overview to the right textview
        overview.setText(currentPosition.getmOverview());

        //Getting the pillar Name of the new from the news_list.xml
        TextView pillar = (TextView) listView.findViewById(R.id.pillarName);

        //Set The new content type
        pillar.setText(currentPosition.getmPilarName());

        //Getting the Name of the news from the news_list.xml
        TextView author = (TextView) listView.findViewById(R.id.authorName);

        //setting the new Author's Name to the appropriate textView
        author.setText(currentPosition.getmAuthorName());


        return listView;
    }
}
