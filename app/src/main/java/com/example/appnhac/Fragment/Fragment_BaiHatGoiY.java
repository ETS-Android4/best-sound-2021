package com.example.appnhac.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appnhac.Adapter.dsBaiHatAdapter;
import com.example.appnhac.Model.BaiHat;
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

public class Fragment_BaiHatGoiY extends Fragment
{
    View view;
    TextView textView_title_baihatgoiy;
    ArrayList<BaiHat> baiHats;
    RecyclerView recyclerView_baiHatGoiY;
    dsBaiHatAdapter dsBaiHat_Adapter;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_baihatgoiy, container, false);

        anhxa();
        init();
        getData();
        return view;
    }

    private void init()
    {
    }

    private void anhxa()
    {
        recyclerView_baiHatGoiY = view.findViewById(R.id.recycleview_baihatgoiy);
        textView_title_baihatgoiy = view.findViewById(R.id.txtview_title_baihatgoiy);

        ArrayList<String> title = new ArrayList<>();
        title.add("Có thể bạn sẽ thích");
        title.add("Gợi ý cho bạn");
        title.add("Nhạc mới phát hành");
        title.add("Các ca khúc được nghe nhiều nhất");
        title.add("Chill Hits");
        title.add("Top Hits Việt Nam");

        Random random = new Random();
        int i = random.nextInt(title.size());
        textView_title_baihatgoiy.setText(title.get(i));
    }

    private void getData()
    {
        DataService dataService = APIService.getService();
        Call<List<BaiHat>> callback = dataService.GetDataBaiHatGoiY();
        callback.enqueue(new Callback<List<BaiHat>>()
        {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response)
            {
                baiHats = (ArrayList<BaiHat>) response.body();

                dsBaiHat_Adapter = new dsBaiHatAdapter(getActivity(), baiHats);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView_baiHatGoiY.setLayoutManager(linearLayoutManager);

                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView_baiHatGoiY.getContext(),
                        DividerItemDecoration.VERTICAL);
                recyclerView_baiHatGoiY.addItemDecoration(dividerItemDecoration);

                recyclerView_baiHatGoiY.setAdapter(dsBaiHat_Adapter);
            }

            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t)
            {

            }
        });
    }
}
