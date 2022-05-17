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
import com.example.appnhac.Activity.Activity_DsChuDe;
import com.example.appnhac.Adapter.ChuDeAdapter;
import com.example.appnhac.Model.ChuDe;
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

public class Fragment_ChuDe extends Fragment
{
    View view;
    RecyclerView recyclerView;
    TextView txtview_xemthem;
    ArrayList<ChuDe> chude;
    ChuDeAdapter chuDeAdapter;
    TextView txtview_title_chude;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_chude,container,false);

        anhxa();
        GetData();
        txtview_xemthem.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getContext(),Activity_DsChuDe.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void anhxa()
    {
        recyclerView = view.findViewById(R.id.recyclerview_chude);
        txtview_xemthem = view.findViewById(R.id.txtview_xemthem_chude);
        txtview_title_chude = view.findViewById(R.id.txtview_title_chude);

        ArrayList<String> title = new ArrayList<>();
        title.add("Chủ đề Hot");
        title.add("Chủ đề mới");

        Random random = new Random();
        int i = random.nextInt(title.size());
        txtview_title_chude.setText(title.get(i));
    }
    private void GetData()
    {
        DataService dataService = APIService.getService();
        Call<List<ChuDe>> callback = dataService.GetDataChuDe();
        callback.enqueue(new Callback<List<ChuDe>>()
        {
            @Override
            public void onResponse(Call<List<ChuDe>> call, Response<List<ChuDe>> response)
            {
                chude = (ArrayList<ChuDe>) response.body();
                chuDeAdapter = new ChuDeAdapter(getActivity(),chude);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(chuDeAdapter);

            }

            @Override
            public void onFailure(Call<List<ChuDe>> call, Throwable t)
            {

            }
        });
    }
}
