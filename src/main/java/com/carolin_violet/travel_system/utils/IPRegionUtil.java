package com.carolin_violet.travel_system.utils;

import org.lionsoul.ip2region.xdb.Searcher;
import java.io.*;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName IPRegionUtil
 * @Description 只能查ipv4
 * @Author zj
 * @Date 2022/9/13 10:28
 * @Version 1.0
 */
public class IPRegionUtil {
    public static void main(String[] args) {
        File directory = new File("src/main/resources");
        String resourcePath = null;
        try {
            resourcePath = directory.getCanonicalPath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String dbPath  = resourcePath + "/ip2region.xdb";

        // 1、从 dbPath 加载整个 xdb 到内存。
        byte[] cBuff;
        try {
            cBuff = Searcher.loadContentFromFile(dbPath);
        } catch (Exception e) {
            System.out.printf("failed to load content from `%s`: %s\n", dbPath, e);
            return;
        }

        // 2、使用上述的 cBuff 创建一个完全基于内存的查询对象。
        Searcher searcher;
        try {
            searcher = Searcher.newWithBuffer(cBuff);
        } catch (Exception e) {
            System.out.printf("failed to create content cached searcher: %s\n", e);
            return;
        }

        // 3、查询
        String ip = "192.168.37.87";
        try {
            long sTime = System.nanoTime();
            String region = searcher.search(ip);
            long cost = TimeUnit.NANOSECONDS.toMicros((long) (System.nanoTime() - sTime));
            System.out.printf("{region: %s, ioCount: %d, took: %d μs}\n", region, searcher.getIOCount(), cost);
        } catch (Exception e) {
            System.out.printf("failed to search(%s): %s\n", ip, e);
        }
    }
}
