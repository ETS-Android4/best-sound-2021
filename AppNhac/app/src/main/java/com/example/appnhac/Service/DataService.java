package com.example.appnhac.Service;

import com.example.appnhac.Model.Album;
import com.example.appnhac.Model.BaiHat;
import com.example.appnhac.Model.ChuDe;
import com.example.appnhac.Model.Playlist;
import com.example.appnhac.Model.Quangcao;
import com.example.appnhac.Model.TheLoai;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DataService
{
    @GET("songbanner.php")
    Call<List<Quangcao>> GetDataBanner();

    @GET("playlist.php")
    Call<List<Playlist>> GetDataPlaylist();

    @GET("chude.php")
    Call<List<ChuDe>> GetDataChuDe();

    @GET("album.php")
    Call<List<Album>> GetDataAlbum();

    @GET("baihatgoiy.php")
    Call<List<BaiHat>> GetDataBaiHatGoiY();

    @GET("top100baihat.php")
    Call<List<BaiHat>> GetData_Top100();

    @GET("alltheloai.php")
    Call<List<TheLoai>> GetData_AllTheLoai();

    @GET("getDsPlaylist.php")
    Call<List<Playlist>> GetDataDsPlaylist();

    @GET("playlisthot.php")
    Call<List<Playlist>> GetData_PlaylistHot();

    @GET("getDsAlbum.php")
    Call<List<Album>> GetDataDsAlbum();

    @GET("albummoi.php")
    Call<List<Album>> GetData_AlbumMoi();

    @GET("getDsChude.php")
    Call<List<ChuDe>> GetDataDsChude();

    @FormUrlEncoded
    @POST("ds_baihat_tuPlaylist.php")
    Call<List<BaiHat>> GetDataDSBaiHat_FromPlaylist(@Field("idPlayList") String idPlayList);

    @FormUrlEncoded
    @POST("ds_baihat_tuAlbum.php")
    Call<List<BaiHat>> GetDataDSBaiHat_FromAlbum(@Field("idAlbum") String idAlbum);

    @FormUrlEncoded
    @POST("ds_TheLoai_tuChuDe.php")
    Call<List<TheLoai>> GetDataDSTheLoai_FromChuDe(@Field("idChuDe") String idChuDe);

    @FormUrlEncoded
    @POST("ds_BaiHat_tuTheLoai.php")
    Call<List<BaiHat>> GetDataDSBaiHat_FromTheLoai(@Field("idTheLoai") String idTheLoai);

    @FormUrlEncoded
    @POST("update_luotthich.php")
    Call<String> Update_LuotThich(@Field("idBaiHat") String idBaiHat);

    @FormUrlEncoded
    @POST("getBaiHat_fromID.php")
    Call<List<BaiHat>> GetBaiHat_FromID(@Field("idBaiHat") String idBaiHat);

    @FormUrlEncoded
    @POST("sendArraylist.php")
    Call<List<BaiHat>> Send_ArrayList(@Field("Array_idBaiHat") ArrayList<String> Array_idBaiHat);

    @FormUrlEncoded
    @POST("getAlbum_FromID.php")
    Call<List<Album>> GetAlbum_FromID(@Field("idAlbum") String idAlbum);

    @FormUrlEncoded
    @POST("getPlaylist_FromID.php")
    Call<List<Playlist>> GetPlaylist_FromID(@Field("idPlayList") String idPlayList);

    @FormUrlEncoded
    @POST("Search_BaiHat.php")
    Call<List<BaiHat>> Search_BaiHat(@Field("TenBaiHat") String TenBaiHat);

}
