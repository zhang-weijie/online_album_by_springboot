package com.zhangweijie.controller.pojocontroller;

import com.zhangweijie.mapper.CreateDateMapper;
import com.zhangweijie.mapper.PojoMapper;
import com.zhangweijie.pojo.CreateDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhangweijie, E-mail:zhangweijiepku@gmail.com
 * @create 2021-01-12 9:53
 */
@RestController
public class CreateDateController implements PojoMapper<CreateDate> {
    @Autowired
    private CreateDateMapper mapper;


    @Override
    @GetMapping("/queryAllCreateDate")
    public List<CreateDate> queryAll() {
        return mapper.queryAll();
    }

    @Override
    //前端的form表单的action并不能拼接成restful的url,只有通过ajax才能实现
//    @GetMapping("/queryCreateDate/{id}")
    @GetMapping("/queryCreateDate")
    public CreateDate query(/*@PathVariable("id")*/ Long id) {
        return mapper.query(id);
    }

    @Override
    @PostMapping("/addCreateDate")
    public Boolean add(CreateDate createDate) {
        return mapper.add(createDate);
    }

    @Override
    //前端的form表单的action并不能拼接成restful的url,只有通过ajax才能实现
//    @PostMapping("/deleteCreateDate/{id}")
    @PostMapping("/deleteCreateDate")
    public Boolean delete(/*@PathVariable("id")*/ Long id) {
        return null;
    }

    @Override
    @PostMapping("/updateCreateDate")
    public Boolean update(CreateDate createDate) {
        return mapper.update(createDate);
    }
}
