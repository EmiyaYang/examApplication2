package com.entity;

import com.utills.JDBCUtils.SQLColumn;
import com.utills.JDBCUtils.SQLIgnore;

import java.util.Date;

/**
 * Created by 10388 on 2017/6/1.
 */
public class PaperBean {

    @SQLColumn("paper.id")
    private int id;

    @SQLColumn("title")
    private String name;

    private String brief;

    @SQLColumn("create_time")
    private String createTime;

    @SQLIgnore
    private int click;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getClick() {
        return click;
    }

    public void setClick(int click) {
        this.click = click;
    }

}
