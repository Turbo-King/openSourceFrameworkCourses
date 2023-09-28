package com.xzit.wzh.web.controller;

import com.alibaba.fastjson2.JSON;
import com.xzit.wzh.service.MajorInfoService;
import com.xzit.wzh.service.impl.MajorInfoServiceImpl;
import com.xzit.wzh.web.Pager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * \* Created with IntelliJ IDEA.
 * \* Author: wzh
 * \* Date: 2023/9/28
 * \* TODO
 * \* Description: Major控制层
 * \
 */
//基于注解的方式
@WebServlet(urlPatterns = {"/majorServlet", "/major"}, loadOnStartup = 1)
public class MajorInfoServlet extends HttpServlet {
    MajorInfoService majorInfoService = new MajorInfoServiceImpl();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //接收客户端的请求
        req.setCharacterEncoding("utf-8");
        String majorName = req.getParameter("majorName");
        int pageNo = Integer.parseInt(req.getParameter("pageNo"));
        int pageSize = Integer.parseInt(req.getParameter("pageSize"));
        //调用service组件完成请求的功能
        Pager pager = majorInfoService.findByPage(majorName, pageNo, pageSize);
        //返回处理的结果（JSON）
        resp.setContentType("text/json;charset=UTF-8");
        String json = JSON.toJSONString(pager);
        PrintWriter out = resp.getWriter();
        out.println(json);
        out.flush();
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        System.out.println("------doPost-------");
    }
}
