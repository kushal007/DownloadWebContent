package com.example.downloadwebcontent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    public class DownloadTask extends AsyncTask<String, Void,String>{



        @Override
        protected String doInBackground(String... strings) {
            String result= "";
            URL url;
            HttpURLConnection urlConnection;
            try{
                url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DownloadTask task = new DownloadTask();
        String result = "";
        try {
            result = task.execute("https://google.com").get();
            Log.i("Output", result);
        }
        catch (Exception e)
        {
            Log.i("Problem", "Exception occurred while async");
        }
        setContentView(R.layout.activity_main);
    }
}