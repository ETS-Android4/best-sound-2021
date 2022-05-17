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
import com.example.appnhac.Activity.Activity_dsPlaylist;
import com.example.appnhac.Adapter.PlaylistAdapter;
import com.example.appnhac.Model.Playlist;
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

public class Fragment_Playlist extends Fragment
{
    View view;
    TextView txtview_title_playlist;
    RecyclerView recycleView_playlist;
    TextView txtview_more_playlist;
    PlaylistAdapter playlistAdapter;
    ArrayList<Playlist> arrayList;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_playlist,container,false);

        anhxa();
        GetData();
        txtview_more_playlist.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getContext(), Activity_dsPlaylist.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void anhxa()
    {
        txtview_title_playlist = view.findViewById(R.id.txtview_title_playlist);
        recycleView_playlist = view.findViewById(R.id.recycleview_playlist);
        txtview_more_playlist = view.findViewById(R.id.txtview_morePlaylist);

        ArrayList<String> title_playlist = new ArrayList<>();
        title_playlist.add("Nghe gì hôm nay ?");
        title_playlist.add("Tận hưởng cuộc sống, chill");
        title_playlist.add("Mới phát hành");
        title_playlist.add("Top PlayList");
        title_playlist.add("PlayList mới");
        title_playlist.add("PlayList Hot");
        title_playlist.add("PlayList mới phát hành");
        title_playlist.add("Nhịp điệu cảm xúc");
        Random random = new Random();
        int i = random.nextInt(title_playlist.size());
        txtview_title_playlist.setText(title_playlist.get(i));
    }

    private void GetData()
    {
        DataService dataService = APIService.getService();
        Call<List<Playlist>> callback = dataService.GetDataPlaylist();
        callback.enqueue(new Callback<List<Playlist>>()
        {
            @Override
            public void onResponse(Call<List<Playlist>> call, Response<List<Playlist>> response)
            {
                arrayList = (ArrayList<Playlist>) response.body();
                playlistAdapter = new PlaylistAdapter(getActivity(),arrayList);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);

                recycleView_playlist.setLayoutManager(linearLayoutManager);
                recycleView_playlist.setAdapter(playlistAdapter);
            }

            @Override
            public void onFailure(Call<List<Playlist>> call, Throwable t)
            {

            }
        });
    }
}
