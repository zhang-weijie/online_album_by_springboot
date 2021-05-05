package com.zhangweijie.aop;

import com.zhangweijie.mapper.*;
import com.zhangweijie.pojo.*;
import com.zhangweijie.util.*;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 利用AOP实现对Redis的优先查询：即优先从数据库中查找相关数据
 * 需要导入spring-boot-starter-aop启动器
 *
 * @author zhangweijie, E-mail:zhangweijiepku@gmail.com
 * @create 2021-01-17 21:33
 */
@Aspect
@Component
public class RedisPointCut {
    //注入工具类RedisUtil的实例对象
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

    @Autowired
    private PhotoMapper photoMapper;

    //一、在OptionController的@RequestMapping("/loadOption")的loadOption()执行前判断：
    //1. 若Redis中已经有Option的Hash的情况下,不需要进入数据库查找
    //2. 若Redis中没有Option的Hash的情况下,需要进入数据库查找,并缓存到Redis中

    //execution执行语句可以存放到AspectUtil中,使用时从中调用
    @Around(AspectUtil.FOR_OPTION_INITIAL)
    public void around1(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("进入了@Aspect的RedisPointCut的around1()...");
        //以下的部分为了便于around3()和around4()复用,抽取到外部称为loadOrReloadOption()
        loadOrReloadOption();

        //执行OptionController的@RequestMapping("/loadOption")的loadOption()
        joinPoint.proceed();
    }

    public void loadOrReloadOption() {
        if (!redisUtil.hasKey("option")) {
            //先从数据库中获取option
            List<CreditTo> creditTos = creditToMapper.queryAll();
            List<CreateDate> createDates = createDateMapper.queryAll();
            List<CreateCountry> createCountries = createCountryMapper.queryAll();
            List<CreateCity> createCities = createCityMapper.queryAll();
            List<Theme> themes = themeMapper.queryAll();
            List<Figure> figures = figureMapper.queryAll();


            //创建Map接收option
            HashMap<Long, Object> creditToMap = new HashMap<>();
            HashMap<Long, Object> createDateMap = new HashMap<>();
            HashMap<Long, Object> createCountryMap = new HashMap<>();
            HashMap<Long, Object> createCityMap = new HashMap<>();
            HashMap<Long, Object> themeMap = new HashMap<>();
            HashMap<Long, Object> figureMap = new HashMap<>();

            //注入option
            for (CreditTo creditTo : creditTos) {
                creditToMap.put(creditTo.getId(), creditTo.getName());
            }
            for (CreateDate createDate : createDates) {
                createDateMap.put(createDate.getId(), createDate.getDate());
            }
            for (CreateCountry createCountry : createCountries) {
                createCountryMap.put(createCountry.getId(), createCountry.getName());
            }
            for (CreateCity createCity : createCities) {
                createCityMap.put(createCity.getId(), createCity.getName());
            }
            for (Theme theme : themes) {
                themeMap.put(theme.getId(), theme.getName());
            }
            for (Figure figure : figures) {
                figureMap.put(figure.getId(), figure.getName());
            }

            //再存入到Redis的Hash中
            redisUtil.hset("option", "creditTo", creditToMap);
            redisUtil.hset("option", "createDate", createDateMap);
            redisUtil.hset("option", "createCountry", createCountryMap);
            redisUtil.hset("option", "createCity", createCityMap);
            redisUtil.hset("option", "theme", themeMap);
            redisUtil.hset("option", "figure", figureMap);
        }
    }

    //以下弃用,因为增删改可以不经由PojoController来完成,否则太过复杂,可以由OptionController中的addOption()/deleteOption()/updateOption()完成
    //二、每次对数据库中的Option进行增删改后也需要更新Redis中Option的Hash
    //即在调用PojoController的实现类时也需要aop
    @Deprecated
    @Around(AspectUtil.FOR_OPTION_UPDATE_DEPRECATED)
    public void around2(ProceedingJoinPoint joinPoint) throws Throwable {
        //执行对数据库中的Option的增删改
        joinPoint.proceed();

        //通过jointPoint获取执行execution的类名(XxxYyyController)、方法名(add/delete/update)和参数(Pojo/id),以便进行相应的操作
        //参见：https://blog.csdn.net/qq_33535433/article/details/79940747
        String pojoControllerName = joinPoint.getSignature().getDeclaringType().getSimpleName();//XxxYyyController
        String pojoName = MyStringUtil.getPojoName(pojoControllerName);//xxxYyy
        String methodName = joinPoint.getSignature().getName();//add/delete/update
        Object[] args = joinPoint.getArgs();//获取传入execution的参数

        //动态设置泛型,需要创建Pojo接口（或父类？）,令pojo类实现该接口,
        //然后才能进行强转
        //为了使用Pojo接口获取pojo类的id和name,还需要在Pojo接口中实现相应的方法
        //参见：https://www.it1352.com/1523388.html
        Map<Long, Object> map = (Map<Long, Object>) redisUtil.hget("option", pojoName);

        if ("add".equals(methodName)) {
            Pojo pojo = (Pojo) args[0];
            Long id = pojo.getId();
            Object object = pojo.getObject();
            map.put(id, object);
        } else if ("delete".equals(methodName)) {
            Long id = (Long) args[0];
            map.remove(id);
        } else {
            Pojo pojo = (Pojo) args[0];
            Long id = pojo.getId();
            Object object = pojo.getObject();
            map.remove(id);
            map.put(id, object);
        }
        redisUtil.hset("option", pojoName, map);
    }

