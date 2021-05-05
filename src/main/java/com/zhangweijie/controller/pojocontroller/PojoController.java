package com.zhangweijie.controller.pojocontroller;

import java.util.List;

/**
 * @author zhangweijie, E-mail:zhangweijiepku@gmail.com
 * @create 2021-01-12 11:14
 */
public interface PojoController<T> {
    public List<T> queryAll();

    public T query(Long id);

    public Boolean add(T t);

    public Boolean delete(Long id);

    public Boolean update(T t);
}
