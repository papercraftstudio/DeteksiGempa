package com.dytra.deteksigempa;

/**
 * Created by USER on 3/29/2018.
 */


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;


/*
 * Custom Adapter class that is responsible for holding the list of sites after they
 * get parsed out of XML and building row views to display them on the screen.
 */
public class SitesAdapter extends ArrayAdapter<StackSite> {

    ImageLoader imageLoader;
    DisplayImageOptions options;


    public SitesAdapter(Context ctx, int textViewResourceId, List<StackSite> sites) {
        super(ctx, textViewResourceId, sites);


        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(ctx).build();
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);

        //Setup options for ImageLoader so it will handle caching for us.
        options = new DisplayImageOptions.Builder()
                .cacheInMemory()
                .cacheOnDisc()
                .build();


    }

    @Override
    public View getView(int pos, View convertView, ViewGroup parent){
        RelativeLayout row = (RelativeLayout)convertView;
        Log.i("StackSites", "getView pos = " + pos);
        if(null == row){
            LayoutInflater inflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = (RelativeLayout)inflater.inflate(R.layout.row_site, null);
        }

        TextView wilayahTxt = (TextView)row.findViewById(R.id.wilayahTxt);
        TextView magnitudeTxt = (TextView)row.findViewById(R.id.magnitudeTxt);
        TextView kedalamanTxt = (TextView)row.findViewById(R.id.kedalamanTxt);
        TextView tanggalTxt = (TextView)row.findViewById(R.id.tanggalTxt);

        wilayahTxt.setText(getItem(pos).getWilayah());
        magnitudeTxt.setText(getItem(pos).getMagnitude());
        kedalamanTxt.setText(getItem(pos).getKedalaman());
        tanggalTxt.setText(getItem(pos).getTanggal());


        return row;


    }

}