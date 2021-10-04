package com.example.appnhac.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.appnhac.Activity.Activity_AlbumMoi;
import com.example.appnhac.Activity.Activity_AllTheLoai;
import com.example.appnhac.Activity.Activity_PlayListHot;
import com.example.appnhac.Activity.Activity_Top100;
import com.example.appnhac.R;
import org.jetbrains.annotations.NotNull;

public class Fragment_Menu extends Fragment
{
    View view;
    ImageButton imageButton_menu_top100;
    ImageButton imageButton_menu_alltheloai;
    ImageButton imageButton_menu_albummoi;
    ImageButton imageButton_menu_playlisthot;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_menu,container,false);

        anhxa();
        handle_top100();
        handle_alltheloai();
        handle_albummoi();
        handle_playlisthot();

        return view;
    }

    private void handle_playlisthot()
    {
        imageButton_menu_playlisthot.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getContext(), Activity_PlayListHot.class);
                startActivity(intent);
            }
        });
    }

    private void handle_albummoi()
    {
        imageButton_menu_albummoi.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getContext(), Activity_AlbumMoi.class);
                startActivity(intent);
            }
        });
    }

    private void handle_alltheloai()
    {
        imageButton_menu_alltheloai.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getContext(), Activity_AllTheLoai.class);
                startActivity(intent);
            }
        });
    }

    private void anhxa()
    {
        imageButton_menu_top100 = view.findViewById(R.id.imgbutton_menu_top100);
        imageButton_menu_alltheloai = view.findViewById(R.id.imgbutton_menu_alltheloai);
        imageButton_menu_albummoi = view.findViewById(R.id.imgbutton_menu_albummoi);
        imageButton_menu_playlisthot = view.findViewById(R.id.imgbutton_menu_playlisthot);
    }

    private void handle_top100()
    {
        imageButton_menu_top100.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getContext(), Activity_Top100.class);
                startActivity(intent);
            }
        });
    }
}
