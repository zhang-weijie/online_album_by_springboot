package com.zhangweijie.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author zhangweijie, E-mail:zhangweijiepku@gmail.com
 * @create 2021-01-13 19:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@Deprecated//弃用此类
public class PhotoData implements Serializable {

    private Long id;
    //注意：data不能定义为Blob类型,否则mybatis无法自动识别
    private byte[] data;
}
