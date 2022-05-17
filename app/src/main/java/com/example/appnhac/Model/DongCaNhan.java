package com.example.appnhac.Model;

public class DongCaNhan
{
    private String title;
    private String soluong;
    int function;
    int image;

    public DongCaNhan(String title, String soluong, int function, int image)
    {
        this.title = title;
        this.soluong = soluong;
        this.function = function;
        this.image = image;
    }

    public String getTitle()
    {
        return title;
    }

    public String getSoluong()
    {
        return soluong;
    }

    public int getFunction()
    {
        return function;
    }

    public int getImage()
    {
        return image;
    }
}
