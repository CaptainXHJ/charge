package com.wallimn.iteye.sp.asset.common.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 这是通用的页面控制器。按规则进行跳转。
 * 介绍：<br>
 * 作者：wallimn 时间： 2017年12月21日 下午9:55:29<br>
 *
 */
@Controller
@RequestMapping
public class PageController{

    private static final Logger log = LoggerFactory.getLogger(PageController.class);

    @GetMapping(value = {"/index","/",""})
    public String login() {
        //log.debug("转登录页面");
        //redirect:
        return "public/index";
    }
    @GetMapping(value = {"/error"})
    public String error() {
        //log.debug("转登错误");
        return "public/error";
    }


    @GetMapping(value = "/{path}/{page}.html")
    public String page(@PathVariable String path,@PathVariable String page) {
        log.debug("path={},page={}",path,page);
        return path + "/" + page;
    }

}
