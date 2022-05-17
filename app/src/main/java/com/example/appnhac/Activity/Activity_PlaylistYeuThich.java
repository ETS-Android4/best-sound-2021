package com.example.appnhac.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.appnhac.Adapter.Ds_PlaylistAdapter;
import com.example.appnhac.Model.Playlist;
import com.example.appnhac.R;
import com.example.appnhac.Service.APIService;
import com.example.appnhac.Service.DataService;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.example.appnhac.Activity.MainActivity.dsid_PlaylistYeuThich;
import static com.example.appnhac.Activity.MainActivity.haveFragment_minium3;
import static com.example.appnhac.Activity.MainActivity.init_Playmusic_minium3;
import static com.example.appnhac.Activity.MainActivity.inmode_dsPlaylistYeuThich;

public class Activity_PlaylistYeuThich extends AppCompatActivity
{
    Toolbar toolbar_playlistyeuthich;
    RecyclerView recyclerView_playlistyeuthich;
    public static ArrayList<Playlist> playlistsYeuThich = new ArrayList<>() ;
    ArrayList<Playlist> playlists2 ;
    public static Ds_PlaylistAdapter dsPlaylist_YeuThichAdapter;
    RelativeLayout loadingPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_yeu_thich);

        inmode_dsPlaylistYeuThich = true;
        haveFragment_minium3 = true;
        init_Playmusic_minium3( Activity_PlaylistYeuThich.this);
        anhxa();
        init();
        getData();
    }

    private void dump_Data()
    {
        loadingPanel.setVisibility(View.GONE);
        dsPlaylist_YeuThichAdapter = new Ds_PlaylistAdapter(Activity_PlaylistYeuThich.this, playlistsYeuThich );
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(Activity_PlaylistYeuThich.this, 2);
        recyclerView_playlistyeuthich.setLayoutManager(linearLayoutManager);
        recyclerView_playlistyeuthich.setAdapter(dsPlaylist_YeuThichAdapter);
    }

    private void getData()
    {

        if (playlistsYeuThich.size() == dsid_PlaylistYeuThich.size())
            dump_Data();
        else
        {
            DataService dataService = APIService.getService();
            for (int i = playlistsYeuThich.size(); i < dsid_PlaylistYeuThich.size(); i++)
            {
                Call<List<Playlist>> callback = dataService.GetPlaylist_FromID(dsid_PlaylistYeuThich.get(i));
                callback.enqueue(new Callback<List<Playlist>>()
                {
                    @Override
                    public void onResponse(Call<List<Playlist>> call, Response<List<Playlist>> response)
                    {
                        playlists2 = (ArrayList<Playlist>) response.body();
                        playlistsYeuThich.add(playlists2.get(0));

                        dump_Data();
                    }

                    @Override
                    public void onFailure(Call<List<Playlist>> call, Throwable t)
                    {

                    }
                });
            }
        }
    }

    @Override
    protected void onDestroy()
    {
        inmode_dsPlaylistYeuThich = false;
        super.onDestroy();
    }

    private void init()
    {
        setSupportActionBar(toolbar_playlistyeuthich);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Playlist yêu thích");
        toolbar_playlistyeuthich.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar_playlistyeuthich.setBackgroundColor(getResources().getColor(R.color.blue));
        toolbar_playlistyeuthich.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                haveFragment_minium3 = false;
                finish();
            }
        });
    }

    private void anhxa()
    {
        toolbar_playlistyeuthich = findViewById(R.id.toolbar_playlistyeuthich);
        recyclerView_playlistyeuthich = findViewById(R.id.recycleview_playlistyeuthich);
        loadingPanel = findViewById(R.id.loadingPanel_playlistyeuthich);
    }
}