package com.example.appnhac.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appnhac.Adapter.CaNhanAdapter;
import com.example.appnhac.Model.DongCaNhan;
import com.example.appnhac.R;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import static com.example.appnhac.Activity.MainActivity.dsid_AlbumYeuThich;
import static com.example.appnhac.Activity.MainActivity.dsid_BaiHatDaTaiXuong;
import static com.example.appnhac.Activity.MainActivity.dsid_BaiHatYeuThich;
import static com.example.appnhac.Activity.MainActivity.dsid_PlaylistYeuThich;
import static com.example.appnhac.Fragment.Fragment_Tim_Kiem.editText_search;

public class Fragment_CaNhan extends Fragment
{
    View view;
    RecyclerView recyclerView_canhan;
    ArrayList<DongCaNhan> dongCaNhans = new ArrayList<>();
    CaNhanAdapter caNhanAdapter;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_canhan, container, false);

        anhxa();
        init();
        return view;
    }

    private void init()
    {
        caNhanAdapter = new CaNhanAdapter(getActivity(),dongCaNhans);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView_canhan.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView_canhan.getContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView_canhan.addItemDecoration(dividerItemDecoration);

        recyclerView_canhan.setAdapter(caNhanAdapter);

    }

    private void anhxa()
    {
        recyclerView_canhan = view.findViewById(R.id.recyclerview_canhan);
    }


    @Override
    public void onResume()
    {
        editText_search.setText("");
        super.onResume();
        dongCaNhans.clear();
        dongCaNhans.add(new DongCaNhan("Đã tải xuống",dsid_BaiHatDaTaiXuong.size() + " Bài Hát",1,R.drawable.icon_baihatdataixuong));
        dongCaNhans.add(new DongCaNhan("Bài hát yêu thích", dsid_BaiHatYeuThich.size() + " Bài Hát",2,R.drawable.icon_baihatyeuthich));
        dongCaNhans.add(new DongCaNhan("Album yêu thích",dsid_AlbumYeuThich.size() + " Album",3,R.drawable.icon_albumyeuthich));
        dongCaNhans.add(new DongCaNhan("PlayList yêu thích",dsid_PlaylistYeuThich.size() + " Playlist",4,R.drawable.icon_playlistyeuthich));
        caNhanAdapter.notifyDataSetChanged();
    }
}
