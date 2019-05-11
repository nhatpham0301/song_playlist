package com.example.admin.mediaplayer;

public class CLassSong {
    private int id;
    private String currentTitle;
    private String currentArtist;
    private String currentLocation;

    public CLassSong(int id, String currentTitle, String currentArtist, String currentLocation) {
        this.id = id;
        this.currentTitle = currentTitle;
        this.currentArtist = currentArtist;
        this.currentLocation = currentLocation;
    }

    public String getCurrentTitle() {
        return currentTitle;
    }

    public void setCurrentTitle(String currentTitle) {
        this.currentTitle = currentTitle;
    }

    public String getCurrentArtist() {
        return currentArtist;
    }

    public void setCurrentArtist(String currentArtist) {
        this.currentArtist = currentArtist;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
