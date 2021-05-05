package com.zhangweijie.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhangweijie, E-mail:zhangweijiepku@gmail.com
 * @create 2021-01-10 22:17
 */

public interface PojoMapper<T> {
    List<T> queryAll();

    T query(Long id);

    Boolean add(T t);

    Boolean delete(Long id);

    Boolean update(T t);
}
