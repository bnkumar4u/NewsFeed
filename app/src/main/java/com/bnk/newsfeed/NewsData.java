package com.bnk.newsfeed;

import android.graphics.Bitmap;

class NewsData
{
    private String headlines,webUrl;
    private Bitmap imgUrl =null;

    public NewsData(String headlines, String webUrl) {
        this.headlines = headlines;
        this.webUrl = webUrl;
    }

    public NewsData(String headlines, String webUrl, Bitmap imgUrl) {
        this.headlines = headlines;
        this.webUrl = webUrl;
        this.imgUrl = imgUrl;
    }

    public String getHeadlines() {
        return headlines;
    }

    public Bitmap getImgBitmap() {
        return imgUrl;
    }

    public String getWebUrl() {
        return webUrl;
    }
}
