package com.zhangweijie.controller.pojocontroller;

import com.zhangweijie.controller.pojocontroller.PojoController;
import com.zhangweijie.mapper.ThemeMapper;
import com.zhangweijie.pojo.Theme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhangweijie, E-mail:zhangweijiepku@gmail.com
 * @create 2021-01-12 10:41
 */
@RestController
public class ThemeController implements PojoController<Theme> {

    @Autowired
    private ThemeMapper mapper;

    @Override
    @GetMapping("/queryAllTheme")
    public List<Theme> queryAll() {
        return mapper.queryAll();
    }

    @Override
    //前端的form表单的action并不能拼接成restful的url,只有通过ajax才能实现
//    @GetMapping("/queryTheme/{id}")
    @GetMapping("/queryTheme")
    public Theme query(/*@PathVariable("id")*/ Long id) {
        return mapper.query(id);
    }

    @Override
    @PostMapping("/addTheme")
    public Boolean add(Theme theme) {
        return mapper.add(theme);
    }

    @Override
    //前端的form表单的action并不能拼接成restful的url,只有通过ajax才能实现
//    @PostMapping("/deleteTheme/{id}")
    @PostMapping("/deleteTheme")
    public Boolean delete(/*@PathVariable("id")*/ Long id) {
        return mapper.delete(id);
    }

    @Override
    @PostMapping("/updateTheme")
    public Boolean update(Theme theme) {
        return mapper.update(theme);
    }
}
