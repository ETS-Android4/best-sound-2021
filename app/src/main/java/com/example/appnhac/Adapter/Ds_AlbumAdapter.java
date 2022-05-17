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
import com.example.appnhac.Activity.Activity_dsBaiHat_tuAlbum;
import com.example.appnhac.Model.Album;
import com.example.appnhac.R;
import com.squareup.picasso.Picasso;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;

public class Ds_AlbumAdapter extends RecyclerView.Adapter<Ds_AlbumAdapter.ViewHolder>
{
    Context context;
    ArrayList<Album> albums;

    public Ds_AlbumAdapter(Context context, ArrayList<Album> albums)
    {
        this.context = context;
        this.albums = albums;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.dong_ds_getall,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Ds_AlbumAdapter.ViewHolder holder, int position)
    {
        Album album = albums.get(position);
        Picasso.with(context).load(album.getHinhAlbum()).into(holder.imageView_hinh);
        holder.txtView_ten.setText(album.getTenAlbum());
        holder.txtView_tencasi.setText(album.getTenCaSiAlbum());
        holder.pos = position;
    }

    @Override
    public int getItemCount()
    {
        return albums.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView_hinh;
        TextView txtView_ten;
        TextView txtView_tencasi;
        int pos = 0;

        public ViewHolder(@NonNull @NotNull View itemView)
        {
            super(itemView);
            imageView_hinh = itemView.findViewById(R.id.imgview_hinh_dsGetAll);
            txtView_ten = itemView.findViewById(R.id.txtview_ten_dsGetAll);
            txtView_tencasi = itemView.findViewById(R.id.txtview_tencasi_dsGetAll);

            itemView.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick (View view)
                {
                    Intent intent = new Intent(context, Activity_dsBaiHat_tuAlbum.class);
                    intent.putExtra("album", albums.get(pos));
                    context.startActivity(intent);
                }
            });
        }
    }
}
