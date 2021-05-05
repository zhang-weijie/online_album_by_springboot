package com.zhangweijie.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @author zhangweijie, E-mail:zhangweijiepku@gmail.com
 * @create 2021-01-12 10:26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class CreateCity implements Serializable, Pojo {
    private Long id;
    private String name;


    @Override
    public void setObject(Object object) {
        this.name = object.toString();
    }

    @Override
    public Object getObject() {
        return getName();
    }
}
