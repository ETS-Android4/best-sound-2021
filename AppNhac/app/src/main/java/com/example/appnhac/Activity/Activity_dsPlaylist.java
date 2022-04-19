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

public class Activity_dsPlaylist extends AppCompatActivity
{
    Toolbar toolbar;
    RecyclerView recyclerView;
    ArrayList<Playlist> playlists;
    Ds_PlaylistAdapter dsplaylistAdapter;
    RelativeLayout loadingPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ds_getall);

        anhxa();
        init();
        getDaTa_DsPlaylist();
        haveFragment_minium3 = true;
        init_Playmusic_minium3(Activity_dsPlaylist.this);
    }

    private void init()
    {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Playlist");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setBackgroundColor(getResources().getColor(R.color.blue));
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                haveFragment_minium3 = false;
                finish();
            }
        });
    }

    private void getDaTa_DsPlaylist()
    {
        DataService dataService = APIService.getService();
        Call<List<Playlist>> callback = dataService.GetDataDsPlaylist();
        callback.enqueue(new Callback<List<Playlist>>()
        {
            @Override
            public void onResponse(Call<List<Playlist>> call, Response<List<Playlist>> response)
            {
                playlists = (ArrayList<Playlist>) response.body();
                loadingPanel.setVisibility(View.GONE);
                dsplaylistAdapter = new Ds_PlaylistAdapter(Activity_dsPlaylist.this,playlists);
                LinearLayoutManager linearLayoutManager = new GridLayoutManager(Activity_dsPlaylist.this,2);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(dsplaylistAdapter);
            }

            @Override
            public void onFailure(Call<List<Playlist>> call, Throwable t)
            {

            }
        });
    }

    private void anhxa()
    {
        toolbar = findViewById(R.id.Toolbar_dsGetAll);
        recyclerView = findViewById(R.id.recycleview_dsGetAll);
        loadingPanel = findViewById(R.id.loadingPanel_dsGetAll);
    }


}