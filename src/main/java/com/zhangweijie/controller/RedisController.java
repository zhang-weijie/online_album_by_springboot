package com.zhangweijie.controller;

import com.zhangweijie.pojo.PhotoData;
import com.zhangweijie.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * @author zhangweijie, E-mail:zhangweijiepku@gmail.com
 * @create 2021-01-16 15:09
 */
@Controller
public class RedisController {
    @Autowired
    private RedisTemplate<String, Object> template;


}
