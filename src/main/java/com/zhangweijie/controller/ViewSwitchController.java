package com.zhangweijie.controller;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author zhangweijie, E-mail:zhangweijiepku@gmail.com
 * @create 2021-01-17 16:23
 */
@Controller
public class ViewSwitchController {
    @GetMapping("/toInfoTable")
    public ModelAndView toInfoTable(
            //由于infoTable页面的js需要从url中获取参数,因此此处采用get
            Long creditToId,
            Long createDateId,
            Long createCountryId,
            Long createCityId,
            Long themeId,
            Long figureId
    ) {
        System.out.println("进入了@Controller的ViewSwitchController的toInfoTable()...");

        ModelAndView mv = new ModelAndView();

        mv.setViewName("/infoTable");//进入infoTable.html

        return mv;
    }

    @GetMapping("/toEditOption")
    public ModelAndView toEditOption() {
        System.out.println("进入了@Controller的ViewSwitchController的toEditOption()...");

        ModelAndView mv = new ModelAndView();

        mv.setViewName("/editOption");//进入editOption.html

        return mv;
    }

}
