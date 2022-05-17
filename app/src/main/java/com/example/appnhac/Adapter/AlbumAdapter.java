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

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHoler>
{
    Context context;
    ArrayList<Album> albums;

    public AlbumAdapter(Context context, ArrayList<Album> albums)
    {
        this.context = context;
        this.albums = albums;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHoler onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.dong_album,null);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AlbumAdapter.ViewHoler holder, int position)
    {
        Album album = albums.get(position);
        Picasso.with(context).load(album.getHinhAlbum()).into(holder.imgview_hinhAlbum);
        holder.txtview_tenAlbum.setText(album.getTenAlbum());
        holder.txtview_tenCasiAlbum.setText(album.getTenCaSiAlbum());
        holder.pos = position;

    }

    @Override
    public int getItemCount()
    {
        return albums.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder
    {
        ImageView imgview_hinhAlbum;
        TextView txtview_tenAlbum;
        TextView txtview_tenCasiAlbum;
        int pos = 0;

        public ViewHoler(@NonNull @NotNull View itemView)
        {
            super(itemView);

            imgview_hinhAlbum = itemView.findViewById(R.id.img_hinhAlbum);
            txtview_tenAlbum = itemView.findViewById(R.id.txtview_tenAlbum);
            txtview_tenCasiAlbum = itemView.findViewById(R.id.txtview_tenCasiAlbum);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent intent = new Intent(context, Activity_dsBaiHat_tuAlbum.class);
                    intent.putExtra("album",albums.get(pos));
                    context.startActivity(intent);
                }
            });
        }
    }
}
