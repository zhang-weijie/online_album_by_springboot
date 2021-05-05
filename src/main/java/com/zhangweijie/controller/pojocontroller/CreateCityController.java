package com.zhangweijie.controller.pojocontroller;

import com.zhangweijie.mapper.CreateCityMapper;
import com.zhangweijie.pojo.CreateCity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhangweijie, E-mail:zhangweijiepku@gmail.com
 * @create 2021-01-12 10:33
 */
@RestController
public class CreateCityController implements PojoController<CreateCity> {
    @Autowired
    private CreateCityMapper mapper;

    @Override
    @GetMapping("/queryAllCreateCity")
    public List<CreateCity> queryAll() {
        return mapper.queryAll();
    }

    @Override
    //前端的form表单的action并不能拼接成restful的url,只有通过ajax才能实现
//    @GetMapping("/queryCreateCity/{id}")
    @GetMapping("/queryCreateCity")
    public CreateCity query(/*@PathVariable("id")*/ Long id) {
        return mapper.query(id);
    }

    @Override
    @PostMapping("/addCreateCity")
    public Boolean add(CreateCity createCity) {
        return mapper.add(createCity);
    }

    @Override
    //前端的form表单的action并不能拼接成restful的url,只有通过ajax才能实现
//    @PostMapping("/deleteCreateCity/{id}")
    @PostMapping("/deleteCreateCity")
    public Boolean delete(/*@PathVariable("id")*/ Long id) {
        return mapper.delete(id);
    }

    @Override
    @PostMapping("/updateCreateCity")
    public Boolean update(CreateCity createCity) {
        return mapper.update(createCity);
    }
}
