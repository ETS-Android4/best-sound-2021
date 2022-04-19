package com.example.appnhac.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Quangcao implements Serializable
{

    @SerializedName("idQuangCao")
    @Expose
    private String idQuangCao;
    @SerializedName("HinhAnh")
    @Expose
    private String hinhAnh;
    @SerializedName("NoiDung")
    @Expose
    private String noiDung;
    @SerializedName("idBaiHat")
    @Expose
    private String idBaiHat;
    @SerializedName("HinhAnhResize")
    @Expose
    private String hinhAnhResize;
    @SerializedName("TenCaSi")
    @Expose
    private String tenCaSi;
    @SerializedName("idPlayList")
    @Expose
    private String idPlayList;

    public String getIdPlayList() {
        return idPlayList;
    }

    public void setIdPlayList(String idPlayList) {
        this.idPlayList = idPlayList;
    }

    public String getIdQuangCao() {
        return idQuangCao;
    }

    public void setIdQuangCao(String idQuangCao) {
        this.idQuangCao = idQuangCao;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getIdBaiHat() {
        return idBaiHat;
    }

    public void setIdBaiHat(String idBaiHat) {
        this.idBaiHat = idBaiHat;
    }

    public String getHinhAnhResize() {
        return hinhAnhResize;
    }

    public void setHinhAnhResize(String hinhAnhResize) {
        this.hinhAnhResize = hinhAnhResize;
    }

    public String getTenCaSi() {
        return tenCaSi;
    }

    public void setTenCaSi(String tenCaSi) {
        this.tenCaSi = tenCaSi;
    }

}
//import java.io.Serializable;
//
//public class Quangcao implements Serializable
//{
//
//    @SerializedName("idQuangCao")
//    @Expose
//    private String idQuangCao;
//    @SerializedName("HinhAnh")
//    @Expose
//    private String hinhAnh;
//    @SerializedName("NoiDung")
//    @Expose
//    private String noiDung;
//    @SerializedName("idBaiHat")
//    @Expose
//    private String idBaiHat;
//    @SerializedName("TenBaiHat")
//    @Expose
//    private String tenBaiHat;
//    @SerializedName("HinhBaiHat")
//    @Expose
//    private String hinhBaiHat;
//
//    public String getIdQuangCao()
//    {
//        return idQuangCao;
//    }
//
//    public void setIdQuangCao(String idQuangCao)
//    {
//        this.idQuangCao = idQuangCao;
//    }
//
//    public String getHinhAnh()
//    {
//        return hinhAnh;
//    }
//
//    public void setHinhAnh(String hinhAnh)
//    {
//        this.hinhAnh = hinhAnh;
//    }
//
//    public String getNoiDung()
//    {
//        return noiDung;
//    }
//
//    public void setNoiDung(String noiDung)
//    {
//        this.noiDung = noiDung;
//    }
//
//    public String getIdBaiHat()
//    {
//        return idBaiHat;
//    }
//
//    public void setIdBaiHat(String idBaiHat)
//    {
//        this.idBaiHat = idBaiHat;
//    }
//
//    public String getTenBaiHat()
//    {
//        return tenBaiHat;
//    }
//
//    public void setTenBaiHat(String tenBaiHat)
//    {
//        this.tenBaiHat = tenBaiHat;
//    }
//
//    public String getHinhBaiHat()
//    {
//        return hinhBaiHat;
//    }
//
//    public void setHinhBaiHat(String hinhBaiHat)
//    {
//        this.hinhBaiHat = hinhBaiHat;
//    }
//
//}
