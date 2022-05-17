package com.example.appnhac.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.appnhac.Adapter.Ds_TheLoaiAdapter;
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

public class Activity_AllTheLoai extends AppCompatActivity
{
    Toolbar toolbar_alltheloai;
    RecyclerView recyclerView_alltheloai;
    ArrayList<TheLoai> theLoais;
    Ds_TheLoaiAdapter ds_theLoaiAdapter;
    RelativeLayout loadingPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_the_loai);

        haveFragment_minium3 = true;
        init_Playmusic_minium3(Activity_AllTheLoai.this);

        anhxa();
        init();
        getData();

    }

    private void init()
    {
        setSupportActionBar(toolbar_alltheloai);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tất Cả Các Thể Loại");
        toolbar_alltheloai.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar_alltheloai.setBackgroundColor(getResources().getColor(R.color.blue));
        toolbar_alltheloai.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                haveFragment_minium3 = false;
                finish();
            }
        });
    }

    private void getData()
    {
        DataService dataService = APIService.getService();
        Call<List<TheLoai>> callback = dataService.GetData_AllTheLoai();
        callback.enqueue(new Callback<List<TheLoai>>()
        {
            @Override
            public void onResponse(Call<List<TheLoai>> call, Response<List<TheLoai>> response)
            {
                theLoais = (ArrayList<TheLoai>) response.body();

                loadingPanel.setVisibility(View.GONE);
                ds_theLoaiAdapter = new Ds_TheLoaiAdapter(Activity_AllTheLoai.this,theLoais);
                LinearLayoutManager linearLayoutManager = new GridLayoutManager(Activity_AllTheLoai.this,2);
                recyclerView_alltheloai.setLayoutManager(linearLayoutManager);
                recyclerView_alltheloai.setAdapter(ds_theLoaiAdapter);
            }

            @Override
            public void onFailure(Call<List<TheLoai>> call, Throwable t)
            {

            }
        });
    }

    private void anhxa()
    {
        toolbar_alltheloai = findViewById(R.id.toolbar_alltheloai);
        recyclerView_alltheloai = findViewById(R.id.recyclerview_alltheloai);
        loadingPanel = findViewById(R.id.loadingPanel_alltheloai);
    }
}