package com.example.appnhac.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.appnhac.Adapter.Ds_TheLoaiAdapter;
import com.example.appnhac.Model.ChuDe;
import com.example.appnhac.Model.TheLoai;
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

public class Activity_dsTheLoai extends AppCompatActivity
{
    Toolbar toolbar;
    RecyclerView recyclerView;
    ChuDe chuDe;
    ArrayList<TheLoai> theLoais;
    Ds_TheLoaiAdapter ds_theLoaiAdapter;
    RelativeLayout loadingPanel;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ds_getall);

        anhxa();
        getData_Intent();
        init();
        getData_Theloai();
        haveFragment_minium3 = true;
        init_Playmusic_minium3(Activity_dsTheLoai.this);
    }

    private void getData_Theloai()
    {
        DataService dataService = APIService.getService();
        Call<List<TheLoai>> callback = dataService.GetDataDSTheLoai_FromChuDe(chuDe.getIdChuDe());
        callback.enqueue(new Callback<List<TheLoai>>()
        {
            @Override
            public void onResponse(Call<List<TheLoai>> call, Response<List<TheLoai>> response)
            {
                theLoais = (ArrayList<TheLoai>) response.body();
                loadingPanel.setVisibility(View.GONE);
                ds_theLoaiAdapter = new Ds_TheLoaiAdapter(Activity_dsTheLoai.this, theLoais);
                LinearLayoutManager linearLayoutManager = new GridLayoutManager(Activity_dsTheLoai.this, 2);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(ds_theLoaiAdapter);
            }

            @Override
            public void onFailure(Call<List<TheLoai>> call, Throwable t)
            {

            }
        });
    }

    private void getData_Intent()
    {
        Intent intent = getIntent();
        if (intent!=null)
        {
            chuDe = (ChuDe) intent.getSerializableExtra("theloai");
        }
    }

    private void init()
    {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(chuDe.getTenChuDe());
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

    private void anhxa()
    {
        toolbar = findViewById(R.id.Toolbar_dsGetAll);
        recyclerView = findViewById(R.id.recycleview_dsGetAll);
        loadingPanel = findViewById(R.id.loadingPanel_dsGetAll);
    }
}