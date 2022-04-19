package com.example.appnhac.Adapter;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
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
import com.example.appnhac.Service.APIService;
import com.example.appnhac.Service.DataService;
import com.squareup.picasso.Picasso;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static android.content.Context.DOWNLOAD_SERVICE;
import static com.example.appnhac.Activity.Activity_PlayMusic.baiHat_isplaying;
import static com.example.appnhac.Activity.Activity_PlayMusic.isclick_tudsbaihat;
import static com.example.appnhac.Activity.Activity_PlayMusic.mediaPlayer;
import static com.example.appnhac.Activity.MainActivity.check_duplicateId;
import static com.example.appnhac.Activity.MainActivity.dsid_BaiHatDaTaiXuong;
import static com.example.appnhac.Activity.MainActivity.dsid_BaiHatYeuThich;
import static com.example.appnhac.Activity.MainActivity.inmode_dsBaiHatYeuThich;

public class Top100Adapter extends RecyclerView.Adapter<Top100Adapter.ViewHolder>
{
    Context context;
    ArrayList<BaiHat> baiHats;

    public Top100Adapter(Context context, ArrayList<BaiHat> baiHats)
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
        View view = layoutInflater.inflate(R.layout.dong_top100,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Top100Adapter.ViewHolder holder, int position)
    {
        BaiHat baiHat = baiHats.get(position);
        holder.textView_tencasi.setText(baiHat.getCaSi());
        holder.textView_tenbh.setText(baiHat.getTenBaiHat());
        holder.textView_stt.setText(String.valueOf((position+1)));
        Picasso.with(context).load(baiHat.getHinhBaiHat()).into(holder.imageView_hinhbh);
        holder.pos = position;
    }

    @Override
    public int getItemCount()
    {
        return baiHats.size();
    }

    private void downloading_fromDongBaiHat(int pos)
    {
        // get BaiHat
        BaiHat baiHat = baiHats.get(pos);

        // get URL
        String Url = baiHat.getLinkBaiHat();

        // Create Download Request
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(Url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("Download");
        request.setDescription("Downloading file...");

        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_MUSIC,
                baiHat.getTenBaiHat() + "_" + baiHat.getCaSi() + "_" +
                        baiHat.getIdBaiHat() + "Offline_.mp3");

        // Get Download Service and enque file
        DownloadManager manager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        manager.enqueue(request);

        dsid_BaiHatDaTaiXuong.add(baiHats.get(pos).getIdBaiHat());
    }

    private void handle_fromDongBaiHat(int pos)
    {
        String[] option = {"Thêm vào Bài hát yêu thích","Tải xuống"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(option, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                if (which == 0)
                {
                    if (!inmode_dsBaiHatYeuThich)
                    {
                        if (!check_duplicateId(dsid_BaiHatYeuThich, baiHats.get(pos).getIdBaiHat()))
                        {
                            dsid_BaiHatYeuThich.add(baiHats.get(pos).getIdBaiHat());
                            Toast.makeText(context, "Đã thêm", Toast.LENGTH_SHORT).show();
                        } else
                            Toast.makeText(context, "Bài hát này đã được thêm trước đó", Toast.LENGTH_SHORT).show();
                    }
                    if (which == 1)
                    {
                        if (check_duplicateId(dsid_BaiHatDaTaiXuong, baiHats.get(pos).getIdBaiHat()))
                            Toast.makeText(context, "Bài hát này đã được tải xuống", Toast.LENGTH_SHORT).show();
                        else
                        {
                            Toast.makeText(context, "Đang Tải Xuống...", Toast.LENGTH_SHORT).show();
                            downloading_fromDongBaiHat(pos);
                        }
                    }
                }
            }
        });
        builder.show();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView imageView_hinhbh;
        ImageView imageView_luotthich;
        TextView textView_tenbh;
        TextView textView_tencasi;
        TextView textView_stt;
        ImageButton imageButton_moreoption;
        ImageView imageView_background;
        int pos = 0;

        public ViewHolder(@NonNull @NotNull View itemView)
        {
            super(itemView);
            imageView_hinhbh = itemView.findViewById(R.id.imgview_hinh_top100);
            textView_tenbh = itemView.findViewById(R.id.txtview_ten_top100);
            textView_tencasi = itemView.findViewById(R.id.txtview_casi_top100);
            textView_stt = itemView.findViewById(R.id.stt_top100);
            imageButton_moreoption = itemView.findViewById(R.id.icon_moreoption_top100);
            imageView_luotthich = itemView.findViewById(R.id.iconLuotThich_top100);
            imageView_background = itemView.findViewById(R.id.imgviewBackground_top100);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    imageView_background.setBackground(context.getResources().getDrawable(R.drawable.custom_background2));
                    final Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run()
                        {
                            if (mediaPlayer != null)
                                if (baiHat_isplaying.getIdBaiHat().equals(baiHats.get(pos).getIdBaiHat()))
                                    isclick_tudsbaihat = true;

                            Intent intent = new Intent(context, Activity_PlayMusic.class);
                            intent.putExtra("baihat", baiHats.get(pos));
                            intent.putExtra("mangbaihat", baiHats);
                            imageView_background.setBackgroundResource(R.color.white);
                            context.startActivity(intent);
                        }
                    }, 80);
                }
            });
            imageView_luotthich.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    imageView_luotthich.setImageResource(R.drawable.iconloved);
                    DataService dataService = APIService.getService();
                    Call<String> stringCall = dataService.Update_LuotThich(baiHats.get(pos).getIdBaiHat());
                    stringCall.enqueue(new Callback<String>()
                    {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response)
                        {
                            String ketqua = response.body();
                            if (ketqua.equals("OK"))
                                Toast.makeText(context, "Đã Thích", Toast.LENGTH_SHORT).show();
                            else Toast.makeText(context, "Bị Lỗi", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t)
                        {

                        }
                    });
                    imageView_luotthich.setEnabled(false);
                }
            });
            imageButton_moreoption.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    handle_fromDongBaiHat(pos);
                }
            });
        }
    }
}
