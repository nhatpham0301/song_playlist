package com.example.admin.mediaplayer;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListSong extends AppCompatActivity {
    private static  final int MY_PERMISSION_REQUEST = 1;

    ArrayList<String> arrayList;
    ListView listView;
    ArrayAdapter<String> adapter;
    ListView listViewSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_song);

        listViewSong = findViewById(R.id.listLSong);
        listViewSong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ListSong.this, "????", Toast.LENGTH_SHORT).show();
            }
        });

     /*if(ContextCompat.checkSelfPermission(ListSong.this,
    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
    {
        if(ActivityCompat.shouldShowRequestPermissionRationale(ListSong.this,
                Manifest.permission.READ_EXTERNAL_STORAGE))
        {
            ActivityCompat.requestPermissions(ListSong.this,
                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},MY_PERMISSION_REQUEST);
        } else {
            ActivityCompat.requestPermissions(ListSong.this,
                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},MY_PERMISSION_REQUEST);
        }
    }   else {
        doStuff();
    }*/
}

   /* public void doStuff()
    {
        listView = findViewById(R.id.listLSong);
        arrayList = new ArrayList<>();
        getMusic();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    public void getMusic()
    {
        ContentResolver contentResolver = getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor = contentResolver.query(songUri, null, null, null,null);

        if(songCursor != null && songCursor.moveToFirst())
        {
            int songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int songArtist = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);

            do {
                String currentTitle = songCursor.getString(songTitle);
                String currentArtist = songCursor.getString(songArtist);
                arrayList.add(currentTitle + "\n" + currentArtist);
            } while (songCursor.moveToNext());
        }
    }*/

   /* @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case  MY_PERMISSION_REQUEST: {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if(ContextCompat.checkSelfPermission(ListSong.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                    {
                        Toast.makeText(ListSong.this,"Permission granted",Toast.LENGTH_SHORT).show();

                        doStuff();
                    } else  {
                        Toast.makeText(ListSong.this, "No Permission granted", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    return;
                }
            }
        }
    }*/
}
