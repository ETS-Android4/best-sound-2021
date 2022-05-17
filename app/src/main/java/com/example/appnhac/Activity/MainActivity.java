package com.example.appnhac.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import com.example.appnhac.Adapter.MainViewPagerAdapter;
import com.example.appnhac.Fragment.Fragment_CaNhan;
import com.example.appnhac.Fragment.Fragment_Tim_Kiem;
import com.example.appnhac.Fragment.Fragment_Trang_Chu;
import com.example.appnhac.Model.BaiHat;
import com.example.appnhac.Model.CreateNotification;
import com.example.appnhac.Model.OnClearFromRecentService;
import com.example.appnhac.R;
import com.example.appnhac.Service.APIService;
import com.example.appnhac.Service.DataService;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.example.appnhac.Activity.Activity_PlayMusic.baiHat_isplaying;
import static com.example.appnhac.Activity.Activity_PlayMusic.imageButton_next;
import static com.example.appnhac.Activity.Activity_PlayMusic.imageButton_play;
import static com.example.appnhac.Activity.Activity_PlayMusic.imageButton_previous;
import static com.example.appnhac.Activity.Activity_PlayMusic.is_pause;
import static com.example.appnhac.Activity.Activity_PlayMusic.mangbaiHats_isplaying;
import static com.example.appnhac.Activity.Activity_PlayMusic.mediaPlayer;
import static com.example.appnhac.Fragment.Fragment_Playmusic_minium.imageButton_play_minium;
import static com.example.appnhac.Fragment.Fragment_Playmusic_minium.imageView_hinhbh_playmusic_minium;
import static com.example.appnhac.Fragment.Fragment_Playmusic_minium.textView_tenbh_playmusic_minium;
import static com.example.appnhac.Fragment.Fragment_Playmusic_minium.textView_tencasi_playmusic_minium;
import static com.example.appnhac.Fragment.Fragment_Playmusic_minium2.imageButton_play_minium2;
import static com.example.appnhac.Fragment.Fragment_Playmusic_minium2.imageView_hinhbh_playmusic_minium2;
import static com.example.appnhac.Fragment.Fragment_Playmusic_minium2.textView_tenbh_playmusic_minium2;
import static com.example.appnhac.Fragment.Fragment_Playmusic_minium2.textView_tencasi_playmusic_minium2;
import static com.example.appnhac.Fragment.Fragment_Playmusic_minium3.imageButton_play_minium3;
import static com.example.appnhac.Fragment.Fragment_Playmusic_minium3.imageView_hinhbh_playmusic_minium3;
import static com.example.appnhac.Fragment.Fragment_Playmusic_minium3.textView_tenbh_playmusic_minium3;
import static com.example.appnhac.Fragment.Fragment_Playmusic_minium3.textView_tencasi_playmusic_minium3;

public class MainActivity extends AppCompatActivity
{
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1000;
    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 3000;
    private long pressedTime;
    TabLayout tabLayout;
    ViewPager viewPager;

    NotificationManager notificationManager;
    public static boolean isPlaying_Notification = false;

    public static boolean haveData_PlayMinium = false;
    public static boolean haveFragment_minium2 = false;
    public static boolean haveFragment_minium3 = false;
    public static boolean isPlay_LastSong = false;
    public static boolean show_keybroad = false;
    public static boolean isclick_fromoffline = false;
    public static boolean have_data_top100music = false;
    public static boolean inmode_dsBaiHatYeuThich = false;
    public static boolean inmode_dsAlbumYeuThich = false;
    public static boolean inmode_dsPlaylistYeuThich = false;
    public static String folder_main = "";

