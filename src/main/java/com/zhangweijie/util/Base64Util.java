package com.zhangweijie.util;

import org.apache.tomcat.util.codec.binary.Base64;

import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author zhangweijie, E-mail:zhangweijiepku@gmail.com
 * @create 2021-01-19 17:17
 */
public class Base64Util {

    public static String encodeToString(byte[] data) {
        //发现压缩后,前端还要unzip解压,所以此处就不压缩了,直接传原文件;
//        byte[] compressedData = compress(data);
        String dataString = new String(Base64.encodeBase64(data));
        return dataString;
    }

    /**
     * 对byte[]进行压缩
     *
     * @return 压缩后的数据
     */
    private static byte[] compress(byte[] data) {
        System.out.println("before:" + data.length);

        GZIPOutputStream gzip = null;
        ByteArrayOutputStream baos = null;
        byte[] newData = null;

        try {
            baos = new ByteArrayOutputStream();
            gzip = new GZIPOutputStream(baos);
            gzip.write(data);
            gzip.finish();
            gzip.flush();

            newData = baos.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                gzip.close();
                baos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("after:" + newData.length);
        return newData;
    }
}

