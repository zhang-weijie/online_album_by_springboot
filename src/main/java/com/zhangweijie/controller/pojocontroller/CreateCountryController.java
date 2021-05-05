package com.zhangweijie.controller.pojocontroller;

import com.zhangweijie.mapper.CreateCountryMapper;
import com.zhangweijie.pojo.CreateCountry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhangweijie, E-mail:zhangweijiepku@gmail.com
 * @create 2021-01-10 23:13
 */
@RestController
public class CreateCountryController implements PojoController<CreateCountry> {
    @Autowired
    private CreateCountryMapper mapper;

    @Override
    @GetMapping("/queryAllCreateCountry")
    public List<CreateCountry> queryAll() {
        return mapper.queryAll();
    }

    @Override
    //前端的form表单的action并不能拼接成restful的url,只有通过ajax才能实现
//    @GetMapping("/queryCreateCountry/{id}")
    @GetMapping("/queryCreateCountry")
    public CreateCountry query(/*@PathVariable("id")*/ Long id) {
        return mapper.query(id);
    }

    @Override
    @PostMapping("/addCreateCountry")
    public Boolean add(CreateCountry createCountry) {
        return mapper.add(createCountry);
    }

    @Override
    //前端的form表单的action并不能拼接成restful的url,只有通过ajax才能实现
//    @PostMapping("/deleteCreateCountry/{id}")
    @PostMapping("/deleteCreateCountry")
    public Boolean delete(/*@PathVariable("id")*/ Long id) {
        return mapper.delete(id);
    }

    @Override
    @PostMapping("/updateCreateCountry")
    public Boolean update(CreateCountry createCountry) {
        return mapper.update(createCountry);
    }
}
