package com.zhangweijie.mapper;

import com.zhangweijie.pojo.PhotoData;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author zhangweijie, E-mail:zhangweijiepku@gmail.com
 * @create 2021-01-13 19:58
 */
@Mapper
@Repository
public interface PhotoDataMapper extends PojoMapper<PhotoData> {
}
