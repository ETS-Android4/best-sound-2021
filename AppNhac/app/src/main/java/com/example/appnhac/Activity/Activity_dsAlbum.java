package com.example.appnhac.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
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

public class Activity_dsAlbum extends AppCompatActivity
{

    Toolbar toolbar;
    RecyclerView recyclerView;
    ArrayList<Album> albums;
    Ds_AlbumAdapter albumAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ds_getall);

        haveFragment_minium3 = true;
        init_Playmusic_minium3(Activity_dsAlbum.this);

        anhxa();
        init();
        getDaTa_DsAlbum();
    }

    private void init()
    {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Albums");
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

    private void getDaTa_DsAlbum()
    {
        DataService dataService = APIService.getService();
        Call<List<Album>> callback = dataService.GetDataDsAlbum();
        callback.enqueue(new Callback<List<Album>>()
        {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response)
            {
                albums = (ArrayList<Album>) response.body();
                albumAdapter = new Ds_AlbumAdapter(Activity_dsAlbum.this,albums);

                LinearLayoutManager linearLayoutManager = new GridLayoutManager(Activity_dsAlbum.this,2);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(albumAdapter);
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t)
            {

            }
        });
    }

    private void anhxa()
    {
        toolbar = findViewById(R.id.Toolbar_dsGetAll);
        recyclerView = findViewById(R.id.recycleview_dsGetAll);
    }
}