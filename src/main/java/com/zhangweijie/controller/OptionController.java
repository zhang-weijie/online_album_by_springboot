package com.zhangweijie.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zhangweijie.mapper.*;
import com.zhangweijie.pojo.*;
import com.zhangweijie.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.sql.Date;

/**
 * @author zhangweijie, E-mail:zhangweijiepku@gmail.com
 * @create 2021-01-17 11:41
 */
@Controller
public class OptionController {
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private CreditToMapper creditToMapper;

    @Autowired
    private CreateDateMapper createDateMapper;

    @Autowired
    private CreateCountryMapper createCountryMapper;

    @Autowired
    private CreateCityMapper createCityMapper;

    @Autowired
    private ThemeMapper themeMapper;

    @Autowired
    private FigureMapper figureMapper;


    //使用redis存储Option,供全局调用;
    @RequestMapping("/loadOption")
    public void loadOption(HttpServletResponse resp) throws IOException {
        System.out.println("进入了@Controller的OptionController的loadOption()...");

        //从Redis的Hash中批量获取
        Map<Object, Object> optionMap = redisUtil.hmget("option");

        //将map转换为JSON字符串,注意要禁止循环引用
        String jsonString = JSON.toJSONString(optionMap, SerializerFeature.DisableCircularReferenceDetect);

        //将响应格式设置为text/plain,否则firefox会当作默认的xml格式进行解析
        //将字符集设置为utf-8,否则会出现中文乱码
        resp.setContentType("text/plain;charset=utf-8");

        resp.getWriter().write(jsonString);
    }

    //不知道为什么,此处无论是return ModelAndView还是return String都无法完成跳转;
    //页面会停在localhost:8080/addOption上
    @PostMapping("/addOption")
    public ModelAndView addOption(@RequestParam String optionName,
                                  @RequestParam Object optionItemName,
                                  HttpServletResponse resp
    ) throws Exception {
        System.out.println("进入了@Controller的OptionController的addOption()...");
        System.out.println("optionName\toptionItemName\t");
        System.out.println(optionName + "\t" + optionItemName + "\t");

        //如果前端input的是日期(type="date"),则后端会接收到形如2021-01-24的字符串,需要将其转换为Date类型
        if ("createDate".equals(optionName)) {
            optionItemName = Date.valueOf(optionItemName.toString());
        }
        System.out.println("optionItemName = " + optionItemName);

        String res = "success";
        //0.创建一个hashMap用于存储PojoMapper
        Map<String, PojoMapper> mapperMap = new HashMap<>();
        mapperMap.put("creditTo", creditToMapper);
        mapperMap.put("createDate", createDateMapper);
        mapperMap.put("createCountry", createCountryMapper);
        mapperMap.put("createCity", createCityMapper);
        mapperMap.put("theme", themeMapper);
        mapperMap.put("figure", figureMapper);

        //1.判断用户传入的optionItemName是否已存在,若存在则不添加;
        boolean exist = false;
        PojoMapper targetMapper = mapperMap.get(optionName);
        List<Pojo> list = targetMapper.queryAll();
        for (int i = 0; i < list.size(); i++) {
            Object object = list.get(i).getObject();
            if (optionItemName.equals(object)) {
                res = "failure";
                exist = true;
                break;
            }
        }

        //2.判断用户是否已存在,是则直接返回,否则添加;
        if (!exist) {
            //3.通过反射获取对应的Xxx类,并创建一个实例pojo，将optionItemId和optionItemName注入其中
            String firstLetter = ("" + optionName.charAt(0)).toUpperCase(Locale.ROOT);
            optionName = firstLetter + optionName.substring(1);
            //注意；由于pojo类跟controller类不再同一个包下，因此此处写全类名
            Class<Pojo> clazz = (Class<Pojo>) Class.forName("com.zhangweijie.pojo." + optionName);
            Pojo pojo = clazz.newInstance();
//            Method setId = clazz.getDeclaredMethod("setId", Long.class);
            Method setObject = clazz.getDeclaredMethod("setObject", Object.class);
//            setId.invoke(pojo,null);
            setObject.invoke(pojo, optionItemName);
            //4.执行add
            targetMapper.add(pojo);
        }
        //注意：同时在RedisPointCut中更新hash

        //由于前端使用form表单而非ajax,没有回调函数,以下可用可不用
        //注意；必须返回一个值,否则前端的回调函数无法执行;
        resp.setContentType("text/plain;charset=utf-8");
        resp.getWriter().write(JSON.toJSONString(res));

        //经测试,以下跳转操作无效？？？
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/editOption");
        return mav;
    }

