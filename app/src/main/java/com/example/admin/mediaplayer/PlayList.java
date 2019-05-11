package com.example.admin.mediaplayer;

public class PlayList {
    private String PlaylistSong;
    private int NumberSong;
    private int Id;
    //private String ImagePlaylist;

    public PlayList(String playlistSong, int numberSong, int id/*, String imagePlaylist*/) {
        PlaylistSong = playlistSong;
        NumberSong = numberSong;
        Id = id;
        //ImagePlaylist = imagePlaylist;
    }

    public String getPlaylistSong() {
        return PlaylistSong;
    }

    public void setPlaylistSong(String playlistSong) {
        this.PlaylistSong = playlistSong;
    }

    public int getNumberSong() {
        return NumberSong;
    }

    public void setNumberSong(int numberSong) {
        this.NumberSong = numberSong;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    /*public String getImagePlaylist() {
        return ImagePlaylist;
    }

    public void setImagePlaylist(String imagePlaylist) {
        this.ImagePlaylist = imagePlaylist;
    }*/
}
