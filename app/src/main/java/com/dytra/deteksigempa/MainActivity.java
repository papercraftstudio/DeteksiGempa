package com.dytra.deteksigempa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.plattysoft.leonids.ParticleSystem;

import java.io.FileNotFoundException;


public class MainActivity extends Activity {

    private SitesAdapter mAdapter;
    private SwipeMenuListView sitesList;
    private TextView TvWilayah, TvWilayah2, TvMagnitude, TvKedalaman, TvTanggal, TvJam, TvKoordinat;
    private ImageView wind_icon, wind_dir_icon, thermo_icon, humid_icon;
    private View view;
    private ParticleSystem raining;
    private String weather_status="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("StackSites", "OnCreate()");
        setContentView(R.layout.activity_scrolling);

        //Get reference to our ListView
        sitesList = findViewById(R.id.sitesList);
        ViewCompat.setNestedScrollingEnabled(sitesList, true);


        TvWilayah = findViewById(R.id.wilayah_tv);
        TvWilayah2 = findViewById(R.id.wilayah2_tv);
        TvMagnitude = findViewById(R.id.magnitude_tv);
        TvKedalaman = findViewById(R.id.kedalaman_tv);
        TvTanggal = findViewById(R.id.tanggal_tv);
        TvJam = findViewById(R.id.jam_tv);
        TvKoordinat = findViewById(R.id.koordinat_tv);

        thermo_icon = findViewById(R.id.img_thermo);
        humid_icon = findViewById(R.id.img_humid);
        wind_icon = findViewById(R.id.img_wind);
        wind_dir_icon = findViewById(R.id.img_windir);

        TvWilayah.setText("Selamat Datang! ");
        TvWilayah2.setText("Klik list untuk melihat detail gempa");

        TvMagnitude.setVisibility(View.INVISIBLE);
        TvKedalaman.setVisibility(View.INVISIBLE);
        TvTanggal.setVisibility(View.INVISIBLE);
        TvJam.setVisibility(View.INVISIBLE);
        TvKoordinat.setVisibility(View.INVISIBLE);



        thermo_icon.setVisibility(View.INVISIBLE);
        humid_icon.setVisibility(View.INVISIBLE);
        wind_icon.setVisibility(View.INVISIBLE);
        wind_dir_icon.setVisibility(View.INVISIBLE);

        view = this.getWindow().getDecorView();
        view.setBackgroundResource(R.drawable.gradient_hrain);

        final Window window = getWindow();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(getColor(R.color.colorHRain));
        } else {
            window.setStatusBarColor(getResources().getColor(R.color.colorRainDark));
        }


        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                openItem.setBackground(new ColorDrawable(Color.TRANSPARENT));
                openItem.setWidth(200);
                openItem.setTitle("Open");
                openItem.setIcon(R.drawable.icmarker);
                menu.addMenuItem(openItem);
            }
        };

        sitesList.setMenuCreator(creator);

        //Set the click listener to launch the browser when a row is clicked.
        sitesList.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int pos,long id) {
                TvMagnitude.setVisibility(View.VISIBLE);
                TvKedalaman.setVisibility(View.VISIBLE);
                TvTanggal.setVisibility(View.VISIBLE);
                TvJam.setVisibility(View.VISIBLE);
                TvKoordinat.setVisibility(View.VISIBLE);

                thermo_icon.setVisibility(View.VISIBLE);
                humid_icon.setVisibility(View.VISIBLE);
                wind_icon.setVisibility(View.VISIBLE);
                wind_dir_icon.setVisibility(View.VISIBLE);

                String wilayahraw = mAdapter.getItem(pos).getWilayah();
                String magnitude = mAdapter.getItem(pos).getMagnitude();

                String[] separate = wilayahraw.split(" ");
                String numkm = separate[0].trim();
                String km = separate[1].trim();
                String arahmataangin = separate[2].trim();
                String wilayah = separate[3].trim();

                TvWilayah.setText(numkm+" "+km+" "+arahmataangin);
                TvWilayah2.setText(wilayah);

                TvMagnitude.setText(magnitude);

                String kedalaman = mAdapter.getItem(pos).getKedalaman();
                String tanggal = mAdapter.getItem(pos).getTanggal();
                TvKedalaman.setText(kedalaman);
                TvTanggal.setText(tanggal);

                String jam = mAdapter.getItem(pos).getJam();
                TvJam.setText(jam);

                String bujur = mAdapter.getItem(pos).getBujur();
                String lintang = mAdapter.getItem(pos).getLintang();
                TvKoordinat.setText(bujur+", "+lintang);

            }

        });

        sitesList.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int pos, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        Intent intentMap = new Intent(MainActivity.this, MapsActivity.class);
                        String position = mAdapter.getItem(pos).getCoordinates();
                        Bundle extras = new Bundle();
                        extras.putString("pos", position);
                        intentMap.putExtras(extras);
                        startActivity(intentMap);
                        break;
                }
                return false;
            }
        });


        if(isNetworkAvailable()){
            Log.i("StackSites", "starting download Task");
            SitesDownloadTask download = new SitesDownloadTask();
            download.execute();
        }else{
            mAdapter = new SitesAdapter(getApplicationContext(), -1, SitesXmlPullParser.getStackSitesFromFile(MainActivity.this));
            sitesList.setAdapter(mAdapter);
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
    private class SitesDownloadTask extends AsyncTask<Void, Void, Void>{

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

        @Override
        protected void onPostExecute(Void result){
            //setup our Adapter and set it to the ListView.
            mAdapter = new SitesAdapter(MainActivity.this, -1, SitesXmlPullParser.getStackSitesFromFile(MainActivity.this));
            sitesList.setAdapter(mAdapter);
            Log.i("StackSites", "adapter size = "+ mAdapter.getCount());
        }
    }
}
