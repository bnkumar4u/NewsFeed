package com.bnk.newsfeed;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import java.util.ArrayList;

import static com.bnk.newsfeed.MainActivity.LOG_TAG;

public final class QueryUtils
{
    private QueryUtils(){}


    public static ArrayList<NewsData> fetchNewsData(String requestedUrl) {

        URL url=createUrl(requestedUrl);

        String jsonREsponse = null;
        try{
            jsonREsponse= makeHttpRequest(url);
        }catch (IOException e){
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }


        ArrayList<NewsData> news = extractFeatureFromJson(jsonREsponse);


        return news;
    }

    private static ArrayList<NewsData> extractFeatureFromJson(String newsJson) {

        if(TextUtils.isEmpty(newsJson))
            return null;



        ArrayList<NewsData> news =new ArrayList<>();

        try{
            JSONObject baseJsonObject= new JSONObject(newsJson);



            JSONArray articles=baseJsonObject.getJSONArray("articles");

            for(int i =0 ;i<articles.length();i++)
            {
                JSONObject currentNews=articles.getJSONObject(i);

                String webTitle=currentNews.getString("title");
                String webUrl=currentNews.getString("url");
                String imgUrl=currentNews.getString("urlToImage");

                Bitmap bitmap=downloadBitmap(imgUrl);

                news.add(new NewsData(webTitle,webUrl,bitmap));
            }




        }catch (JSONException e){
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }


        return news;
    }

    private static String makeHttpRequest(URL url)  throws IOException
    {
        String jsonResponse="";
        if(url==null)
            return jsonResponse;

        HttpURLConnection urlConnection =null;
        InputStream inputStream = null;

        try{
            urlConnection=(HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(1000000);
            urlConnection.setConnectTimeout(1500000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode()==200)
            {
                inputStream = urlConnection.getInputStream();
                jsonResponse=readFromStream(inputStream);
            }else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        }catch (IOException e){
            Log.e(LOG_TAG,"Problem retrieving the earthquake JSON results.", e);
        }finally {
            if(urlConnection!=null)
                urlConnection.disconnect();
            if(inputStream!=null)
                inputStream.close();
        }

        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output =new StringBuilder();

        if(inputStream!=null)
        {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader= new BufferedReader(inputStreamReader);
            String line =reader.readLine();
            while(line!=null)
            {
                output.append(line);
                line=reader.readLine();
            }
        }
        return output.toString();
    }

    private static URL createUrl(String requestedUrl)
    {
        URL url=null;
        try{
            url=new URL(requestedUrl);
        }catch (MalformedURLException e){
            Log.e(LOG_TAG,"Problem buiding url",e);
        }
        return url;
    }
    private static Bitmap downloadBitmap(String url) {
        HttpURLConnection urlConnection = null;
        try {
            URL uri = new URL(url);
            urlConnection = (HttpURLConnection) uri.openConnection();
            int statusCode = urlConnection.getResponseCode();
            int x=statusCode;
            if (statusCode != 200) {
                return null;
            }

            InputStream inputStream = urlConnection.getInputStream();
            if (inputStream != null) {
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }
        } catch (Exception e) {
            Log.w("ImageDownloader", "Error downloading image from " + url,e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return null;
    }
}
