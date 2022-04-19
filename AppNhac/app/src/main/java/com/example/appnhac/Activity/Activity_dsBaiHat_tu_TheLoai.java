package com.example.appnhac.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import com.example.appnhac.Model.TheLoai;
import com.example.appnhac.R;
import com.example.appnhac.Service.APIService;
import com.example.appnhac.Service.DataService;
import com.google.android.material.appbar.CollapsingToolbarLayout;
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

public class Activity_dsBaiHat_tu_TheLoai extends AppCompatActivity
{
    CollapsingToolbarLayout collapsingToolbarLayout;
    RecyclerView recyclerView;
    Toolbar toolbar;
    ImageView imageView_hinh;
    TextView   textView_noidung;
    TextView textView_tencasi;
    TheLoai theLoai;
    ArrayList<BaiHat> baiHats;
    dsBaiHatAdapter dsBaiHat_Adapter;
    ImageButton imageButton_moreoption;
    RelativeLayout loadingPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dsbaihat);

        anhxa();
        getData_Intent();
        init();
        getData_BaiHat();
        haveFragment_minium2 = true;
        init_Playmusic_minium2(Activity_dsBaiHat_tu_TheLoai.this);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_dsBaiHat_tu_TheLoai.this);
                builder.setItems(option, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if (which == 0)
                            if (!check_duplicateId(dsid_PlaylistYeuThich,theLoai.getidPlayList()))
                            {
                                Toast.makeText(Activity_dsBaiHat_tu_TheLoai.this, "Đã thêm", Toast.LENGTH_SHORT).show();
                                dsid_PlaylistYeuThich.add(theLoai.getidPlayList());
                            }
                            else Toast.makeText(Activity_dsBaiHat_tu_TheLoai.this, "PlayList này đã được thêm trước đó", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
    }

    private void getData_BaiHat()
    {
        DataService dataService = APIService.getService();
        Call<List<BaiHat>> listCall = dataService.GetDataDSBaiHat_FromTheLoai(theLoai.getIdTheLoai());
        listCall.enqueue(new Callback<List<BaiHat>>()
        {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response)
            {
                baiHats = (ArrayList<BaiHat>) response.body();
                loadingPanel.setVisibility(View.GONE);
                dsBaiHat_Adapter = new dsBaiHatAdapter(Activity_dsBaiHat_tu_TheLoai.this,baiHats);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Activity_dsBaiHat_tu_TheLoai.this);
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

        textView_noidung.setText(theLoai.getTenTheLoai());
        textView_tencasi.setText("V.A");
        Picasso.with(Activity_dsBaiHat_tu_TheLoai.this).load(theLoai.getHinhTheLoai()).into(imageView_hinh);
    }

    private void getData_Intent()
    {
        Intent intent = getIntent();
        if (intent!=null)
        {
            theLoai = (TheLoai) intent.getSerializableExtra("theloai");
        }
    }

    private void anhxa()
    {
        collapsingToolbarLayout = findViewById(R.id.collapsingToolbar_dsbaihat);
        toolbar = findViewById(R.id.Toolbar_dsbaihat);
        imageView_hinh = findViewById(R.id.imgview_hinh_dsbaihat);
        textView_noidung = findViewById(R.id.txtview_noidung_dsbaihat);
        textView_tencasi = findViewById(R.id.txtview_tencasi_dsbaihat);
        recyclerView = findViewById(R.id.recycleview_dsbaihat);
        imageButton_moreoption = findViewById(R.id.imgbutton_moreoption_dsbaihat);
        loadingPanel = findViewById(R.id.loadingPanel_dsbaihat);
    }
}