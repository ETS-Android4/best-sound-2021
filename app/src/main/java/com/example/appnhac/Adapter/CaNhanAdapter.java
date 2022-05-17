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
import com.example.appnhac.Activity.Activity_AlbumYeuThich;
import com.example.appnhac.Activity.Activity_PlaylistYeuThich;
import com.example.appnhac.Activity.Activity_dsBaiHatYeuThich;
import com.example.appnhac.Activity.Activity_dsBaiHat_DaTaiXuong;
import com.example.appnhac.Model.DongCaNhan;
import com.example.appnhac.R;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class CaNhanAdapter extends RecyclerView.Adapter<CaNhanAdapter.ViewHolder>
{
    Context context;
    ArrayList<DongCaNhan> dongCaNhans;

    public CaNhanAdapter(Context context, ArrayList<DongCaNhan> dongCaNhans)
    {
        this.context = context;
        this.dongCaNhans = dongCaNhans;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.dong_canhan,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CaNhanAdapter.ViewHolder holder, int position)
    {
        DongCaNhan dongCaNhan = dongCaNhans.get(position);
        holder.imageView_hinh.setImageResource(dongCaNhan.getImage());
        holder.textView_title.setText(dongCaNhan.getTitle());
        holder.textView_soluong.setText(dongCaNhan.getSoluong());
        holder.function = dongCaNhan.getFunction();
    }

    @Override
    public int getItemCount()
    {
        return dongCaNhans.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView textView_title;
        TextView textView_soluong;
        ImageView imageView_hinh;
        int function = 0;

        public ViewHolder(@NonNull @NotNull View itemView)
        {
            super(itemView);
            textView_title = itemView.findViewById(R.id.txtview_title_dongcanhan);
            textView_soluong = itemView.findViewById(R.id.txtview_soluong_dongcanhan);
            imageView_hinh = itemView.findViewById(R.id.imgview_hinh_dongcanhan);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if (function == 1)
                    {
                        Intent intent = new Intent(context, Activity_dsBaiHat_DaTaiXuong.class);
                        context.startActivity(intent);
                    } else if (function == 2)
                    {
                        Intent intent = new Intent(context, Activity_dsBaiHatYeuThich.class);
                        context.startActivity(intent);
                    }
                    else if (function == 3)
                    {
                        Intent intent = new Intent(context, Activity_AlbumYeuThich.class);
                        context.startActivity(intent);
                    }
                    else if (function == 4)
                    {
                        Intent intent = new Intent(context, Activity_PlaylistYeuThich.class);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }
}
