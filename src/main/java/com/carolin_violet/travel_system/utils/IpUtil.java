package com.carolin_violet.travel_system.utils;

import com.maxmind.db.CHMCache;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.model.CountryResponse;
import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Country;

import java.io.*;
import java.net.InetAddress;

public class IpUtil {

    public static void main(String[] args) {
        String ip = "::3d80:8044";
        try {
            // 读取当前工程下的IP库文件
            File directory = new File("src/main/resources");
            String resourcePath = directory.getCanonicalPath();
            String countryUrl  = (resourcePath + "/GeoLite2-Country.mmdb");
            String cityFileUrl  = (resourcePath + "/GeoLite2-City.mmdb");
            File countryFile = new File(countryUrl);
            File cityFile = new File(cityFileUrl);

            // 读取IP库文件
            DatabaseReader countryReader = (new DatabaseReader.Builder(countryFile).withCache(new CHMCache())).build();
            DatabaseReader cityReader = (new DatabaseReader.Builder(cityFile).withCache(new CHMCache())).build();
            CountryResponse countryResponse = countryReader.country(InetAddress.getByName(ip));
            Country country = countryResponse.getCountry();
            CityResponse cityResponse = cityReader.city(InetAddress.getByName(ip));
            City city = cityResponse.getCity();

            System.out.println("从country IP库读取国家结果： " + country);
            System.out.println("从city IP库读取国家结果：" + cityResponse.getCountry());
            System.out.println("从city IP库读取城市结果：" + city);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}