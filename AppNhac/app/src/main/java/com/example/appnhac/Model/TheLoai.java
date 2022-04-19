package com.example.appnhac.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class TheLoai implements Serializable
{

    @SerializedName("idTheLoai")
    @Expose
    private String idTheLoai;
    @SerializedName("idChuDe")
    @Expose
    private String idChuDe;
    @SerializedName("TenTheLoai")
    @Expose
    private String tenTheLoai;
    @SerializedName("HinhTheLoai")
    @Expose
    private String hinhTheLoai;
    @SerializedName("idPlayList")
    @Expose
    private String idPlayList;

    public String getidPlayList() {
        return idPlayList;
    }

    public void setidPlayList(String idPlayList) {
        this.idPlayList = idPlayList;
    }

    public String getIdTheLoai() {
        return idTheLoai;
    }

    public void setIdTheLoai(String idTheLoai) {
        this.idTheLoai = idTheLoai;
    }

    public String getIdChuDe() {
        return idChuDe;
    }

    public void setIdChuDe(String idChuDe) {
        this.idChuDe = idChuDe;
    }

    public String getTenTheLoai() {
        return tenTheLoai;
    }

    public void setTenTheLoai(String tenTheLoai) {
        this.tenTheLoai = tenTheLoai;
    }

    public String getHinhTheLoai() {
        return hinhTheLoai;
    }

    public void setHinhTheLoai(String hinhTheLoai) {
        this.hinhTheLoai = hinhTheLoai;
    }

}