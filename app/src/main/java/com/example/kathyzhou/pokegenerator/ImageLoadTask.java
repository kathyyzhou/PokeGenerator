package com.example.kathyzhou.pokegenerator;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * loads the image in the background of the app
 */
public class ImageLoadTask extends AsyncTask<Void, Void, Bitmap> {

    /**
     * the url of the image
     */
    private String url;
    /**
     * the image view
     */
    private ImageView imageView;

    /**
     * constructor
     * @param url the url of the image
     * @param imageView the imageView
     */
    public ImageLoadTask(String url, ImageView imageView) {
        this.url = url;
        this.imageView = imageView;
    }

    /**
     * loads image in background
     * @param params the image url
     * @return the loaded or null if no image is loaded
     */
    @Override
    protected Bitmap doInBackground(Void... params) {
        try {
            //sets up a URL connection
            URL urlConnection = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlConnection
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            //creates a bitMap from the input
            Bitmap myBitmap = BitmapFactory.decodeStream(input);

            return myBitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * sets the image to the imageView
     * @param result the loaded image
     */
    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        imageView.setImageBitmap(result);
    }

}
