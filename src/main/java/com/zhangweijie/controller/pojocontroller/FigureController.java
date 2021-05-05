package com.zhangweijie.controller.pojocontroller;

import com.zhangweijie.mapper.FigureMapper;
import com.zhangweijie.pojo.Figure;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * @author zhangweijie, E-mail:zhangweijiepku@gmail.com
 * @create 2021-01-13 8:16
 */
@Mapper
@Repository
public class FigureController implements PojoController<Figure> {
    @Autowired
    private FigureMapper mapper;

    @Override
    @GetMapping("/queryAllFigure")
    public List<Figure> queryAll() {
        return mapper.queryAll();
    }

    @Override
    //前端的form表单的action并不能拼接成restful的url,只有通过ajax才能实现
//    @GetMapping("/queryFigure/{id}")
    @GetMapping("/queryFigure")
    public Figure query(/*@PathVariable("id")*/ Long id) {
        return mapper.query(id);
    }

    @Override
    @PostMapping("/addFigure")
    public Boolean add(Figure figure) {
        return mapper.add(figure);
    }

    @Override
    //前端的form表单的action并不能拼接成restful的url,只有通过ajax才能实现
//    @PostMapping("/deleteFigure/{id}")
    @PostMapping("/deleteFigure")
    public Boolean delete(/*@PathVariable("id")*/ Long id) {
        return mapper.delete(id);
    }

    @Override
    @PostMapping("/updateFigure")
    public Boolean update(Figure figure) {
        return mapper.update(figure);
    }
}
