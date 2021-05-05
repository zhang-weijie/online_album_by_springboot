package com.zhangweijie;

import com.mysql.cj.jdbc.Blob;
import com.zhangweijie.util.MyStringUtil;
import io.netty.util.internal.StringUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class OnlineAlbumBySpringbootApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    DataSource dataSource;

    @Test
    void datasourceTest() throws SQLException {
        System.out.println(dataSource.getClass());
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }

    @Test
    public void BlobTypeTest() {

    }

    @Test
    public void SubStringTest() throws UnsupportedEncodingException {
        String url = "A帆帆的提问@理教%40";
        String encodedURL = URLEncoder.encode(url, "utf-8");
//        System.out.println(encodedURL);
//        此处：%E5%B8%86%E5%B8%86%E7%9A%84%E6%8F%90%E9%97%AE%40%E7%90%86%E6%95%99
//        前端：%E5%B8%86%E5%B8%86%E7%9A%84%E6%8F%90%E9%97%AE@%E7%90%86%E6%95%99
        // %40 vs. @
        // 40就是@的十六进制ascii码
        String url1 = "%帆帆的提问@理教40%";
        String[] split = url1.split("%");
        System.out.println("split.length = " + split.length);
        for (int i = 0; i < split.length; i++) {
            System.out.println(split[i]);

        }


    }

    @Test
    public void stringTest() {
        String url = "帆帆的提问@理教";
        char c = url.charAt(0);
        int cAsInt = (int) c;
        System.out.println(cAsInt);//24070
        System.out.println("" + c);//帆

        for (int i = 0; i < url.length(); i++) {
            //假如char为ascii表上（即ascii码<128）的字符,且不为0-9,a-z,A-Z任一字符
            //则将其转换为对应的ascii码即可
            char ch = url.charAt(i);
            if (ch < 128) {//排除非ascii码
                if (ch < 48 || ch > 57) {//排除0-9
                    if (ch < 65 || ch > 90) {//排除A-Z
                        if (ch < 97 || ch > 122) {//排除a-z
                            //进行替换

                        }
                        continue;
                    }
                    continue;
                }
                continue;
            }
            continue;
        }
    }

    @Test
    public void transferTest() throws UnsupportedEncodingException {
        String url = "A帆@理40%";
        String url1 = MyStringUtil.getURL(url);
        System.out.println(url1);
    }
}
