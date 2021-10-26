package com.reptile.utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class FileDownloadUtil {
    /**
     * 下载文件---返回下载后的文件存储路径
     *
     * @param url      文件路径
     * @param dir      目标存储目录
     * @return
     */
    public static void downloadHttpUrl(String url, String dir) {
        try {
            URL httpurl = new URL(url);
            File dirfile = new File(dir);
            if (!dirfile.exists()) {
                dirfile.mkdirs();
            }
            FileUtils.copyURLToFile(httpurl, new File(dir + "/" + url.substring(url.lastIndexOf("/") + 1)));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载文件---返回下载后的文件存储路径
     *
     * @param url      文件路径
     * @param dir      目标存储目录
     * @return
     */
    public static void downloadHttpUrl(String url, String dir,Integer userId) {
        try {
            URL httpurl = new URL(url);
            File dirfile = new File(dir);
            if (!dirfile.exists()) {
                dirfile.mkdirs();
            }
            FileUtils.copyURLToFile(httpurl, new File(dir + "/" + url.substring(url.lastIndexOf("/") + 1)));
        } catch (MalformedURLException e) {
            System.out.println("---"+userId);
        } catch (IOException e) {
            System.out.println("---"+userId);
        }
    }


    public static boolean deleteFile(String dir, String fileName) {
        File file = new File(dir + fileName);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }

    public static void main(String[] args) {
        String avatarurl = "https://dating-1256663796.file.myqcloud.com/avatar/016259924653403952-1000x1000.jpg";
        downloadHttpUrl(avatarurl,"pic");
        ;
    }
}