package com.entity.relation;

import com.utills.JDBCUtils.SQLColumn;
import com.utills.JDBCUtils.SQLIgnore;

import java.util.Date;

/**
 * Created by 10388 on 2017/6/5.
 */
public class UserRPaper {

    private int id;

    @SQLColumn("who")
    private int userId;

    @SQLColumn("which")
    private int paperId;

    @SQLColumn("submit_time")
    private String time;

    @SQLColumn("sec_used")
    private int timeUsed;

    private float accuracy;

    public UserRPaper() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPaperId() {
        return paperId;
    }

    public void setPaperId(int paperId) {
        this.paperId = paperId;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime() {

        return time;
    }

    public void setTimeUsed(int timeUsed) {
        this.timeUsed = timeUsed;
    }

    public int getTimeUsed() {

        return timeUsed;
    }

    public float getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(float accuracy) {
        this.accuracy = accuracy;
    }
}
