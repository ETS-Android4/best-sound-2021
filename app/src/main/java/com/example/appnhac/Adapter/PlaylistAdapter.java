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
import com.example.appnhac.Activity.Activity_dsBaiHat_tuPlaylist;
import com.example.appnhac.Model.Playlist;
import com.example.appnhac.R;
import com.squareup.picasso.Picasso;
import org.jetbrains.annotations.NotNull;
import java.io.Serializable;
import java.util.ArrayList;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.ViewHolder>
{
    Context context;
    ArrayList<Playlist> playlists;

    public PlaylistAdapter(Context context, ArrayList<Playlist> playlists)
    {
        this.context = context;
        this.playlists = playlists;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.dong_playlist,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PlaylistAdapter.ViewHolder holder, int position)
    {
        Playlist playlist = playlists.get(position);
        Picasso.with(context).load(playlist.getHinhIcon()).into(holder.imageView_hinh_playlist);
        holder.txtView_ten_playlist.setText(playlist.getTen());
        holder.txtView_tencasi_playlist.setText(playlist.getTenCaSi());
        holder.pos = position;
    }

    @Override
    public int getItemCount()
    {
        return playlists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView_hinh_playlist;
        TextView txtView_ten_playlist;
        TextView txtView_tencasi_playlist;
        int pos = 0 ;

        public ViewHolder(@NonNull @NotNull View itemView)
        {
            super(itemView);
            imageView_hinh_playlist = itemView.findViewById(R.id.img_hinhPlaylist);
            txtView_ten_playlist = itemView.findViewById(R.id.txtview_tenPlaylist);
            txtView_tencasi_playlist = itemView.findViewById(R.id.txtview_tenCasiPlaylist);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent intent = new Intent(context, Activity_dsBaiHat_tuPlaylist.class);
                    intent.putExtra("playlist", (Serializable) playlists.get(pos));
                    context.startActivity(intent);
                }
            });
        }
    }

}
