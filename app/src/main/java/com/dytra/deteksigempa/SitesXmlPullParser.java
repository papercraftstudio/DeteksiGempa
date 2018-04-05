package com.dytra.deteksigempa;

/**
 * Created by USER on 3/29/2018.
 */

import android.content.Context;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SitesXmlPullParser {


    static final String KEY_GEMPA = "gempa";
    static final String KEY_TANGGAL = "Tanggal";
    static final String KEY_JAM = "Jam";
    static final String KEY_COORDINATES = "coordinates";
    static final String KEY_LINTANG = "Lintang";
    static final String KEY_BUJUR = "Bujur";
    static final String KEY_MAGNITUDE = "Magnitude";
    static final String KEY_KEDALAMAN = "Kedalaman";
    static final String KEY_WILAYAH = "Wilayah";



    public static List<StackSite> getStackSitesFromFile(Context ctx) {

        // List of StackSites that we will return
        List<StackSite> stackSites;
        stackSites = new ArrayList<StackSite>();

        // temp holder for current StackSite while parsing
        StackSite curStackSite = null;
        // temp holder for current text value while parsing
        String curText = "";

        try {
            // Get our factory and PullParser
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();

            // Open up InputStream and Reader of our file.
            FileInputStream fis = ctx.openFileInput("datagempa.xml");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            // point the parser to our file.
            xpp.setInput(reader);

            // get initial eventType
            int eventType = xpp.getEventType();

            // Loop through pull events until we reach END_DOCUMENT
            while (eventType != XmlPullParser.END_DOCUMENT) {
                // Get the current tag
                String tagname = xpp.getName();

                // React to different event types appropriately
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase(KEY_GEMPA)) {
                            // If we are starting a new <site> block we need
                            //a new StackSite object to represent it
                            curStackSite = new StackSite();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        //grab the current text so we can use it in END_TAG event
                        curText = xpp.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase(KEY_GEMPA)) {
                            // if </site> then we are done with current Site
                            // add it to the list.
                            stackSites.add(curStackSite);
                        } else if (tagname.equalsIgnoreCase(KEY_TANGGAL)) {
                            // if </name> use setName() on curSite
                            curStackSite.setTanggal(curText);
                        } else if (tagname.equalsIgnoreCase(KEY_JAM)) {
                            // if </link> use setLink() on curSite
                            curStackSite.setJam(curText);
                        } else if (tagname.equalsIgnoreCase(KEY_COORDINATES)) {
                            // if </link> use setLink() on curSite
                            curStackSite.setCoordinates(curText);
                        } else if (tagname.equalsIgnoreCase(KEY_LINTANG)) {
                            // if </name> use setName() on curSite
                            curStackSite.setLintang(curText);
                        } else if (tagname.equalsIgnoreCase(KEY_BUJUR)) {
                            // if </link> use setLink() on curSite
                            curStackSite.setBujur(curText);
                        } else if (tagname.equalsIgnoreCase(KEY_MAGNITUDE)) {
                            // if </about> use setAbout() on curSite
                            curStackSite.setMagnitude(curText);
                        } else if (tagname.equalsIgnoreCase(KEY_KEDALAMAN)) {
                            // if </about> use setAbout() on curSite
                            curStackSite.setKedalaman(curText);
                        } else if (tagname.equalsIgnoreCase(KEY_WILAYAH)) {
                            // if </about> use setAbout() on curSite
                            curStackSite.setWilayah(curText);
                        }
                        break;

                    default:
                        break;
                }
                //move on to next iteration
                eventType = xpp.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // return the populated list.
        return stackSites;
    }
}