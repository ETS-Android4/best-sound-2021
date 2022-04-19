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
import static com.example.appnhac.Activity.MainActivity.haveFragment_minium3;
import static com.example.appnhac.Activity.MainActivity.init_Playmusic_minium3;

public class Activity_PlayListHot extends AppCompatActivity
{
    Toolbar toolbar_playlisthot;
    RecyclerView recyclerView_playlisthot;
    Ds_PlaylistAdapter dsplaylistAdapter;
    ArrayList<Playlist> playlists;
    RelativeLayout loadingPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_list_hot);

        haveFragment_minium3 = true;
        init_Playmusic_minium3(Activity_PlayListHot.this);
        anhxa();
        init();
        getData();
    }

    private void getData()
    {
        DataService dataService = APIService.getService();
        Call<List<Playlist>> callback = dataService.GetData_PlaylistHot();
        callback.enqueue(new Callback<List<Playlist>>()
        {
            @Override
            public void onResponse(Call<List<Playlist>> call, Response<List<Playlist>> response)
            {
                playlists = (ArrayList<Playlist>) response.body();
                loadingPanel.setVisibility(View.GONE);
                dsplaylistAdapter = new Ds_PlaylistAdapter(Activity_PlayListHot.this,playlists);
                LinearLayoutManager linearLayoutManager = new GridLayoutManager(Activity_PlayListHot.this,2);
                recyclerView_playlisthot.setLayoutManager(linearLayoutManager);
                recyclerView_playlisthot.setAdapter(dsplaylistAdapter);
            }

            @Override
            public void onFailure(Call<List<Playlist>> call, Throwable t)
            {

            }
        });
    }

    private void init()
    {
        setSupportActionBar(toolbar_playlisthot);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Top PlayList Hot");
        toolbar_playlisthot.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar_playlisthot.setBackgroundColor(getResources().getColor(R.color.blue));
        toolbar_playlisthot.setNavigationOnClickListener(new View.OnClickListener()
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
        toolbar_playlisthot = findViewById(R.id.toolbar_playlisthot);
        recyclerView_playlisthot = findViewById(R.id.recyclerview_playlisthot);
        loadingPanel = findViewById(R.id.loadingPanel_playlisthot);
    }
}