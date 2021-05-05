package com.zhangweijie.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangweijie, E-mail:zhangweijiepku@gmail.com
 * @create 2021-01-18 9:38
 */
public class MyStringUtil {

    public static String getPojoClassName(String pojoControllerName) {
        String pojoClassName = pojoControllerName.substring(0, pojoControllerName.lastIndexOf('C'));
        return pojoClassName;
    }

    /***
     * 将XxxYyyController变成xxxYyy
     * @param pojoControllerName
     * @return
     */
    public static String getPojoName(String pojoControllerName) {
        String pojoClassName = pojoControllerName.substring(0, pojoControllerName.lastIndexOf('C'));
        String firstLetter = pojoControllerName.substring(0, 1).toLowerCase();
        String pojoName = firstLetter + pojoClassName.substring(1);

        return pojoName;
    }


    //处理URLEncoder.encode();
    private static List<String> parseURL(String url) {
        List<String> stringList = new ArrayList<>();
        String[] tempStrings = new String[2];
        //1.以特殊字符为断点将url截断并按偶奇交替的顺序存到一个List<String>中,
        boolean loop = true;
        while (loop) {
            int count = 0;
            for (int i = 0; i < url.length(); i++) {
                count++;
                //假如char为ascii表上（即ascii码<128）的字符,且不为0-9,a-z,A-Z任一字符
                //则将其转换为对应的ascii码即可
                char ch = url.charAt(i);
                if (ch < 128) {//排除非ascii码
                    if (ch < 48 || ch > 57) {//排除0-9
                        if (ch < 65 || ch > 90) {//排除A-Z
                            if (ch < 97 || ch > 122) {//排除a-z
                                //进行截断和保存：只截取一次(limit=2)
                                tempStrings = url.split("" + ch, 2);
                                //将前半截字符串存入List<String>的偶数位置(从0开始)
                                stringList.add(tempStrings[0]);
                                //将断点字符转换为字符串存入List<String>的奇数位置(从1开始)
                                stringList.add("" + ch);
                                //将url更新为后半截字符串
                                url = tempStrings[1];
                                //url发生变化,需要跳出for循环
                                break;
                            }
                        }
                    }
                }
            }
            //循环结束时判断：
            //1.遍历url字符串完毕,正常退出,此时设置循环条件loop=false
            if (count == url.length()) {
                loop = false;
            }
            //2.遇到特殊符号break退出
        }

        //将tempStrings中保存的末尾剩下的url串存入List<String>
        stringList.add(tempStrings[1]);
        //由于split()方法,最后得到的List<String>的size为奇数,
        //为了方便遍历和拼接,我们再为List<String>添加一个空串
        stringList.add("");
        return stringList;
    }

    //将parseURL()返回的List<String>拼接成新的url
    public static String getURL(String url) throws UnsupportedEncodingException {
        String resURL = "";
        List<String> stringList = parseURL(url);
        //对List<String>中偶数位的字符串进行URLEncoder.encode()
        //每编码一个字符串后,将List<String>中紧随其后的特殊字符拼接上
        for (int i = 0; i < stringList.size(); i += 2) {
            String normalURL = URLEncoder.encode(stringList.get(i), "utf-8");
            String specialURL = stringList.get(i + 1);
            resURL += (normalURL + specialURL);
        }
        return resURL;
    }
}
