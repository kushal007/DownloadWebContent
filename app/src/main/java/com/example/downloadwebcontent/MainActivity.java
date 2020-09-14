package com.example.downloadwebcontent;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    ImageView down_image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        down_image = (ImageView)findViewById(R.id.imageView);




        //DownloadTask task = new DownloadTask();
        //String result = "";
//        try {
//            result = task.execute("https://google.com").get();
//            Log.i("Output", result);
//        }
//        catch (Exception e)
//        {
//            Log.i("Problem", "Exception occurred while async");
//        }


        setContentView(R.layout.activity_main);
    }

    public void downloadImage(View view)
    {
        down_image = (ImageView)findViewById(R.id.imageView);
        Log.i("Test","button pressed");
        DownloadingImage di = new DownloadingImage();
        try {
            Bitmap myImage = di.execute("https://upload.wikimedia.org/wikipedia/en/a/aa/Bart_Simpson_200px.png").get();
            Log.i("Output", "Downloading has started");
            if(myImage == null)
                Log.i("Out", "returned null");
            else
                down_image.setImageBitmap(myImage);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    public class DownloadingImage extends AsyncTask<String,Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... strings) {
            try {

                URL url = new URL(strings[0]);
                Log.i("Url is", String.valueOf(url)) ;
                HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                urlConnection.connect();
                InputStream in = urlConnection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(in);
                return myBitmap;
            }
            catch (Exception e)
            {
                Toast.makeText(MainActivity.this, "Can't download image", Toast.LENGTH_SHORT).show();
                return null;
            }

        }
    }
    public class DownloadTask extends AsyncTask<String, Void,String>{



        @Override
        protected String doInBackground(String... strings) {
            String result= "";
            URL url;
            HttpURLConnection urlConnection;
            try{
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                InputStream in = urlConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while(data!= -1)
                {
                    char c = (char) data;
                    result +=c;
                    data = reader.read();
                }
                return result;
            }
            catch (Exception e)
            {
                return "Failed" ;
            }
            //return result;
        }
    }

}