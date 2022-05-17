package com.example.appnhac.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.appnhac.Model.BaiHat;
import com.example.appnhac.R;
import com.squareup.picasso.Picasso;
import org.jetbrains.annotations.NotNull;

public class Fragment_HinhBaiHat_PlayMusic extends Fragment
{
    View view;
    public static ImageView imageView_hinhbh_playmusic;
    BaiHat baiHat;

    public Fragment_HinhBaiHat_PlayMusic(BaiHat baiHat)
    {
        this.baiHat = baiHat;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_hinhbaihat_playmusic,null);

        imageView_hinhbh_playmusic = view.findViewById(R.id.imageview_hinhbh_playmusic);
        if (baiHat.getHinhBaiHat().equals("Offline"))
            imageView_hinhbh_playmusic.setImageResource(R.drawable.main_image);
        else Picasso.with(getContext()).load(baiHat.getHinhBaiHat()).into(imageView_hinhbh_playmusic);

        return view;
    }
}
