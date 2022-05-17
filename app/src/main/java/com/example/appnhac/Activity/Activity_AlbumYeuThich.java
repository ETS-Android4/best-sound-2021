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
import static com.example.appnhac.Activity.MainActivity.dsid_AlbumYeuThich;
import static com.example.appnhac.Activity.MainActivity.haveFragment_minium3;
import static com.example.appnhac.Activity.MainActivity.init_Playmusic_minium3;
import static com.example.appnhac.Activity.MainActivity.inmode_dsAlbumYeuThich;

public class Activity_AlbumYeuThich extends AppCompatActivity
{
    Toolbar toolbar_albumyeuthich;
    RecyclerView recyclerView_albumyeuthich;
    public static ArrayList <Album> albums_albumyeuthich = new ArrayList<>() ;
    ArrayList<Album> albums2 ;
    public static Ds_AlbumAdapter albumAdapter_albumyeuthich;
    RelativeLayout loadingPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album_yeu_thich);

        inmode_dsAlbumYeuThich = true;
        haveFragment_minium3 = true;
        init_Playmusic_minium3( Activity_AlbumYeuThich.this);

        anhxa();
        init();
        getData();
    }

    @Override
    protected void onDestroy()
    {
        inmode_dsAlbumYeuThich = false;
        super.onDestroy();
    }

    private void dump_Data()
    {
        loadingPanel.setVisibility(View.GONE);
        albumAdapter_albumyeuthich = new Ds_AlbumAdapter(Activity_AlbumYeuThich.this, albums_albumyeuthich);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(Activity_AlbumYeuThich.this, 2);
        recyclerView_albumyeuthich.setLayoutManager(linearLayoutManager);
        recyclerView_albumyeuthich.setAdapter(albumAdapter_albumyeuthich);
    }

    private void getData()
    {
        if (albums_albumyeuthich.size()==dsid_AlbumYeuThich.size())
            dump_Data();
        else
        {
            DataService dataService = APIService.getService();
            for (int i = albums_albumyeuthich.size(); i < dsid_AlbumYeuThich.size(); i++)
            {
                Call<List<Album>> callback = dataService.GetAlbum_FromID(dsid_AlbumYeuThich.get(i));
                callback.enqueue(new Callback<List<Album>>()
                {
                    @Override
                    public void onResponse(Call<List<Album>> call, Response<List<Album>> response)
                    {
                        albums2 = (ArrayList<Album>) response.body();
                        albums_albumyeuthich.add(albums2.get(0));

                        dump_Data();
                    }

                    @Override
                    public void onFailure(Call<List<Album>> call, Throwable t)
                    {

                    }
                });
            }
        }
    }

    private void anhxa()
    {
        toolbar_albumyeuthich = findViewById(R.id.toolbar_albumyeuthich);
        recyclerView_albumyeuthich = findViewById(R.id.recyclerview_albumyeuthich);
        loadingPanel = findViewById(R.id.loadingPanel_albumyeuthich);
    }

    private void init()
    {
        setSupportActionBar(toolbar_albumyeuthich);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Album yêu thích");
        toolbar_albumyeuthich.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar_albumyeuthich.setBackgroundColor(getResources().getColor(R.color.blue));
        toolbar_albumyeuthich.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                haveFragment_minium3 = false;
                finish();
            }
        });
    }

}