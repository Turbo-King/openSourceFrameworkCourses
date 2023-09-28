package com.xzit.wzh.web;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* Author: wzh
 * \* Date: 2023/9/28
 * \* TODO
 * \* Description: 分页
 * \
 */
public class Pager<T> {
    //定义当前页码
    private int pageNo = 1;
    //定义每页显示记录
    private int pageSize = 10;
    //总记录数
    private int count;
    //总页数
    private int totalPages;
    //定义保存数的集合
    private List<T> data;

    //相关方法
    public Pager() {
    }

    public Pager(int pageNo, int pageSize, int count, int totalPages, List<T> data) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.count = count;
        this.totalPages = totalPages;
        this.data = data;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotalPages() {
        return count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
