package com.zhangweijie.mapper;

import com.zhangweijie.pojo.Theme;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


/**
 * @author zhangweijie, E-mail:zhangweijiepku@gmail.com
 * @create 2021-01-10 22:24
 */
@Mapper//此注解表明接口是mybatis的mapper类,也可以在SpringBootApplication下@MapperScan("com.zhangweijie.mapper")扫描整个mpper包
@Repository//此类表明接口是springboot的一个@Component
public interface ThemeMapper extends PojoMapper<Theme> {

}
