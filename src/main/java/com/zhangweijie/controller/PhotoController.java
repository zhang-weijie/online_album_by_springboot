package com.zhangweijie.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zhangweijie.mapper.PhotoMapper;
import com.zhangweijie.pojo.Photo;
import com.zhangweijie.util.Base64Util;
import com.zhangweijie.util.MyStringUtil;
import com.zhangweijie.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Date;
import java.util.List;

/**
 * @author zhangweijie, E-mail:zhangweijiepku@gmail.com
 * @create 2021-01-13 8:22
 * <p>
 * PhotoController应当同时执行对photo_table和photo_data_table的增删改操作
 */
@Controller
//@RestController
public class PhotoController {
    //注入工具类RedisUtil的实例对象
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private PhotoMapper mapper;

    @GetMapping("/queryAllInfo")
    public List<Photo> queryAll() {
        return mapper.queryAllInfo();
    }

    //前端的form表单的action并不能拼接成restful的url,只有通过ajax才能实现
    @GetMapping("/queryMultiInfo")
    public void queryMultiInfoByGet(HttpServletResponse resp,
            /*@PathVariable("creditToId")*/ Long creditToId,
            /*@PathVariable("createDateId")*/ Long createDateId,
            /*@PathVariable("createCountryId")*/ Long createCountryId,
            /*@PathVariable("createCityId")*/ Long createCityId,
            /*@PathVariable("themeId")*/ Long themeId,
            /*@PathVariable("figureId")*/ Long figureId) throws IOException {

        System.out.println("进入了@GetMapping()的PhotoController的queryPhotos...");
//        测试前端默认的input值;结论:null
        System.out.println("creditToId\tcreateDateId\tcreateCountryId\tcreateCityId\tthemeId\tfigureId\t");
        System.out.println("" + creditToId + "\t" + createDateId + "\t" +
                createCountryId + "\t" + createCityId + "\t" + themeId + "\t" + figureId + "\t");

        List<Photo> photos = mapper.queryMultiInfo(null, creditToId, createDateId, createCountryId, createCityId, themeId, figureId);

        //将list转换为JSON字符串,注意要禁止循环引用
        String jsonString = JSON.toJSONString(photos, SerializerFeature.DisableCircularReferenceDetect);

        //将响应格式设置为text/plain,否则firefox会当作默认的xml格式进行解析
        //将字符集设置为utf-8,否则会出现中文乱码
        resp.setContentType("text/plain;charset=utf-8");

        resp.getWriter().write(jsonString);
    }

    //前端的form表单的action并不能拼接成restful的url,只有通过ajax才能实现
    @PostMapping("/queryMultiInfo")
//    {id}/{creditToId}/{createDateId}/{createCountryId}/{createCityId}/{themeId}/{figureId}
    public void queryMultiInfoByPost(HttpServletResponse resp,
            /*@PathVariable("creditToId")*/ Long creditToId,
            /*@PathVariable("createDateId")*/ Long createDateId,
            /*@PathVariable("createCountryId")*/ Long createCountryId,
            /*@PathVariable("createCityId")*/ Long createCityId,
            /*@PathVariable("themeId")*/ Long themeId,
            /*@PathVariable("figureId")*/ Long figureId) throws IOException {

        System.out.println("进入了@PostMapping()的PhotoController的queryPhotos...");
//        测试前端默认的input值;结论:null
        System.out.println("creditToId\tcreateDateId\tcreateCountryId\tcreateCityId\tthemeId\tfigureId\t");
        System.out.println("" + creditToId + "\t" + createDateId + "\t" +
                createCountryId + "\t" + createCityId + "\t" + themeId + "\t" + figureId + "\t");

        List<Photo> photos = mapper.queryMultiInfo(null, creditToId, createDateId, createCountryId, createCityId, themeId, figureId);

        //将list转换为JSON字符串,注意要禁止循环引用
        String jsonString = JSON.toJSONString(photos, SerializerFeature.DisableCircularReferenceDetect);

        //将响应格式设置为text/plain,否则firefox会当作默认的xml格式进行解析
        //将字符集设置为utf-8,否则会出现中文乱码
        resp.setContentType("text/plain;charset=utf-8");

        resp.getWriter().write(jsonString);

    }

