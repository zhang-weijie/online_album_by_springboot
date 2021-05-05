package com.zhangweijie.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.sql.Date;

/**
 * @author zhangweijie, E-mail:zhangweijiepku@gmail.com
 * @create 2021-01-10 21:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class CreateDate implements Serializable, Pojo {
    private Long id;
    private Date date;

    @Override
    public void setObject(Object object) {
        this.date = (Date) object;
    }

    @Override
    public Object getObject() {
        return getDate();
    }
}
