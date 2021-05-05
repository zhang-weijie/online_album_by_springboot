package com.zhangweijie.mapper;

import com.zhangweijie.pojo.Photo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @author zhangweijie, E-mail:zhangweijiepku@gmail.com
 * @create 2021-01-10 22:24
 */
@Mapper//此注解表明接口是mybatis的mapper类,也可以在SpringBootApplication下@MapperScan("com.zhangweijie.mapper")扫描整个mapper包
@Repository//此类表明接口是springboot的一个@Component
public interface PhotoMapper {

    List<Photo> queryAllInfo();

    List<Photo> queryMultiInfo(Long id,
                               Long creditToId,
                               Long createDateId,
                               Long createCountryId,
                               Long createCityId,
                               Long themeId,
                               Long figureId);

    Photo queryData(Long id);

    Boolean add(Photo photo);

    Boolean delete(Long id);


    Boolean update(Photo photo);
}