    @GetMapping("/toAddPhoto")
    public ModelAndView toAdd() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("addPhoto");//跳转至addPhoto.html
        return modelAndView;
    }

    @PostMapping("/addPhoto")
    public ModelAndView add(
            @RequestParam(required = false) Long creditToId,
            @RequestParam(required = false) Long createDateId,
            @RequestParam(required = false) Long createCountryId,
            @RequestParam(required = false) Long createCityId,
            @RequestParam(required = false) Long themeId,
            @RequestParam(required = false) Long figureId,
            @RequestParam(required = false) String desc,
//          直接传入mutipartFile参数Spring将无法自动映射上传文件到参数中，因此需要使用@RequestParam指定，将上传的文件映射到参数中。
            @RequestParam CommonsMultipartFile file,
            HttpServletResponse resp) throws IOException {
        System.out.println("进入了@PostMapping()的PhotoController的add()...");
//        测试前端默认的input值;结论:null
        System.out.println("creditToId\tcreateDateId\tcreateCountryId\tcreateCityId\tthemeId\tfigureId\tdesc\t");
        System.out.println("" + "\t" + creditToId + "\t" + createDateId + "\t" +
                createCountryId + "\t" + createCityId + "\t" + themeId + "\t" + figureId + "\t" + desc + "\t");

        //将file转换成byte[]
        byte[] data = null;
        if (file != null) {
            data = file.getBytes();
        }
        Photo photo = new Photo(null, creditToId, new Date(System.currentTimeMillis()), createDateId, createCountryId, createCityId, themeId, figureId, desc, data);
//        Photo photo = new Photo(null, Long.valueOf(creditToId), new Date(System.currentTimeMillis()), Long.valueOf(createDateId), Long.valueOf(createCountryId), Long.valueOf(createCountryId), Long.valueOf(themeId), Long.valueOf(figureId), desc,data);
        mapper.add(photo);
        System.out.println("添加图片完成...");

        ModelAndView mav = new ModelAndView();
        //直接返回mav会报错：Request method ‘POST’ not supported
        //解决方案：使用重定向
        mav.setViewName("redirect:/addPhoto.html");
        return mav;
    }

    //由于普通的form表单中type="text"的input数据不能与type="file",编码为formenctype="multipart/form-data"的数据兼容
    //故而要在新增照片时单独分配一个Mapping给/upload
    //也可在前端利用javascript实现
    /*
    @PostMapping("/uploadPhotoWhenAdd")
    public void upload(@RequestParam("file") CommonsMultipartFile file){
        System.out.println("进入了@PostMapping()的PhotoController的upload()...");

        byte[] data = file.getBytes();

        PhotoData photoData = new PhotoData(null, data);
        mapper1.add(photoData);
    }
    */


    @PostMapping("/updatePhoto")
    public ModelAndView update(@RequestParam Long id,
                               @RequestParam Long creditToId,
                               @RequestParam Long createDateId,
                               @RequestParam Long createCountryId,
                               @RequestParam Long createCityId,
                               @RequestParam Long themeId,
                               @RequestParam Long figureId,
                               @RequestParam String desc,
                               //直接传入mutipartFile参数Spring将无法自动映射上传文件到参数中，
                               //因此需要使用@RequestParam指定，将上传的文件映射到参数中。
                               //如果参数名跟form表单中对应标签的name一致,则不需要指定
            /*@RequestParam("file")或*/@RequestParam CommonsMultipartFile file) throws UnsupportedEncodingException {
        System.out.println("进入了@PostMapping()的PhotoController的update()...");
        System.out.println("id\tcreditToId\tcreateDateId\tcreateCountryId\tcreateCityId\tthemeId\tfigureId\tdesc\t");
        System.out.println("" + id + "\t" + creditToId + "\t" + createDateId + "\t" +
                createCountryId + "\t" + createCityId + "\t" + themeId + "\t" + figureId + "\t" + desc + "\t");

        //将file转换成byte[]
        byte[] data = null;
        if (file != null) {
            data = file.getBytes();
        }
        Photo photo = new Photo(id, creditToId, new Date(System.currentTimeMillis()), createDateId, createCountryId, createCityId, themeId, figureId, desc, data);

        mapper.update(photo);

        ModelAndView mav = new ModelAndView();
        //注意重定向时需要携带参数,否则前端updatePhoto.html的getQueryVariable()无法获取参数;
        //对url中的一些特殊字符如@进行处理：转换为%ASCII的格式
        desc = MyStringUtil.getURL(desc);
        mav.setViewName("redirect:/updatePhoto.html?" +
                "id=" + id +
                "&creditToId=" + creditToId +
                "&createCountryId=" + createCountryId +
                "&createCityId=" + createCityId +
                "&themeId=" + themeId +
                "&figureId=" + figureId +
                "&desc=" + desc);
        return mav;
    }

    //查看照片
    //从redis的hash中获取,参见RedisPointCut.around3()
    @PostMapping("/viewPhoto")
    public ModelAndView view(@RequestParam("id") Long id,
                             HttpServletResponse resp) throws IOException {
        System.out.println("进入了@PostMapping()的PhotoController的view()...");
        //从redis的hash中获取,参见RedisPointCut.around3()
        System.out.println("PhotoController.view():id = " + id);
//        String dataAsString = (String) redisUtil.hget("photo", id.toString());
//        byte[] data = dataAsString.getBytes(StandardCharsets.UTF_8);
//        byte[] data = mapper.queryData(id).getData();
//        System.out.println("data.length =" + data.length);

        //利用Base64将二进制文件转换为json
        //参见：Base64Util.encodeToJSON
        //https://www.cnblogs.com/WarBlog/p/4747155.html
        //https://www.cnblogs.com/hula100/p/7350628.html
        resp.setContentType("text/plain;charset=utf-8");
        String string = (String) redisUtil.hget("photo", id.toString());
        String jsonString = JSON.toJSONString(string, SerializerFeature.DisableCircularReferenceDetect);
        resp.getWriter().write(jsonString);

        return new ModelAndView();
    }

    //考虑到文件传输时采用的是二进制数组+Base64编码的方式，服务器上没有本地文件
    //因此在下载时通过前端调用后台view()将Base64编码传到前端,然后在前端完成下载即可;
//    @PostMapping("/downloadPhoto")
//    public ModelAndView download(@RequestParam Long id){
//
//    }

    //前端的form表单的action并不能拼接成restful的url,只有通过ajax才能实现
//    @PostMapping("/deletePhoto/{id}")
    @PostMapping("/deletePhoto")
    public void delete(/*@PathVariable("id")*/@RequestParam Long id,
                                              HttpServletResponse resp) throws IOException {
        System.out.println("进入了@PostMapping()的PhotoController的delete()...");
        System.out.println("id\t");
        System.out.println(id);
        mapper.delete(id);

        //必须返回一个值,否则前端的location.reload()无法执行;
        resp.setContentType("text/plain;charset=utf-8");
        resp.getWriter().write("success");
    }
}
