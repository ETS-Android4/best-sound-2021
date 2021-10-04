package com.example.appnhac.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import com.example.appnhac.Adapter.dsBaiHat_DaTaiXuongAdapter;
import com.example.appnhac.Model.BaiHat;
import com.example.appnhac.R;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import static com.example.appnhac.Activity.MainActivity.dsid_BaiHatDaTaiXuong;
import static com.example.appnhac.Activity.MainActivity.folder_main;
import static com.example.appnhac.Activity.MainActivity.haveFragment_minium2;
import static com.example.appnhac.Activity.MainActivity.init_Playmusic_minium2;


public class Activity_dsBaiHat_DaTaiXuong extends AppCompatActivity
{
    androidx.appcompat.widget.Toolbar toolbar_dataixuong;
    RecyclerView recyclerView_dataixuong;
    public static ArrayList<BaiHat> baiHats_DaTaiXuong =  new ArrayList<>();
    public static dsBaiHat_DaTaiXuongAdapter dsBaiHat_daTaiXuongAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ds_bai_hat_da_tai_xuong);

        haveFragment_minium2 = true;
        init_Playmusic_minium2(Activity_dsBaiHat_DaTaiXuong.this);
        anhxa();
        getData();
        dump_data();
        init();
    }

    private void dump_data()
    {
        dsBaiHat_daTaiXuongAdapter = new dsBaiHat_DaTaiXuongAdapter(Activity_dsBaiHat_DaTaiXuong.this, baiHats_DaTaiXuong );
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Activity_dsBaiHat_DaTaiXuong.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView_dataixuong.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView_dataixuong.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView_dataixuong.addItemDecoration(dividerItemDecoration);

        recyclerView_dataixuong.setAdapter(dsBaiHat_daTaiXuongAdapter);
    }

    private void getData()
    {
        if (dsid_BaiHatDaTaiXuong.size() == baiHats_DaTaiXuong.size())
            dump_data();
        else
        {
            baiHats_DaTaiXuong.clear();
            ArrayList<HashMap<String, String>> songList = getPlayList(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/" + Environment.DIRECTORY_MUSIC);
            if (songList != null)
            {
                for (int i = 0; i < songList.size(); i++)
                {
                    String tenbh_tencssi_id = songList.get(i).get("file_name");
                    String path = songList.get(i).get("file_path");
                    convert(tenbh_tencssi_id, path);
                }
            }
        }
    }

    private void convert(String filename, String path)
    {
        String[] arrOfStr = filename.split("_", 4);
        if (arrOfStr.length == 4)
            baiHats_DaTaiXuong .add(new BaiHat(arrOfStr[2], arrOfStr[0], arrOfStr[1], path,
                "Offline", "0"));
        else
        {
            File file = new File(path);
            String name = "";
            String artist = "";

            MediaMetadataRetriever mediaMetadataRetriever = (MediaMetadataRetriever) new MediaMetadataRetriever();
            Uri uri = (Uri) Uri.fromFile(file);
            mediaMetadataRetriever.setDataSource(Activity_dsBaiHat_DaTaiXuong.this, uri);
            name = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
            artist = mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);

            if (name == null)
            {
                String[] arrOfStr2 = filename.split("\\.", 2);
                name = arrOfStr2[0];
            }
            if (artist == null)
                artist = "<unknown artist>";

            baiHats_DaTaiXuong .add(new BaiHat(filename, name,  artist, path,
                    "Offline", "0"));
        }
    }

    ArrayList<HashMap<String, String>> getPlayList(String rootPath)
    {
        ArrayList<HashMap<String, String>> fileList = new ArrayList<>();
        try
        {
            File rootFolder = new File(rootPath);
            File[] files = rootFolder.listFiles();

            for (File file : files)
            {
                if (file.isDirectory())
                {
                    if (getPlayList(file.getAbsolutePath()) != null)
                    {
                        fileList.addAll(getPlayList(file.getAbsolutePath()));
                    } else
                    {
                        break;
                    }
                } else if (file.getName().endsWith(".mp3"))
                {
                    HashMap<String, String> song = new HashMap<>();
                    song.put("file_path", file.getAbsolutePath());
                    song.put("file_name", file.getName());
                    fileList.add(song);
                }
            }
            return fileList;
        } catch (Exception e)
        {
            return null;
        }
    }

    private void init()
    {
        setSupportActionBar(toolbar_dataixuong);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Bài hát đã tải xuống");
        toolbar_dataixuong.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar_dataixuong.setBackgroundColor(getResources().getColor(R.color.blue));
        toolbar_dataixuong.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                haveFragment_minium2 = false;
                finish();
            }
        });
    }

    private void anhxa()
    {
        toolbar_dataixuong = findViewById(R.id.toolbar_dataixuong);
        recyclerView_dataixuong = findViewById(R.id.recyclerview_dataixuong);
    }
}