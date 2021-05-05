package com.zhangweijie.controller.pojocontroller;

import com.zhangweijie.mapper.CreditToMapper;
import com.zhangweijie.pojo.CreditTo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @author zhangweijie, E-mail:zhangweijiepku@gmail.com
 * @create 2021-01-12 10:12
 */
@Mapper
@Repository
public class CreditToController implements PojoController<CreditTo> {
    @Autowired
    private CreditToMapper mapper;

    @Override
    @GetMapping("/queryAllCreditTo")
    public List<CreditTo> queryAll() {
        return mapper.queryAll();
    }

    @Override
    //前端的form表单的action并不能拼接成restful的url,只有通过ajax才能实现
//    @GetMapping("/queryCreditTo/{id}")
    @GetMapping("/queryCreditTo")
    public CreditTo query(/*@PathVariable("id")*/ Long id) {
        return null;
    }

    @Override
    @PostMapping("/addCreditTo")
    public Boolean add(CreditTo creditTo) {
        return mapper.add(creditTo);
    }

    @Override
    //前端的form表单的action并不能拼接成restful的url,只有通过ajax才能实现
//    @PostMapping("/deleteCreditTo/{id}")
    @PostMapping("/deleteCreditTo")
    public Boolean delete(/*@PathVariable("id")*/ Long id) {
        return mapper.delete(id);
    }

    @Override
    @PostMapping("/updateCreditTo")
    public Boolean update(CreditTo creditTo) {
        return mapper.update(creditTo);
    }
}
