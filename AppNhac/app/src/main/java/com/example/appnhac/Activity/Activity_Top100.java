package com.example.appnhac.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.appnhac.Adapter.Top100Adapter;
import com.example.appnhac.Model.BaiHat;
import com.example.appnhac.R;
import com.example.appnhac.Service.APIService;
import com.example.appnhac.Service.DataService;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.example.appnhac.Activity.MainActivity.data_top100music;
import static com.example.appnhac.Activity.MainActivity.haveFragment_minium2;
import static com.example.appnhac.Activity.MainActivity.have_data_top100music;
import static com.example.appnhac.Activity.MainActivity.init_Playmusic_minium2;

public class Activity_Top100 extends AppCompatActivity
{
    Toolbar toolbar_top100;
    RecyclerView recyclerView_top100;
    ArrayList<BaiHat> baiHats;
    Top100Adapter top100Adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    RelativeLayout loadingPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top100);

        anhxa();
        init();
        haveFragment_minium2 = true;
        init_Playmusic_minium2(Activity_Top100.this);
    }

    private void handle_SwipeRefresh()
    {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                getData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    protected void onPause()
    {
        data_top100music = baiHats;
        super.onPause();
    }

    @Override
    protected void onStart()
    {
        getData_AndDump();
        handle_SwipeRefresh();
        super.onStart();
    }

    private void init()
    {
        setSupportActionBar(toolbar_top100);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Top 100 Bài Hát Hot Nhất");
        toolbar_top100.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar_top100.setBackgroundColor(getResources().getColor(R.color.blue));
        toolbar_top100.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                haveFragment_minium2 = false;
                finish();
            }
        });
    }

    private void getData()
    {
        DataService dataService = APIService.getService();
        Call<List<BaiHat>> callback = dataService.GetData_Top100();
        callback.enqueue(new Callback<List<BaiHat>>()
        {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response)
            {
                baiHats = (ArrayList<BaiHat>) response.body();

                loadingPanel.setVisibility(View.GONE);
                top100Adapter = new Top100Adapter(Activity_Top100.this, baiHats);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Activity_Top100.this);
                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                recyclerView_top100.setLayoutManager(linearLayoutManager);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView_top100.getContext(),
                        DividerItemDecoration.VERTICAL);
                recyclerView_top100.addItemDecoration(dividerItemDecoration);
                recyclerView_top100.setAdapter(top100Adapter);
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t)
            {

            }
        });
    }

    private void getData_AndDump()
    {
        if (!have_data_top100music)
        {
            getData();
            have_data_top100music = true;
        }
        else
        {
            loadingPanel.setVisibility(View.GONE);
            baiHats = data_top100music;
            top100Adapter = new Top100Adapter(Activity_Top100.this, baiHats);

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Activity_Top100.this);
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            recyclerView_top100.setLayoutManager(linearLayoutManager);

            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView_top100.getContext(),
                    DividerItemDecoration.VERTICAL);
            recyclerView_top100.addItemDecoration(dividerItemDecoration);

            recyclerView_top100.setAdapter(top100Adapter);
        }
    }

    private void anhxa()
    {
        toolbar_top100 = findViewById(R.id.toolbar_top100);
        recyclerView_top100 = findViewById(R.id.recyclerview_top100);
        swipeRefreshLayout = findViewById(R.id.swiperefreshlayout_top100);
        loadingPanel = findViewById(R.id.loadingPanel_top100);
    }
}