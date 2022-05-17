package com.example.appnhac.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.appnhac.Adapter.Ds_ChudeAdapter;
import com.example.appnhac.Model.ChuDe;
import com.example.appnhac.R;
import com.example.appnhac.Service.APIService;
import com.example.appnhac.Service.DataService;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.example.appnhac.Activity.MainActivity.haveFragment_minium2;
import static com.example.appnhac.Activity.MainActivity.init_Playmusic_minium2;

public class Activity_DsChuDe extends AppCompatActivity
{
    Toolbar toolbar;
    RecyclerView recyclerView;
    ArrayList<ChuDe> chuDes;
    Ds_ChudeAdapter ds_chudeAdapter;
    RelativeLayout loadingPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dschude);

        anhxa();
        init();
        getData_DsChuDe();
        haveFragment_minium2 = true;
        init_Playmusic_minium2(Activity_DsChuDe.this);
    }

    private void getData_DsChuDe()
    {
        DataService dataService = APIService.getService();
        Call<List<ChuDe>> callback = dataService.GetDataDsChude();
        callback.enqueue(new Callback<List<ChuDe>>()
        {
            @Override
            public void onResponse(Call<List<ChuDe>> call, Response<List<ChuDe>> response)
            {
                chuDes = (ArrayList<ChuDe>) response.body();
                loadingPanel.setVisibility(View.GONE);
                ds_chudeAdapter = new Ds_ChudeAdapter(Activity_DsChuDe.this,chuDes);
                LinearLayoutManager linearLayoutManager = new GridLayoutManager(Activity_DsChuDe.this,2);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(ds_chudeAdapter);
            }

            @Override
            public void onFailure(Call<List<ChuDe>> call, Throwable t)
            {

            }
        });
    }

    private void init()
    {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tất Cả Chủ Đề");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setBackgroundColor(getResources().getColor(R.color.blue));
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
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
        toolbar = findViewById(R.id.Toolbar_dsChude);
        recyclerView = findViewById(R.id.recycleview_dsChude);
        loadingPanel = findViewById(R.id.loadingPanel_dsChude);
    }
}