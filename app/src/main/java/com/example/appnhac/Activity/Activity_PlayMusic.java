package com.example.appnhac.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.appnhac.Adapter.PlayMusicAdapter;
import com.example.appnhac.Fragment.Fragment_HinhBaiHat_PlayMusic;
import com.example.appnhac.Fragment.Fragment_loibaihat_Playmusic;
import com.example.appnhac.Model.BaiHat;
import com.example.appnhac.Model.CreateNotification;
import com.example.appnhac.R;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;
import me.relex.circleindicator.CircleIndicator;
import static com.example.appnhac.Activity.Activity_dsBaiHatYeuThich.baiHats_BaiHatYeuThich;
import static com.example.appnhac.Activity.Activity_dsBaiHatYeuThich.dsBaiHatAdapter_BaiHatYeuThich;
import static com.example.appnhac.Activity.MainActivity.background_playOffline;
import static com.example.appnhac.Activity.MainActivity.check_duplicateId;
import static com.example.appnhac.Activity.MainActivity.dsid_BaiHatDaTaiXuong;
import static com.example.appnhac.Activity.MainActivity.dsid_BaiHatYeuThich;
import static com.example.appnhac.Activity.MainActivity.getBitmapFromURL;
import static com.example.appnhac.Activity.MainActivity.get_Pos_Have_ID1;
import static com.example.appnhac.Activity.MainActivity.get_Pos_Have_ID2;
import static com.example.appnhac.Activity.MainActivity.haveData_PlayMinium;
import static com.example.appnhac.Activity.MainActivity.haveFragment_minium2;
import static com.example.appnhac.Activity.MainActivity.haveFragment_minium3;
import static com.example.appnhac.Activity.MainActivity.init_Playmusic_minium;
import static com.example.appnhac.Activity.MainActivity.init_Playmusic_minium2;
import static com.example.appnhac.Activity.MainActivity.init_Playmusic_minium3;
import static com.example.appnhac.Activity.MainActivity.inmode_dsBaiHatYeuThich;
import static com.example.appnhac.Activity.MainActivity.isPlay_LastSong;
import static com.example.appnhac.Activity.MainActivity.isPlaying_Notification;
import static com.example.appnhac.Activity.MainActivity.isclick_fromoffline;
import static com.example.appnhac.Fragment.Fragment_HinhBaiHat_PlayMusic.imageView_hinhbh_playmusic;
import static com.example.appnhac.Fragment.Fragment_Playmusic_minium.imageButton_play_minium;

public class Activity_PlayMusic extends AppCompatActivity
{
    SeekBar seekBar_playmusic;
    TextView textView_currtime;
    TextView textView_totaltime;
    Toolbar toolbar;
    public static TextView textView_tenbh, textView_tencasi;
    ViewPager viewpager;
    CircleIndicator circleIndicator;
    ImageButton imageButton_moreoption ;
    ImageButton imageButton_repeat, imageButton_shuffle, imageButton_download;
    public static ImageButton  imageButton_play, imageButton_previous, imageButton_next;

