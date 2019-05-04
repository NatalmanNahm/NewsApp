package com.example.android.newsapplication;

public class News {

    /**
     * Initializing the type of news
     */
    private String mType;

    /**
     * Initializing the section of the news
     */
    private String mSection;

    /**
     * Initializing the date it was published
     */
    private String mDate;

    /**
     * Initializing the overview of the article
     */
    private String mOverview;

    /**
     * Initializing the url to the web page
     */
    private String mUrl;

    /**
     * Initializing the pillar name
     */
    private String mPilarName;

    /**
     * Initializing the Author's Name
     */
    private String mAuthorName;

    /**
     * Creating a constructor for the class News
     *
     * @param type
     * @param section
     * @param date
     * @param overview
     * @param url
     * @param pilarname
     * @param author
     */
    public News(String type, String section, String date, String url,
                String overview, String pilarname, String author) {
        mType = type;
        mSection = section;
        mDate = date;
        mOverview = overview;
        mUrl = url;
        mPilarName = pilarname;
        mAuthorName = author;
    }

    /**
     * Creating getters for all the initialized variables
     */
    public String getmType() {
        return mType;
    }

    public String getmSection() {
        return mSection;
    }

    public String getmDate() {
        return mDate;
    }

    public String getmOverview() {
        return mOverview;
    }

    public String getmUrl() {
        return mUrl;
    }

    public String getmPilarName() {
        return mPilarName;
    }

    public String getmAuthorName() {
        return mAuthorName;
    }
}
