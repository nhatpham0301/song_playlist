package com.example.admin.mediaplayer;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.AudioManager;
import android.media.MediaPlayer;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    int songTitle,songArtist,songLocation;
    String currentLocation,currentTitle,currentArtist;

    // Biến kết nối database
    final String DATABASE_NAME =  "dbQLPlayLists.sqlite";
    SQLiteDatabase database;


    CLassSong cLassSong;
    ArrayList<CLassSong> listSong;
    Uri playUri;
    // Biến lấy danh sánh bài hát trong thiết bị
    private static  final int MY_PERMISSION_REQUEST = 1;

    ArrayList<String> arrayList;
    ListView listView;
    ArrayAdapter<String> adapter;

    // MediaPlayer
    MediaPlayer mediaPlayer;

    // Biến tạo danh sách playList
    EditText edtName;

    Button btncreatePlaylists;
    PlayListBaseAdapter adapterPlayList;
    List<PlayList> listplayList;
    PlayList playList;
    ListView listViewPlayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControl();
        readDataPlayList();
        insertDataSong();
        readDataSong();
        buttonEvent();
        showSong();


    }

    // Lấy danh sách bài hát
    private void showSong() {
        if(ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE))
            {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},MY_PERMISSION_REQUEST);
            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},MY_PERMISSION_REQUEST);
            }
        }   else {
            doStuff();
        }
    }

    public void doStuff()
    {
        listView = findViewById(R.id.listLSong);
        arrayList = new ArrayList<>();
        getMusic();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Uri myUri = ....; // initialize Uri here
                playUri = Uri.parse(listSong.get(position).getCurrentLocation());
                MediaPlayer mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    mediaPlayer.setDataSource(getApplicationContext(), playUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mediaPlayer.start();

            }
        });
    }

    public void NumberDSSong()
    {
        int count = 0;
        for(int i = 0; i < arrayList.size(); i ++)
        {
            count ++;
        }
        String number = String.valueOf(count);
        Toast.makeText(MainActivity.this,number,Toast.LENGTH_SHORT).show();
    }

    public void getMusic()
    {
        ContentResolver contentResolver = getContentResolver();
        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor songCursor = contentResolver.query(songUri, null, null, null,null);

        if(songCursor != null && songCursor.moveToFirst())
        {
             int id = 0;
             songTitle = songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
             songArtist = songCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
             songLocation = songCursor.getColumnIndex(MediaStore.Audio.Media.DATA);

            do {
                 currentTitle = songCursor.getString(songTitle);
                 currentArtist = songCursor.getString(songArtist);
                 currentLocation = songCursor.getString(songLocation);
                arrayList.add(currentTitle + "\n" + currentArtist + "\n"+ currentLocation);
                listSong = new ArrayList<>();
                cLassSong = new CLassSong(id,currentTitle,currentArtist,currentLocation);
                listSong.add(cLassSong);
                id ++;

            } while (songCursor.moveToNext());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case  MY_PERMISSION_REQUEST: {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    if(ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                    {
                        Toast.makeText(MainActivity.this,"Permission granted",Toast.LENGTH_SHORT).show();

                        doStuff();
                    } else  {
                        Toast.makeText(MainActivity.this, "No Permission granted", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    return;
                }
            }
        }
    }

    // Các sự kiện click của button
    private void buttonEvent()
    {
        btncreatePlaylists.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(MainActivity.this);
                dialog.setTitle("Create Playlists");
                dialog.setCancelable(false);
                dialog.setContentView(R.layout.create_playlists);
                TextView text = dialog.findViewById(R.id.txtText);
                text.setText("Create play list");
                edtName = dialog.findViewById(R.id.editNamePlaylists);
                Button btnOK = dialog.findViewById(R.id.btnOK);
                Button btnCancle = dialog.findViewById(R.id.btnCancle);

                btnOK.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        insert();
                        adapterPlayList.notifyDataSetChanged();
                        dialog.cancel();

                    }
                });

                btnCancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });

        listViewPlayList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "You click on position:"+position, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void showDialog(final String namePlaylist, final int idplaylist)
    {
        final Dialog dialog = new Dialog(this);
        dialog.setTitle("Edit PlayLists");
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.create_playlists);
        edtName = dialog.findViewById(R.id.editNamePlaylists);
        TextView text = dialog.findViewById(R.id.txtText);

        text.setText("Edit play list");
        edtName.setText(namePlaylist);

        Button btnOK = dialog.findViewById(R.id.btnOK);
        Button btnCancle = dialog.findViewById(R.id.btnCancle);

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update(idplaylist);
                dialog.cancel();

            }
        });

        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    private void update(int idplaylist) {
        String nameplaylist = edtName.getText().toString();

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", nameplaylist);

        SQLiteDatabase database = Database.initDatabase(this,DATABASE_NAME);
         database.update("play_list",contentValues,"id = ?", new String[]{idplaylist + ""});
        listplayList.clear();
        readDataPlayList();
        adapterPlayList.notifyDataSetChanged();

    }

    public void deletePlayLits(final int idPlaylist)
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Xác nhận xóa");
        builder.setMessage("Bạn chắc chắn có muốn xóa playlist này");
        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                delete(idPlaylist);
            }
        });

        builder.setNegativeButton("không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();


    }

    private void delete(int idPlaylist) {
        SQLiteDatabase database = Database.initDatabase(MainActivity.this,DATABASE_NAME);
        database.delete("play_list", "id = ? ", new String[] {idPlaylist + ""} );
        listplayList.clear();
        readDataPlayList();
        adapterPlayList.notifyDataSetChanged();

    }

    private void insert() {
        String name = edtName.getText().toString();
        int number = 0;

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("number",number);

        SQLiteDatabase database = Database.initDatabase(this,DATABASE_NAME);
        database.insert("play_list",null ,contentValues);
        Cursor cursor = database.rawQuery("SELECT * FROM play_list",null);
        cursor.moveToPosition(cursor.getCount()-1);
        int id = cursor.getInt(0);
        listplayList.add(new PlayList(name,number,id));
    }

    private void readDataPlayList() {
        database = Database.initDatabase(this,DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM play_list",null);
        for (int i = 0; i< cursor.getCount(); i ++)
        {
            cursor.moveToPosition(i);
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            int number = cursor.getInt(2);
            listplayList.add(new PlayList(name,number,id));
        }
    }

    private void insertDataSong()
    {
        for (int i = 0 ; i  < listSong.size(); i ++)
        {
            cLassSong = listSong.get(i);
            int id = cLassSong.getId();
            String Title = cLassSong.getCurrentTitle();
            String Artist = cLassSong.getCurrentArtist();
            String Data = cLassSong.getCurrentLocation();

            ContentValues contentValues = new ContentValues();
            contentValues.put("id",id);
            contentValues.put("name",Title);
            contentValues.put("musician",Artist);
            contentValues.put("data",Data);

            SQLiteDatabase database = Database.initDatabase(this,DATABASE_NAME);
            database.insert("song",null,contentValues);
        }
    }

    private void readDataSong() {
        database = Database.initDatabase(this,DATABASE_NAME);
        Cursor cursor = database.rawQuery("SELECT * FROM song",null);
    }

    private List<PlayList> getList(String namePlayList, int numberSong)
    {
        listplayList = new ArrayList<>();
        //playList = new PlayList(namePlayList,numberSong);

        listplayList.add(playList);

        return listplayList;
    }

    private void addControl() {
        TabHost tabHost = findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tab1 = tabHost.newTabSpec("t1");
        tab1.setIndicator("Songs");
        tab1.setContent(R.id.tab1);
        tabHost.addTab(tab1);

        TabHost.TabSpec tab2 = tabHost.newTabSpec("t2");
        tab2.setIndicator("Playlists");
        tab2.setContent(R.id.tab2);
        tabHost.addTab(tab2);

        //ListplayList = getList("songTung",15);
        listplayList = new ArrayList<>();
        listViewPlayList = findViewById(R.id.listPlaylist1);
        adapterPlayList = new PlayListBaseAdapter(listplayList,this);
        listViewPlayList.setAdapter(adapterPlayList);

        btncreatePlaylists = findViewById(R.id.btnCreatePlaylist);

    }
}
