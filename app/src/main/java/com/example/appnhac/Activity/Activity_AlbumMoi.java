package com.example.appnhac.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.appnhac.Adapter.Ds_AlbumAdapter;
import com.example.appnhac.Model.Album;
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

public class Activity_AlbumMoi extends AppCompatActivity
{
    Toolbar toolbar_albummoi;
    RecyclerView recyclerView_albummoi;
    ArrayList<Album> albums;
    Ds_AlbumAdapter albumAdapter;
    RelativeLayout loadingPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_moi);

        haveFragment_minium3 = true;
        init_Playmusic_minium3(Activity_AlbumMoi.this);

        anhxa();
        init();
        getData();

    }

    private void getData()
    {
        DataService dataService = APIService.getService();
        Call<List<Album>> callback = dataService.GetData_AlbumMoi();
        callback.enqueue(new Callback<List<Album>>()
        {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response)
            {
                albums = (ArrayList<Album>) response.body();

                loadingPanel.setVisibility(View.GONE);
                albumAdapter = new Ds_AlbumAdapter(Activity_AlbumMoi.this,albums);
                LinearLayoutManager linearLayoutManager = new GridLayoutManager(Activity_AlbumMoi.this,2);
                recyclerView_albummoi.setLayoutManager(linearLayoutManager);
                recyclerView_albummoi.setAdapter(albumAdapter);
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t)
            {

            }
        });
    }

    private void init()
    {
        setSupportActionBar(toolbar_albummoi);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Album Mới Phát Hành");
        toolbar_albummoi.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar_albummoi.setBackgroundColor(getResources().getColor(R.color.blue));
        toolbar_albummoi.setNavigationOnClickListener(new View.OnClickListener()
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
        toolbar_albummoi = findViewById(R.id.toolbar_albummoi);
        recyclerView_albummoi = findViewById(R.id.recyclerview_albummoi);
        loadingPanel = findViewById(R.id.loadingPanel_albummoi);
    }
}