    public static MediaPlayer mediaPlayer;
    public static BaiHat baiHat_isplaying;
    public static ArrayList<BaiHat> mangbaiHats_isplaying = new ArrayList<>();
    public static boolean is_repeat = false;
    public static boolean is_shuffle = false;
    public static boolean isclick_tudsbaihat = false;
    public static boolean isclick_playmusicminium = false;
    public static boolean is_pause = true;
    public static PlayMusicAdapter playMusicAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        anhxa();
        getIntent_BaiHat();
        haveData_PlayMinium = true;
        init();

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                handle_playmusic();
            }
        }, 5);

        handle_seekbar();
        handle_next();
        handle_previous();
        handle_repeat();
        handle_shuffle();
        handle_moreoption();
        handle_download();
    }

    private void anhxa()
    {
        toolbar = findViewById(R.id.toolbar_playmusic);
        textView_tenbh = findViewById(R.id.textview_tenbaihat_playmusic);
        textView_tencasi = findViewById(R.id.textview_tencasi_playmusic);
        viewpager = findViewById(R.id.viewpager_playmusic);
        circleIndicator = findViewById(R.id.indicator_playmusic);
        textView_currtime = findViewById(R.id.textview_currtime_playmusic);
        textView_totaltime = findViewById(R.id.textview_totaltime_playmusic);
        seekBar_playmusic = findViewById(R.id.seekbar_playmusic);
        imageButton_moreoption = findViewById(R.id.imagebutton_moreoption_playmusic);
        imageButton_play = findViewById(R.id.imagebutton_play_playmusic);
        imageButton_previous = findViewById(R.id.imagebutton_previous_playmusic);
        imageButton_next = findViewById(R.id.imagebutton_next_playmusic);
        imageButton_repeat = findViewById(R.id.imagebutton_repeat);
        imageButton_shuffle = findViewById(R.id.imagebutton_shuffle);
        imageButton_download = findViewById(R.id.imagebutton_download);
    }

    private void getIntent_BaiHat()
    {
        Intent intent = getIntent();
        if (intent!=null)
        {
            if (intent.hasExtra("baihat"))
                baiHat_isplaying = (BaiHat) intent.getParcelableExtra("baihat");
            if (intent.hasExtra("mangbaihat"))
                mangbaiHats_isplaying = intent.getParcelableArrayListExtra("mangbaihat");
        }
    }

    private void init()
    {
        textView_tenbh.setText(baiHat_isplaying.getTenBaiHat());
        textView_tencasi.setText(baiHat_isplaying.getCaSi());

        playMusicAdapter = new PlayMusicAdapter(getSupportFragmentManager(),1);
        playMusicAdapter.addFragment(new Fragment_HinhBaiHat_PlayMusic(baiHat_isplaying));
        playMusicAdapter.addFragment(new Fragment_loibaihat_Playmusic());
        viewpager.setAdapter(playMusicAdapter);
        circleIndicator.setViewPager(viewpager);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        toolbar.setNavigationOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
    }

    private void init_playminium()
    {
        if (isclick_playmusicminium)
            isclick_playmusicminium = false;

        if (isclick_tudsbaihat)
            isclick_tudsbaihat = false;

        init_Playmusic_minium(Activity_PlayMusic.this);
        if (haveFragment_minium2)
            init_Playmusic_minium2(Activity_PlayMusic.this);
        if (haveFragment_minium3)
            init_Playmusic_minium3(Activity_PlayMusic.this);
    }

    @Override
    protected void onPause()
    {
        init_playminium();
        super.onPause();
    }

    @Override
    protected void onDestroy()
    {
        if (isclick_playmusicminium)
            isclick_playmusicminium = false;

        if (isclick_tudsbaihat)
            isclick_tudsbaihat = false;

        init_Playmusic_minium(Activity_PlayMusic.this);
        if (haveFragment_minium2)
            init_Playmusic_minium2(Activity_PlayMusic.this);
        if (haveFragment_minium3)
            init_Playmusic_minium3(Activity_PlayMusic.this);

        super.onDestroy();
    }

    private void set_playNotification()
    {
        CreateNotification.createNotification(Activity_PlayMusic.this, baiHat_isplaying,
                R.drawable.ic_baseline_pause_24);
        isPlaying_Notification = true;
    }

    private void handle_playmusic()
    {
        if (!isclick_playmusicminium && !isclick_tudsbaihat)
        {
            if (mediaPlayer != null)
                mediaPlayer.stop();

            new playmp3().execute(baiHat_isplaying.getLinkBaiHat());
            is_pause = false;
            isPlay_LastSong = false;
        }

        if (!isPlay_LastSong)
        {
            if (isclick_playmusicminium | isclick_tudsbaihat)
            {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                textView_currtime.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                timesong();

                if (is_repeat == true)
                    imageButton_repeat.setImageResource(R.drawable.icon_repeated);
                else if (is_shuffle == true)
                    imageButton_shuffle.setImageResource(R.drawable.icon_shuffled);
            }
        }
        else if (isPlay_LastSong && isclick_playmusicminium )
        {
            is_pause = true;
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

                mediaPlayer.setDataSource(baiHat_isplaying.getLinkBaiHat());
                mediaPlayer.prepare();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            timesong();
            Update_Time();


            imageButton_play.setImageResource(R.drawable.icon_play);
            isPlay_LastSong = false;
        }

        imageButton_play.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (mediaPlayer.isPlaying())
                {
                    is_pause = true;
                    mediaPlayer.pause();
                    imageButton_play.setImageResource(R.drawable.icon_play);

                    // handle play notification
                    CreateNotification.createNotification(Activity_PlayMusic.this, baiHat_isplaying,
                            R.drawable.ic_play_arrow);
                    isPlaying_Notification = false;
                }
                else
                {
                    is_pause = false;
                    mediaPlayer.start();
                    imageButton_play.setImageResource(R.drawable.icon_pause);

                    // handle play notification
                    CreateNotification.createNotification(Activity_PlayMusic.this, baiHat_isplaying,
                            R.drawable.ic_baseline_pause_24);
                    isPlaying_Notification = true;
                }
            }
        });
        Update_Time();

        // set mode đã download
        if (isclick_fromoffline)
        {
            imageButton_download.setEnabled(false);
            isclick_fromoffline = false;
        }

        // set các nút pause, play
        if (is_pause)
            imageButton_play_minium.setImageResource(R.drawable.icon_play_minium);
        else imageButton_play_minium.setImageResource(R.drawable.icon_pause_minium);
        if (is_pause)
            imageButton_play.setImageResource(R.drawable.icon_play);
        else imageButton_play.setImageResource(R.drawable.icon_pause);
    }

    private void handle_seekbar()
    {
        seekBar_playmusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b)
            {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
    }

    private void handle_next()
    {
        imageButton_next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                is_pause = false;
                int vt = mangbaiHats_isplaying.size() - 1;

                for (int i=0;i<mangbaiHats_isplaying.size();i++)
                    if (mangbaiHats_isplaying.get(i).getIdBaiHat().equals(baiHat_isplaying.getIdBaiHat()))
                    {
                        vt = i;
                        break;
                    }
                if (vt < mangbaiHats_isplaying.size() - 1) vt++;
                baiHat_isplaying = mangbaiHats_isplaying.get(vt);

                if (baiHat_isplaying.getHinhBaiHat().equals("Offline"))
                    imageView_hinhbh_playmusic.setImageResource(R.drawable.main_image);
                else Picasso.with(Activity_PlayMusic.this).load(baiHat_isplaying.getHinhBaiHat()).into(imageView_hinhbh_playmusic);
                textView_tenbh.setText(baiHat_isplaying.getTenBaiHat());
                textView_tencasi.setText(baiHat_isplaying.getCaSi());

                if (isclick_playmusicminium | isclick_tudsbaihat)
                {
                    if (mediaPlayer != null)
                        mediaPlayer.stop();

                    new playmp3().execute(baiHat_isplaying.getLinkBaiHat());
                }
                handle_playmusic();

                imageButton_next.setEnabled(false);
                imageButton_previous.setEnabled(false);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        imageButton_next.setEnabled(true);
                        imageButton_previous.setEnabled(true);
                    }
                },1500);
            };
        });
    }

    private void handle_previous()
    {
        imageButton_previous.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                is_pause = false;
                int vt = 0;

                for (int i=0;i<mangbaiHats_isplaying.size();i++)
                    if (mangbaiHats_isplaying.get(i).getIdBaiHat().equals(baiHat_isplaying.getIdBaiHat()))
                    {
                        vt = i;
                        break;
                    }
                if (vt > 0) vt--;
                baiHat_isplaying = mangbaiHats_isplaying.get(vt);

                if (baiHat_isplaying.getHinhBaiHat().equals("Offline"))
                    imageView_hinhbh_playmusic.setImageResource(R.drawable.main_image);
                else Picasso.with(Activity_PlayMusic.this).load(baiHat_isplaying.getHinhBaiHat()).into(imageView_hinhbh_playmusic);
                textView_tenbh.setText(baiHat_isplaying.getTenBaiHat());
                textView_tencasi.setText(baiHat_isplaying.getCaSi());

                if (isclick_playmusicminium | isclick_tudsbaihat)
                {
                    if (mediaPlayer != null)
                        mediaPlayer.stop();

                    new playmp3().execute(baiHat_isplaying.getLinkBaiHat());
                }
                handle_playmusic();

                imageButton_next.setEnabled(false);
                imageButton_previous.setEnabled(false);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        imageButton_next.setEnabled(true);
                        imageButton_previous.setEnabled(true);
                    }
                },1500);
            }
        });
    }

    private void handle_repeat()
    {
        imageButton_repeat.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (is_shuffle == true)
                {
                    imageButton_shuffle.setImageResource(R.drawable.icon_shuffle);
                    is_shuffle = false;
                }
                if (is_repeat == false)
                {
                    imageButton_repeat.setImageResource(R.drawable.icon_repeated);
                    is_repeat = true;
                }
                else
                {
                    imageButton_repeat.setImageResource(R.drawable.icon_repeat);
                    is_repeat = false;
                }
            }
        });
    }

    private void handle_shuffle()
    {
        imageButton_shuffle.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (is_repeat == true)
                {
                    imageButton_repeat.setImageResource(R.drawable.icon_repeat);
                    is_repeat = false;
                }
                if (is_shuffle == false)
                {
                    imageButton_shuffle.setImageResource(R.drawable.icon_shuffled);
                    is_shuffle = true;
                } else
                {
                    imageButton_shuffle.setImageResource(R.drawable.icon_shuffle);
                    is_shuffle = false;
                }
            }
        });
    }

    private void handle_moreoption()
    {
        imageButton_moreoption.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String s = "";
                if (!inmode_dsBaiHatYeuThich)
                    s = "Thêm vào Bài hát yêu thích";
                else s = "Xoá khỏi Bài hát yêu thích";
                String[] colors = {s};

                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_PlayMusic.this);
                builder.setItems(colors, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if (which == 0)
                        {
                            if (!inmode_dsBaiHatYeuThich)
                            {
                                if (!check_duplicateId(dsid_BaiHatYeuThich, baiHat_isplaying.getIdBaiHat()))
                                {
                                    dsid_BaiHatYeuThich.add(baiHat_isplaying.getIdBaiHat());
                                    Toast.makeText(Activity_PlayMusic.this, "Đã thêm", Toast.LENGTH_SHORT).show();
                                }
                                else Toast.makeText(Activity_PlayMusic.this, "Bài hát này đã được thêm trước đó", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                int vt1 = get_Pos_Have_ID1(dsid_BaiHatYeuThich,baiHat_isplaying.getIdBaiHat());
                                int vt2 = get_Pos_Have_ID2(baiHats_BaiHatYeuThich,baiHat_isplaying.getIdBaiHat());

                                dsid_BaiHatYeuThich.remove(vt1);
                                baiHats_BaiHatYeuThich.remove(vt2);
                                dsBaiHatAdapter_BaiHatYeuThich.notifyDataSetChanged();

                                Toast.makeText(Activity_PlayMusic.this, "Đã xoá", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                builder.show();
            }
        });
    }

    private void handle_download()
    {
        imageButton_download.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (check_duplicateId(dsid_BaiHatDaTaiXuong,baiHat_isplaying.getIdBaiHat()))
                    Toast.makeText(Activity_PlayMusic.this, "Bài hát này đã được tải xuống", Toast.LENGTH_SHORT).show();
                else
                {
                    Toast.makeText(Activity_PlayMusic.this, "Đang tải xuống...", Toast.LENGTH_SHORT).show();
                    downloading();
                }
            }
        });
    }

    private void downloading()
    {
        // get URL
        String Url = baiHat_isplaying.getLinkBaiHat();

        // Create Download Request
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(Url));
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setTitle("Download");
        request.setDescription("Downloading file...");

        request.allowScanningByMediaScanner();
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_MUSIC,
                baiHat_isplaying.getTenBaiHat() + "_" + baiHat_isplaying.getCaSi() + "_" +
                        baiHat_isplaying.getIdBaiHat() + "Offline_.mp3");

        // Get Download Service and enque file
        DownloadManager manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        manager.enqueue(request);

        // set mode đã download
        imageButton_download.setEnabled(false);
        dsid_BaiHatDaTaiXuong.add(baiHat_isplaying.getIdBaiHat());
    }

    private class playmp3 extends AsyncTask<String,Void,String>
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
            timesong();
            Update_Time();
            set_playNotification();
        }
    }

    private void timesong()
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        textView_totaltime.setText(simpleDateFormat.format(mediaPlayer.getDuration()));
        seekBar_playmusic.setMax(mediaPlayer.getDuration());
    }

    private void Update_Time()
    {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                if (mediaPlayer != null)
                    seekBar_playmusic.setProgress(mediaPlayer.getCurrentPosition());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
                textView_currtime.setText(simpleDateFormat.format(mediaPlayer.getCurrentPosition()));
                handler.postDelayed(this,300);
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
                {
                    @Override
                    public void onCompletion(MediaPlayer mediaPlayer)
                    {
                        if (is_repeat == true)
                        {
                            if (mediaPlayer != null)
                                mediaPlayer.stop();

                            new playmp3().execute(baiHat_isplaying.getLinkBaiHat());
                            imageButton_play.setImageResource(R.drawable.icon_pause);
                        }
                        else if (is_shuffle == true)
                        {
                            int vt = 0;
                            for (int i = 0; i < mangbaiHats_isplaying.size(); i++)
                                if (mangbaiHats_isplaying.get(i).getIdBaiHat().equals(baiHat_isplaying.getIdBaiHat()))
                                {
                                    vt = i;
                                    break;
                                }

                            Random random = new Random();
                            int vt2 = random.nextInt(mangbaiHats_isplaying.size());

                            while (vt == vt2)
                            {
                                vt2 = random.nextInt(mangbaiHats_isplaying.size());
                            }

                            baiHat_isplaying = mangbaiHats_isplaying.get(vt2);

                            Picasso.with(Activity_PlayMusic.this).load(baiHat_isplaying.getHinhBaiHat()).into(imageView_hinhbh_playmusic);
                            textView_tenbh.setText(baiHat_isplaying.getTenBaiHat());
                            textView_tencasi.setText(baiHat_isplaying.getCaSi());

                            if (mediaPlayer != null)
                                mediaPlayer.stop();

                            new playmp3().execute(baiHat_isplaying.getLinkBaiHat());
                            imageButton_play.setImageResource(R.drawable.icon_pause);

                            init_Playmusic_minium(Activity_PlayMusic.this);
                            if (haveFragment_minium2)
                                init_Playmusic_minium2(Activity_PlayMusic.this);
                            if (haveFragment_minium3)
                                init_Playmusic_minium3(Activity_PlayMusic.this);
                        }
                        else
                        {
                            int vt = mangbaiHats_isplaying.size() - 1;

                            for (int i = 0; i < mangbaiHats_isplaying.size(); i++)
                                if (mangbaiHats_isplaying.get(i).getIdBaiHat().equals(baiHat_isplaying.getIdBaiHat()))
                                {
                                    vt = i;
                                    break;
                                }
                            if (vt < mangbaiHats_isplaying.size() - 1) vt++;
                            baiHat_isplaying = mangbaiHats_isplaying.get(vt);

                            Picasso.with(Activity_PlayMusic.this).load(baiHat_isplaying.getHinhBaiHat()).into(imageView_hinhbh_playmusic);
                            textView_tenbh.setText(baiHat_isplaying.getTenBaiHat());
                            textView_tencasi.setText(baiHat_isplaying.getCaSi());

                            if (mediaPlayer != null)
                                mediaPlayer.stop();

                            new playmp3().execute(baiHat_isplaying.getLinkBaiHat());
                            imageButton_play.setImageResource(R.drawable.icon_pause);

                            init_Playmusic_minium(Activity_PlayMusic.this);
                            if (haveFragment_minium2)
                                init_Playmusic_minium2(Activity_PlayMusic.this);
                            if (haveFragment_minium3)
                                init_Playmusic_minium3(Activity_PlayMusic.this);
                        }
                    }
                });
            }
        },300);
    }
}