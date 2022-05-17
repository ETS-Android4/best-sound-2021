package com.example.appnhac.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.appnhac.Adapter.dsBaiHatAdapter;
import com.example.appnhac.Model.BaiHat;
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
import static com.example.appnhac.Activity.MainActivity.inmode_dsBaiHatYeuThich;
import static com.example.appnhac.Activity.MainActivity.dsid_BaiHatYeuThich;

public class Activity_dsBaiHatYeuThich extends AppCompatActivity
{
    Toolbar toolbar_baihatyeuthich;
    RecyclerView recyclerView_baihatyeuthich;
    public static ArrayList<BaiHat> baiHats_BaiHatYeuThich = new ArrayList<>() ;
    ArrayList<BaiHat> baiHats2 ;
    public static dsBaiHatAdapter dsBaiHatAdapter_BaiHatYeuThich;
    RelativeLayout loadingPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dsbaihatyeuthich);

        haveFragment_minium2 = true;
        inmode_dsBaiHatYeuThich = true;
        init_Playmusic_minium2( Activity_dsBaiHatYeuThich.this);
        anhxa();
        init();
        getData();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        inmode_dsBaiHatYeuThich = false;
    }

    private void dump_Data()
    {
        loadingPanel.setVisibility(View.GONE);
        dsBaiHatAdapter_BaiHatYeuThich = new dsBaiHatAdapter(Activity_dsBaiHatYeuThich.this, baiHats_BaiHatYeuThich);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Activity_dsBaiHatYeuThich.this);
        recyclerView_baihatyeuthich.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView_baihatyeuthich.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView_baihatyeuthich.addItemDecoration(dividerItemDecoration);
        recyclerView_baihatyeuthich.setAdapter(dsBaiHatAdapter_BaiHatYeuThich);
    }

    private void getData()
    {
        if (baiHats_BaiHatYeuThich.size()==dsid_BaiHatYeuThich.size())
            dump_Data();
        else
        {
            DataService dataService = APIService.getService();
            for (int i = baiHats_BaiHatYeuThich.size(); i < dsid_BaiHatYeuThich.size(); i++)
            {
                Call<List<BaiHat>> callback = dataService.GetBaiHat_FromID(dsid_BaiHatYeuThich.get(i));
                callback.enqueue(new Callback<List<BaiHat>>()
                {
                    @Override
                    public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response)
                    {
                        baiHats2 = (ArrayList<BaiHat>) response.body();
                        baiHats_BaiHatYeuThich.add(baiHats2.get(0));
                        dump_Data();
                    }

                    @Override
                    public void onFailure(Call<List<BaiHat>> call, Throwable t)
                    {

                    }
                });
            }
        }
    }

    private void init()
    {
        setSupportActionBar(toolbar_baihatyeuthich);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Bài hát yêu thích");
        toolbar_baihatyeuthich.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar_baihatyeuthich.setBackgroundColor(getResources().getColor(R.color.blue));
        toolbar_baihatyeuthich.setNavigationOnClickListener(new View.OnClickListener()
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
        toolbar_baihatyeuthich = findViewById(R.id.toolbar_baihatyeuthich);
        recyclerView_baihatyeuthich = findViewById(R.id.recyclerview_baihatyeuthich);
        loadingPanel = findViewById(R.id.loadingPanel_dsbaihatyeuthich);
    }
}