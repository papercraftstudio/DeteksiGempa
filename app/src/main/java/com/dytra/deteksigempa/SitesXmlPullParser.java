package com.dytra.deteksigempa;

import android.content.Context;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

class SitesXmlPullParser {

    private static final String KEY_GEMPA = "gempa";
    private static final String KEY_TANGGAL = "Tanggal";
    private static final String KEY_JAM = "Jam";
    private static final String KEY_COORDINATES = "coordinates";
    private static final String KEY_LINTANG = "Lintang";
    private static final String KEY_BUJUR = "Bujur";
    private static final String KEY_MAGNITUDE = "Magnitude";
    private static final String KEY_KEDALAMAN = "Kedalaman";
    private static final String KEY_WILAYAH = "Wilayah";

    static List<StackSite> getStackSitesFromFile(Context ctx) {
        List<StackSite> stackSites = new ArrayList<>();
        StackSite curStackSite = null;
        String curText = "";
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xpp = factory.newPullParser();
            FileInputStream fis = ctx.openFileInput("datagempa.xml");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
            xpp.setInput(reader);
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = xpp.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase(KEY_GEMPA)) {
                            curStackSite = new StackSite();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        curText = xpp.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        if (tagname.equalsIgnoreCase(KEY_GEMPA)) {
                            stackSites.add(curStackSite);
                        } else if (tagname.equalsIgnoreCase(KEY_TANGGAL)) {
                            if (curStackSite != null) {
                                curStackSite.setTanggal(curText);
                            }
                        } else if (tagname.equalsIgnoreCase(KEY_JAM)) {
                            if (curStackSite != null) {
                                curStackSite.setJam(curText);
                            }
                        } else if (tagname.equalsIgnoreCase(KEY_COORDINATES)) {
                            if (curStackSite != null) {
                                curStackSite.setCoordinates(curText);
                            }
                        } else if (tagname.equalsIgnoreCase(KEY_LINTANG)) {
                            if (curStackSite != null) {
                                curStackSite.setLintang(curText);
                            }
                        } else if (tagname.equalsIgnoreCase(KEY_BUJUR)) {
                            if (curStackSite != null) {
                                curStackSite.setBujur(curText);
                            }
                        } else if (tagname.equalsIgnoreCase(KEY_MAGNITUDE)) {
                            if (curStackSite != null) {
                                curStackSite.setMagnitude(curText);
                            }
                        } else if (tagname.equalsIgnoreCase(KEY_KEDALAMAN)) {
                            if (curStackSite != null) {
                                curStackSite.setKedalaman(curText);
                            }
                        } else if (tagname.equalsIgnoreCase(KEY_WILAYAH)) {
                            if (curStackSite != null) {
                                curStackSite.setWilayah(curText);
                            }
                        }
                        break;
                    default:
                        break;
                }
                eventType = xpp.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stackSites;
    }
}