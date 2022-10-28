package com.carolin_violet.travel_system.utils;

/**
 * @ClassName IpdbUtil
 * @Description 根据ip获取地址,只支持ipv4不支持ipv6
 * @Author zj
 * @Date 2022/9/12 22:54
 * @Version 1.0
 */
import net.ipip.ipdb.City;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * 从城市查询
 *
 * @author lengcz
 */
public class IpdbUtil {

    private static City city_DB;

    static {
        try {
            File directory = new File("src/main/resources");
            String resourcePath = directory.getCanonicalPath();
            String ipdbPath  =resourcePath + "/ipipfree.ipdb";


            city_DB = new City(ipdbPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过IP查询城市
     *
     * @param ip            (IPv4或者 IPv6)
     * @param language,例如CN
     * @return 例如[中国, 广东, 深圳]
     */
    public static String[] find(String ip, String language) {
        try {
            return city_DB.find(ip, language);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void test1() {
//        String[] cns = find("117.60.78.20", "CN");
        String[] cns = find("192.168.37.87", "CN");
        System.out.println(Arrays.toString(cns));
    }
}