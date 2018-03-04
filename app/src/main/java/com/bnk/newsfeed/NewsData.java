package com.bnk.newsfeed;

class NewsData
{
    private String headlines,webUrl,date;
    private String imgUrl =null;
    public NewsData(String headlines, String webUrl, String imgUrl,String date) {
        this.headlines = headlines;
        this.webUrl = webUrl;
        this.imgUrl = imgUrl;
        this.date=date;
    }

    public String getDate() {
        return date;
    }

    public String getHeadlines() {
        return headlines;
    }


    public String getImgUrl() {
        return imgUrl;
    }

    public String getWebUrl() {
        return webUrl;
    }
}
