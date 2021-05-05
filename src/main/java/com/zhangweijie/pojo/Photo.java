package com.zhangweijie.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Blob;
import java.sql.Date;

/**
 * @author zhangweijie, E-mail:zhangweijiepku@gmail.com
 * @create 2021-01-10 21:07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class Photo implements Serializable {
    private Long id;
    private Long creditToId;
    private Date updateTime;
    private Long createDateId;
    private Long createCountryId;
    private Long createCityId;
    private Long themeId;
    private Long figureId;
    private String desc;
    private byte[] data;
}
