package com.example.admin.mediaplayer;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class PlayListBaseAdapter extends BaseAdapter {
    private List<PlayList> listData;
    private LayoutInflater layoutInflater;
    private MainActivity context;
    private PlayList playList;

    public PlayListBaseAdapter(List<PlayList> listData,MainActivity context) {
        this.listData = listData;
        this.layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        final ViewHoder hoder;
        playList = listData.get(position);
        final int id = playList.getId();
        final String name = playList.getPlaylistSong();
        if(convertView == null)
        {
            convertView = layoutInflater.inflate(R.layout.playlist_insong,null);
            hoder = new ViewHoder();
            hoder.playImageView = convertView.findViewById(R.id.imageView);
            hoder.playlistView = convertView.findViewById(R.id.txtNamePlaylists);
            hoder.numbersongView = convertView.findViewById(R.id.txtNumberSongs);
            hoder.btnMenu = convertView.findViewById(R.id.btnMenuImage);
            /*hoder.listViewPlayList = convertView.findViewById(R.id.listPlaylist1);

            hoder.listViewPlayList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(view.getContext(), "????", Toast.LENGTH_SHORT).show();
                }
            });*/


            hoder.btnMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popupMenu = new PopupMenu(parent.getContext(),v);
                    popupMenu.getMenuInflater().inflate(R.menu.menu,popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId())
                            {
                                case R.id.menuEdit:
                                    context.showDialog(name,id);
                                    break;
                                case R.id.menuRemove:
                                    context.deletePlayLits(id);
                                    break;
                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });

            convertView.setTag(hoder);
        }
        else
        {
            hoder = (ViewHoder) convertView.getTag();
        }
        hoder.playlistView.setText(playList.getPlaylistSong());
        hoder.numbersongView.setText(playList.getNumberSong() + " Song");



        /*int imageId = getMipMapRessIdByName(playList.getImagePlaylist());
        hoder.playImageView.setImageResource(imageId);*/

        return convertView;
    }

    // Tìm ID của Image ứng với tên của ảnh (trong thư mục mip map)
    public int getMipMapRessIdByName(String resName)
    {
        String pkgName = context.getPackageName();

        // Trả về 0 nếu không tìm thấy
        int resID = context.getResources().getIdentifier(resName,"mipmap",pkgName);
        Log.i("CustomListView","Res Name: "+resName+ "==> ResID = " +resID );
        return  resID;
    }


    static class ViewHoder
    {
        ImageButton btnMenu;
        ImageView playImageView;
        TextView playlistView;
        TextView numbersongView;
    }

}
