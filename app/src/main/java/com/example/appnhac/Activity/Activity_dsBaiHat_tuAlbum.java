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
import com.example.appnhac.Model.Album;
import com.example.appnhac.Model.BaiHat;
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


import static com.example.appnhac.Activity.Activity_AlbumYeuThich.albumAdapter_albumyeuthich;
import static com.example.appnhac.Activity.Activity_AlbumYeuThich.albums_albumyeuthich;
import static com.example.appnhac.Activity.Activity_dsBaiHatYeuThich.baiHats_BaiHatYeuThich;
import static com.example.appnhac.Activity.MainActivity.check_duplicateId;
import static com.example.appnhac.Activity.MainActivity.dsid_AlbumYeuThich;
import static com.example.appnhac.Activity.MainActivity.dsid_BaiHatYeuThich;
import static com.example.appnhac.Activity.MainActivity.getRandom_Background;
import static com.example.appnhac.Activity.MainActivity.haveFragment_minium2;
import static com.example.appnhac.Activity.MainActivity.init_Playmusic_minium2;
import static com.example.appnhac.Activity.MainActivity.inmode_dsAlbumYeuThich;

public class Activity_dsBaiHat_tuAlbum extends AppCompatActivity
{
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    RecyclerView recyclerView;
    ImageView imageView_hinh;
    TextView textView_noidung;
    TextView textView_tencasi;
    ArrayList<BaiHat> baiHats;
    Album album;
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
        init_Playmusic_minium2(Activity_dsBaiHat_tuAlbum.this);
        handle_moreoption();
    }

    private void handle_moreoption()
    {
        imageButton_moreoption.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String s = "";
                if (!inmode_dsAlbumYeuThich)
                    s = "Thêm vào Album yêu thích";
                else s = "Xoá khỏi Album yêu thích";
                String[] colors = {s};

                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_dsBaiHat_tuAlbum.this);

                builder.setItems(colors, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if (which == 0)
                        {
                            if (!inmode_dsAlbumYeuThich )
                            {
                                if (!check_duplicateId(dsid_AlbumYeuThich,album.getIdAlbum()))
                                {
                                    Toast.makeText(Activity_dsBaiHat_tuAlbum.this, "Đã thêm", Toast.LENGTH_SHORT).show();
                                    dsid_AlbumYeuThich.add(album.getIdAlbum());
                                }
                                else Toast.makeText(Activity_dsBaiHat_tuAlbum.this, "Album này đã được thêm trước đó", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                int vt = 0;
                                for (int i = 0; i < dsid_AlbumYeuThich.size(); i++)
                                    if (album.getIdAlbum().equals(dsid_AlbumYeuThich.get(i)))
                                    {
                                        vt = i;
                                        break;
                                    }
                                dsid_AlbumYeuThich.remove(vt);

                                int vt2 = 0;
                                for (int i=0;i<albums_albumyeuthich.size();i++)
                                    if (album.getIdAlbum().equals(albums_albumyeuthich.get(i).getIdAlbum()))
                                    {
                                        vt2 = i;
                                        break;
                                    }
                                albums_albumyeuthich.remove(vt2);
                                albumAdapter_albumyeuthich.notifyDataSetChanged();

                                haveFragment_minium2 = false;
                                finish();
                                Toast.makeText(Activity_dsBaiHat_tuAlbum.this, "Đã xoá", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                builder.show();
            }
        });

    }

    private void getData_BaiHat()
    {
        DataService dataService = APIService.getService();
        Call<List<BaiHat>> callback = dataService.GetDataDSBaiHat_FromAlbum(album.getIdAlbum());
        callback.enqueue(new Callback<List<BaiHat>>()
        {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response)
            {
                baiHats = (ArrayList<BaiHat>) response.body();
                loadingPanel.setVisibility(View.GONE);
                dsBaiHat_Adapter = new dsBaiHatAdapter(Activity_dsBaiHat_tuAlbum.this,baiHats);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(Activity_dsBaiHat_tuAlbum.this);
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

    private void getData_Intent()
    {
        Intent intent = getIntent();
        if (intent!=null)
        {
            album = (Album) intent.getSerializableExtra("album");
        }
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
        textView_noidung.setText(album.getTenAlbum());
        textView_tencasi.setText(album.getTenCaSiAlbum());
        Picasso.with(Activity_dsBaiHat_tuAlbum.this).load(album.getHinhAlbum()).into(imageView_hinh);

    }

    private void anhxa()
    {
        imageButton_moreoption = findViewById(R.id.imgbutton_moreoption_dsbaihat);
        collapsingToolbarLayout = findViewById(R.id.collapsingToolbar_dsbaihat);
        textView_noidung = findViewById(R.id.txtview_noidung_dsbaihat);
        textView_tencasi = findViewById(R.id.txtview_tencasi_dsbaihat);
        imageView_hinh = findViewById(R.id.imgview_hinh_dsbaihat);
        toolbar = findViewById(R.id.Toolbar_dsbaihat);
        recyclerView = findViewById(R.id.recycleview_dsbaihat);
        loadingPanel = findViewById(R.id.loadingPanel_dsbaihat);
    }
}