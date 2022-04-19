package com.example.appnhac.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
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
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.example.appnhac.Activity.MainActivity.show_keybroad;

public class Fragment_Tim_Kiem extends Fragment
{
    View view;
    public static EditText editText_search;
    TextView textView_thongbao_search;
    RecyclerView recyclerView_search;
    ImageButton imageButton_search;
    ArrayList<BaiHat> baiHats;
    dsBaiHatAdapter dsBaiHat_Adapter;
    RelativeLayout loadingPanel;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @org.jetbrains.annotations.NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_tim_kiem,container,false);

        anhxa();
        loadingPanel.setVisibility(View.GONE);
        editText_search.requestFocus();

        handle_search();
        handle_editText();

        return view;
    }

    private void reset_result()
    {
        if (baiHats != null && baiHats.size() > 0)
        {
            baiHats.clear();
            dsBaiHat_Adapter.notifyDataSetChanged();
        }
    }

    private void handle_editText()
    {
        editText_search.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
            {
                if (editText_search.getText().toString().length() == 0)
                {
                    textView_thongbao_search.setText("");
                    reset_result();
                }
            }

            @Override
            public void afterTextChanged(Editable editable)
            {

            }
        });
        editText_search.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                show_keybroad = true;
            }
        });
    }

    @Override
    public void onPause()
    {
        if (show_keybroad)
        {
            View view = getActivity().getCurrentFocus();
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
            show_keybroad = false;
        }
        super.onPause();
    }

    private void handle_search()
    {
        imageButton_search.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (editText_search.getText().toString().length() > 0)
                {
                    textView_thongbao_search.setText("");
                    reset_result();
                    loadingPanel.setVisibility(View.VISIBLE);
                    GetData();

                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                    show_keybroad = false;
                }
            }
        });
    }

    private void GetData()
    {
        DataService dataService = APIService.getService();
        Call<List<BaiHat>> callback = dataService.Search_BaiHat(editText_search.getText().toString().toLowerCase());
        callback.enqueue(new Callback<List<BaiHat>>()
        {
            @Override
            public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response)
            {
                baiHats = (ArrayList<BaiHat>) response.body();
                loadingPanel.setVisibility(View.GONE);
                if (baiHats.size() > 0)
                {
                    textView_thongbao_search.setText("Tìm thấy " + baiHats.size() + " kết quả");
                }
                else
                {
                    textView_thongbao_search.setText("Không có kết quả phù hợp");
                }
                dsBaiHat_Adapter = new dsBaiHatAdapter(getActivity(), baiHats);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                recyclerView_search.setLayoutManager(linearLayoutManager);

                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView_search.getContext(),
                        DividerItemDecoration.VERTICAL);
                recyclerView_search.addItemDecoration(dividerItemDecoration);

                recyclerView_search.setAdapter(dsBaiHat_Adapter);
            }
            @Override
            public void onFailure(Call<List<BaiHat>> call, Throwable t)
            {

            }
        });
    }

    private void anhxa()
    {
        editText_search = view.findViewById(R.id.etxt_search);
        textView_thongbao_search = view.findViewById(R.id.txtview_thongbao_search);
        recyclerView_search = view.findViewById(R.id.recycleview_search);
        imageButton_search = view.findViewById(R.id.imgbtn_search);
        loadingPanel =  view.findViewById(R.id.loadingPanel_timkiem);
    }
}
