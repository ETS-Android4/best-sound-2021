package com.example.appnhac.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appnhac.Activity.Activity_PlayMusic;
import com.example.appnhac.Model.BaiHat;
import com.example.appnhac.R;
import com.squareup.picasso.Picasso;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class dsBaiHat_SearchAdapter extends RecyclerView.Adapter<dsBaiHat_SearchAdapter.ViewHolder>
{
    Context context;
    ArrayList<BaiHat> baiHats;

    public dsBaiHat_SearchAdapter(Context context, ArrayList<BaiHat> baiHats)
    {
        this.context = context;
        this.baiHats = baiHats;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.dong_dsbaihat_search,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull dsBaiHat_SearchAdapter.ViewHolder holder, int position)
    {
        BaiHat baiHat = baiHats.get(position);
        holder.textView_tenbh.setText(baiHat.getTenBaiHat());
        Picasso.with(context).load(baiHat.getHinhBaiHat()).into(holder.imageView_hinhbh);
        holder.textView_tencasi.setText(baiHat.getCaSi());
        holder.pos = position;
    }

    @Override
    public int getItemCount()
    {
        return baiHats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView textView_tenbh;
        ImageView imageView_hinhbh;
        TextView textView_tencasi;
        int pos = 0;

        public ViewHolder(@NonNull @NotNull View itemView)
        {
            super(itemView);
            textView_tenbh = itemView.findViewById(R.id.txtview_tenbh_search);
            imageView_hinhbh = itemView.findViewById(R.id.imgview_hinhbh_search);
            textView_tencasi = itemView.findViewById(R.id.txtview_casi_search);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent intent = new Intent(context, Activity_PlayMusic.class);
                    intent.putExtra("baihat", baiHats.get(pos));
                    intent.putExtra("mangbaihat", baiHats);
                    context.startActivity(intent);
                }
            });

        }
    }
}
