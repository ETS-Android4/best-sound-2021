package com.example.appnhac.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.appnhac.Activity.Activity_PlayMusic;
import com.example.appnhac.Model.BaiHat;
import com.example.appnhac.R;
import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.util.ArrayList;
import static com.example.appnhac.Activity.Activity_PlayMusic.baiHat_isplaying;
import static com.example.appnhac.Activity.Activity_PlayMusic.is_pause;
import static com.example.appnhac.Activity.Activity_PlayMusic.isclick_tudsbaihat;
import static com.example.appnhac.Activity.Activity_PlayMusic.mediaPlayer;
import static com.example.appnhac.Activity.Activity_dsBaiHatYeuThich.baiHats_BaiHatYeuThich;
import static com.example.appnhac.Activity.Activity_dsBaiHat_DaTaiXuong.baiHats_DaTaiXuong;
import static com.example.appnhac.Activity.Activity_dsBaiHat_DaTaiXuong.dsBaiHat_daTaiXuongAdapter;
import static com.example.appnhac.Activity.MainActivity.dsid_BaiHatDaTaiXuong;
import static com.example.appnhac.Activity.MainActivity.dsid_BaiHatYeuThich;
import static com.example.appnhac.Activity.MainActivity.get_Pos_Have_ID1;
import static com.example.appnhac.Activity.MainActivity.get_Pos_Have_ID2;
import static com.example.appnhac.Activity.MainActivity.haveData_PlayMinium;

import static com.example.appnhac.Activity.MainActivity.isPlay_LastSong;
import static com.example.appnhac.Activity.MainActivity.isclick_fromoffline;
import static com.example.appnhac.Activity.MainActivity.reset_Playmusic_minium;
import static com.example.appnhac.Activity.MainActivity.reset_Playmusic_minium2;

public class dsBaiHat_DaTaiXuongAdapter extends RecyclerView.Adapter<dsBaiHat_DaTaiXuongAdapter.ViewHolder>
{
    Context context;
    ArrayList<BaiHat> baiHats;

    public dsBaiHat_DaTaiXuongAdapter(Context context, ArrayList<BaiHat> baiHats)
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
        View view = layoutInflater.inflate(R.layout.dong_baihat_dataixuong,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull dsBaiHat_DaTaiXuongAdapter.ViewHolder holder, int position)
    {
        BaiHat baiHat = baiHats.get(position);
        holder.imageView_hinh.setImageResource(R.drawable.main_image);
        holder.textView_tenbh.setText(baiHat.getTenBaiHat());
        holder.textView_tencasi.setText(baiHat.getCaSi());
        holder.pos = position;
    }

    @Override
    public int getItemCount()
    {
        return baiHats.size();
    }

    private void handle_fromDongBaiHat_DaTaiXuong(int pos)
    {
        String[] option = {"Xoá bài hát"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setItems(option, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                if (which == 0)
                {
                    String id = baiHats_DaTaiXuong.get(pos).getIdBaiHat();
                    String uri = baiHats_DaTaiXuong.get(pos).getLinkBaiHat();
                    File file = new File (uri);
                    file.delete();

                    int vt1 = get_Pos_Have_ID1(dsid_BaiHatDaTaiXuong,id);
                    int vt2 = get_Pos_Have_ID2(baiHats_DaTaiXuong,id);

                    dsid_BaiHatDaTaiXuong.remove(vt1);
                    baiHats_DaTaiXuong.remove(vt2);
                    dsBaiHat_daTaiXuongAdapter.notifyDataSetChanged();
                    Toast.makeText(context, "Đã xoá", Toast.LENGTH_SHORT).show();

                    // nếu đang phát bài đang xoá thì reset
                    if (baiHat_isplaying.getIdBaiHat().equals(id))
                    {
                        if (mediaPlayer != null)
                            mediaPlayer.stop();
                        reset_Playmusic_minium2(context);
                        reset_Playmusic_minium(context);
                        haveData_PlayMinium = false;
                    }
                }
            }
        });
        builder.show();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView_hinh;
        TextView textView_tenbh;
        TextView textView_tencasi;
        ImageButton imageButton_moreoption;
        ImageView imageView_background;
        int pos = 0;

        public ViewHolder(@NonNull @NotNull View itemView)
        {
            super(itemView);
            imageView_hinh = itemView.findViewById(R.id.imgview_hinhbh_dataixuong);
            textView_tenbh = itemView.findViewById(R.id.txtview_ten_dataixuong);
            textView_tencasi = itemView.findViewById(R.id.txtview_casi_dataixuong);
            imageButton_moreoption= itemView.findViewById(R.id.icon_moreoption_dataixuong);
            imageView_background = itemView.findViewById(R.id.imgviewBackground_dataixuong);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    imageView_background.setBackground(context.getResources().getDrawable(R.drawable.custom_background2));
                    // TH bài hát đang được bật nhưng người dùng lại click vô lần nữa
                    final Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run()
                        {
                            if (mediaPlayer != null)
                            {
                                if (baiHat_isplaying.getIdBaiHat().equals(baiHats.get(pos).getIdBaiHat()))
                                    isclick_tudsbaihat = true;
                            }

                            isclick_fromoffline = true;
                            isPlay_LastSong = false;
                            Intent intent = new Intent(context, Activity_PlayMusic.class);
                            intent.putExtra("baihat", baiHats.get(pos));
                            intent.putExtra("mangbaihat", baiHats);
                            imageView_background.setBackgroundResource(R.color.white);
                            context.startActivity(intent);
                        }
                    }, 80);
                }
            });
            imageButton_moreoption.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    handle_fromDongBaiHat_DaTaiXuong(pos);
                }
            });
        }
    }
}
