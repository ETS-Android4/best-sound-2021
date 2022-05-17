package com.example.appnhac.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appnhac.Activity.Activity_dsTheLoai;
import com.example.appnhac.Model.ChuDe;
import com.example.appnhac.R;
import com.squareup.picasso.Picasso;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class Ds_ChudeAdapter extends RecyclerView.Adapter<Ds_ChudeAdapter.ViewHolder>
{
    Context context;
    ArrayList<ChuDe> chuDes;

    public Ds_ChudeAdapter(Context context, ArrayList<ChuDe> chuDes)
    {
        this.context = context;
        this.chuDes = chuDes;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.dong_ds_chude,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Ds_ChudeAdapter.ViewHolder holder, int position)
    {
        ChuDe chuDe = chuDes.get(position);
        Picasso.with(context).load(chuDe.getHinhChuDe()).into(holder.imgview_hinhchude);
        holder.pos = position;
    }

    @Override
    public int getItemCount()
    {
        return chuDes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imgview_hinhchude;
        int pos = 0;

        public ViewHolder(@NonNull @NotNull View itemView)
        {
            super(itemView);
            imgview_hinhchude = itemView.findViewById(R.id.imgview_hinh_dsChude);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent intent = new Intent(context, Activity_dsTheLoai.class);
                    intent.putExtra("theloai", chuDes.get(pos));
                    context.startActivity(intent);
                }
            });
        }
    }
}
