package com.dytra.deteksigempa;

/**
 * Created by USER on 3/29/2018.
 */

public class StackSite {

    private String tanggal;
    private String jam;
    private String coordinates;
    private String lintang;
    private String bujur;
    private String magnitude;
    private String kedalaman;
    private String wilayah;

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getLintang() {
        return lintang;
    }

    public void setLintang(String lintang) {
        this.lintang = lintang;
    }

    public String getBujur() {
        return bujur;
    }

    public void setBujur(String bujur) {
        this.bujur = bujur;
    }

    public String getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(String magnitude) {
        this.magnitude = magnitude;
    }

    public String getKedalaman() {
        return kedalaman;
    }

    public void setKedalaman(String kedalaman) {
        this.kedalaman = kedalaman;
    }

    public String getWilayah() {
        return wilayah;
    }

    public void setWilayah(String wilayah) {
        this.wilayah = wilayah;
    }



    @Override
    public String toString() {
        return "StackSite [tanggal=" + tanggal +
                ", jam=" + jam +
                ", coordinates=" + coordinates +
                ", lintang=" + lintang +
                ", bujur=" + bujur +
                ", magnitude=" + magnitude +
                ", kedalaman=" + kedalaman +
                ", wilayah=" + wilayah +"]";
    }
}
