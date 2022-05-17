package com.example.appnhac.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import com.example.appnhac.Adapter.BannerAdapter;
import com.example.appnhac.Model.Quangcao;
import com.example.appnhac.R;
import com.example.appnhac.Service.APIService;
import com.example.appnhac.Service.DataService;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Banner extends Fragment
{
    View view;
    ViewPager viewPager1;
    CircleIndicator circleIndicator;
    BannerAdapter bannerAdapter;
    Runnable runnable;
    Handler handler;
    int currerent_item ;


    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_banner,container,false);

        anhxa();
        GetData();
        return view;
    }

    private void anhxa()
    {
        viewPager1 = view.findViewById(R.id.myViewPager1);
        circleIndicator = view.findViewById(R.id.indicator_default);
    }

    private void GetData()
    {
        DataService dataService = APIService.getService();
        Call<List<Quangcao>> callback = dataService.GetDataBanner();
        callback.enqueue(new Callback<List<Quangcao>>()
        {
            @Override
            public void onResponse(Call<List<Quangcao>> call, Response<List<Quangcao>> response)
            {
                ArrayList<Quangcao> banners = (ArrayList<Quangcao>) response.body();

                bannerAdapter = new BannerAdapter(banners, getActivity());

                viewPager1.setAdapter(bannerAdapter);
                circleIndicator.setViewPager(viewPager1);
                handler = new Handler();
                runnable = () ->
                {
                    currerent_item = viewPager1.getCurrentItem();
                    currerent_item++;
                    if (viewPager1.getAdapter() != null)
                        if (currerent_item >= viewPager1.getAdapter().getCount())
                            currerent_item = 0;
                    viewPager1.setCurrentItem(currerent_item,true);
                    handler.postDelayed(runnable,3000);
                };
                handler.postDelayed(runnable,3000);
            }
            @Override
            public void onFailure(Call<List<Quangcao>> call, Throwable t)
            {
            }
        });
    }
}
