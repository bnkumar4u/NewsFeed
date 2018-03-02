package com.bnk.newsfeed;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class NewsAdapter extends ArrayAdapter{

    public NewsAdapter(@NonNull Context context, @NonNull List objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView=convertView;

        if(listItemView==null)
        {
            listItemView= LayoutInflater.from(getContext()).inflate(

                    R.layout.simple_list_view,parent,false);
        }
        NewsData currentNews=(NewsData)getItem(position);

        ImageView imageView=listItemView.findViewById(R.id.image) ;

        Bitmap bitmap=currentNews.getImgBitmap();
        if(bitmap!=null)
             imageView.setImageBitmap(bitmap);
        else
            imageView.setImageBitmap(null);

        TextView hl=listItemView.findViewById(R.id.tv);

        hl.setText(currentNews.getHeadlines());

        return listItemView;
    }

//    class ImageDownloaderTask extends AsyncTask<String, Void, Bitmap> {
//        private final WeakReference<ImageView> imageViewReference;
//
//        public ImageDownloaderTask(ImageView imageView) {
//            imageViewReference = new WeakReference<ImageView>(imageView);
//        }
//
//        @Override
//        protected Bitmap doInBackground(String... params) {
//            return downloadBitmap(params[0]);
//        }
//
//        @Override
//        protected void onPostExecute(Bitmap bitmap) {
//            if (isCancelled()) {
//                bitmap = null;
//            }
//
//            if (imageViewReference != null) {
//                ImageView imageView = imageViewReference.get();
//                if (imageView != null) {
//                    if (bitmap != null) {
//                        imageView.setImageBitmap(bitmap);
//                    } else {
//                        Drawable placeholder = imageView.getContext().getResources().getDrawable(R.mipmap.news_app_logo);
//                        imageView.setImageDrawable(placeholder);
//                    }
//                }
//            }
//        }
//    }
//    private Bitmap downloadBitmap(String url) {
//        HttpURLConnection urlConnection = null;
//        try {
//            URL uri = new URL(url);
//            urlConnection = (HttpURLConnection) uri.openConnection();
//            int statusCode = urlConnection.getResponseCode();
//            int x=statusCode;
//            if (statusCode != 200) {
//                return null;
//            }
//
//            InputStream inputStream = urlConnection.getInputStream();
//            if (inputStream != null) {
//                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                return bitmap;
//            }
//        } catch (Exception e) {
//            urlConnection.disconnect();
//            Log.w("ImageDownloader", "Error downloading image from " + url,e);
//        } finally {
//            if (urlConnection != null) {
//                urlConnection.disconnect();
//            }
//        }
//        return null;
//    }
}
