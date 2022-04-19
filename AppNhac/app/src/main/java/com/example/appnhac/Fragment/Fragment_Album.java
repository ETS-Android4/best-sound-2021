package com.example.appnhac.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appnhac.Activity.Activity_dsAlbum;
import com.example.appnhac.Adapter.AlbumAdapter;
import com.example.appnhac.Model.Album;
import com.example.appnhac.R;
import com.example.appnhac.Service.APIService;
import com.example.appnhac.Service.DataService;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Album extends Fragment
{
    View view;
    ArrayList<Album> albums;
    TextView txtview_xemthemalbum;
    RecyclerView recyclerView_album;
    AlbumAdapter albumAdapter;
    TextView txtview_title_album;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_album,container,false);

        anhxa();
        getData();
        txtview_xemthemalbum.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getContext(), Activity_dsAlbum.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void anhxa()
    {
        txtview_xemthemalbum = view.findViewById(R.id.txtview_xemthem_album);
        recyclerView_album = view.findViewById(R.id.recyclerview_album);
        txtview_title_album = view.findViewById(R.id.txtview_title_album);

        ArrayList<String> title = new ArrayList<>();
        title.add("Album Hot");
        title.add("Album mới");
        title.add("Album được nghe nhiều nhất");
        title.add("Album dành cho bạn");

        Random random = new Random();
        int i = random.nextInt(title.size());
        txtview_title_album.setText(title.get(i));
    }

    private void getData()
    {
        DataService dataService = APIService.getService();
        Call<List<Album>> callback = dataService.GetDataAlbum();
        callback.enqueue(new Callback<List<Album>>()
        {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response)
            {
                albums = (ArrayList<Album>) response.body();
                albumAdapter = new AlbumAdapter(getActivity(),albums);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());

                linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
                
                recyclerView_album.setLayoutManager(linearLayoutManager);
                recyclerView_album.setAdapter(albumAdapter);
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t)
            {

            }
        });
    }
}

