package com.example.appnhac.Service;

public class APIService
{
  private static String base_url = "https://damhongducappnghenhac.000webhostapp.com/Server/";

  public static DataService getService(){
      return APIRetrofitClient.getClient(base_url).create(DataService.class);
  }

}
