package com.example.appnhac.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.appnhac.Adapter.dsBaiHatAdapter;
import com.example.appnhac.Model.BaiHat;
import com.example.appnhac.Model.Quangcao;
import com.example.appnhac.R;
import com.example.appnhac.Service.APIService;
import com.example.appnhac.Service.DataService;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.example.appnhac.Activity.MainActivity.check_duplicateId;
import static com.example.appnhac.Activity.MainActivity.dsid_PlaylistYeuThich;
import static com.example.appnhac.Activity.MainActivity.getRandom_Background;
import static com.example.appnhac.Activity.MainActivity.haveFragment_minium2;
import static com.example.appnhac.Activity.MainActivity.init_Playmusic_minium2;

public class Activity_dsBaiHat_tuQC extends AppCompatActivity
{
    Quangcao quangcao;

    CoordinatorLayout coordinatorLayout;
    CollapsingToolbarLayout collapsingToolbarLayout;
    Toolbar toolbar;
    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;
    ImageView imageView;
    TextView txtview_noidung;
    TextView txtview_casi;
    ArrayList<BaiHat> baiHat;
   dsBaiHatAdapter dsBaiHat_Adapter;
   ImageButton imageButton_moreoption;
    RelativeLayout loadingPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dsbaihat);

        anhxa();
        DataIntent();
        init();
        haveFragment_minium2 = true;
        init_Playmusic_minium2(Activity_dsBaiHat_tuQC.this);

        if (quangcao != null)
        {
            setValueinView(quangcao.getHinhAnhResize(),quangcao.getNoiDung(), quangcao.getTenCaSi());
            getDataQuangCao(quangcao.getIdPlayList());
        }
        handle_moreoption();
    }

    private void handle_moreoption()
    {
        imageButton_moreoption.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String[] option = {"Thêm vào Playlist yêu thích"};

                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_dsBaiHat_tuQC.this);

                builder.setItems(option, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if (which == 0)
                            if (!check_duplicateId(dsid_PlaylistYeuThich,quangcao.getIdPlayList()))
                            {
                                Toast.makeText(Activity_dsBaiHat_tuQC.this, "Đã thêm", Toast.LENGTH_SHORT).show();
                                dsid_PlaylistYeuThich.add(quangcao.getIdPlayList());
                            }
                    }
                });
                builder.show();
            }
        });
    }

    private void setValueinView(String hinh, String noidung, String tencasi)
    {
        collapsingToolbarLayout.setTitle(noidung);
        Picasso.with(this).load(hinh).into(imageView);
        txtview_noidung.setText(noidung);
        txtview_casi.setText(tencasi);
    }

    private void getDataQuangCao(String id)
    {
        DataService dataService = APIService.getService();
        Call<List<BaiHat>> callback = dataService.GetDataDSBaiHat_FromPlaylist(id);
        callback.enqueue(new Callback<List<BaiHat>>()
        {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response)
            {
                baiHat = (ArrayList<BaiHat>) response.body();
                loadingPanel.setVisibility(View.GONE);
                dsBaiHat_Adapter = new dsBaiHatAdapter(Activity_dsBaiHat_tuQC.this,baiHat);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Activity_dsBaiHat_tuQC.this);
                recyclerView.setLayoutManager(linearLayoutManager);
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                        DividerItemDecoration.VERTICAL);
                recyclerView.addItemDecoration(dividerItemDecoration);
                recyclerView.setAdapter(dsBaiHat_Adapter);
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t)
            {

            }
        });
    }

    private void init()
    {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                haveFragment_minium2 = false;
                finish();
            }
        });

        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.white));
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(R.color.white));
        collapsingToolbarLayout.setBackgroundColor(getRandom_Background());
    }

    private void anhxa()
    {
        coordinatorLayout = findViewById(R.id.coordinatorLayout_dsbaihat);
        collapsingToolbarLayout = findViewById(R.id.collapsingToolbar_dsbaihat);
        toolbar = findViewById(R.id.Toolbar_dsbaihat);
        recyclerView = findViewById(R.id.recycleview_dsbaihat);
        floatingActionButton = findViewById(R.id.floatingbutton_dsbaihat);

        imageView = findViewById(R.id.imgview_hinh_dsbaihat);
        txtview_noidung = findViewById(R.id.txtview_noidung_dsbaihat);
        txtview_casi = findViewById(R.id.txtview_tencasi_dsbaihat);
        imageButton_moreoption = findViewById(R.id.imgbutton_moreoption_dsbaihat);
        loadingPanel = findViewById(R.id.loadingPanel_dsbaihat);
    }

    private void DataIntent()
    {
        Intent intent = getIntent();
        if (intent != null)
            if (intent.hasExtra("quangcao"))
            {
                quangcao = (Quangcao) intent.getSerializableExtra("quangcao");
            }
    }
}