package com.example.appnhac.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import com.example.appnhac.Activity.Activity_dsBaiHat_tuQC;
import com.example.appnhac.Model.Quangcao;
import com.example.appnhac.R;
import com.squareup.picasso.Picasso;
import org.jetbrains.annotations.NotNull;
import java.io.Serializable;
import java.util.ArrayList;

public class BannerAdapter extends PagerAdapter
{
    ArrayList<Quangcao> quangcaoArrayList;
    Context context;

    public BannerAdapter(ArrayList<Quangcao> quangcaoArrayList, Context context)
    {
        this.quangcaoArrayList = quangcaoArrayList;
        this.context = context;
    }

    @Override
    public int getCount()
    {
        return quangcaoArrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull @NotNull View view, @NonNull @NotNull Object object)
    {
        return view == object;
    }

    @NonNull
    @NotNull
    @Override
    public Object instantiateItem(@NonNull @NotNull ViewGroup container, int position)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.dong_banner,null);

        ImageView img_background_banner = view.findViewById(R.id.imgviewbackground_banner);
        Picasso.with(context).load(quangcaoArrayList.get(position).getHinhAnh()).into(img_background_banner);

        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(context, Activity_dsBaiHat_tuQC.class);
                intent.putExtra("quangcao", (Serializable) quangcaoArrayList.get(position));
                context.startActivity(intent);
            }
        });
        
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull @NotNull ViewGroup container, int position, @NonNull @NotNull Object object)
    {
        container.removeView((View) object);
    }

}
