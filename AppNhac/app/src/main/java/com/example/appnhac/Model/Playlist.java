package com.example.appnhac.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
public class Playlist implements Serializable
{

    @SerializedName("idPlayList")
    @Expose
    private String idPlayList;
    @SerializedName("Ten")
    @Expose
    private String ten;
    @SerializedName("TenCaSi")
    @Expose
    private String TenCaSi;
    @SerializedName("HinhIcon")
    @Expose
    private String hinhIcon;

    public String getIdPlayList() {
        return idPlayList;
    }

    public void setIdPlayList(String idPlayList) {
        this.idPlayList = idPlayList;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getTenCaSi() {
        return TenCaSi;
    }

    public void setTenCaSi(String hinhNen) {
        this.TenCaSi = hinhNen;
    }

    public String getHinhIcon() {
        return hinhIcon;
    }

    public void setHinhIcon(String hinhIcon) {
        this.hinhIcon = hinhIcon;
    }

}