    //以下弃用,因为重复执行了OptionController.addOption/updateOption/deleteOption
    @Deprecated
//    @Around(AspectUtil.FOR_OPTION_UPDATE)
    public void around3(ProceedingJoinPoint joinPoint) throws Throwable {
        //执行对数据库中的Option的增删改
        joinPoint.proceed();

        //通过jointPoint获取执行execution的方法名(addOption/deleteOption/updateOption)和参数(OptionName,OptionItemName),以便进行相应的操作
        //参见：https://blog.csdn.net/qq_33535433/article/details/79940747
        String methodName = joinPoint.getSignature().getName();//addOption/deleteOption/updateOption
        Object[] args = joinPoint.getArgs();//获取传入execution的参数:args[0]即(String)optionName,args[1]即(Object)optionItemName

        //动态设置泛型,需要创建Pojo接口（或父类？）,令pojo类实现该接口,
        //然后才能进行强转
        //为了使用Pojo接口获取pojo类的id和name,还需要在Pojo接口中实现相应的方法
        //参见：https://www.it1352.com/1523388.html
        Map<Long, Object> optionMap = (Map<Long, Object>) redisUtil.hget("option", args[0].toString());

        //根据args[0]即optionName如creditTo决定使用哪个mapper如creditToMapper;
        Map<String, PojoMapper> mapperMap = new HashMap<>();
        mapperMap.put("creditTo", creditToMapper);
        mapperMap.put("createDate", createDateMapper);
        mapperMap.put("createCountry", createCountryMapper);
        mapperMap.put("createCity", createCityMapper);
        mapperMap.put("theme", themeMapper);
        mapperMap.put("figure", figureMapper);

        PojoMapper targetMapper = mapperMap.get(args[0].toString());

        //根据methodName如addOption决定调用该mapper的哪个方法;
        switch (methodName) {
            case "addOption":
                targetMapper.add(args[1]);
                break;
            case "updateOption":
                targetMapper.update(args[1]);
                break;
            case "deleteOption":
                targetMapper.delete((Long) args[1]);
        }

        //更新redis的hash中存储的option
        redisUtil.hdel("option", "creditTo");
        redisUtil.hdel("option", "createDate");
        redisUtil.hdel("option", "createCountry");
        redisUtil.hdel("option", "createCity");
        redisUtil.hdel("option", "theme");
        redisUtil.hdel("option", "figure");
        loadOrReloadOption();
    }

    @Around(AspectUtil.FOR_OPTION_UPDATE)
    public void around4(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("进入了@Aspect的RedisPointCut的around4()...");

        //1.执行对数据库中的Option的增删改
        joinPoint.proceed();

        //2.更新redis的hash中存储的option
        //(1) 首先删除原有的option
        redisUtil.hdel("option", "creditTo");
        redisUtil.hdel("option", "createDate");
        redisUtil.hdel("option", "createCountry");
        redisUtil.hdel("option", "createCity");
        redisUtil.hdel("option", "theme");
        redisUtil.hdel("option", "figure");
        //(2) 从数据库中下载新的option
        loadOrReloadOption();
    }


    //三、查看图片时判断应当从数据库中获取存到redis的hash中,还是从hash中直接获取
    // 在PhotoController的@PostMapping("/viewPhoto")的view(Long id)上切入
    @Around(AspectUtil.FOR_PHOTO_VIEW)
    public void around5(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("进入了@Aspect的RedisPointCut的around5()...");
        //获取前端提交表单的id,即要查看照片的id
        Object[] args = joinPoint.getArgs();
        Long id = (Long) args[0];
        System.out.println("RedisPointCut.around3():id = " + id);
        boolean isInCache = redisUtil.hHasKey("photo", id.toString());
        if (!isInCache) {
            //从数据库中获取编号为id的photo的data
            Photo photo = photoMapper.queryData(id);
            byte[] data = photo.getData();
            //直接将二进制字节数组存入redis会导致乱码,使用Base64对其进行编码后再存入redis
            String dataAsString = Base64Util.encodeToString(data);
            //将dataAsString存入redis的hash中,并设置有效时间
            redisUtil.hset("photo", id.toString(), dataAsString, RedisHashUtil.EXPIRE_TIME);
        }

        joinPoint.proceed();
    }

}