    @PostMapping("/updateOption")
    public ModelAndView updateOption(@RequestParam String optionName,
                                     @RequestParam Long optionItemId,
                                     @RequestParam Object optionItemName,
                                     HttpServletResponse resp) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException, IOException {
        System.out.println("进入了@Controller的OptionController的addOption()...");
        //1.创建一个hashMap用于存储PojoMapper
        Map<String, PojoMapper> mapperMap = new HashMap<>();
        mapperMap.put("creditTo", creditToMapper);
        mapperMap.put("createDate", createDateMapper);
        mapperMap.put("createCountry", createCountryMapper);
        mapperMap.put("createCity", createCityMapper);
        mapperMap.put("theme", themeMapper);
        mapperMap.put("figure", figureMapper);
        //2.根据optionName如creditTo获取对应的XxxMapper
        PojoMapper targetMapper = mapperMap.get(optionName);

        //3.通过反射获取对应的Xxx类,并创建一个实例pojo，将optionItemId和optionItemName注入其中
        String firstLetter = ("" + optionName.charAt(0)).toUpperCase(Locale.ROOT);
        optionName = firstLetter + optionName.substring(1);
        //注意；由于pojo类跟controller类不再同一个包下，因此此处写全类名
        Class<Pojo> clazz = (Class<Pojo>) Class.forName("com.zhangweijie.pojo." + optionName);
        Pojo pojo = clazz.newInstance();
        Method setId = clazz.getDeclaredMethod("setId", Long.class);
        Method setObject = clazz.getDeclaredMethod("setObject", Object.class);
        setId.invoke(pojo, optionItemId);
        setObject.invoke(pojo, optionItemName);


        //2.将pojo作为参数传入update中;
        targetMapper.update(pojo);
        //注意：同时在RedisPointCut中更新hash


        //由于前端使用form表单而非ajax,没有回调函数,以下可用可不用
        //注意；必须返回一个值,否则前端的回调函数无法执行;
        resp.setContentType("text/plain;charset=utf-8");
        resp.getWriter().write(JSON.toJSONString("success"));

        //经测试,以下跳转操作无效？？？
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/editOption");
        return mav;
    }

    @PostMapping("/deleteOption")
    public ModelAndView deleteOption(@RequestParam String optionName,
                                     @RequestParam Long optionItemId,
                                     HttpServletResponse resp) throws IOException {
        System.out.println("进入了@Controller的OptionController的addOption()...");
        //1.创建一个hashMap用于存储PojoMapper
        Map<String, PojoMapper> mapperMap = new HashMap<>();
        mapperMap.put("creditTo", creditToMapper);
        mapperMap.put("createDate", createDateMapper);
        mapperMap.put("createCountry", createCountryMapper);
        mapperMap.put("createCity", createCityMapper);
        mapperMap.put("theme", themeMapper);
        mapperMap.put("figure", figureMapper);
        //2.根据optionName如creditTo获取对应的XxxMapper
        PojoMapper targetMapper = mapperMap.get(optionName);

        //2.将pojo作为参数传入update中;
        targetMapper.delete(optionItemId);
        //注意：同时在RedisPointCut中更新hash

        //由于前端使用form表单而非ajax,没有回调函数,以下可用可不用
        //注意；必须返回一个值,否则前端的回调函数无法执行;
        resp.setContentType("text/plain;charset=utf-8");
        resp.getWriter().write(JSON.toJSONString("success"));

        //经测试,以下跳转操作无效？？？
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/editOption");
        return mav;

    }
}
