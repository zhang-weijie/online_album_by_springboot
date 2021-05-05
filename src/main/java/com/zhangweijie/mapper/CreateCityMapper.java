package com.zhangweijie.mapper;

import com.zhangweijie.pojo.CreateCity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author zhangweijie, E-mail:zhangweijiepku@gmail.com
 * @create 2021-01-12 10:34
 */
@Mapper
@Repository
public interface CreateCityMapper extends PojoMapper<CreateCity> {
}