    public static ArrayList<BaiHat> data_top100music;
    public static ArrayList<String> dsid_BaiHatYeuThich = new ArrayList<>();
    public static ArrayList<String> dsid_AlbumYeuThich = new ArrayList<>();
    public static ArrayList<String> dsid_PlaylistYeuThich = new ArrayList<>();
    public static ArrayList<String> dsid_BaiHatDaTaiXuong = new ArrayList<>();
    static ArrayList<Integer> background = new ArrayList<Integer>();
    public static ArrayList<Integer> background_playOffline = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        anhxa();
        init();
        setup_Random_Background();
        setup_Random_Background_playOffline();
        handle_writeStorage_permisson();
        handle_readStorage_permisson();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createChannel();
            registerReceiver(broadcastReceiver, new IntentFilter("TRACKS_TRACKS"));
            startService(new Intent(getBaseContext(), OnClearFromRecentService.class));
        }

        handle_LatSongPlayed();
        load_dsid_BaiHatDaTaiXuong(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/" + Environment.DIRECTORY_MUSIC);
        dsid_BaiHatYeuThich = load_dsid("BaiHatYeuThich.txt");
        dsid_AlbumYeuThich = load_dsid("AlbumYeuThich.txt");
        dsid_PlaylistYeuThich = load_dsid("PlaylistYeuThich.txt");
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getExtras().getString("actionname");

            switch (action){
                case CreateNotification.ACTION_PREVIUOS:
                    onTrackPrevious();
                    break;
                case CreateNotification.ACTION_PLAY:
                    if (isPlaying_Notification){
                        onTrackPause();
                    } else {
                        onTrackPlay();
                    }
                    break;
                case CreateNotification.ACTION_NEXT:
                    onTrackNext();
                    break;
            }
        }
    };

    private void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CreateNotification.CHANNEL_ID,
                    "KOD Dev", NotificationManager.IMPORTANCE_LOW);

            notificationManager = getSystemService(NotificationManager.class);
            if (notificationManager != null){
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    private void onTrackPrevious()
    {
        imageButton_previous.performClick();

        // handle playminium
        init_Playmusic_minium(MainActivity.this);
        if (haveFragment_minium2)
            init_Playmusic_minium2(MainActivity.this);
        if (haveFragment_minium3)
            init_Playmusic_minium3(MainActivity.this);
    }

    private void onTrackNext()
    {
        imageButton_next.performClick();

        // handle playminium
        init_Playmusic_minium(MainActivity.this);
        if (haveFragment_minium2)
            init_Playmusic_minium2(MainActivity.this);
        if (haveFragment_minium3)
            init_Playmusic_minium3(MainActivity.this);
    }

    private void onTrackPause()
    {
        if (mediaPlayer.isPlaying())
        {
            CreateNotification.createNotification(MainActivity.this, baiHat_isplaying,
                    R.drawable.ic_play_arrow);
            isPlaying_Notification = false;
            mediaPlayer.pause();
            is_pause = true;
            imageButton_play.setImageResource(R.drawable.icon_play);

            // handle playminium
            imageButton_play_minium.setImageResource(R.drawable.icon_play_minium);
            if (haveFragment_minium2)
                imageButton_play_minium2.setImageResource(R.drawable.icon_play_minium);
            if (haveFragment_minium3)
                imageButton_play_minium3.setImageResource(R.drawable.icon_play_minium);
        }
    }
    private void onTrackPlay()
    {
        if (!mediaPlayer.isPlaying())
        {
            CreateNotification.createNotification(MainActivity.this, baiHat_isplaying,
                    R.drawable.ic_baseline_pause_24);
            isPlaying_Notification = true;
            is_pause = false;
            mediaPlayer.start();
            imageButton_play.setImageResource(R.drawable.icon_pause);

            // handle playminium
            imageButton_play_minium.setImageResource(R.drawable.icon_pause_minium);
            if (haveFragment_minium2)
                imageButton_play_minium2.setImageResource(R.drawable.icon_pause_minium);
            if (haveFragment_minium3)
                imageButton_play_minium3.setImageResource(R.drawable.icon_pause_minium);
        }
    }

    private void anhxa()
    {
        tabLayout = (TabLayout) findViewById(R.id.myTabLayout);
        viewPager = (ViewPager) findViewById(R.id.myViewPager);
    }

    private void init()
    {
        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager(), 1);
        mainViewPagerAdapter.addFragment(new Fragment_CaNhan(), "Cá Nhân");
        mainViewPagerAdapter.addFragment(new Fragment_Trang_Chu(), "Online");
        mainViewPagerAdapter.addFragment(new Fragment_Tim_Kiem(), "");

        viewPager.setAdapter(mainViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(2).setIcon(R.drawable.icon_search);

        TabLayout.Tab tab = tabLayout.getTabAt(1);
        tab.select();
    }

    private void setup_Random_Background()
    {
        background.add(getResources().getColor(R.color.background_1));
        background.add(getResources().getColor(R.color.background_2));
        background.add(getResources().getColor(R.color.background_3));
        background.add(getResources().getColor(R.color.background_4));
        background.add(getResources().getColor(R.color.background_5));
        background.add(getResources().getColor(R.color.background_6));
        background.add(getResources().getColor(R.color.background_7));
        background.add(getResources().getColor(R.color.background_8));
        background.add(getResources().getColor(R.color.background_9));
        background.add(getResources().getColor(R.color.background_10));
        background.add(getResources().getColor(R.color.background_11));
        background.add(getResources().getColor(R.color.background_12));
        background.add(getResources().getColor(R.color.background_13));
        background.add(getResources().getColor(R.color.background_14));
        background.add(getResources().getColor(R.color.background_15));
        background.add(getResources().getColor(R.color.background_16));
        background.add(getResources().getColor(R.color.background_17));
        background.add(getResources().getColor(R.color.background_18));
    }

    private void setup_Random_Background_playOffline()
    {
        background_playOffline.add(R.drawable.backgroundoffline_1);
        background_playOffline.add(R.drawable.backgroundoffline_2);
        background_playOffline.add(R.drawable.backgroundoffline_3);
        background_playOffline.add(R.drawable.backgroundoffline_4);
        background_playOffline.add(R.drawable.backgroundoffline_5);
        background_playOffline.add(R.drawable.backgroundoffline_6);
        background_playOffline.add(R.drawable.backgroundoffline_7);
        background_playOffline.add(R.drawable.backgroundoffline_8);
        background_playOffline.add(R.drawable.backgroundoffline_9);
        background_playOffline.add(R.drawable.backgroundoffline_10);
        background_playOffline.add(R.drawable.backgroundoffline_11);
        background_playOffline.add(R.drawable.backgroundoffline_12);
        background_playOffline.add(R.drawable.backgroundoffline_13);
        background_playOffline.add(R.drawable.backgroundoffline_14);
    }

    public static int getRandom_Background()
    {
        Random r = new Random();
        return background.get(r.nextInt(background.size()));
    }

    // xử lí cấp quyền lưu vào bộ nhớ
    private void handle_writeStorage_permisson()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED)
            {
                // PERMISSION DENIED
                String [] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                // show popup for runtime permission
                requestPermissions(permission,MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                Log.d("Permission","Ask : user for Permission WRITE");
            }
            else
            {
                //Permission granted
            }
        }
        else
        {
            //Permission granted
        }
    }

    // xử lí cấp quyền đọc bộ nhớ
    private void handle_readStorage_permisson()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED)
            {
                // PERMISSION DENIED
                String [] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
                // show popup for runtime permission
                requestPermissions(permission,MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                Log.d("Permission","Ask : user for Permission READ");
            }
            else
            {
                //Permission granted
            }
        }
        else
        {
            //Permission granted
        }
    }

    // phản hồi sau khi yêu cầu các quyền
    @Override
    public void onRequestPermissionsResult(final int requestCode, @NonNull final String[] permissions, @NonNull final int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        Boolean check_permission = false;
        switch (requestCode)
        {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE:
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Log.d("Permission", "Respond : Permission granted WRITE");
                    check_permission = true;
                }
                else
                    Log.d("Permission","Respond : Permission denied WRITE");
            }
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    Log.d("Permission", "Respond : Permission granted READ");
                    check_permission = true;
                }
                else
                    Log.d("Permission","Respond : Permission denied READ");
            }
        }

        // lần đầu tiên cài app . TH đã có sẵn BH ở thư mục DIRECTORY_MUSIC
        // lúc này đang đợt users accept quyền nên dsid_BaiHatDaTaiXuong có size = 0
        // xử lí khi cấp quyền thì đọc lại
        if (check_permission)
            load_dsid_BaiHatDaTaiXuong(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/" + Environment.DIRECTORY_MUSIC);
    }

    // tạo media player và prepare cho đã được phát cuối cùng
    private void handle_LatSongPlayed()
    {
        String idbh = loadLastSongPlayed();
        if (idbh.length() > 0)
        {
            DataService dataService = APIService.getService();
            Call<List<BaiHat>> callback = dataService.GetBaiHat_FromID(idbh);
            callback.enqueue(new Callback<List<BaiHat>>()
            {
                @Override
                public void onResponse(Call<List<BaiHat>> call, Response<List<BaiHat>> response)
                {
                    mangbaiHats_isplaying = (ArrayList<BaiHat>) response.body();
                    baiHat_isplaying = mangbaiHats_isplaying.get(0);

                    init_Playmusic_minium(MainActivity.this);

                    haveData_PlayMinium = true;
                    isPlay_LastSong = true;
                }

                @Override
                public void onFailure(Call<List<BaiHat>> call, Throwable t)
                {

                }
            });
        }
    }

    // lưu bài hát đã được phát cuối cùng vào file
    private void saveLastSongPlayed()
    {
        String text = baiHat_isplaying.getIdBaiHat();
        File file = new File(getFilesDir().getAbsoluteFile(),"LastSongPlayed.txt");

        FileOutputStream fos = null;
        try
        {
            fos = new FileOutputStream(file);
            fos.write(text.getBytes());
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            if (fos != null)
            {
                try
                {
                    fos.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    // load bài hát được chơi cuối cùng
    private String loadLastSongPlayed()
    {
        String text = "";
        Scanner s = null;
        try
        {
            s = new Scanner(new File(getFilesDir().getAbsoluteFile() + "/" + "LastSongPlayed.txt"));
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        if (s!=null)
        {
            while (s.hasNext())
            {
                text = s.next();
            }
            s.close();
        }
        return text;
    }

    // load danh sách id của những bài hát đã tải xuống. Tách id ra từ tên_file.mp3
    private void load_dsid_BaiHatDaTaiXuong(String rootPath)
    {
        try
        {
            File rootFolder = new File(rootPath);
            File[] files = rootFolder.listFiles();

            for (File file : files)
            {
                if (file.isDirectory())
                {

                } else if (file.getName().endsWith(".mp3"))
                {
                    String s = file.getName();
                    String[] arrOfStr = s.split("_", 4);
                    if (arrOfStr.length == 4)
                    {
                        String[] arrOfStr2 = arrOfStr[2].split("O", 2);
                        dsid_BaiHatDaTaiXuong.add(arrOfStr2[0]);
                    }
                    else
                        dsid_BaiHatDaTaiXuong.add(s);
                }
            }
        } catch (Exception e)
        {
            Log.d("Error " , e.getMessage());
        }
    }

    // load danh sách id đã được lưu trong file
    private ArrayList<String> load_dsid(String filename)
    {
        ArrayList<String> result = new ArrayList<>();
        Scanner s = null;
        try
        {
            s = new Scanner(new File(getFilesDir().getAbsoluteFile() + "/" + filename));
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        if (s!=null)
        {
            while (s.hasNext())
            {
                result.add(s.next());
            }
            s.close();
        }
        return result;
    }

    // lưu danh sách id vào file
    private void save_dsid(ArrayList<String> dsid, String filename)
    {
        File file = new File(getFilesDir().getAbsoluteFile(),filename);

        FileOutputStream fos = null;
        try
        {
            fos = new FileOutputStream(file);
            for (int i=0;i<dsid.size();i++)
            {
                fos.write(dsid.get(i).getBytes());
                fos.write(10);
            }
            fos.close();
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            if (fos != null)
            {
                try
                {
                    fos.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    // lưu các dữ liệu cần thiết
    @Override
    protected void onDestroy()
    {
        for (int i=1;i<=4;i++)
        {
            if (i == 1)
                save_dsid(dsid_AlbumYeuThich,"AlbumYeuThich.txt");
            else if (i == 2)
                save_dsid(dsid_BaiHatYeuThich,"BaiHatYeuThich.txt");
            else if (i == 3)
                save_dsid(dsid_PlaylistYeuThich,"PlaylistYeuThich.txt");
            else if (i == 4)
                saveLastSongPlayed();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            notificationManager.cancelAll();
        }

        unregisterReceiver(broadcastReceiver);

        super.onDestroy();
    }

    // xử lí nhấn 2 lần để thoát
    @Override
    public void onBackPressed()
    {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Nhấn lần nữa để thoát", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }

    // lấy vị trí hiện tại của bài hát đang được phát trong mảng để xử lí next , privious
    public static int get_Curr_BaiHat(int vt)
    {
        for (int i=0;i<mangbaiHats_isplaying.size();i++)
            if (mangbaiHats_isplaying.get(i).getIdBaiHat().equals(baiHat_isplaying.getIdBaiHat()))
            {
                vt = i;
                break;
            }
        return vt;
    }

    // kiểm tra id có xuất hiện trong mảng s hay không
    public static boolean check_duplicateId(ArrayList<String> s, String id)
    {
        for (int i=0;i<s.size();i++)
            if (id.equals(s.get(i)))
                return true;
        return false;
    }

    // load ảnh, tên bài hát , tên ca sĩ cho trình chơi nhạc ở dưới cùng màn hình
    public static void init_Playmusic_minium(Context context)
    {
        if (baiHat_isplaying.getHinhBaiHat().equals("Offline"))
            imageView_hinhbh_playmusic_minium.setImageResource(R.drawable.main_image);
        else Picasso.with(context).load(baiHat_isplaying.getHinhBaiHat()).into(imageView_hinhbh_playmusic_minium);

        textView_tenbh_playmusic_minium.setText(baiHat_isplaying.getTenBaiHat());
        textView_tencasi_playmusic_minium.setText(baiHat_isplaying.getCaSi());

        if (is_pause)
            imageButton_play_minium.setImageResource(R.drawable.icon_play_minium);
        else imageButton_play_minium.setImageResource(R.drawable.icon_pause_minium);
    }

    // load ảnh, tên bài hát , tên ca sĩ cho trình chơi nhạc ở dưới cùng màn hình
    public static void init_Playmusic_minium2(Context context)
    {
        if (haveData_PlayMinium)
        {
            if (baiHat_isplaying.getHinhBaiHat().equals("Offline"))
                imageView_hinhbh_playmusic_minium2.setImageResource(R.drawable.main_image);
            else Picasso.with(context).load(baiHat_isplaying.getHinhBaiHat()).into(imageView_hinhbh_playmusic_minium2);

            textView_tenbh_playmusic_minium2.setText(baiHat_isplaying.getTenBaiHat());
            textView_tencasi_playmusic_minium2.setText(baiHat_isplaying.getCaSi());

            if (is_pause)
                imageButton_play_minium2.setImageResource(R.drawable.icon_play_minium);
            else imageButton_play_minium2.setImageResource(R.drawable.icon_pause_minium);
        }
    }

    // load ảnh, tên bài hát , tên ca sĩ cho trình chơi nhạc ở dưới cùng màn hình
    public static void init_Playmusic_minium3(Context context)
    {
        if (haveData_PlayMinium)
        {
            if (baiHat_isplaying.getHinhBaiHat().equals("Offline"))
                imageView_hinhbh_playmusic_minium3.setImageResource(R.drawable.main_image);
            else Picasso.with(context).load(baiHat_isplaying.getHinhBaiHat()).into(imageView_hinhbh_playmusic_minium3);

            textView_tenbh_playmusic_minium3.setText(baiHat_isplaying.getTenBaiHat());
            textView_tencasi_playmusic_minium3.setText(baiHat_isplaying.getCaSi());

            if (is_pause)
                imageButton_play_minium3.setImageResource(R.drawable.icon_play_minium);
            else imageButton_play_minium3.setImageResource(R.drawable.icon_pause_minium);
        }
    }

    // reset khi xoá bài hát đã tải xuống
    public static void  reset_Playmusic_minium(Context context)
    {
        is_pause = true;
        imageButton_play_minium.setImageResource(R.drawable.icon_play_minium);
        imageView_hinhbh_playmusic_minium.setImageResource(R.drawable.no_image);
        textView_tenbh_playmusic_minium.setText("no name");
        textView_tencasi_playmusic_minium.setText("no artist");
    }

    // reset khi xoá bài hát đã tải xuống
    public static void  reset_Playmusic_minium2(Context context)
    {
        is_pause = true;
        imageButton_play_minium2.setImageResource(R.drawable.icon_play_minium);
        imageView_hinhbh_playmusic_minium2.setImageResource(R.drawable.no_image);
        textView_tenbh_playmusic_minium2.setText("no name");
        textView_tencasi_playmusic_minium2.setText("no artist");
    }
    public static int get_Pos_Have_ID1(ArrayList<String> dsid,String id)
    {
        int vt = 0;
        for (int i=0;i<dsid.size();i++)
            if (id.equals(dsid.get(i)))
            {
                vt = i;
                break;
            }
        return vt;
    }
    public static int get_Pos_Have_ID2(ArrayList<BaiHat> dsBaiHat,String id)
    {
        int vt2 = 0;
        for (int i=0;i<dsBaiHat.size();i++)
            if (id.equals(dsBaiHat.get(i).getIdBaiHat()))
            {
                vt2 = i;
                break;
            }
        return vt2;
    }
    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }
}

