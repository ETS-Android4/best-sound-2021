package com.example.appnhac.Fragment;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.appnhac.Activity.Activity_PlayMusic;
import com.example.appnhac.R;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import static com.example.appnhac.Activity.Activity_PlayMusic.baiHat_isplaying;
import static com.example.appnhac.Activity.Activity_PlayMusic.is_pause;
import static com.example.appnhac.Activity.Activity_PlayMusic.isclick_playmusicminium;
import static com.example.appnhac.Activity.Activity_PlayMusic.mangbaiHats_isplaying;
import static com.example.appnhac.Activity.Activity_PlayMusic.mediaPlayer;
import static com.example.appnhac.Activity.MainActivity.get_Curr_BaiHat;
import static com.example.appnhac.Activity.MainActivity.haveFragment_minium2;
import static com.example.appnhac.Activity.MainActivity.haveFragment_minium3;
import static com.example.appnhac.Activity.MainActivity.init_Playmusic_minium;
import static com.example.appnhac.Activity.MainActivity.init_Playmusic_minium2;
import static com.example.appnhac.Activity.MainActivity.init_Playmusic_minium3;
import static com.example.appnhac.Activity.MainActivity.isPlay_LastSong;
import static com.example.appnhac.Fragment.Fragment_Playmusic_minium.imageButton_play_minium;
import static com.example.appnhac.Fragment.Fragment_Playmusic_minium2.imageButton_play_minium2;

public class Fragment_Playmusic_minium3 extends Fragment
{
    View view3;
    public static ImageView imageView_hinhbh_playmusic_minium3;
    public static TextView textView_tenbh_playmusic_minium3;
    public static TextView textView_tencasi_playmusic_minium3;
    public static ImageButton imageButton_play_minium3;

    ImageButton imageButton_previous3, imageButton_next3;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState)
    {
        view3 = inflater.inflate(R.layout.fragment_playmusic_minium, container, false);

        imageView_hinhbh_playmusic_minium3 = view3.findViewById(R.id.imgview_hinh_playmusicminium);
        textView_tenbh_playmusic_minium3 = view3.findViewById(R.id.imgview_tenbh_playmusicminium);
        textView_tencasi_playmusic_minium3 = view3.findViewById(R.id.imgview_tencasi_playmusicminium);
        imageButton_play_minium3 = view3.findViewById(R.id.imagebutton_play_playminium);
        imageButton_previous3 = view3.findViewById(R.id.imagebutton_previous_playminium);
        imageButton_next3 = view3.findViewById(R.id.imagebutton_next_playminium);

        handle_playmusic_minium();
        handle_next_playmusic_minium();
        handle_previous_playmusic_minium();

        view3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (textView_tenbh_playmusic_minium3.getText().equals("no name") == false)
                {
                    isclick_playmusicminium  = true;
                    Intent intent = new Intent(getContext(), Activity_PlayMusic.class);
                    getContext().startActivity(intent);
                }
            }
        });

        return view3;
    }

    private void handle_playmusic_minium()
    {
        imageButton_play_minium3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (textView_tenbh_playmusic_minium3.getText().equals("no name") == false && !isPlay_LastSong)
                {
                    if (mediaPlayer.isPlaying())
                    {
                        is_pause = true;
                        mediaPlayer.pause();

                        imageButton_play_minium3.setImageResource(R.drawable.icon_play_minium);
                        imageButton_play_minium.setImageResource(R.drawable.icon_play_minium);
                        if (haveFragment_minium2)
                            imageButton_play_minium2.setImageResource(R.drawable.icon_play_minium);

                    } else
                    {
                        is_pause = false;
                        mediaPlayer.start();

                        imageButton_play_minium3.setImageResource(R.drawable.icon_pause_minium);
                        imageButton_play_minium.setImageResource(R.drawable.icon_pause_minium);
                        if (haveFragment_minium2)
                            imageButton_play_minium2.setImageResource(R.drawable.icon_pause_minium);
                    }
                }
                else if (isPlay_LastSong && baiHat_isplaying != null)
                {
                    is_pause = false;
                    new Fragment_Playmusic_minium.playmp3().execute(baiHat_isplaying.getLinkBaiHat());
                    imageButton_play_minium.setImageResource(R.drawable.icon_pause_minium);
                    if (haveFragment_minium2)
                        imageButton_play_minium2.setImageResource(R.drawable.icon_pause_minium);
                    if (haveFragment_minium3)
                        imageButton_play_minium3.setImageResource(R.drawable.icon_pause_minium);
                    isPlay_LastSong = false;
                }
            }
        });
    }

    private void handle_button()
    {
        imageButton_next3.setEnabled(false);
        imageButton_previous3.setEnabled(false);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                imageButton_next3.setEnabled(true);
                imageButton_previous3.setEnabled(true);
            }
        },1500);
    }

    private void handle_previous_playmusic_minium()
    {
        imageButton_previous3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                is_pause = false;
                if (textView_tenbh_playmusic_minium3.getText().equals("no name") == false)
                {
                    // lấy vị trí của bài hát và -1
                    int vt = 0;
                    vt = get_Curr_BaiHat(vt);
                    if (vt > 0) vt--;
                    baiHat_isplaying = mangbaiHats_isplaying.get(vt);

                    // cập nhật dữ liệu
                    init_Playmusic_minium(getContext());
                    if (haveFragment_minium2)
                        init_Playmusic_minium2(getContext());
                    if (haveFragment_minium3)
                        init_Playmusic_minium3(getContext());

                    // chạy bài hát
                    if (mediaPlayer != null)
                    {
                        mediaPlayer.stop();
                        new playmp3().execute(baiHat_isplaying.getLinkBaiHat());
                    }
                    handle_playmusic_minium();

                    // xử lí cho button không bấm được trong 1.5s tránh tình trạng user spam
                    handle_button();
                }
            }
        });
    }

    private void handle_next_playmusic_minium()
    {
        imageButton_next3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                is_pause = false;
                if (textView_tenbh_playmusic_minium3.getText().equals("no name") == false)
                {
                    // lấy vị trí của bài hát và +1
                    int vt = mangbaiHats_isplaying.size() - 1;
                    vt = get_Curr_BaiHat(vt);
                    if (vt < mangbaiHats_isplaying.size() - 1) vt++;
                    baiHat_isplaying = mangbaiHats_isplaying.get(vt);

                    // cập nhật dữ liệu
                    init_Playmusic_minium(getContext());
                    if (haveFragment_minium2)
                        init_Playmusic_minium2(getContext());
                    if (haveFragment_minium3)
                        init_Playmusic_minium3(getContext());

                    // chạy bài hát
                    if (mediaPlayer != null)
                    {
                        mediaPlayer.stop();
                        new playmp3().execute(baiHat_isplaying.getLinkBaiHat());
                    }

                    handle_playmusic_minium();

                    // xử lí cho button không bấm được trong 1.5s tránh tình trạng user spam
                    handle_button();
                }
            }

        });

    }
    class playmp3 extends AsyncTask<String,Void,String>
    {

        @Override
        protected String doInBackground(String... strings)
        {
            return strings[0];
        }

        @Override
        protected void onPostExecute(String baihat)
        {
            super.onPostExecute(baihat);
            try
            {
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
                {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer)
                    {
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                });

                mediaPlayer.setDataSource(baihat);
                mediaPlayer.prepare();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            mediaPlayer.start();
        }
    }
}
