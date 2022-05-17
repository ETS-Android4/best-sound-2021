package com.example.appnhac.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appnhac.Activity.Activity_dsBaiHat_tu_TheLoai;
import com.example.appnhac.Model.TheLoai;
import com.example.appnhac.R;
import com.squareup.picasso.Picasso;
import org.jetbrains.annotations.NotNull;
import java.io.Serializable;
import java.util.ArrayList;

public class Ds_TheLoaiAdapter extends RecyclerView.Adapter<Ds_TheLoaiAdapter.ViewHolder>
{
    Context context;
    ArrayList<TheLoai> theLoais;

    public Ds_TheLoaiAdapter(Context context, ArrayList<TheLoai> theLoais)
    {
        this.context = context;
        this.theLoais = theLoais;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.dong_ds_theloai, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Ds_TheLoaiAdapter.ViewHolder holder, int position)
    {
        TheLoai theLoai = theLoais.get(position);
        Picasso.with(context).load(theLoai.getHinhTheLoai()).into(holder.imageView_hinhtheloai);
        holder.pos = position;
    }

    @Override
    public int getItemCount()
    {
        return theLoais.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView_hinhtheloai;
        int pos = 0;

        public ViewHolder(@NonNull @NotNull View itemView)
        {
            super(itemView);
            imageView_hinhtheloai = itemView.findViewById(R.id.hinh_theloai);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent intent = new Intent(context, Activity_dsBaiHat_tu_TheLoai.class);
                    intent.putExtra("theloai", (Serializable) theLoais.get(pos));
                    context.startActivity(intent);
                }
            });
        }
    }
}
