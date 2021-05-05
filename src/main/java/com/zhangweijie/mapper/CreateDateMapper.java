package com.zhangweijie.mapper;

import com.zhangweijie.pojo.CreateDate;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhangweijie, E-mail:zhangweijiepku@gmail.com
 * @create 2021-01-10 22:23
 */
@Mapper//此注解表明接口是mybatis的mapper类,也可以在SpringBootApplication下@MapperScan("com.zhangweijie.mapper")扫描整个mapper包
@Repository//此类表明接口是springboot的一个@Component
public interface CreateDateMapper extends PojoMapper<CreateDate> {

}
