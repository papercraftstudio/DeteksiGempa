package com.dytra.deteksigempa;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import java.io.FileNotFoundException;


public class MainActivity extends Activity {
    private TextView TvWilayah, TvWilayah2, TvMagnitude, TvKedalaman, TvTanggal, TvJam, TvKoordinat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("StackSites", "OnCreate()");
        setContentView(R.layout.list_main);


        TvWilayah.setText("Selamat Datang! ");
        TvWilayah2.setText("Klik list untuk melihat detail gempa");

        TvMagnitude.setVisibility(View.INVISIBLE);
        TvKedalaman.setVisibility(View.INVISIBLE);
        TvTanggal.setVisibility(View.INVISIBLE);
        TvJam.setVisibility(View.INVISIBLE);
        TvKoordinat.setVisibility(View.INVISIBLE);

        final Window window = getWindow();


        if (isNetworkAvailable()) {
            Log.i("StackSites", "starting download Task");
            SitesDownloadTask download = new SitesDownloadTask();
            download.execute();
        }

    }

    //Helper method to determine if Internet connection is available.
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /*
     * AsyncTask that will download the xml file for us and store it locally.
     * After the download is done we'll parse the local file.
     */
    private class SitesDownloadTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg0) {
            //Download the file
            try {
                Downloader.DownloadFromUrl("http://data.bmkg.go.id/gempaterkini.xml", openFileOutput("datagempa.xml", Context.MODE_PRIVATE));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}
